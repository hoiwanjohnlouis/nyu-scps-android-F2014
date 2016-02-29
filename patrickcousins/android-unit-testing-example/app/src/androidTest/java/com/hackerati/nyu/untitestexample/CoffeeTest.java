/*
    Copyright 2014 Patrick Cousins

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
package com.hackerati.nyu.untitestexample;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;

@RunWith(RobolectricTestRunner.class)
public class CoffeeTest {


    Pot mockPot;
    Heater mockHeater;
    CoffeeMaker coffeeMaker;


    @Before
    public void setup() {
        mockPot = mock(Pot.class);
        mockHeater = mock(Heater.class);
    }


    @Test
    public void testGetCoffee_isNotNull() {

        coffeeMaker = new CoffeeMaker(mockPot, mockHeater);

        when(mockPot.getSize()).thenReturn(-1);
        when(mockHeater.getTemp()).thenReturn(5000);

        assertThat(coffeeMaker.getCoffee()).isNull();

    }






}
