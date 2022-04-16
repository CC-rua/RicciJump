package com.github.ccrua.riccijump.action;

import com.github.ccrua.riccijump.notify.JumpNotifierCenter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;

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
        //检查是否是java类型
        if (!psiFile.getName().endsWith(".java")) {
            JumpNotifierCenter.notifyError(project, "not *.java");
            return;
        }
        //psi
        @NotNull PsiFile[] filesByName = FilenameIndex.getFilesByName(project, "Main.java", GlobalSearchScope.allScope(project));

    }
}
