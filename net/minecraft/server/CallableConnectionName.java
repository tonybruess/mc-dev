package net.minecraft.server;

import java.util.concurrent.Callable;

class CallableConnectionName implements Callable {

    final PlayerConnection a;

    final ServerConnection b;

    CallableConnectionName(ServerConnection serverconnection, PlayerConnection playerconnection) {
        this.b = serverconnection;
        this.a = playerconnection;
    }

    public String a() {
        return this.a.toString();
    }

    public Object call() {
        return this.a();
    }
}
