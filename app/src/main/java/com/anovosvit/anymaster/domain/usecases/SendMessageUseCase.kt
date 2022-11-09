package com.anovosvit.anymaster.domain.usecases

import com.anovosvit.anymaster.data.repositories.MessageRepository
import com.anovosvit.anymaster.presentation.models.MessageType
import java.util.*
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {

    suspend fun execute(uuid: UUID, type: MessageType, time: String, text: String) =
        messageRepository.sendMessage(
            uuid = uuid,
            type = type,
            time = time,
            text = text
        )

}