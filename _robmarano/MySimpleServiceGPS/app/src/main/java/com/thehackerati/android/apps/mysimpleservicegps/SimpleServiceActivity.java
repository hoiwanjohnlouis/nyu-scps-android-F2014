package com.thehackerati.android.apps.mysimpleservicegps;

public class SimpleServiceActivity extends MenuActivity {
    @Override
    void prepareMenu() {
       addMenuItem("1. Service Control", ServiceControlActivity.class);
    }
}
