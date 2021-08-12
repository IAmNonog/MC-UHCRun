package fr.nonog.UHCRun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }

    }
    public void del() {
        nameUHC.unregister();
        health.unregister();
        scoreBoard = null;
    }

    public Scoreboard getScoreBoard() {
        return scoreBoard;
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
        Score s44 = nameUHC.getScore(ChatColor.AQUA+"The map will have a size of "+ (main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction"))+"x"+(main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction")));
        s44.setScore(6);
        Score s5 = nameUHC.getScore("  ");
        s5.setScore(5);
        Score s6 = nameUHC.getScore(ChatColor.GREEN+"start of reduction in :"+ChatColor.YELLOW+" "+timeM+"m "+timeS+"s");
        s6.setScore(4);
        Score s7 = nameUHC.getScore("    ");
        s7.setScore(3);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }
    }

    public void setInReduction(int timeS, int timeM) {
        for(String e : scoreBoard.getEntries()) {
            scoreBoard.resetScores(e);
        }

        Score s1 = nameUHC.getScore("");
        s1.setScore(10);
        Score s2 = nameUHC.getScore(ChatColor.AQUA+"Be careful, the map is shrinking");
        s2.setScore(9);
        Score s3 = nameUHC.getScore(ChatColor.AQUA+"in : "+ChatColor.YELLOW+""+ (main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction") /2)+" // "+ (main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction") /2));
        s3.setScore(8);
        Score s4 = nameUHC.getScore(" ");
        s4.setScore(7);
        Score s44 = nameUHC.getScore(ChatColor.AQUA+"The map will have a size of "+ (main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction"))+"x"+(main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction")));
        s44.setScore(6);
        Score s5 = nameUHC.getScore("  ");
        s5.setScore(5);
        Score s6 = nameUHC.getScore(ChatColor.GREEN+"end of reduction in :"+ChatColor.YELLOW+" "+timeM+"m "+timeS+"s");
        s6.setScore(4);
        Score s7 = nameUHC.getScore("    ");
        s7.setScore(3);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }
    }

    public void setInEndgame() {
        for(String e : scoreBoard.getEntries()) {
            scoreBoard.resetScores(e);
        }

        Score s1 = nameUHC.getScore("");
        s1.setScore(10);
        Score s2 = nameUHC.getScore(ChatColor.GREEN+"Be the last survivor! ");
        s2.setScore(9);
        Score s22 = nameUHC.getScore(" ");
        s22.setScore(8);
        Score s3 = nameUHC.getScore("   ");
        s3.setScore(7);
        Score s4 = nameUHC.getScore(ChatColor.RED+"ENDGAME ! ");
        s4.setScore(6);
        Score s5 = nameUHC.getScore("  ");
        s5.setScore(5);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }

    }
    public void setGameFinished() {
        for(String e : scoreBoard.getEntries()) {
            scoreBoard.resetScores(e);
        }

        Score s1 = nameUHC.getScore("");
        s1.setScore(10);
        Score s2 = nameUHC.getScore(ChatColor.GREEN+"The game is over ");
        s2.setScore(9);
        Score s22 = nameUHC.getScore(" ");
        s22.setScore(8);
        Score s3 = nameUHC.getScore("   ");
        s3.setScore(7);
        Score s4 = nameUHC.getScore(ChatColor.RED+"check the chat to ");
        s4.setScore(6);
        Score s5 = nameUHC.getScore(ChatColor.RED+"see the winners ");
        s5.setScore(5);
        Score s6 = nameUHC.getScore("        ");
        s6.setScore(4);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }

    }

    public void setBeforeTaupe(int timeS, int timeM) {
        for(String e : scoreBoard.getEntries()) {
            scoreBoard.resetScores(e);
        }

        Score s1 = nameUHC.getScore("");
        s1.setScore(10);
        Score s2 = nameUHC.getScore(ChatColor.AQUA+"The Taupes (intruders) will ");
        s2.setScore(9);
        Score s4 = nameUHC.getScore(ChatColor.AQUA+"soon be revealed");
        s4.setScore(8);
        Score s5 = nameUHC.getScore("  ");
        s5.setScore(7);
        Score s6 = nameUHC.getScore(ChatColor.GREEN+"Taupes Revealed in: :"+ChatColor.YELLOW+" "+timeM+"m "+timeS+"s");
        s6.setScore(6);
        Score s7 = nameUHC.getScore("    ");
        s7.setScore(5);

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(scoreBoard);
        }
    }




}
