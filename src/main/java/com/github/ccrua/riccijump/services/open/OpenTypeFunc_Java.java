package com.github.ccrua.riccijump.services.open;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String name = _psiFile.getName();
            name = name.replace(".java", "");


            //这个类是枚举的处理
            if (aClass.isEnum()) {
                String regex = "ALEnum *\\b" + name + "\\b";
                Pattern pattern = Pattern.compile(regex);
                processByStrFind(javaFile,pattern);
            } else {
                String regex = "ALProtocol *\\b" + name + "\\b";
                Pattern pattern = Pattern.compile(regex);
                processByStrFind(javaFile,pattern);
            }
        }


    }

    public void processByStrFind(PsiFile _psiFile, Pattern _pattern) {

        //去协议所在文件夹
        VirtualFile protocolDic = LocalFileSystem.getInstance().findFileByPath(RicciUntil.PROTOCOL_SCRIPTS_DICTIONARY);
        if (protocolDic == null) {
            return;
        }

        VfsUtilCore.iterateChildrenRecursively(protocolDic, null, fileOrDir -> {
            if (fileOrDir.isDirectory()) {
                return true;
            }
            try {
                InputStream inputStream = fileOrDir.getInputStream();
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }

                Matcher matcher = _pattern.matcher(result.toString(StandardCharsets.UTF_8).replace("\r\n", " ")
                        .replace("\t", " "));

                if (matcher.find()) {
                    VirtualFile canonicalFile = fileOrDir.getCanonicalFile();
                    if (canonicalFile == null) {
                        return true;
                    }
                    OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(_psiFile.getProject(), canonicalFile, matcher.start());
                    FileEditorManager.getInstance(_psiFile.getProject()).openTextEditor(openFileDescriptor, true);
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        });
    }
}
