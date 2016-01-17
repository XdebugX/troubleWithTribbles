package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;


public class Save {

	public static void saveGame (Tribbles trib, SavedGame s) {
			Json json = new Json();

			String gameData = json.toJson( s );
			Preferences prefs = Gdx.app.getPreferences("Data");
			
            prefs.putString("slot1", gameData);
            
            prefs.flush();
			
	}
	
	public static SavedGame loadGame () {
	
		SavedGame s=null;

		Json json = new Json();			
		
		String gameData=null;
			
		try {

			Preferences prefs = Gdx.app.getPreferences("Data");

            if (prefs.contains("slot1")) gameData = prefs.getString("slot1"); else return null;

			if (gameData!=null) s = (SavedGame) json.fromJson( SavedGame.class, gameData );
			
			} catch (Exception e) {};

			return (s);
	}
	
}
