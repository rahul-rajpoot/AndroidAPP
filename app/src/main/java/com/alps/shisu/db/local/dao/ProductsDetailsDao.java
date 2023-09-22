package com.alps.shisu.db.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alps.shisu.db.local.entity.ProductsDetails;

import java.util.List;

@Dao
public interface ProductsDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertProductsDetails(ProductsDetails productsDetails);

    @Query("SELECT * FROM ProductsDetails WHERE user_id=:userId")
    List<ProductsDetails> getAllProductsDetails(String userId);


    @Query("SELECT * FROM ProductsDetails WHERE product_id=:productId AND user_id=:userId")
    ProductsDetails getProductsDetails(String productId, String userId);

    @Query("UPDATE ProductsDetails SET mrp=:newMrp  WHERE product_id=:pId AND user_id=:userId")
    void updateProductsDetailsMRPByProductId(String newMrp, String pId, String userId);

    @Query("UPDATE ProductsDetails SET weight=:newWeight  WHERE product_id=:pId AND user_id=:userId")
    void updateProductsDetailsWeightByProductId(String newWeight, String pId, String userId);

    @Query("UPDATE ProductsDetails SET quantity=:newQuantity  WHERE product_id=:pId AND user_id=:userId")
    void updateProductsDetailsQuantityByProductId(String newQuantity, String pId, String userId);
//
//    @Query("UPDATE OrderItemDetails SET left_time=:leftTime WHERE user_id=:userId AND attempt_id=:attempt_id")
//    void updateTestSeriesLeftTimeBySetId(String userId, String leftTime, String attempt_id);
//
//    @Query("UPDATE OrderItemDetails SET test_type=:tsTime WHERE user_id=:userId AND attempt_id=:attempt_id")
//    void updateTestSeriesTsTimeBySetId(String userId, String tsTime, String attempt_id);
//
//    @Query("UPDATE OrderItemDetails SET selected_file_uri=:selected_file_uri WHERE user_id=:userId AND attempt_id=:attempt_id")
//    void updateTestSeriesSelectedFileUri(String selected_file_uri, String userId, String attempt_id);
//
//    @Query("UPDATE OrderItemDetails SET attempted=:attempted WHERE user_id=:userId AND attempt_id=:attempt_id")
//    void updateTestSeriesAttemptedStatusBySetId(String userId, String attempted, String attempt_id);
//
//    @Query("UPDATE OrderItemDetails SET is_submitted=:isSubmitted WHERE user_id=:userId AND attempt_id=:attempt_id")
//    void updateTestSeriesSubmitDetailsBySetId(String userId, String isSubmitted, String attempt_id);
//
//    @Query("UPDATE OrderItemDetails SET attempt_date_time=:currentTime WHERE user_id=:userId AND attempt_id=:attempt_id")
//    void updateTestSeriesAttemptDateTimeDetailsBySetId(String userId, String currentTime, String attempt_id);
//
//    @Query("UPDATE OrderItemDetails SET synced_to_server=:syncedWithServer  WHERE user_id=:userId AND attempt_id=:attempt_id")
//    void updateTestSeriesSubmitDetailsIsSyncedBySetId(String userId, String syncedWithServer, String attempt_id);
//
    @Query("DELETE FROM ProductsDetails WHERE product_id=:pId AND user_id=:userId")
    void deleteProductsDetails(String pId, String userId);
//
//    @Update
//    void updateTestSeriesDetailsData(OrderItemDetails tsAttemptedQuesAnsDetails);
//
    @Delete
    void deleteProductsDetailsData(ProductsDetails productsDetails);

    @Query("DELETE FROM ProductsDetails")
    void deleteAllProductsDetailsData();

}
