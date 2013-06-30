package net.minecraft.server;

class ContainerHorseInventorySlotArmor extends Slot {

    final EntityHorse index;

    final ContainerHorseInventory b;

    ContainerHorseInventorySlotArmor(ContainerHorseInventory containerhorseinventory, IInventory iinventory, int i, int j, int k, EntityHorse entityhorse) {
        super(iinventory, i, j, k);
        this.b = containerhorseinventory;
        this.index = entityhorse;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return super.isAllowed(itemstack) && this.index.cr() && EntityHorse.v(itemstack.id) && !this.e();
    }
}
