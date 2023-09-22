package com.alps.shisu.modelclass;

public class OrderItemDetails {

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getInvNO() {
        return invNO;
    }

    public void setInvNO(String invNO) {
        this.invNO = invNO;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getbV() {
        return bV;
    }

    public void setbV(String bV) {
        this.bV = bV;
    }

    public String getpV() {
        return pV;
    }

    public void setpV(String pV) {
        this.pV = pV;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderurl() {
        return orderurl;
    }

    public void setOrderurl(String orderurl) {
        this.orderurl = orderurl;
    }

    public String getInvurl() {
        return invurl;
    }

    public void setInvurl(String invurl) {
        this.invurl = invurl;
    }

    private String orderNo;
    private String orderDate;
    private String invNO;
    private String invDate;
    private String amount;
    private String qty;
    private String bV;
    private String pV;
    private String status;

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    private String statusColor;
    private String orderurl;
    private String invurl;

}


