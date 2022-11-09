package com.anovosvit.anymaster.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anovosvit.anymaster.domain.usecases.*
import com.anovosvit.anymaster.presentation.models.*
import com.anovosvit.anymaster.utils.createUniqueUUID
import com.anovosvit.anymaster.utils.getCurrentTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val fetchChatHistoryUseCase: FetchChatHistoryUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val editMessageUseCase: EditMessageUseCase,
    private val changeDeliveredStatusUseCase: ChangeDeliveredStatusUseCase,
    private val subscribeToNewMessagesUseCase: SubscribeToNewMessagesUseCase,
    private val subscribeToDeliveredStatusUseCase: SubscribeToDeliveredStatusUseCase,
) : ViewModel() {

    private val _viewState = MutableLiveData(ChatViewState())
    val viewState: LiveData<ChatViewState> = _viewState

    fun obtainEvent(viewEvent: ChatEvent) {
        when (viewEvent) {
            is ChatEvent.EditMessage -> {
                _viewState.value?.let {
                    _viewState.postValue(
                        it.copy(
                            isEditModeEnabled = false,
                            needToScroll = false,
                            messages = it.messages.editMessage(viewEvent.message, viewEvent.newText)
                        )
                    )
                }
                editMessage(viewEvent.message, viewEvent.newText)
            }
            ChatEvent.ContextMenuClosed -> _viewState.postValue(
                _viewState.value?.copy(isBlurred = false, needToScroll = false)
            )
            ChatEvent.ContextMenuOpened -> _viewState.postValue(
                _viewState.value?.copy(isBlurred = true, needToScroll = false)
            )
            is ChatEvent.MessageWasRead -> changeDeliveredStatus(viewEvent.uuid)
            is ChatEvent.SendMessage -> sendMessage(viewEvent.text)
            ChatEvent.EditMessageModeEnabled -> _viewState.postValue(
                _viewState.value?.copy(
                    isEditModeEnabled = true,
                    isBlurred = false,
                    needToScroll = false
                )
            )
            ChatEvent.Init -> {
                fetchData()
                subscribeToNewMessages()
                subscribeToDeliveredStatus()
            }
            ChatEvent.EditMessageModeDisabled -> _viewState.postValue(
                _viewState.value?.copy(isEditModeEnabled = false, needToScroll = false)
            )
        }
    }

    private fun editMessage(message: RecyclerChatMessage, newText: String) {
        viewModelScope.launch { editMessageUseCase.execute(message.id, newText) }
    }

    private fun changeDeliveredStatus(uuid: UUID) {
        viewModelScope.launch { changeDeliveredStatusUseCase.execute(uuid) }
    }

    private fun subscribeToNewMessages() {
        viewModelScope.launch {
            subscribeToNewMessagesUseCase.execute()
                .catch {
                    // handle error
                }.collect { newMessage ->
                    changeStateWithNewMessage(newMessage)
                }
        }
    }

    private fun subscribeToDeliveredStatus() {
        viewModelScope.launch {
            subscribeToDeliveredStatusUseCase.execute()
                .catch {
                    // handle error
                }.collect { deliveredUUID ->
                    _viewState.postValue(
                        _viewState.value?.copy(
                            needToScroll = false,
                            messages = _viewState.value?.messages?.markAsDelivered(deliveredUUID)
                                ?: listOf()
                        )
                    )
                }
        }
    }

    private fun sendMessage(text: String) {
        val message = RecyclerChatMessage(
            id = createUniqueUUID(), text = text, time = getCurrentTimeString()
        )
        changeStateWithNewMessage(message)
        viewModelScope.launch {
            sendMessageUseCase.execute(
                uuid = message.id,
                type = message.type,
                time = message.time,
                text = message.text
            )
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            fetchChatHistoryUseCase.execute()
                .catch {
                    // handle error
                }.collect {
                    _viewState.postValue(_viewState.value?.copy(messages = it, needToScroll = true))
                }
        }
    }

    private fun changeStateWithNewMessage(newMessage: RecyclerChatMessage) {
        _viewState.postValue(
            _viewState.value?.copy(
                needToScroll = true,
                messages = (_viewState.value?.messages?.toMutableList()
                    .also { list -> list?.add(newMessage) }
                    ?: listOf(newMessage)).changeLastMessagesInChain()
            )
        )
    }

    sealed class ChatEvent {
        object ContextMenuOpened : ChatEvent()
        object ContextMenuClosed : ChatEvent()
        object EditMessageModeEnabled : ChatEvent()
        object EditMessageModeDisabled : ChatEvent()
        class EditMessage(val message: RecyclerChatMessage, val newText: String) : ChatEvent()
        object Init : ChatEvent()
        class SendMessage(val text: String) : ChatEvent()
        class MessageWasRead(val uuid: UUID) : ChatEvent()
    }

}