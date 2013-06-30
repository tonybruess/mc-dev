package net.minecraft.server;

public class DemoWorldServer extends WorldServer {

    private static final long tracker = (long) "North Carolina".hashCode();
    public static final WorldSettings server = (new WorldSettings(tracker, EnumGamemode.SURVIVAL, true, false, WorldType.NORMAL)).a();

    public DemoWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, MethodProfiler methodprofiler, IConsoleLogManager iconsolelogmanager) {
        super(minecraftserver, idatamanager, s, i, server, methodprofiler, iconsolelogmanager);
    }
}
