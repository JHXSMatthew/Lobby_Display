package com.mcndsj.Lobby_Display;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matthew on 4/07/2016.
 */
public class ChengHaocommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Lobby_Display.getInstance().getApi().openPrefixInventory((Player) commandSender);
        }
        return true;
    }
}
