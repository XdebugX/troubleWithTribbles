package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MMScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean selectBlobOn,clicked,oneFrame,showCPC;
	private int selectBlobI,selection;
	private static GlyphLayout gl;



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
		int p=0;
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
		int p=0,a=0;
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
		int p=0,a=0;
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
	
	}
}