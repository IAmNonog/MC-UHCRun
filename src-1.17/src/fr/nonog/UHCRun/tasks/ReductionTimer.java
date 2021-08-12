package fr.nonog.UHCRun.tasks;

import fr.nonog.UHCRun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class ReductionTimer extends BukkitRunnable {

    private int timerM;
    private int timerS;
    private UHCRun main;

    public ReductionTimer(UHCRun main) {
        this.main = main;

        timerS = 59;
        timerM = main.getConfigur().getInt("game.gradualReduction.TimeBeforeReduction") -1;

    }

    @Override
    public void run() {

        main.getScoreBoard().setDuringReduction(timerS, timerM);

        timerS--;
        if(timerS<=0 && timerM>0) {
            timerS = 59;
            timerM--;
        }
        if(timerM==0 && timerS==0) {
            Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - The map begins to shrink to a size of "+main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction"));

            main.reductionWorldBorder(main.getConfigur().getInt("game.gradualReduction.mapSizeAfterReduction"), main.getConfigur().getInt("game.gradualReduction.ReductionSpeed"));

            if(main.getConfigur().getBoolean("game.gradualReduction.enableReduction")) {
                DuringReductionTimer drt = new DuringReductionTimer(main);
                drt.runTaskTimer(main, 0, 20);
            }

            cancel();
        }
        if(!main.isGameLaunch()) {
            main.getScoreBoard().setGameFinished();
            cancel();
        }

    }
}
