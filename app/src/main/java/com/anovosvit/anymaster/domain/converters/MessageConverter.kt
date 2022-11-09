package com.anovosvit.anymaster.domain.converters

import com.anovosvit.anymaster.data.models.MessageResponse
import com.anovosvit.anymaster.presentation.models.RecyclerChatMessage
import com.anovosvit.anymaster.utils.getTimeString
import java.util.*

fun MessageResponse.toRecyclerChatMessage(isLastMessageInChain: Boolean = true) =
    RecyclerChatMessage(
        id = UUID.fromString(id),
        type = type,
        time = time.getTimeString(),
        text = text,
        isDelivered = isDelivered,
        isLastMessageInChain = isLastMessageInChain
    )

fun List<MessageResponse>.toMessages(): List<RecyclerChatMessage> = when (size) {
    0 -> emptyList()
    1 -> map { it.toRecyclerChatMessage(true) }
    else -> mapIndexed { index, messageResponse ->
        val isLastMessageInChain = if (index < lastIndex) {
            get(lastIndex).type != get(index).type
        } else true
        messageResponse.toRecyclerChatMessage(isLastMessageInChain)
    }
}
