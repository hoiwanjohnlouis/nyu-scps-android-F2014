/***************************************************************************
 * Copyright March, 2016 HW Tech Services, LLC
 * <p/>
 * Login   HW Tech Services, LLC
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package com.hoiwanlouis.mystockportfolio.enums;

/***************************************************************************
 * Program Synopsis
 * <p/>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * <p/>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of
 *
 ***************************************************************************/
// from FixProtocol.org website www.fixprotocol.org
public enum EFieldType {

//    DEFAULT_COMPANY_NAME,
//    DEFAULT_TICKER_SYMBOL,
//    DEFAULT_PRICE,
//    DEFAULT_QUANTITY,
//    DEFAULT_DATE_TIME_STAMP,

    COMPANY_NAME("COMPANY_NAME"),
    TICKER_SYMBOL("55"),
    OPENING_PRICE("OPENING_PRICE"),
    PREVIOUS_DAYS_CLOSING_PRICE("PREVIOUS_DAYS_CLOSING_PRICE"),
    TRADE_PRICE("31"),
    TRADE_QUANTITY("32"),
    TRADE_DATE_TIME_STAMP("60");

    private final String fieldTypeValue;

    private EFieldType(final String fieldTypeValue) {
        this.fieldTypeValue = fieldTypeValue;
    }

    public String getFieldTypeValue() {
        return fieldTypeValue;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name());
        sb.append(":[");
        sb.append(getFieldTypeValue());
        sb.append("]");
        return sb.toString();
    }
}
