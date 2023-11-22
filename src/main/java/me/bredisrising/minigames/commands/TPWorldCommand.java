package me.bredisrising.minigames.commands;

import me.bredisrising.minigames.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player)sender;
            p.teleport(new Location(Bukkit.getWorld(args[0]), 0, Bukkit.getWorld(args[0]).getHighestBlockYAt(0, 0), 0));
            p.sendMessage("in world:" + p.getWorld().getName());
        }
        return true;
    }

}
