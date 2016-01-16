package com.xdebugxgames.trouble.with.tribbles.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.xdebugxgames.trouble.with.tribbles.GV;
import com.xdebugxgames.trouble.with.tribbles.IActivityRequestHandler;
import com.xdebugxgames.trouble.with.tribbles.Tribbles;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		config.useAccelerometer=false;
		config.hideStatusBar=true;
		config.maxSimultaneousSounds=3;
		config.numSamples=2;
		
		initialize(new Tribbles(this), config);
	}

	@Override
	public void sendMsg(String msgg) {
		// TODO Auto-generated method stub
		if (msgg.equals("showCPAd")) GV.exit=true;
		
	}

	@Override
	public void saveGame(byte[] s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame() {
		// TODO Auto-generated method stub
		
	}
}
