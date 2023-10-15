package me.bredisrising.minigames.commands;// ^ remove before submitting!

import me.bredisrising.minigames.Minigames;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class RunnerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player)sender;
            p.sendMessage(ChatColor.GOLD+"YOU ARE NOW THE RUNNER! GOOD LUCK!");

            Minigames.theRunner = p;

        }

        return true;
    }
}
