package net.minecraft.server;

public class BlockColored extends Block {

    public BlockColored(int i, Material material) {
        super(i, material);
        this.a(CreativeModeTab.b);
    }

    public int getDropData(int i) {
        return i;
    }

    public static int j_(int i) {
        return ~i & 15;
    }

    public static int c(int i) {
        return ~i & 15;
    }
}
