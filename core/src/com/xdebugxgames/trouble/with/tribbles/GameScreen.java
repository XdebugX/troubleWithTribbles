package com.xdebugxgames.trouble.with.tribbles;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;


public class GameScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private static int p,t;
	private ShapeRenderer sr;
	private GlyphLayout gl;
	private Background b;
	private long pauseTime;

	private static boolean doBack;



	// constructor to keep a reference to the main Game class
	public GameScreen (Tribbles game) {
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
		
		batch.end();
		
	
		//update game
		
		if (doBack) {
			doBack=false;
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
		sr=new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);

		gl = new GlyphLayout ();

		instantiateArrays ();

		sizes();
			
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
		if (sr!=null) sr.dispose();
	}

	@Override
	public boolean keyDown (int keycode) {
		return true;
	}

	@Override
	public boolean keyUp (int keycode) {


		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){
			doBack=true;
		}
		return true;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {

		
			return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		return true;

	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {		
		return true;
	}

	@Override
	public boolean mouseMoved (int x, int y) {
		return true;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}

	private void sizes () {
	
		b=new Background();

		
	}
	
	private void instantiateArrays () {
	}
	
	
	private int abs (int x) {
		if (x>=0) return (x); else return (x*-1);
	}
	
	private float abs (float x) {
	if (x>=0) return (x); else return (x*-1);
	}
	
	private void updateTimers (long t) {
		b.nextAsteroid+=t;
		b.nextComet+=t;
		b.nextUFO+=t;
	}

	
}