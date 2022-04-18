package com.github.ccrua.riccijump.action;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.github.ccrua.riccijump.notify.JumpNotifierCenter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.util.Query;

import java.util.ArrayList;
import java.util.List;

import static com.github.ccrua.riccijump.comm.RicciUntil.AL_PROTOCOL_INTERFACE_NAME;

public class JumpDealerAction extends AnAction {
    private static final String PROTOCOL_USER_DEALER = "NPUserMsgDealer";
    private static final String PROTOCOL_REQ_DEALER = "NPRequestDealer";



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
        PsiFile curFile = event.getData(PlatformDataKeys.PSI_FILE);
        if (curFile == null) {
            JumpNotifierCenter.notifyError(project, "psiFile not found");
            return;
        }
        //是否是java类
        if (!(curFile instanceof PsiJavaFileImpl)) {
            return;
        }
        PsiJavaFile curJavaFile = (PsiJavaFile) curFile;
        //这个文件是否继承自  AL_PROTOCOL_INTERFACE_NAME
        if (!RicciUntil.fileIsExtend(curJavaFile, AL_PROTOCOL_INTERFACE_NAME)) {
            return;
        }

        String curJavaFileName = curJavaFile.getName();
        curJavaFileName = curJavaFileName.replace(".java", "");

        ArrayList<PsiClass> dealersPsiClasses = new ArrayList<>();
        //查找超类 NPUserMsgDealer
        dealersPsiClasses.addAll(List.of(PsiShortNamesCache.getInstance(project)
                .getClassesByName(PROTOCOL_USER_DEALER, GlobalSearchScope.projectScope(project))));
        //查找超类 NPRequestDealer
        dealersPsiClasses.addAll(List.of(PsiShortNamesCache.getInstance(project)
                .getClassesByName(PROTOCOL_REQ_DEALER, GlobalSearchScope.projectScope(project))));

        //遍历这些类
        for (PsiClass dealersPsiClass : dealersPsiClasses) {
            //获取所有子类
            Query<PsiClass> inheritorsClass = ClassInheritorsSearch.search(dealersPsiClass);
            for (PsiClass aClass : inheritorsClass) {
                if (aClass == null) {
                    continue;
                }
                PsiJavaFile psiFile = (PsiJavaFile) aClass.getContainingFile();
                //检查是否导入过 curJavaFile
                if (RicciUntil.fileIsImports(psiFile, curJavaFileName)) {
                    //打开到指定目录
                    OpenFileDescriptor openFileDescriptor =
                            new OpenFileDescriptor(project, psiFile.getVirtualFile(), psiFile.getTextOffset());
                    FileEditorManager.getInstance(project).openTextEditor(openFileDescriptor, true);
                }
            }
        }
    }
}