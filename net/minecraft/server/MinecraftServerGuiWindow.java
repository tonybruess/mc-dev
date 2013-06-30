package net.minecraft.server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class MinecraftServerGuiWindow extends WindowAdapter {

    final DedicatedServer a;

    MinecraftServerGuiWindow(DedicatedServer dedicatedserver) {
        this.a = dedicatedserver;
    }

    public void windowClosing(WindowEvent windowevent) {
        this.a.safeShutdown();

        while (!this.a.isStopped()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }

        System.exit(0);
    }
}
