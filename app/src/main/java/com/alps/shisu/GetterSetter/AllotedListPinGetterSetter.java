package com.alps.shisu.GetterSetter;

public class AllotedListPinGetterSetter {
    public String epin;
    public String gendate;
    public String uid;
    public String ubyname;
    public String udate;
    public String statusl;

    public AllotedListPinGetterSetter(String epin, String gendate, String uid, String ubyname, String udate, String statusl) {
        this.epin = epin;
        this.gendate = gendate;
        this.uid = uid;
        this.ubyname = ubyname;
        this.udate = udate;
        this.statusl = statusl;
    }

    public String getEpin() {
        return epin;
    }

    public void setEpin(String epin) {
        this.epin = epin;
    }

    public String getGendate() {
        return gendate;
    }

    public void setGendate(String gendate) {
        this.gendate = gendate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUbyname() {
        return ubyname;
    }

    public void setUbyname(String ubyname) {
        this.ubyname = ubyname;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getStatusl() {
        return statusl;
    }

    public void setStatusl(String statusl) {
        this.statusl = statusl;
    }
}
