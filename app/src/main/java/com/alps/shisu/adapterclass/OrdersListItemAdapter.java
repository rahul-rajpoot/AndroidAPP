package com.alps.shisu.adapterclass;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alps.shisu.R;
import com.alps.shisu.Session.SessionManagement;
import com.alps.shisu.modelclass.OrderItemDetails;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class OrdersListItemAdapter extends BaseAdapter
{
    private final Context mContext;
    final List<OrderItemDetails> orderItemDetailsList;


    public OrdersListItemAdapter(Context c, List<OrderItemDetails> orderItemDetailsList)
    {
        mContext = c;
        this.orderItemDetailsList = orderItemDetailsList;
    }

    @Override
    public int getCount()
    {
        return orderItemDetailsList.size();
    }
    @Override
    public Object getItem(int position)
    {
        return position;
    }
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup
            parent)
    {
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
//        if (convertView == null)
//        {

            SessionManagement sessionManagement = new SessionManagement(mContext);
            HashMap<String, String> user = sessionManagement.getUserDetails();

            gridView = new View(mContext);
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.order_list_item, null);
            // set value into textview

            OrderItemDetails orderItemDetails = orderItemDetailsList.get(position);

            TextView quantity =
                    gridView.findViewById(R.id.quantity);

            TextView orderNo =
                    gridView.findViewById(R.id.orderNo);

            TextView invoiceNo =
                    gridView.findViewById(R.id.invoiceNo);

            TextView orderDate =
                    gridView.findViewById(R.id.orderDate);

//            TextView date =
//                    gridView.findViewById(R.id.date);

            TextView totalAmount =
                    gridView.findViewById(R.id.totalAmount);

//            TextView weight =
//                    gridView.findViewById(R.id.weight);

            TextView status =
                    gridView.findViewById(R.id.status);

            TextView invDate =
                    gridView.findViewById(R.id.invDate);


            String orderNO = "<font color=#333333>Order No: </font> <font color=#777777><br>"+orderItemDetails.getOrderNo()+"</font>";
            String InvNo = "<font color=#333333>Inv No: </font> <font color=#777777><br>"+orderItemDetails.getInvNO()+"</font>";
            String oderDate = "<font color=#333333>Order Date: </font> <font color=#777777><br>"+orderItemDetails.getOrderDate()+"</font>";
            String _invDate = "<font color=#333333>Inv Date: </font> <font color=#777777><br>"+orderItemDetails.getInvDate()+"</font>";
            String _totalAmount = "<font color=#333333>Total Amount: </font> <font color=#777777><br>"+ user.get(SessionManagement.KEY_CURRENCY)+""+orderItemDetails.getAmount()+"</font>";
            String _quantity = "<font color=#333333>Quantity: </font> <font color=#777777><br>"+orderItemDetails.getQty()+"</font>";
//            String _status = "<font color=#333333>Status: </font> <font color=#"+orderItemDetails.getStatusColor()+">"+orderItemDetails.getStatus()+"</font>";
            String _status = "<font color=#333333>Status: <br></font> <font color="+orderItemDetails.getStatusColor()+">"+orderItemDetails.getStatus()+"</font>";

            Log.e(TAG, "getView: _status :  "+_status  );
            orderNo.setText(Html.fromHtml(orderNO));
            invoiceNo.setText(Html.fromHtml(InvNo));
            orderDate.setText(Html.fromHtml(oderDate));
            invDate.setText(Html.fromHtml(_invDate));
            totalAmount.setText(Html.fromHtml(_totalAmount));
            status.setText(Html.fromHtml(_status));
//          productName.setText(orderItemDetails.get());
//          weight.setText(orderItemDetails.get());
            quantity.setText(Html.fromHtml(_quantity));
            // set image based on selected text
//            ImageView imageView =
//                    gridView.findViewById(R.id.product_image_order_list);
//            if (orderItemDetails.getOrderurl() != null && !orderItemDetails.getOrderurl().isEmpty()) {
//                Glide.with(mContext).load(orderItemDetails.getOrderurl())
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                                Log.e("GlideException", e.getMessage());
//                                imageView.setImageResource(R.drawable.no_image);
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                return false;
//                            }
//                        })
//                        .into(imageView);
//            } else {
//                imageView.setImageResource(R.drawable.no_image);
//            }
//        }
//        else
//        {
//            gridView = (View) convertView;
//        }
        return gridView;
    }
}

