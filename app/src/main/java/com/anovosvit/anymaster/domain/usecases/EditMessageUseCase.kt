package com.anovosvit.anymaster.domain.usecases

import com.anovosvit.anymaster.data.repositories.MessageRepository
import java.util.*
import javax.inject.Inject

class EditMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend fun execute(messageUUID: UUID, newText: String) =
        messageRepository.editMessage(messageUUID, newText)

}
