package com.github.ccrua.riccijump.notify;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.sun.istack.Nullable;

public class JumpNotifierCenter {
    public static void notifyError(@Nullable Project project,
                                   String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("Custom Notification Group")
                .createNotification(content, NotificationType.ERROR)
                .notify(project);
    }
}