package com.anovosvit.anymaster.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.renderscript.Allocation
import androidx.renderscript.Element
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur
import kotlin.math.roundToInt

object BlurBuilder {

    private const val BITMAP_SCALE = 0.6f
    private const val BLUR_RADIUS = 10f

    fun blur(context: Context, image: Bitmap): Bitmap {
        val inputBitmap = Bitmap.createScaledBitmap(
            image,
            (image.width * BITMAP_SCALE).roundToInt(),
            (image.height * BITMAP_SCALE).roundToInt(),
            false
        )
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        RenderScript.create(context).apply {
            val intrinsicBlur = ScriptIntrinsicBlur.create(this, Element.U8_4(this))
                .also { it.setRadius(BLUR_RADIUS) }
            Allocation.createFromBitmap(this, inputBitmap).also { intrinsicBlur.setInput(it) }
            Allocation.createFromBitmap(this, outputBitmap).also {
                intrinsicBlur.forEach(it)
                it.copyTo(outputBitmap)
            }
        }

        return outputBitmap
    }

}

/**
 * Makes a screenshot of the view with the exception of a specific view
 * @param view the view that will be in the screenshot
 * @param exceptionView the view that won't be in the screenshot
 * @return the bitmap of the screenshot
 * */
fun getScreenshot(view: View, exceptionView: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    Canvas(bitmap).also {
        exceptionView.isInvisible = true
        view.draw(it)
        exceptionView.isVisible = true
    }
    return bitmap
}
