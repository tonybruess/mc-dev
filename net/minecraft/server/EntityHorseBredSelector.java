package net.minecraft.server;

final class EntityHorseBredSelector implements IEntitySelector {

    EntityHorseBredSelector() {}

    public boolean a(Entity entity) {
        return entity instanceof EntityHorse && ((EntityHorse) entity).ce();
    }
}
