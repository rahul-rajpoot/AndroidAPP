package com.alps.shisu.GetterSetter;

public class ActivitionOrderGetterSetter {
    String CatName;
    String Imgurl;
    String CategoryID;

    public ActivitionOrderGetterSetter(String catName, String imgurl, String categoryID) {
        CatName = catName;
        Imgurl = imgurl;
        CategoryID = categoryID;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String imgurl) {
        Imgurl = imgurl;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
