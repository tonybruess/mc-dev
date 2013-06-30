package net.minecraft.server;

public class ItemWithAuxData extends ItemBlock {

    private final Block id;
    private String[] durability;

    public ItemWithAuxData(int i, boolean flag) {
        super(i);
        this.id = Block.byId[this.g()];
        if (flag) {
            this.setMaxDurability(0);
            this.a(true);
        }
    }

    public int filterData(int i) {
        return i;
    }

    public ItemWithAuxData a(String[] astring) {
        this.durability = astring;
        return this;
    }

    public String d(ItemStack itemstack) {
        if (this.durability == null) {
            return super.d(itemstack);
        } else {
            int i = itemstack.getData();

            return i >= 0 && i < this.durability.length ? super.d(itemstack) + "." + this.durability[i] : super.d(itemstack);
        }
    }
}
