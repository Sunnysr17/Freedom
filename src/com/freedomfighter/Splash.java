package com.freedomfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Thread splashTimer = new Thread() {
			public void run() {
				try {
					sleep(3000);
					Intent mainIntent = new Intent(getApplicationContext(),
							Login.class);
					startActivity(mainIntent);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		splashTimer.start();
	}
}
