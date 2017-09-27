package com.mcndsj.Lobby_Display.API;

import com.mcndsj.Lobby_Display.Scoreboard.BoardEntity;

/**
 * Created by Matthew on 2016/4/17.
 */
public interface BoardCaller {

    BoardRep getRep();

    void newBoadEntryQuery(BoardEntity entity, EntryContainer container);

}
