package com.mcndsj.Lobby_Display.API;

import java.util.Collection;

/**
 * Created by Matthew on 2016/4/16.
 */
public class BoardRep {
    public String title;
    public Animation titleAnimation;
    public Collection<EntryContainer> entries;
    /**
     *
     * @param title
     * @param entries
     * @return
     */
    public BoardRep(String title,Animation titleAnimation,Collection<EntryContainer> entries){
        this.title = title;
        this.titleAnimation = titleAnimation;
        this.entries = entries;
    }
}
