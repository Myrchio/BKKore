package it.blastmc.hook;

import it.blastmc.Main;
import it.blastmc.features.FallenPlayerList;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceHolderAPIHook extends PlaceholderExpansion {

    private final FallenPlayerList fallenPlayers = Main.getInstance().fallenPlayerList;

    @Override
    public @NotNull String getIdentifier() {
        return "bkk";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Mqrchio";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("isFallenPlayer")){
            return ((Boolean) fallenPlayers.search(player.getUniqueId())).toString();
        }
        return null;
    }
}
