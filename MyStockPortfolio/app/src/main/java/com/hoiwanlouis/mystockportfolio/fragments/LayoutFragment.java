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
package com.hoiwanlouis.mystockportfolio.fragments;

import com.hoiwanlouis.mystockportfolio.R;

/***************************************************************************
 * Program Synopsis
 * <p>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * <p>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of 
 * the small dark slabs of the noble South Sea war-wood, are frequently met
 * with in the forecastles of American whalers. 
 *
 ***************************************************************************/
public final class LayoutFragment {

    public static final int toRIds[] = {
            R.id.TextView_symbol,
            R.id.TextView_opening_price,
            R.id.TextView_previous_closing_price,
            R.id.TextView_bid_price,
            R.id.TextView_ask_price,
            R.id.TextView_last_trade_price,
            R.id.TextView_last_trade_time
    };

    // DefaultValues: remove if not be needed. also remove related entries from res/values/strings.xml
    public static final class DefaultValues {
        private DefaultValues() {}
        public static final String defaultOpeningPrice = "" + R.string.default_opening_price;
        public static final String defaultClosingPrice = "" + R.string.default_closing_price;
        public static final String defaultBidPrice = "" + R.string.default_bid_price;
        public static final String defaultBidSize = "" + R.string.default_bid_size;
        public static final String defaultAskPrice = "" + R.string.default_ask_price;
        public static final String defaultAskSize = "" + R.string.default_ask_size;
        public static final String defaultTradePrice = "" + R.string.default_trade_price;
        public static final String defaultTradeQuantity = "" + R.string.default_trade_quantity;
    }


}
