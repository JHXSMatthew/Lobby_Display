package com.mcndsj.Lobby_Display.Prefix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Created by Matthew on 2016/4/16.
 */
public class PrefixContainer {
    private String prefix;
    private String lore;
    private Material mt;
    private byte durability = 0;

    public PrefixContainer(String prefix , String lore,Material mt){
        this.prefix = prefix;
        this.lore = lore.replace('&', ChatColor.COLOR_CHAR);
        this.mt = mt;
    }
    public PrefixContainer(String prefix , String lore,Material mt,byte durability){
        this.prefix = prefix;
        this.lore = lore.replace('&', ChatColor.COLOR_CHAR);
        this.mt = mt;
        this.durability = durability;

    }

    public String getPrefix(){
        return prefix;
    }

    public String getLores(){
        return lore;
    }

    public Material getMaterial(){
        return mt;
    }



}