package me.bredisrising.minigames.commands;// ^ remove before submitting!

import me.bredisrising.minigames.Minigames;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
                    Location loc = new Location(Bukkit.getWorld("TempWorld"), 0, Bukkit.getWorld("TempWorld").getHighestBlockYAt(0,0), 0);
                    p.teleport(loc);
                }

                for(World w : Bukkit.getWorlds()){
                    if(!w.getName().contains("TempWorld")){
                        File f = w.getWorldFolder();
                        Bukkit.broadcastMessage(w.getName()+" "+String.valueOf(Bukkit.unloadWorld(w, false)));
                        Bukkit.getWorlds().remove(w);

                        Bukkit.broadcastMessage("deleted: " + deleteWorld(f));
                    }
                }



                Minigames.theRunner = null;
                Minigames.runnerName = null;
                Minigames.hunterNames.clear();


                World world;
                World end;
                World nether;

                long seed = new Random().nextLong();

                WorldCreator worldCreator = new WorldCreator("world");
                worldCreator.seed(seed);
                Bukkit.getWorlds().add(Bukkit.getServer().createWorld(worldCreator));

                worldCreator = new WorldCreator("world_the_end");
                worldCreator.environment(World.Environment.THE_END);
                worldCreator.seed(seed);
                Bukkit.getWorlds().add(Bukkit.getServer().createWorld(worldCreator));

                worldCreator = new WorldCreator("world_nether");
                worldCreator.environment(World.Environment.NETHER);
                worldCreator.seed(seed);
                Bukkit.getWorlds().add(Bukkit.getServer().createWorld(worldCreator));

                for(Player p : Bukkit.getOnlinePlayers()){
                    Location loc = new Location(Bukkit.getWorld("world"), Bukkit.getWorld("world").getSpawnLocation().getX(), Bukkit.getWorld("world").getSpawnLocation().getY(), Bukkit.getWorld("world").getSpawnLocation().getZ());
                    p.teleport(loc);
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


