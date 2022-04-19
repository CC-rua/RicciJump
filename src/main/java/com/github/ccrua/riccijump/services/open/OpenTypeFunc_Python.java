package com.github.ccrua.riccijump.services.open;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiPlainTextFileImpl;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jetbrains.annotations.NotNull;

import static com.github.ccrua.riccijump.comm.RicciUntil.AL_PROTO_SUFFIX;

public class OpenTypeFunc_Python implements _AOpenTypeFunc {

    /**
     * 将文件转换成要跳转的文件名
     *
     * @param _psiFile 源文件
     * @return String
     */
    public String getShortName(PsiFile _psiFile) {
        PsiPlainTextFileImpl javaFile = (PsiPlainTextFileImpl) _psiFile;
        String name = javaFile.getName();
        //以 .py 结尾的text文件
        if (name.endsWith(AL_PROTO_SUFFIX)) {
            String replacePy = name.replace(".py", ".java");
            replacePy = RicciUntil.transSnakeToStr(replacePy);
            replacePy += "BO";
            return replacePy;
        }
        return "";
    }


    /**
     * 跳转位置
     *
     * @param _psiFile 所在文件
     * @return int
     */
    public int getTextOffSet(PsiFile _psiFile) {
        return _psiFile.getTextOffset();
    }

    @Override
    public void jump(PsiFile _psiFile) {
        //不带后缀的文件名
        String fileShortName = getShortName(_psiFile);
        //查询跳转操作
        @NotNull Project project = _psiFile.getProject();
        PsiFile[] filesByName = PsiShortNamesCache.getInstance(project).getFilesByName(fileShortName);
        for (PsiFile file : filesByName) {
            OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(project, file.getVirtualFile(), getTextOffSet(file));
            FileEditorManager.getInstance(project).openTextEditor(openFileDescriptor, true);
        }
    }
}
