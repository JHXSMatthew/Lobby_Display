package com.mcndsj.Lobby_Display.Scoreboard;

import com.mcndsj.Lobby_Display.Lobby_Display;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * Created by Matthew on 2016/4/17.
 */
public class BoardEntity {
    private Player p ;
    private ScoreBoard board;
    boolean isUpdating = false;

    public BoardEntity(Player p,String title){
        this.p = p;
        board = new ScoreBoard(title);
        board.send(p);
    }

    public void setEntry(int index, String finalStr){
        if(finalStr == null){
            return;
        }
        board.add(finalStr,index);
    }

    public Player getPlayer(){
        return p;
    }

    public void update(){
        if(isUpdating) return;
        isUpdating = true;
        board.update();
        isUpdating = false;

    }

    public void setTitle(String title){
        board.setTitle(title);
    }

    public void updatePlayerPrefix(Player p){
        removePlayerPrefix(p);
        createNewPlayerPrefix(p);
    }


    public void createNewPlayerPrefix(Player p){
        int prefixCode = Lobby_Display.getInstance().getPrefixController().getIndexFromPrefix(Lobby_Display.getPrefix(p));
        if(prefixCode == -1)
            return;
        if(board.getScoreboard().getTeam("Prefix_T" + prefixCode) == null){
            Team t = board.getScoreboard().registerNewTeam("Prefix_T" + prefixCode);
            t.addEntry(p.getName());
            t.setPrefix(ChatColor.translateAlternateColorCodes('&',Lobby_Display.getInstance().getPrefixController().getPrefixFromIndex(prefixCode).replace("&l","")));
        }else{
            Team t  = board.getScoreboard().getTeam("Prefix_T" + prefixCode);
            if(t.hasEntry(p.getName())) return;
            t.addEntry(p.getName());
        }
    }
    public void removePlayerPrefix(Player p){
        int prefixCode = Lobby_Display.getInstance().getPrefixController().getIndexFromPrefix(Lobby_Display.getPrefix(p));
        if(prefixCode == -1)
            return;
        if(board.getScoreboard().getTeam("Prefix_T" + prefixCode) == null){
            return;
        }else{
            Team t  = board.getScoreboard().getTeam("Prefix_T" + prefixCode);
            t.removeEntry(p.getName());
        }
    }

    public void dispose(){
        board.reset();
    }


}
