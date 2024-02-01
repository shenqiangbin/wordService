package com.example.demo.model.bll;

/* 对应数据库中的 v_md_instance_search_relation 表 */
public class MetaRelation {

    private String id;
    private String name;

    private String primaryTalbe;
    private String primaryTalbeTitle;
    private String primaryTalbeField;

    private String secondTalbe;
    private String secondTalbeTitle;
    private String secondTalbeField;

    private String middleTalbe;
    private String middleTalbeTitle;
    private String middleTalbePrimaryField;
    private String middleTalbeSecondsField;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryTalbe() {
        return primaryTalbe;
    }

    public void setPrimaryTalbe(String primaryTalbe) {
        this.primaryTalbe = primaryTalbe;
    }

    public String getPrimaryTalbeTitle() {
        return primaryTalbeTitle;
    }

    public void setPrimaryTalbeTitle(String primaryTalbeTitle) {
        this.primaryTalbeTitle = primaryTalbeTitle;
    }

    public String getPrimaryTalbeField() {
        return primaryTalbeField;
    }

    public void setPrimaryTalbeField(String primaryTalbeField) {
        this.primaryTalbeField = primaryTalbeField;
    }

    public String getSecondTalbe() {
        return secondTalbe;
    }

    public void setSecondTalbe(String secondTalbe) {
        this.secondTalbe = secondTalbe;
    }

    public String getSecondTalbeTitle() {
        return secondTalbeTitle;
    }

    public void setSecondTalbeTitle(String secondTalbeTitle) {
        this.secondTalbeTitle = secondTalbeTitle;
    }

    public String getSecondTalbeField() {
        return secondTalbeField;
    }

    public void setSecondTalbeField(String secondTalbeField) {
        this.secondTalbeField = secondTalbeField;
    }

    public String getMiddleTalbe() {
        return middleTalbe;
    }

    public void setMiddleTalbe(String middleTalbe) {
        this.middleTalbe = middleTalbe;
    }

    public String getMiddleTalbeTitle() {
        return middleTalbeTitle;
    }

    public void setMiddleTalbeTitle(String middleTalbeTitle) {
        this.middleTalbeTitle = middleTalbeTitle;
    }

    public String getMiddleTalbePrimaryField() {
        return middleTalbePrimaryField;
    }

    public void setMiddleTalbePrimaryField(String middleTalbePrimaryField) {
        this.middleTalbePrimaryField = middleTalbePrimaryField;
    }

    public String getMiddleTalbeSecondsField() {
        return middleTalbeSecondsField;
    }

    public void setMiddleTalbeSecondsField(String middleTalbeSecondsField) {
        this.middleTalbeSecondsField = middleTalbeSecondsField;
    }
}
