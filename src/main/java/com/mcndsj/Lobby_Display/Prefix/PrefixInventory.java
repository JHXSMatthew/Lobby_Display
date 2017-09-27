package com.mcndsj.Lobby_Display.Prefix;

import com.mcndsj.Lobby_Display.Lobby_Display;
import com.mcndsj.lobby_Gui.Guis.AbstractGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 2016/4/16.
 */
public class PrefixInventory extends AbstractGui{

    private static String t = "称号录";

    private Player p;
    private List<ItemStack> allow;
    private List<ItemStack> disallow;
    private ItemStack current ;
    private Inventory inv = Bukkit.createInventory(null, 54, "称号录");

    private ItemMeta allowMeta;
    private ItemMeta disallowMeta;
    private ItemMeta allMeta;

    public PrefixInventory(Player p,ItemStack current,List<ItemStack> disallow ,List<ItemStack> allow){
        super(t);
        this.allow = allow;
        this.disallow = disallow;
        this.current = current;
        this.p = p;
        if(current == null){
            allow.get(0).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            this.current = allow.get(0);
        }else{
            this.current = current;
            current.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }
        load();
        setDefault();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt){
        if(evt.getInventory().getTitle().equals(inv.getTitle()) && evt.getPlayer() == p){
            HandlerList.unregisterAll(this);
            //Lobby_Display.getInstance().getPrefixController().removeTrace(p.getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt){
        if(evt.getPlayer() == p){
            HandlerList.unregisterAll(this);
            //Lobby_Display.getInstance().getPrefixController().removeTrace(p.getUniqueId());
        }
    }

    @Override
    protected void callOnClick(Player player, ItemStack itemStack) {
        if(player != p)
            return;
        String name = itemStack.getItemMeta().getDisplayName();
        if(name.contains("切换")){
            SwitchInv();
            return;
        }else if(!name.contains("一页")){
            changePrefix(itemStack);
        }
        player.closeInventory();
    }



    public void load(){
        ItemStack item = new ItemStack(Material.WOOL);
        allowMeta = item.getItemMeta();
        disallowMeta  = item.getItemMeta().clone();
        allMeta = item.getItemMeta().clone();

        allowMeta.setDisplayName( ChatColor.YELLOW + "切换");
        disallowMeta.setDisplayName(ChatColor.YELLOW + "切换");
        allMeta.setDisplayName(ChatColor.YELLOW + "切换");


        List<String> list = new ArrayList<String>();
        list.add(ChatColor.GREEN + "点击查看已获得称号.");
        allowMeta.setLore(list);

        list.clear();
        list.add(ChatColor.GREEN + "点击查看未获得称号.");
        disallowMeta.setLore(list);

        list.clear();
        list.add(ChatColor.GREEN + "点击查看所有称号.");
        allMeta.setLore(list);


    }

    private void setMixedInv(){
        inv.clear();
        int position = 10;
        List<ItemStack> mix = new ArrayList<ItemStack>();
        mix.addAll(allow);
        mix.addAll(disallow);
        for(ItemStack dis : mix){
            while(position % 9 ==0 || position % 9 == 8){
                position ++;
            }
            if(position > 44){
                break;
            }
            inv.setItem(position, dis);
            position ++;
        }
    }

    private void setAllowInv(){
        inv.clear();
        int position = 10;
        for(ItemStack dis : allow){
            while(position % 9 ==0 || position % 9 == 8){
                position ++;
            }
            if(position > 44){
                break;
            }
            inv.setItem(position, dis);
            position ++;
        }
    }

    private void setDisallowInv(){
        inv.clear();
        int position = 10;
        for(ItemStack dis : disallow){
            while(position % 9 ==0 || position % 9 == 8){
                position ++;
            }
            if(position > 44){
                break;
            }
            inv.setItem(position, dis);
            position ++;
        }


    }

    public void changeCurrent(ItemStack item){
        if(current == null){
            allow.get(0).removeEnchantment(Enchantment.DURABILITY);
        }else{
            current.removeEnchantment(Enchantment.DURABILITY);
        }
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        current = item;
    }

    public void setDefault(){
        ItemStack item = new ItemStack(Material.WOOL);
        setMixedInv();
        item.setDurability(DyeColor.GREEN.getData());
        item.setItemMeta(disallowMeta);
        inv.setItem(4, item);
    }

    // white = all , green = allow  , yellow = disallow

    public void SwitchInv(){
        ItemStack item = inv.getItem(4);

        if(item == null  || item.getType().equals(Material.AIR) || item.getDurability() == DyeColor.YELLOW.getData()){
            if(item == null){
                item = new ItemStack(Material.WOOL);
            }
            setDisallowInv();
            item.setDurability(DyeColor.WHITE.getData());
            item.setItemMeta(allMeta);
            inv.setItem(4, item);
            p.updateInventory();
            return;
        }

        if(item.getDurability() == DyeColor.WHITE.getData()){
            setMixedInv();
            item.setDurability(DyeColor.GREEN.getData());
            item.setItemMeta(allowMeta);
            inv.setItem(4, item);
            p.updateInventory();

            return;
        }

        if(item.getDurability() == DyeColor.GREEN.getData()){
            setAllowInv();
            item.setDurability(DyeColor.YELLOW.getData());
            item.setItemMeta(disallowMeta);
            inv.setItem(4, item);
            p.updateInventory();

            return;
        }
    }

    public void changePrefix(ItemStack item){
        if(!item.getItemMeta().hasLore()){
            return;
        }
        if(item.getItemMeta().getLore().contains(ChatColor.RED + ChatColor.BOLD.toString() + "✘ 未拥有")){
            p.sendMessage(ChatColor.AQUA + "YourCraft >> 您未拥有该称号！");
            p.closeInventory();
            return;
        }
        if(item.getItemMeta().getDisplayName().contains("无称号")){
            changeCurrent(item);
            Lobby_Display.setPrefix(p, "");
            p.sendMessage(ChatColor.AQUA +"YourCraft >> 您的称号已经成功改变！");
            p.closeInventory();
            return;
        }
        changeCurrent(item);
        Lobby_Display.setPrefix(p, item.getItemMeta().getDisplayName().replace(ChatColor.COLOR_CHAR, '&'));
        p.sendMessage(ChatColor.AQUA +"YourCraft >> 您的称号已经成功改变！");
        Lobby_Display.getInstance().getScoreboardController().updatePlayerPrefix(p);
        p.closeInventory();
    }


    public Inventory getInv(){
        return inv;
    }




}