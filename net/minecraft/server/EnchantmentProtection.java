package net.minecraft.server;

public class EnchantmentProtection extends Enchantment {

    private static final String[] C = new String[] { "all", "fire", "fall", "explosion", "projectile"};
    private static final int[] D = new int[] { 1, 10, 5, 5, 3};
    private static final int[] E = new int[] { 11, 8, 6, 8, 6};
    private static final int[] F = new int[] { 20, 12, 10, 12, 15};
    public final int weight;

    public EnchantmentProtection(int i, int j, int k) {
        super(i, j, EnchantmentSlotType.ARMOR);
        this.weight = k;
        if (k == 2) {
            this.slot = EnchantmentSlotType.ARMOR_FEET;
        }
    }

    public int a(int i) {
        return D[this.weight] + (i - 1) * E[this.weight];
    }

    public int b(int i) {
        return this.a(i) + F[this.weight];
    }

    public int getMaxLevel() {
        return 4;
    }

    public int a(int i, DamageSource damagesource) {
        if (damagesource.ignoresInvulnerability()) {
            return 0;
        } else {
            float f = (float) (6 + i * i) / 3.0F;

            return this.weight == 0 ? MathHelper.d(f * 0.75F) : (this.weight == 1 && damagesource.m() ? MathHelper.d(f * 1.25F) : (this.weight == 2 && damagesource == DamageSource.FALL ? MathHelper.d(f * 2.5F) : (this.weight == 3 && damagesource.c() ? MathHelper.d(f * 1.5F) : (this.weight == 4 && damagesource.a() ? MathHelper.d(f * 1.5F) : 0))));
        }
    }

    public String a() {
        return "enchantment.protect." + C[this.weight];
    }

    public boolean a(Enchantment enchantment) {
        if (enchantment instanceof EnchantmentProtection) {
            EnchantmentProtection enchantmentprotection = (EnchantmentProtection) enchantment;

            return enchantmentprotection.weight == this.weight ? false : this.weight == 2 || enchantmentprotection.weight == 2;
        } else {
            return super.a(enchantment);
        }
    }

    public static int a(Entity entity, int i) {
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_FIRE.id, entity.getEquipment());

        if (j > 0) {
            i -= MathHelper.d((float) i * (float) j * 0.15F);
        }

        return i;
    }

    public static double a(Entity entity, double d0) {
        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS.id, entity.getEquipment());

        if (i > 0) {
            d0 -= (double) MathHelper.floor(d0 * (double) ((float) i * 0.15F));
        }

        return d0;
    }
}
