package com.better.nothing.music.vizualizer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionTrampolineActivity : ComponentActivity() {

    private val projectionManager by lazy {
        getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
    }

    private val projectionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val serviceIntent = Intent(this, AudioCaptureService::class.java).apply {
                    putExtra(AudioCaptureService.EXTRA_RESULT_CODE, result.resultCode)
                    putExtra(AudioCaptureService.EXTRA_DATA, result.data)
                    putExtra(AudioCaptureService.EXTRA_PRESET_KEY, getDefaultPreset())
                }
                ContextCompat.startForegroundService(this, serviceIntent)
            }
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // If essential recording permission is missing, redirect to main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(MainActivity.EXTRA_REQUEST_START, true)
            startActivity(intent)
            finish()
            return
        }

        projectionLauncher.launch(projectionManager.createScreenCaptureIntent())
    }
    
    private fun getDefaultPreset(): String {
        // First try the main app prefs
        val appPrefs = getSharedPreferences("viz_prefs", Context.MODE_PRIVATE)
        var preset = appPrefs.getString("selected_preset", "")
        
        if (preset.isNullOrBlank()) {
            // Fallback to service internal prefs if any
            val svcPrefs = getSharedPreferences("glyph_visualizer_prefs", Context.MODE_PRIVATE)
            preset = svcPrefs.getString("current_preset", "np1s")
        }
        
        return preset ?: "np1s"
    }
}
