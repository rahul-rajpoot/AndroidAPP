package com.alps.shisu.GetterSetter;

public class TdsReportGetterSetter {

    public String cutoofmonth;
    public String tdstotalincome;
    public String tdsratetds;
    public String tdsamounttds;
    public String tdsstatus;

    public TdsReportGetterSetter(String cutoofmonth, String tdstotalincome, String tdsratetds, String tdsamounttds, String tdsstatus) {
        this.cutoofmonth = cutoofmonth;
        this.tdstotalincome = tdstotalincome;
        this.tdsratetds = tdsratetds;
        this.tdsamounttds = tdsamounttds;
        this.tdsstatus = tdsstatus;
    }

    public String getCutoofmonth() {
        return cutoofmonth;
    }

    public void setCutoofmonth(String cutoofmonth) {
        this.cutoofmonth = cutoofmonth;
    }

    public String getTdstotalincome() {
        return tdstotalincome;
    }

    public void setTdstotalincome(String tdstotalincome) {
        this.tdstotalincome = tdstotalincome;
    }

    public String getTdsratetds() {
        return tdsratetds;
    }

    public void setTdsratetds(String tdsratetds) {
        this.tdsratetds = tdsratetds;
    }

    public String getTdsamounttds() {
        return tdsamounttds;
    }

    public void setTdsamounttds(String tdsamounttds) {
        this.tdsamounttds = tdsamounttds;
    }

    public String getTdsstatus() {
        return tdsstatus;
    }

    public void setTdsstatus(String tdsstatus) {
        this.tdsstatus = tdsstatus;
    }
}
