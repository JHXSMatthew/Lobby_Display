package com.mcndsj.Lobby_Display.Title.nms;

import org.bukkit.entity.Player;

/**
 * Created by Matthew on 2016/4/17.
 */
public interface Title {
    public void sendTitle(Player player, int fadein, int stay, int fadeout, String title, String subtitle) ;
    public void sendTab(Player p,String header, String footer);
}
