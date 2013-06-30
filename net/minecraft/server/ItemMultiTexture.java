package net.minecraft.server;

public class ItemMultiTexture extends ItemBlock {

    private final Block id;
    private final String[] durability;

    public ItemMultiTexture(int i, Block block, String[] astring) {
        super(i);
        this.id = block;
        this.durability = astring;
        this.setMaxDurability(0);
        this.a(true);
    }

    public int filterData(int i) {
        return i;
    }

    public String d(ItemStack itemstack) {
        int i = itemstack.getData();

        if (i < 0 || i >= this.durability.length) {
            i = 0;
        }

        return super.getName() + "." + this.durability[i];
    }
}
