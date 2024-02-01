package com.example.demo.model.bll;

/**
 * 构成 规则 Rule 的 Json 项
 */
public class RuleItem {

    private String ruleID;
    private String targetName;
    private String groupName;
    private String unit;


    private String outputType;
    private String area;

    public RuleItem(String ruleID, String targetName) {
        this.ruleID = ruleID;
        this.targetName = targetName;
    }

    public RuleItem(String ruleID, String targetName, String unit) {
        this.ruleID = ruleID;
        this.targetName = targetName;
        this.unit = unit;
    }

    public RuleItem(String ruleID, String targetName, String groupName, String unit, String outputType) {
        this.ruleID = ruleID;
        this.targetName = targetName;
        this.groupName = groupName;
        this.unit = unit;
        this.outputType = outputType;
    }

    public RuleItem(String ruleID, String targetName, String groupName, String unit, String outputType, String area) {
        this.ruleID = ruleID;
        this.targetName = targetName;
        this.groupName = groupName;
        this.unit = unit;
        this.outputType = outputType;
        this.area = area;
    }

    public String getRuleID() {
        return ruleID;
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
