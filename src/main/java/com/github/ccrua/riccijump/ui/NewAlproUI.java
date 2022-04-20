package com.github.ccrua.riccijump.ui;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.github.ccrua.riccijump.comm.RicciUntil.AL_PROTO_SUFFIX;
import static com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE;
import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

/**
 * @description: 创建新的alpro文件UI
 * @author: ricci
 * @date: 2022-04-19 15:24:01
 */
public class NewAlproUI extends DialogWrapper {
    //所属项目
    private final Project _m_project;
    //触发的事件
    private final AnActionEvent _m_event;
    private JPanel Main;
    private JTextField fileName;
    private JTextField mainId;
    private JTextField subId;
    private JLabel name;
    private JTextField describe;
    private JCheckBox genCSCheckBox;
    private JTextField packageName;
    private JLabel packageNameLabel;

    //文件位置
    private String _m_path;


    public NewAlproUI(Project _project, AnActionEvent _event) {
        super(_project, false);
        _m_project = _project;
        _m_event = _event;
        init();
        DataContext dataContext = _m_event.getDataContext();
        PsiFile psiFile = dataContext.getData(PSI_FILE);
        if (psiFile != null) {
            fileName.setText(psiFile.getName().replace(AL_PROTO_SUFFIX, ""));
        }
        VirtualFile virtualFile = dataContext.getData(VIRTUAL_FILE);
        if (virtualFile == null) {
            return;
        }
        //不是一个目录
        if (!virtualFile.isDirectory()) {
            virtualFile = virtualFile.getParent();
        }
        if (virtualFile == null) {
            return;
        }
        _m_path = virtualFile.getPath();
        //将 path 解析成 packagename
        String[] splitName = _m_path.split("ProtocolScripts/");
        if (splitName.length < 2) {
            return;
        }
        packageName.setText(splitName[1].replace('/', '.'));
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return Main;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        //文件名
        String fileNameText = fileName.getText();
        //主id
        String mainIdText = mainId.getText();
        //子id
        String subIdText = subId.getText();

        String describeText = this.describe.getText();

        String content = RicciUntil.ALProTemplate;

        String packagePath = packageNameLabel.getText();
        content = content.replace("$PACKAGE$", packagePath + ";");
        if (genCSCheckBox.isSelected()) {
            content = content.replace("$CS_PACKAGE$", "csharpspace " + packagePath + ";");
        } else {
            content = content.replace("$CS_PACKAGE$", "");
        }
        content = content.replace("$FILE_NAME$", fileNameText);
        content = content.replace("$MAIN_ID$", mainIdText);
        content = content.replace("$SUB_ID$", subIdText);
        content = content.replace("$DESCRIBE$", describeText);

        processCodeToDisk(_m_path, fileNameText, content);
        //打开这个文件
        VirtualFile createFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(_m_path + "/" + fileNameText + AL_PROTO_SUFFIX);
        if (createFile == null) {
            return;
        }
        OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(_m_project, createFile, 0);
        FileEditorManager.getInstance(_m_project).openTextEditor(openFileDescriptor, true);
    }

    /**
     * 生成代码到磁盘
     *
     * @param _projectPath 位置
     * @param _name        文件名
     * @param _content     内容
     */
    public void processCodeToDisk(String _projectPath, String _name, String _content) {

        String filePath = _projectPath + "/" + _name + AL_PROTO_SUFFIX;
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        try {
            if (!parentFile.exists() && !parentFile.mkdirs()) {
                throw new IOException("目录创建失败: " + parentFile.getPath());
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(_content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
