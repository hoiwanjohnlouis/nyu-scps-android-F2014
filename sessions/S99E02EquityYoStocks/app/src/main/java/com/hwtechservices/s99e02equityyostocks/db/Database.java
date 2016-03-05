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
package com.hwtechservices.s99e02equityyostocks.db;

import android.provider.BaseColumns;
import android.util.Log;

/***************************************************************************
 * Program Synopsis
 * <p>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * <p>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of
 * the small dark slabs of the noble South Sea war-wood, are frequently met
 * with in the forecastles of American whalers.
 *
 ***************************************************************************/
public final class Database {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    public static final String DATABASE_NAME = "EquityYo.db";
    public static final int DATABASE_VERSION = 1;

    // EquityYo table: contains portfolio data
    public static final class EquityYo implements BaseColumns {
        private EquityYo() {}
        public static final String EQUITYYO_TABLE_NAME = "equityyo_table";
        public static final String SYMBOL = "symbol";  // from symbol table
        public static final String OPENING_PRICE = "opening_price";  // opening price
        public static final String PREVIOUS_CLOSING_PRICE = "previous_closing_price";  // prev closing price
        public static final String BID_PRICE = "bid_price";  // bid price of quote
        public static final String BID_SIZE = "bid_size";  // bid size of quote
        public static final String ASK_PRICE = "ask_price";  // ask side of quote
        public static final String ASK_SIZE = "ask_size";  // bid size of quote
        public static final String LAST_TRADE_PRICE = "last_trade_price";  // price of last trade
        public static final String LAST_TRADE_QUANTITY = "last_trade_quantity";  // share quantity of last trade
        public static final String LAST_TRADE_DATE = "last_trade_date";  // date of last trade
        public static final String LAST_TRADE_TIME = "last_trade_time";  // time of last trade
        public static final String INSERT_DATETIME = "insert_datetime";  // date of insertion
        public static final String MODIFY_DATETIME = "modify_datetime";  // date of last modification
        public static final String DEFAULT_SORT_ORDER = SYMBOL + " ASC";
    }

    private Database() {}

}
