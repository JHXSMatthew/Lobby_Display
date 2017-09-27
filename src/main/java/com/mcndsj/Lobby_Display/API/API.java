package com.mcndsj.Lobby_Display.API;

import com.mcndsj.Lobby_Display.Lobby_Display;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Created by Matthew on 2016/4/16.
 */
public class API {
    public String getPrefix(Player p){
        return Lobby_Display.getPrefix(p);
    }

    public void openPrefixInventory(Player p){
        Lobby_Display.getInstance().getPrefixController().addTrace(p);
    }

    public void addPrefix(Player p, int id){
        Lobby_Display.getInstance().getPrefixController().addPrefix(p.getName(),id);
    }

    public void removePrefix(Player p, int id){
        Lobby_Display.getInstance().getPrefixController().removePrefix(p.getName(),id);
    }


    public void registerBoardCaller(BoardCaller caller){
        Lobby_Display.getInstance().getScoreboardController().registerBoardCaller(caller);
    }

    public void registerTitleAnimations(List<Animation> animations){
        Lobby_Display.getInstance().registerAnimation(animations);
    }

    public void refreshBoard(Player p){
        new BukkitRunnable(){
            @Override
            public void run() {
                Lobby_Display.getInstance().getScoreboardController().removeBoard(p);
                Lobby_Display.getInstance().getScoreboardController().createScoreboard(p);
            }
        }.runTask(Lobby_Display.getInstance());

    }


}
