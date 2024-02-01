package com.example.demo.myenum;

public enum GraphEntityType {
    Conceptual("概念实体", 0),
    Entity("实体", 1),
    Property("属性", 2),
    Method("方法", 3),
    Model("模型", 4),
    Cockpit("驾驶舱", 5);

    private final String name;

    private Integer value;

    public String getName() {
        return name;
    }

    public final Integer getValue() {
        return value;
    }

    private GraphEntityType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
