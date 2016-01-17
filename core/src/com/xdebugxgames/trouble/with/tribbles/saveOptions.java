package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;


public class saveOptions {
	
	public static void save (Options s) {
		Json json = new Json();

		String gameData = json.toJson( s );
		Preferences prefs = Gdx.app.getPreferences("Data");
		
        prefs.putString("options", gameData);
        
        prefs.flush();
		
}

public static Options loadOptions () {

	Options s=null;

	Json json = new Json();			
	
	String gameData=null;
		
	try {

		Preferences prefs = Gdx.app.getPreferences("Data");

        if (prefs.contains("options")) gameData = prefs.getString("options"); else return null;

		if (gameData!=null) s = (Options) json.fromJson( Options.class, gameData );
		
		} catch (Exception e) {};

		return (s);
}


	}
