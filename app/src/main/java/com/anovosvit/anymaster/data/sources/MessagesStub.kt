package com.anovosvit.anymaster.data.sources

import com.anovosvit.anymaster.data.models.MessageResponse
import com.anovosvit.anymaster.presentation.models.MessageType
import com.anovosvit.anymaster.utils.generateRandomMessage

object MessagesStub {

    val messages = listOf(
        MessageResponse(text = "Today", type = MessageType.TITLE),
        generateRandomMessage(MessageType.FIRST, 0),
        generateRandomMessage(MessageType.MY),
        generateRandomMessage(MessageType.MY),
        generateRandomMessage(MessageType.OTHER),
        generateRandomMessage(MessageType.OTHER),
    )

}
