package com.hoiwanlouis.mystockportfolio.fields;

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

// from FixProtocol.org website www.fixprotocol.org

public enum EOrderSide {

    BUY (1),
    SELL (2),
    BUY_MINUS (3),
    SELL_PLUS (4),
    SELL_SHORT (5),
    SELL_SHORT_EXEMPT (6),
    UNDISCLOSED (7),
    CROSS (8),
    CROSS_SHORT (9),
    CROSS_SHORT_EXEMPT ('A'),
    AS_DEFINED ('B'),
    OPPOSITE ('C'),
    SUBSCRIBE ('D'),
    REDEEM ('E'),
    LEND_FINANCING ('F'),
    BORROW_FINANCING ('G');

    private int value;

    private EOrderSide(int value) {
        this.value = value;
    }

}
