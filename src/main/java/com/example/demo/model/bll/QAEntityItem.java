package com.example.demo.model.bll;

import java.util.List;

public class QAEntityItem {
    private long uuid;
    private int dbid;//数据源id
    private String color;//颜色
    private int size;//大小
    private String label;//标记为
    private Integer orderProp; // 排序属性
    private String dbname;//数据源显示名称
    private String name;//显示名称
    private String field;//对应关系数据库字段
    private String dbfield;//对应关系数据库字段
    private Integer entitytype;//0=概念实体,1=实体,2=属性,3=函数,4=模型,5=驾驶舱
    private Integer querytype;
    private Integer showstyle;//0=文本,1=地图,2=散点,3=柱图,4=饼图,5=折线图,6=表格,7=地图分布
    private Integer isshow;
    private String unit;//文本形式可能+单位
    private String targeturl;//模型和驾驶舱对应的url
    private String sql;//查询函数
    private String table;//对应关系数据库表名
    private String detailshowfield;//细览页显示字段,在sql不为空时使用,其他不使用
    private String targettable;//对应关系数据库表名
    private Integer sortcode;//排序码,越小越靠前
    private Integer answerstyle;//答案页样式
    private List<String> filter;//过滤条件<显示名字,数据库字段> 显示名字对应智能问答意图推测后台发布的字段
    private Double fx;
    private Double fy;
    private String alias;//别名
    private String tempid;//单位回选
    private Integer graphModel;//创建图谱种类（0 普通图谱 1 概念图谱 2 生成图谱）
    private Integer isTableNode;//是否表名节点  1 是  0 否
    private Integer tableId;//表Id
    private Integer isRecommend;//是否推荐字段  1 是  0 否
    private Integer fieldId;//字段ID

    private String metaTableCode;
    private String metaFieldCode;


    public String getMetaTableCode() {
        return metaTableCode;
    }

    public void setMetaTableCode(String metaTableCode) {
        this.metaTableCode = metaTableCode;
    }

    public String getMetaFieldCode() {
        return metaFieldCode;
    }

    public void setMetaFieldCode(String metaFieldCode) {
        this.metaFieldCode = metaFieldCode;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getOrderProp() {
        return orderProp;
    }

    public void setOrderProp(Integer orderProp) {
        this.orderProp = orderProp;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDbfield() {
        return dbfield;
    }

    public void setDbfield(String dbfield) {
        this.dbfield = dbfield;
    }

    public Integer getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(Integer entitytype) {
        this.entitytype = entitytype;
    }

    public Integer getQuerytype() {
        return querytype;
    }

    public void setQuerytype(Integer querytype) {
        this.querytype = querytype;
    }

    public Integer getShowstyle() {
        return showstyle;
    }

    public void setShowstyle(Integer showstyle) {
        this.showstyle = showstyle;
    }

    public Integer getIsshow() {
        return isshow;
    }

    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTargeturl() {
        return targeturl;
    }

    public void setTargeturl(String targeturl) {
        this.targeturl = targeturl;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDetailshowfield() {
        return detailshowfield;
    }

    public void setDetailshowfield(String detailshowfield) {
        this.detailshowfield = detailshowfield;
    }

    public String getTargettable() {
        return targettable;
    }

    public void setTargettable(String targettable) {
        this.targettable = targettable;
    }

    public Integer getSortcode() {
        return sortcode;
    }

    public void setSortcode(Integer sortcode) {
        this.sortcode = sortcode;
    }

    public Integer getAnswerstyle() {
        return answerstyle;
    }

    public void setAnswerstyle(Integer answerstyle) {
        this.answerstyle = answerstyle;
    }

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public Double getFx() {
        return fx;
    }

    public void setFx(Double fx) {
        this.fx = fx;
    }

    public Double getFy() {
        return fy;
    }

    public void setFy(Double fy) {
        this.fy = fy;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid;
    }

    public Integer getGraphModel() {
        return graphModel;
    }

    public void setGraphModel(Integer graphModel) {
        this.graphModel = graphModel;
    }

    public Integer getIsTableNode() {
        return isTableNode;
    }

    public void setIsTableNode(Integer isTableNode) {
        this.isTableNode = isTableNode;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
}
