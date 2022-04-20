package com.github.ccrua.riccijump.services.open;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jetbrains.annotations.NotNull;

import static com.github.ccrua.riccijump.comm.RicciUntil.AL_DB_BASE_BO_NAME;
import static com.github.ccrua.riccijump.comm.RicciUntil.AL_PROTOCOL_INTERFACE_NAME;

public class OpenTypeFunc_Java implements _AOpenTypeFunc {
    /**
     * 将文件转换成要跳转的文件名
     *
     * @param _psiClass 源class
     * @return String
     */
    public String getShortName(PsiClass _psiClass) {
        //遍历文件的超类
        for (PsiClass aSuper : _psiClass.getSupers()) {
            if (aSuper == null) {
                continue;
            }
            //继承自 _ALProtocolStructure
            String name = _psiClass.getName();
            if (name == null) {
                continue;
            }
            if (AL_PROTOCOL_INTERFACE_NAME.equals(aSuper.getName())) {
                return name + ".alpro";
            } else if (AL_DB_BASE_BO_NAME.equals(aSuper.getName())) {
                //删除BO命名
                String replaceBO = name.replace("Bo", "");
                //替换后缀
                String replace = replaceBO.replace(".java", ".py");
                //改为snake风格
                String fileName = RicciUntil.transStrToSnakeType(replace);

                return fileName.replace("bo", "");
            }
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
        PsiJavaFileImpl javaFile = (PsiJavaFileImpl) _psiFile;
        //遍历文件的子类
        for (PsiClass aClass : javaFile.getClasses()) {
            if (aClass == null) {
                continue;
            }
            //这个类是枚举的处理
            if (aClass.isEnum()) {
                //去协议所在文件夹里找
//                LocalFileSystem.getInstance().refreshAndFindFileByPath();
            } else {
                //不带后缀的文件名
                String fileShortName = getShortName(aClass);
                //查询跳转操作
                @NotNull Project project = _psiFile.getProject();
                PsiFile[] filesByName = PsiShortNamesCache.getInstance(project).getFilesByName(fileShortName);
                for (PsiFile file : filesByName) {
                    OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(project, file.getVirtualFile(), getTextOffSet(file));
                    FileEditorManager.getInstance(project).openTextEditor(openFileDescriptor, true);
                }
            }
        }
    }
}
