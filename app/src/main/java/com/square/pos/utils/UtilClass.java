package com.square.pos.utils;

public class UtilClass {

    private static UtilClass instance;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    private   int checked;

    private UtilClass() {
    }
    public static UtilClass getInstance() {
        if (instance == null)
            instance = new UtilClass();

        return instance;
    }
}