package com.example.demo.myenum;

public enum FunctionMarkEnum implements IEnum{
    search("检索", 0),
    groupField("分组", 1),
    calField("计算", 2);

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

    private FunctionMarkEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
