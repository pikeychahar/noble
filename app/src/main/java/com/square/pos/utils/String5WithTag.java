package com.square.pos.utils;

/**
 * Created by Prahalad Chahar on 2019-06-21.
 */
public class String5WithTag {
    public String string;
    public Object tag;

    public String5WithTag(String stateName, Object tagPart) {
        string = stateName;
        tag = tagPart;
    }

    @Override
    public String toString() {
        return string;
    }

}
