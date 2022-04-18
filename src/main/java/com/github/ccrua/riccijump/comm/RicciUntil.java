package com.github.ccrua.riccijump.comm;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;

public class RicciUntil {
    public static final String AL_PROTOCOL_INTERFACE_NAME = "_IALProtocolStructure";

    public static final String AL_DB_BASE_BO_NAME = "IBaseBO";

    public static String transStrToSnakeType(String _selected) {
        _selected = _selected.replaceAll("[A-Z]", "_$0");
        _selected = _selected.toLowerCase();
        if (_selected.charAt(0) == '_') {
            _selected = _selected.substring(1);
        }
        return _selected;

    }

    /**
     * 文件是否 import 过这个类
     *
     * @param _psiFile        文件
     * @param _importFileName 指定类的类名
     * @return boolean
     */
    public static boolean fileIsImports(PsiJavaFile _psiFile, String _importFileName) {
        for (PsiClass singleClassImport : _psiFile.getSingleClassImports(true)) {
            if (singleClassImport == null) {
                continue;
            }
            //是否 import 当前文件
            if (_importFileName.equals(singleClassImport.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件是否 extend 过这个类
     *
     * @param _curJavaFile    文件
     * @param _extendFileName 指定类的类名
     * @return boolean
     */
    public static boolean fileIsExtend(PsiJavaFile _curJavaFile, String _extendFileName) {
        Project project = _curJavaFile.getProject();
        //找到指定超类
        PsiClass[] classes = _curJavaFile.getClasses();
        for (PsiClass aClass : classes) {
            if (aClass == null) {
                continue;
            }
            for (PsiClass aSuper : aClass.getSupers()) {
                if (aSuper == null) {
                    continue;
                }
                if (_extendFileName.equals(aSuper.getName())) {
                    return true;
                }
            }

        }
        return false;
    }

    //驼峰转蛇形
    public String transSnakeToStr(String _selected) {
        _selected = _selected.replaceAll("_([A-Z])", "$1");
        int i = -32;

        for (char c = 'a'; c <= 'z'; ++c) {
            _selected = _selected.replaceAll("_" + c, String.valueOf((char) (c + i)));
        }
        return _selected;
    }
}
