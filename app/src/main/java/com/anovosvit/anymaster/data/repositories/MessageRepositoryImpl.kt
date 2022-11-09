package com.anovosvit.anymaster.data.repositories

import com.anovosvit.anymaster.data.models.MessageResponse
import com.anovosvit.anymaster.data.sources.SocketMock
import com.anovosvit.anymaster.presentation.models.MessageType
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val socketMock: SocketMock) :
    MessageRepository {

    override suspend fun fetchChatHistory(): Flow<List<MessageResponse>> =
        socketMock.fetchChatHistory()

    override suspend fun sendMessage(uuid: UUID, type: MessageType, time: String, text: String) =
        socketMock.sendMessage(uuid, type, time, text)

    override suspend fun changeDeliveredStatus(messageUUID: UUID) =
        socketMock.changeDeliveredStatus(messageUUID)

    override suspend fun editMessage(messageUUID: UUID, newText: String) =
        socketMock.editMessage(messageUUID, newText)

    override suspend fun subscribeToDeliveredMessages(): Flow<UUID> =
        socketMock.subscribeToDeliveredMessages()

    override suspend fun subscribeToNewMessages(): Flow<MessageResponse> =
        socketMock.subscribeToNewMessages()

}
