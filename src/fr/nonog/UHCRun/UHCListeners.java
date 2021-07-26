package fr.nonog.UHCRun;

import fr.nonog.UHCRun.commands.CommandsUHC;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Iterator;
import java.util.Vector;


public class UHCListeners implements Listener {

    private UHCRun main;
    private CommandsUHC commandsUHC;
    private ScoreBoard sc;

    public UHCListeners(UHCRun main, CommandsUHC commandsUHC, ScoreBoard sc) {
        this.main = main;
        this.commandsUHC = commandsUHC;
        this.sc = sc;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        sc.setScoreBoardForThisPlayer(player);

        if(!main.isGameLaunch()) {
            player.teleport(new Location( (Bukkit.getWorld(main.getConfigur().getString("game.map"))) , main.getConfigur().getDouble("spawn.coordonate.x") , main.getConfigur().getDouble("spawn.coordonate.y") +3 , main.getConfigur().getDouble("spawn.coordonate.z") ));
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(19.9);
            player.setLevel(0);

            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard board = scoreboardManager.getNewScoreboard();




        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(main.isGameLaunch()) {
            boolean quitverif = commandsUHC.leavePlayerTeam(e.getEntity());
            if(quitverif) {
                main.getServer().getConsoleSender().sendMessage("[UHC] - Player "+e.getEntity().getName()+" left his team (death) ");
            }
            if(commandsUHC.getNBTeams()<=1) {
                Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - The game is over ! ");

                commandsUHC.printWinner();

                e.getEntity().spigot().respawn();
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.teleport(new Location( (Bukkit.getWorld(main.getConfigur().getString("game.map"))) , main.getConfigur().getDouble("spawn.coordonate.x") , main.getConfigur().getDouble("spawn.coordonate.y") +5 , main.getConfigur().getDouble("spawn.coordonate.z") ));
                    p.setGameMode(GameMode.ADVENTURE);
                }

                main.setGameLaunch(false);


            }
            else{

                e.getEntity().setGameMode(GameMode.SPECTATOR);
                Location l = e.getEntity().getLocation();
                e.getEntity().spigot().respawn();

                e.getEntity().teleport(l);
            }


        }


    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if(main.getConfigur().getBoolean("run.enableRun")) {


            Block b = e.getBlock();


            //Tree Run Breaker :
            if(b.getType() == Material.LOG) {
                Vector<Block> arbre = getTree(b);
                for(Block bl : arbre) {
                    if(bl.getType() == Material.LEAVES) {
                        int r = (int)(Math.random() * 100);
                        if(r<5) {
                            bl.getLocation().getWorld().dropItem(bl.getLocation(), new ItemStack(Material.APPLE));
                        }
                    }
                    bl.breakNaturally();

                }

            }
            if(b.getType() == Material.IRON_ORE) {

                int nbR = (int) (1 + (Math.random() * (3 - 1)));

                b.getLocation().getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT, nbR));
                e.setCancelled(true);
                b.setType(Material.AIR);

            }
            if(b.getType() == Material.GOLD_ORE) {


                b.getLocation().getWorld().dropItem(b.getLocation(), new ItemStack(Material.GOLD_INGOT));
                e.setCancelled(true);
                b.setType(Material.AIR);

            }
            if(b.getType() == Material.GRAVEL) {


                b.getLocation().getWorld().dropItem(b.getLocation(), new ItemStack(Material.ARROW, 2));
                e.setCancelled(true);
                b.setType(Material.AIR);

            }


        }
    }
    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if(main.getConfigur().getBoolean("run.enableRun")) {

            if(e.getRecipe().getResult().equals(new ItemStack(Material.WOOD_PICKAXE))) {
                ItemStack item = new ItemStack(Material.STONE_PICKAXE);
                ItemMeta iM = item.getItemMeta();
                iM.addEnchant(Enchantment.DIG_SPEED, 2, true);
                item.setItemMeta(iM);

                e.getInventory().setResult(item);

            }
            if(e.getRecipe().getResult().equals(new ItemStack(Material.WOOD_AXE))) {
                ItemStack item = new ItemStack(Material.STONE_AXE);
                ItemMeta iM = item.getItemMeta();
                iM.addEnchant(Enchantment.DIG_SPEED, 2, true);
                item.setItemMeta(iM);

                e.getInventory().setResult(item);

            }
            if(e.getRecipe().getResult().equals(new ItemStack(Material.IRON_PICKAXE))) {
                ItemStack item = new ItemStack(Material.IRON_PICKAXE);
                ItemMeta iM = item.getItemMeta();
                iM.addEnchant(Enchantment.DIG_SPEED, 2, true);
                item.setItemMeta(iM);

                e.getInventory().setResult(item);

            }
            if(e.getRecipe().getResult().equals(new ItemStack(Material.IRON_AXE))) {
                ItemStack item = new ItemStack(Material.IRON_AXE);
                ItemMeta iM = item.getItemMeta();
                iM.addEnchant(Enchantment.DIG_SPEED, 2, true);
                item.setItemMeta(iM);

                e.getInventory().setResult(item);

            }



        }
    }
    @EventHandler
    public void OnMobDeath(EntityDeathEvent e) {
        if(main.getConfigur().getBoolean("run.enableRun")) {
            if(e.getEntity() instanceof Pig) {
                e.getDrops().clear();
                e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.GRILLED_PORK, 2));
            }
            if(e.getEntity() instanceof Cow) {
                e.getDrops().clear();
                e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.COOKED_BEEF, 2));
                e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.LEATHER, 2));

            }
            if(e.getEntity() instanceof Chicken) {
                e.getDrops().clear();
                e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.COOKED_CHICKEN, 2));
                e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.ARROW, 2));

            }
            if(e.getEntity() instanceof Sheep) {
                e.getDrops().clear();
                e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.COOKED_MUTTON, 2));
                e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.WOOL, 2));

            }
        }
    }





    public Vector<Block> getBlockAlentour(Block depart, Vector<Material> blocksAutorise) {

        Vector<Block> blocks = new Vector<>();
        for(int x=-2; x<4; x++) {
            for(int y=-5; y<10; y++) {
                for(int z=-2; z<4; z++) {
                    Block block = depart.getLocation().add(x, y, z).getBlock();
                    if(block!=null && !blocks.contains(block) && blocksAutorise.contains(block.getType())) {
                        blocks.add(block);
                    }
                }
            }
        }

        return blocks;
    }

    public Vector<Block> getTree(Block depart) {
        Vector<Material> blocksautorise = new Vector<Material>();
        blocksautorise.add(Material.LOG);
        blocksautorise.add(Material.LEAVES);
        blocksautorise.add(Material.LOG_2);
        blocksautorise.add(Material.LEAVES_2);
        return getBlockAlentour(depart, blocksautorise);
    }
}
