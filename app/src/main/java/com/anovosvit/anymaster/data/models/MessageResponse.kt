package com.anovosvit.anymaster.data.models

import com.anovosvit.anymaster.presentation.models.MessageType
import com.anovosvit.anymaster.utils.createUniqueUUID

data class MessageResponse(
    val id: String = createUniqueUUID().toString(),
    val text: String = "",
    val time: Long = 0L,
    val type: MessageType = MessageType.FIRST,
    val isDelivered: Boolean = true
)
