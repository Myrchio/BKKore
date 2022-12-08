package it.blastmc.features.listener;

import it.blastmc.Main;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;


public class BlockTimedListener implements Listener {

    private final Plugin plugin = Main.getPlugin(Main.class);
    private final ArrayList<String> blocksTimed = new ArrayList<>(plugin.getConfig().getStringList("timed-blocks.blocks"));

    private final boolean displayHologram = plugin.getConfig().getBoolean("timed-blocks.hologram");
    private final float high = (float) plugin.getConfig().getDouble("timed-blocks.hologram-high");

    private Block getBlockAt(Location l) {
        return l.getWorld().getBlockAt(l);
    }

    private boolean isAir(Location l) {return getBlockAt(l).getType().equals(Material.AIR);}

    private ArrayList<Location> keepAirLocationNearCobweb(Block b){
        World l = b.getWorld();
        ArrayList<Location> newLocations = new ArrayList<>();
        Location center = b.getLocation();
        newLocations.add(center);
        Location front = new Location(l, center.getBlockX()+1, center.getBlockY(), center.getBlockZ());
        if (isAir(front)) {
            front.getBlock().setType(Material.WEB);
            newLocations.add(front);
        }
        Location behind = new Location(l, center.getBlockX()-1, center.getBlockY(), center.getBlockZ());
        if (isAir(behind)) {
            behind.getBlock().setType(Material.WEB);
            newLocations.add(behind);
        }
        Location right = new Location(l, center.getBlockX(), center.getBlockY(), center.getBlockZ()+1);
        if (isAir(right)) {
            right.getBlock().setType(Material.WEB);
            newLocations.add(right);
        }
        Location left = new Location(l, center.getBlockX(), center.getBlockY(), center.getBlockZ()-1);
        if (isAir(left)) {
            left.getBlock().setType(Material.WEB);
            newLocations.add(left);
        }
        return newLocations;
    }

    private void BuildWebs(Block b, Long d){
        ArrayList<Location> newLocations = keepAirLocationNearCobweb(b);
        (new BukkitRunnable() {
            public void run() {
                b.setType(Material.AIR);
                for (Location newlocation : newLocations){
                    newlocation.getBlock().setType(Material.AIR);
                }
            }
        }).runTaskLater(Main.getInstance(), d);
    }

    private void createHologram(Block b, long d){
        Location position = new Location(b.getWorld(), b.getLocation().getBlockX() + 0.5, b.getLocation().getBlockY() + 1 + high, b.getLocation().getBlockZ() + 0.5);
        HolographicDisplaysAPI api = HolographicDisplaysAPI.get(plugin); // The API instance for your plugin
        Hologram hologram = api.createHologram(position);
        final int[] time = {(int) d / 20};
        (new BukkitRunnable() {
            public void run() {
                hologram.getLines().insertText(0, Integer.toString(time[0]));
                if (time[0] <= 0){
                    hologram.delete();
                    return;
                }
                time[0] = time[0] -1;
                hologram.getLines().remove(1);
            }
        }).runTaskTimer(Main.getInstance(), 0, 20);
        (new BukkitRunnable() {
            public void run() {
            }
        }).runTaskLater(Main.getInstance(), d);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        long duration = 0;
        boolean bool = false;
        Material materialPlaced = e.getBlockPlaced().getType();
        final Block b = this.getBlockAt(e.getBlockPlaced().getLocation());
        if (b.getType().equals(Material.WEB)) {
            for (String blockAndDuration : blocksTimed) {
                String[] blockAndDurationList = blockAndDuration.split(":");
                if (Material.getMaterial(blockAndDurationList[0]).equals(Material.WEB)) {
                    duration = Long.parseLong(blockAndDurationList[1]) * 20;
                    BuildWebs(b, duration);
                    bool = true;
                    break;
                }
            }
        } else {
            for (String blockAndDuration : blocksTimed) {
                String[] blockAndDurationList = blockAndDuration.split(":");
                Material block = Material.getMaterial(blockAndDurationList[0]);
                if (materialPlaced.equals(block)) {
                    duration = Long.parseLong(blockAndDurationList[1]) * 20;
                    b.setType(block);
                    (new BukkitRunnable() {
                        public void run() {
                            b.setType(Material.AIR);
                        }
                    }).runTaskLater(Main.getInstance(), duration);
                    bool = true;
                    break;
                }
            }
        }
        if (bool){
            if (displayHologram)
                createHologram(b, duration);
        }
    }
}
