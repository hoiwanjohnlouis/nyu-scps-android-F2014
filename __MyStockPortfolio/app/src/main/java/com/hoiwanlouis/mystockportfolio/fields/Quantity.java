package com.hoiwanlouis.mystockportfolio.fields;

/*
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


import android.util.Log;

public class Quantity implements IQuantity {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    private EDescription description;
    private double quantity;

    @Override
    public EDescription getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(EDescription description) {
        this.description = description;
    }

    @Override
    public double getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(double quantity) {
        Log.v(DEBUG_TAG, "in setQuantity");
        this.quantity = quantity;
    }


}
