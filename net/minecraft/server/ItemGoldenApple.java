package net.minecraft.server;

public class ItemGoldenApple extends ItemFood {

    public ItemGoldenApple(int i, int j, float f, boolean flag) {
        super(i, j, f, flag);
        this.a(true);
    }

    protected void c(ItemStack itemstack, World world, EntityHuman entityhuman) {
        if (!world.isStatic) {
            entityhuman.d(new MobEffect(MobEffectList.x.id, 2400, 0));
        }

        if (itemstack.getData() > 0) {
            if (!world.isStatic) {
                entityhuman.d(new MobEffect(MobEffectList.REGENERATION.id, 600, 4));
                entityhuman.d(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0));
                entityhuman.d(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0));
            }
        } else {
            super.c(itemstack, world, entityhuman);
        }
    }
}
