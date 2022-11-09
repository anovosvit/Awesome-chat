package com.anovosvit.anymaster.presentation.models

import androidx.annotation.DrawableRes
import com.anovosvit.anymaster.R

data class OfferInfo(
    val person: String = "Daniel",
    val price: String = "€1 498,00",
    val offerName: String = "Cleaning of a two-room apartment",
    @DrawableRes val image: Int = R.drawable.avatar
)

data class ChatViewState(
    val messages: List<RecyclerChatMessage> = listOf(),
    val isBlurred: Boolean = false,
    val isEditModeEnabled: Boolean = false,
    val needToScroll: Boolean = true,
)
