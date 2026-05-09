package com.better.nothing.music.vizualizer;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class HapticsTileService extends TileService {
    @Override public void onStartListening() { super.onStartListening(); refresh(); }
    @Override public void onClick() {
        super.onClick();
        boolean current = AudioCaptureService.isHapticEnabledGlobal(this);
        
        // If service is running, send intent to toggle immediately
        if (AudioCaptureService.isRunning()) {
            Intent intent = new Intent(this, AudioCaptureService.class);
            intent.setAction(AudioCaptureService.ACTION_TOGGLE_HAPTICS);
            startService(intent);
        } else {
            // Just update prefs
            boolean next = !current;
            getSharedPreferences("viz_prefs", MODE_PRIVATE)
                    .edit().putBoolean("haptic_motor_enabled", next).apply();
            refresh(next);
        }
    }
    
    private void refresh() { refresh(AudioCaptureService.isHapticEnabledGlobal(this)); }
    
    private void refresh(boolean on) {
        Tile t = getQsTile(); if (t == null) return;
        t.setState(on ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        t.setLabel("Glyph Haptics");
        t.setSubtitle(on ? "On" : "Off");
        t.setIcon(Icon.createWithResource(this, R.drawable.app_icon));
        t.updateTile();
    }
}
