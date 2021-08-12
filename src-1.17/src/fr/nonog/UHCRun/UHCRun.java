package fr.nonog.UHCRun;

import fr.nonog.UHCRun.commands.CommandsUHC;
import fr.nonog.UHCRun.tasks.BeforeTaupeTimer;
import fr.nonog.UHCRun.tasks.ReductionTimer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Vector;

public class UHCRun extends JavaPlugin {

    private FileConfiguration config;
    private boolean gameLaunch;
    private CommandsUHC commandsUHC;
    private ScoreBoard scoreBoard;



    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = this.getConfig();
        gameLaunch = false;



        scoreBoard = new ScoreBoard(this);
        scoreBoard.setBeforeGame();

        commandsUHC = new CommandsUHC(this);

        String worldname = config.getString("game.map");
        World world = Bukkit.getWorld(worldname);

        WorldBorder wb = world.getWorldBorder();
        wb.setCenter(config.getInt("spawn.coordonate.x"), config.getInt("spawn.coordonate.z"));
        wb.setSize(config.getDouble("game.map-size"));


        if(config.getBoolean("spawn.buildSpawn")) {
            buildSpawn(world);
        }




        getCommand("UHC").setExecutor(commandsUHC);
        getCommand("t").setExecutor(commandsUHC);


        getServer().getPluginManager().registerEvents(new UHCListeners(this, commandsUHC, scoreBoard), this);



        world.setGameRuleValue("naturalRegeneration", "false");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[UHCRun] plugin enabled !");
    }

    @Override
    public void onDisable() {



        boolean verifTeamDel = commandsUHC.deleteAllScoreBoardTeams();
        if(verifTeamDel) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "[UHCRun] Scoreboard Teams correctly deleted !");
        }
        else{
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "[UHCRun] Error : Scoreboard Teams not properly deleted !");

        }
        commandsUHC.deleteAllTeams();
        scoreBoard.del();


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
        w.getBlockAt(xc, yc, zc).setType(Material.GRAY_STAINED_GLASS);
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



    public void launchGame(Boolean team) {

        Bukkit.getWorld(config.getString("game.map")).setTime(0);

        if(team) {



            commandsUHC.deleteEmptyTeams();
            setGameLaunch(true);


            //Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), String.format("spreadplayers %d %d %d %d %b %s", x, z, minDistance, maxRange, respectTeams, playersSelector));
            commandsUHC.spreedPlayers();

            for(Player p : Bukkit.getOnlinePlayers()) {
                if(commandsUHC.isInATeam(p)) {
                    p.setGameMode(GameMode.SURVIVAL);

                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.setLevel(0);
                    p.getInventory().clear();
                    p.getInventory().setArmorContents(null);
                    //p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*60, 5, false, true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*30, 5, false, true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*30, 5, false, true));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20*30, 5, false, true));


                }

            }


            if(config.getBoolean("taupe.enableTaupe")) {
                BeforeTaupeTimer beforeTaupeTimer = new BeforeTaupeTimer(this);
                beforeTaupeTimer.runTaskTimer(this, 0, 20);
            }
            else if(config.getBoolean("game.gradualReduction.enableReduction")) {
                ReductionTimer reductionTimer = new ReductionTimer(this);
                reductionTimer.runTaskTimer(this, 0, 20);
            }
            else{
                scoreBoard.setInEndgame();
            }

        }
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }


    public void reductionWorldBorder(int mapSize, int speed) {
        String worldname = config.getString("game.map");
        World world = Bukkit.getWorld(worldname);


        WorldBorder wb = world.getWorldBorder();

        int sp = (int)((wb.getSize()-mapSize)/speed);

        wb.setSize(mapSize, sp);
    }

    public void selectTaupes() {
        commandsUHC.selectionTaupe();

    }



}
