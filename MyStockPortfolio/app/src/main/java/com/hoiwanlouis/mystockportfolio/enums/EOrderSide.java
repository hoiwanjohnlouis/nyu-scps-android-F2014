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
public enum EOrderSide {

    BUY ("1"),
    SELL ("2"),
    BUY_MINUS ("3"),
    SELL_PLUS ("4"),
    SELL_SHORT ("5"),
    SELL_SHORT_EXEMPT ("6"),
    UNDISCLOSED ("7"),
    CROSS ("8"),
    CROSS_SHORT ("9"),
    CROSS_SHORT_EXEMPT ("A"),
    AS_DEFINED ("B"),
    OPPOSITE ("C"),
    SUBSCRIBE ("D"),
    REDEEM ("E"),
    LEND_FINANCING ("F"),
    BORROW_FINANCING ("G");

    private final String orderSideValue;

    EOrderSide(final String side) {
        this.orderSideValue = side;
    }

    public String getOrderSideValue() {
        return orderSideValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name());
        sb.append(":[");
        sb.append(getOrderSideValue());
        sb.append("]");
        return sb.toString();
    }

}
