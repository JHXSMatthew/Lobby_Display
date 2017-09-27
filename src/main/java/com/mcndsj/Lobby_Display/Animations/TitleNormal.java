package com.mcndsj.Lobby_Display.Animations;

import com.mcndsj.Lobby_Display.API.Animation;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Map;

/**
 * Created by Matthew on 2016/4/17.
 */
public class TitleNormal implements Animation{

    Map cache = null;
    String title;

    /**
     * "title"
     * "subTitle"
     *
     * @param s
     * @throws Exception
     */
    public TitleNormal(Map s) throws Exception {
        cache = s;
        setOriginString(s);
    }

    @Override
    public boolean isSleep() {
        return false;
    }

    /**
     * "title"
     * "subTitle"
     *
     * @param s
     * @throws Exception
     */
    @Override
    public void setOriginString(Map s) throws Exception {

    }

    @Override
    public String animate() {
        return null;
    }


    public String getTitle(){
        return title;
    }

    public String getSubTitle(){
        return getSubTitle();
    }


    @Override
    public int getTickPerTrigger() {
        return 0;
    }

    @Override
    public TitleNormal clone(){
        try {
            return new TitleNormal(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
