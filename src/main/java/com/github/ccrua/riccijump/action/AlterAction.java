package com.github.ccrua.riccijump.action;

import com.github.ccrua.riccijump.notify.JumpNotifierCenter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class AlterAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        JumpNotifierCenter.notifyError(e.getProject(),"hhh");
    }
}
