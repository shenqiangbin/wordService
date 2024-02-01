package com.example.demo.model.bll;

/* 对应数据库中的 v_md_instance_search_code （枚举码表） 表 */
public class MetaCode {

    private String code_id;
    private String kind_id;
    private String type_id;
    private String type_name;
    private String f_type_id;
    private String provincial_id;
    private String provincial_name;
    private String remark;
    private String f_type_code;

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }

    public String getKind_id() {
        return kind_id;
    }

    public void setKind_id(String kind_id) {
        this.kind_id = kind_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getF_type_id() {
        return f_type_id;
    }

    public void setF_type_id(String f_type_id) {
        this.f_type_id = f_type_id;
    }

    public String getProvincial_id() {
        return provincial_id;
    }

    public void setProvincial_id(String provincial_id) {
        this.provincial_id = provincial_id;
    }

    public String getProvincial_name() {
        return provincial_name;
    }

    public void setProvincial_name(String provincial_name) {
        this.provincial_name = provincial_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getF_type_code() {
        return f_type_code;
    }

    public void setF_type_code(String f_type_code) {
        this.f_type_code = f_type_code;
    }
}
