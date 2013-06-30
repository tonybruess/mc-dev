package net.minecraft.server;

class ContainerHorseInventorySlotSaddle extends Slot {

    final ContainerHorseInventory index;

    ContainerHorseInventorySlotSaddle(ContainerHorseInventory containerhorseinventory, IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.index = containerhorseinventory;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return super.isAllowed(itemstack) && itemstack.id == Item.SADDLE.id && !this.e();
    }
}
