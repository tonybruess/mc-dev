package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CommandSpreadPlayers extends CommandAbstract {

    public CommandSpreadPlayers() {}

    public String c() {
        return "spreadplayers";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandListener icommandlistener) {
        return "commands.spreadplayers.usage";
    }

    public void b(ICommandListener icommandlistener, String[] astring) {
        if (astring.length < 6) {
            throw new ExceptionUsage("commands.spreadplayers.usage", new Object[0]);
        } else {
            byte b0 = 0;
            int i = b0 + 1;
            double d0 = a(icommandlistener, Double.NaN, astring[b0]);
            double d1 = a(icommandlistener, Double.NaN, astring[i++]);
            double d2 = a(icommandlistener, astring[i++], 0.0D);
            double d3 = a(icommandlistener, astring[i++], d2 + 1.0D);
            boolean flag = c(icommandlistener, astring[i++]);
            ArrayList arraylist = Lists.newArrayList();

            while (true) {
                while (i < astring.length) {
                    String s = astring[i++];

                    if (PlayerSelector.isPattern(s)) {
                        EntityPlayer[] aentityplayer = PlayerSelector.getPlayers(icommandlistener, s);

                        if (aentityplayer == null || aentityplayer.length == 0) {
                            throw new ExceptionPlayerNotFound();
                        }

                        Collections.addAll(arraylist, aentityplayer);
                    } else {
                        EntityPlayer entityplayer = MinecraftServer.getServer().getPlayerList().getPlayer(s);

                        if (entityplayer == null) {
                            throw new ExceptionPlayerNotFound();
                        }

                        arraylist.add(entityplayer);
                    }
                }

                if (arraylist.isEmpty()) {
                    throw new ExceptionPlayerNotFound();
                }

                icommandlistener.ICommandListener(ChatMessageComponent.b("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[] { b(arraylist), Double.valueOf(d0), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3)}));
                this.a(icommandlistener, arraylist, new CommandSpreadPlayersPosition(d0, d1), d2, d3, ((EntityLiving) arraylist.get(0)).world, flag);
                return;
            }
        }
    }

    private void a(ICommandListener icommandlistener, List list, CommandSpreadPlayersPosition commandspreadplayersposition, double d0, double d1, World world, boolean flag) {
        Random random = new Random();
        double d2 = commandspreadplayersposition.a - d1;
        double d3 = commandspreadplayersposition.b - d1;
        double d4 = commandspreadplayersposition.a + d1;
        double d5 = commandspreadplayersposition.b + d1;
        CommandSpreadPlayersPosition[] acommandspreadplayersposition = this.a(random, flag ? this.a(list) : list.size(), d2, d3, d4, d5);
        int i = this.a(commandspreadplayersposition, d0, world, random, d2, d3, d4, d5, acommandspreadplayersposition, flag);
        double d6 = this.a(list, world, acommandspreadplayersposition, flag);

        a(icommandlistener, "commands.spreadplayers.success." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayersposition.length), Double.valueOf(commandspreadplayersposition.a), Double.valueOf(commandspreadplayersposition.b)});
        if (acommandspreadplayersposition.length > 1) {
            icommandlistener.ICommandListener(ChatMessageComponent.b("commands.spreadplayers.info." + (flag ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d6)}), Integer.valueOf(i)}));
        }
    }

    private int a(List list) {
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityLiving entityliving = (EntityLiving) iterator.next();

            if (entityliving instanceof EntityHuman) {
                hashset.add(((EntityHuman) entityliving).getScoreboardTeam());
            } else {
                hashset.add(null);
            }
        }

        return hashset.size();
    }

    private int a(CommandSpreadPlayersPosition commandspreadplayersposition, double d0, World world, Random random, double d1, double d2, double d3, double d4, CommandSpreadPlayersPosition[] acommandspreadplayersposition, boolean flag) {
        boolean flag1 = true;
        double d5 = 3.4028234663852886E38D;

        int i;

        for (i = 0; i < 10000 && flag1; ++i) {
            flag1 = false;
            d5 = 3.4028234663852886E38D;

            CommandSpreadPlayersPosition commandspreadplayersposition1;
            int j;

            for (int k = 0; k < acommandspreadplayersposition.length; ++k) {
                CommandSpreadPlayersPosition commandspreadplayersposition2 = acommandspreadplayersposition[k];

                j = 0;
                commandspreadplayersposition1 = new CommandSpreadPlayersPosition();

                for (int l = 0; l < acommandspreadplayersposition.length; ++l) {
                    if (k != l) {
                        CommandSpreadPlayersPosition commandspreadplayersposition3 = acommandspreadplayersposition[l];
                        double d6 = commandspreadplayersposition2.a(commandspreadplayersposition3);

                        d5 = Math.min(d6, d5);
                        if (d6 < d0) {
                            ++j;
                            commandspreadplayersposition1.a += commandspreadplayersposition3.a - commandspreadplayersposition2.a;
                            commandspreadplayersposition1.b += commandspreadplayersposition3.b - commandspreadplayersposition2.b;
                        }
                    }
                }

                if (j > 0) {
                    commandspreadplayersposition1.a /= (double) j;
                    commandspreadplayersposition1.b /= (double) j;
                    double d7 = (double) commandspreadplayersposition1.b();

                    if (d7 > 0.0D) {
                        commandspreadplayersposition1.a();
                        commandspreadplayersposition2.b(commandspreadplayersposition1);
                    } else {
                        commandspreadplayersposition2.a(random, d1, d2, d3, d4);
                    }

                    flag1 = true;
                }

                if (commandspreadplayersposition2.a(d1, d2, d3, d4)) {
                    flag1 = true;
                }
            }

            if (!flag1) {
                CommandSpreadPlayersPosition[] acommandspreadplayersposition1 = acommandspreadplayersposition;
                int i1 = acommandspreadplayersposition.length;

                for (j = 0; j < i1; ++j) {
                    commandspreadplayersposition1 = acommandspreadplayersposition1[j];
                    if (!commandspreadplayersposition1.b(world)) {
                        commandspreadplayersposition1.a(random, d1, d2, d3, d4);
                        flag1 = true;
                    }
                }
            }
        }

        if (i >= 10000) {
            throw new CommandException("commands.spreadplayers.failure." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayersposition.length), Double.valueOf(commandspreadplayersposition.a), Double.valueOf(commandspreadplayersposition.b), String.format("%.2f", new Object[] { Double.valueOf(d5)})});
        } else {
            return i;
        }
    }

    private double a(List list, World world, CommandSpreadPlayersPosition[] acommandspreadplayersposition, boolean flag) {
        double d0 = 0.0D;
        int i = 0;
        HashMap hashmap = Maps.newHashMap();

        for (int j = 0; j < list.size(); ++j) {
            EntityLiving entityliving = (EntityLiving) list.get(j);
            CommandSpreadPlayersPosition commandspreadplayersposition;

            if (flag) {
                ScoreboardTeam scoreboardteam = entityliving instanceof EntityHuman ? ((EntityHuman) entityliving).getScoreboardTeam() : null;

                if (!hashmap.containsKey(scoreboardteam)) {
                    hashmap.put(scoreboardteam, acommandspreadplayersposition[i++]);
                }

                commandspreadplayersposition = (CommandSpreadPlayersPosition) hashmap.get(scoreboardteam);
            } else {
                commandspreadplayersposition = acommandspreadplayersposition[i++];
            }

            entityliving.a((double) ((float) MathHelper.floor(commandspreadplayersposition.a) + 0.5F), (double) commandspreadplayersposition.a(world), (double) MathHelper.floor(commandspreadplayersposition.b) + 0.5D);
            double d1 = Double.MAX_VALUE;

            for (int k = 0; k < acommandspreadplayersposition.length; ++k) {
                if (commandspreadplayersposition != acommandspreadplayersposition[k]) {
                    double d2 = commandspreadplayersposition.a(acommandspreadplayersposition[k]);

                    d1 = Math.min(d2, d1);
                }
            }

            d0 += d1;
        }

        d0 /= (double) list.size();
        return d0;
    }

    private CommandSpreadPlayersPosition[] a(Random random, int i, double d0, double d1, double d2, double d3) {
        CommandSpreadPlayersPosition[] acommandspreadplayersposition = new CommandSpreadPlayersPosition[i];

        for (int j = 0; j < acommandspreadplayersposition.length; ++j) {
            CommandSpreadPlayersPosition commandspreadplayersposition = new CommandSpreadPlayersPosition();

            commandspreadplayersposition.a(random, d0, d1, d2, d3);
            acommandspreadplayersposition[j] = commandspreadplayersposition;
        }

        return acommandspreadplayersposition;
    }
}
