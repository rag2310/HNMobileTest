package com.rago.hnmobiletest.ui.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rago.hnmobiletest.R

abstract class SwipeToDeleteCallback(private val context: Context) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT
) {
    private val background = ColorDrawable()
    private val backgroundColor = context.getColor(R.color.background_delete)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        val p = Paint()
        val text = context.getString(R.string.delete)

        val textSize = 32F
        p.color = Color.WHITE
        p.isAntiAlias = true
        p.textSize = textSize

        val textWidth = p.measureText(text)

        val intrinsicHeight = 45

        // Calculate position of delete text
        val textTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val textMargin = (itemHeight - intrinsicHeight) / 2
        val textRight = itemView.right - textMargin
        val textBottom = textTop + intrinsicHeight

        c.drawText(text, textRight - textWidth, textBottom.toFloat() - (textSize / 2), p)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}