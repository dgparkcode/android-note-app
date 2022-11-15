package com.dgparkcode.note.ui.extension

import android.view.View
import androidx.core.widget.ContentLoadingProgressBar

fun ContentLoadingProgressBar.showOrHide(isVisible: Boolean) {
    if (isVisible) show() else hide()
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}