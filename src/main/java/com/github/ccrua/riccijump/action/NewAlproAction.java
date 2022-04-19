package com.github.ccrua.riccijump.action;

import com.github.ccrua.riccijump.ui.NewAlproUI;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class NewAlproAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        //获得当前project
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        //获得虚拟文件
        VirtualFile virtualFile = e.getData(VIRTUAL_FILE);
        if (virtualFile == null) {
            return;
        }
        //运行时的模块
        @Nullable Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        NewAlproUI alproUI = new NewAlproUI(project, e);
        alproUI.showAndGet();
    }
}
