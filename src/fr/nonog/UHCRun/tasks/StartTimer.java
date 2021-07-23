package fr.nonog.UHCRun.tasks;

import fr.nonog.UHCRun.ScoreBoard;
import fr.nonog.UHCRun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class StartTimer extends BukkitRunnable {

    private int timer = 10;
    private UHCRun main;

    public StartTimer(UHCRun main) {
        this.main = main;
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - The game will start in "+timer+" s");
        main.getScoreBoard().setDuringStartTimer(timer);
        timer--;
        if(timer==0) {
            Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - Game on ! Good luck to everyone");
            main.launchGame(true);
            cancel();
        }

    }
}
