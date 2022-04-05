package com.square.pos.utils;

/**
 * Created by Prahalad Chahar on 2019-06-25.
 */
public class String3WithTag {
    public String string;
    public Object tag;

    public String3WithTag(String stateName, Object tagPart) {
        string = stateName;
        tag = tagPart;
    }

    @Override
    public String toString() {
        return string;
    }
}
