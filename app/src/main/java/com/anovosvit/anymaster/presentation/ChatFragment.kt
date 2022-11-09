package com.anovosvit.anymaster.presentation

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anovosvit.anymaster.R
import com.anovosvit.anymaster.databinding.*
import com.anovosvit.anymaster.presentation.models.ChatViewState
import com.anovosvit.anymaster.presentation.models.MessageType
import com.anovosvit.anymaster.presentation.models.OfferInfo
import com.anovosvit.anymaster.presentation.models.RecyclerChatMessage
import com.anovosvit.anymaster.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding by viewBinding(FragmentChatBinding::bind)
    private val viewModel by viewModels<ChatViewModel>()

    // It is better to get info as nav args or from the local storage
    private val offerInfoMock by lazy { OfferInfo() }

    private var selectedItem: RecyclerChatMessage? = null
    private var selectedView: View? = null
    private var isEditContextItemClicked: Boolean? = null

    private val conversationAdapter by lazy {
        ConversationAdapter(
            items = listOf(),
            viewHolderCreator = { parent, viewType ->
                when (viewType) {
                    MessageType.FIRST.typeId -> parent.viewHolderFrom(ItemChatFirstMessageBinding::inflate)
                    MessageType.MY.typeId -> parent.viewHolderFrom(ItemChatMyMessageBinding::inflate)
                    MessageType.OTHER.typeId -> parent.viewHolderFrom(ItemChatOtherMessageBinding::inflate)
                    else -> parent.viewHolderFrom(ItemChatDateBinding::inflate)
                }
            },
            viewHolderBinder = { viewHolder, item, _ ->
                when (item.type) {
                    MessageType.FIRST -> (viewHolder.binding as? ItemChatFirstMessageBinding)?.apply {
                        tvOfferDetails.text = getString(R.string.gets_offer, offerInfoMock.person)
                        tvExplanation.text =
                            getString(R.string.deal_explanation, offerInfoMock.person)
                        tvOfferPrice.text = offerInfoMock.price
                    }
                    MessageType.TITLE -> {
                        (viewHolder.binding as? ItemChatDateBinding)?.root?.text = item.text
                    }
                    MessageType.MY -> (viewHolder.binding as? ItemChatMyMessageBinding)?.apply {
                        tvMessage.text = item.text
                        ivReadStatus.isVisible = item.isDelivered
                        tvTime.text = item.time
                        ivTongue.isVisible = item.isLastMessageInChain
                        root.setOnLongClickListener {
                            selectedItem = item
                            selectedView = root
                            false
                        }
                    }
                    MessageType.OTHER -> (viewHolder.binding as? ItemChatOtherMessageBinding)?.apply {
                        tvMessage.text = item.text
                        tvTime.text = item.time
                        ivTongue.isVisible = item.isLastMessageInChain
                        if (item.isDelivered.not()) viewModel.obtainEvent(
                            ChatViewModel.ChatEvent.MessageWasRead(item.id)
                        )
                    }
                }
            },
            getItemType = { item -> item.type.typeId }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.obtainEvent(ChatViewModel.ChatEvent.Init)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            // init offer info
            initOffer()

            // init recycler
            rvConversation.addElementsSpacing(R.dimen.spacing_8, R.dimen.spacing_4)
            rvConversation.adapter = conversationAdapter
            registerForContextMenu(rvConversation)

            // set listeners
            tilMessage.setEndIconOnClickListener { showToast(getString(R.string.open_camera)) }
            ivBack.setOnClickListener { showToast(getString(R.string.go_back)) }
            ivMore.setOnClickListener { showToast(getString(R.string.show_more_menu)) }
            btnChangeOffer.setOnClickListener { showToast(getString(R.string.change_offer)) }

            tietMessage.setOnFocusChangeListener { _, hasFocus -> ibSubmit.isVisible = hasFocus }
            tietMessage.addTextChangedListener { text ->
                ibSubmit.isEnabled = text?.trim().isNullOrEmpty().not()
            }

            ibSubmit.setOnClickListener {
                val text = tietMessage.text?.trim()?.toString().orEmpty()
                selectedItem?.let { item ->
                    viewModel.obtainEvent(
                        ChatViewModel.ChatEvent.EditMessage(message = item, newText = text)
                    )
                } ?: run { viewModel.obtainEvent(ChatViewModel.ChatEvent.SendMessage(text)) }
                resetInput()
            }

            ibCLose.setOnClickListener {
                resetInput()
                viewModel.obtainEvent(ChatViewModel.ChatEvent.EditMessageModeDisabled)
            }
        }
        viewModel.viewState.observe(viewLifecycleOwner) { updateState(it) }
    }

    private fun resetInput() {
        binding.tietMessage.setText("")
        selectedItem = null
    }

    private fun initOffer() = binding.apply {
        tvInterlocutorName.text = offerInfoMock.person
        tvOfferName.paint?.isUnderlineText = true
        tvOfferName.text = offerInfoMock.offerName
        tvOfferPrice.text = offerInfoMock.price
        ivInterlocutorPhoto.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), offerInfoMock.image)
        )
    }

    private fun updateState(state: ChatViewState) = binding.apply {
        isEditContextItemClicked = state.isEditModeEnabled
        gEditMessage.isVisible = state.isEditModeEnabled
        if (state.isEditModeEnabled) {
            tvMessageToEdit.text = selectedItem?.text.orEmpty()
            tietMessage.setText(selectedItem?.text.orEmpty())
            tietMessage.setSelection(tietMessage.length())
        }

        conversationAdapter.setItems(state.messages)
        if (state.needToScroll && state.isBlurred.not())
            rvConversation.scrollToPosition(state.messages.size - 1)
        if (state.isBlurred) selectedView?.let {
            binding.blur.setImageBitmap(
                BlurBuilder.blur(binding.root.context, getScreenshot(binding.root, it))
            )
        } else binding.blur.setImageResource(0)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.addNewItem(R.drawable.ic_save_template, getString(R.string.save_as_template))
            ?.apply { setOnChatMenuClickListener() }
        menu.addNewItem(R.drawable.ic_copy, getString(R.string.copy))
            ?.apply { setOnChatMenuClickListener() }
        menu.addNewItem(R.drawable.ic_edit, getString(R.string.edit))
            ?.apply { setOnChatMenuClickListener(isEditItem = true) }
        menu.addNewItem(R.drawable.ic_delete, getString(R.string.delete), true)
            ?.apply { setOnChatMenuClickListener() }
        viewModel.obtainEvent(ChatViewModel.ChatEvent.ContextMenuOpened)
    }

    private fun MenuItem.setOnChatMenuClickListener(isEditItem: Boolean = false) {
        setOnMenuItemClickListener {
            isEditContextItemClicked = isEditItem
            if (isEditItem.not()) showToast(title.toString())
            true
        }
    }

    fun onContextMenuClosed() {
        if (isEditContextItemClicked == true)
            viewModel.obtainEvent(ChatViewModel.ChatEvent.EditMessageModeEnabled)
        else {
            viewModel.obtainEvent(ChatViewModel.ChatEvent.ContextMenuClosed)
            isEditContextItemClicked = false
            selectedItem = null
        }
    }

    private fun ContextMenu.addNewItem(
        @DrawableRes drawable: Int,
        title: String,
        needMakeRed: Boolean = false
    ) = ContextCompat.getDrawable(requireContext(), drawable)?.let {
        this.add(0, 1, 1, getMenuTextWithIcon(it, title, needMakeRed))
    }

    private fun showToast(text: String) =
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        selectedView = null
        selectedItem = null
        super.onDestroyView()
    }

}
