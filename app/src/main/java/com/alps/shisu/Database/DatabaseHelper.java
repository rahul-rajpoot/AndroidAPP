package com.alps.shisu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.alps.shisu.GetterSetter.CartGetter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "WCCart.db";
    public static final String TABLE_NAME = "Demo_cart_value";
    public static final String TABLE_NAME2 = "WCCart_product";
    public static final String COL_0 = "SerialNo";
    public static final String COL_1 = "PID";
    public static final String COL_2 = "ProductName";
    public static final String COL_3 = "ProductImg";
    public static final String COL_4 = "Quantity";
    public static final String COL_5 = "MRP";
    public static final String COL_6 = "DP";
    public static final String COL_7 = "BV";
    public static final String COL_24 = "PV";
    public static final String COL_8 = "LoginID";
    //  public static final String COL_9 = "TotalAmount";

    public static final String COL_9 = "Name";
    public static final String COL_10 = "Emailid";
    public static final String COL_11 = "MobileNo";
    public static final String COL_12 = "Address";
    public static final String COL_13 = "CID";
    public static final String COL_14 = "CTID";
    public static final String COL_15 = "SID";
    public static final String COL_16 = "DISTID";
    public static final String COL_17 = "Pincode";

    public static final String COL_18 = "Paymode";
    public static final String COL_19 = "V_Amount";
    public static final String COL_20 = "TotalAmount";
    public static final String COL_21 = "BankName";
    public static final String COL_22 = "RefNo";
    public static final String COL_23 = "TotalAmount";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (SerialNo INTEGER PRIMARY KEY AUTOINCREMENT,PID INTEGER ,ProductName TEXT,ProductImg TEXT,Quantity TEXT,MRP TEXT,DP TEXT,BV TEXT, PV TEXT,LoginID TEXT,Name TEXT," +
                "Emailid TEXT,MobileNo TEXT,Address TEXT,CID TEXT, CTID TEXT,SID TEXT,DISTID TEXT,Pincode TEXT,Paymode TEXT,V_Amount TEXT,TotalAmount TEXT,BankName TEXT,RefNo TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (SerialNo INTEGER PRIMARY KEY AUTOINCREMENT,PID INTEGER ,ProductName TEXT,ProductImg TEXT,Quantity TEXT,MRP TEXT,DP TEXT,BV TEXT,PV TEXT,LoginID TEXT,TotalAmount TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        Log.e("table", String.valueOf(db));
        onCreate(db);
    }
    /*
    public void update(PaymentPostGetter paymentPostGetter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put(COL_18, paymentPostGetter.getPaymode());
        con.put(COL_21, paymentPostGetter.getBankName());
        con.put(COL_22, paymentPostGetter.getRefNo());
        con.put(COL_18, paymentPostGetter.getV_Amount());
        con.put(COL_14, paymentPostGetter.getCTID());
        con.put(COL_13, paymentPostGetter.getCID());
        con.put(COL_15, paymentPostGetter.getSID());
        con.put(COL_16, paymentPostGetter.getDISTID());
        con.put(COL_17, paymentPostGetter.getPincode());
        con.put(COL_12, paymentPostGetter.getAddress());
        con.put(COL_11, paymentPostGetter.getMobileNo());
        con.put(COL_10, paymentPostGetter.getEmailid());
        db.update(TABLE_NAME, con, COL_8 + " = ?", new String[]{paymentPostGetter.getLoginID()});
        db.close();
    }
    */
    public boolean checkCartProduct(String pid,String Userid){
        boolean flag;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor;
        String Sql=String.format("SELECT * From "+TABLE_NAME2+" WHERE LoginID='%s' AND PID='%s'",Userid,pid);

        cursor=db.rawQuery(Sql,null);
        if (cursor.getCount()>0){
            flag= true;
        }else {
            flag=false;
        }
        cursor.close();
        return flag;
    }
    public void CartUpdate(String pid,String userid) {
        SQLiteDatabase db = getReadableDatabase();
        String query=String.format("UPDATE "+TABLE_NAME2+" SET Quantity=Quantity+1  WHERE PID='%s' AND LoginID='%s'",pid,userid);
        db.execSQL(query);
    }
    public void addtoCart(CartGetter cartGetter){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT OR REPLACE INTO "+TABLE_NAME2+"(PID,ProductName,ProductImg,Quantity,MRP,DP,BV,LoginID,TotalAmount) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s');",
                cartGetter.getProductesid(),
                cartGetter.getProductesName(),
                cartGetter.getProductesImage(),
                cartGetter.getProductesQty(),
                cartGetter.getProductesMrpc(),
                cartGetter.getProductesCp(),
                cartGetter.getProductesBv(),
                cartGetter.getUserLoginid(),
                cartGetter.getTotalAMounts());
        db.execSQL(query);
    }
    public boolean insertData(String pid,String pname,String pimg ,String pqty,String pmrp,String pdpcp,String pbv,String pv,String userloginid,
                              String name,String email,String phone,String address,String countryid,String cityid,String stateid,String disid,String pincode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,pid);
        contentValues.put(COL_2,pname);
        contentValues.put(COL_3,pimg);
        contentValues.put(COL_4,pqty);
        contentValues.put(COL_5,pmrp);
        contentValues.put(COL_6,pdpcp);
        contentValues.put(COL_7,pbv);
        contentValues.put(COL_24,pv);
        contentValues.put(COL_8,userloginid);
        contentValues.put(COL_9,name);
        contentValues.put(COL_10,email);
        contentValues.put(COL_11,phone);
        contentValues.put(COL_12,address);
        contentValues.put(COL_13,countryid);
        contentValues.put(COL_14,cityid);
        contentValues.put(COL_15,stateid);
        contentValues.put(COL_16,disid);
        contentValues.put(COL_17,pincode);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    /*
       public Cursor getData(String userid) {
           SQLiteDatabase db = this.getWritableDatabase();
           Cursor res = db.rawQuery("select * from "+TABLE_NAME +"where UserLoginId="+userid,null);
           return res;
       }

   */
    public String TotalAmountget(String Userid) {
        String tt = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String Sql = String.format("SELECT Quantity*DP From " + TABLE_NAME2 + " WHERE LoginID='%s' ", Userid);

        cursor = db.rawQuery(Sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                tt = cursor.getString(0);
            } while (cursor.moveToNext());


        }
        return tt;
    }
    public String TotalCal(String Userid) {
        String tt = "0";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String Sql = String.format("SELECT SUM(Quantity*DP) From " + TABLE_NAME2 + " WHERE LoginID='%s' ", Userid);

        cursor = db.rawQuery(Sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                tt = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        if (tt==null)
        {
            tt="0";
        }
        return tt;
    }
    public String TotalBvAmount(String Userid) {
        String tt = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String Sql = String.format("SELECT SUM(Quantity*BV) From " + TABLE_NAME2 + " WHERE LoginID='%s' ", Userid);

        cursor = db.rawQuery(Sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                tt = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return tt;
    }
    public String TotalPvAmount(String Userid) {
        String tt = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String Sql = String.format("SELECT SUM(Quantity*PV) From " + TABLE_NAME2 + " WHERE LoginID='%s' ", Userid);

        cursor = db.rawQuery(Sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                tt = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return tt;
    }
    public List<CartGetter> getCart(String Userid)
    {
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlselect={"SerialNo","ProductName","PID","DP","BV","PV","ProductImg","MRP","Quantity","LoginID","TotalAmount"};

        qb.setTables(TABLE_NAME2);
        Cursor c=qb.query(db,sqlselect,"LoginID=?",new String[]{Userid},null,null,null);
        final List<CartGetter>result=new ArrayList<>();
        if (c.moveToNext())
        {
            do {
                result.add(new CartGetter(
                        //c.getString(c.getColumnIndex("SerialNo")),
                        c.getString(c.getColumnIndex("PID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("BV")),
                        c.getString(c.getColumnIndex("DP")),
                        c.getString(c.getColumnIndex("MRP")),
                        c.getString(c.getColumnIndex("ProductImg")),
                        c.getString(c.getColumnIndex("LoginID")),
                        c.getString(c.getColumnIndex("TotalAmount"))

                ));
            }while (c.moveToNext());
        }
        return result;
    }
    /*
    //Get data post order
    public  List<PaymentPostGetter> getPaymentDetails()
    {
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlselect={"PID","Quantity","BV","DP","MRP","LoginID","CID","CTID",
                "SID","DISTID","Name","TotalAmount","BankName","RefNo","Pincode","Address","Emailid","MobileNo","Paymode","V_Amount"};

        qb.setTables(TABLE_NAME);
        Cursor c=qb.query(db,sqlselect,null,null,null,null,null);
        final List<PaymentPostGetter>result=new ArrayList<>();
        if (c.moveToNext())
        {
            do {
                result.add(new PaymentPostGetter(
                        c.getString(c.getColumnIndex("PID")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("BV")),
                        c.getString(c.getColumnIndex("DP")),
                        c.getString(c.getColumnIndex("MRP")),
                        c.getString(c.getColumnIndex("LoginID")),
                        c.getString(c.getColumnIndex("CID")),
                        c.getString(c.getColumnIndex("CTID")),
                        c.getString(c.getColumnIndex("SID")),
                        c.getString(c.getColumnIndex("DISTID")),
                        c.getString(c.getColumnIndex("Name")),
                        c.getString(c.getColumnIndex("TotalAmount")),
                        c.getString(c.getColumnIndex("BankName")),
                        c.getString(c.getColumnIndex("RefNo")),
                        c.getString(c.getColumnIndex("Pincode")),
                        c.getString(c.getColumnIndex("Address")),
                        c.getString(c.getColumnIndex("Emailid")),
                        c.getString(c.getColumnIndex("MobileNo")),
                        c.getString(c.getColumnIndex("Paymode")),
                        c.getString(c.getColumnIndex("V_Amount"))
                ));
            }while (c.moveToNext());
        }
        return result;

    }
    */
    public void UpdateTotal(String totalamount ,String pid,String userid) {
        SQLiteDatabase db = getReadableDatabase();
        String query=String.format("UPDATE "+TABLE_NAME2+" SET TotalAmount='%s'  WHERE PID='%s' AND LoginID='%s'",totalamount,pid,userid);
        db.execSQL(query);
    }
    public void updateCart(CartGetter order) {
        SQLiteDatabase db = getReadableDatabase();
        String query=String.format("UPDATE "+TABLE_NAME2+" SET Quantity=%s  WHERE PID='%s' AND LoginID='%s'",order.getProductesQty(),order.getProductesid(),order.getUserLoginid());
        db.execSQL(query);
    }
    public void updatepaymentdetails(String mop,String bankname,String refno,String vwamount,String Cityid, String Countryid,
                                     String Stateid,String Distid,String Pincode,String Address,String Moileno,String Emildid,String userloginid,String Totalamount,String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put(COL_18, mop);
        con.put(COL_21, bankname);
        con.put(COL_22, refno);
        con.put(COL_19, vwamount);
        con.put(COL_14, Cityid);
        con.put(COL_13, Countryid);
        con.put(COL_15, Stateid);
        con.put(COL_16, Distid);
        con.put(COL_17, Pincode);
        con.put(COL_12, Address);
        con.put(COL_11, Moileno);
        con.put(COL_10, Emildid);
        con.put(COL_20, Totalamount);
        con.put(COL_9, username);
        db.update(TABLE_NAME, con, COL_8 + " = ?", new String[]{userloginid});
        db.close();
    }
    public void cleanCart(String Userid)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query=String.format("DELETE from "+TABLE_NAME2+" WHERE LoginID = '"+Userid+"' ");
        db.execSQL(query);
        // return   db.delete(TABLE_NAME2, COL_8+ "=" +Userid,null )>0 ;
        //db.close();
    }
    public boolean deleteTitle(String id)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete(TABLE_NAME2, COL_1 + "=" + id, null) > 0;
    }
    public boolean cler(String id)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete(TABLE_NAME2, COL_1 + "=" + id, null) > 0;
    }
    public String CartTotal(String Userid) {
        String tt = "0";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String Sql = String.format("SELECT COUNT(*) From " + TABLE_NAME2 + " WHERE LoginID='%s' ", Userid);

        cursor = db.rawQuery(Sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                tt = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        if (tt==null)
        {
            tt="0";
        }
        return tt;
    }

}

