package com.example.demo.myenum;

public enum CheckInfoSyncState implements IEnum{
    defaultVal("默认值", 0),
    waitSync("待同步", 1),
    success("同步成功", 2),
    fail("同步失败", 50);

    private final  String name;

    private  Integer value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public final Integer getValue() {
        return value;
    }

    private CheckInfoSyncState(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
