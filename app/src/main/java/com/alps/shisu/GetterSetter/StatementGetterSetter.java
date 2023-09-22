package com.alps.shisu.GetterSetter;

public class StatementGetterSetter {
    public String remarks;
    public String amount;
    public String paydate;
    public String statusstatement;

    public StatementGetterSetter(String remarks, String amount, String paydate, String statusstatement) {
        this.remarks = remarks;
        this.amount = amount;
        this.paydate = paydate;
        this.statusstatement = statusstatement;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public String getStatusstatement() {
        return statusstatement;
    }

    public void setStatusstatement(String statusstatement) {
        this.statusstatement = statusstatement;
    }
}
