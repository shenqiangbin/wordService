package com.example.demo.myenum;

public enum TypeMarkEnum implements IEnum{
    empty("空", -1),
    primaryKey("主键", 0),
    time("时间", 1),
    location("地点", 2),
    lng("经度", 3),
    lat("纬度", 4),
    title("标题", 5);

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

    private TypeMarkEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
