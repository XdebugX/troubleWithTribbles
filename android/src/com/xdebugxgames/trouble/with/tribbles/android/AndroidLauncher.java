package com.xdebugxgames.trouble.with.tribbles.android;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.xdebugxgames.trouble.with.tribbles.GV;
import com.xdebugxgames.trouble.with.tribbles.IActivityRequestHandler;
import com.xdebugxgames.trouble.with.tribbles.Tribbles;

import android.os.Message;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
	
	protected Handler handler;
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		config.useAccelerometer=false;
		config.hideStatusBar=true;
		config.maxSimultaneousSounds=3;
		config.numSamples=2;
		
		initialize(new Tribbles(this), config);
		
		
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				String msgg="";
				if (msg!=null) {

					if (msg.getData().getString ("message")!=null) msgg = msg.getData().getString ("message");

					if (msgg.equals ("ourGames")) {
						showGames();
					}

					if (msgg.equals ("showCPAd")) {
						GV.exit=true;//remove
						showCPAd();
					}

					
				
				}
			}
		};
		
	}

	@Override
	public void sendMsg(String msgg) {
		Message msg = handler.obtainMessage();
		Bundle bund = new Bundle();
		bund.putString ("message",msgg);
		msg.setData(bund);
		handler.sendMessage(msg);
	}
	
	private void showGames () {
		
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=XdebugX")));
		} catch (ActivityNotFoundException e) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=XdebugX")));
		}

	}
	
	private void showCPAd() {
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
