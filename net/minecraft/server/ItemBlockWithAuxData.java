package net.minecraft.server;

public class ItemBlockWithAuxData extends ItemBlock {

    private Block id;

    public ItemBlockWithAuxData(int i, Block block) {
        super(i);
        this.id = block;
        this.setMaxDurability(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i;
    }
}
