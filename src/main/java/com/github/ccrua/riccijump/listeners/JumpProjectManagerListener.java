package com.github.ccrua.riccijump.listeners;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.github.ccrua.riccijump.services.JumpProjectService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class JumpProjectManagerListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        project.getService(JumpProjectService.class);
        VirtualFile fileByPath = null;
        //是NP
        VirtualFile npFilePath = LocalFileSystem.getInstance().findFileByPath(project.getBasePath() +
                RicciUntil.NP_PROTOCOL_SCRIPTS_DICTIONARY);
        if (npFilePath != null) {
            fileByPath = npFilePath;
        }
        //是MG
        VirtualFile mgFilePath = LocalFileSystem.getInstance().findFileByPath(project.getBasePath() +
                RicciUntil.MG_PROTOCOL_SCRIPTS_DICTIONARY);
        if (mgFilePath != null) {
            fileByPath = mgFilePath;
        }
        //是CHAT
        VirtualFile chatFilePath = LocalFileSystem.getInstance().findFileByPath(project.getBasePath() +
                RicciUntil.CHAT_PROTOCOL_SCRIPTS_DICTIONARY);
        if (chatFilePath != null) {
            fileByPath = chatFilePath;
        }
        if (fileByPath != null) {
            RicciUntil.PROTOCOL_SCRIPTS_DICTIONARY = fileByPath.getPath();
        }

    }
}
