package com.github.ccrua.riccijump.services.open;

import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiPlainTextFileImpl;

public class OpenTypeFunc_Text implements _AOpenTypeFunc {
    private static final String AL_PROTO_SUFFIX = ".alpro";

    @Override
    public String getShortName(PsiFile _psiFile) {
        PsiPlainTextFileImpl javaFile = (PsiPlainTextFileImpl) _psiFile;
        String name = javaFile.getName();
        //以 .alpro 结尾的text文件
        if (name.endsWith(AL_PROTO_SUFFIX)) {
            return name.replace(".alpro", ".java");
        }
        return "";
    }

    @Override
    public int getTextOffSet(PsiFile _psiFile) {
        return _psiFile.getTextOffset();
    }
}
