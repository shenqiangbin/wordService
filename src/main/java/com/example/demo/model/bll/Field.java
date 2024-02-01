package com.example.demo.model.bll;

import java.util.List;
import java.util.Map;

public class Field {

    private String metaFieldCode;
    private String name;
    private String displayName;
    private String label;

    private boolean enumValueIsCode;
    private Map<String, String> enumMap;
    private List<MetaCode> enumList;

    public Field(String name, String displayName, String label) {
        this.name = name;
        this.displayName = displayName;
        this.label = label;
    }

    public String getMetaFieldCode() {
        return metaFieldCode;
    }

    public void setMetaFieldCode(String metaFieldCode) {
        this.metaFieldCode = metaFieldCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, String> getEnumMap() {
        return enumMap;
    }

    public void setEnumMap(Map<String, String> enumMap) {
        this.enumMap = enumMap;
    }

    public boolean isEnumValueIsCode() {
        return enumValueIsCode;
    }

    public void setEnumValueIsCode(boolean enumValueIsCode) {
        this.enumValueIsCode = enumValueIsCode;
    }

    public List<MetaCode> getEnumList() {
        return enumList;
    }

    public void setEnumList(List<MetaCode> enumList) {
        this.enumList = enumList;
    }
}
