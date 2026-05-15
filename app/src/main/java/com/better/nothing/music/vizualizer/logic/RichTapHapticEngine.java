package com.better.nothing.music.vizualizer.logic;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.apprichtap.haptic.RichTapUtils;

import java.util.Objects;

/**
 * RichTap implementation of the haptic engine for high-fidelity vibration.
 * Uses RichTap SDK to drive the haptic motor with real-time amplitude updates.
 */
public final class RichTapHapticEngine {

    private static final String TAG = "RichTapHapticEngine";

    // Matching the cadence and tuning of ContinuousHapticEngine
    private static final int HAPTIC_STEP_MS = 100;
    private static final long MIN_RESUBMIT_INTERVAL_MS = 20L;
    private static final int AMPLITUDE_THRESHOLD = 2;

    private static final float DEFAULT_DECAY = 0.85f;
    private static final float EPSILON = 0.0001f;
    private static final float PEAK_FALLOFF = 0.99f;
    private static final float SPECTRUM_GAIN = 4.0f;
    private static final int MAX_AMPLITUDE = 255;

    private float hapticMultiplier = 1.0f;
    private int hapticFrequency = 50;

    private float decayedState = 0f;
    private float peakTracker = EPSILON;

    private int lastAmplitude = -1;
    private int lastFrequency = -1;
    private long lastSubmitMs = 0L;
    private boolean isPlaying = false;

    // A simple continuous haptic pattern in RichTap's HE JSON format
    private static final String CONTINUOUS_HE_JSON = "{\"Metadata\":{\"Version\":1},\"Events\":[{\"Type\":\"Continuous\",\"RelativeTime\":0,\"Duration\":5000,\"Parameters\":{\"Intensity\":100,\"Frequency\":50}}]}";

    public RichTapHapticEngine(Context context) {
        Context appContext = context.getApplicationContext();
        RichTapUtils.getInstance().init(appContext);
    }

    public synchronized void setHapticMultiplier(float multiplier) {
        this.hapticMultiplier = Math.max(0f, multiplier);
    }

    public synchronized void setHapticFrequency(int frequency) {
        this.hapticFrequency = clampInt(frequency, 0, 100);
    }

    public synchronized void performHapticFeedback(float rawPeak, @Nullable AudioProcessor.VisualizerConfig config) {
        if (!RichTapUtils.getInstance().isSupportedRichTap()) {
            return;
        }

        final float decay = (config != null) ? config.decay : DEFAULT_DECAY;

        // 1. Same base gain as LEDs
        float current = Math.max(0f, rawPeak) * SPECTRUM_GAIN;

        // 2. Instant-attack peak follower
        if (current > decayedState) {
            decayedState = current;
        } else {
            decayedState = (decay * decayedState) + ((1f - decay) * current);
        }

        if (decayedState < EPSILON) {
            decayedState = 0f;
            stopHapticsInternal();
            return;
        }

        // 3. Peak tracking for auto-normalization
        peakTracker = Math.max(decayedState, peakTracker * PEAK_FALLOFF);
        if (peakTracker < EPSILON) peakTracker = EPSILON;

        // 4. Normalize to recent peak
        float normalized = decayedState / peakTracker;

        // 5. Apply User Multiplier (Gamma removed per request)
        float shaped = normalized * hapticMultiplier;
        
        int amplitude = Math.round(Math.min(1.0f, shaped) * MAX_AMPLITUDE);
        amplitude = clampInt(amplitude, 0, MAX_AMPLITUDE);

        if (amplitude <= 0) {
            stopHapticsInternal();
            return;
        }

        final long now = SystemClock.elapsedRealtime();
        
        // Skip if it's strictly the same to save overhead
        if (isPlaying && amplitude == lastAmplitude && hapticFrequency == lastFrequency) {
            return;
        }
        
        // Only resubmit if change is significant OR enough time has passed
        boolean significantChange = Math.abs(amplitude - lastAmplitude) >= AMPLITUDE_THRESHOLD 
                || hapticFrequency != lastFrequency;
        boolean cooldownOver = (now - lastSubmitMs) >= MIN_RESUBMIT_INTERVAL_MS;

        if (isPlaying && !significantChange && (now - lastSubmitMs) < 100) {
            return;
        }
        
        if (isPlaying && !cooldownOver) {
            return;
        }

        submitRichTapHaptic(amplitude, hapticFrequency);
    }

    private void submitRichTapHaptic(int amplitude, int frequency) {
        try {
            if (!isPlaying) {
                // Start a looping haptic effect
                // playHaptic(json, loop, interval, amplitude, frequency)
                RichTapUtils.getInstance().playHaptic(CONTINUOUS_HE_JSON, 1000, 0, amplitude, frequency);
                isPlaying = true;
            } else {
                // Update parameters of the currently playing loop
                RichTapUtils.getInstance().sendLoopParameter(amplitude, 0, frequency);
            }
            
            lastAmplitude = amplitude;
            lastFrequency = frequency;
            lastSubmitMs = SystemClock.elapsedRealtime();
        } catch (Exception e) {
            Log.w(TAG, "Failed to submit RichTap haptic", e);
            stopHapticsInternal();
        }
    }

    public synchronized void stopHaptics() {
        stopHapticsInternal();
        decayedState = 0f;
        peakTracker = EPSILON;
    }

    private void stopHapticsInternal() {
        if (!isPlaying) {
            lastAmplitude = -1;
            lastSubmitMs = 0L;
            return;
        }

        try {
            RichTapUtils.getInstance().stop();
        } catch (Exception e) {
            Log.w(TAG, "Failed to stop RichTap haptics", e);
        }

        isPlaying = false;
        lastAmplitude = -1;
        lastSubmitMs = 0L;
    }

    public void quit() {
        RichTapUtils.getInstance().quit();
    }

    private static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
