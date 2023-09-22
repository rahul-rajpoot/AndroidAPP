package com.alps.shisu.db.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alps.shisu.db.local.dao.ProductsDetailsDao;
import com.alps.shisu.db.local.entity.ProductsDetails;

@Database(entities = {ProductsDetails.class}, version = 1, exportSchema = false)
//@TypeConverters(Converters.class)
public abstract class SaveAppInfoRoomDataBase extends RoomDatabase {

    public abstract ProductsDetailsDao productsDetailsDao();

}
