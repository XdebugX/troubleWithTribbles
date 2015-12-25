package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class Tribbles extends Game implements ApplicationListener {

	public SplashScreen splashScreen;
	public MMScreen mmScreen;
	public GameScreen gameScreen;
	public CreditsScreen creditsScreen;
    public IActivityRequestHandler myRequestHandler;

    public Tribbles (IActivityRequestHandler handler) {
        myRequestHandler = handler;
    }
	
	@Override
	public void create() {		
		GV.opts = new Options ();
		splashScreen = new SplashScreen(this);
		mmScreen = new MMScreen(this);
		gameScreen = new GameScreen (this);
		creditsScreen = new CreditsScreen (this);
		
		
		GV.opts = saveOptions.loadOptions();
		if (GV.opts==null) {
			GV.opts = new Options ();
			GV.opts.musicOn=true;
			GV.opts.sfxOn=true;
			saveOptions.save(GV.opts);
		}
		
		GV.s=Save.loadGame();
		
		setScreen(splashScreen); 		
	}

	@Override
	public void dispose() {
		if (gameScreen!=null) gameScreen.dispose();		
		if (splashScreen!=null) splashScreen.dispose();
		if (mmScreen!=null) mmScreen.dispose();
		if (creditsScreen!=null) creditsScreen.dispose();
		TH.dispose();	  
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize (width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	public void saveOpts () {
		saveOptions.save(GV.opts);
	}
		
}