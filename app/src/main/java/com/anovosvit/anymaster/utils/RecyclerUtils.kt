package com.anovosvit.anymaster.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anovosvit.anymaster.presentation.ConversationViewHolder


fun <T : ViewBinding> ViewGroup.viewHolderFrom(
    creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T
): ConversationViewHolder<T> = ConversationViewHolder(this, creator)

fun RecyclerView.addElementsSpacing(@DimenRes firstLastOffset: Int, @DimenRes elementsOffset: Int) =
    apply {
        addItemDecoration(
            BaseRecyclerViewItemDecoration(
                resources.getDimension(firstLastOffset).toInt(),
                resources.getDimension(elementsOffset).toInt()
            )
        )
    }
