package net.minecraft.server;

public class ItemMinecart extends Item {

    private static final IDispenseBehavior durability = new DispenseBehaviorMinecart();
    public int a;

    public ItemMinecart(int i, int j) {
        super(i);
        this.maxStackSize = 1;
        this.a = j;
        this.a(CreativeModeTab.e);
        BlockDispenser.a.a(this, durability);
    }

    public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
        int i1 = world.getTypeId(i, j, k);

        if (BlockMinecartTrackAbstract.e_(i1)) {
            if (!world.isStatic) {
                EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, (double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), this.a);

                if (itemstack.hasName()) {
                    entityminecartabstract.a(itemstack.getName());
                }

                world.addEntity(entityminecartabstract);
            }

            --itemstack.count;
            return true;
        } else {
            return false;
        }
    }
}
