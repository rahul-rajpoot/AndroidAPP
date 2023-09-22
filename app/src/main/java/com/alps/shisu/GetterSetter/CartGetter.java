package com.alps.shisu.GetterSetter;

public class CartGetter {
    // String Sno;
    String Productesid;
    String ProductesName;
    String ProductesQty;
    String ProductesBv;
    Object ProductesCp;
    String ProductesMrpc;
    String ProductesImage;
    String UserLoginid;
    String TotalAMounts;

    public CartGetter(String productesid, String productesName, String productesQty, String productesBv, Object productesCp, String productesMrpc, String productesImage, String userLoginid, String totalAMounts) {
        Productesid = productesid;
        ProductesName = productesName;
        ProductesQty = productesQty;
        ProductesBv = productesBv;
        ProductesCp = productesCp;
        ProductesMrpc = productesMrpc;
        ProductesImage = productesImage;
        UserLoginid = userLoginid;
        TotalAMounts = totalAMounts;
    }

    public String getProductesid() {
        return Productesid;
    }

    public void setProductesid(String productesid) {
        Productesid = productesid;
    }

    public String getProductesName() {
        return ProductesName;
    }

    public void setProductesName(String productesName) {
        ProductesName = productesName;
    }

    public String getProductesQty() {
        return ProductesQty;
    }

    public void setProductesQty(String productesQty) {
        ProductesQty = productesQty;
    }

    public String getProductesBv() {
        return ProductesBv;
    }

    public void setProductesBv(String productesBv) {
        ProductesBv = productesBv;
    }

    public Object getProductesCp() {
        return ProductesCp;
    }

    public void setProductesCp(Object productesCp) {
        ProductesCp = productesCp;
    }

    public String getProductesMrpc() {
        return ProductesMrpc;
    }

    public void setProductesMrpc(String productesMrpc) {
        ProductesMrpc = productesMrpc;
    }

    public String getProductesImage() {
        return ProductesImage;
    }

    public void setProductesImage(String productesImage) {
        ProductesImage = productesImage;
    }

    public String getUserLoginid() {
        return UserLoginid;
    }

    public void setUserLoginid(String userLoginid) {
        UserLoginid = userLoginid;
    }

    public String getTotalAMounts() {
        return TotalAMounts;
    }

    public void setTotalAMounts(String totalAMounts) {
        TotalAMounts = totalAMounts;
    }
}
