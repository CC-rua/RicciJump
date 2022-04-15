package com.github.ccrua.riccijump.listeners;

import com.github.ccrua.riccijump.services.JumpProjectService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

public class JumpProjectManagerListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        project.getService(JumpProjectService.class);
    }
}
