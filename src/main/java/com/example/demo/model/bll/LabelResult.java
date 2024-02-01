package com.example.demo.model.bll;

import com.example.demo.myenum.IndicatorLabel;

public class LabelResult {

    private IndicatorLabel indicatorLabel;
    private String esCommonFieldName;
    private String commonFieldName;
    private String regionFormat;

    public IndicatorLabel getIndicatorLabel() {
        return indicatorLabel;
    }

    public void setIndicatorLabel(IndicatorLabel indicatorLabel) {
        this.indicatorLabel = indicatorLabel;
    }

    public String getEsCommonFieldName() {
        return esCommonFieldName;
    }

    public void setEsCommonFieldName(String esCommonFieldName) {
        this.esCommonFieldName = esCommonFieldName;
    }

    public String getCommonFieldName() {
        return commonFieldName;
    }

    public void setCommonFieldName(String commonFieldName) {
        this.commonFieldName = commonFieldName;
    }

    public String getRegionFormat() {
        return regionFormat;
    }

    public void setRegionFormat(String regionFormat) {
        this.regionFormat = regionFormat;
    }

    public static LabelResult convertToLabelResult(String dataBusinessType) {

        dataBusinessType = dataBusinessType.replace("（","(");
        dataBusinessType = dataBusinessType.replace("）",")");

        if(dataBusinessType.contains("经度")){
            dataBusinessType = "经度";
        }
        if(dataBusinessType.contains("纬度")){
            dataBusinessType = "纬度";
        }

        LabelResult labelResult = new LabelResult();
        switch (dataBusinessType) {
            case "经度":
                labelResult.setIndicatorLabel(IndicatorLabel.lng);
                labelResult.setEsCommonFieldName(IndicatorLabel.lng.toString());
                labelResult.setCommonFieldName(IndicatorLabel.lng.getName());
                break;
            case "纬度":
                labelResult.setIndicatorLabel(IndicatorLabel.lat);
                labelResult.setEsCommonFieldName(IndicatorLabel.lat.toString());
                labelResult.setCommonFieldName(IndicatorLabel.lat.getName());
                break;
            case "行政区划编码":
                labelResult.setIndicatorLabel(IndicatorLabel.areaCode);
                labelResult.setEsCommonFieldName(IndicatorLabel.areaCode.toString());
                labelResult.setCommonFieldName(IndicatorLabel.areaCode.getName());
                break;
            case "身份证":
                labelResult.setIndicatorLabel(IndicatorLabel.idCard);
                labelResult.setEsCommonFieldName(IndicatorLabel.idCard.toString());
                labelResult.setCommonFieldName(IndicatorLabel.idCard.getName());
                break;
            case "手机号码":
                labelResult.setIndicatorLabel(IndicatorLabel.mobilephone);
                labelResult.setEsCommonFieldName(IndicatorLabel.mobilephone.toString());
                labelResult.setCommonFieldName(IndicatorLabel.mobilephone.getName());
                break;
            case "固定电话":
                labelResult.setIndicatorLabel(IndicatorLabel.telephone);
                labelResult.setEsCommonFieldName(IndicatorLabel.telephone.toString());
                labelResult.setCommonFieldName(IndicatorLabel.telephone.getName());
                break;
            case "电子邮箱":
                labelResult.setIndicatorLabel(IndicatorLabel.email);
                labelResult.setEsCommonFieldName(IndicatorLabel.email.toString());
                labelResult.setCommonFieldName(IndicatorLabel.email.getName());
                break;
            case "邮编":
                labelResult.setIndicatorLabel(IndicatorLabel.postcode);
                labelResult.setEsCommonFieldName(IndicatorLabel.postcode.toString());
                labelResult.setCommonFieldName(IndicatorLabel.postcode.getName());
                break;
            case "性别":
                labelResult.setIndicatorLabel(IndicatorLabel.gender);
                labelResult.setEsCommonFieldName(IndicatorLabel.gender.toString());
                labelResult.setCommonFieldName(IndicatorLabel.gender.getName());
                break;
            default:
                return null;
        }
        return labelResult;
    }
}
