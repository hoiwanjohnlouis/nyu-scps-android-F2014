/***********************************************************************
 * Copyright 2016 HW Tech Services, LLC
 * Licensed under the Apache License, Version 2.0 (the "License");
 * http://www.apache.org/licenses/LICENSE-2.0
 ***********************************************************************/
package com.hfad.beeradvisor;

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    List<String> getBeerBrandsByColor(String beerColor) {
        List<String> beerBrandList = new ArrayList<>();
        if (beerColor.equals("light")) {
            beerBrandList.add("Miller Lite");
        }
        else
        if (beerColor.equals("amber")) {
            beerBrandList.add("Jack Amber");
            beerBrandList.add("Red Moose");
        }
        else
        if (beerColor.equals("brown")) {
            beerBrandList.add("Jail Pale Ale");
        }
        else
        if (beerColor.equals("dark")) {
            beerBrandList.add("Guiness");
        }
        return beerBrandList;
    }

}
