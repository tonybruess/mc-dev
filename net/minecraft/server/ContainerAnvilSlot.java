package net.minecraft.server;

class ContainerAnvilSlot extends Slot {

    final World index;

    final int b;

    final int c;

    final int d;

    final ContainerAnvil e;

    ContainerAnvilSlot(ContainerAnvil containeranvil, IInventory iinventory, int i, int j, int k, World world, int l, int i1, int j1) {
        super(iinventory, i, j, k);
        this.e = containeranvil;
        this.index = world;
        this.b = l;
        this.c = i1;
        this.d = j1;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public boolean a(EntityHuman entityhuman) {
        return (entityhuman.abilities.canInstantlyBuild || entityhuman.expLevel >= this.e.a) && this.e.a > 0 && this.e();
    }

    public void a(EntityHuman entityhuman, ItemStack itemstack) {
        if (!entityhuman.abilities.canInstantlyBuild) {
            entityhuman.levelDown(-this.e.a);
        }

        ContainerAnvil.a(this.e).setItem(0, (ItemStack) null);
        if (ContainerAnvil.b(this.e) > 0) {
            ItemStack itemstack1 = ContainerAnvil.a(this.e).getItem(1);

            if (itemstack1 != null && itemstack1.count > ContainerAnvil.b(this.e)) {
                itemstack1.count -= ContainerAnvil.b(this.e);
                ContainerAnvil.a(this.e).setItem(1, itemstack1);
            } else {
                ContainerAnvil.a(this.e).setItem(1, (ItemStack) null);
            }
        } else {
            ContainerAnvil.a(this.e).setItem(1, (ItemStack) null);
        }

        this.e.a = 0;
        if (!entityhuman.abilities.canInstantlyBuild && !this.index.isStatic && this.index.getTypeId(this.b, this.c, this.d) == Block.ANVIL.id && entityhuman.aB().nextFloat() < 0.12F) {
            int i = this.index.getData(this.b, this.c, this.d);
            int j = i & 3;
            int k = i >> 2;

            ++k;
            if (k > 2) {
                this.index.setAir(this.b, this.c, this.d);
                this.index.triggerEffect(1020, this.b, this.c, this.d, 0);
            } else {
                this.index.setData(this.b, this.c, this.d, j | k << 2, 2);
                this.index.triggerEffect(1021, this.b, this.c, this.d, 0);
            }
        } else if (!this.index.isStatic) {
            this.index.triggerEffect(1021, this.b, this.c, this.d, 0);
        }
    }
}
