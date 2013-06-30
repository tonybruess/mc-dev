package net.minecraft.server;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class MinecraftServerGuiFocus extends FocusAdapter {

    final MinecraftServerGuiMain a;

    MinecraftServerGuiFocus(MinecraftServerGuiMain minecraftserverguimain) {
        this.a = minecraftserverguimain;
    }

    public void focusGained(FocusEvent focusevent) {}
}
