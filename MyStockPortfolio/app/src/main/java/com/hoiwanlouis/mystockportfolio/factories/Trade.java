package com.hoiwanlouis.mystockportfolio.factories;

/*
    Copyright (c) 2015  HW Tech Services, Inc., LLC
 
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
import com.hoiwanlouis.mystockportfolio.interfaces.IRecordFactory;


public class Trade extends Record {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // We define the type of record we want to create
    // by stating the factory to build trades
    IRecordFactory recordFactory;

    // The trade factory is sent to this method.
    // The factory will specifies what fields to populate

    public Trade(IRecordFactory recordFactory) {
        Log.v(DEBUG_TAG, "in Trade constructor");

        this.recordFactory = recordFactory;

    }

    public void prepare() {
        Log.v(DEBUG_TAG, "in prepare");

        // The fields needed were passed in the factory
        // should use factory builder method.

        company = recordFactory.addCompany(EFieldType.COMPANY_NAME, "");
        symbol = recordFactory.addSymbol(EFieldType.TICKER_SYMBOL, "");
        openingPrice = recordFactory.addOpeningPrice(EFieldType.OPENING_PRICE, 0.0);
        previousDaysClosingPrice = recordFactory.addPreviousDaysClosingPrice(EFieldType.PREVIOUS_DAYS_CLOSING_PRICE, 0.0);
        lastTradePrice = recordFactory.addTradePrice(EFieldType.TRADE_PRICE, 0.0);
        lastTradeQuantity = recordFactory.addTradeQuantity(EFieldType.TRADE_QUANTITY, 0.0);
        lastTradeDateTimeStamp = recordFactory.addTradeDateTimeStamp(EFieldType.TRADE_DATE_TIME_STAMP, "");

    }


}
