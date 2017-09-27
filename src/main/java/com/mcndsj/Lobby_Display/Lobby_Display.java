package com.mcndsj.Lobby_Display;

import com.mcndsj.Lobby_Display.API.API;
import com.mcndsj.Lobby_Display.API.Animation;
import com.mcndsj.Lobby_Display.BossBar.BossBarController;
import com.mcndsj.Lobby_Display.Prefix.PrefixController;
import com.mcndsj.Lobby_Display.Scoreboard.BoardController;
import com.mcndsj.Lobby_Display.Title.TitleController;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Matthew on 2016/4/16.
 */
public class Lobby_Display extends JavaPlugin {
    private ExecutorService pool;
    private BossBarController bs;
    private BoardController board;
    private PrefixController prefix;
    private TitleController title;
    private API api;
    private static Lobby_Display instance;


    public void onEnable(){
        instance = this;
        pool = Executors.newFixedThreadPool(3);
        bs = new BossBarController();
        board = new BoardController();
        prefix = new PrefixController();
        title = new TitleController();
        api = new API();
        this.getServer().getPluginManager().registerEvents(prefix,this);
        this.getServer().getPluginManager().registerEvents(board,this);
        this.getServer().getPluginManager().registerEvents(title,this);
        getCommand("chenghao").setExecutor(new ChengHaocommand());
    }

    public ExecutorService getThread(){
        return this.pool;
    }

    public static Lobby_Display getInstance(){
        return instance;
    }

    public API getApi(){
        return api;
    }

    public static String getPrefix(Player p){
        PermissionUser user = PermissionsEx.getUser(p);
        return user.getPrefix();
    }

    public static void setPrefix(Player p,String prefix){
        PermissionUser user = PermissionsEx.getUser(p);
        user.setPrefix(prefix, null);
        getInstance().getApi().refreshBoard(p);
    }

    public void registerAnimation(List<Animation> list){
        //title.registerAnima(list);
    }

    public BoardController getScoreboardController(){
        return board;
    }

    public PrefixController getPrefixController(){
        return prefix;
    }
}
