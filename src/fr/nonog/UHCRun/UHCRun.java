package fr.nonog.UHCRun;

import fr.nonog.UHCRun.commands.CommandsUHC;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;
import java.util.Vector;

public class UHCRun extends JavaPlugin {

    private FileConfiguration config;
    private boolean gameLaunch;
    private CommandsUHC commandsUHC;
    private Objective healthPrint;



    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = this.getConfig();
        gameLaunch = false;
        commandsUHC = new CommandsUHC(this);

        String worldname = config.getString("game.map");
        World world = Bukkit.getWorld(worldname);

        WorldBorder wb = world.getWorldBorder();
        wb.setCenter(config.getInt("spawn.coordonate.x"), config.getInt("spawn.coordonate.z"));
        wb.setSize(config.getDouble("game.map-size"));


        if(config.getBoolean("spawn.buildSpawn")) {
            buildSpawn(world);
        }


        healthPrint = Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective("HealthUHC", "health");
        healthPrint.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        getCommand("UHC").setExecutor(commandsUHC);


        getServer().getPluginManager().registerEvents(new UHCListeners(this, commandsUHC), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[UHCRun] plugin enabled !");
    }

    @Override
    public void onDisable() {

        healthPrint.unregister();
        boolean verifTeamDel = commandsUHC.deleteAllScoreBoardTeams();
        if(verifTeamDel) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "[UHCRun] Scoreboard Teams correctly deleted !");
        }
        else{
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "[UHCRun] Error : Scoreboard Teams not properly deleted !");

        }



        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[UHCRun] plugin disabled !");
    }

    public void buildSpawn(World w) {
        Bukkit.broadcastMessage(ChatColor.GOLD + "[UHCRun] - Generate spawn...");
        int xc = config.getInt("spawn.coordonate.x");
        int yc = config.getInt("spawn.coordonate.y");
        int zc = config.getInt("spawn.coordonate.z");

        Vector<Block> spawn = new Vector<Block>();
        for(int x=(xc-10); x<=(xc+10); x++) {
            for(int z=(zc-10); z<=(zc+10); z++) {
                spawn.add(w.getBlockAt(x, yc, z));
                for(int y=yc; y<yc+6; y++) {
                    spawn.add(w.getBlockAt(x, y, (zc+10)));
                    spawn.add(w.getBlockAt(x, y, (zc-10)));
                    spawn.add(w.getBlockAt((xc+10), y, z));
                    spawn.add(w.getBlockAt((xc-10), y, z));
                }
            }

        }

        for(Block bl : spawn) {
            bl.setType(Material.GLASS);
        }
        w.getBlockAt(xc, yc, zc).setType(Material.STAINED_GLASS);
        Bukkit.broadcastMessage(ChatColor.GREEN + "[UHCRun] - Spawn generated");
    }

    public void setGameLaunch(boolean gameLaunch) {
        this.gameLaunch = gameLaunch;
    }

    public boolean isGameLaunch() {
        return gameLaunch;
    }

    public FileConfiguration getConfigur() {
        return config;
    }
}
