package com.github.ccrua.riccijump.ui;

import com.github.ccrua.riccijump.comm.RicciUntil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

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

    public NewAlproUI(Project _project, AnActionEvent _event) {
        super(_project, false);
        _m_project = _project;
        _m_event = _event;
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return Main;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        DataContext dataContext = _m_event.getDataContext();
        VirtualFile virtualFile = dataContext.getData(VIRTUAL_FILE);
        String packageName = virtualFile.getName();
        //文件名
        String fileNameText = fileName.getText();
        //主id
        String mainIdText = mainId.getText();
        //子id
        String subIdText = subId.getText();

        String sb = new String(RicciUntil.ALProTemplate);
//        sb.replace("$PACKAGE$", )
    }
}
