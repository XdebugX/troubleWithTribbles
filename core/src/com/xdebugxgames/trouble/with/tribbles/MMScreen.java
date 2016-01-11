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
	private boolean selectBlobOn,clicked,oneFrame,showCPC,areYouSure,noSavedGame,selectGameType,paused;
	private int selectBlobI,selection,p,numMenuStrings,numGameType,numBtns,btnsTexts[],btnsTextsSure[];
	private static GlyphLayout gl;
	private static BitmapFontData bfD;
	private static float gameTypeScale,gameTypeStringsX[],gameTypeStringsW[],gameTypeStringsH[],gameTypeStringsY[],pausedX,pausedY,btnsX,btnsSpac,btnsY,btnsTextsOffY[],btnsTextsOffX[],surePX,surePY,btnsTextsOffYSure[],btnsTextsOffXSure[];
	private static final String [] gameTypeStrings = {"Select gameplay type:","Endless","Clear the level"};



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


		batch.draw(TH.texts[TH.ItxtBack], 0, 0, TH.textsW[TH.ItxtBack], TH.textsH[TH.ItxtBack]);
		batch.draw(TH.texts[TH.ItxtPlanet], GV.planetX, GV.planetY, TH.textsW[TH.ItxtPlanet], TH.textsH[TH.ItxtPlanet]);
		batch.draw(TH.texts[TH.ItxtSatt], GV.sattX, GV.sattY, TH.textsW[TH.ItxtSatt], TH.textsH[TH.ItxtSatt]);
		
		if (paused) {
			batch.draw(TH.texts[TH.ItxtPixel], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);
			batch.draw(TH.texts[TH.ItxtPaused], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);

		} else {

		if (areYouSure) {

			batch.draw (TH.texts[TH.ItxtSure], surePX, surePY, TH.textsW[TH.ItxtSure], TH.textsH[TH.ItxtSure]);
			for (p=0;p<2;p++) {
				if (selectBlobI==p && selectBlobOn) batch.setColor(0.5f,0.5f,0.5f,1.0f); else batch.setColor (Color.WHITE); 
				batch.draw (TH.texts[TH.ItxtBtnBlu], btnsX, surePY+TH.textsH[TH.ItxtSure]+((p+1)*btnsSpac), TH.textsW[TH.ItxtBtnBlu], TH.textsH[TH.ItxtBtnBlu]);
				batch.draw (TH.texts[btnsTextsSure[p]], btnsTextsOffXSure[p], btnsTextsOffYSure[p], TH.textsW[btnsTextsSure[p]], TH.textsH[btnsTextsSure[p]]);
			}
			batch.setColor (Color.WHITE);
			
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
			for (p=0;p<numBtns;p++) {
				if (!(p==1 && noSavedGame)) {
					if (selectBlobI==p && selectBlobOn) batch.setColor(0.5f,0.5f,0.5f,1.0f); else batch.setColor (Color.WHITE); 
					batch.draw(TH.texts[TH.ItxtBtnBlu], btnsX, btnsY + (btnsSpac*p), TH.textsW[TH.ItxtBtnBlu], TH.textsH[TH.ItxtBtnBlu]);
					batch.draw(TH.texts[btnsTexts[p]], btnsTextsOffX[p], btnsTextsOffY[p], TH.textsW[btnsTexts[p]], TH.textsH[btnsTexts[p]]);
				}
			}
			
			batch.setColor (Color.WHITE);
		}

		}//not paused
		
		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked==true) {
			selectBlobOn=false;
			clicked=false;

			if (areYouSure) {
				if (selection==0) {
					selectGameType=true;
					areYouSure=false;
				}

				if (selection==1) {
					areYouSure=false;
				}

			} else if (selectGameType) {
				if (selection ==1) {
					GV.s = new SavedGame();
					GV.s.gameType=0;
					GV.doNewGame();
					selectGameType=false;
					//for (p=0;p<TH.numMusic;p++) TH.loopingMusic[p].stop();
					game.setScreen(game.gameScreen);
				}
				if (selection == 2) {
					GV.s = new SavedGame();
					GV.s.gameType=1;
					GV.doNewGame();
					selectGameType=false;
					//for (p=0;p<TH.numMusic;p++) TH.loopingMusic[p].stop();
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
					//for (p=0;p<TH.numMusic;p++) TH.loopingMusic[p].stop();
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
			//game.setScreen (game.startScreen);
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
		sizes();
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
			
			for (p=0;p<2;p++) {
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>surePY+TH.textsH[TH.ItxtSure]+((p+1)*btnsSpac) && y<surePY+TH.textsH[TH.ItxtSure]+((p+1)*btnsSpac) + TH.textsH[TH.ItxtBtnBlu]) { 
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

			for (p=0;p<numBtns;p++) {
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>btnsY + (btnsSpac*p) && y<btnsY + (btnsSpac*p) + TH.textsH[TH.ItxtBtnBlu]) { 
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

			for (p=0;p<2;p++) {
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>surePY+TH.textsH[TH.ItxtSure]+((p+1)*btnsSpac) && y<surePY+TH.textsH[TH.ItxtSure]+((p+1)*btnsSpac) + TH.textsH[TH.ItxtBtnBlu] && selectBlobI==p) { 
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
			
			for (p=0;p<numBtns;p++) {
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>btnsY + (btnsSpac*p) && y<btnsY + (btnsSpac*p) + TH.textsH[TH.ItxtBtnBlu] && selectBlobI==p) { 
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

		bfD = TH.bf.getData();

		float a,z;
		int breaker;
		
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
		




		numGameType = gameTypeStrings.length;
		gameTypeStringsX=new float [numGameType];
		gameTypeStringsY=new float [numGameType];
		gameTypeStringsW=new float [numGameType];
		gameTypeStringsH=new float [numGameType];


		float logoH=0;/// change when you have a logo
		float gameTypeSpacing = (GV.h-logoH) / (numGameType+1);


		bfD.setScale(gameTypeScale);

		for (p=0;p<numGameType;p++) {
			gl = new GlyphLayout (TH.bf,gameTypeStrings[p]);
			gameTypeStringsX[p]=(GV.w-gl.width)/2.0f;
			gameTypeStringsY[p]=gameTypeSpacing*(p+1);
			gameTypeStringsW[p]=gl.width;
			gameTypeStringsH[p]=bfD.lineHeight;
		}

		pausedX=(GV.w-TH.textsW[TH.ItxtPaused]) / 2.0f;
		pausedY=(GV.h-TH.textsH[TH.ItxtPaused]) / 2.0f;

				
		numBtns=4;
		btnsTexts = new int [] {TH.ItxtNew,TH.ItxtCont,TH.ItxtOpt,TH.ItxtCred};
		
		if (GV.isAndroid) { 
			numBtns=8;
			btnsTexts = new int [] {TH.ItxtNew,TH.ItxtCont,TH.ItxtOpt,TH.ItxtCred, TH.ItxtAch, TH.ItxtLdr,TH.ItxtOur, TH.ItxtShare};

		}

		
		btnsTextsOffY = new float [numBtns];
		btnsTextsOffX = new float [numBtns];
		btnsX = (GV.w - TH.textsW[TH.ItxtBtnBlu]) / 2.0f;
		btnsSpac = (GV.h-logoH) / (numBtns);
		btnsY = logoH;
		
		for (p=0;p<numBtns;p++) {
		btnsTextsOffY[p] = btnsY + (btnsSpac*p) + ((TH.textsH[TH.ItxtBtnBlu]-TH.textsH[btnsTexts[p]]) / 2.0f);
		btnsTextsOffX[p] = (GV.w-TH.textsW[btnsTexts[p]]) / 2.0f;
		}
		
		surePY = btnsY;
		surePX = (GV.w-TH.textsW[TH.ItxtSure]) / 2.0f;
		btnsTextsOffYSure= new float [2];
		btnsTextsOffXSure = new float [2];
		btnsTextsSure = new int [] {TH.ItxtYes, TH.ItxtNo};
		
		for (p=0;p<2;p++) {
			btnsTextsOffYSure[p]=surePY+TH.textsH[TH.ItxtSure]+((p+1)*btnsSpac) +((TH.textsH[TH.ItxtBtnBlu]-TH.textsH[btnsTextsSure[p]])/2.0f);
			btnsTextsOffXSure[p]=(GV.w - TH.textsW[btnsTextsSure[p]]) / 2.0f;
		}
		
		
		
	}
	
	private void resumeGame () {
		paused=false;
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}

}