package com.mcndsj.Lobby_Display.API;

/**
 * Created by Matthew on 2016/4/16.
 */
public class EntryContainer {

    final public Animation ani;
    public  int index;
    /**
     * var is written as
     * $1 $2 $3 $4
     * would check registed vars for this index
     */
    final public String entry;
    final public boolean needVarAsk ;


    public EntryContainer( int index ,String s,Animation ani,boolean needVarAsk){
        this.ani = ani;
        this.index = index;
        this.entry = s;
        this.needVarAsk = needVarAsk;

    }
}
