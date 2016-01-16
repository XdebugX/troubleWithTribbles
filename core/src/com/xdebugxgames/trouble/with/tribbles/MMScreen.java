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
	private boolean selectBlobOn,clicked,oneFrame,showCPC,areYouSure,noSavedGame,selectGameType,paused,stretchIn,pullIn;
	private int selectBlobI,selection,p,numMenuStrings,numGameType,numBtns,btnsTexts[],btnsTextsSure[],btnsTextsGP[];
	private static float logoH,pausedX,pausedY,btnsX,btnsSpac,btnsY,btnsTextsOffY[],btnsTextsOffX[],surePX,surePY,btnsTextsOffYSure[],btnsTextsOffXSure[],btnsTextsOffYGP[],btnsTextsOffXGP[],gameTypeX,gameTypeY,logoX,logoY,btnXOff,btnYOff,btnScaleOff,btnAlpha;
	


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
		batch.draw(TH.texts[TH.ItxtLogo], logoX, logoY, TH.textsW[TH.ItxtLogo], TH.textsH[TH.ItxtLogo]);
		
		if (paused) {
			batch.draw(TH.texts[TH.ItxtPixel], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);
			batch.draw(TH.texts[TH.ItxtPaused], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);

		} else {

		if (areYouSure) {

			batch.draw (TH.texts[TH.ItxtSure], surePX, surePY, TH.textsW[TH.ItxtSure], TH.textsH[TH.ItxtSure]);
			for (p=0;p<2;p++) {
				if (selectBlobI==p && selectBlobOn) batch.setColor(0.5f,0.5f,0.5f,1.0f); else batch.setColor (Color.WHITE); 
				batch.draw (TH.texts[TH.ItxtBtnBlu], btnsX, surePY+TH.textsH[TH.ItxtSure]+((p+1)*btnsSpac), TH.textsW[TH.ItxtBtnBlu], TH.textsH[TH.ItxtBtnBlu]);
				batch.draw (TH.texts[btnsTextsSure[p]], btnsTextsOffXSure[p], btnsTextsOffYSure[p],TH.textsW[btnsTextsSure[p]], TH.textsH[btnsTextsSure[p]]);
			}
			batch.setColor (Color.WHITE);
			
		} else if (selectGameType) {
			
			batch.draw (TH.texts[TH.ItxtGameType],gameTypeX,gameTypeY,TH.textsW[TH.ItxtGameType], TH.textsH[TH.ItxtGameType]);
			for (p=0;p<2;p++) {
				if (selectBlobI==p && selectBlobOn) batch.setColor(0.5f,0.5f,0.5f,1.0f); else batch.setColor (Color.WHITE); 
				batch.draw (TH.texts[TH.ItxtBtnBlu], btnsX, gameTypeY+TH.textsH[TH.ItxtGameType]+((p+1)*btnsSpac), TH.textsW[TH.ItxtBtnBlu], TH.textsH[TH.ItxtBtnBlu]);
				batch.draw (TH.texts[btnsTextsGP[p]], btnsTextsOffXGP[p], btnsTextsOffYGP[p], TH.textsW[btnsTextsGP[p]], TH.textsH[btnsTextsGP[p]]);
			}
			batch.setColor (Color.WHITE);
			
		} else {
			for (p=0;p<numBtns;p++) {
				if (!(p==1 && noSavedGame)) {
					batch.setColor(1.0f,1.0f,1.0f,btnAlpha);
					if (!pullIn && !stretchIn) if (selectBlobI==p && selectBlobOn) batch.setColor(0.5f,0.5f,0.5f,1.0f); else batch.setColor (Color.WHITE);
					batch.draw(TH.texts[TH.ItxtBtnBlu], btnsX+btnXOff, btnsY + (btnsSpac*p)+btnYOff, TH.textsW[TH.ItxtBtnBlu]/2.0f, TH.textsH[TH.ItxtBtnBlu]/2.0f, TH.textsW[TH.ItxtBtnBlu], TH.textsH[TH.ItxtBtnBlu], btnScaleOff, 1.0f, 0.0f);
					batch.draw(TH.texts[btnsTexts[p]], btnsTextsOffX[p]+btnXOff, btnsTextsOffY[p]+btnYOff, TH.textsW[btnsTexts[p]]/2.0f, TH.textsH[btnsTexts[p]]/2.0f, TH.textsW[btnsTexts[p]], TH.textsH[btnsTexts[p]], btnScaleOff, 1.0f, 0.0f);
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
				if (selection == 0) {
					GV.s = new SavedGame();
					GV.s.gameType=0;
					GV.doNewGame();
					selectGameType=false;
					//for (p=0;p<TH.numMusic;p++) TH.loopingMusic[p].stop();
					game.setScreen(game.gameScreen);
				}
				if (selection == 1) {
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
					if (GV.s!=null) {
						if (!GV.s.gameOver) {
							areYouSure=true; 
					}
					else {
						selectGameType=true;
					}
					} else {
						selectGameType=true;
					}
				}

				if (!noSavedGame) if (selection==1) {
					GV.s=Save.loadGame();
					//for (p=0;p<TH.numMusic;p++) TH.loopingMusic[p].stop();
					if (GV.s.gameOver) game.setScreen (game.gameOverScreen); else game.setScreen(game.gameScreen);
				}
				
				if (selection==2) {
					game.setScreen (game.optionsScreen);
				}
				
				if (selection==3) {
					game.setScreen (game.creditsScreen);
				}
				
			}

		}


		if (stretchIn) {
			btnAlpha+=.15f*delta;
			btnScaleOff+=1.5f*delta;
			if (btnScaleOff>2.5f) {
				pullIn=true;
				stretchIn=false;
			}
		}
		
		if (pullIn) {
			btnAlpha+=0.4f*delta;
			btnXOff+=(TH.textsW[TH.ItxtBtnBlu])*delta;
			//btnYOff+=(TH.textsH[TH.ItxtBtnBlu])*delta;
			btnScaleOff-=1.0f*delta;
			
			if (btnXOff>0) btnXOff=0.0f;
			if (btnYOff>0) btnYOff=0.0f;
			if (btnScaleOff<1.0f) btnScaleOff=1.0f;
			if (btnAlpha>1.0f) btnAlpha=1.0f;
			
			if (btnXOff==0 && btnYOff==0 && btnScaleOff==1.0f && btnAlpha==1.0f) pullIn=false;
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
		
		if (!GV.warpIn) {
		btnScaleOff=0.25f;
		btnXOff=-(TH.textsW[TH.ItxtBtnBlu]);
		btnYOff=0.0f;//-(TH.textsH[TH.ItxtBtnBlu]);
		btnAlpha=0.1f;
		stretchIn=true;
		pullIn=false;
		} 
		
		GV.warpIn=true;
		
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

			for (p=0;p<2;p++) {
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>gameTypeY+TH.textsH[TH.ItxtGameType]+((p+1)*btnsSpac) && y<gameTypeY+TH.textsH[TH.ItxtGameType]+((p+1)*btnsSpac) + TH.textsH[TH.ItxtBtnBlu]) { 
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
			
			for (p=0;p<2;p++) {
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>gameTypeY+TH.textsH[TH.ItxtGameType]+((p+1)*btnsSpac) && y<gameTypeY+TH.textsH[TH.ItxtGameType]+((p+1)*btnsSpac) + TH.textsH[TH.ItxtBtnBlu] && selectBlobI==p) { 
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

	

		pausedX=(GV.w-TH.textsW[TH.ItxtPaused]) / 2.0f;
		pausedY=(GV.h-TH.textsH[TH.ItxtPaused]) / 2.0f;

				
		numBtns=4;
		btnsTexts = new int [] {TH.ItxtNew,TH.ItxtCont,TH.ItxtOpt,TH.ItxtCred};
		
		if (GV.isAndroid) { 
			numBtns=7;
			btnsTexts = new int [] {TH.ItxtNew,TH.ItxtCont,TH.ItxtOpt,TH.ItxtCred, TH.ItxtAch, TH.ItxtLdr,TH.ItxtOur};

		}

		logoX=(GV.w-TH.textsW[TH.ItxtLogo]) / 2.0f;
		logoY=0.0f;
		logoH=logoY + TH.textsH[TH.ItxtLogo];
		
		
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

		gameTypeX=(GV.w-TH.textsW[TH.ItxtGameType]) / 2.0f;
		gameTypeY=btnsY + GV.h*.05f;
		
		btnsTextsOffYGP= new float [2];
		btnsTextsOffXGP = new float [2];
		btnsTextsGP = new int [] {TH.ItxtClassic, TH.ItxtCTL};

		for (p=0;p<2;p++) {
			btnsTextsOffYGP[p]=gameTypeY+TH.textsH[TH.ItxtGameType]+((p+1)*btnsSpac) +((TH.textsH[TH.ItxtBtnBlu]-TH.textsH[btnsTextsGP[p]])/2.0f);
			btnsTextsOffXGP[p]=(GV.w - TH.textsW[btnsTextsGP[p]]) / 2.0f;
		}

		
		
	}
	
	private void resumeGame () {
		paused=false;
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}

}