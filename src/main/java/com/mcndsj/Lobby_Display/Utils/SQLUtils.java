package com.mcndsj.Lobby_Display.Utils;



import com.mcndsj.JHXSMatthew.Shared.GameManager;
import com.mcndsj.JHXSMatthew.Shared.LobbyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2016/4/16.
 */
public class SQLUtils {

    public static List<Integer> getPrefixList(String name){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Integer> returnValue = new ArrayList<Integer>();
        try {
            connection = LobbyManager.getInstance().getConnection();
            if(connection == null || connection.isClosed()){
                return null;
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT `Prefix` FROM `LobbyPlayers` Where `Name`='"+ name +"';");
            if(resultSet.next()){
               String s = resultSet.getString("Prefix");
                if(s == null){
                    returnValue.add(0);
                }else{
                    String[] a = s.split("\\|");
                    for(String temp : a){
                        returnValue.add(Integer.parseInt(temp));
                    }
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
        }


        return returnValue;
    }

    public static byte isInTable(String name){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        byte returnValue = -1;
        try {
            connection = LobbyManager.getInstance().getConnection();
            if(connection == null || connection.isClosed()){
                return returnValue;
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT `id` FROM `LobbyPlayers` Where `Name`='"+ name +"';");
            if(resultSet.next()){
                returnValue = 1;
            }else{
                returnValue = 0;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
        }


        return returnValue;
    }

    public static void savePrefixList(String name,List<Integer> list){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = LobbyManager.getInstance().getConnection();
            if(connection == null || connection.isClosed()){
                return ;
            }
            statement = connection.createStatement();
            StringBuffer buffer = new StringBuffer();
            for(Integer i : list){
                buffer.append(i);
                buffer.append("|");
            }
            List<Integer> pending = getPrefixList(name);
            byte isIntable = isInTable(name);
            if(isIntable == -1){
                System.err.println("ERROR!");
                Bukkit.getPlayer(name).sendMessage(ChatColor.RED + "出现了重大BUG,请立刻输入/wozaina并登录论坛投诉建议区反馈");
                Bukkit.getPlayer(name).sendMessage(ChatColor.RED + "出现了重大BUG,请立刻输入/wozaina并登录论坛投诉建议区反馈");
                Bukkit.getPlayer(name).sendMessage(ChatColor.RED + "出现了重大BUG,请立刻输入/wozaina并登录论坛投诉建议区反馈");
                Bukkit.getPlayer(name).sendMessage(ChatColor.RED + "出现了重大BUG,请立刻输入/wozaina并登录论坛投诉建议区反馈");

                return;
            }
            if(isIntable == 0){
                statement.executeUpdate("INSERT INTO `LobbyPlayers` (`Name`,`Prefix`) VALUES ('"+ name +"','"+ buffer.toString() + "');");
            }else {
                statement.executeUpdate("UPDATE `LobbyPlayers` SET `Prefix`='" + buffer.toString() + "' Where `Name`='" + name + "';");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }


    public static Map<String,String> fetchBossBarSQL(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Map<String,String> returnValue = new HashMap<String,String>();
        try {
            connection = GameManager.getInstance().getConnection();
            if(connection == null || connection.isClosed()){
                return null;
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `BossBarInfo` Where `id`='0';");
            if(resultSet.next()){
                returnValue.put("sidder",resultSet.getString("sidder"));
                returnValue.put("front",resultSet.getString("front"));
                returnValue.put("fliker",resultSet.getString("fliker"));
                returnValue.put("after",resultSet.getString("after"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {e.printStackTrace();}
            if (statement != null) try { statement.close(); } catch (SQLException e) {e.printStackTrace();}
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
        }

        return returnValue;
    }
}
