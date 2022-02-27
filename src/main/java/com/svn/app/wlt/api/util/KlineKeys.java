package com.svn.app.wlt.api.util;


public enum KlineKeys {
    OPENTIME(0),OPEN(1),HIGH(2),LOW(3),CLOSE(4),VOLUME(5),CLOSETIME(6),
    QUOTEASSETVOLUME(7),NUMBEROFTRADES(8);

    private final int value;

    private KlineKeys(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
