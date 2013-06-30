package net.minecraft.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

class MinecraftServerGuiAction implements ActionListener {

    final JTextField a;

    final MinecraftServerGuiMain b;

    MinecraftServerGuiAction(MinecraftServerGuiMain minecraftserverguimain, JTextField jtextfield) {
        this.b = minecraftserverguimain;
        this.a = jtextfield;
    }

    public void actionPerformed(ActionEvent actionevent) {
        String s = this.a.getText().trim();

        if (s.length() > 0) {
            MinecraftServerGuiMain.a(this.b).issueCommand(s, MinecraftServer.getServer());
        }

        this.a.setText("");
    }
}
