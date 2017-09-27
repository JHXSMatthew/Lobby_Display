package com.mcndsj.Lobby_Display.Prefix;

import com.mcndsj.Lobby_Display.Lobby_Display;
import com.mcndsj.Lobby_Display.Utils.SQLUtils;
import com.mcndsj.lobby_Gui.Utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Matthew on 2016/4/16.
 */
public class PrefixController implements Listener{

    private ExecutorService pool;
    //private HashMap<UUID,PrefixInventory> map;
    private List<PrefixContainer> pc;
    private List<ItemStack> prefixItems;

    public PrefixController(){
        //this.map = new HashMap<UUID,PrefixInventory>();
        pc = Collections.synchronizedList(new ArrayList<PrefixContainer>());
        prefixItems = Collections.synchronizedList(new ArrayList<ItemStack>());
        pool = Executors.newSingleThreadExecutor();
        load();
        loadItem();
    }

    public void addTrace(Player p){
        pool.execute(new Runnable() {
            @Override
            public void run() {
                PrefixInventory inv  = createPrefixInventory(p, Lobby_Display.getPrefix(p));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.closeInventory();
                        p.openInventory(inv.getInv());
                    }
                }.runTask(Lobby_Display.getInstance());
            }
        });
    }
/*
    public void removeTrace(UUID uuid){
        map.remove(uuid);
    }
/*
    @EventHandler
    public void onQuit(PlayerQuitEvent evt){
        map.remove(evt.getPlayer().getUniqueId());
    }
*/

    public void addPrefix(String player , int id){
        if(Bukkit.getPlayer(player) != null)
            Bukkit.getPlayer(player).closeInventory();
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<Integer> list = SQLUtils.getPrefixList(player);
                if(!list.contains(id)){
                    list.add(id);
                    try {
                        Collections.sort(list);
                    }catch(Exception e){

                    }
                    SQLUtils.savePrefixList(player,list);
                }
            }
        });
    }

    public void removePrefix(String player , int id){
        if(Bukkit.getPlayer(player) != null)
            Bukkit.getPlayer(player).closeInventory();
        Lobby_Display.setPrefix(Bukkit.getPlayer(player),"");
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<Integer> list = SQLUtils.getPrefixList(player);
                if(list.contains(id)){
                    Iterator<Integer> iterator = list.iterator();
                    while(iterator.hasNext()){
                        int next = iterator.next();
                        if(next == id) {
                            iterator.remove();
                            break;
                        }
                    }
                    try {
                        Collections.sort(list);
                    }catch(Exception e){

                    }
                    SQLUtils.savePrefixList(player,list);
                }
            }
        });
    }

    private PrefixInventory createPrefixInventory(Player p, String currentPrefix){
        List<ItemStack> list = new ArrayList<ItemStack>();
        List<ItemStack> offlist = new ArrayList<ItemStack> ();

        //picked is all prefix in database
        List<Integer> picked = SQLUtils.getPrefixList(p.getName());

        ItemStack current = null;
        currentPrefix = currentPrefix.replace('&' , ChatColor.COLOR_CHAR );
        int count = 0;
        for(ItemStack i : prefixItems){
            ItemStack item = i.clone();
            ItemMeta meta =  i.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',meta.getDisplayName()));
            List<String> lore = meta.getLore();
            lore.add(" ");
            if(picked.contains(count)){
                lore.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ 已拥有");
                meta.setLore(lore);
                if(meta.getDisplayName().contains(currentPrefix)){
                    current = item;
                    current.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                }
                item.setItemMeta(meta);
                list.add(item);

            }else{
                lore.add(ChatColor.RED + ChatColor.BOLD.toString() + "✘ 未拥有");
                meta.setLore(lore);
                item.setItemMeta(meta);
                offlist.add(item);
            }
            count ++;
        }

        PrefixInventory pre = new PrefixInventory(p,current,offlist,list);
        return pre;
    }


    private void loadItem(){
        for(PrefixContainer container : pc){
            prefixItems.add(ItemFactory.create(container.getMaterial(),(byte)0,container.getPrefix(),container.getLores().split("\\|")));
        }
    }

    public List<PrefixContainer> getAllPrefix(){
        return this.pc;
    }

    public String getPrefixFromIndex(int index){
        return pc.get(index).getPrefix();
    }

    public int getIndexFromPrefix(String prefix){
        for(PrefixContainer temp : pc){
            if(temp.getPrefix().equals(prefix.replaceAll("§","&"))){
                return pc.indexOf(temp);
            }
        }
        return -1;
    }


    private void load(){
        //0
        pc.add(new PrefixContainer("&7无称号", "&7设置您的称号为空.", Material.BARRIER));
        //1
        pc.add(new PrefixContainer("&2&lVIP✔","&7购买&2VIP&7获取的称号 |&7是您赞助服务器的证明.",Material.IRON_BLOCK));
        //2
        pc.add(new PrefixContainer("&a&lVIP+✔","&7购买&a&lVIP+✔&7获取的称号 |&7是您赞助服务器的证明.",Material.GOLD_BLOCK));
        //3
        pc.add(new PrefixContainer("&2&l&e&lMVP✔","&7购买&e&lMVP✔&7获取的称号 |&7是您赞助服务器的证明.",Material.DIAMOND_BLOCK));
        //4
        pc.add(new PrefixContainer("&2&l&6&lMVP+✔","&7购买&6&lMVP+✔&7获取的称号 |&7是您赞助服务器的证明.",Material.EMERALD_BLOCK));
        //5
        pc.add(new PrefixContainer("&c&l一笑倾城❡","&7认证妹纸获得的称号 |&7欢迎来玩YourCraft哦~.",Material.RED_ROSE));
        //6 is 主播
        pc.add(new PrefixContainer("&b&l游戏主播✔","&7认证主播获得的称号 |&7祝您在这里玩的愉快!",Material.REDSTONE_TORCH_ON));
        //7
        pc.add(new PrefixContainer("&a&l正版✔","&7您购买了Minecraft正版 |&7感谢您对Minecraft界的支持!",Material.RAILS));
        //8
        pc.add(new PrefixContainer("&e&l建筑✔","&7YourCraft手艺服建筑师",Material.BRICK));
        //9
        pc.add(new PrefixContainer("&6&l建筑✔","&7YourCraft手艺服高级建筑师",Material.CLAY));
        //10 is 实况主
        pc.add(new PrefixContainer("&b&l实况主✔","&7认证实况主获得的称号 |&7祝您在这里玩的愉快!",Material.REDSTONE_TORCH_ON));
        //11
        pc.add(new PrefixContainer("&b&l版主✔","&7参与论坛版块管理获得的称号 |&7执法必严,违法必究!",Material.ARROW));
        //12
        pc.add(new PrefixContainer("&5&l风纪委员✔","&7YourCraft运营团队管理员称号 |&7看什么看没见过帅哥吗？",Material.DIAMOND));

    }



}
