package com.anovosvit.anymaster.utils

import com.anovosvit.anymaster.data.models.MessageResponse
import com.anovosvit.anymaster.presentation.models.MessageType
import java.text.SimpleDateFormat
import java.util.*

fun Long.getTimeString(): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(this))

fun getCurrentTimeString(): String = Date().time.getTimeString()

fun createUniqueUUID(): UUID = UUID.randomUUID()

fun generateRandomMessage(
    type: MessageType,
    messageSize: Int = (5 until LOREM.length).random(),
) = MessageResponse(
    id = createUniqueUUID().toString(),
    type = type,
    time = System.currentTimeMillis(),
    text = LOREM.substring(0, messageSize),
    isDelivered = true
)

private const val LOREM =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. In est ante in nibh mauris cursus."
