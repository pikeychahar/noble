package com.square.pos.utils;

/**
 * Created by Prahalad Chahar on 2019-06-21.
 */
public class String4WithTagRaheja {
    public String string;
    public String nameValue;
    public Object tag;

    public String4WithTagRaheja(String full, String name, Object tagPart) {
        string = full;
        tag = tagPart;
        nameValue = name;
    }

    @Override
    public String toString() {
        return string;
    }

}
