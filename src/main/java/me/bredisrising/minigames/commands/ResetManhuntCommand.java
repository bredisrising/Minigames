package me.bredisrising.minigames.commands;// ^ remove before submitting!

import me.bredisrising.minigames.Manhunt;
import me.bredisrising.minigames.Minigames;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.BufferedReader;
import java.io.File;
import java.util.*;

public class ResetManhuntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(true){
            if(sender.hasPermission("resetmanhunt.use")){

                Bukkit.broadcastMessage("resetting!");

                for(Player p : Bukkit.getOnlinePlayers()){
                    Location loc = new Location(Bukkit.getWorld("world"), 0, Bukkit.getWorld("world").getHighestBlockYAt(0,0), 0);
                    p.teleport(loc);
                }


                Bukkit.broadcastMessage("Number of Worlds " + Bukkit.getWorlds().size());
                for(World world : Bukkit.getWorlds()){
                    Bukkit.broadcastMessage(world.getName());
                }

                Bukkit.broadcastMessage("");

                for(int i = 0; i < Bukkit.getWorlds().size(); i++){
                    World world = Bukkit.getWorlds().get(i);
                    if(world.getName().contains("manhunt")){
                        Bukkit.broadcastMessage("deleting: " + world.getName());

                        //check unload cases

                        Bukkit.broadcastMessage("is null: " + String.valueOf(world == null));
                        Bukkit.broadcastMessage("player count: " + world.getPlayers().size());

                        for (Chunk chunk : world.getLoadedChunks()) {
                            chunk.unload();
                        }
                        Bukkit.broadcastMessage(world.getName()+" "+String.valueOf(Bukkit.unloadWorld(world, false)));
                        File folder = world.getWorldFolder();
                        Bukkit.getWorlds().remove(world);
                        Bukkit.broadcastMessage("deleted: " + deleteWorld(folder) + "\n");
                        i--;
                    }
                }



                Minigames.theRunner = null;
                Minigames.runnerName = null;
                Minigames.hunterNames.clear();

                long seed = new Random().nextLong();

                WorldCreator worldCreator = new WorldCreator("manhunt_world");
                worldCreator.seed(seed);
                Bukkit.getWorlds().add(Bukkit.getServer().createWorld(worldCreator));

                worldCreator = new WorldCreator("manhunt_end");
                worldCreator.environment(World.Environment.THE_END);
                worldCreator.seed(seed);
                Bukkit.getWorlds().add(Bukkit.getServer().createWorld(worldCreator));

                worldCreator = new WorldCreator("manhunt_nether");
                worldCreator.environment(World.Environment.NETHER);
                worldCreator.seed(seed);
                Bukkit.getWorlds().add(Bukkit.getServer().createWorld(worldCreator));

                for(Player p : Bukkit.getOnlinePlayers()){
                    Location loc = new Location(Bukkit.getWorld("manhunt_world"), Bukkit.getWorld("manhunt_world").getSpawnLocation().getX(), Bukkit.getWorld("manhunt_world").getSpawnLocation().getY(), Bukkit.getWorld("manhunt_world").getSpawnLocation().getZ());

                    Bukkit.broadcastMessage(loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ());

                    //p.teleport(loc);
                    p.setBedSpawnLocation(loc, true);

                    p.getInventory().clear();
                    p.setTotalExperience(0);
                    p.setExp(0);


                    Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
                    while(iterator.hasNext()){
                        AdvancementProgress progress = p.getAdvancementProgress(iterator.next());
                        for(String criteria : progress.getAwardedCriteria())
                            progress.revokeCriteria(criteria);
                    }
                }

                Manhunt.instance = null;

                for (Player player : Bukkit.getOnlinePlayers()){
                    Bukkit.broadcastMessage(player.getDisplayName() + " " + player.getWorld().getName());
                }


            }else{
                Bukkit.broadcastMessage("dont have permission!");
            }
        }

        return true;
    }

    public boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }
}


