package com.alps.shisu.modelclass;

public class DepositRequestItem {
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getBankReciept() {
        return bankReciept;
    }

    public void setBankReciept(String bankReciept) {
        this.bankReciept = bankReciept;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDepositRequest() {
        return depositRequest;
    }

    public void setDepositRequest(String depositRequest) {
        this.depositRequest = depositRequest;
    }

    private String depositRequest;
    private String amount;
    private String paymentMode;
    private String bankName;
    private String refNo;
    private String bankReciept;
    private String paymentDate;

}
