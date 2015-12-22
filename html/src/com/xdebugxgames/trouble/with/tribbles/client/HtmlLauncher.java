package com.xdebugxgames.trouble.with.tribbles.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.xdebugxgames.trouble.with.tribbles.IActivityRequestHandler;
import com.xdebugxgames.trouble.with.tribbles.Tribbles;

public class HtmlLauncher extends GwtApplication implements IActivityRequestHandler {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 640);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new Tribbles(this);
        }

		@Override
		public void sendMsg(String msgg) {
			// TODO Auto-generated method stub
			
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