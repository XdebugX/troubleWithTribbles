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


public class OptionsScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean clicked,paused;

	private static GlyphLayout glphLay;
	private static BitmapFontData bfD;

	private Background b;
	private static float fntH,optionsTextScale,textX[],textY[],textH[],textW[],pausedScale,pausedX,pausedY,pausedW,pausedH;
	private static int numTexts,p,selectBlobI,selection;
	private static long pauseTime;
	private static final String pausedString = "Paused";
	private static final String optionsText[] = {"Sound: ON","Music: ON","Done."};
	private static boolean doBack,selectBlobOn;



	// constructor to keep a reference to the main Game class
	public OptionsScreen (Tribbles game) {
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
			bfD.setScale(optionsTextScale);

			for (p=0;p<numTexts;p++) {
				batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
				batch.draw(TH.texts[TH.ItxtPixel], textX[p], textY[p], textW[p],textH[p]);
				batch.setColor (Color.WHITE);
				
				if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); else TH.bf.setColor(Color.GOLD);
				TH.bf.draw(batch, optionsText[p], textX[p], textY[p]);
			}
		}

		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked) {
			clicked=false;
			if (selection==0) {
				GV.opts.sfxOn=!GV.opts.sfxOn;
				saveOptions.save(GV.opts);
			}
			
			if (selection==1) {
				GV.opts.musicOn=!GV.opts.musicOn;
				saveOptions.save(GV.opts);
			}
			
			if (selection==2) doBack=true;
			
			if (GV.opts.sfxOn) optionsText[0]="Sound: ON"; else optionsText[0]="Sound: OFF";
			if (GV.opts.musicOn) {
				optionsText[1]="Music: ON";
				TH.loopingMusic[TH.ImusicMM].play();
			}
			else {
				for (p=0;p<TH.numMusic;p++) if (TH.loopingMusic!=null) if (TH.loopingMusic[p]!=null) TH.loopingMusic[p].pause();				
				optionsText[1]="Music: OFF";
			}


		}

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
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);		

		sizes();		
		clicked=false;
		doBack=false;

		if (GV.opts.musicOn) TH.loopingMusic[TH.ImusicMM].play();
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
				doBack=true;
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

		int p=0;
		selectBlobOn=false;
		selectBlobI=numTexts+10;

		if (!paused) {
			for (p=0;p<numTexts;p++) {
				if (x>textX[p] && x<textX[p]+textW[p] && y>textY[p] && y<textY[p]+textH[p]) {
					selectBlobI=p;
					selectBlobOn=true;
				}

			}
		}

		return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		if (paused) resumeGame(); else {
			if (selectBlobOn) {
				selection=selectBlobI;
				clicked=true;
			}
			selectBlobOn=false;
		}
		return true;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {

		if (!paused) {
			if (selectBlobOn) {
				selectBlobOn=false;
				for (p=0;p<numTexts;p++) {
					if (x>textX[p] && x<textX[p]+textW[p] && y>textY[p] && y<textY[p]+textH[p] && selectBlobI==p) {
						selectBlobOn=true;
					}
				}

			}
		}


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
			glphLay = new GlyphLayout (TH.bf,optionsText[1]);
			a=glphLay.width;
			breaker++;
		} while (a<GV.w*0.65f && breaker<1500);

		optionsTextScale = z;
		float fontH=glphLay.height;

		numTexts = optionsText.length;
		textX=new float [numTexts];
		textY=new float [numTexts];
		textW=new float [numTexts];
		textH=new float [numTexts];

		float logoH=0;/// change when you have a logo

		float sectionSpacing;
		sectionSpacing = (GV.h-logoH)/4;

		if (GV.opts.sfxOn) optionsText[0]="Sound: ON"; else optionsText[0]="Sound: OFF";
		if (GV.opts.musicOn) optionsText[1]="Music: ON"; else optionsText[1]="Music: OFF";


		for (p=0;p<numTexts;p++) {
			glphLay = new GlyphLayout (TH.bf,optionsText[p]);
			textX[p]=(GV.w-glphLay.width)/2.0f;
			textY[p]=logoH+(sectionSpacing*(p+1));
			textW[p]=glphLay.width;
			textH[p]=bfD.lineHeight;
		}		

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
		if (GV.opts.musicOn) TH.loopingMusic[TH.ImusicMM].play();
	}


	private void updateTimers (long t) {
		b.nextAsteroid+=t;
		b.nextComet+=t;
		b.nextUFO+=t;
	}

}