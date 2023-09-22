package com.alps.shisu.db.local;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.alps.shisu.db.local.entity.ProductsDetails;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.alps.shisu.ApiUtil.Config.DB_NAME;

public class RoomDBRepository {

    private final SaveAppInfoRoomDataBase saveAppInfoRoomDataBase;

    public RoomDBRepository(Context context) {
        saveAppInfoRoomDataBase = Room.databaseBuilder(context, SaveAppInfoRoomDataBase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public void insertProductsDetails(final ProductsDetails productsDetails) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    saveAppInfoRoomDataBase.productsDetailsDao().insertProductsDetails(productsDetails);
                    return null;
                }
            }.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProductsDetailsMRPByProductId(final String newMrp, final String pId, final String userId) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesQuestionsAnswersDao().updateTestSeriesDetailsByQId(markedAnswer, currentIndex, qId);
                saveAppInfoRoomDataBase.productsDetailsDao().updateProductsDetailsMRPByProductId(newMrp, pId, userId);
                Log.e(TAG, "doInBackground: row updated : newMrp : "+ newMrp +" pId : "+ pId+ " User id : "+userId);
                return null;
            }
        }.execute();
    }

    public void updateProductsDetailsWeightByProductId(final String weight, final String pId, final String userId) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesQuestionsAnswersDao().updateTestSeriesDetailsByQId(markedAnswer, currentIndex, qId);
                saveAppInfoRoomDataBase.productsDetailsDao().updateProductsDetailsWeightByProductId(weight, pId, userId);
                Log.e(TAG, "doInBackground: row updated : weight : "+ weight +" pId : "+ pId);
                return null;
            }
        }.execute();
    }

    public void updateProductsDetailsQuantityByProductId(final String quantity, final String pId, final String userId) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesQuestionsAnswersDao().updateTestSeriesDetailsByQId(markedAnswer, currentIndex, qId);
                saveAppInfoRoomDataBase.productsDetailsDao().updateProductsDetailsQuantityByProductId(quantity, pId, userId);
                Log.e(TAG, "doInBackground: row updated : quantity : "+ quantity +" pId : "+ pId);
                return null;
            }
        }.execute();
    }

//    public void updateTestSeriesQuestionCurrentIndexBySetIdTask(final String userId, final String currentIndex, final String setId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesQCurrentIndexBySetId(userId,  currentIndex,  setId);
//                Log.e(TAG, "doInBackground: row updated : setId : "+ setId + " Current index : "+currentIndex);
//                return null;
//            }
//        }.execute();
//    }

//    public void updateTestSeriesLeftTimeBySetId(final String userId, final String leftTime, final String setId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesLeftTimeBySetId(userId, leftTime,  setId);
//                Log.e(TAG, "doInBackground: row updated : setId : "+ setId +" leftTime : "+ leftTime);
//                return null;
//            }
//        }.execute();
//    }
//
//    public void updateTestSeriesTsTimeBySetId(final String userId, final String tsTime, final String attemptId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesTsTimeBySetId(userId, tsTime,  attemptId);
//                Log.e(TAG, "doInBackground: row updated : attemptId : "+ attemptId +" tsTime : "+ tsTime);
//                return null;
//            }
//        }.execute();
//    }
//
//
//    public void updateTestSeriesSelectedFileURIByAttemptId(final String selectedFileUri, final String userId, final String attemptId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesSelectedFileUri(selectedFileUri, userId,  attemptId);
//                Log.e(TAG, "doInBackground: row updated : attemptId : "+ attemptId +" selectedFileUri : "+ selectedFileUri);
//                return null;
//            }
//        }.execute();
//    }
//
//
//    public void updateTestSeriesAttemptedStatusBySetId(final String userId, final String attempted, final String setId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesAttemptedStatusBySetId(userId, attempted,  setId);
//                Log.e(TAG, "doInBackground: row updated : setId : "+ setId +" attempted : "+ attempted);
//                return null;
//            }
//        }.execute();
//    }
//
//    public void updateTestSeriesSubmitDetailsBySetIdTask(final String userId, final String isSubmitted, final String attemptId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesSubmitDetailsBySetId(userId, isSubmitted, attemptId);
//                Log.e(TAG, "doInBackground: row updated : attemptId : "+ attemptId + " isSubmitted : "+isSubmitted);
//                return null;
//            }
//        }.execute();
//    }
//
//    public void updateTestSeriesSubmittedTimeDetailsBySetIdTask(final String userId, final String currentTime, final String attemptId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesAttemptDateTimeDetailsBySetId(userId, currentTime, attemptId);
//                Log.e(TAG, "doInBackground: row updated : attemptId : "+ attemptId + " currentTime : "+currentTime);
//                return null;
//            }
//        }.execute();
//    }
//
//
//    public void updateTestSeriesSubmitDetailsIsSyncedBySetIdTask(final String userId, final String syncedWithServer, final String attemptId) {
//
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveAppInfoRoomDataBase.testSeriesDetailsDao().updateTestSeriesSubmitDetailsIsSyncedBySetId(userId, syncedWithServer,  attemptId);
//                Log.e(TAG, "doInBackground: row updated : setId : "+ attemptId + " syncedWithServer : "+syncedWithServer);
//                return null;
//            }
//        }.execute();
//    }
//
    public ProductsDetails getProductsDetails(String orderNo, final String userId) {
        try {
            return new GetTestSeriesDetailsAsyncTask().execute(orderNo+"#"+userId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetTestSeriesDetailsAsyncTask extends AsyncTask<String, Void, ProductsDetails>
    {
        @Override
        protected ProductsDetails doInBackground(String... url) {
            String [] array = url[0].split("#");
            String orderNo =array[0];
            String userId =array[1];
            return  saveAppInfoRoomDataBase.productsDetailsDao().getProductsDetails(orderNo, userId);
        }
    }

    public List<ProductsDetails>  getAllProductsDetails(final String userId) {
        try {
            return new GetProductsDetailsAsyncTask().execute(userId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetProductsDetailsAsyncTask extends AsyncTask<String, Void, List<ProductsDetails>>
    {
        @Override
        protected List<ProductsDetails> doInBackground(String... url) {
//            String [] array = url[0].split("#");
//            String userId =array[0];
//            String attemptId =array[1];
            return  saveAppInfoRoomDataBase.productsDetailsDao().getAllProductsDetails(url[0]);
        }
    }

    public void deleteProductDetailsByProductId(final String pId, final String userId) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                saveAppInfoRoomDataBase.productsDetailsDao().deleteProductsDetails(pId, userId);
                return null;
            }
        }.execute();
    }

    public void deleteAllProductDetails() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                saveAppInfoRoomDataBase.productsDetailsDao().deleteAllProductsDetailsData();
                return null;
            }
        }.execute();
    }

}
