package com.alps.shisu.GetterSetter;

public class DirectSponsorGetterSetter {
    public String nameds;
    public String idds;
    public String packagestatus;
    public String designation;
    public String regdate;
    public String regno;
    public String statusds;
    public String SponsorCount;
    public String C_GBV;
    public String C_PBV;
    public String C_TBV;
    public String P_GBV;
    public String P_PBV;
    public String P_TBV;
    public String T_GBV;
    public String T_PBV;
    public  String slab;

    public String getC_TBV() {
        return C_TBV;
    }

    public void setC_TBV(String c_TBV) {
        C_TBV = c_TBV;
    }

    public String getP_TBV() {
        return P_TBV;
    }

    public void setP_TBV(String p_TBV) {
        P_TBV = p_TBV;
    }

    public String getT_GBV() {
        return T_GBV;
    }

    public void setT_GBV(String t_GBV) {
        T_GBV = t_GBV;
    }

    public String getT_PBV() {
        return T_PBV;
    }

    public void setT_PBV(String t_PBV) {
        T_PBV = t_PBV;
    }

    public String getT_TBV() {
        return T_TBV;
    }

    public void setT_TBV(String t_TBV) {
        T_TBV = t_TBV;
    }

    public String T_TBV;

    public String getSlab() {
        return slab;
    }

    public void setSlab(String slab) {
        slab = slab;
    }


    public DirectSponsorGetterSetter(String nameds, String idds, String packagestatus, String regdate,
                                     String regno, String statusds, String sponsorCount, String C_GBV,
                                     String C_PBV, String C_TBV, String P_GBV, String P_PBV, String P_TBV,
                                     String T_GBV, String T_PBV, String T_TBV, String Designation, String slab) {
        this.nameds = nameds;
        this.idds = idds;
        this.packagestatus = packagestatus;
        this.regdate = regdate;
        this.regno = regno;
        this.statusds = statusds;
        SponsorCount = sponsorCount;
        this.C_GBV = C_GBV;
        this.C_PBV = C_PBV;
        this.C_TBV = C_TBV;
        this.P_GBV = P_GBV;
        this.P_PBV = P_PBV;
        this.P_TBV = P_TBV;
        this.T_GBV = T_GBV;
        this.T_PBV = T_PBV;
        this.T_TBV = T_TBV;
        this.designation=Designation;
        this.slab=slab;
    }

    public String getC_GBV() {
        return C_GBV;
    }

    public void setC_GBV(String c_GBV) {
        C_GBV = c_GBV;
    }

    public String getC_PBV() {
        return C_PBV;
    }

    public void setC_PBV(String c_PBV) {
        C_PBV = c_PBV;
    }

    public String getP_GBV() {
        return P_GBV;
    }

    public void setP_GBV(String p_GBV) {
        P_GBV = p_GBV;
    }

    public String getP_PBV() {
        return P_PBV;
    }

    public void setP_PBV(String p_PBV) {
        P_PBV = p_PBV;
    }

    public String getNameds() {
        return nameds;
    }

    public void setNameds(String nameds) {
        this.nameds = nameds;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation =designation;
    }


    public String getIdds() {
        return idds;
    }

    public void setIdds(String idds) {
        this.idds = idds;
    }

    public String getPackagestatus() {
        return packagestatus;
    }

    public void setPackagestatus(String packagestatus) {
        this.packagestatus = packagestatus;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getStatusds() {
        return statusds;
    }

    public void setStatusds(String statusds) {
        this.statusds = statusds;
    }

    public String getSponsorCount() {
        return SponsorCount;
    }

    public void setSponsorCount(String sponsorCount) {
        SponsorCount = sponsorCount;
    }
}