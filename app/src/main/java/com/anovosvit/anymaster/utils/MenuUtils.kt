package com.anovosvit.anymaster.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan

fun getMenuTextWithIcon(
    drawable: Drawable,
    title: String,
    needMakeRed: Boolean
): CharSequence {
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val sb = SpannableString("   $title")
    val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM)
    if (needMakeRed) sb.setSpan(ForegroundColorSpan(Color.RED), 0, sb.length, 0)
    sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return sb
}
