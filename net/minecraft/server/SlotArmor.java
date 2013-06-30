package net.minecraft.server;

class SlotArmor extends Slot {

    final int index;

    final ContainerPlayer b;

    SlotArmor(ContainerPlayer containerplayer, IInventory iinventory, int i, int j, int k, int l) {
        super(iinventory, i, j, k);
        this.b = containerplayer;
        this.index = l;
    }

    public int a() {
        return 1;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return itemstack == null ? false : (itemstack.getItem() instanceof ItemArmor ? ((ItemArmor) itemstack.getItem()).durability == this.index : (itemstack.getItem().id != Block.PUMPKIN.id && itemstack.getItem().id != Item.SKULL.id ? false : this.index == 0));
    }
}
