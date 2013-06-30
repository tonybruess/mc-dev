package net.minecraft.server;

final class EntitySelectorNotUndead implements IEntitySelector {

    EntitySelectorNotUndead() {}

    public boolean a(Entity entity) {
        return entity instanceof EntityLiving && ((EntityLiving) entity).aU() != EnumMonsterType.UNDEAD;
    }
}
