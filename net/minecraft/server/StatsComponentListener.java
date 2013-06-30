package net.minecraft.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StatsComponentListener implements ActionListener {

    final StatsComponent a;

    StatsComponentListener(StatsComponent statscomponent) {
        this.a = statscomponent;
    }

    public void actionPerformed(ActionEvent actionevent) {
        StatsComponent.a(this.a);
    }
}
