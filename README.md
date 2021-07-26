# MC-UHCRun
Minecraft Spigot plugin 1.8 (Add-on for Minecraft Java server) 

UHC configurable [ UHC - UHC Run - Taupe Run - Taupe Gun ]

_______________________________________________

Minecraft version : 1.8
(New versions will be available soon)

this plugin is a customizable UHC Game. Depending on the parameters enabled or not, you can play a UHC CLASSIC, a UHC RUN, a TAUPE GUN, a TAUPE RUN.
To change between these different games you can change the different settings in config.yml  

NB: a TAUPE GUN is a UHC but an intruder is hiding in each team. This one receives a message at a certain moment of the game to warn him. He then forms a team with the other intruders and must betray his team. He must therefore avoid being spotted by his team  

type "/uhc" to get the list of commands  

_______________________________________________
# Configure a game

play without a team: just enter "/uhc start single"  

play as a team:
  * create a team: /uhc team create [nameTeam] or /uhc team create [nameTeam] [COLOR]  
                 available colors : RED, BLUE, GREEN, GOLD, WHITE, YELLOW, AQUA, PURPLE  
  * join a team:   /uhc team join [nameTeam] [namePlayer]  
  * launch:        /uhc start team  
  
  
________________________________________________
# Course of the game
  
  The phases are executed in the following order:  
     1: Time before TAUPES announcement (if TAUPE option enabled)  
     2: Time before map reduction (if GradualReduction option activated)  
     3: Reduction of the map (if GradualReduction option activated) [the duration of this phase depends on the selected speed Block/seconds)  
     4: EndGame  
  If one of its phases is disabled (depending on the options chosen in this file) the game will go directly to the next phase  

  type "/uhc" to get the list of commands  
