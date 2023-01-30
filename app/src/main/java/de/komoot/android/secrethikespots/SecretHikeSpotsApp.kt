package de.komoot.android.secrethikespots

import android.app.Application
import android.content.Context
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.coroutines.MainScope

fun Context.appScope() = (applicationContext as SecretHikeSpotsApp).applicationScope

class SecretHikeSpotsApp : Application() {

    val applicationScope = MainScope() // stays open

    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(this)
    }
}