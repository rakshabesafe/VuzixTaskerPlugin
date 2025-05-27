package com.example.vuzixtaskerplugin;

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig;
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper;
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput;

import org.jetbrains.annotations.NotNull;

public class EditTextActivityHelper extends TaskerPluginConfigHelper<String, String, VuzixTextSenderRunner> {

    public EditTextActivityHelper(@NotNull TaskerPluginConfig<String> config) {
        super(config);
    }

    @NotNull
    @Override
    public Class<VuzixTextSenderRunner> getRunnerClass() {
        return VuzixTextSenderRunner.class;
    }

    @NotNull
    @Override
    public Class<String> getOutputClass() {
        return String.class; // Or Unit.class if no output variables are needed
    }

    @Override
    public void addToStringBlurb(@NotNull TaskerInput<String> input, @NotNull StringBuilder blurbBuilder) {
        if (input.getRegular() != null && !input.getRegular().isEmpty()) {
            blurbBuilder.append("Text: ").append(input.getRegular());
        } else {
            blurbBuilder.append("No text configured");
        }
    }
}
