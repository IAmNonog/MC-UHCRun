package fr.nonog.UHCRun.commands;

import fr.nonog.UHCRun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

public class CommandsUHC implements CommandExecutor {

    private UHCRun main;
    private TreeMap<String, Vector<Player>> teams;
    private ScoreboardManager scoreboardManager;
    private TreeMap<String, Team> scoreboardTeams;

    public CommandsUHC(UHCRun main) {
        this.main = main;
        teams = new TreeMap<String, Vector<Player>>();
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboardTeams = new TreeMap<String, Team>();

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
                        scoreboardTeams.put(strings[2], scoreboardManager.getMainScoreboard().registerNewTeam(strings[2]));

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
                            teams.get(strings[2]).remove(leaver);
                            scoreboardTeams.get(strings[2]).removeEntry(leaver.getDisplayName());
                            commandSender.sendMessage("[UHC] - "+leaver.getDisplayName()+" left "+ strings[2]);
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
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team join [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player joins the indicated team");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team leave [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player leaves the indicated team");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team del [TeamName]" + ChatColor.WHITE +" : delete the indicated team");
                        commandSender.sendMessage(ChatColor.GOLD + "/uhc team list" + ChatColor.WHITE +" : get the list of teams");
                        commandSender.sendMessage(ChatColor.RED + "[UHC] - The command you entered does not follow the syntax above. Please check");
                    }
                }



            }
            else{
                commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                commandSender.sendMessage(ChatColor.GOLD + "-- UHC - List of Commands --");
                commandSender.sendMessage(ChatColor.GREEN + "---------------------");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team create [TeamName]" + ChatColor.WHITE +" : create a team with the given name");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team join [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player joins the indicated team");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team leave [TeamName] [PlayerName]" + ChatColor.WHITE +" : the indicated player leaves the indicated team");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team del [TeamName]" + ChatColor.WHITE +" : delete the indicated team");
                commandSender.sendMessage(ChatColor.GOLD + "/uhc team list" + ChatColor.WHITE +" : get the list of teams");



            }


        }

        return exec;
    }

    public boolean deleteAllScoreBoardTeams() {
        boolean result = false;
        for(String teamName : scoreboardTeams.keySet()) {
            try{
                scoreboardTeams.get(teamName).unregister();
                result = true;
            }catch (Exception e) {
                main.getServer().getConsoleSender().sendMessage(e.getMessage());
            }

        }
        return result;

    }
    public boolean leavePlayerTeam(Player p) {
        boolean result = false;
        for(String nameTeam : teams.keySet()) {
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
                        teams.remove(nameTeam);
                        scoreboardTeams.get(nameTeam).unregister();
                    }
                }
            }
        }
        return result;
    }
}
