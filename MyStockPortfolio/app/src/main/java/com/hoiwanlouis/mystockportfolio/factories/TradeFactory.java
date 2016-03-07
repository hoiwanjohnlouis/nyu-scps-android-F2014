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

import com.hoiwanlouis.mystockportfolio.enums.EFieldType;
import com.hoiwanlouis.mystockportfolio.fields.DateTimeStamp;
import com.hoiwanlouis.mystockportfolio.fields.Company;
import com.hoiwanlouis.mystockportfolio.fields.Price;
import com.hoiwanlouis.mystockportfolio.fields.Quantity;
import com.hoiwanlouis.mystockportfolio.fields.Symbol;
import com.hoiwanlouis.mystockportfolio.interfaces.IRecordFactory;


public class TradeFactory implements IRecordFactory {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    public Company addCompany(EFieldType fieldType, String longName) {
        Log.v(DEBUG_TAG, "in addCompany");
        return new Company(fieldType, longName);
    }

    @Override
    public Symbol addSymbol(EFieldType fieldType, String symbol) {
        Log.v(DEBUG_TAG, "in addSymbol");
        return new Symbol(fieldType, symbol);
    }

    @Override
    public Price addOpeningPrice(EFieldType fieldType, double price) {
        Log.v(DEBUG_TAG, "in addOpeningPrice");
        return new Price(fieldType, price);
    }

    @Override
    public Price addPreviousDaysClosingPrice(EFieldType fieldType, double price) {
        Log.v(DEBUG_TAG, "in addPreviousDaysClosingPrice");
        return new Price(fieldType, price);
    }

    @Override
    public Price addTradePrice(EFieldType fieldType, double price) {
        Log.v(DEBUG_TAG, "in addTradePrice");
        return new Price(fieldType, price);
    }

    @Override
    public Quantity addTradeQuantity(EFieldType fieldType, double quantity) {
        Log.v(DEBUG_TAG, "in addTradeQuantity");
        return new Quantity(fieldType, quantity);
    }

    @Override
    public DateTimeStamp addTradeDateTimeStamp(EFieldType fieldType, String dateTimeStamp) {
        Log.v(DEBUG_TAG, "in addTradeDateTimeStamp");
        return new DateTimeStamp(fieldType, dateTimeStamp);
    }

}
