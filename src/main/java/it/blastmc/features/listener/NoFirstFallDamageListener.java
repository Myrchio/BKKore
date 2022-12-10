package it.blastmc.features.listener;

import it.blastmc.Main;
import it.blastmc.features.FallenPlayerList;
import it.blastmc.features.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class NoFirstFallDamageListener implements Listener {
    private final Plugin plugin = Main.getInstance();
    private final FallenPlayerList fallenPlayers = Main.getInstance().fallenPlayerList;
    private final String pos1 = plugin.getConfig().getString("no-first-fall-damage.position-A");
    private final String pos2 = plugin.getConfig().getString("no-first-fall-damage.position-B");

    private Location BuildLocation(String string){
        return new Location(
                Bukkit.getWorld(string.split(":")[3]),
                Float.parseFloat(string.split(":")[0]),
                Float.parseFloat(string.split(":")[1]),
                Float.parseFloat(string.split(":")[2])
        );
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent p) {
        Position po = new Position (p.getPlayer().getLocation());
        UUID uuid = p.getPlayer().getUniqueId();
        if (!po.isInRegion(BuildLocation(pos1), BuildLocation(pos2)) && fallenPlayers.search(uuid)){
            fallenPlayers.add(uuid);
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent p) {
        UUID uuid = p.getPlayer().getUniqueId();
        if (fallenPlayers.search(uuid))
            fallenPlayers.remove(uuid);
    }

    @EventHandler
    public void onTeleportEvent(final PlayerTeleportEvent p) {
        if (! p.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)){
            Position po = new Position(p.getTo());
            Location l1 = BuildLocation(pos1);
            Location l2 = BuildLocation(pos2);
            if (po.isInRegion(l1, l2)){
                fallenPlayers.remove(p.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onEntityDamageEvent(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player){
            Player p = ((Player) e.getEntity()).getPlayer();
            e.getEntity().getFallDistance();
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && !fallenPlayers.search(p.getUniqueId()) && p.getFallDistance()!=0) {
                e.setCancelled(true);
                fallenPlayers.add(p.getUniqueId());
            }
        }
    }
}
