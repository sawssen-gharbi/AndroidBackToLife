package com.example.backtolife.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

object HelperFunctions {

    fun launchURL(context: Context, url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
    fun toastMsg(context: Context, message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    fun usePicasso(
        url: String,
        placeholder: Int,
        view: ImageView,
    ) =
        Picasso.get()
            .load(url)
            .networkPolicy(NetworkPolicy.NO_STORE, NetworkPolicy.NO_CACHE)
            .placeholder(placeholder)
            .into(view)
}