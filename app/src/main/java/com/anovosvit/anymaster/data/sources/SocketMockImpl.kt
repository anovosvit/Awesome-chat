package com.anovosvit.anymaster.data.sources

import com.anovosvit.anymaster.data.models.MessageResponse
import com.anovosvit.anymaster.presentation.models.MessageType
import com.anovosvit.anymaster.utils.generateRandomMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import java.util.*

// Connect your ws client
class SocketMockImpl : SocketMock {

    private val _deliveredMessageIdsFlow = MutableSharedFlow<UUID>()
    private val deliveredMessageIdsFlow = _deliveredMessageIdsFlow.asSharedFlow()

    private val _newMessagesFlow = MutableSharedFlow<MessageResponse>()
    private val newMessagesFlow = _newMessagesFlow.asSharedFlow()

    override suspend fun fetchChatHistory(): Flow<List<MessageResponse>> =
        flow { emit(MessagesStub.messages) }

    override suspend fun sendMessage(uuid: UUID, type: MessageType, time: String, text: String) {
        // send message text and simulate answers
        simulateEventsInChannels(uuid)
    }

    private suspend fun simulateEventsInChannels(uuid: UUID) {
        // Mark message as read after 1s waiting
        delay(MOCK_DELAY)
        _deliveredMessageIdsFlow.emit(uuid)
        // Send a response message on condition (adds the effect of a random) after 1s waiting
        if (System.currentTimeMillis() / 1000 % 2 == 0L) {
            delay(MOCK_DELAY)
            _newMessagesFlow.emit(generateRandomMessage(type = MessageType.OTHER))
        }
    }

    override suspend fun changeDeliveredStatus(messageUUID: UUID) {
        // change delivered status for message with messageUUID
    }

    override suspend fun editMessage(messageUUID: UUID, newText: String) {
        // edit message with messageUUID
    }

    override suspend fun subscribeToDeliveredMessages(): Flow<UUID> = deliveredMessageIdsFlow

    override suspend fun subscribeToNewMessages(): Flow<MessageResponse> = newMessagesFlow

    companion object {
        private const val MOCK_DELAY = 1000L
    }

}