package net.minecraft.server;

class MobSpawner extends MobSpawnerAbstract {

    final TileEntityMobSpawner mobName;

    MobSpawner(TileEntityMobSpawner tileentitymobspawner) {
        this.mobName = tileentitymobspawner;
    }

    public void a(int i) {
        this.mobName.world.playNote(this.mobName.x, this.mobName.y, this.mobName.z, Block.MOB_SPAWNER.id, i, 0);
    }

    public World a() {
        return this.mobName.world;
    }

    public int b() {
        return this.mobName.x;
    }

    public int c() {
        return this.mobName.y;
    }

    public int d() {
        return this.mobName.z;
    }

    public void a(TileEntityMobSpawnerData tileentitymobspawnerdata) {
        super.a(tileentitymobspawnerdata);
        if (this.a() != null) {
            this.a().notify(this.mobName.x, this.mobName.y, this.mobName.z);
        }
    }
}
