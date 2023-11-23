package me.bredisrising.minigames;

import me.bredisrising.minigames.commands.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;

public class Minigames extends JavaPlugin implements Listener {

    public static Minigames minigamesPlugin;

    public static Player theRunner;
    public static Location lastRunnerOverworldPortalLocation;
    public static Location lastRunnerNetherPortalLocation;
    public static Location lastRunnerEndPortalLocation;

    public static String runnerName;
    public static HashMap<String, Integer> hunterNames;
    public static HashMap<String, Integer> compasses;

    @Override
    public void onEnable() {

        minigamesPlugin = this;


        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Manhunt(this), this);

        hunterNames = new HashMap<>();
        compasses = new HashMap<>();


        new CommandHandler(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(p.getWorld().getName().equals("world")){
            Location loc = new Location(Bukkit.getWorld("manhunt_world"), Bukkit.getWorld("manhunt_world").getSpawnLocation().getX(), Bukkit.getWorld("manhunt_world").getSpawnLocation().getY(), Bukkit.getWorld("manhunt_world").getSpawnLocation().getZ());
            p.teleport(loc);
            p.setBedSpawnLocation(p.getLocation());
        }

        if(p.getName().equals(runnerName)){
            theRunner=p;
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if(e.getRespawnLocation().getWorld().getName().equals("world")){
            e.setRespawnLocation(Bukkit.getWorld("manhunt_world").getSpawnLocation());
            e.getPlayer().setBedSpawnLocation(Bukkit.getWorld("manhunt_world").getSpawnLocation(), true);
        }else{
            e.setRespawnLocation(e.getPlayer().getBedSpawnLocation());
        }

    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e){
        if(e.getPlayer().getWorld().getName().equalsIgnoreCase("manhunt_world")){
            if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){
                e.getTo().setWorld(Bukkit.getWorld("manhunt_nether"));
            }
            if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
                e.getTo().setWorld(Bukkit.getWorld("manhunt_end"));
            }
        }
        if(e.getPlayer().getWorld().getName().equalsIgnoreCase("manhunt_nether")) {
            if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
                e.getTo().setWorld(Bukkit.getWorld("manhunt_world"));
            }
        }
    }
}
