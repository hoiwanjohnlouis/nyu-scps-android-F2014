package com.example.hoiwanlouis.s09e51_myandroidservice;

public class SimpleServiceActivity extends MenuActivity {
	@Override
	void prepareMenu() {
		addMenuItem("1. Service Control", ServiceControlActivity.class);
	}
}