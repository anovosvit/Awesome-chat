package com.anovosvit.anymaster.presentation.models

import com.anovosvit.anymaster.utils.createUniqueUUID
import java.util.*

data class RecyclerChatMessage(
    val id: UUID = createUniqueUUID(),
    val type: MessageType = MessageType.MY,
    val time: String = "",
    val text: String,
    val isDelivered: Boolean = false,
    // Shows the last message in the message chain of one type.
    // If true, show the tongue for message shape
    val isLastMessageInChain: Boolean = true,
)

enum class MessageType(val typeId: Int) {
    FIRST(0), TITLE(1), MY(2), OTHER(3)
}

fun List<RecyclerChatMessage>.changeLastMessagesInChain(): List<RecyclerChatMessage> = when (size) {
    0, 1 -> this
    else -> if ((this[lastIndex].type == MessageType.MY && this[lastIndex - 1].type == MessageType.MY)
        || (this[lastIndex].type == MessageType.OTHER && this[lastIndex - 1].type == MessageType.OTHER)
    ) mapIndexed { index, recyclerChatMessage ->
        if (index == lastIndex || index == lastIndex - 1) {
            recyclerChatMessage.copy(isLastMessageInChain = index == lastIndex)
        } else recyclerChatMessage
    } else this
}

fun List<RecyclerChatMessage>.editMessage(message: RecyclerChatMessage, newText: String) =
    map { messageItem ->
        if (messageItem == message) messageItem.copy(text = newText)
        else messageItem
    }

fun List<RecyclerChatMessage>.markAsDelivered(uuid: UUID) =
    map { messageItem ->
        if (messageItem.id == uuid) messageItem.copy(isDelivered = true)
        else messageItem
    }
