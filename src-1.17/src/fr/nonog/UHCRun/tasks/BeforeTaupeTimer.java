package fr.nonog.UHCRun.tasks;


import fr.nonog.UHCRun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class BeforeTaupeTimer extends BukkitRunnable {

    private int timerM;
    private int timerS;
    private UHCRun main;

    public BeforeTaupeTimer(UHCRun main) {
        this.main = main;

        timerS = 59;
        timerM = main.getConfigur().getInt("taupe.TimeBeforeReveal") -1;

    }

    @Override
    public void run() {

        main.getScoreBoard().setBeforeTaupe(timerS, timerM);
        if(timerM==0 && ( timerS==10 || timerS==5 || timerS==4 || timerS==3 || timerS==2 || timerS==1 ) ) {
            Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - The 'Taupes' (intruders) will be revealed in "+timerS+" s");
        }

        timerS--;
        if(timerS<=0 && timerM>0) {
            timerS = 59;
            timerM--;
        }


        if(timerM==0 && timerS==0) {
            Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - The 'Taupes' (intruders) have received a message to warn them! Be careful in your team!");

            main.selectTaupes();

            if(main.getConfigur().getBoolean("game.gradualReduction.enableReduction")) {
                ReductionTimer reductionTimer = new ReductionTimer(main);
                reductionTimer.runTaskTimer(main, 0, 20);
            }
            else{
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

