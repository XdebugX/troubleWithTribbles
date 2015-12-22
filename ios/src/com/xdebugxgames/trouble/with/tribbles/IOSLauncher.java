package com.xdebugxgames.trouble.with.tribbles;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.xdebugxgames.trouble.with.tribbles.Tribbles;

public class IOSLauncher extends IOSApplication.Delegate implements IActivityRequestHandler {
    
	@Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.allowIpod=true;
        config.orientationLandscape=false;
        config.orientationPortrait=true;
        config.useAccelerometer=false;
        config.preventScreenDimming=true;        
      
        return new IOSApplication(new Tribbles(this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

	@Override
	public void sendMsg(String msgg) {
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