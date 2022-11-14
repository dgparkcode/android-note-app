package com.dgparkcode.note.ui.extension

import androidx.core.widget.ContentLoadingProgressBar

fun ContentLoadingProgressBar.showOrHide(isVisible: Boolean) {
    if (isVisible) show() else hide()
}