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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.hoiwanlouis.mystockportfolio.PrimoActivity;
import com.hoiwanlouis.mystockportfolio.database.Database.Company;
import com.hoiwanlouis.mystockportfolio.database.Database.Exchange;
import com.hoiwanlouis.mystockportfolio.database.Database.Catalogue;
import com.hoiwanlouis.mystockportfolio.database.Database.Portfolio;



//FYI: This is the same setup as PetTracker
public class DatabaseHelper extends SQLiteOpenHelper {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();


    public DatabaseHelper(
            Context context,
            String databaseName,
            CursorFactory cursorFactory,
            int databaseVersion) {
        super(context, databaseName, cursorFactory, databaseVersion);
        Log.v(DEBUG_TAG, "in Constructor");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the Company table
        // todo: add support in code to use the Company table
        Log.v(DEBUG_TAG, "creating " + Company.COMPANY_TABLE_NAME + "...");
        StringBuilder sbCreateCompanyTable = new StringBuilder();
        sbCreateCompanyTable.append("CREATE TABLE ");
        sbCreateCompanyTable.append(Company.COMPANY_TABLE_NAME);
        sbCreateCompanyTable.append(" (");

        sbCreateCompanyTable.append(Company._ID);
        sbCreateCompanyTable.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        sbCreateCompanyTable.append(Company.COMPANY_NAME);
        sbCreateCompanyTable.append(" TEXT NOT NULL UNIQUE, ");

        sbCreateCompanyTable.append(Company.COMPANY_LONG_NAME);
        sbCreateCompanyTable.append(" TEXT, ");

        sbCreateCompanyTable.append(Company.INSERT_DATETIME);
        sbCreateCompanyTable.append(" DATETIME, ");

        sbCreateCompanyTable.append(Company.MODIFY_DATETIME);
        sbCreateCompanyTable.append(" DATETIME ");

        sbCreateCompanyTable.append("); ");
        Log.i(DEBUG_TAG, "using SQL[" + sbCreateCompanyTable.toString() + "].");
        db.execSQL(sbCreateCompanyTable.toString());
        Log.v(DEBUG_TAG, "creating " + Company.COMPANY_TABLE_NAME + " complete.");


        // Create the Exchange table
        // todo: add support in code to use the Exchange table
        Log.v(DEBUG_TAG, "creating " + Exchange.EXCHANGE_TABLE_NAME + "...");
        StringBuilder sbCreateExchangeTable = new StringBuilder();
        sbCreateExchangeTable.append("CREATE TABLE " );
        sbCreateExchangeTable.append(Exchange.EXCHANGE_TABLE_NAME);
        sbCreateExchangeTable.append(" (");

        sbCreateExchangeTable.append(Exchange._ID);
        sbCreateExchangeTable.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        sbCreateExchangeTable.append(Exchange.EXCHANGE_CODE);
        sbCreateExchangeTable.append(" TEXT NOT NULL UNIQUE, ");

        sbCreateExchangeTable.append(Exchange.EXCHANGE_NAME );
        sbCreateExchangeTable.append(" TEXT, ");

        sbCreateExchangeTable.append(Exchange.INSERT_DATETIME);
        sbCreateExchangeTable.append(" DATETIME, ");

        sbCreateExchangeTable.append(Exchange.MODIFY_DATETIME);
        sbCreateExchangeTable.append(" DATETIME ");
        sbCreateExchangeTable.append(");");
        Log.i(DEBUG_TAG, "using SQL[" + sbCreateExchangeTable.toString() + "].");
        db.execSQL(sbCreateExchangeTable.toString());
        Log.v(DEBUG_TAG, "creating " + Exchange.EXCHANGE_TABLE_NAME + " complete.");


        // Create the Catalogue table
        // todo: add support in code to use the Catalogue table
        Log.v(DEBUG_TAG, "creating " + Catalogue.CATALOGUE_TABLE_NAME + "...");
        StringBuilder sbCreateCatalogueTable = new StringBuilder();
        sbCreateCatalogueTable.append("CREATE TABLE ");
        sbCreateCatalogueTable.append(Catalogue.CATALOGUE_TABLE_NAME);
        sbCreateCatalogueTable.append(" (");

        sbCreateCatalogueTable.append(Catalogue._ID);
        sbCreateCatalogueTable.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        sbCreateCatalogueTable.append(Catalogue.PORTFOLIO_ID);
        sbCreateCatalogueTable.append(" INTEGER, ");

        sbCreateCatalogueTable.append(Catalogue.COMPANY_ID);
        sbCreateCatalogueTable.append(" INTEGER, ");

        sbCreateCatalogueTable.append(Catalogue.EXCHANGE_ID);
        sbCreateCatalogueTable.append(" INTEGER, ");

        sbCreateCatalogueTable.append(Catalogue.INSERT_DATETIME);
        sbCreateCatalogueTable.append(" DATETIME, ");

        sbCreateCatalogueTable.append(Catalogue.MODIFY_DATETIME);
        sbCreateCatalogueTable.append(" DATETIME ");

        sbCreateCatalogueTable.append(");");
        Log.i(DEBUG_TAG, "using SQL[" + sbCreateCatalogueTable.toString() + "].");
        db.execSQL(sbCreateCatalogueTable.toString());
        Log.v(DEBUG_TAG, "creating " + Catalogue.CATALOGUE_TABLE_NAME + " complete.");


        // Create the Portfolio table
        // todo: add support to JOIN other tables for SYMBOL_ID, COMPANY_ID, EXCHANGE_ID, etc
        Log.v(DEBUG_TAG, "creating " + Portfolio.PORTFOLIO_TABLE_NAME + "...");
        StringBuilder sbCreatePortfolioTable = new StringBuilder();
        sbCreatePortfolioTable.append("CREATE TABLE ");
        sbCreatePortfolioTable.append(Portfolio.PORTFOLIO_TABLE_NAME);
        sbCreatePortfolioTable.append(" (");

        sbCreatePortfolioTable.append(Portfolio._ID);
        sbCreatePortfolioTable.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");

        sbCreatePortfolioTable.append(Portfolio.SYMBOL);
        sbCreatePortfolioTable.append(" TEXT NOT NULL UNIQUE, ");

        sbCreatePortfolioTable.append(Portfolio.OPENING_PRICE);
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.PREVIOUS_CLOSING_PRICE);
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.BID_PRICE);
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.BID_SIZE );
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.ASK_PRICE );
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.ASK_SIZE );
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.LAST_TRADE_PRICE);
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.LAST_TRADE_QUANTITY);
        sbCreatePortfolioTable.append(" DOUBLE, ");

        sbCreatePortfolioTable.append(Portfolio.LAST_TRADE_DATE);
        sbCreatePortfolioTable.append(" TEXT, ");

        sbCreatePortfolioTable.append(Portfolio.LAST_TRADE_TIME);
        sbCreatePortfolioTable.append(" TEXT, ");

        sbCreatePortfolioTable.append(Portfolio.INSERT_DATETIME);
        sbCreatePortfolioTable.append(" DATETIME, ");

        sbCreatePortfolioTable.append(Portfolio.MODIFY_DATETIME);
        sbCreatePortfolioTable.append(" DATETIME ");

        sbCreatePortfolioTable.append(");");
        Log.i(DEBUG_TAG, "using SQL[" + sbCreatePortfolioTable.toString() + "].");
        db.execSQL(sbCreatePortfolioTable.toString());
        Log.v(DEBUG_TAG, "creating " + Portfolio.PORTFOLIO_TABLE_NAME + " complete.");

    }



    // Housekeeping here. Implement how "move" your application data during an upgrade of schema versions
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(DEBUG_TAG, "in onUpgrade");
    }


    // Housekeeping here. Implement how "move" your application data during a downgrade of schema versions
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(DEBUG_TAG, "in onDowngrade");
    }


    //
    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.v(DEBUG_TAG, "in onOpen");
        super.onOpen(db);
    }


    //
    @Override
    public SQLiteDatabase getReadableDatabase() {
        Log.v(DEBUG_TAG, "in getReadableDatabase");
        return super.getReadableDatabase();
    }


    //
    @Override
    public SQLiteDatabase getWritableDatabase() {
        Log.v(DEBUG_TAG, "in getWritableDatabase");
        return super.getWritableDatabase();
    }


    //
    @Override
    public void close() {
        Log.v(DEBUG_TAG, "in onOpen");
        super.close();
    }


}
