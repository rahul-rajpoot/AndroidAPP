package com.alps.shisu.GetterSetter;

public class DeliveryReportGetter {
    String Amountdr;
    String BVdr;
    String InvDatedr;
    String InvNOdr;
    String OrderDatedr;
    String OrderNodr;
    String Qtydr;
    String Status;
    String OrderNourl;
    String InvNOutl;
    String PV;

    public DeliveryReportGetter(String amountdr, String BVdr, String invDatedr, String invNOdr, String orderDatedr, String orderNodr, String qtydr, String status, String orderNourl, String invNOutl, String PV) {
        Amountdr = amountdr;
        this.BVdr = BVdr;
        InvDatedr = invDatedr;
        InvNOdr = invNOdr;
        OrderDatedr = orderDatedr;
        OrderNodr = orderNodr;
        Qtydr = qtydr;
        Status = status;
        OrderNourl = orderNourl;
        InvNOutl = invNOutl;
        this.PV = PV;
    }

    public String getAmountdr() {
        return Amountdr;
    }

    public void setAmountdr(String amountdr) {
        Amountdr = amountdr;
    }

    public String getBVdr() {
        return BVdr;
    }

    public void setBVdr(String BVdr) {
        this.BVdr = BVdr;
    }

    public String getInvDatedr() {
        return InvDatedr;
    }

    public void setInvDatedr(String invDatedr) {
        InvDatedr = invDatedr;
    }

    public String getInvNOdr() {
        return InvNOdr;
    }

    public void setInvNOdr(String invNOdr) {
        InvNOdr = invNOdr;
    }

    public String getOrderDatedr() {
        return OrderDatedr;
    }

    public void setOrderDatedr(String orderDatedr) {
        OrderDatedr = orderDatedr;
    }

    public String getOrderNodr() {
        return OrderNodr;
    }

    public void setOrderNodr(String orderNodr) {
        OrderNodr = orderNodr;
    }

    public String getQtydr() {
        return Qtydr;
    }

    public void setQtydr(String qtydr) {
        Qtydr = qtydr;
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

   /* public DeliveryReportGetter(String amountdr, String BVdr, String invDatedr, String invNOdr, String orderDatedr, String orderNodr, String qtydr, String status, String orderNourl, String invNOutl) {
        Amountdr = amountdr;
        this.BVdr = BVdr;
        InvDatedr = invDatedr;
        InvNOdr = invNOdr;
        OrderDatedr = orderDatedr;
        OrderNodr = orderNodr;
        Qtydr = qtydr;
        Status = status;
        OrderNourl = orderNourl;
        InvNOutl = invNOutl;
    }

    public String getAmountdr() {
        return Amountdr;
    }

    public String getBVdr() {
        return BVdr;
    }

    public String getInvDatedr() {
        return InvDatedr;
    }

    public String getInvNOdr() {
        return InvNOdr;
    }

    public String getOrderDatedr() {
        return OrderDatedr;
    }

    public String getOrderNodr() {
        return OrderNodr;
    }

    public String getQtydr() {
        return Qtydr;
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
