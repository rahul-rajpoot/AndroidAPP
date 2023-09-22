package com.alps.shisu.modelclass;

import java.io.Serializable;

public class StateDetails implements Serializable {

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getDistID() {
        return distID;
    }

    public void setDistID(String distID) {
        this.distID = distID;
    }

    public String getcTID() {
        return cTID;
    }

    public void setcTID(String cTID) {
        this.cTID = cTID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    private String sID;
    private String stateName;
    private String distName;
    private String distID;
    private String cTID;
    private String cityName;

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    private String pinCode;

}
