package com.github.ccrua.riccijump.services.open;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;

public class OpenTypeFunc_Java implements _AOpenTypeFunc {
    private static final String AL_PROTOCOL_INTERFACE_NAME = "_ALProtocolStructure";
    private static final String AL_DB_BASE_BO_NAME = "_ABaseBO";

    @Override
    public String getShortName(PsiFile _psiFile) {
        PsiJavaFileImpl javaFile = (PsiJavaFileImpl) _psiFile;
        //遍历文件的子类
        for (PsiClass aClass : javaFile.getClasses()) {
            if (aClass == null) {
                continue;
            }
            //遍历文件的超类
            for (PsiClass aSuper : aClass.getSupers()) {
                if (aSuper == null) {
                    continue;
                }
                //继承自 _ALProtocolStructure
                String name = javaFile.getName();
                if (AL_PROTOCOL_INTERFACE_NAME.equals(aSuper.getName())) {
                    return name.replace(".java", ".alpro");
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
        }
        return "";
    }

    @Override
    public int getTextOffSet(PsiFile _psiFile) {
        return _psiFile.getTextOffset();
    }
}
