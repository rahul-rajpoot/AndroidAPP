package com.alps.shisu.GetterSetter;

public class TeamListGetterSetter {
    public String ids;
    public String statuss;
    public String names;
    public String directs;
    public String repurchases;

    public TeamListGetterSetter(String ids, String statuss, String names, String directs, String repurchases) {
        this.ids = ids;
        this.statuss = statuss;
        this.names = names;
        this.directs = directs;
        this.repurchases = repurchases;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDirects() {
        return directs;
    }

    public void setDirects(String directs) {
        this.directs = directs;
    }

    public String getRepurchases() {
        return repurchases;
    }

    public void setRepurchases(String repurchases) {
        this.repurchases = repurchases;
    }
}
