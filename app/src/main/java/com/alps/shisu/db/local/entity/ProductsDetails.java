package com.alps.shisu.db.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "ProductsDetails", primaryKeys = {"user_id", "product_id"})
public class ProductsDetails {


    //Product id
    @NonNull
    @ColumnInfo(name = "product_id")
    private String product_id;

    //User id
    @NonNull
    @ColumnInfo(name = "user_id")
    private String user_id;

    //Product Name
    @NonNull
    @ColumnInfo(name = "product_name")
    private String product_name;

    @NonNull
    @ColumnInfo(name = "mrp")
    private String mrp;

    @NonNull
    @ColumnInfo(name = "product_code")
    private String product_code;

    @ColumnInfo(name = "dp")
    private String dp;

    @ColumnInfo(name = "bv")
    private String bv;

    @ColumnInfo(name = "pv")
    private String pv;

    @ColumnInfo(name = "quantity")
    private String quantity;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "size")
    private String size;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "extra_margin")
    private String extra_margin;

    @ColumnInfo(name = "paymet_mode")
    private String paymet_mode;

    @ColumnInfo(name = "img")
    private String img;

    @ColumnInfo(name = "weight")
    private String weight;

    @ColumnInfo(name = "category_name")
    private String category_name;

    @NonNull
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(@NonNull String product_id) {
        this.product_id = product_id;
    }

    @NonNull
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(@NonNull String product_name) {
        this.product_name = product_name;
    }

    @NonNull
    public String getMrp() {
        return mrp;
    }

    public void setMrp(@NonNull String mrp) {
        this.mrp = mrp;
    }

    @NonNull
    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(@NonNull String product_code) {
        this.product_code = product_code;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getExtra_margin() {
        return extra_margin;
    }

    public void setExtra_margin(String extra_margin) {
        this.extra_margin = extra_margin;
    }

    public String getPaymet_mode() {
        return paymet_mode;
    }

    public void setPaymet_mode(String paymet_mode) {
        this.paymet_mode = paymet_mode;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @NonNull
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(@NonNull String user_id) {
        this.user_id = user_id;
    }

}
