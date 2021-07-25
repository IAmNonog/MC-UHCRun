package fr.nonog.UHCRun.tasks;

import fr.nonog.UHCRun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class DuringReductionTimer extends BukkitRunnable {

    private int timerM;
    private int timerS;
    private UHCRun main;

    public DuringReductionTimer(UHCRun main) {
        this.main = main;


        int temp = main.getConfigur().getInt("game.gradualReduction.ReductionSpeed");
        temp = (int)(((Bukkit.getWorld(main.getConfigur().getString("game.map")).getWorldBorder().getSize()) - main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction"))/temp);

        timerM = (int) temp/60;
        timerS = (int) temp%60;
    }

    @Override
    public void run() {

        main.getScoreBoard().setInReduction(timerS, timerM);

        timerS--;
        if(timerS<=0 && timerM>0) {
            timerS = 59;
            timerM--;
        }
        if(timerM==0 && timerS==0) {
            Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - The map has finished shrinking");
            if(! main.getConfigur().getBoolean("game.deathMatch.activateDeathMatch")) {
                main.getScoreBoard().setInEndgame();
            }

            cancel();
        }
        if(!main.isGameLaunch()) {
            main.getScoreBoard().setGameFinished();
            cancel();
        }

    }
}