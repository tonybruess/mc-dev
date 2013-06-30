package net.minecraft.server;

public class HealthBoostMobEffect extends MobEffectList {

    public HealthBoostMobEffect(int i, boolean flag, int j) {
        super(i, flag, j);
    }

    public void a(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
        super.a(entityliving, attributemapbase, i);
        if (entityliving.aJ() > entityliving.aP()) {
            entityliving.g(entityliving.aP());
        }
    }
}
