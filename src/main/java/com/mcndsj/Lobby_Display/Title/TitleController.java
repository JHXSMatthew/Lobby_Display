package com.mcndsj.Lobby_Display.Title;


import com.mcndsj.Lobby_Display.Animations.TitleNormal;
import com.mcndsj.Lobby_Display.Lobby_Display;
import com.mcndsj.Lobby_Display.Title.nms.Title;
import com.mcndsj.Lobby_Display.Title.nms.Title1_8R_3;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2016/4/17.
 */
public class TitleController implements Listener {

    private Title title;

    private String titleStr = ChatColor.GREEN + "YourCraft";
    private String subTitleStr = ChatColor.RED + "中国最大小游戏服";

    private List<TitleNormal> TitleNormalList;

    public TitleController(){
        title = new Title1_8R_3 ();
        TitleNormalList = new ArrayList<TitleNormal>();
        /*
        Map map = new HashMap<String,String>();
        map.put("title",titleStr);
        map.put("subTitle",subTitleStr);

        try {
            TitleNormalList.add(new TitleNormal(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt){
        if(TitleNormalList.isEmpty()){
            title.sendTitle(evt.getPlayer(),10,30,10,titleStr,subTitleStr);
        }else{
            List<TitleNormal> Queue = cloneTitleNormal(TitleNormalList);
            runQuueue(evt.getPlayer(),Queue,0);
        }
        title.sendTab(evt.getPlayer(), ChatColor.WHITE + ChatColor.BOLD.toString() +"YourCraft-你的世界", ChatColor.WHITE + "请访问 " + ChatColor.GREEN + "www.mcndsj.com" + ChatColor.WHITE + " 获取最新活动、参与游戏讨论.");
    }

    public void registerAnima(List<TitleNormal> ani){
        TitleNormalList = ani;
    }


    private void runQuueue(Player p, List<TitleNormal> TitleNormal, int count){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline())
                    cancel();
                if(TitleNormal.get(count).isSleep()){
                    if(TitleNormal.size()  >= count){
                        cancel();
                    }else {
                        runQuueue(p, TitleNormal, count + 1);
                        cancel();
                    }
                }else{
                    title.sendTitle(p,0,7,0,TitleNormal.get(count).animate()," ");
                }
            }
        }.runTaskTimer(Lobby_Display.getInstance(),0,TitleNormal.get(count).getTickPerTrigger());
    }

    private List<TitleNormal> cloneTitleNormal(List<TitleNormal> args){
        List<TitleNormal> list = new ArrayList<TitleNormal>();
        for(TitleNormal ani : args){
            list.add(ani.clone());
        }
        return list;
    }

}
