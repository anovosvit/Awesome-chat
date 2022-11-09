package com.anovosvit.anymaster.domain.usecases

import com.anovosvit.anymaster.data.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class SubscribeToDeliveredStatusUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    suspend fun execute(): Flow<UUID> =
        messageRepository.subscribeToDeliveredMessages()

}
