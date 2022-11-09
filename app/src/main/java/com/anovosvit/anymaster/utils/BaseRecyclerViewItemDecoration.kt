package com.anovosvit.anymaster.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerViewItemDecoration(
    private val firstLastOffset: Int,
    private val elementsOffset: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val mode = (parent.layoutManager as LinearLayoutManager).orientation
        parent.adapter?.let { adapter ->
            when (mode) {
                RecyclerView.HORIZONTAL -> {
                    outRect.right =
                        if (parent.getChildAdapterPosition(view) == adapter.itemCount - 1) firstLastOffset else elementsOffset
                    outRect.left =
                        if (parent.getChildAdapterPosition(view) == 0) firstLastOffset else 0
                }
                RecyclerView.VERTICAL -> {
                    outRect.bottom =
                        if (parent.getChildAdapterPosition(view) == adapter.itemCount - 1) firstLastOffset else elementsOffset
                    outRect.top =
                        if (parent.getChildAdapterPosition(view) == 0) firstLastOffset else 0
                }
            }
        }
    }

}