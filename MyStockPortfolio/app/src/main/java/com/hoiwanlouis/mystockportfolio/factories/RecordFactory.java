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

import com.hoiwanlouis.mystockportfolio.enums.FieldType;
import com.hoiwanlouis.mystockportfolio.fields.Company;
import com.hoiwanlouis.mystockportfolio.fields.DateTimestamp;
import com.hoiwanlouis.mystockportfolio.fields.Price;
import com.hoiwanlouis.mystockportfolio.fields.Quantity;
import com.hoiwanlouis.mystockportfolio.fields.Symbol;

public interface RecordFactory {

    public Company addCompany(FieldType fieldType, String longName);
    public Price addOpeningPrice(FieldType fieldType, double price);
    public Price addPreviousDaysClosingPrice(FieldType fieldType, double price);
    public Price addTradePrice(FieldType fieldType, double price);
    public Quantity addTradeQuantity(FieldType fieldType, double quantity);
    public Symbol addSymbol(FieldType fieldType, String symbol);
    public DateTimestamp addTradeDateTimeStamp(FieldType fieldType, String dateTimeStamp);

}
