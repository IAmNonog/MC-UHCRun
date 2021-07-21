package fr.nonog.UHCRun;

import fr.nonog.UHCRun.commands.CommandsUHC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class UHCListeners implements Listener {

    private UHCRun main;
    private CommandsUHC commandsUHC;

    public UHCListeners(UHCRun main, CommandsUHC commandsUHC) {
        this.main = main;
        this.commandsUHC = commandsUHC;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if(!main.isGameLaunch()) {
            player.teleport(new Location( (Bukkit.getWorld(main.getConfigur().getString("game.map"))) , main.getConfigur().getDouble("spawn.coordonate.x") , main.getConfigur().getDouble("spawn.coordonate.y") +3 , main.getConfigur().getDouble("spawn.coordonate.z") ));
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            player.setLevel(0);

            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard board = scoreboardManager.getNewScoreboard();




        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        //if(main.isGameLaunch()) {
            boolean quitverif = commandsUHC.leavePlayerTeam(e.getEntity());
            if(quitverif) {
                main.getServer().getConsoleSender().sendMessage("[UHC] - Player "+e.getEntity().getName()+" left his team (death) ");
            }

        //}


    }
}
