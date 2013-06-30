package net.minecraft.server;

import java.util.List;

public class CommandEffect extends CommandAbstract {

    public CommandEffect() {}

    public String c() {
        return "effect";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandListener icommandlistener) {
        return "commands.effect.usage";
    }

    public void b(ICommandListener icommandlistener, String[] astring) {
        if (astring.length < 2) {
            throw new ExceptionUsage("commands.effect.usage", new Object[0]);
        } else {
            EntityPlayer entityplayer = d(icommandlistener, astring[0]);

            if (astring[1].equals("clear")) {
                if (entityplayer.aH().isEmpty()) {
                    throw new CommandException("commands.effect.failure.notActive.all", new Object[] { entityplayer.getLocalizedName()});
                }

                entityplayer.aG();
                a(icommandlistener, "commands.effect.success.removed.all", new Object[] { entityplayer.getLocalizedName()});
            } else {
                int i = a(icommandlistener, astring[1], 1);
                int j = 600;
                int k = 30;
                int l = 0;

                if (i < 0 || i >= MobEffectList.byId.length || MobEffectList.byId[i] == null) {
                    throw new ExceptionInvalidNumber("commands.effect.notFound", new Object[] { Integer.valueOf(i)});
                }

                if (astring.length >= 3) {
                    k = a(icommandlistener, astring[2], 0, 1000000);
                    if (MobEffectList.byId[i].isInstant()) {
                        j = k;
                    } else {
                        j = k * 20;
                    }
                } else if (MobEffectList.byId[i].isInstant()) {
                    j = 1;
                }

                if (astring.length >= 4) {
                    l = a(icommandlistener, astring[3], 0, 255);
                }

                if (k == 0) {
                    if (!entityplayer.EntityLiving(i)) {
                        throw new CommandException("commands.effect.failure.notActive", new Object[] { ChatMessageComponent.e(MobEffectList.byId[i].a()), entityplayer.getLocalizedName()});
                    }

                    entityplayer.k(i);
                    a(icommandlistener, "commands.effect.success.removed", new Object[] { ChatMessageComponent.e(MobEffectList.byId[i].a()), entityplayer.getLocalizedName()});
                } else {
                    MobEffect mobeffect = new MobEffect(i, j, l);

                    entityplayer.d(mobeffect);
                    a(icommandlistener, "commands.effect.success", new Object[] { ChatMessageComponent.e(mobeffect.f()), Integer.valueOf(i), Integer.valueOf(l), entityplayer.getLocalizedName(), Integer.valueOf(k)});
                }
            }
        }
    }

    public List a(ICommandListener icommandlistener, String[] astring) {
        return astring.length == 1 ? a(astring, this.d()) : null;
    }

    protected String[] d() {
        return MinecraftServer.getServer().getPlayers();
    }

    public boolean a(String[] astring, int i) {
        return i == 0;
    }
}
