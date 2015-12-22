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
		config.width=480;
		config.height=640;
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
