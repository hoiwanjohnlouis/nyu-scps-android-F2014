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
public enum EMsgType {

    HEARTBEAT("0", "Heartbeat"),
    TEST_REQUEST("1", "Test Request"),
    RESEND_REQUEST("2", "Resend Request"),
    REJECT("3", "Reject"),
    SEQUENCE_RESET("4", "Sequence Reset"),
    LOGOUT("5", "Logout"),
    EXECUTION_REPORT("8", "Execution Report"),
    ORDER_CANCEL_REJECT("9", "Order Cancel Reject"),
    NEW_ORDER("D", "New Order - Single"),
    ORDER_CANCEL_REQUEST("F", "Order Cancel Request"),
    ORDER_MODIFICATION_REQUEST("G", "Order Cancel/Replace Request"),
    ORDER_STATUS_REQUEST("H", "Order Status Request");

    private final String msgTypeValue;
    private final String msgTypeDescription;

    private EMsgType(final String msgType, final String msgDescription) {
        this.msgTypeValue = msgType;
        this.msgTypeDescription = msgDescription;
    }

    public String getMsgTypeValue() {
        return msgTypeValue;
    }

    public String getMsgTypeDescription() {
        return msgTypeDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name());
        sb.append(":[");
        sb.append(getMsgTypeValue());
        sb.append("]");
        sb.append(":[");
        sb.append(getMsgTypeDescription());
        sb.append("]");
        return sb.toString();
    }

}
