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
	private boolean selectBlobOn,clicked,oneFrame,showCPC,areYouSure,noSavedGame;
	private int selectBlobI,selection,p,numMenuStrings,numSaved,numSure;
	private static GlyphLayout gl;
	private static BitmapFontData bfD;
	private static Background b;
	private static long pauseTime;
	private static float menuTextScale,noSavedScale,sureScale,menuStringsX[],menuStringsW[],menuStringsH[],menuStringsY[],savedStringsX[],savedStringsW[],savedStringsH[],savedStringsY[],sureStringsX[],sureStringsW[],sureStringsH[],sureStringsY[];;
	private static final String [] menuStrings = {"New Game","Continue"};
	private static final String [] noSavedStrings ={"You have no saved game yet."};
	private static final String [] sureStrings = {"You already have a saved game.","Starting a new game will overwrite your saved game.","Are you sure?","Yes","No"};



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

		if (noSavedGame) {

			bfD.setScale(noSavedScale);
			for (p=0;p<numSaved;p++) {
				TH.bf.setColor(Color.GOLD);
				//if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); 
				TH.bf.draw(batch, noSavedStrings[p], savedStringsX[p], savedStringsY[p]);
			}


		} else if (areYouSure) {

			for (p=0;p<numSure;p++) {

				if (p<2) bfD.setScale(sureScale); else bfD.setScale (menuTextScale);

				TH.bf.setColor(Color.GOLD);
				if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); 
				TH.bf.draw(batch, sureStrings[p], sureStringsX[p], sureStringsY[p]);
			}


		} else {

			bfD.setScale(menuTextScale);

			for (p=0;p<numMenuStrings;p++) {
				TH.bf.setColor(Color.GOLD);
				if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); 
				TH.bf.draw(batch, menuStrings[p], menuStringsX[p], menuStringsY[p]);
			}

		}

		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked==true) {
			selectBlobOn=false;
			clicked=false;

			if (areYouSure) {
				if (selection==3) {
					GV.doNewGame();
					game.setScreen(game.gameScreen);
				}

				if (selection==4) {
					areYouSure=false;
				}

			} else 
				if (noSavedGame) {
					//doNothing
				} else 
				{
					System.out.println ("selection "+selection);
					if (selection==0) {
						GV.s=Save.loadGame();
						if (GV.s!=null) areYouSure=true; 
						else {
							GV.doNewGame();
							game.setScreen(game.gameScreen);
						}
					}

					if (selection==1) {
						System.out.println ("new game clicked");
						GV.s=Save.loadGame();
						if (GV.s==null) noSavedGame=true; else game.setScreen(game.gameScreen);			
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
		if (GV.numIntsShown<5 && GV.firstMM) {
			if (System.currentTimeMillis()-GV.lastAdShown>60*1000*3) game.myRequestHandler.sendMsg("showAd");
		}
		GV.firstMM=true;

		game.myRequestHandler.sendMsg("showBanner");

		areYouSure=false;
		noSavedGame=false;

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
	}

	@Override
	public boolean keyDown (int keycode) {


		return true;
	}

	@Override
	public boolean keyUp (int keycode) {

		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){

			if (noSavedGame) {
				noSavedGame=false; 
			} else {
				oneFrame=false;
				showCPC=true;
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
		selectBlobI=numMenuStrings+10;

		if (noSavedGame) {
			//noNothing
		} else 
			if (areYouSure) {
				for (p=3;p<5;p++) {
					if (x>sureStringsX[p] && x<sureStringsX[p]+sureStringsW[p] && y>sureStringsY[p] && y<sureStringsY[p]+sureStringsH[p]) {
						selectBlobI=p;
						selectBlobOn=true;
					}
				}

			}

			else {

				for (p=0;p<numMenuStrings;p++) {
					if (x>menuStringsX[p] && x<menuStringsX[p]+menuStringsW[p] && y>menuStringsY[p] && y<menuStringsY[p]+menuStringsH[p]) {
						selectBlobI=p;
						selectBlobOn=true;
					}
				}
			}

		return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {

		if (noSavedGame) {
			noSavedGame=false;
		} else
			if (areYouSure) {
				if (selectBlobOn) {
					selection=selectBlobI;
					clicked=true;
				}
			}

			else {

				if (selectBlobOn) {
					System.out.println ("touch up "+selectBlobI);
					selection=selectBlobI;
					clicked=true;
				}

			}
		return true;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {

		selectBlobOn=false;

		if (noSavedGame) {
			//doNothing
		} else if (areYouSure) {

			for (p=3;p<5;p++) {
				if (x>sureStringsX[p] && x<sureStringsX[p]+sureStringsW[p] && y>sureStringsY[p] && y<sureStringsY[p]+sureStringsH[p] && selectBlobI==p) {
					selectBlobOn=true;
				}
			}


		}
		else {

			for (p=0;p<numMenuStrings;p++) {
				if (x>menuStringsX[p] && x<menuStringsX[p]+menuStringsW[p] && y>menuStringsY[p] && y<menuStringsY[p]+menuStringsH[p] && selectBlobI==p) {
					selectBlobOn=true;
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

		float a=0;
		float z=0.1f;
		int breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,"AWHIL!,qpw?.");
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
			gl = new GlyphLayout (TH.bf,noSavedStrings[0]);
			a=gl.width;
			breaker++;
		} while (a<GV.w*0.75f && breaker<1500);

		noSavedScale = z;

		a=0;
		z=0.1f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,sureStrings[1]);
			a=gl.width;
			breaker++;
		} while (a<GV.w*0.75f && breaker<1500);

		sureScale = z;



		bfD.setScale(menuTextScale);

		numMenuStrings=menuStrings.length;
		numSaved=noSavedStrings.length;
		numSure = sureStrings.length;
		menuStringsX=new float [numMenuStrings];
		menuStringsY=new float [numMenuStrings];
		menuStringsW=new float [numMenuStrings];
		menuStringsH=new float [numMenuStrings];

		savedStringsX=new float [numSaved];
		savedStringsY=new float [numSaved];
		savedStringsW=new float [numSaved];
		savedStringsH=new float [numSaved];

		sureStringsX=new float [numSure];
		sureStringsY=new float [numSure];
		sureStringsW=new float [numSure];
		sureStringsH=new float [numSure];


		float logoH=0;/// change when you have a logo
		float menuSpacing = (GV.h-logoH) / (numMenuStrings+1);
		float savedSpacing = (GV.h-logoH) / (numSaved+1);
		float sureSpacing = (GV.h-logoH) / (numSure+1);

		for (p=0;p<numMenuStrings;p++) {
			gl = new GlyphLayout (TH.bf,menuStrings[p]);
			menuStringsX[p]=(GV.w-gl.width)/2.0f;
			menuStringsY[p]=menuSpacing*(p+1);
			menuStringsW[p]=gl.width;
			menuStringsH[p]=gl.height;
		}

		bfD.setScale(sureScale);

		for (p=0;p<2;p++) {
			gl = new GlyphLayout (TH.bf,sureStrings[p]);
			sureStringsX[p]=(GV.w-gl.width)/2.0f;
			sureStringsY[p]=sureSpacing*(p+1);
			sureStringsW[p]=gl.width;
			sureStringsH[p]=gl.height;
		}

		bfD.setScale(menuTextScale);

		for (p=2;p<5;p++) {
			gl = new GlyphLayout (TH.bf,sureStrings[p]);
			sureStringsX[p]=(GV.w-gl.width)/2.0f;
			sureStringsY[p]=sureSpacing*(p+1);
			sureStringsW[p]=gl.width;
			sureStringsH[p]=gl.height;
		}


		bfD.setScale(noSavedScale);

		for (p=0;p<numSaved;p++) {
			gl = new GlyphLayout (TH.bf,noSavedStrings[p]);
			savedStringsX[p]=(GV.w-gl.width)/2.0f;
			savedStringsY[p]=savedSpacing*(p+1);
			savedStringsW[p]=gl.width;
			savedStringsH[p]=gl.height;
		}

		bfD.setScale(menuTextScale);


	}

	private void updateTimers (long t) {
		b.nextAsteroid+=t;
		b.nextComet+=t;
		b.nextUFO+=t;
	}

}