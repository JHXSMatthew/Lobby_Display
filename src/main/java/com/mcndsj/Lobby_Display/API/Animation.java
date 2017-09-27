package com.mcndsj.Lobby_Display.API;

import java.util.Map;

/**
 * Created by Matthew on 2016/4/16.
 */
public interface Animation {
    /**
     *  is Sleep
     */
    boolean isSleep();

    /**
     * Constructor
     */
    void setOriginString(Map s) throws Exception;

    /**
     *
     * @return the animation at current tick
     */
    String animate();

    /**
     *  return how many tick should this animation triggerd
     * @return
     */
    int getTickPerTrigger();

    public Animation clone();
}
