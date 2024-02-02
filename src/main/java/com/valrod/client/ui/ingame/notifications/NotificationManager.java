package com.valrod.client.ui.ingame.notifications;

import java.util.concurrent.LinkedBlockingQueue;

import net.minecraft.client.gui.GuiGraphics;

public class NotificationManager {
    private static LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
    private static Notification currentNotification = null;

    public void show(Notification notification) {
        pendingNotifications.add(notification);
    }

    private void update() {
        if (currentNotification != null && !currentNotification.isShown()) {
            currentNotification = null;
        }

        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }

    }

    public void clearList() {
    	pendingNotifications.clear();
    }
    
    public void render(GuiGraphics g) {
        update();

        if (currentNotification != null)
            currentNotification.render(g);
    }
}
