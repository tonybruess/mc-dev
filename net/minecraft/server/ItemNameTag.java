package net.minecraft.server;

public class ItemNameTag extends Item {

    public ItemNameTag(int i) {
        super(i);
        this.a(CreativeModeTab.i);
    }

    public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving) {
        if (!itemstack.hasName()) {
            return false;
        } else if (entityliving instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) entityliving;

            entitylivingbase.setCustomName(itemstack.getName());
            entitylivingbase.bv();
            --itemstack.count;
            return true;
        } else {
            return super.a(itemstack, entityhuman, entityliving);
        }
    }
}
