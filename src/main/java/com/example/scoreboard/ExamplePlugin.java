package com.example.scoreboard;

import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements Listener {
    private ScoreboardLibrary scoreboardLibrary;
    private Sidebar sidebar;

    @Override
    public void onEnable() {
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(this);
        } catch (NoPacketAdapterAvailableException e) {
            getLogger().warning("No scoreboard packet adapter available!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Create the sidebar
        sidebar = scoreboardLibrary.createSidebar();
        sidebar.title(Component.text("Title Here"));
        sidebar.line(0, Component.text("Hello, world!"));

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        if (scoreboardLibrary != null) {
            scoreboardLibrary.close();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Make the player able to see the sidebar on join
        sidebar.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // And remove them when they quit
        sidebar.addPlayer(event.getPlayer());
    }
}
