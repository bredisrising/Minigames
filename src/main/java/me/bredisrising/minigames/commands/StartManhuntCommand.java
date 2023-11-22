package me.bredisrising.minigames.commands;// ^ remove before submitting!

import me.bredisrising.minigames.Manhunt;
import me.bredisrising.minigames.Minigames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class StartManhuntCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            if(sender.hasPermission("startmanhunt.use")){
                Manhunt.instance = new Manhunt(Minigames.minigamesPlugin, true);
            }
        }

        return true;
    }
}
