package com.anovosvit.anymaster.domain.usecases

import com.anovosvit.anymaster.data.repositories.MessageRepository
import com.anovosvit.anymaster.domain.converters.toRecyclerChatMessage
import com.anovosvit.anymaster.presentation.models.RecyclerChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscribeToNewMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    suspend fun execute(): Flow<RecyclerChatMessage> =
        messageRepository.subscribeToNewMessages().map { it.toRecyclerChatMessage() }

}
