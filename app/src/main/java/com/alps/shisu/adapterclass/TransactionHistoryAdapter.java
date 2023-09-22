package com.alps.shisu.adapterclass;

// --Commented out by Inspection START (06-07-2021 04:55 PM):
//public class TransactionHistoryAdapter extends BaseAdapter
//{
//    private final Context mContext;
//    private List<TransactionRecordsItem> transactionRecordsItemList;
//
//    public TransactionHistoryAdapter(Context c, List<TransactionRecordsItem> transactionRecordsItemList )
//    {
//        mContext = c;
//        this.transactionRecordsItemList = transactionRecordsItemList;
//    }
//
//    @Override
//    public int getCount()
//    {
//        return transactionRecordsItemList.size();
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
//            gridView = inflater.inflate(R.layout.transaction_records_item_layout, null);
//            // set value into textview
//
//            TransactionRecordsItem transactionRecordsItem = transactionRecordsItemList.get(position);
//            TextView remark =
//                    gridView.findViewById(R.id.remark);
//            remark.setText(transactionRecordsItem.getRemark());
//
//            TextView mode =
//                    gridView.findViewById(R.id.mode);
//            mode.setText(transactionRecordsItem.getMode());
//
//            TextView transactionAmount =
//                    gridView.findViewById(R.id.transactionAmount);
//            transactionAmount.setText(transactionRecordsItem.getTransamount());
//
//            TextView goldAmount =
//                    gridView.findViewById(R.id.goldAmount);
//            goldAmount.setText(transactionRecordsItem.getTransGold());
//
//
//
//        }
//        else
//        {
//            gridView = (View) convertView;
//        }
//        return gridView;
//    }
//}
// --Commented out by Inspection STOP (06-07-2021 04:55 PM)
