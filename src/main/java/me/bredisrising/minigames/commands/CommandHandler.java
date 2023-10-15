package me.bredisrising.minigames.commands;// ^ remove before submitting!

import me.bredisrising.minigames.Minigames;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandHandler implements CommandExecutor {
    private Minigames plugin;

    public CommandHandler(Minigames plugin){
        this.plugin = plugin;

        plugin.getCommand("hunter").setExecutor(new HunterCommand());
        plugin.getCommand("startManhunt").setExecutor(new StartManhuntCommand());
        plugin.getCommand("resetManhunt").setExecutor(new ResetManhuntCommand());
        plugin.getCommand("runner").setExecutor(new RunnerCommand());
        plugin.getCommand("makeworld").setExecutor(new MakeWorldCommand());

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
