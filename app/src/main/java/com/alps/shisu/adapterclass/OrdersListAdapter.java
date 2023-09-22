package com.alps.shisu.adapterclass;

// --Commented out by Inspection START (06-07-2021 04:55 PM):
//public class OrdersListAdapter extends BaseAdapter
//{
//    private final Context mContext;
//    final List<OrderItemDetails> orderItemDetailsList;
//
//    public OrdersListAdapter(Context c, List<OrderItemDetails> orderItemDetailsList)
//    {
//        mContext = c;
//        this.orderItemDetailsList = orderItemDetailsList;
//    }
//
//    @Override
//    public int getCount()
//    {
//        return orderItemDetailsList.size();
//    }
//    @Override
//    public Object getItem(int position)
//    {
//        return position;
//    }
//    @Override
//    public long getItemId(int position)
//    {
//        return 0;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup
//            parent)
//    {
//        LayoutInflater inflater = (LayoutInflater)
//                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View gridView;
//        if (convertView == null)
//        {
//            gridView = new View(mContext);
//            // get layout from mobile.xml
//            gridView = inflater.inflate(R.layout.order_item_layout, null);
//            // set value into textview
//
//            OrderItemDetails orderItemDetails = orderItemDetailsList.get(position);
//
//            TextView quantity =
//                    gridView.findViewById(R.id.quantity);
//
//            TextView orderNo =
//                    gridView.findViewById(R.id.orderNo);
//
//            TextView invoiceNo =
//                    gridView.findViewById(R.id.invoiceNo);
//
//            TextView orderDate =
//                    gridView.findViewById(R.id.orderDate);
//
//            TextView date =
//                    gridView.findViewById(R.id.date);
//
//            TextView totalAmount =
//                    gridView.findViewById(R.id.totalAmount);
//
//            TextView weight =
//                    gridView.findViewById(R.id.weight);
//
//            TextView status =
//                    gridView.findViewById(R.id.status);
//
//            TextView productName =
//                    gridView.findViewById(R.id.productName);
//
//
//            orderNo.setText(orderItemDetails.getOrderNo());
//            invoiceNo.setText(orderItemDetails.getInvNO());
//            orderDate.setText(orderItemDetails.getOrderDate());
//            date.setText(orderItemDetails.getInvDate());
//            totalAmount.setText(orderItemDetails.getAmount());
//            status.setText(orderItemDetails.getStatus());
////          productName.setText(orderItemDetails.get());
////          weight.setText(orderItemDetails.get());
//            quantity.setText(orderItemDetails.getQty());
//            // set image based on selected text
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
//        return gridView;
//    }
//}
// --Commented out by Inspection STOP (06-07-2021 04:55 PM)

