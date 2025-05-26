package com.example.vuzixtaskerplugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig;
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EditTextActivity extends Activity implements TaskerPluginConfig<String> {

    private EditText editText;
    private EditTextActivityHelper taskerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text); // Ensure this layout exists

        editText = findViewById(R.id.editText);
        Button saveButton = findViewById(R.id.saveButton);

        taskerHelper = getTaskerHelper();
        taskerHelper.onCreate();

        saveButton.setOnClickListener(v -> taskerHelper.finishForTasker());
    }

    @NotNull
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @NotNull
    @Override
    public Class<String> getInputClass() {
        return String.class;
    }

    @Override
    public void assignFromInput(@NotNull TaskerInput<String> input) {
        if (input.getRegular() != null) {
            editText.setText(input.getRegular());
        }
    }

    @NotNull
    @Override
    public TaskerInput<String> getInputForTasker() {
        return new TaskerInput<>(editText.getText().toString());
    }

    @Nullable
    @Override
    public String getBlurbText() {
        return "Send text: " + editText.getText().toString();
    }

    private EditTextActivityHelper getTaskerHelper() {
        if (taskerHelper == null) {
            taskerHelper = new EditTextActivityHelper(this);
        }
        return taskerHelper;
    }

    // Optional: Lifecycle methods to inform the helper
    @Override
    protected void onResume() {
        super.onResume();
        if (taskerHelper != null) {
            taskerHelper.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (taskerHelper != null) {
            taskerHelper.onDestroy();
        }
    }
}
