package net.minecraft.server;

public class ItemSeeds extends Item {

    private int id;
    private int durability;

    public ItemSeeds(int i, int j, int k) {
        super(i);
        this.id = j;
        this.durability = k;
        this.a(CreativeModeTab.l);
    }

    public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
        if (l != 1) {
            return false;
        } else if (entityhuman.a(i, j, k, l, itemstack) && entityhuman.a(i, j + 1, k, l, itemstack)) {
            int i1 = world.getTypeId(i, j, k);

            if (i1 == this.durability && world.isEmpty(i, j + 1, k)) {
                world.setTypeIdUpdate(i, j + 1, k, this.id);
                --itemstack.count;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
