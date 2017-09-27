package com.mcndsj.Lobby_Display.Scoreboard;

import com.mcndsj.Lobby_Display.API.EntryContainer;
import com.mcndsj.Lobby_Statistics.Api.QueryCallBack;
import com.mcndsj.Lobby_Statistics.Cache.Data;

/**
 * Created by Matthew on 2016/4/17.
 */
public class BoardEntityStatsCallBack implements QueryCallBack{
    BoardEntity entity ;
    String oriStr ;
    String[] keys;
    int index;

    public BoardEntityStatsCallBack(String[] key, EntryContainer container, BoardEntity entity){
        this.entity = entity;
        this.oriStr = container.entry;
        this.index = container.index;
        this.keys = key;
    }

    @Override
    public void onResult(Data data) {
        if(data == null){
            int count = 1;
            for(String key : keys){
                oriStr = oriStr.replace("$" + count, "0");
                count ++;
            }
            //System.out.print("CallBack : " + index + " " + oriStr);
            entity.setEntry(index, oriStr);
            entity.update();
        }else {
            try {
                int count = 1;
                for (String key : keys) {
                    oriStr = oriStr.replace("$" + count,  data.get(key).toString());
                    count++;
                }
                entity.setEntry(index, oriStr);
                entity.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
