package me.bredisrising.minigames.commands;// ^ remove before submitting!

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class MakeWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("makeworld.use")){
            Bukkit.broadcastMessage("Removed as not needed.");
//            WorldCreator wc = new WorldCreator("TempWorld");
//            wc.seed(69420);
//            wc.environment(World.Environment.NORMAL);
//            wc.createWorld();
        }

        return false;
    }
}
