package com.anovosvit.anymaster.domain.usecases

import com.anovosvit.anymaster.data.repositories.MessageRepository
import com.anovosvit.anymaster.domain.converters.toMessages
import com.anovosvit.anymaster.presentation.models.RecyclerChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchChatHistoryUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend fun execute(): Flow<List<RecyclerChatMessage>> =
        messageRepository.fetchChatHistory().map { it.toMessages() }

}
