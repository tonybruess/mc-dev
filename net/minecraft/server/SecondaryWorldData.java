package net.minecraft.server;

public class SecondaryWorldData extends WorldData {

    private final WorldData seed;

    public SecondaryWorldData(WorldData worlddata) {
        this.seed = worlddata;
    }

    public NBTTagCompound a() {
        return this.seed.a();
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        return this.seed.a(nbttagcompound);
    }

    public long getSeed() {
        return this.seed.getSeed();
    }

    public int c() {
        return this.seed.c();
    }

    public int d() {
        return this.seed.d();
    }

    public int e() {
        return this.seed.e();
    }

    public long getTime() {
        return this.seed.getTime();
    }

    public long getDayTime() {
        return this.seed.getDayTime();
    }

    public NBTTagCompound i() {
        return this.seed.i();
    }

    public int j() {
        return this.seed.j();
    }

    public String getName() {
        return this.seed.getName();
    }

    public int l() {
        return this.seed.l();
    }

    public boolean isThundering() {
        return this.seed.isThundering();
    }

    public int getThunderDuration() {
        return this.seed.getThunderDuration();
    }

    public boolean hasStorm() {
        return this.seed.hasStorm();
    }

    public int getWeatherDuration() {
        return this.seed.getWeatherDuration();
    }

    public EnumGamemode getGameType() {
        return this.seed.getGameType();
    }

    public void setTime(long i) {}

    public void setDayTime(long i) {}

    public void setSpawn(int i, int j, int k) {}

    public void setName(String s) {}

    public void e(int i) {}

    public void setThundering(boolean flag) {}

    public void setThunderDuration(int i) {}

    public void setStorm(boolean flag) {}

    public void setWeatherDuration(int i) {}

    public boolean shouldGenerateMapFeatures() {
        return this.seed.shouldGenerateMapFeatures();
    }

    public boolean isHardcore() {
        return this.seed.isHardcore();
    }

    public WorldType getType() {
        return this.seed.getType();
    }

    public void setType(WorldType worldtype) {}

    public boolean allowCommands() {
        return this.seed.allowCommands();
    }

    public boolean isInitialized() {
        return this.seed.isInitialized();
    }

    public void d(boolean flag) {}

    public GameRules getGameRules() {
        return this.seed.getGameRules();
    }
}
