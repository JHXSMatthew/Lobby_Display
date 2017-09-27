package com.mcndsj.Lobby_Display.Scoreboard;

import com.mcndsj.Lobby_Display.API.BoardCaller;
import com.mcndsj.Lobby_Display.API.BoardRep;
import com.mcndsj.Lobby_Display.API.EntryContainer;
import com.mcndsj.Lobby_Display.Animations.WhiteAndGold;
import com.mcndsj.Lobby_Display.Lobby_Display;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Matthew on 2016/4/16.
 */
public class BoardController implements Listener{
    // per prefix per player
    private HashMap<UUID,BoardEntity> boards;
    private BoardCaller caller = null;

    public BoardController(){
        boards = new HashMap<UUID,BoardEntity>();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt){
        createScoreboard(evt.getPlayer());
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent evt){
        removeBoard(evt.getPlayer());
    }

    public void removeBoard(Player p){
        if(caller == null)
            return;
        if(boards.get(p.getUniqueId()) == null)
            return;

         for(BoardEntity temp : boards.values()){
           temp.removePlayerPrefix(p);
       }
        BoardEntity entity = boards.remove(p.getUniqueId());
        entity.dispose();
    }



    public void createScoreboard(Player p){
        removeBoard(p);
        if(caller == null)
            return;
        BoardEntity entity = new BoardEntity(p,caller.getRep().title);
        for(EntryContainer entry : caller.getRep().entries){
            if(entry.needVarAsk){
                caller.newBoadEntryQuery(entity,entry);
            }else{
                entity.setEntry(entry.index,entry.entry);
            }
        }
        boards.put(p.getUniqueId(),entity);;
        for(Player temp : Bukkit.getOnlinePlayers()){
            entity.createNewPlayerPrefix(temp);
            boards.get(temp.getUniqueId()).createNewPlayerPrefix(p);
        }

    }
    public void updatePlayerPrefix(Player p){
        for(BoardEntity entity : boards.values()){
            entity.updatePlayerPrefix(p);
        }
    }

    public void registerBoardCaller(BoardCaller caller){
        this.caller = caller;
        if(caller.getRep().titleAnimation != null){
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(caller.getRep().titleAnimation.isSleep())
                        return;

                    for(BoardEntity entity : boards.values()){
                        entity.setTitle(caller.getRep().titleAnimation.animate());
                    }
                }
            }.runTaskTimerAsynchronously(Lobby_Display.getInstance(),0,caller.getRep().titleAnimation.getTickPerTrigger());
        }

    }
}
