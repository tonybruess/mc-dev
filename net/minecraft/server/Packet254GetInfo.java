package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;

public class Packet254GetInfo extends Packet {

    public int a;

    public Packet254GetInfo() {}

    public void a(DataInput datainput) {
        try {
            this.a = datainput.readByte();
        } catch (Throwable throwable) {
            this.a = 0;
        }
    }

    public void a(DataOutput dataoutput) {}

    public void handle(Connection connection) {
        connection.a(this);
    }

    public int a() {
        return 0;
    }
}
