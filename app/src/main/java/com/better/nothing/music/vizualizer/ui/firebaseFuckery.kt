package com.better.nothing.music.vizualizer.ui

import com.google.firebase.database.FirebaseDatabase
import android.media.MediaMetadata
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.CompositionLocalProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import androidx.compose.runtime.remember
import android.os.Build

object FirebaseFuckery {
    fun init() {
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        } catch (e: Exception) {}
    }
}
