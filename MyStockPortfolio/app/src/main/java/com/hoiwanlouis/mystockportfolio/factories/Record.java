package com.hoiwanlouis.mystockportfolio.factories;
/**
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


import com.hoiwanlouis.mystockportfolio.enums.RecordType;
import com.hoiwanlouis.mystockportfolio.fields.Company;
import com.hoiwanlouis.mystockportfolio.fields.DateTimestamp;
import com.hoiwanlouis.mystockportfolio.fields.Price;
import com.hoiwanlouis.mystockportfolio.fields.Quantity;
import com.hoiwanlouis.mystockportfolio.fields.Symbol;


public abstract class Record implements Comparable {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    private RecordType recordType;
    private StringBuilder stringBuilder = new StringBuilder();

    protected Company company;                 // 1
    protected Symbol symbol;                   // 2
    protected Price openingPrice;              // 3
    protected Price previousDaysClosingPrice;  // 4
    protected Price lastTradePrice;            // 5
    protected Quantity lastTradeQuantity;      // 6
    protected DateTimestamp lastTradeDateTimestamp;    // 7


    // concrete definition is by whom inherits this class
    abstract void prepare();


    //
    public RecordType getRecordType() {
        return recordType;
    }

    //
    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }


    // If any EnemyShip object is printed to screen this shows up
    public String toString(){

        // clean up the buffer before using.
        stringBuilder.delete(0, stringBuilder.length());

        stringBuilder.append(this.getRecordType());
        stringBuilder.append("\n");
        stringBuilder.append(company.toString());
        stringBuilder.append(",\n");
        stringBuilder.append(symbol.toString());
        stringBuilder.append(",\n");
        stringBuilder.append(openingPrice.toString());
        stringBuilder.append(",\n");
        stringBuilder.append(previousDaysClosingPrice.toString());
        stringBuilder.append(",\n");
        stringBuilder.append(lastTradePrice.toString());
        stringBuilder.append(",\n");
        stringBuilder.append(lastTradeQuantity.toString());
        stringBuilder.append(",\n");
        stringBuilder.append(lastTradeDateTimestamp.toString());

        return stringBuilder.toString();

    }

    @Override
    public int compareTo(Object another) {
        // todo: add correct behavior once Trade record is fleshed out
        return 0;
    }

}
