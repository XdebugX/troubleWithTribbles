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


public class CreditsScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean clicked;
	
	private static GlyphLayout glphLay;
	private static BitmapFontData bfD;

	private static float fntH,creditTextScale;
	


	// constructor to keep a reference to the main Game class
	public CreditsScreen (Tribbles game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		if (delta>0.033333f) delta = 0.033333f;
		////////////////////////////////////////////// Render //////////////////////////////////////////////////////////		
		 Gdx.gl.glClearColor( 0, 0, 0, 1 );
		 Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		 
		batch.begin();
		
		TH.bf.setColor(Color.WHITE);
		bfD.setScale(creditTextScale);

		
		//TH.bf.draw(batch, strText[0], (GV.w-strTextW[0])/2.0f, GV.h-(GV.adH*1.5f)-(fntH*2.5f));
		
		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked==true) {
				clicked=false;
				game.setScreen(game.mmScreen);

		}
	}

	@Override
	public void resize(int width, int height) {
		GV.doSizes();
		TH.sizes();
		sizes();
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

		sizes();		
		clicked=false;
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
			clicked=true;
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
		clicked=true;
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
	
	
	private static void sizes () {
		bfD = TH.bf.getData();

		float a=0;
		float z=0.01f;
		int breaker=0;
				
		do {
			z=z+0.01f;
			bfD.setScale(z);
			glphLay = new GlyphLayout (TH.bf,"AWHIL!,qpw?.");
			a=glphLay.height;
			breaker++;
		} while (a<GV.h*0.05f && breaker<1500);
		
		creditTextScale = z;
		
		fntH=a;
		fntH=fntH+(fntH*0.3f);
}
	
}