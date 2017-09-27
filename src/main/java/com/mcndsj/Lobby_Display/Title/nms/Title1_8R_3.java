package com.mcndsj.Lobby_Display.Title.nms;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by Matthew on 2016/4/17.
 */
public class Title1_8R_3 implements Title{
    public void sendTitle(Player player, int fadein, int stay, int fadeout, String title, String subtitle) {
        PlayerConnection pConn = ((CraftPlayer) player).getHandle().playerConnection;
        PacketPlayOutTitle pTitleInfo = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent) null, (int) fadein, (int) stay, (int) fadeout);
        pConn.sendPacket(pTitleInfo);
        if (subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            IChatBaseComponent iComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle pSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, iComp);
            pConn.sendPacket(pSubtitle);
        }
        if (title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);
            IChatBaseComponent iComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle pTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iComp);
            pConn.sendPacket(pTitle);
        }
    }

    @Override
    public void sendTab(Player p,String header, String footer) {
        CraftPlayer cp = (CraftPlayer) p;
        PlayerConnection pc = cp.getHandle().playerConnection;

        IChatBaseComponent top = IChatBaseComponent.ChatSerializer.a("{text: '" + header + "'}");
        IChatBaseComponent bottom = IChatBaseComponent.ChatSerializer.a("{text: '" + footer + "'}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try{
            Field f = packet.getClass().getDeclaredField("a");
            f.setAccessible(true);
            f.set(packet, top);
            f.setAccessible(!f.isAccessible());

            Field f1 = packet.getClass().getDeclaredField("b");
            f1.setAccessible(true);
            f1.set(packet, bottom);
            f1.setAccessible(!f.isAccessible());

        }catch(Exception e){

        }
        pc.sendPacket(packet);
    }

}
