package net.minecraft.server;

import com.google.common.collect.Multimap;

public class ItemTool extends Item {

    private Block[] craftingResult;
    protected float a = 4.0F;
    private float d;
    protected EnumToolMaterial durability;

    protected ItemTool(int i, float f, EnumToolMaterial enumtoolmaterial, Block[] ablock) {
        super(i);
        this.durability = enumtoolmaterial;
        this.craftingResult = ablock;
        this.maxStackSize = 1;
        this.setMaxDurability(enumtoolmaterial.a());
        this.a = enumtoolmaterial.b();
        this.d = f + enumtoolmaterial.c();
        this.a(CreativeModeTab.i);
    }

    public float getDestroySpeed(ItemStack itemstack, Block block) {
        for (int i = 0; i < this.craftingResult.length; ++i) {
            if (this.craftingResult[i] == block) {
                return this.a;
            }
        }

        return 1.0F;
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        itemstack.a(2, entityliving1);
        return true;
    }

    public boolean a(ItemStack itemstack, World world, int i, int j, int k, int l, EntityLiving entityliving) {
        if ((double) Block.byId[i].l(world, j, k, l) != 0.0D) {
            itemstack.a(1, entityliving);
        }

        return true;
    }

    public int c() {
        return this.durability.e();
    }

    public String g() {
        return this.durability.toString();
    }

    public boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return this.durability.f() == itemstack1.id ? true : super.a(itemstack, itemstack1);
    }

    public Multimap h() {
        Multimap multimap = super.h();

        multimap.put(ItemHayStack.e.a(), new AttributeModifier(e, "Tool modifier", (double) this.d, 0));
        return multimap;
    }
}
