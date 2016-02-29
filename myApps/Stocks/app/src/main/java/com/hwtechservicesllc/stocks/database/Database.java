package com.hwtechservicesllc.stocks.database;

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

import com.hwtechservicesllc.stocks.R;

//FYI: This is the same setup as PetTracker, same schema
public final class Database {

    private Database() {}

    public static final String DATABASE_NAME = "portfolio.db";
    public static final int DATABASE_VERSION = 1;


    public static final class Query implements BaseColumns {
        private Query() {}

        public static final String asColumnsToReturn[] = {
                Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio._ID,
                Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.TICKER_SYMBOL,
                Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.OPENING_PRICE,
                Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.PREVIOUS_CLOSING_PRICE,
                Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.LAST_TRADE_PRICE,
                Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.LAST_TRADE_TIME
        };

        public static final String fromDBColumns[] =  {
                Database.Portfolio.TICKER_SYMBOL,
                Database.Portfolio.OPENING_PRICE,
                Database.Portfolio.PREVIOUS_CLOSING_PRICE,
                Database.Portfolio.LAST_TRADE_PRICE,
                Database.Portfolio.LAST_TRADE_TIME
        };

        public static final int toRIds[] = {
                R.id.TextView_symbol,
                R.id.TextView_opening_price,
                R.id.TextView_previous_closing_price,
                R.id.TextView_last_trade_price,
                R.id.TextView_last_trade_time
        };
    }



    // todo: Company table: contains Company data
    public static final class Company implements BaseColumns {
        private Company() {}
        public static final String COMPANY_TABLE_NAME = "company_table";
        public static final String COMPANY_NAME = "company_name";        // name: International Business Machines
        public static final String TICKER_SYMBOL = "ticker_symbol";      // ticker symbol
        public static final String INSERT_DATETIME = "insert_datetime";  // date of insertion
        public static final String MODIFY_DATETIME = "modify_datetime";  // date of last modification
        public static final String DEFAULT_SORT_ORDER = COMPANY_NAME + " ASC";
    }


    // todo: Exchange table: contains Exchange data
    public static final class Exchange implements BaseColumns {
        private Exchange() {}
        public static final String EXCHANGE_TABLE_NAME = "exchange_table";
        public static final String EXCHANGE_CODE = "exchange_code"; // unique code id for exchange. eg: NYSE
        public static final String EXCHANGE_NAME = "exchange_name"; // name of exchange. eg: New York Stock Exchange
        public static final String INSERT_DATETIME = "insert_datetime";  // date of insertion
        public static final String MODIFY_DATETIME = "modify_datetime";  // date of last modification
        public static final String DEFAULT_SORT_ORDER = EXCHANGE_NAME + " ASC";
    }


    // todo: Catalogue table to look up company, & exchange table data using the portfolio_id field
    public static final class Catalogue implements BaseColumns {
        private Catalogue() {}
        public static final String CATALOGUE_TABLE_NAME = "catalogue_table";
        public static final String PORTFOLIO_ID = "portfolio_id";
        public static final String COMPANY_ID = "company_id";
        public static final String EXCHANGE_ID = "exchange_id";
        public static final String INSERT_DATETIME = "insert_datetime";  // date of insertion
        public static final String MODIFY_DATETIME = "modify_datetime";  // date of last modification
        public static final String DEFAULT_SORT_ORDER = PORTFOLIO_ID + " ASC";
    }


    // Portfolio table: contains portfolio data
    public static final class Portfolio implements BaseColumns {
        private Portfolio() {}
        public static final String PORTFOLIO_TABLE_NAME = "portfolio_table";
        public static final String TICKER_SYMBOL = "ticker_symbol";  // from company table
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
        public static final String DEFAULT_SORT_ORDER = TICKER_SYMBOL + " ASC";
    }

}
