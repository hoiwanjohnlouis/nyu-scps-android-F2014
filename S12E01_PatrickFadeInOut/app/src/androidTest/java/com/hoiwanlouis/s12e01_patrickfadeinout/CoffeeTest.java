package com.hoiwanlouis.s12e01_patrickfadeinout;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import dalvik.annotation.TestTargetClass;

/**
 * Created by hoiwanlouis on 11/22/14.
 *
 */
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
    public void testGetCoffeeMaker_isNotNull() {

    }



}
