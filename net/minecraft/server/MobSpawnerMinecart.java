package net.minecraft.server;

class MobSpawnerMinecart extends MobSpawnerAbstract {

    final EntityMinecartMobSpawner mobName;

    MobSpawnerMinecart(EntityMinecartMobSpawner entityminecartmobspawner) {
        this.mobName = entityminecartmobspawner;
    }

    public void a(int i) {
        this.mobName.world.broadcastEntityEffect(this.mobName, (byte) i);
    }

    public World a() {
        return this.mobName.world;
    }

    public int b() {
        return MathHelper.floor(this.mobName.locX);
    }

    public int c() {
        return MathHelper.floor(this.mobName.locY);
    }

    public int d() {
        return MathHelper.floor(this.mobName.locZ);
    }
}
