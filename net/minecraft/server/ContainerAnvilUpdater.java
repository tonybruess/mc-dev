package net.minecraft.server;

class ContainerAnvilUpdater extends InventorySubcontainer {

    final ContainerAnvil a;

    ContainerAnvilUpdater(ContainerAnvil containeranvil, String s, boolean flag, int i) {
        super(s, flag, i);
        this.a = containeranvil;
    }

    public void update() {
        super.update();
        this.a.a((IInventory) this);
    }

    public boolean b(int i, ItemStack itemstack) {
        return true;
    }
}
