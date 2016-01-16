package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class StartScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean clicked,paused;
	
 
	private static float pausedX,pausedY,logoX,logoY,doorLX,doorLY,doorRX,doorRY,btnX,btnY,wallX,wallY,openAmt,openAmtInc,scale;
	private static long pauseTime;
	private static boolean doorOpen;
	
	// constructor to keep a reference to the main Game class
	public StartScreen (Tribbles game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		if (delta>0.033333f) delta = 0.033333f;
		////////////////////////////////////////////// Render //////////////////////////////////////////////////////////		
		 Gdx.gl.glClearColor( 0, 0, 0, 1 );
		 Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
	

			batch.begin();

			batch.draw(TH.texts[TH.ItxtBack], 0, 0, TH.textsW[TH.ItxtBack], TH.textsH[TH.ItxtBack]);
			batch.draw(TH.texts[TH.ItxtPlanet], GV.planetX, GV.planetY, TH.textsW[TH.ItxtPlanet], TH.textsH[TH.ItxtPlanet]);
			batch.draw(TH.texts[TH.ItxtSatt], GV.sattX, GV.sattY, TH.textsW[TH.ItxtSatt], TH.textsH[TH.ItxtSatt]);
			
			batch.draw(TH.texts[TH.ItxtStartDoorL], doorLX-openAmt, doorLY, TH.textsW[TH.ItxtStartDoorL] / 2.0f, TH.textsH[TH.ItxtStartDoorL] / 2.0f, TH.textsW[TH.ItxtStartDoorL], TH.textsH[TH.ItxtStartDoorL], scale, scale, 0.0f);
			batch.draw(TH.texts[TH.ItxtStartDoorR], doorRX+openAmt, doorRY, TH.textsW[TH.ItxtStartDoorR] / 2.0f, TH.textsH[TH.ItxtStartDoorR] / 2.0f,TH.textsW[TH.ItxtStartDoorR], TH.textsH[TH.ItxtStartDoorR], scale, scale, 0.0f);
			batch.draw(TH.texts[TH.ItxtStartBtn], btnX-openAmt, btnY, TH.textsW[TH.ItxtStartBtn]/2.0f, TH.textsH[TH.ItxtStartBtn]/2.0f, TH.textsW[TH.ItxtStartBtn], TH.textsH[TH.ItxtStartBtn], scale, scale, 0.0f);
			batch.draw(TH.texts[TH.ItxtStartWall], wallX, wallY, TH.textsW[TH.ItxtStartWall]/2.0f, TH.textsH[TH.ItxtStartWall]/2.0f, TH.textsW[TH.ItxtStartWall], TH.textsH[TH.ItxtStartWall], scale, scale, 0.0f);
			batch.draw(TH.texts[TH.ItxtLogo], logoX, logoY, TH.textsW[TH.ItxtLogo], TH.textsH[TH.ItxtLogo]);

			if (paused) {
							
				batch.draw(TH.texts[TH.ItxtPixel], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);
				batch.draw(TH.texts[TH.ItxtPaused], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);

			}
			
		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked==true && !doorOpen) {
			clicked=false;
			doorOpen=true;
		}
		
		if (doorOpen) {
			openAmt+=openAmtInc*delta*scale;
			scale+=0.5f*delta;
			if (openAmt>GV.w/2.0f*scale) game.setScreen(game.mmScreen);
		}
	}

	@Override
	public void resize(int width, int height) {
		GV.doSizes();
		TH.sizes();
		sizes();
		camera.setToOrtho(true, GV.w, GV.trueH);
		camera.position.set(GV.w / 2, GV.trueH / 2, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void show() {
		camera = new OrthographicCamera(GV.w, GV.trueH);
		camera.setToOrtho(true, GV.w, GV.trueH);
		camera.position.set(GV.w / 2, GV.trueH / 2, 0);
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);		

		sizes();		
		clicked=false;
		doorOpen=false;
		openAmt=0.0f;
		
		
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}

	@Override
	public void hide() {
		for (int p=0;p<TH.numMusic;p++) if (TH.loopingMusic!=null) if (TH.loopingMusic[p]!=null) TH.loopingMusic[p].pause();
	}
		
	@Override
	public void pause() {
		pauseTime=System.currentTimeMillis();
		int p;
		for (p=0;p<TH.numMusic;p++) if (TH.loopingMusic!=null) if (TH.loopingMusic[p]!=null) TH.loopingMusic[p].pause();
		paused=true;
	}
		
	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		if (batch!=null) batch.dispose();
	}

	@Override
	public boolean keyDown (int keycode) {

		
		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			if (paused) resumeGame(); else {
			clicked=true;
		}
		}
		return true;
	}

	@Override
	public boolean keyTyped (char character) {
		return true;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		if (paused) resumeGame(); else clicked=true;
		return true;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		return true;
	}

	@Override
	public boolean mouseMoved (int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}	
	
	
	private void sizes () {	
		pausedX=(GV.w-TH.textsW[TH.ItxtPaused]) / 2.0f;
		pausedY=(GV.h-TH.textsH[TH.ItxtPaused]) / 2.0f;

		
		logoX=(GV.w-TH.textsW[TH.ItxtLogo])/2.0f;
		logoY=0.0f;
		wallX=0.0f;
		wallY=0.0f;
		doorLX=44.0f*GV.aspRatW;
		doorLY=238.0f*GV.aspRatW;
		doorRY=238.0f*GV.aspRatW;
		doorRX=292.0f*GV.aspRatW;
		btnX=210*GV.aspRatW;
		btnY=697.0f*GV.aspRatW;
		
		openAmtInc=GV.w/2.0f;
		
		scale=1.0f;
		
}
	
	private void resumeGame () {
		updateTimers (System.currentTimeMillis()-pauseTime);
		paused=false;
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}


	private void updateTimers (long t) {
	}
	
}