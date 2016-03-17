/***************************************************************************
 * Copyright March, 2016 HW Tech Services, LLC
 * <p/>
 * Login   HW Tech Services, LLC
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package com.hoiwanlouis.mystockportfolio.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseColumns;

/***************************************************************************
 * Program Synopsis
 * <p>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * <p>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of 
 *
 ***************************************************************************/
public class StockListCursorAdapter extends CursorAdapter {
    private Context mContext;
    private Cursor mCursor;
    private int mFlags;

    // layout of display, field by field
    static class ViewHolder {
        public TextView symbolTextView;
        public TextView openingPriceTextView;
        public TextView previousClosingPriceTextView;
        public TextView bidPriceTextView;
        public TextView bidSizeTextView;
        public TextView askPriceTextView;
        public TextView askSizeTextView;
        public TextView lastTradePriceTextView;
        public TextView lastTradeQuantityTextView;
        public TextView lastTradeDateTimeTextView;
        public TextView insertDateTimeTextView;
        public TextView modifyDateTimeTextView;
    }

    //
    public StockListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mContext = context;
        this.mCursor = c;
        this.mFlags = flags;
    }

    //
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_stock_detail, parent, false);

        //
        ViewHolder holder = new ViewHolder();
        // bind the TextView data, must match the detail layout
        holder.symbolTextView = (TextView) v.findViewById(R.id.symbolTextView);
        holder.openingPriceTextView = (TextView) v.findViewById(R.id.openingPriceTextView);
        holder.previousClosingPriceTextView = (TextView) v.findViewById(R.id.previousClosingPriceTextView);
        holder.bidPriceTextView = (TextView) v.findViewById(R.id.bidPriceTextView);
        holder.bidSizeTextView = (TextView) v.findViewById(R.id.bidSizeTextView);
        holder.askPriceTextView = (TextView) v.findViewById(R.id.askPriceTextView);
        holder.askSizeTextView = (TextView) v.findViewById(R.id.askSizeTextView);
        holder.lastTradePriceTextView = (TextView) v.findViewById(R.id.lastTradePriceTextView);
        holder.lastTradeQuantityTextView = (TextView) v.findViewById(R.id.lastTradeQuantityTextView);
        holder.lastTradeDateTimeTextView = (TextView) v.findViewById(R.id.lastTradeDateTimeTextView);
        holder.insertDateTimeTextView = (TextView) v.findViewById(R.id.insertDateTimeTextView);
        holder.modifyDateTimeTextView = (TextView) v.findViewById(R.id.modifyDateTimeTextView);

        // save the reference to the ViewHolder, basically the view to be displayed
        v.setTag(holder);
        return v;
    }

    //
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // resource Ids for readonly TextViews, must match detail layout
        // fetch the column indices for each data item
        int symbolIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.SYMBOL);
        int openingPriceIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.OPENING_PRICE);
        int previousClosingPriceIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.PREVIOUS_CLOSING_PRICE);
        int bidPriceIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.BID_PRICE);
        int bidSizeIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.BID_SIZE);
        int askPriceIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.ASK_PRICE);
        int askSizeIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.ASK_SIZE);
        int lastTradePriceIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_PRICE);
        int lastTradeQuantityIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_QUANTITY);
        int lastTradeDateTimeIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_DATETIME);
        int insertDateTimeIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.INSERT_DATETIME);
        int modifyDateTimeIndex = cursor.getColumnIndex(DatabaseColumns.Portfolio.MODIFY_DATETIME);

        //
        ViewHolder holder = (ViewHolder) view.getTag();

        // fetch the data and load into the TextViews
        holder.symbolTextView.setText(cursor.getString(symbolIndex));
        holder.openingPriceTextView.setText(cursor.getString(openingPriceIndex));
        holder.previousClosingPriceTextView.setText(cursor.getString(previousClosingPriceIndex));
        holder.bidPriceTextView.setText(cursor.getString(bidPriceIndex));
        holder.bidSizeTextView.setText(cursor.getString(bidSizeIndex));
        holder.askPriceTextView.setText(cursor.getString(askPriceIndex));
        holder.askSizeTextView.setText(cursor.getString(askSizeIndex));
        holder.lastTradePriceTextView.setText(cursor.getString(lastTradePriceIndex));
        holder.lastTradeQuantityTextView.setText(cursor.getString(lastTradeQuantityIndex));
        holder.lastTradeDateTimeTextView.setText(cursor.getString(lastTradeDateTimeIndex));
        holder.insertDateTimeTextView.setText(cursor.getString(insertDateTimeIndex));
        holder.modifyDateTimeTextView.setText(cursor.getString(modifyDateTimeIndex));
    }

}
