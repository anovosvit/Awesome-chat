package com.anovosvit.anymaster.utils

import androidx.recyclerview.widget.DiffUtil
import com.anovosvit.anymaster.presentation.models.RecyclerChatMessage

class DiffUtilCallback : DiffUtil.Callback() {

    private val oldItems: MutableList<RecyclerChatMessage> = ArrayList()
    private val newItems: MutableList<RecyclerChatMessage> = ArrayList()

    fun update(newList: List<RecyclerChatMessage>) = apply {
        oldItems.clear()
        oldItems.addAll(newItems)
        newItems.clear()
        newItems.addAll(newList)
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

}
