package net.minecraft.server;

public class ItemSeedFood extends ItemFood {

    private int durability;
    private int craftingResult;

    public ItemSeedFood(int i, int j, float f, int k, int l) {
        super(i, j, f, false);
        this.durability = k;
        this.craftingResult = l;
    }

    public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
        if (l != 1) {
            return false;
        } else if (entityhuman.a(i, j, k, l, itemstack) && entityhuman.a(i, j + 1, k, l, itemstack)) {
            int i1 = world.getTypeId(i, j, k);

            if (i1 == this.craftingResult && world.isEmpty(i, j + 1, k)) {
                world.setTypeIdUpdate(i, j + 1, k, this.durability);
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
