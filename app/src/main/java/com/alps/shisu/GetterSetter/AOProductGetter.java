package com.alps.shisu.GetterSetter;

public class AOProductGetter {
    String BVs;
    String Dps;
    String ImageUrl;
    String MRP;
    String Pid;
    String Pname;
    String PV;

    public AOProductGetter(String BVs, String dps, String imageUrl, String MRP, String pid, String pname, String PV) {
        this.BVs = BVs;
        Dps = dps;
        ImageUrl = imageUrl;
        this.MRP = MRP;
        Pid = pid;
        Pname = pname;
        this.PV = PV;
    }

    public String getBVs() {
        return BVs;
    }

    public void setBVs(String BVs) {
        this.BVs = BVs;
    }

    public String getDps() {
        return Dps;
    }

    public void setDps(String dps) {
        Dps = dps;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getPV() {
        return PV;
    }

    public void setPV(String PV) {
        this.PV = PV;
    }
}