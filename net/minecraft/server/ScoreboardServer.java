package net.minecraft.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ScoreboardServer extends Scoreboard {

    private final MinecraftServer objectivesByName;
    private final Set objectivesByCriteria = new HashSet();
    private ScoreboardSaveData playerScores;

    public ScoreboardServer(MinecraftServer minecraftserver) {
        this.objectivesByName = minecraftserver;
    }

    public void handleScoreChanged(ScoreboardScore scoreboardscore) {
        super.handleScoreChanged(scoreboardscore);
        if (this.objectivesByCriteria.contains(scoreboardscore.getObjective())) {
            this.objectivesByName.getPlayerList().sendAll(new Packet207SetScoreboardScore(scoreboardscore, 0));
        }

        this.b();
    }

    public void handlePlayerRemoved(String s) {
        super.handlePlayerRemoved(s);
        this.objectivesByName.getPlayerList().sendAll(new Packet207SetScoreboardScore(s));
        this.b();
    }

    public void setDisplaySlot(int i, ScoreboardObjective scoreboardobjective) {
        ScoreboardObjective scoreboardobjective1 = this.getObjectiveForSlot(i);

        super.setDisplaySlot(i, scoreboardobjective);
        if (scoreboardobjective1 != scoreboardobjective && scoreboardobjective1 != null) {
            if (this.h(scoreboardobjective1) > 0) {
                this.objectivesByName.getPlayerList().sendAll(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            } else {
                this.g(scoreboardobjective1);
            }
        }

        if (scoreboardobjective != null) {
            if (this.objectivesByCriteria.contains(scoreboardobjective)) {
                this.objectivesByName.getPlayerList().sendAll(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            } else {
                this.e(scoreboardobjective);
            }
        }

        this.b();
    }

    public void addPlayerToTeam(String s, ScoreboardTeam scoreboardteam) {
        super.addPlayerToTeam(s, scoreboardteam);
        this.objectivesByName.getPlayerList().sendAll(new Packet209SetScoreboardTeam(scoreboardteam, Arrays.asList(new String[] { s}), 3));
        this.b();
    }

    public void removePlayerFromTeam(String s, ScoreboardTeam scoreboardteam) {
        super.removePlayerFromTeam(s, scoreboardteam);
        this.objectivesByName.getPlayerList().sendAll(new Packet209SetScoreboardTeam(scoreboardteam, Arrays.asList(new String[] { s}), 4));
        this.b();
    }

    public void handleObjectiveAdded(ScoreboardObjective scoreboardobjective) {
        super.handleObjectiveAdded(scoreboardobjective);
        this.b();
    }

    public void handleObjectiveChanged(ScoreboardObjective scoreboardobjective) {
        super.handleObjectiveChanged(scoreboardobjective);
        if (this.objectivesByCriteria.contains(scoreboardobjective)) {
            this.objectivesByName.getPlayerList().sendAll(new Packet206SetScoreboardObjective(scoreboardobjective, 2));
        }

        this.b();
    }

    public void handleObjectiveRemoved(ScoreboardObjective scoreboardobjective) {
        super.handleObjectiveRemoved(scoreboardobjective);
        if (this.objectivesByCriteria.contains(scoreboardobjective)) {
            this.g(scoreboardobjective);
        }

        this.b();
    }

    public void handleTeamAdded(ScoreboardTeam scoreboardteam) {
        super.handleTeamAdded(scoreboardteam);
        this.objectivesByName.getPlayerList().sendAll(new Packet209SetScoreboardTeam(scoreboardteam, 0));
        this.b();
    }

    public void handleTeamChanged(ScoreboardTeam scoreboardteam) {
        super.handleTeamChanged(scoreboardteam);
        this.objectivesByName.getPlayerList().sendAll(new Packet209SetScoreboardTeam(scoreboardteam, 2));
        this.b();
    }

    public void handleTeamRemoved(ScoreboardTeam scoreboardteam) {
        super.handleTeamRemoved(scoreboardteam);
        this.objectivesByName.getPlayerList().sendAll(new Packet209SetScoreboardTeam(scoreboardteam, 1));
        this.b();
    }

    public void a(ScoreboardSaveData scoreboardsavedata) {
        this.playerScores = scoreboardsavedata;
    }

    protected void b() {
        if (this.playerScores != null) {
            this.playerScores.c();
        }
    }

    public List getScoreboardScorePacketsForObjective(ScoreboardObjective scoreboardobjective) {
        ArrayList arraylist = new ArrayList();

        arraylist.add(new Packet206SetScoreboardObjective(scoreboardobjective, 0));

        for (int i = 0; i < 3; ++i) {
            if (this.getObjectiveForSlot(i) == scoreboardobjective) {
                arraylist.add(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            }
        }

        Iterator iterator = this.getScoresForObjective(scoreboardobjective).iterator();

        while (iterator.hasNext()) {
            ScoreboardScore scoreboardscore = (ScoreboardScore) iterator.next();

            arraylist.add(new Packet207SetScoreboardScore(scoreboardscore, 0));
        }

        return arraylist;
    }

    public void e(ScoreboardObjective scoreboardobjective) {
        List list = this.getScoreboardScorePacketsForObjective(scoreboardobjective);
        Iterator iterator = this.objectivesByName.getPlayerList().players.iterator();

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();
            Iterator iterator1 = list.iterator();

            while (iterator1.hasNext()) {
                Packet packet = (Packet) iterator1.next();

                entityplayer.playerConnection.sendPacket(packet);
            }
        }

        this.objectivesByCriteria.add(scoreboardobjective);
    }

    public List f(ScoreboardObjective scoreboardobjective) {
        ArrayList arraylist = new ArrayList();

        arraylist.add(new Packet206SetScoreboardObjective(scoreboardobjective, 1));

        for (int i = 0; i < 3; ++i) {
            if (this.getObjectiveForSlot(i) == scoreboardobjective) {
                arraylist.add(new Packet208SetScoreboardDisplayObjective(i, scoreboardobjective));
            }
        }

        return arraylist;
    }

    public void g(ScoreboardObjective scoreboardobjective) {
        List list = this.f(scoreboardobjective);
        Iterator iterator = this.objectivesByName.getPlayerList().players.iterator();

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();
            Iterator iterator1 = list.iterator();

            while (iterator1.hasNext()) {
                Packet packet = (Packet) iterator1.next();

                entityplayer.playerConnection.sendPacket(packet);
            }
        }

        this.objectivesByCriteria.remove(scoreboardobjective);
    }

    public int h(ScoreboardObjective scoreboardobjective) {
        int i = 0;

        for (int j = 0; j < 3; ++j) {
            if (this.getObjectiveForSlot(j) == scoreboardobjective) {
                ++i;
            }
        }

        return i;
    }
}
