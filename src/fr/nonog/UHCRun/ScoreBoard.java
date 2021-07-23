package fr.nonog.UHCRun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoard {

    private UHCRun main;
    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreBoard;
    private Objective nameUHC;
    private Objective health;

    public ScoreBoard(UHCRun main) {
        this.main = main;
        init();

    }
    public void init() {
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreBoard = scoreboardManager.getNewScoreboard();
        nameUHC = scoreBoard.registerNewObjective("scName", "dummy");
        nameUHC.setDisplayName(ChatColor.GOLD+"UHC");
        nameUHC.setDisplaySlot(DisplaySlot.SIDEBAR);

        health = scoreBoard.registerNewObjective("healthUHC", "health");
        health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }
    public void del() {
        nameUHC.unregister();
        health.unregister();
    }

    public void setScoreBoardForThisPlayer(Player p) {
        p.setScoreboard(scoreBoard);
    }

    public void setBeforeGame() {
        for(String e : scoreBoard.getEntries()) {
            scoreBoard.resetScores(e);
        }

        Score s1 = nameUHC.getScore("");
        s1.setScore(10);
        Score s2 = nameUHC.getScore("The game will start soon");
        s2.setScore(9);
        Score s22 = nameUHC.getScore(" ");
        s22.setScore(8);
        Score s3 = nameUHC.getScore("make your teams");
        s3.setScore(7);
        Score s4 = nameUHC.getScore("use"+ChatColor.GOLD+ "/uhc start");
        s4.setScore(6);
        Score s5 = nameUHC.getScore("  ");
        s5.setScore(5);
        Score s6 = nameUHC.getScore(ChatColor.GREEN+"start in :"+ChatColor.YELLOW+"- s");
        s6.setScore(4);
        Score s7 = nameUHC.getScore("    ");
        s7.setScore(3);

        for(Player all : Bukkit.getOnlinePlayers()) {
                all.setScoreboard(scoreBoard);
        }

    }

    public void setDuringStartTimer(int time) {
        for(String e : scoreBoard.getEntries()) {
            scoreBoard.resetScores(e);
        }

        Score s1 = nameUHC.getScore("");
        s1.setScore(10);
        Score s2 = nameUHC.getScore("The game will start soon");
        s2.setScore(9);
        Score s22 = nameUHC.getScore(" ");
        s22.setScore(8);
        Score s3 = nameUHC.getScore("Get ready !");
        s3.setScore(7);
        Score s5 = nameUHC.getScore("  ");
        s5.setScore(6);
        Score s6 = nameUHC.getScore(ChatColor.GREEN+"start in :"+ChatColor.YELLOW+" "+time+" s");
        s6.setScore(5);
        Score s7 = nameUHC.getScore("    ");
        s7.setScore(4);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }
    }

    public void setDuringReduction(int timeS, int timeM) {
        for(String e : scoreBoard.getEntries()) {
            scoreBoard.resetScores(e);
        }

        Score s1 = nameUHC.getScore("");
        s1.setScore(10);
        Score s2 = nameUHC.getScore(ChatColor.AQUA+"The map will gradually shrink");
        s2.setScore(9);
        Score s3 = nameUHC.getScore(ChatColor.AQUA+"in : "+ChatColor.YELLOW+""+ (main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction") /2)+" // "+ (main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction") /2));
        s3.setScore(8);
        Score s4 = nameUHC.getScore(" ");
        s4.setScore(7);
        Score s5 = nameUHC.getScore("  ");
        s5.setScore(6);
        Score s6 = nameUHC.getScore(ChatColor.GREEN+"start of reduction in :"+ChatColor.YELLOW+" "+timeM+"m "+timeS+"s");
        s6.setScore(5);
        Score s7 = nameUHC.getScore("    ");
        s7.setScore(4);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }
    }




}
