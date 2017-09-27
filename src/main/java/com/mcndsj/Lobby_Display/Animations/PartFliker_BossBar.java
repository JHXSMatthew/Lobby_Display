package com.mcndsj.Lobby_Display.Animations;

import com.mcndsj.Lobby_Display.API.Animation;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2016/4/16.
 */
public class PartFliker_BossBar implements Animation {
    private List<String> strs;
    private Map<String,String> hash;
    private int counter = -1;
    private int CALL_TICKS = 5;

    String sidder = "test";
    String fliker = " test";
    String front = " test";
    String after = " test";

    /**
     * sidder
     * filiker
     * front
     * after
     */
    public PartFliker_BossBar() {

    }

    private void incCounter(){
        counter ++;
        if(counter > strs.size() - 1)
            counter = 0;
    }

    @Override
    public boolean isSleep() {
        try{
            if(strs.get(counter).equals(strs.get(counter -1))){
                incCounter();
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public void setOriginString(Map s) throws Exception {
        this.hash = s;
        try{
            sidder = hash.get("sidder");
            fliker = hash.get("fliker");
            front = hash.get("front");
            after = hash.get("after");
        }catch(NullPointerException e){
            e.printStackTrace();
            System.err.print("SET BOSSBAR INFO FAILURE!, ERROR INDEX 1");
            throw new Exception("Error!");
        }
        strs =flickEffect(ChatColor.WHITE, ChatColor.WHITE, ChatColor.GREEN , ChatColor.RED, ChatColor.YELLOW);
        hash = null;
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

    private List<String> flickEffect(ChatColor sidder , ChatColor flikerFrom, ChatColor flikerTo, ChatColor front , ChatColor after){
        List<String> returnValue =  new ArrayList<String>();
        returnValue.add(sidder + this.sidder + front + this.front + flikerFrom  + ChatColor.BOLD.toString()+ this.fliker + after + this.after+ sidder + this.sidder);

        for(int i = 0; i < fliker.length() ; i ++){
            String tempFront = fliker.substring(0, i);
            String tempAfter = fliker.substring(i,fliker.length());
            String realFliker = flikerTo + ChatColor.BOLD.toString()+ tempFront + flikerFrom  + ChatColor.BOLD.toString()+tempAfter;
            returnValue.add(sidder + this.sidder + front + this.front +  realFliker + after + this.after+ sidder + this.sidder);
        }



        returnValue.add(sidder + this.sidder + front + this.front + flikerTo  + ChatColor.BOLD.toString()+ this.fliker + after + this.after+ sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerTo  + ChatColor.BOLD.toString()+ this.fliker + after + this.after+ sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerTo  + ChatColor.BOLD.toString()+ this.fliker + after + this.after+ sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerTo  + ChatColor.BOLD.toString()+ this.fliker + after + this.after+ sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerTo  + ChatColor.BOLD.toString()+ this.fliker + after + this.after+ sidder + this.sidder);

        returnValue.add(sidder + this.sidder + front + this.front + flikerFrom  + ChatColor.BOLD.toString()+ this.fliker + after + this.after + sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerFrom  + ChatColor.BOLD.toString()+ this.fliker + after + this.after + sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerFrom  + ChatColor.BOLD.toString()+ this.fliker + after + this.after + sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerFrom  + ChatColor.BOLD.toString()+ this.fliker + after + this.after + sidder + this.sidder);
        returnValue.add(sidder + this.sidder + front + this.front + flikerFrom + ChatColor.BOLD.toString()+ this.fliker + after + this.after + sidder + this.sidder);



        return returnValue;

    }

}
