package com.github.ccrua.riccijump.services.open;

import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.PsiPlainTextFileImpl;

import java.util.HashMap;
import java.util.Map;

public class OpenTypeMgr {
    ///单例化///
    private static volatile OpenTypeMgr _m_instance;

    private final Map<Class<? extends PsiFile>, _AOpenTypeFunc> _m_openFuncMap;

    private OpenTypeMgr() {
        _m_openFuncMap = new HashMap<>();

        regist(PsiJavaFileImpl.class, new OpenTypeFunc_Java());
        regist(PsiPlainTextFileImpl.class, new OpenTypeFunc_Text());
//        regist(TextMateFile.class, new OpenTypeFunc_Python());
    }

    /**
     * 获取单例对象
     *
     * @return OpenTypeMgr
     */
    public static OpenTypeMgr getInstance() {
        synchronized (OpenTypeMgr.class) {
            if (_m_instance == null) {
                synchronized (OpenTypeMgr.class) {
                    if (_m_instance == null) {
                        _m_instance = new OpenTypeMgr();
                    }
                }
            }
            return _m_instance;
        }
    }

    public _AOpenTypeFunc getOpenFunc(Class<? extends PsiFile> _psiClass) {
        return _m_openFuncMap.get(_psiClass);
    }

    public void regist(Class<? extends PsiFile> _psiClass, _AOpenTypeFunc _openFunc) {
        _m_openFuncMap.putIfAbsent(_psiClass, _openFunc);
    }
}
