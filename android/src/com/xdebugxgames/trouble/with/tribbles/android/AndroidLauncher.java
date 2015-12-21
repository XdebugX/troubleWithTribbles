package com.xdebugxgames.trouble.with.tribbles.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.xdebugxgames.trouble.with.tribbles.IActivityRequestHandler;
import com.xdebugxgames.trouble.with.tribbles.Tribbles;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		config.useAccelerometer=false;
		
		initialize(new Tribbles(this), config);
	}

	@Override
	public void sendMsg(String msgg) {
		// TODO Auto-generated method stub
		
	}
}
