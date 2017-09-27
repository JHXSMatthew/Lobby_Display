package com.mcndsj.Lobby_Display.BossBar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Matthew on 2016/4/16.
 */
public class BossBarListener implements Listener {
    BossBarController controller = null;
    public BossBarListener(BossBarController controller){
        this.controller = controller;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt){
        controller.addTrace(evt.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt){
        controller.removeTrace(evt.getPlayer());
    }
}
