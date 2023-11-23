package me.bredisrising.minigames;// ^ remove before submitting!

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
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

    public static Manhunt instance;

    public Manhunt(Minigames plugin){
        this.plugin = plugin;
    }

    public Manhunt(Minigames plugin, boolean start){
        this.plugin = plugin;

        Bukkit.broadcastMessage("Starting Compass Modifier");
        startCompassModifier();


    }

    void startCompassModifier(){
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        int taskID = scheduler.scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override
            public void run() {

                for(String playerName : Minigames.hunterNames.keySet()){
                    Player p = Bukkit.getPlayerExact(playerName);

                    ItemStack compass = p.getInventory().getItem(Minigames.compasses.get(playerName));
                    if(compass == null || !compass.getType().equals(Material.COMPASS)){

                        for (int inventoryIndex = 0; inventoryIndex < p.getInventory().getSize(); inventoryIndex++){
                            if(p.getInventory().getItem(inventoryIndex) != null && p.getInventory().getItem(inventoryIndex).getType().equals(Material.COMPASS)){
                                Minigames.compasses.put(playerName, inventoryIndex);
                                compass = p.getInventory().getItem(Minigames.compasses.get(playerName));
                            }
                        }
                    }

                    if(Minigames.theRunner.getWorld().getName().equalsIgnoreCase("manhunt_world")){
                        if(p.getWorld().getName().equalsIgnoreCase("manhunt_world")){

                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.theRunner.getLocation());

                            compass.setItemMeta(compassMeta);

                            Bukkit.broadcastMessage("compass is " + compass.getType().toString());


                        }else if(p.getWorld().getName().equalsIgnoreCase("manhunt_nether")) {

                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerNetherPortalLocation);

                            compass.setItemMeta(compassMeta);

                        }
                    }else if(Minigames.theRunner.getWorld().getName().equalsIgnoreCase("manhunt_nether")){
                        if(p.getWorld().getName().equalsIgnoreCase("manhunt_world")){

                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerOverworldPortalLocation);

                            compass.setItemMeta(compassMeta);


                        }else if(p.getWorld().getName().equalsIgnoreCase("manhunt_nether")) {

                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.theRunner.getLocation());

                            compass.setItemMeta(compassMeta);

                        }
                    }else{
                        if(p.getWorld().getName().equalsIgnoreCase("manhunt_nether")){

                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerNetherPortalLocation);

                            compass.setItemMeta(compassMeta);
//                            p.setCompassTarget(Minigames.lastRunnerNetherPortalLocation);

                        }else if(p.getWorld().getName().equalsIgnoreCase("manhunt_end")){

                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.theRunner.getLocation());

                            compass.setItemMeta(compassMeta);
//                            p.setCompassTarget(Minigames.theRunner.getLocation());

                        }else{

                            CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
                            compassMeta.setLodestoneTracked(false);
                            compassMeta.setLodestone(Minigames.lastRunnerEndPortalLocation);

                            compass.setItemMeta(compassMeta);
//                            p.setCompassTarget(Minigames.lastRunnerEndPortalLocation);
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
            Minigames.compasses.put(e.getPlayer().getName(), 8);
        }
    }

    @EventHandler
    public void onRunnerPortal(PlayerPortalEvent e){
        if(e.getPlayer().getName().equals(Minigames.theRunner.getName())){
            if(e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL){
                if(e.getPlayer().getWorld().getName().equalsIgnoreCase("manhunt_world")){
                    Minigames.lastRunnerOverworldPortalLocation = e.getPlayer().getLocation();
                }else if(e.getPlayer().getWorld().getName().equalsIgnoreCase("manhunt_nether")){
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
