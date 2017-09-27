package com.mcndsj.Lobby_Display.Animations;

import com.mcndsj.Lobby_Display.API.Animation;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2016/4/16.
 */
public class WhiteAndGold implements Animation{

    private List<String> strs;
    private int counter = 0;
    private int sleepCounter = 0;

    private int CALL_TICKS = 5;

    public WhiteAndGold(String msg){
        strs = new ArrayList<String>();
        strs.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + msg);
        for(int i = 0; i < msg.length() ;i ++){
            StringBuffer sbf = new StringBuffer();
            sbf.append(ChatColor.WHITE +  ChatColor.BOLD.toString());
            for(int k = 0; k < i ; k ++){
                sbf.append(msg.charAt(k));
            }
            sbf.append(msg.charAt(i));
            sbf.append(ChatColor.YELLOW +  ChatColor.BOLD.toString());
            for(int j = i; j < msg.length() - 1; j ++){
                sbf.append(msg.charAt(j + 1));
            }
            //System.out.print(sbf.toString());
            strs.add(sbf.toString());
        }
        strs.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + msg);

    }

    private void incCounter(){
        counter ++;
        if(counter > strs.size() - 1)
            counter = 0;
    }

    @Override
    public boolean isSleep() {
        if(counter == strs.size() - 1){
            if(sleepCounter < 9){
                sleepCounter ++;
                return true;
            }else{
                sleepCounter = 0;
                incCounter();
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public void setOriginString(Map s) throws Exception {

    }

    @Override
    public String animate() {
        incCounter();
        return strs.get(counter);
    }

    @Override
    public int getTickPerTrigger() {
        return CALL_TICKS;
    }

    @Override
    public Animation clone() {
        return null;
    }
}
