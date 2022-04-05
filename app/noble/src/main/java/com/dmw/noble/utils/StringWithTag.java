package com.dmw.noble.utils;

/**
 * Created by Prahalad Chahar on 2019-06-20.
 */
public class StringWithTag {
    public String string;
    public Object tag;

    public StringWithTag(String stateName, Object tagPart) {
        string = stateName;
        tag = tagPart;
    }

    @Override
    public String toString() {
        return string;
    }
}
