package me.bredisrising.minigames;// ^ remove before submitting!

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class Manhunt implements Listener {
    private Minigames plugin;

    public Manhunt(Minigames plugin){
        this.plugin = plugin;
    }

    public Manhunt(Minigames plugin, boolean start){
        this.plugin = plugin;
        startCompassModifier();
    }

    void startCompassModifier(){
        World world = Bukkit.getWorld("world");
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        int taskID = scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                for(String playerName : Minigames.hunterNames.keySet()){
                    Player p = Bukkit.getPlayerExact(playerName);
                    if(Minigames.theRunner.getWorld().getName().equalsIgnoreCase("world")){
                        if(p.getWorld().getName().equalsIgnoreCase("world")){
                            //p.setCompassTarget(Minigames.theRunner.getLocation());

                            ItemStack compass = p.getInventory().getItem(8);
                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.theRunner.getLocation());

                            p.getInventory().getItem(8).setItemMeta(compassMeta);


                        }else if(p.getWorld().getName().equalsIgnoreCase("world_nether")) {
                            ItemStack compass = p.getInventory().getItem(8);
                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerNetherPortalLocation);

                            p.getInventory().getItem(8).setItemMeta(compassMeta);
                        }
                    }else if(Minigames.theRunner.getWorld().getName().equalsIgnoreCase("world_nether")){
                        if(p.getWorld().getName().equalsIgnoreCase("world")){
                            ItemStack compass = p.getInventory().getItem(8);
                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerOverworldPortalLocation);

                            p.getInventory().getItem(8).setItemMeta(compassMeta);
                        }else if(p.getWorld().getName().equalsIgnoreCase("world_nether")) {
                            ItemStack compass = p.getInventory().getItem(8);
                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.theRunner.getLocation());

                            p.getInventory().getItem(8).setItemMeta(compassMeta);

                        }
                    }else{
                        if(p.getWorld().getName().equalsIgnoreCase("world_nether")){
                            ItemStack compass = p.getInventory().getItem(8);
                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerNetherPortalLocation);

                            p.getInventory().getItem(8).setItemMeta(compassMeta);
                        }else if(p.getWorld().getName().equalsIgnoreCase("world_the_end")){
                            ItemStack compass = p.getInventory().getItem(8);
                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.theRunner.getLocation());

                            p.getInventory().getItem(8).setItemMeta(compassMeta);
                        }else{
                            ItemStack compass = p.getInventory().getItem(8);
                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerEndPortalLocation);

                            p.getInventory().getItem(8).setItemMeta(compassMeta);
                        }
                    }



                }
            }
        }, 0L, (long)(5));
    }

    @EventHandler
    public void onHunterSpawn(PlayerRespawnEvent e){
        if(Minigames.hunterNames.containsKey(e.getPlayer().getName())){
            ItemStack compass = new ItemStack(Material.COMPASS);
            e.getPlayer().getInventory().setItem(8,compass);
            Minigames.hunterNames.put(e.getPlayer().getName(), 0);

        }
    }

    @EventHandler
    public void onRunnerPortal(PlayerPortalEvent e){
        if(e.getPlayer().getName().equals(Minigames.theRunner.getName())){
            if(e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL){
                if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world")){
                    Minigames.lastRunnerOverworldPortalLocation = e.getPlayer().getLocation();
                }else if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world_nether")){
                    Minigames.lastRunnerNetherPortalLocation = e.getPlayer().getLocation();
                }
            }else if(e.getCause()==PlayerTeleportEvent.TeleportCause.END_PORTAL){
                Minigames.lastRunnerEndPortalLocation = e.getPlayer().getLocation();
            }
        }
    }

    /*@EventHandler
    public void onInvInteract(InventoryClickEvent e ){
        if(Minigames.huntersCompass.containsKey(e.getWhoClicked())){
            if(e.getSlot()==8){
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        if(Minigames.huntersCompass.containsKey(e.getPlayer())&&e.getMainHandItem().getType().equals(Material.COMPASS)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(Minigames.huntersCompass.containsKey(e.getPlayer())){
            if(e.getItemDrop().getItemStack().getType().equals(Material.COMPASS)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(Minigames.huntersCompass.containsKey(e.getPlayer())){
            if(e.getItem().getType().equals(Material.COMPASS)){
                if(e.getPlayer().getInventory().getItem(8).getType().equals(Material.COMPASS)){
                    e.setCancelled(true);
                }
            }
        }
    }*/

}
