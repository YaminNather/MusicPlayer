package com.example.musicplayer.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

public fun Context.getActivity(): Activity? {
    var currentContext = this

    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) return currentContext

        currentContext = currentContext.baseContext
    }

    return null
}
