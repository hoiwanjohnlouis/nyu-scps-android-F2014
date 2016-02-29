package com.hoiwanlouis.s14e05_robsimpleservicegps;

public class SimpleServiceActivity extends MenuActivity {
    @Override
    void prepareMenu() {
       addMenuItem("1. Service Control", ServiceControlActivity.class);
    }
}
