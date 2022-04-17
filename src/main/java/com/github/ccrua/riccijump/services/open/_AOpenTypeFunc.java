package com.github.ccrua.riccijump.services.open;

import com.intellij.psi.PsiFile;

public interface _AOpenTypeFunc {

    /**
     * 将文件转换成要跳转的文件名
     *
     * @param _psiFile 源文件
     */
    String getShortName(PsiFile _psiFile);

    /**
     * 跳转位置
     *
     * @param _psiFile 所在文件
     * @return int
     */
    int getTextOffSet(PsiFile _psiFile);
}
