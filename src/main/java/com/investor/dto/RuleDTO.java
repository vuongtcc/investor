package com.investor.dto;

public class RuleDTO {
    String code, change, volumn,type;

    public RuleDTO(String code, String change, String volumn, String type) {
        this.code = code;
        this.change = change;
        this.volumn = volumn;
        this.type = type;
    }

    @Override
    public String toString() {
        return "RuleDTO{" +
                "code='" + code + '\'' +
                ", change='" + change + '\'' +
                ", volumn='" + volumn + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public RuleDTO() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
