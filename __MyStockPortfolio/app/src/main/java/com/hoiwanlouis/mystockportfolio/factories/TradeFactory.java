package com.hoiwanlouis.mystockportfolio.factories;

/*
    Copyright (c) 2014  HW Tech Services, Inc., LLC
 
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

import android.util.Log;

import com.hoiwanlouis.mystockportfolio.fields.Company;
import com.hoiwanlouis.mystockportfolio.fields.DateStamp;
import com.hoiwanlouis.mystockportfolio.fields.ICompany;
import com.hoiwanlouis.mystockportfolio.fields.IDateStamp;
import com.hoiwanlouis.mystockportfolio.fields.IPrice;
import com.hoiwanlouis.mystockportfolio.fields.IQuantity;
import com.hoiwanlouis.mystockportfolio.fields.IRecordFactory;
import com.hoiwanlouis.mystockportfolio.fields.ISymbol;
import com.hoiwanlouis.mystockportfolio.fields.ITimeStamp;
import com.hoiwanlouis.mystockportfolio.fields.Price;
import com.hoiwanlouis.mystockportfolio.fields.Quantity;
import com.hoiwanlouis.mystockportfolio.fields.Symbol;
import com.hoiwanlouis.mystockportfolio.fields.TimeStamp;


public class TradeFactory implements IRecordFactory {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    @Override
    public ICompany addCompany() {
        Log.v(DEBUG_TAG, "in addCompany");
        return new Company();
    }

    @Override
    public ISymbol addSymbol() {
        Log.v(DEBUG_TAG, "in addSymbol");
        return new Symbol();
    }

    @Override
    public IPrice addOpeningPrice() {
        Log.v(DEBUG_TAG, "in addOpeningPrice");
        return new Price();
    }

    @Override
    public IPrice addPreviousDaysClosingPrice() {
        Log.v(DEBUG_TAG, "in addPreviousDaysClosingPrice");
        return new Price();
    }

    @Override
    public IPrice addTradePrice() {
        Log.v(DEBUG_TAG, "in addTradePrice");
        return new Price();
    }

    @Override
    public IQuantity addTradeQuantity() {
        Log.v(DEBUG_TAG, "in addTradeQuantity");
        return new Quantity();
    }

    @Override
    public IDateStamp addTradeDateStamp() {
        Log.v(DEBUG_TAG, "in addTradeDateStamp");
        return new DateStamp();
    }

    @Override
    public ITimeStamp addTradeTimeStamp() {
        Log.v(DEBUG_TAG, "in addTradeTimeStamp");
        return new TimeStamp();
    }
}
