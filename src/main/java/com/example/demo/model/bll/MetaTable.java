package com.example.demo.model.bll;

public class MetaTable {

    private String syscode;
    private String enName;
    private String cnName;
    private String tablecode;
    /**
     * 业务或技术
     */
    private String busOrTech;
    /**
     * 表分类
     */
    private String category;
    private String keywords;
    /**
     * 是否提供检索
     */
    private String enableSearch;

    /**
     * 对应的 ES 信息
     */
    private String esinfo;


    public String getEsinfo() {
        return esinfo;
    }

    public void setEsinfo(String esinfo) {
        this.esinfo = esinfo;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getTablecode() {
        return tablecode;
    }

    public void setTablecode(String tablecode) {
        this.tablecode = tablecode;
    }

    public String getBusOrTech() {
        return busOrTech;
    }

    public void setBusOrTech(String busOrTech) {
        this.busOrTech = busOrTech;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEnableSearch() {
        return enableSearch;
    }

    public void setEnableSearch(String enableSearch) {
        this.enableSearch = enableSearch;
    }
}
