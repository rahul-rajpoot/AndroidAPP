package com.alps.shisu.GetterSetter;

public class PayoutDeductionGetterSetter {

    public String pd_payid;
    public String pd_totalincomes;
    public String pd_tdsamount;
    public String pd_tdsrate;
    public String pd_proamount;
    public String pd_prorate;
    public String pd_lockamt;
    public String pd_totaldeduction;
    public String pd_netpayable;
    public String status;

    public PayoutDeductionGetterSetter(String pd_payid, String pd_totalincomes, String pd_tdsamount, String pd_tdsrate, String pd_proamount, String pd_prorate, String pd_lockamt, String pd_totaldeduction, String pd_netpayable, String status) {
        this.pd_payid = pd_payid;
        this.pd_totalincomes = pd_totalincomes;
        this.pd_tdsamount = pd_tdsamount;
        this.pd_tdsrate = pd_tdsrate;
        this.pd_proamount = pd_proamount;
        this.pd_prorate = pd_prorate;
        this.pd_lockamt = pd_lockamt;
        this.pd_totaldeduction = pd_totaldeduction;
        this.pd_netpayable = pd_netpayable;
        this.status = status;
    }

    public String getPd_payid() {
        return pd_payid;
    }

    public void setPd_payid(String pd_payid) {
        this.pd_payid = pd_payid;
    }

    public String getPd_totalincomes() {
        return pd_totalincomes;
    }

    public void setPd_totalincomes(String pd_totalincomes) {
        this.pd_totalincomes = pd_totalincomes;
    }

    public String getPd_tdsamount() {
        return pd_tdsamount;
    }

    public void setPd_tdsamount(String pd_tdsamount) {
        this.pd_tdsamount = pd_tdsamount;
    }

    public String getPd_tdsrate() {return pd_tdsrate;}
    public void setPd_tdsrate(String pd_tdsrate) {
        this.pd_tdsrate = pd_tdsrate;
    }

    public String getPd_proamount() {
        return pd_proamount;
    }

    public void setPd_proamount(String pd_proamount) {
        this.pd_proamount = pd_proamount;
    }


    public String getPd_prorate() {return pd_prorate;}
    public void setPd_prorate(String pd_prorate) {
        this.pd_prorate = pd_prorate;
    }

    public String getPd_lockamt() {
        return pd_lockamt;
    }

    public void setPd_lockamt(String pd_lockamt) {
        this.pd_lockamt = pd_lockamt;
    }

    public String getPd_totaldeduction() {
        return pd_totaldeduction;
    }

    public void setPd_totaldeduction(String pd_totaldeduction) {
        this.pd_totaldeduction = pd_totaldeduction;
    }

    public String getPd_netpayable() {
        return pd_netpayable;
    }

    public void setPd_netpayable(String pd_netpayable) {
        this.pd_netpayable = pd_netpayable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
