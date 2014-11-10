package com.example.hoiwanlouis.s10e04_robsimpleservice;

public class SimpleServiceActivity extends MenuActivity {
	@Override
	void prepareMenu() {
		addMenuItem("1. Service Control", ServiceControlActivity.class);
	}
}