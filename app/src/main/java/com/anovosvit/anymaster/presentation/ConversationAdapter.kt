package com.anovosvit.anymaster.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.anovosvit.anymaster.presentation.models.RecyclerChatMessage
import com.anovosvit.anymaster.utils.DiffUtilCallback

class ConversationAdapter<T : RecyclerView.ViewHolder>(
    private var items: List<RecyclerChatMessage>,
    private val viewHolderCreator: (ViewGroup, Int) -> T,
    private val viewHolderBinder: (holder: T, item: RecyclerChatMessage, position: Int) -> Unit,
    private val getItemType: (item: RecyclerChatMessage) -> Int,
    private val diffUtilCallback: DiffUtilCallback = DiffUtilCallback(),
) : RecyclerView.Adapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T =
        viewHolderCreator(parent, viewType)

    override fun onBindViewHolder(holder: T, position: Int) =
        viewHolderBinder(holder, items[position], position)

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = getItemType(items[position])

    fun setItems(newItems: List<RecyclerChatMessage>) = with(arrayListOf<RecyclerChatMessage>()
        .apply { addAll(newItems) }) {
        dispatchUpdates(this)
        synchronized(items) { items = this }
        true
    }

    private fun dispatchUpdates(newItems: List<RecyclerChatMessage>) =
        DiffUtil.calculateDiff(diffUtilCallback.update(newItems)).dispatchUpdatesTo(this)

}

class ConversationViewHolder<V : ViewBinding>(val binding: V) :
    RecyclerView.ViewHolder(binding.root) {
    constructor(
        parent: ViewGroup,
        creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> V
    ) : this(creator(LayoutInflater.from(parent.context), parent, false))
}

