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
package com.hoiwanlouis.mystockportfolio.stocks;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

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
public class StockDetailCursorAdapter extends CursorAdapter {
    private Context context;
    private Cursor cursor;
    private int flags;

    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();

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
    public StockDetailCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
        this.cursor = c;
        this.flags = flags;
    }


    //
    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        //
        ViewHolder holder = new ViewHolder();

        return v;
    }


    //
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        //
        ViewHolder holder = (ViewHolder) view.getTag();

    }

}
