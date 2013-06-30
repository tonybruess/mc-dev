package net.minecraft.server;

public class EnchantmentWeaponDamage extends Enchantment {

    private static final String[] C = new String[] { "all", "undead", "arthropods"};
    private static final int[] D = new int[] { 1, 5, 5};
    private static final int[] E = new int[] { 11, 8, 8};
    private static final int[] F = new int[] { 20, 20, 20};
    public final int weight;

    public EnchantmentWeaponDamage(int i, int j, int k) {
        super(i, j, EnchantmentSlotType.WEAPON);
        this.weight = k;
    }

    public int a(int i) {
        return D[this.weight] + (i - 1) * E[this.weight];
    }

    public int b(int i) {
        return this.a(i) + F[this.weight];
    }

    public int getMaxLevel() {
        return 5;
    }

    public float a(int i, EntityLiving entityliving) {
        return this.weight == 0 ? (float) i * 1.25F : (this.weight == 1 && entityliving.aU() == EnumMonsterType.UNDEAD ? (float) i * 2.5F : (this.weight == 2 && entityliving.aU() == EnumMonsterType.ARTHROPOD ? (float) i * 2.5F : 0.0F));
    }

    public String a() {
        return "enchantment.damage." + C[this.weight];
    }

    public boolean a(Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentWeaponDamage);
    }

    public boolean canEnchant(ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemAxe ? true : super.canEnchant(itemstack);
    }
}
