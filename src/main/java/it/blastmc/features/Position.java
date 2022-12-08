package it.blastmc.features;

import org.bukkit.Location;
import org.bukkit.World;

public class Position {
    private final Location location;

    public Position(Location location){
        this.location = location;
    }
    public Position(World w, double x, double y, double z){
        this.location = new Location(w, x, y, z);
    }
    public boolean isInRegion(Location l1, Location l2){
        boolean c1, c2, c3;
        if (l1.getBlockX() <= l2.getBlockX())
            c1 = isInCoords(location.getBlockX(), l1.getBlockX(), l2.getBlockX());
        else
            c1 = isInCoords(location.getBlockX(), l2.getBlockX(), l1.getBlockX());
        if (l1.getBlockY() <= l2.getBlockY())
            c2 = isInCoords(location.getBlockY(), l1.getBlockY(), l2.getBlockY());
        else {
            c2 = isInCoords(location.getBlockY(), l2.getBlockY(), l1.getBlockY());
        }
        if (l1.getBlockZ() <= l2.getBlockZ())
            c3 = isInCoords(location.getBlockZ(), l1.getBlockZ(), l2.getBlockZ());
        else {
            c3 = isInCoords(location.getBlockZ(), l2.getBlockZ(), l1.getBlockZ());
        }
        return c1 && c2 && c3;
    }
    private boolean isInCoords(int param, int less_param, int more_param){
        return (param >= less_param && param <= more_param) || (param >= more_param && param <= less_param);
    }
}
