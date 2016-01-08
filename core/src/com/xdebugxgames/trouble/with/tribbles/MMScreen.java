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
import com.badlogic.gdx.math.MathUtils;


public class MMScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean selectBlobOn,clicked,oneFrame,showCPC,areYouSure,noSavedGame,selectGameType,paused;
	private int selectBlobI,selection,p,numMenuStrings,numSure,numGameType;
	private static GlyphLayout gl;
	private static BitmapFontData bfD;
	private Background b;
	private static long pauseTime;
	private static float menuTextScale,sureScale,gameTypeScale,menuStringsX[],menuStringsW[],menuStringsH[],menuStringsY[],sureStringsX[],sureStringsW[],sureStringsH[],sureStringsY[],gameTypeStringsX[],gameTypeStringsW[],gameTypeStringsH[],gameTypeStringsY[],pausedX,pausedY,pausedScale,pausedW,pausedH;
	private static final String [] menuStrings = {"New Game","Continue","Options","Credits"};
	private static final String [] sureStrings = {"You already have a saved game.","Starting a new game will overwrite your saved game.","Are you sure?","Yes","No"};
	private static final String [] gameTypeStrings = {"Select gameplay type:","Endless","Clear the level"};
	private static final String pausedString = "Paused";



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
		
		if (paused) {

			batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
			batch.draw(TH.texts[TH.ItxtPixel], pausedX, pausedY, pausedW, pausedH);
			batch.setColor(Color.WHITE);
			
			bfD.setScale(pausedScale);
			TH.bf.setColor(Color.GOLD);
			TH.bf.draw(batch, pausedString, pausedX, pausedY);

		} else {

		if (areYouSure) {

			for (p=0;p<numSure;p++) {

				batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
				batch.draw(TH.texts[TH.ItxtPixel], sureStringsX[p], sureStringsY[p], sureStringsW[p], sureStringsH[p]);
				batch.setColor(Color.WHITE);
				
				if (p<2) bfD.setScale(sureScale); else bfD.setScale (menuTextScale);

				TH.bf.setColor(Color.GOLD);
				if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); 
				TH.bf.draw(batch, sureStrings[p], sureStringsX[p], sureStringsY[p]);
			}


		} else if (selectGameType) {

			bfD.setScale(gameTypeScale);

			for (p=0;p<numGameType;p++) {
				
				batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
				batch.draw(TH.texts[TH.ItxtPixel], gameTypeStringsX[p], gameTypeStringsY[p], gameTypeStringsW[p], gameTypeStringsH[p]);
				batch.setColor(Color.WHITE);

				
					TH.bf.setColor(Color.GOLD);
					if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); 
					TH.bf.draw(batch, gameTypeStrings[p], gameTypeStringsX[p], gameTypeStringsY[p]);
			}

			
		} else {

			bfD.setScale(menuTextScale);

			for (p=0;p<numMenuStrings;p++) {
				if (!(p==1 && noSavedGame)) {
					
					batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
					batch.draw(TH.texts[TH.ItxtPixel], menuStringsX[p], menuStringsY[p], menuStringsW[p], menuStringsH[p]);
					batch.setColor(Color.WHITE);
					
					TH.bf.setColor(Color.GOLD);
					if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); 
					TH.bf.draw(batch, menuStrings[p], menuStringsX[p], menuStringsY[p]);
				}
			}

		}

		}//not paused
		
		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked==true) {
			selectBlobOn=false;
			clicked=false;

			if (areYouSure) {
				if (selection==3) {
					selectGameType=true;
					areYouSure=false;
				}

				if (selection==4) {
					areYouSure=false;
				}

			} else if (selectGameType) {
				if (selection ==1) {
					GV.s = new SavedGame();
					GV.s.gameType=0;
					GV.doNewGame();
					selectGameType=false;
					game.setScreen(game.gameScreen);
				}
				if (selection == 2) {
					GV.s = new SavedGame();
					GV.s.gameType=1;
					GV.doNewGame();
					selectGameType=false;
					game.setScreen (game.gameScreen);
				}
				
			} else {

				if (selection==0) {
					GV.s=Save.loadGame();
					if (GV.s!=null) areYouSure=true; 
					else {
						selectGameType=true;
					}
				}

				if (!noSavedGame) if (selection==1) {
					GV.s=Save.loadGame();
					game.setScreen(game.gameScreen);			
				}
				
				if (selection==2) {
					game.setScreen (game.optionsScreen);
				}
				
				if (selection==3) {
					game.setScreen (game.creditsScreen);
				}
				
			}

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

		if (!paused) {

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

		}//notpaused
		
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


		clicked=false;

		areYouSure=false;
		noSavedGame=false;
		selectGameType=false;

		sizes();

		GV.s=Save.loadGame();
		if (GV.s==null) noSavedGame=true;
		
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

		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){
			if (paused) resumeGame(); else {
			if (areYouSure) areYouSure=false; else 
			if (selectGameType) selectGameType=false; else {
			oneFrame=false;
			showCPC=true;
			}
		}//not paused
			
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
		selectBlobI=numMenuStrings+10;

		if (!paused) {
		if (areYouSure) {
			for (p=3;p<5;p++) {
				if (x>sureStringsX[p] && x<sureStringsX[p]+sureStringsW[p] && y>sureStringsY[p] && y<sureStringsY[p]+sureStringsH[p]) {
					selectBlobI=p;
					selectBlobOn=true;
				}
			}

		}

		else if (selectGameType) {

			for (p=1;p<3;p++) {
				if (x>gameTypeStringsX[p] && x<gameTypeStringsX[p]+gameTypeStringsW[p] && y>gameTypeStringsY[p] && y<gameTypeStringsY[p]+gameTypeStringsH[p]) {
					selectBlobI=p;
					selectBlobOn=true;
				}
			}

			
		} else {

			for (p=0;p<numMenuStrings;p++) {
				if (x>menuStringsX[p] && x<menuStringsX[p]+menuStringsW[p] && y>menuStringsY[p] && y<menuStringsY[p]+menuStringsH[p]) {
					selectBlobI=p;
					selectBlobOn=true;
				}
			}
		}
		
		}//not paused

		return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {

		if (paused) resumeGame (); else {

		
		if (areYouSure) {
			if (selectBlobOn) {
				selection=selectBlobI;
				clicked=true;
			}
		}

		else {

			if (selectBlobOn) {
				selection=selectBlobI;
				clicked=true;
			}

		}
		
		}//notpaused
		
		return true;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {

		if (!paused) {
		selectBlobOn=false;

		if (areYouSure) {

			for (p=3;p<5;p++) {
				if (x>sureStringsX[p] && x<sureStringsX[p]+sureStringsW[p] && y>sureStringsY[p] && y<sureStringsY[p]+sureStringsH[p] && selectBlobI==p) {
					selectBlobOn=true;
				}
			}


		}
		else if (selectGameType) {
			
			for (p=1;p<3;p++) {
				if (x>gameTypeStringsX[p] && x<gameTypeStringsX[p]+gameTypeStringsW[p] && y>gameTypeStringsY[p] && y<gameTypeStringsY[p]+gameTypeStringsH[p] && selectBlobI==p) {
					selectBlobOn=true;
				}
			}
			
			
		} else {
			
			for (p=0;p<numMenuStrings;p++) {
				if (x>menuStringsX[p] && x<menuStringsX[p]+menuStringsW[p] && y>menuStringsY[p] && y<menuStringsY[p]+menuStringsH[p] && selectBlobI==p) {
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

	private void sizes() {
		b = new Background();

		bfD = TH.bf.getData();

		float a,z;
		int breaker;
		
		a=0.0f;
		z=0.1f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,menuStrings[0]);
			a=gl.height;
			breaker++;
		} while (a<GV.h*0.05f && breaker<1500);

		menuTextScale = z;

		a=0;
		z=0.1f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,sureStrings[1]);
			a=gl.width;
			breaker++;
		} while (a<GV.w*0.85f && breaker<1500);

		sureScale = z;

		a=0;
		z=0.1f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,gameTypeStrings[0]);
			a=gl.width;
			breaker++;
		} while (a<GV.w*0.90f && breaker<1500);

		gameTypeScale = z;
		
		z=0.1f;
		a=0.0f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,pausedString);
			a=gl.width;
			breaker++;
		} while (a<GV.w/3 && breaker<1500);

		pausedScale = z;

		pausedX=(GV.w-gl.width)/2.0f;
		pausedY=(GV.h-gl.height)/2.0f;
		pausedW=gl.width;
		pausedH=bfD.lineHeight;





		numMenuStrings=menuStrings.length;
		numSure = sureStrings.length;
		numGameType = gameTypeStrings.length;
		menuStringsX=new float [numMenuStrings];
		menuStringsY=new float [numMenuStrings];
		menuStringsW=new float [numMenuStrings];
		menuStringsH=new float [numMenuStrings];

		sureStringsX=new float [numSure];
		sureStringsY=new float [numSure];
		sureStringsW=new float [numSure];
		sureStringsH=new float [numSure];

		gameTypeStringsX=new float [numGameType];
		gameTypeStringsY=new float [numGameType];
		gameTypeStringsW=new float [numGameType];
		gameTypeStringsH=new float [numGameType];


		float logoH=0;/// change when you have a logo
		float menuSpacing = (GV.h-logoH) / (numMenuStrings+1);
		float sureSpacing = (GV.h-logoH) / (numSure+1);
		float gameTypeSpacing = (GV.h-logoH) / (numGameType+1);

		bfD.setScale(menuTextScale);

		
		for (p=0;p<numMenuStrings;p++) {
			gl = new GlyphLayout (TH.bf,menuStrings[p]);
			menuStringsX[p]=(GV.w-gl.width)/2.0f;
			menuStringsY[p]=menuSpacing*(p+1);
			menuStringsW[p]=gl.width;
			menuStringsH[p]=bfD.lineHeight;
		}

		bfD.setScale(sureScale);

		for (p=0;p<2;p++) {
			gl = new GlyphLayout (TH.bf,sureStrings[p]);
			sureStringsX[p]=(GV.w-gl.width)/2.0f;
			sureStringsY[p]=sureSpacing*(p+1);
			sureStringsW[p]=gl.width;
			sureStringsH[p]=bfD.lineHeight;
		}

		bfD.setScale(menuTextScale);

		for (p=2;p<5;p++) {
			gl = new GlyphLayout (TH.bf,sureStrings[p]);
			sureStringsX[p]=(GV.w-gl.width)/2.0f;
			sureStringsY[p]=sureSpacing*(p+1);
			sureStringsW[p]=gl.width;
			sureStringsH[p]=bfD.lineHeight;
		}

		bfD.setScale(gameTypeScale);

		for (p=0;p<numGameType;p++) {
			gl = new GlyphLayout (TH.bf,gameTypeStrings[p]);
			gameTypeStringsX[p]=(GV.w-gl.width)/2.0f;
			gameTypeStringsY[p]=gameTypeSpacing*(p+1);
			gameTypeStringsW[p]=gl.width;
			gameTypeStringsH[p]=bfD.lineHeight;
		}

		

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