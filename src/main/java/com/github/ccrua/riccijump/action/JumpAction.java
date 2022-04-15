package com.github.ccrua.riccijump.action;

import com.github.ccrua.riccijump.notify.JumpNotifierCenter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class JumpAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        JumpNotifierCenter.notifyError(event.getProject(),"hhh");
    }
}
