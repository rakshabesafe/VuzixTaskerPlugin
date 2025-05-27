package com.example.vuzixtaskerplugin;

import android.content.Context;
import android.util.Log;

import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction;
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput;
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult;
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultError;
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess;

// Vuzix SDK imports
import com.vuzix.ultralite.sdk.UltraliteSDK;
import com.vuzix.ultralite.sdk.Device;
import com.vuzix.ultralite.sdk.DisplayError;
import com.vuzix.ultralite.sdk.TextDisplay;

import org.jetbrains.annotations.NotNull;

public class VuzixTextSenderRunner extends TaskerPluginRunnerAction<String, String> {
    private static final String TAG = "VuzixTaskerRunner";

    @NotNull
    @Override
    public TaskerPluginResult<String> run(@NotNull Context context, @NotNull TaskerInput<String> input) {
        String textToSend = input.getRegular();
        Log.d(TAG, "Attempting to send text to Vuzix: " + textToSend);

        if (textToSend == null || textToSend.isEmpty()) {
            Log.w(TAG, "Text to send is empty.");
            return new TaskerPluginResultError(new Exception("Text to send cannot be empty."));
        }

        try {
            UltraliteSDK ultralite = UltraliteSDK.getInstance(context);
            if (ultralite == null) {
                Log.e(TAG, "UltraliteSDK instance is null.");
                return new TaskerPluginResultError(new Exception("Vuzix SDK not initialized."));
            }

            Device device = ultralite.getDevice();
            if (device == null || !device.isAvailable()) {
                Log.e(TAG, "Vuzix device not available or not connected.");
                return new TaskerPluginResultError(new Exception("Vuzix device not available or not connected."));
            }

            TextDisplay textDisplay = new TextDisplay(context);
            textDisplay.setText(textToSend);
            // Optional: textDisplay.setDisplayTimeout(10); // seconds

            DisplayError error = device.displayText(textDisplay);

            if (error == null || error == DisplayError.DISPLAY_ERROR_NONE) {
                Log.i(TAG, "Text sent successfully to Vuzix: " + textToSend);
                return new TaskerPluginResultSucess<>("Text sent: " + textToSend);
            } else {
                Log.e(TAG, "Failed to send text to Vuzix: " + error.toString());
                return new TaskerPluginResultError(new Exception("Failed to send text to Vuzix: " + error.toString()));
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception while sending text to Vuzix", e);
            return new TaskerPluginResultError(new Exception("Error sending text to Vuzix: " + e.getMessage()));
        }
    }
}
