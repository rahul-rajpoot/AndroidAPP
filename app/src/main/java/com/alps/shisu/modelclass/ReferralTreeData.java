package com.alps.shisu.modelclass;

public class ReferralTreeData {


    public ReferralTreeData(String serialNo, String levelValue, String userName, String name, String curr_PBV, String curr_GBV, String pre_PBV, String pre_GBV) {
        this.serialNo = serialNo;
        this.levelValue = levelValue;
        this.userName = userName;
        this.name = name;
        this.curr_PBV = curr_PBV;
        this.curr_GBV = curr_GBV;
        this.pre_PBV = pre_PBV;
        this.pre_GBV = pre_GBV;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }


    public String getCurr_PBV() {
        return curr_PBV;
    }

    public void setCurr_PBV(String curr_PBV) {
        this.curr_PBV = curr_PBV;
    }

    public String getCurr_GBV() {
        return curr_GBV;
    }

    public void setCurr_GBV(String curr_GBV) {
        this.curr_GBV = curr_GBV;
    }

    public String getPre_PBV() {
        return pre_PBV;
    }

    public void setPre_PBV(String pre_PBV) {
        this.pre_PBV = pre_PBV;
    }

    public String getPre_GBV() {
        return pre_GBV;
    }

    public void setPre_GBV(String pre_GBV) {
        this.pre_GBV = pre_GBV;
    }

    String serialNo;
    String levelValue;
    String userName;
    String name;
    String curr_PBV;
    String curr_GBV;
    String pre_PBV;
    String pre_GBV;

}
