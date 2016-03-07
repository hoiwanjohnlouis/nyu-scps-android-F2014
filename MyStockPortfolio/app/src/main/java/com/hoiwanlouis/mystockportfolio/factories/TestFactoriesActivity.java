package com.hoiwanlouis.mystockportfolio.factories;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;

import com.hoiwanlouis.mystockportfolio.R;

import com.hoiwanlouis.mystockportfolio.enums.ERecordType;


public class TestFactoriesActivity extends Activity {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_portfolio_factories);


        //  testing ticker record construction
        RecordBuilding createTrade = new TradeBuilding();
        Record record = createTrade.orderRecord(ERecordType.TRADE_RECORD);
        System.out.println(record + "\n");


    }

}
