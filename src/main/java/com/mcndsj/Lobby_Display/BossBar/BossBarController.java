package com.mcndsj.Lobby_Display.BossBar;

import com.mcndsj.Lobby_Display.API.Animation;
import com.mcndsj.Lobby_Display.Animations.PartFliker_BossBar;
import com.mcndsj.Lobby_Display.BossBar.nms.BossBar1_8R_03;
import com.mcndsj.Lobby_Display.Lobby_Display;
import com.mcndsj.Lobby_Display.Utils.SQLUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import us.myles.ViaVersion.api.ViaVersion;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

import java.util.HashMap;

/**
 * Created by Matthew on 2016/4/16.
 */
public class BossBarController {

    private BossBar bossBar1_9 = null;
    private BossBar bossBar1_8 = null;
    private Animation animation = null;

    public BossBarController(){
        bossBar1_9 =  ViaVersion.getInstance().createBossBar("启动中……", BossColor.GREEN, BossStyle.SOLID);
        bossBar1_9.show();
        bossBar1_8 = new BossBar1_8R_03("启动中……",1);
        animation = new PartFliker_BossBar();
        try {
            fetchSQL();
        }catch(Exception e){
            HashMap<String,String> msgMap =  new HashMap<String,String>();
            msgMap.put("sidder","☀ ");
            msgMap.put("fliker","YourCraft-你的世界 ");
            msgMap.put("front","欢迎来到 ");
            msgMap.put("after","请访问 www.mcndsj.com ");
            try {
                animation.setOriginString(msgMap);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        //runnable to regular animation
        new BukkitRunnable(){
            public void run(){
                if(animation.isSleep()){
                    return;
                }
                if(!bossBar1_9.getPlayers().isEmpty() ){
                    bossBar1_9.setTitle(animation.animate());
                }
                if(!bossBar1_8.getPlayers().isEmpty()){
                    bossBar1_8.setTitle(animation.animate());
                }

            }
        }.runTaskTimerAsynchronously(Lobby_Display.getInstance(),0,animation.getTickPerTrigger());

        new BukkitRunnable(){
            public void run(){
                try{
                    fetchSQL();
                }catch(Exception e){
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(Lobby_Display.getInstance(),0,20 * 60 * 10);
        Lobby_Display.getInstance().getServer().getPluginManager().registerEvents(new BossBarListener(this),Lobby_Display.getInstance());
    }

    public void addTrace(Player p){
        if(ViaVersion.getInstance().isPorted(p)){
            bossBar1_9.addPlayer(p);
        }else{
            bossBar1_8.addPlayer(p);
        }

    }

    public void removeTrace(Player p){
        if(ViaVersion.getInstance().isPorted(p)) {
            bossBar1_9.removePlayer(p);
        }else{
            bossBar1_8.removePlayer(p);
        }
    }

    public void fetchSQL()throws Exception{
        animation.setOriginString(SQLUtils.fetchBossBarSQL());
    }

}
