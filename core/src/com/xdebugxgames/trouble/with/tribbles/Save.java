package com.xdebugxgames.trouble.with.tribbles;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;


public class Save {

	public static void saveGame (Tribbles trib, SavedGame s) {
		trib.myRequestHandler.saveGame(serialize (s));
		if (Gdx.files.isLocalStorageAvailable()) {
			
			Json json = new Json();

			String gameData = json.toJson( s );
			gameData = Base64Coder.encodeString( gameData );

			FileHandle handle = Gdx.files.local("saveGame.sav");
			handle.writeString(gameData, false);
		}//local files available
	}
	public static SavedGame loadGame () {
	
		SavedGame s=null;
		if (Gdx.files.isLocalStorageAvailable()) {
			Json json = new Json();			
			String gameData;
			FileHandle handle = Gdx.files.local("saveGame.sav");
			try {
			gameData = handle.readString();
			gameData = Base64Coder.decodeString( gameData );
			s = (SavedGame) json.fromJson( SavedGame.class, gameData );
			} catch (Exception e) {};
		}//local files available;
		return (s);
	}
    public static byte[] serialize(Object obj) {
    		try {
	        ByteArrayOutputStream b = new ByteArrayOutputStream();
	        ObjectOutputStream o = new ObjectOutputStream(b);
	        o.writeObject(obj);
	        return b.toByteArray();
    		} catch (IOException e) {
    			System.out.println ("IOException "+e);
    			return null;
    		}
	}
}
