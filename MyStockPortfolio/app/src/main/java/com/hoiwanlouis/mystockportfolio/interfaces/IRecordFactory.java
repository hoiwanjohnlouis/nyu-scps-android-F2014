package com.hoiwanlouis.mystockportfolio.interfaces;

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

import com.hoiwanlouis.mystockportfolio.enums.EFieldType;
import com.hoiwanlouis.mystockportfolio.fields.Company;
import com.hoiwanlouis.mystockportfolio.fields.DateTimeStamp;
import com.hoiwanlouis.mystockportfolio.fields.Price;
import com.hoiwanlouis.mystockportfolio.fields.Quantity;
import com.hoiwanlouis.mystockportfolio.fields.Symbol;

public interface IRecordFactory {

    public Company addCompany(EFieldType fieldType, String longName);
    public Price addOpeningPrice(EFieldType fieldType, double price);
    public Price addPreviousDaysClosingPrice(EFieldType fieldType, double price);
    public Price addTradePrice(EFieldType fieldType, double price);
    public Quantity addTradeQuantity(EFieldType fieldType, double quantity);
    public Symbol addSymbol(EFieldType fieldType, String symbol);
    public DateTimeStamp addTradeDateTimeStamp(EFieldType fieldType, String dateTimeStamp);

}
