package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


public class MMScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean selectBlobOn,clicked,oneFrame,showCPC;
	private int selectBlobI,selection,p;
	private static GlyphLayout gl;
	private static Background b;
	private static long pauseTime;



	// constructor to keep a reference to the main Game class
	public MMScreen (Tribbles game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		if (delta>0.033333f) delta = 0.033333f;
		////////////////////////////////////////////// Render //////////////////////////////////////////////////////////		
		 Gdx.gl.glClearColor( 0, 0, 0, 1 );
		 Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		 
		batch.begin();
		
		for (p=0;p<b.numStrips;p++) {
			batch.draw(TH.strips[b.stripNum40[p]], 0, b.stripY[p], TH.stripsW[0], TH.stripsH[0]);
			batch.draw(TH.strips[10+b.stripNum10[p]], 0, b.stripY[p]+TH.stripsH[0], TH.stripsW[10], TH.stripsH[10]);
			batch.draw(TH.strips[20+b.stripNum20[p]], 0, b.stripY[p]+TH.stripsH[0]+TH.stripsH[10], TH.stripsW[20], TH.stripsH[20]);
		}
		
		if (b.isPlanet) batch.draw(TH.texts[TH.ItxtPlanet], b.planetX, b.planetY, TH.textsW[TH.ItxtPlanet], TH.textsH[TH.ItxtPlanet]);
		if (b.isSatt) batch.draw(TH.texts[TH.ItxtSatt], b.sattX, b.sattY, TH.textsW[TH.ItxtSatt], TH.textsH[TH.ItxtSatt]);
		if (b.asteroidOnScreen) batch.draw(TH.texts[TH.ItxtAsteroids+b.asteroidType], b.asteroidX, b.asteroidY, TH.textsW[TH.ItxtAsteroids+b.asteroidType],TH.textsH[TH.ItxtAsteroids+b.asteroidType]);
		if (b.cometOnScreen) {			
		if (!b.cometDX) batch.draw(TH.texts[TH.ItxtComets+b.cometType], b.cometX, b.cometY, TH.textsW[TH.ItxtComets+b.cometType], TH.textsH[TH.ItxtComets+b.cometType]);								
		else batch.draw(TH.texts[TH.ItxtComets+b.cometType], b.cometX, b.cometY, TH.textsW[TH.ItxtComets+b.cometType]/2.0f, TH.textsH[TH.ItxtComets+b.cometType]/2.0f, TH.textsW[TH.ItxtComets+b.cometType], TH.textsH[TH.ItxtComets+b.cometType],1.0f,1.0f,270.0f);
		}
		
		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked==true) {
				selectBlobOn=false;
				clicked=false;
			
		}
		
		
		if (GV.exit) {
			GV.exit=false;
			Gdx.app.exit(); 
		}
		
		if (showCPC) {
			if (oneFrame) {
				game.myRequestHandler.sendMsg("showCPAd");
				showCPC=false;
			} else oneFrame=true;
		
		}

		//////////////////do background stuff
		if (!b.asteroidOnScreen) if (System.currentTimeMillis()>b.nextAsteroid) {
			GV.spawnAsteroid(b);
		}		
		if (b.asteroidOnScreen) {
			GV.moveAsteroid(b, delta);	
		}

		if (!b.cometOnScreen) if (System.currentTimeMillis()>b.nextComet) {
			GV.spawnComet(b);
		}		
		if (b.cometOnScreen) {
			GV.moveComet(b, delta);	
		}

		
	}

	@Override
	public void resize(int width, int height) {
		GV.doSizes();
		TH.sizes();
		doSizes();
		camera.setToOrtho(true, GV.w, GV.h);
		camera.position.set(GV.w / 2, GV.h / 2, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void show() {
		camera = new OrthographicCamera(GV.w, GV.h);
		camera.setToOrtho(true, GV.w, GV.h);
		camera.position.set(GV.w / 2, GV.h / 2, 0);
		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);		
		

		clicked=false;
		if (GV.numIntsShown<5 && GV.firstMM) {
			if (System.currentTimeMillis()-GV.lastAdShown>60*1000*3) game.myRequestHandler.sendMsg("showAd");
		}
		GV.firstMM=true;
		
		game.myRequestHandler.sendMsg("showBanner");
		
		doSizes();
	}

	@Override
	public void hide() {
	}
	@Override
	public void pause() {
		pauseTime=System.currentTimeMillis();
	}

	@Override
	public void resume() {
		updateTimers (System.currentTimeMillis()-pauseTime);
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
		
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){
			oneFrame=false;
			showCPC=true;
		}
		return true;
	}

	@Override
	public boolean keyTyped (char character) {
		return true;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		selectBlobOn=false;
		selectBlobI=10;
		
		return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		if (selectBlobOn) {
			selection=selectBlobI;
			clicked=true;
		}
		selectBlobI=100;
		return true;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		selectBlobOn=false;

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
	
	private void doSizes() {
	b = new Background();
	}
	
	private void updateTimers (long t) {
		b.nextAsteroid+=t;
		b.nextComet+=t;
		b.nextUFO+=t;
	}
	
}