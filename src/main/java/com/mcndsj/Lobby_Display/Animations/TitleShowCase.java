package com.mcndsj.Lobby_Display.Animations;

import com.mcndsj.Lobby_Display.API.Animation;

import java.util.Map;

/**
 * Created by Matthew on 2016/4/17.
 */
public class TitleShowCase extends TitleNormal {
    public TitleShowCase(Map s) throws Exception {
        super(s);
    }

    @Override
    public boolean isSleep() {
        return false;
    }

    @Override
    public String animate() {
        return null;
    }

    @Override
    public int getTickPerTrigger() {
        return 0;
    }
}
