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
	private boolean clicked,paused;
	
	private static GlyphLayout glphLay;
	private static BitmapFontData bfD;

	private Background b;
	private static float fntH,creditTextScale,textX[],textY[],textH[],textW[],pausedScale,pausedX,pausedY,pausedW,pausedH;
	private static int numTexts,p;
	private static long pauseTime;
	private static final String pausedString = "Paused";
	private static final String creditText[] = {"Music:","\"Pamgaea\" Kevin MacLeod (incompetech.com)","Licensed under Creative Commons: By Attribution 3.0","http://creativecommons.org/licenses/by/3.0/","Trouble With Tribbles","Tribbles are born pregnant!","We must get them off the ship!","Luckily there is a Klingon ship nearby.","In order to use the transporter efficiently,","the captain has ordered us to only beam","them when 2 or more are together.","Copyright 2016 XdebugX Games"};
	


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
			batch.setColor(Color.WHITE);
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
			
			if (paused) {
				
				batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
				batch.draw(TH.texts[TH.ItxtPixel], pausedX, pausedY, pausedW, pausedH);
				batch.setColor(Color.WHITE);
				
				bfD.setScale(pausedScale);
				TH.bf.setColor(Color.GOLD);
				TH.bf.draw(batch, pausedString, pausedX, pausedY);

			} else {
			
			TH.bf.setColor(Color.GOLD);
			
			bfD.setScale(creditTextScale);

			for (p=0;p<numTexts;p++) {
				batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
				batch.draw(TH.texts[TH.ItxtPixel], textX[p], textY[p], textW[p],textH[p]);
				batch.setColor(Color.WHITE);
				TH.bf.draw(batch, creditText[p], textX[p], textY[p]);
			}
			batch.setColor (Color.WHITE);
			}
		 
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
		int p=0;
		
		b=new Background ();
		
		bfD = TH.bf.getData();

		float a,z;
		int breaker;
		
		a=0.0f;
		z=0.1f;
		breaker=0;
			
		do {
			z=z+0.1f;
			bfD.setScale(z);
			glphLay = new GlyphLayout (TH.bf,creditText[2]);
			a=glphLay.width;
			breaker++;
		} while (a<GV.w*0.85f && breaker<1500);
		
		creditTextScale = z;
		float fontH=glphLay.height;
		
		numTexts = creditText.length;
		textX=new float [numTexts];
		textY=new float [numTexts];
		textW=new float [numTexts];
		textH=new float [numTexts];
		
		float logoH=0;/// change when you have a logo
		
		float sectionSpacing,storySpacing;
		sectionSpacing = (GV.h-logoH)/3;
		storySpacing = sectionSpacing / 8;
		
		for (p=0;p<4;p++) {
			glphLay = new GlyphLayout (TH.bf,creditText[p]);
			textX[p]=(GV.w-glphLay.width)/2.0f;
			textY[p]=logoH+((fontH*2)*p);
			textW[p]=glphLay.width;
			textH[p]=bfD.lineHeight;
		}		
		

		for (p=4;p<11;p++) {
			glphLay = new GlyphLayout (TH.bf,creditText[p]);
			textX[p]=(GV.w-glphLay.width)/2.0f;
			textY[p]=logoH+sectionSpacing+(storySpacing*p);
			textW[p]=glphLay.width;
			textH[p]=bfD.lineHeight;
		}		

		glphLay = new GlyphLayout (TH.bf,creditText[p]);
		textX[p]=(GV.w-glphLay.width)/2.0f;
		textY[p]=GV.h-(fontH*2);
		textW[p]=glphLay.width;
		textH[p]=bfD.lineHeight;
		
		z=0.1f;
		a=0.0f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			glphLay = new GlyphLayout (TH.bf,pausedString);
			a=glphLay.width;
			breaker++;
		} while (a<GV.w/3 && breaker<1500);

		pausedScale = z;

		pausedX=(GV.w-glphLay.width)/2.0f;
		pausedY=(GV.h-glphLay.height)/2.0f;
		pausedW=glphLay.width;
		pausedH=bfD.lineHeight;
		
}
	
	private void resumeGame () {
		updateTimers (System.currentTimeMillis()-pauseTime);
		paused=false;
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}


	private void updateTimers (long t) {
		b.nextAsteroid+=t;
		b.nextComet+=t;
		b.nextUFO+=t;
	}
	
}