package net.minecraft.server;

class SlotBrewing extends Slot {

    final ContainerBrewingStand index;

    public SlotBrewing(ContainerBrewingStand containerbrewingstand, IInventory iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
        this.index = containerbrewingstand;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return itemstack != null ? Item.byId[itemstack.id].x() : false;
    }

    public int a() {
        return 64;
    }
}
