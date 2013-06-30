package net.minecraft.server;

public class SlotFurnaceResult extends Slot {

    private EntityHuman index;
    private int b;

    public SlotFurnaceResult(EntityHuman entityhuman, IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.index = entityhuman;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public ItemStack a(int i) {
        if (this.e()) {
            this.b += Math.min(i, this.getItem().count);
        }

        return super.a(i);
    }

    public void a(EntityHuman entityhuman, ItemStack itemstack) {
        this.b(itemstack);
        super.a(entityhuman, itemstack);
    }

    protected void a(ItemStack itemstack, int i) {
        this.b += i;
        this.b(itemstack);
    }

    protected void b(ItemStack itemstack) {
        itemstack.a(this.index.world, this.index, this.b);
        if (!this.index.world.isStatic) {
            int i = this.b;
            float f = RecipesFurnace.getInstance().c(itemstack.id);
            int j;

            if (f == 0.0F) {
                i = 0;
            } else if (f < 1.0F) {
                j = MathHelper.d((float) i * f);
                if (j < MathHelper.f((float) i * f) && (float) Math.random() < (float) i * f - (float) j) {
                    ++j;
                }

                i = j;
            }

            while (i > 0) {
                j = EntityExperienceOrb.getOrbValue(i);
                i -= j;
                this.index.world.addEntity(new EntityExperienceOrb(this.index.world, this.index.locX, this.index.locY + 0.5D, this.index.locZ + 0.5D, j));
            }
        }

        this.b = 0;
        if (itemstack.id == Item.IRON_INGOT.id) {
            this.index.a((Statistic) AchievementList.k, 1);
        }

        if (itemstack.id == Item.COOKED_FISH.id) {
            this.index.a((Statistic) AchievementList.p, 1);
        }
    }
}
