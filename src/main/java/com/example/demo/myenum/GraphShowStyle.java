package com.example.demo.myenum;

public enum GraphShowStyle {
    text("文本", 0),
    mappoint("地图定位", 1),
    mapscatter("周边定位", 2),
    bar("柱图", 3),
    pie("饼图", 4),
    line("折线图", 5),
    table("表格", 6),
    maplocation("地图分布", 7),
    mapeventlocation("事件地图", 8),
    mapdetection("侦查地图", 9),
    mapaccident("遭遇地图", 10),
    heatmap("热力图", 11),
    mapdistribution("事件", 12),
    datacockpit("驾驶舱", 13);

    private final  String name;

    private  Integer value;

    public String getName() {
        return name;
    }

    public final Integer getValue() {
        return value;
    }

    private GraphShowStyle(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
