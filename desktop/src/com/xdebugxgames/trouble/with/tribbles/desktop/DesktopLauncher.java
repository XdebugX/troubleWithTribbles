package com.xdebugxgames.trouble.with.tribbles.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xdebugxgames.trouble.with.tribbles.GV;
import com.xdebugxgames.trouble.with.tribbles.IActivityRequestHandler;
import com.xdebugxgames.trouble.with.tribbles.Tribbles;

public class DesktopLauncher implements IActivityRequestHandler {
	
	private static DesktopLauncher application;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=607;//750;//607;
		config.height=1080;//1334;//1080;
		config.backgroundFPS=-1;
		config.foregroundFPS=66;
		config.vSyncEnabled=false;
		
		config.title="Trouble with Tribbles";
		if (application == null) {
			application = new DesktopLauncher();
		}

		
		new LwjglApplication(new Tribbles(application), config);
	}

	@Override
	public void sendMsg(String msgg) {
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
