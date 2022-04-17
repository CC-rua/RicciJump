package com.github.ccrua.riccijump.action;

import com.github.ccrua.riccijump.notify.JumpNotifierCenter;
import com.github.ccrua.riccijump.services.open.OpenTypeMgr;
import com.github.ccrua.riccijump.services.open._AOpenTypeFunc;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.PsiShortNamesCache;

public class ViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        //psi 文件
        PsiFile psiFile = event.getData(PlatformDataKeys.PSI_FILE);
        if (psiFile == null) {
            JumpNotifierCenter.notifyError(project, "psiFile not found");
            return;
        }
        _AOpenTypeFunc openFunc = OpenTypeMgr.getInstance().getOpenFunc(psiFile.getClass());
        //找不到处理方法
        if (openFunc == null) {
            System.out.printf("openFunc not found %s", psiFile.getClass());
            return;
        }
        //不带后缀的文件名
        String fileShortName = openFunc.getShortName(psiFile);
        //查询跳转操作
        PsiFile[] filesByName = PsiShortNamesCache.getInstance(project).getFilesByName(fileShortName);
        for (PsiFile file : filesByName) {
            OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(project, file.getVirtualFile(), openFunc.getTextOffSet(file));
            FileEditorManager.getInstance(project).openTextEditor(openFileDescriptor, true);
        }
    }
}
