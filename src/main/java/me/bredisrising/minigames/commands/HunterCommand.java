package me.bredisrising.minigames.commands;// ^ remove before submitting!

import me.bredisrising.minigames.Minigames;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HunterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player cmdSender = (Player)sender;
            cmdSender.sendMessage(ChatColor.GOLD+"YOU ARE NOW A HUNTER!");

            //Minigames.hunters.put(cmdSender.getName(), cmdSender);
            ItemStack compass = new ItemStack(Material.COMPASS);

            cmdSender.getInventory().setItem(8, compass);

            Minigames.hunterNames.put(cmdSender.getName(), 0);

        }

        return true;
    }
}
