package net.minecraft.server;

public class SlotMerchantResult extends Slot {

    private final InventoryMerchant index;
    private EntityHuman b;
    private int c;
    private final IMerchant d;

    public SlotMerchantResult(EntityHuman entityhuman, IMerchant imerchant, InventoryMerchant inventorymerchant, int i, int j, int k) {
        super(inventorymerchant, i, j, k);
        this.b = entityhuman;
        this.d = imerchant;
        this.index = inventorymerchant;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public ItemStack a(int i) {
        if (this.e()) {
            this.c += Math.min(i, this.getItem().count);
        }

        return super.a(i);
    }

    protected void a(ItemStack itemstack, int i) {
        this.c += i;
        this.b(itemstack);
    }

    protected void b(ItemStack itemstack) {
        itemstack.a(this.b.world, this.b, this.c);
        this.c = 0;
    }

    public void a(EntityHuman entityhuman, ItemStack itemstack) {
        this.b(itemstack);
        MerchantRecipe merchantrecipe = this.index.getRecipe();

        if (merchantrecipe != null) {
            ItemStack itemstack1 = this.index.getItem(0);
            ItemStack itemstack2 = this.index.getItem(1);

            if (this.a(merchantrecipe, itemstack1, itemstack2) || this.a(merchantrecipe, itemstack2, itemstack1)) {
                this.d.a(merchantrecipe);
                if (itemstack1 != null && itemstack1.count <= 0) {
                    itemstack1 = null;
                }

                if (itemstack2 != null && itemstack2.count <= 0) {
                    itemstack2 = null;
                }

                this.index.setItem(0, itemstack1);
                this.index.setItem(1, itemstack2);
            }
        }
    }

    private boolean a(MerchantRecipe merchantrecipe, ItemStack itemstack, ItemStack itemstack1) {
        ItemStack itemstack2 = merchantrecipe.getBuyItem1();
        ItemStack itemstack3 = merchantrecipe.getBuyItem2();

        if (itemstack != null && itemstack.id == itemstack2.id) {
            if (itemstack3 != null && itemstack1 != null && itemstack3.id == itemstack1.id) {
                itemstack.count -= itemstack2.count;
                itemstack1.count -= itemstack3.count;
                return true;
            }

            if (itemstack3 == null && itemstack1 == null) {
                itemstack.count -= itemstack2.count;
                return true;
            }
        }

        return false;
    }
}
