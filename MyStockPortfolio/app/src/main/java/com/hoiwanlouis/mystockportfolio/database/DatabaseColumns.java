package com.hoiwanlouis.mystockportfolio.database;

/*
    Copyright (c) 2014  Hoi Wan Louis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import android.provider.BaseColumns;


//FYI: This is the same setup as PetTracker, same schema
public final class DatabaseColumns {

    private DatabaseColumns() {}

    public static final String DATABASE_NAME = "portfolio.sqlite3";
    public static final int DATABASE_VERSION = 1;

    //
    public static final class Company implements BaseColumns {
        private Company() {}
        public static final String COMPANY_TABLE_NAME = "company_table";
        public static final String COMPANY_NAME = "company_name"; // short name: IBM
        public static final String COMPANY_LONG_NAME = "company_long_name"; // long name: International Business Machines
        public static final String INSERT_DATETIME = "insert_datetime";     // datetime format YYYY-MM-DD HH:MM:SS.SSS
        public static final String MODIFY_DATETIME = "modify_datetime";     // datetime format YYYY-MM-DD HH:MM:SS.SSS
        public static final String DEFAULT_SORT_ORDER = COMPANY_NAME + " ASC";
    }

    //
    public static final class Exchange implements BaseColumns {
        private Exchange() {}
        public static final String EXCHANGE_TABLE_NAME = "exchange_table";
        public static final String EXCHANGE_CODE = "exchange_code"; // unique code id for exchange. eg: NYSE
        public static final String EXCHANGE_NAME = "exchange_name"; // name of exchange. eg: New York Stock Exchange
        public static final String INSERT_DATETIME = "insert_datetime";     // datetime format YYYY-MM-DD HH:MM:SS.SSS
        public static final String MODIFY_DATETIME = "modify_datetime";     // datetime format YYYY-MM-DD HH:MM:SS.SSS
        public static final String DEFAULT_SORT_ORDER = EXCHANGE_NAME + " ASC";
    }


    // todo: Catalogue table to look up company, & exchange table data using the portfolio_id field
    public static final class Catalogue implements BaseColumns {
        private Catalogue() {}
        public static final String CATALOGUE_TABLE_NAME = "catalogue_table";
        public static final String PORTFOLIO_ID = "portfolio_id";
        public static final String COMPANY_ID = "company_id";
        public static final String EXCHANGE_ID = "exchange_id";
        public static final String INSERT_DATETIME = "insert_datetime"; // datetime, YYYY-MM-DD HH:MM:SS.SSS
        public static final String MODIFY_DATETIME = "modify_datetime"; // datetime, YYYY-MM-DD HH:MM:SS.SSS
        public static final String DEFAULT_SORT_ORDER = PORTFOLIO_ID + " ASC";
    }


    // Portfolio table: contains portfolio data
    public static final class Portfolio implements BaseColumns {
        private Portfolio() {}
        public static final String PORTFOLIO_TABLE_NAME = "portfolio_table";
        public static final String SYMBOL = "symbol";  // from symbol table
        public static final String OPENING_PRICE = "opening_price";  // opening price
        public static final String PREVIOUS_CLOSING_PRICE = "previous_closing_price";  // prev closing price
        public static final String BID_PRICE = "bid_price";  // bid price of quote
        public static final String BID_SIZE = "bid_size";  // bid size of quote
        public static final String ASK_PRICE = "ask_price";  // ask side of quote
        public static final String ASK_SIZE = "ask_size";  // bid size of quote
        public static final String LAST_TRADE_PRICE = "last_trade_price";  // price of last trade
        public static final String LAST_TRADE_QUANTITY = "last_trade_quantity";  // share quantity of last trade
        public static final String LAST_TRADE_DATETIME = "last_trade_datetime";  // datetime of last trade,YYYY-MM-DD HH:MM:SS.SSS
        public static final String INSERT_DATETIME = "insert_datetime"; // datetime, YYYY-MM-DD HH:MM:SS.SSS
        public static final String MODIFY_DATETIME = "modify_datetime"; // datetime, YYYY-MM-DD HH:MM:SS.SSS
        public static final String DEFAULT_SORT_ORDER = SYMBOL + " ASC";
    }


}
