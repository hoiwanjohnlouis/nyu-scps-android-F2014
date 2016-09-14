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
package com.hoiwanlouis.mystockportfolio.fields;

import android.util.Log;

import com.hoiwanlouis.mystockportfolio.enums.FieldType;

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
public class DateTimestamp {
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    private final FieldType fieldType;
    private final String dateTimestamp;
    private final StringBuilder sb;

    public DateTimestamp(final FieldType fieldType, final String dateTimestamp) {
        Log.v(DEBUG_TAG, "in constructor(..)");
        this.fieldType = fieldType;
        this.dateTimestamp = dateTimestamp;
        sb = new StringBuilder();
    }

    public String getDateTimestamp() {
        return this.dateTimestamp;
    }

    public static final String extractDateSegment(final String dateTimestamp) {
        // dateTimestamps are stored as YYYY-MM-DD HH:MM:SS.SSS"
        // split up the fields to facilitate display
        String[] segment = dateTimestamp.split(" ");
        return segment[0];
    }

    public static final String extractTimestampSegment(final String dateTimestamp) {
        // dateTimestamps are stored as YYYY-MM-DD HH:MM:SS.SSS"
        // split up the fields to facilitate display
        String[] segment = dateTimestamp.split(" ");
        return segment[1];
    }

    @Override
    public String toString() {
        sb.setLength(0);
        sb.append(fieldType.toString());
        sb.append(":[");
        sb.append(getDateTimestamp());
        sb.append("]");
        return sb.toString();
    }

}
