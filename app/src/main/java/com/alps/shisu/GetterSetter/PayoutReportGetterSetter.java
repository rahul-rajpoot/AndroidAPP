package com.alps.shisu.GetterSetter;

public class PayoutReportGetterSetter {
    public String payid;

    public String cfR;
    public String cfL;
    public String currentR;
    public String currentL;
    public String totalR;
    public String totalL;

    public String flushes;
    public String reqBonus;
    public  String levelBonus;

    public String getReqBonus() {
        return reqBonus;
    }

    public void setReqBonus(String reqBonus) {
        this.reqBonus = reqBonus;
    }

    public String getLevelBonus() {
        return levelBonus;
    }

    public void setLevelBonus(String levelBonus) {
        this.levelBonus = levelBonus;
    }

    public String getSilver() {
        return silver;
    }

    public void setSilver(String silver) {
        this.silver = silver;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getPlatinum() {
        return platinum;
    }

    public void setPlatinum(String platinum) {
        this.platinum = platinum;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public String getDiamondOverriding() {
        return diamondOverriding;
    }

    public void setDiamondOverriding(String diamondOverriding) {
        this.diamondOverriding = diamondOverriding;
    }

    public String getCarFund() {
        return carFund;
    }

    public void setCarFund(String carFund) {
        this.carFund = carFund;
    }

    public String getHouseFund() {
        return houseFund;
    }

    public void setHouseFund(String houseFund) {
        this.houseFund = houseFund;
    }

    public String silver;
    public String gold;
    public String platinum;
    public String diamond;
    public String diamondOverriding;
    public String carFund;
    public String houseFund;
    public String matching;
    public String totalincomes;
    public String royaltyincome;
    public String pvincomes;

    public PayoutReportGetterSetter(String payid, String cfR, String cfL, String currentR, String currentL, String totalR, String totalL, String flushes, String reqBonus,
                                    String matching, String totalincomes, String silver,
                                    String gold, String platinum, String diamond, String diamondOverriding, String carFund, String houseFund, String levelBonus ) {
        this.payid = payid;
        this.cfR = cfR;
        this.cfL = cfL;
        this.currentR = currentR;
        this.currentL = currentL;
        this.totalR = totalR;
        this.totalL = totalL;
        this.flushes = flushes;
        this.reqBonus = reqBonus;
        this.matching = matching;
        this.totalincomes = totalincomes;
        this.silver = silver;
        this.gold = gold;
        this.platinum = platinum;
        this.diamond = diamond;
        this.diamondOverriding = diamondOverriding;
        this.carFund = carFund;
        this.houseFund = houseFund;
        this.levelBonus=levelBonus;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getCfR() {
        return cfR;
    }

    public void setCfR(String cfR) {
        this.cfR = cfR;
    }

    public String getCfL() {
        return cfL;
    }

    public void setCfL(String cfL) {
        this.cfL = cfL;
    }

    public String getCurrentR() {
        return currentR;
    }

    public void setCurrentR(String currentR) {
        this.currentR = currentR;
    }

    public String getCurrentL() {
        return currentL;
    }

    public void setCurrentL(String currentL) {
        this.currentL = currentL;
    }

    public String getTotalR() {
        return totalR;
    }

    public void setTotalR(String totalR) {
        this.totalR = totalR;
    }

    public String getTotalL() {
        return totalL;
    }

    public void setTotalL(String totalL) {
        this.totalL = totalL;
    }

    public String getFlushes() {
        return flushes;
    }

    public void setFlushes(String flushes) {
        this.flushes = flushes;
    }

    public String getMatching() {
        return matching;
    }

    public void setMatching(String matching) {
        this.matching = matching;
    }

    public String getTotalincomes() {
        return totalincomes;
    }

    public void setTotalincomes(String totalincomes) {
        this.totalincomes = totalincomes;
    }

    public String getRoyaltyincome() {
        return royaltyincome;
    }

    public void setRoyaltyincome(String royaltyincome) {
        this.royaltyincome = royaltyincome;
    }

    public String getPvincomes() {
        return pvincomes;
    }

    public void setPvincomes(String pvincomes) {
        this.pvincomes = pvincomes;
    }
}
