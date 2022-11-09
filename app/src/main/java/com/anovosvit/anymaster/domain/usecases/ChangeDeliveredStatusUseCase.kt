package com.anovosvit.anymaster.domain.usecases

import com.anovosvit.anymaster.data.repositories.MessageRepository
import java.util.*
import javax.inject.Inject

class ChangeDeliveredStatusUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend fun execute(messageUUID: UUID) =
        messageRepository.changeDeliveredStatus(messageUUID)

}
