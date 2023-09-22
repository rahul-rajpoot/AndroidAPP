package com.alps.shisu.GetterSetter;

public class PurchaseListGetter {
    String Amount;
    String BV;
    String InvDate;
    String InvNO;
    String OrderDate;
    String OrderNo;
    String Qty;
    String Status;
    String OrderNourl;
    String InvNOutl;
    String PV;

    public PurchaseListGetter(String amount, String BV, String invDate, String invNO, String orderDate, String orderNo, String qty, String status, String orderNourl, String invNOutl, String PV) {
        Amount = amount;
        this.BV = BV;
        InvDate = invDate;
        InvNO = invNO;
        OrderDate = orderDate;
        OrderNo = orderNo;
        Qty = qty;
        Status = status;
        OrderNourl = orderNourl;
        InvNOutl = invNOutl;
        this.PV = PV;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getBV() {
        return BV;
    }

    public void setBV(String BV) {
        this.BV = BV;
    }

    public String getInvDate() {
        return InvDate;
    }

    public void setInvDate(String invDate) {
        InvDate = invDate;
    }

    public String getInvNO() {
        return InvNO;
    }

    public void setInvNO(String invNO) {
        InvNO = invNO;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderNourl() {
        return OrderNourl;
    }

    public void setOrderNourl(String orderNourl) {
        OrderNourl = orderNourl;
    }

    public String getInvNOutl() {
        return InvNOutl;
    }

    public void setInvNOutl(String invNOutl) {
        InvNOutl = invNOutl;
    }

    public String getPV() {
        return PV;
    }

    public void setPV(String PV) {
        this.PV = PV;
    }
}

   /* public PurchaseListGetter(String amount, String BV, String invDate, String invNO, String orderDate, String orderNo, String qty, String status, String orderNourl, String invNOutl) {
        Amount = amount;
        this.BV = BV;
        InvDate = invDate;
        InvNO = invNO;
        OrderDate = orderDate;
        OrderNo = orderNo;
        Qty = qty;
        Status = status;
        OrderNourl = orderNourl;
        InvNOutl = invNOutl;
    }

    public String getAmount() {
        return Amount;
    }

    public String getBV() {
        return BV;
    }

    public String getInvDate() {
        return InvDate;
    }

    public String getInvNO() {
        return InvNO;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public String getQty() {
        return Qty;
    }

    public String getStatus() {
        return Status;
    }

    public String getOrderNourl() {
        return OrderNourl;
    }

    public String getInvNOutl() {
        return InvNOutl;
    }
}*/
