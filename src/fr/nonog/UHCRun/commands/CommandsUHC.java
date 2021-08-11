package fr.nonog.UHCRun.commands;

import fr.nonog.UHCRun.UHCRun;
import fr.nonog.UHCRun.tasks.StartTimer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Random;

public class CommandsUHC implements CommandExecutor {

    private UHCRun main;
    private TreeMap<String, Vector<Player>> teams;
    private ScoreboardManager scoreboardManager;
    private TreeMap<String, Team> scoreboardTeams;
    private Scoreboard newSC;

    public CommandsUHC(UHCRun main) {
        this.main = main;
        teams = new TreeMap<String, Vector<Player>>();
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboardTeams = new TreeMap<String, Team>();
        newSC = main.getScoreBoard().getScoreBoard();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        boolean exec = false;

        if(command.getName().equalsIgnoreCase("UHC")) {





            if(strings.length >= 1 && strings[0].equalsIgnoreCase("team")) {


                if (strings.length >= 2) {
                    if(strings[1].equalsIgnoreCase("create") && strings.length >= 3) {
                        commandSender.sendMessage("[UHC] Team "+strings[2]+" created !");
                        teams.put(strings[2], new Vector<Player>());


                        if(strings.length >= 4) {
                            Team t = newSC.registerNewTeam(strings[2]);

                            switch (strings[3]) {
                                case "RED":
                                    t.setPrefix(ChatColor.RED.toString());
                                    break;
                                case "BLUE":
                                    t.setPrefix(ChatColor.BLUE.toString());
                                    break;
                                case "GREEN":
                                    t.setPrefix(ChatColor.GREEN.toString());
                                    break;
                                case "GOLD":
                                    t.setPrefix(ChatColor.GOLD.toString());
                                    break;
                                case "WHITE":
                                    t.setPrefix(ChatColor.WHITE.toString());
                                    break;
                                case "YELLOW":
                                    t.setPrefix(ChatColor.YELLOW.toString());
                                    break;
                                case "AQUA":
                                    t.setPrefix(ChatColor.AQUA.toString());
                                    break;
                                case "PURPLE":
                                    t.setPrefix(ChatColor.LIGHT_PURPLE.toString());
                                    break;
                            }
                            scoreboardTeams.put(strings[2], t);

                        }
                        else{
                            scoreboardTeams.put(strings[2], newSC.registerNewTeam(strings[2]));
                        }

                    }
                    else if(strings[1].equalsIgnoreCase("join") && strings.length >= 4) {
                        Player joiner = Bukkit.getPlayer(strings[3]);
                        try{
                            teams.get(strings[2]).add(joiner);
                            scoreboardTeams.get(strings[2]).addEntry(joiner.getDisplayName());
                            commandSender.sendMessage("[UHC] - "+joiner.getDisplayName()+" add to "+ strings[2]);
                        }
                        catch(Exception e) {
                            commandSender.sendMessage(ChatColor.RED + "[UHC] Error : use /uhc team join [team] [player]");
                            commandSender.sendMessage("An error has occurred. Please look at the console");
                            main.getServer().getConsoleSender().sendMessage(e.getMessage());
                        }
                    }
                    else if(strings[1].equalsIgnoreCase("leave") && strings.length >= 4) {
                        try{
                            Player leaver = Bukkit.getPlayer(strings[3]);
                            if(leaver==null) {
                                leavePlayerTeamByName(strings[3]);
                            }
                            else{
                                teams.get(strings[2]).remove(leaver);
                                scoreboardTeams.get(strings[2]).removeEntry(leaver.getDisplayName());
                                commandSender.sendMessage("[UHC] - "+leaver.getDisplayName()+" left "+ strings[2]);
                                if(teams.get(strings[2]).size() == 0) {
                                    teams.remove(strings[2]);
                                    scoreboardTeams.get(strings[2]).unregister();
                                    scoreboardTeams.remove(strings[2]);
                                    Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - Team "+strings[2]+" has been eliminated (empty team)");
                                }
                            }

                        }
                        catch (Exception e) {
                            commandSender.sendMessage(ChatColor.RED + "[UHC] Error : use /uhc team leave [team] [player]");
                            commandSender.sendMessage("An error has occurred. Please look at the console");
                            main.getServer().getConsoleSender().sendMessage(e.getMessage());

                        }

                    }
                    else if(strings[1].equalsIgnoreCase("list")) {
                        commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                        commandSender.sendMessage(ChatColor.GOLD + "-- UHC - List of Teams --");
                        for(String team : teams.keySet()) {
                            commandSender.sendMessage(ChatColor.GOLD + "---------------------");
                            commandSender.sendMessage(ChatColor.GREEN + "Team :  "+team);
                            commandSender.sendMessage(ChatColor.GOLD + "Players");
                            for(Player pl : teams.get(team)) {
                                if(teams.get(team).size() > 0) {
                                    commandSender.sendMessage(pl.getName());
                                }

                            }
                            commandSender.sendMessage(ChatColor.GOLD + "---------------------");
                        }
                    }
                    else if(strings[1].equalsIgnoreCase("del") && strings.length >=3) {
                        try{
                            teams.remove(strings[2]);
                            commandSender.sendMessage("[UHC] - you deleted "+strings[2]+" team");
                            scoreboardTeams.get(strings[2]).unregister();
                            scoreboardTeams.remove(strings[2]);
                        }catch (Exception e) {
                            commandSender.sendMessage("[UHC] Error : It seems that this team does not exist");
                            main.getServer().getConsoleSender().sendMessage(e.getMessage());
                        }

                    }
                    else{
                        commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                        commandSender.sendMessage(ChatColor.GOLD + "-- UHC - List of Team Commands --");
                        commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team create [TeamName]" + ChatColor.WHITE +" : create a team with the given name");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team create [TeamName] [COLOR]" + ChatColor.WHITE +" : create a team with a color (In capital letters) e.g. : uhc team create example RED");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team join [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player joins the indicated team");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team leave [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player leaves the indicated team");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team del [TeamName]" + ChatColor.WHITE +" : delete the indicated team");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team list" + ChatColor.WHITE +" : get the list of teams");
                        commandSender.sendMessage(ChatColor.RED + "[UHC] - The command you entered does not follow the syntax above. Please check");
                    }
                }



            }
            else if(strings.length >= 1 && strings[0].equalsIgnoreCase("start")){
                if (strings.length >= 2) {
                    if (strings[1].equalsIgnoreCase("team")) {
                        Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - The game is about to start");
                        StartTimer startTimer = new StartTimer(main);
                        startTimer.runTaskTimer(main, 0, 20);

                    }
                    if (strings[1].equalsIgnoreCase("single")) {
                        Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - The game is about to start");
                        createSoloTeams();
                        StartTimer startTimer = new StartTimer(main);
                        startTimer.runTaskTimer(main, 0, 20);

                    }
                }
                else{
                    commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                    commandSender.sendMessage(ChatColor.GOLD + "-- UHC - List of Start Commands --");
                    commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                    commandSender.sendMessage(ChatColor.GOLD + "/uhc start team" + ChatColor.WHITE +" : start the game respecting the teams you have previously created");
                    commandSender.sendMessage(ChatColor.GOLD + "/uhc start single" + ChatColor.WHITE +" : start the game in a team of 1 player");

                }
            }
            else if(strings.length >= 1 && strings[0].equalsIgnoreCase("generate")) {

                if(main.getConfigur().getBoolean("run.generation.increaseOreGeneration")) {
                    Bukkit.broadcastMessage(ChatColor.RED+""+ChatColor.BOLD+"[UHC] - Warning ! Generation of additional minerals ... This will generate a lot of lag (Beta feature)");

                    int xm = (int)(main.getConfigur().getInt("game.map-size") /2);
                    int zm = (int)(main.getConfigur().getInt("game.map-size") /2);
                    World w = Bukkit.getWorld(main.getConfigur().getString("game.map"));
                    for(int x=(-xm); x<=(xm); x++) {
                        for (int z = (-zm); z <= (zm); z++) {
                            for (int y = 1; y < 65; y++) {
                                Block b = w.getBlockAt(x, y, z);
                                if(b.getType().equals(Material.STONE)) {
                                    int r = (int)(Math.random()*2000);
                                    if(r<2) {
                                        b.setType(Material.DIAMOND_ORE);
                                    }
                                    if(r>2 && r<6) {
                                        b.setType(Material.IRON_ORE);
                                    }
                                }
                            }
                        }
                    }
                    Bukkit.broadcastMessage(ChatColor.GREEN+""+ChatColor.BOLD+"[UHC] - Done");



                }

            }
            else{
                commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                commandSender.sendMessage(ChatColor.GOLD + "-- UHC - List of Commands --");
                commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team create [TeamName]" + ChatColor.WHITE +" : create a team with the given name");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team create [TeamName] [COLOR]" + ChatColor.WHITE +" : create a team with a color (In capital letters) e.g. : uhc team create example RED");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team join [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player joins the indicated team");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team leave [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player leaves the indicated team");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team del [TeamName]" + ChatColor.WHITE +" : delete the indicated team");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team list" + ChatColor.WHITE +" : get the list of teams");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc start [Team/Single]" + ChatColor.WHITE +" : start the game");
                commandSender.sendMessage(ChatColor.GOLD + "/t [MSG]" + ChatColor.WHITE +" : communicate with other 'Taupes'");



            }


        }


        //Taupe chat :
        if(command.getName().equalsIgnoreCase("t")) {
            if(commandSender instanceof Player) {
                Player p = (Player) commandSender;

                try{
                    if(teams.get("TAUPES").contains(p)) {
                        String msg = "[TAUPES] - ["+p.getDisplayName()+"]";

                        for(int i=0; i<strings.length; i++) {
                            msg+=" "+strings[i];
                        }
                        for(Player pl : teams.get("TAUPES")) {
                            pl.sendMessage(ChatColor.RED+msg);
                        }
                    }
                }
                catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"UHCRun]"+e.getMessage());
                    p.sendMessage(ChatColor.RED+"Error, you are not a Taupe !");
                }


            }
        }


        return exec;
    }

    public boolean deleteAllScoreBoardTeams() {
        boolean result = false;
        for(String teamName : scoreboardTeams.keySet()) {
            try{
                scoreboardTeams.get(teamName).unregister();
                newSC = null;
                result = true;
            }catch (Exception e) {
                main.getServer().getConsoleSender().sendMessage(e.getMessage());
            }

        }
        return result;

    }
    public boolean leavePlayerTeam(Player p) {
        boolean result = false;
        Iterator<String> namesTeam = teams.keySet().iterator();
        while(namesTeam.hasNext()) {
            String nameTeam = namesTeam.next();
        //for(String nameTeam : teams.keySet()) {
            //for(Player pl : teams.get(nameTeam)) {
            Iterator<Player> itr = teams.get(nameTeam).iterator();
            while(itr.hasNext()) {
                Player pl = itr.next();
                if(p.equals(pl)) {
                    try {
                        //teams.get(nameTeam).remove(p);
                        itr.remove();
                        scoreboardTeams.get(nameTeam).removeEntry(p.getDisplayName());
                    } catch (Exception e) {
                        main.getServer().getConsoleSender().sendMessage(e.getMessage());
                    }

                    result = true;

                    if(teams.get(nameTeam).size() == 0) {
                        //teams.remove(nameTeam);
                        namesTeam.remove();
                        if(scoreboardTeams.get(nameTeam)!=null) {
                            scoreboardTeams.get(nameTeam).unregister();
                            scoreboardTeams.remove(nameTeam);
                        }

                        Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - Team "+nameTeam+" has been eliminated (empty team)");
                    }
                }
            }
        }
        return result;
    }

    public boolean leavePlayerTeamWithoutSC(Player p) {
        boolean result = false;
        Iterator<String> namesTeam = teams.keySet().iterator();
        while(namesTeam.hasNext()) {
            String nameTeam = namesTeam.next();
            //for(String nameTeam : teams.keySet()) {
            //for(Player pl : teams.get(nameTeam)) {
            Iterator<Player> itr = teams.get(nameTeam).iterator();
            while(itr.hasNext()) {
                Player pl = itr.next();
                if(p.equals(pl)) {
                    try {
                        //teams.get(nameTeam).remove(p);
                        itr.remove();
                        //scoreboardTeams.get(nameTeam).removeEntry(p.getDisplayName());
                    } catch (Exception e) {
                        main.getServer().getConsoleSender().sendMessage(e.getMessage());
                    }

                    result = true;

                    if(teams.get(nameTeam).size() == 0) {
                        //teams.remove(nameTeam);
                        namesTeam.remove();
                        if(scoreboardTeams.get(nameTeam)!=null) {
                            scoreboardTeams.get(nameTeam).unregister();
                            scoreboardTeams.remove(nameTeam);
                        }

                        Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - Team "+nameTeam+" has been eliminated (empty team)");
                    }
                }
            }
        }
        return result;
    }


    public boolean leavePlayerTeamByName(String name) {
        boolean result = false;
        Iterator<String> namesTeam = teams.keySet().iterator();
        while(namesTeam.hasNext()) {
            String nameTeam = namesTeam.next();

            Iterator<Player> itr = teams.get(nameTeam).iterator();
            while(itr.hasNext()) {
                Player pl = itr.next();
                if(pl.getDisplayName().equals(name)) {
                    try {
                        itr.remove();
                        scoreboardTeams.get(nameTeam).removeEntry(pl.getDisplayName());
                        Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - Player "+name+" has been removed from his team");
                    } catch (Exception e) {
                        main.getServer().getConsoleSender().sendMessage(e.getMessage());
                    }

                    result = true;

                    if(teams.get(nameTeam).size() == 0) {
                        namesTeam.remove();
                        scoreboardTeams.get(nameTeam).unregister();
                        scoreboardTeams.remove(nameTeam);
                        Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - Team "+nameTeam+" has been eliminated (empty team)");
                    }
                }
            }
        }
        return result;
    }

    public int getNBTeams() {
        int compt = 0;
        for(String s : teams.keySet()) {
            compt++;
        }
        return compt;
    }

    public void printWinner() {
        for(String s : teams.keySet()) {
            Bukkit.broadcastMessage(ChatColor.GREEN+"[UHC] - The team "+s+" won the game!");
            Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - Winners : ");
            for(Player pl : teams.get(s)) {
                Bukkit.broadcastMessage(ChatColor.GOLD+" - "+pl.getDisplayName());
            }
        }
    }
    public void deleteEmptyTeams() {
        Iterator<String> namesTeam = teams.keySet().iterator();
        while(namesTeam.hasNext()) {
            String nameTeam = namesTeam.next();
        //for(String nameTeam : teams.keySet()) {
            if(teams.get(nameTeam).size() == 0) {
                namesTeam.remove();
                //teams.remove(nameTeam);
                try{
                    scoreboardTeams.get(nameTeam).unregister();
                    scoreboardTeams.remove(nameTeam);
                }
                catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"[UHCRun] - "+e.getMessage());
                }

                Bukkit.broadcastMessage(ChatColor.GOLD+"[UHC] - Team "+nameTeam+" has been eliminated (empty team)");
            }
        }
    }
    public boolean isInATeam(Player p) {
        boolean b = false;
        for(String s : teams.keySet()) {
            for(Player pl : teams.get(s)) {
                if(pl.getDisplayName().equals(p.getDisplayName())) {
                    b = true;
                }
            }
        }
        return b;
    }


    public void selectionTaupe() {
        teams.put("TAUPES", new Vector<Player>());
        Iterator<String> namesTeam = teams.keySet().iterator();
        while(namesTeam.hasNext()) {
            String nameTeam = namesTeam.next();
            Vector<Player> t = null;
            if(!nameTeam.equals("TAUPES")) {
                t = teams.get(nameTeam);
                if(t.size()>0) {
                    Random random = new Random();

                    int r = random.nextInt(t.size());
                    Player taupe = t.get(r);
                    leavePlayerTeamWithoutSC(taupe);
                    teams.get("TAUPES").add(taupe);
                }
            }




        }
        for(Player pl : teams.get("TAUPES")) {
            pl.sendMessage(ChatColor.RED+"[UHC] - You are a Taupe (a traitor) you must betray your team and win with the other Taupes");
            pl.sendMessage(ChatColor.RED+"[UHC] - use '/t' to communicate with other Taupes");
            pl.sendMessage(ChatColor.RED+"[UHC] - Here is the list of TAUPES : ");
            for(Player pla : teams.get("TAUPES")) {
                pl.sendMessage(ChatColor.RED+"- "+pla.getDisplayName());
            }
            pl.sendMessage(ChatColor.RED+"----------");

        }


    }
    public void spreedPlayers() {

        for(String s : teams.keySet()) {
            int maxRange = (main.getConfigur().getInt("game.map-size") /2);
            Random random = new Random();
            int x = -maxRange+random.nextInt(maxRange-(-maxRange));
            int z = -maxRange+random.nextInt(maxRange-(-maxRange));


            for (Player pl : teams.get(s)) {
                pl.teleport(new Location(pl.getWorld(), x, pl.getWorld().getHighestBlockYAt(x, z)+5, z));

            }
        }
    }
    public void deleteAllTeams() {
        deleteEmptyTeams();
        deleteAllScoreBoardTeams();
        teams.clear();
        /*
        Iterator<String> namesTeam = teams.keySet().iterator();
        while(namesTeam.hasNext()) {
            namesTeam.remove();

        }*/
    }
    public void createSoloTeams() {
        deleteAllTeams();
        int i=0;
        for(Player p : Bukkit.getOnlinePlayers()) {
            teams.put("UHCTeamSolo"+i, new Vector<Player>());
            teams.get("UHCTeamSolo"+i).add(p);
            i++;
        }
    }
}
