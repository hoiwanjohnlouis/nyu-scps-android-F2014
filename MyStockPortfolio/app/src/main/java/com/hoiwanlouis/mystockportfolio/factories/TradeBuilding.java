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

import com.hoiwanlouis.mystockportfolio.fields.ERecordType;
import com.hoiwanlouis.mystockportfolio.fields.IRecordFactory;


public class TradeBuilding extends RecordBuilding {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    public Record makeRecord(ERecordType recordType) {
        Log.v(DEBUG_TAG, "in makeRecord:" + recordType);

        Record tradeRecord = (Trade) null;

        IRecordFactory tradeRecordFactory = new TradeFactory();
        tradeRecord = new Trade(tradeRecordFactory);
        tradeRecord.setRecordType(recordType);

        return tradeRecord;
    }

}
