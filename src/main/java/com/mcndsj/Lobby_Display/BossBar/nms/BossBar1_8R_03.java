package com.mcndsj.Lobby_Display.BossBar.nms;

import com.mcndsj.Lobby_Display.Lobby_Display;
import io.netty.util.internal.ConcurrentSet;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBarAPI;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossFlag;
import us.myles.ViaVersion.api.boss.BossStyle;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Matthew on 2016/4/16.
 */
public class BossBar1_8R_03 implements BossBar{

    private UUID uuid;
    private String title;
    private float health;
    private ConcurrentSet<UUID> players;
    private boolean visible;

    public BossBar1_8R_03(String title,float health){
        Validate.notNull(title,"Title cannot be null");
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.health = health;
        this.players = new ConcurrentSet<>();
        visible = true;
        this.title = title;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BossBar setTitle(String s) {
        this.title = s;
        send();
        return this;
    }

    private void send(){
        for(UUID uuid : players){
            BossBarAPI.setMessage(Lobby_Display.getInstance().getServer().getPlayer(uuid),title);
        }
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public BossBar setHealth(float v) {
        this.health = v;
        return this;
    }

    @Override
    public BossColor getColor() {
        return null;
    }

    @Override
    public BossBar setColor(BossColor bossColor) {
        return null;
    }

    @Override
    public BossStyle getStyle() {
        return null;
    }

    @Override
    public BossBar setStyle(BossStyle bossStyle) {
        return null;
    }

    @Override
    public BossBar addPlayer(Player player) {
        players.add(player.getUniqueId());
        send();
        return this;
    }

    @Override
    public BossBar addPlayers(Player... players) {
        for(Player p : players){
            addPlayer(p);
        }
        return this;
    }

    @Override
    public BossBar removePlayer(Player player) {
        this.players.remove(player.getUniqueId());
        BossBarAPI.removeBar(player);
        return this;
    }


    @Override
    public boolean hasFlag(BossFlag bossFlag) {
        return false;
    }

    @Override
    public Set<UUID> getPlayers() {
        return players;
    }

    @Override
    public BossBar show() {
        this.visible = true;
        return this;
    }

    @Override
    public BossBar hide() {
        this.visible = false;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public BossBar addFlag(BossFlag bossFlag) {
        return null;
    }

    @Override
    public BossBar removeFlag(BossFlag bossFlag) {
        return null;
    }

}
