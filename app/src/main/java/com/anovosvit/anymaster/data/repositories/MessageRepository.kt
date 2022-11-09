package com.anovosvit.anymaster.data.repositories

import com.anovosvit.anymaster.data.models.MessageResponse
import com.anovosvit.anymaster.presentation.models.MessageType
import kotlinx.coroutines.flow.Flow
import java.util.*

interface MessageRepository {

    suspend fun fetchChatHistory(): Flow<List<MessageResponse>>

    suspend fun sendMessage(uuid: UUID, type: MessageType, time: String, text: String)

    suspend fun changeDeliveredStatus(messageUUID: UUID)

    suspend fun editMessage(messageUUID: UUID, newText: String)

    suspend fun subscribeToDeliveredMessages(): Flow<UUID>

    suspend fun subscribeToNewMessages(): Flow<MessageResponse>

}
