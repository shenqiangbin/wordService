package com.example.demo.model.bll;

/* 对应数据库中的 v_md_instance_search_column 表 */
public class MetaField {

    private String tableCode;
    private String fieldCode;
    private String fieldEnName;
    private String fieldCnName;
    /**
     * 信息项名称
     */
    private String fieldMsgName;
    /**
     * 字段分类
     */
    private String category;
    /**
     * 数据业务类型
     */
    private String businessType;
    /**
     * 枚举值解释
     */
    private String enumStr;

    /**
     * 是否核心实体名称
     */
    private String coreEntityName;

    /**
     * 是否搜索结果展示
     */
    private String showSearchResult;

    /**
     * 数据技术类型
     */
    private String filedType;

    /**
     * 是否业务主键
     */
    private String isBusinessKey;

    /**
     * 是否技术主键
     */
    private String isTechKey;

    /**
     * 数值比例
     */
    private String unitRatio;

    /**
     * 规格单位
     */
    private String unit;


    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldEnName() {
        return fieldEnName;
    }

    public void setFieldEnName(String fieldEnName) {
        this.fieldEnName = fieldEnName;
    }

    public String getFieldCnName() {
        return fieldCnName;
    }

    public void setFieldCnName(String fieldCnName) {
        this.fieldCnName = fieldCnName;
    }

    public String getFieldMsgName() {
        return fieldMsgName;
    }

    public void setFieldMsgName(String fieldMsgName) {
        this.fieldMsgName = fieldMsgName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEnumStr() {
        return enumStr;
    }

    public void setEnumStr(String enumStr) {
        this.enumStr = enumStr;
    }

    public String getCoreEntityName() {
        return coreEntityName;
    }

    public void setCoreEntityName(String coreEntityName) {
        this.coreEntityName = coreEntityName;
    }

    public String getShowSearchResult() {
        return showSearchResult;
    }

    public void setShowSearchResult(String showSearchResult) {
        this.showSearchResult = showSearchResult;
    }

    public String getFiledType() {
        return filedType;
    }

    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }

    public String getIsBusinessKey() {
        return isBusinessKey;
    }

    public void setIsBusinessKey(String isBusinessKey) {
        this.isBusinessKey = isBusinessKey;
    }

    public String getIsTechKey() {
        return isTechKey;
    }

    public void setIsTechKey(String isTechKey) {
        this.isTechKey = isTechKey;
    }

    public String getUnitRatio() {
        return unitRatio;
    }

    public void setUnitRatio(String unitRatio) {
        this.unitRatio = unitRatio;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
