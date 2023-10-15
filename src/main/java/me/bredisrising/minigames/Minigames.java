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

    @Override
    public void onEnable() {

        minigamesPlugin = this;

        Bukkit.getWorlds().add(getServer().createWorld(new WorldCreator("TempWorld")));

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Manhunt(this), this);

        hunterNames = new HashMap<>();

        new CommandHandler(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(p.getWorld().getName().contains("TempWorld")){
            Location loc = new Location(Bukkit.getWorld("world"), Bukkit.getWorld("world").getSpawnLocation().getX(), Bukkit.getWorld("world").getSpawnLocation().getY(), Bukkit.getWorld("world").getSpawnLocation().getZ());
            p.teleport(loc);
            p.setBedSpawnLocation(p.getLocation());
        }

        if(p.getName().equals(runnerName)){
            theRunner=p;
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if(e.getRespawnLocation().getWorld().getName().contains("TempWorld")){
            e.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
            e.getPlayer().setBedSpawnLocation(Bukkit.getWorld("world").getSpawnLocation(), true);
        }else{
            e.setRespawnLocation(e.getPlayer().getBedSpawnLocation());
        }

    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e){
        if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world")){
            if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){
                e.getTo().setWorld(Bukkit.getWorld("world_nether"));
            }
            if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
                e.getTo().setWorld(Bukkit.getWorld("world_the_end"));
            }
        }
        if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world_nether")){
            if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){
                e.getTo().setWorld(Bukkit.getWorld("world"));
            }
        }



    }

}
