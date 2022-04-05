package com.dmw.noble.utils;

/**
 * Created by Prahalad Chahar on 2019-06-22.
 */
public class BankWithId {
    public String string;
    public Object tag;

    public BankWithId(String stateName, Object tagPart) {
        string = stateName;
        tag = tagPart;
    }

    @Override
    public String toString() {
        return string;
    }
}
