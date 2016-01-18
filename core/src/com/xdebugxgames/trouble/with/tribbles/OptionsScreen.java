package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class OptionsScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean clicked,paused;

	private static float pausedX,pausedY,btnsX,btnsY[],btnsTX[],btnsTY[],optTX,optTY,btnSpac;
	private static int p,selectBlobI,selection,numButtons,btnsTxt[],whatKind,whatText;
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

		batch.draw(TH.texts[TH.ItxtBack], 0, 0, TH.textsW[TH.ItxtBack], TH.textsH[TH.ItxtBack]);
		batch.draw(TH.texts[TH.ItxtPlanet], GV.planetX, GV.planetY, TH.textsW[TH.ItxtPlanet], TH.textsH[TH.ItxtPlanet]);
		batch.draw(TH.texts[TH.ItxtSatt], GV.sattX, GV.sattY, TH.textsW[TH.ItxtSatt], TH.textsH[TH.ItxtSatt]);
		if (paused) {

			batch.draw(TH.texts[TH.ItxtPixel], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);
			batch.draw(TH.texts[TH.ItxtPaused], pausedX, pausedY, TH.textsW[TH.ItxtPaused], TH.textsH[TH.ItxtPaused]);


		} else {

			batch.draw(TH.texts[TH.ItxtOptT], optTX, optTY, TH.textsW[TH.ItxtOptT], TH.textsH[TH.ItxtOptT]);

			for (p=0;p<numButtons;p++) {
				if (p==0) if (GV.opts.sfxOn) {
					whatKind=TH.ItxtBtnGrn;
					whatText = TH.ItxtSoundOn;
				} else {
					whatKind=TH.ItxtBtnRed;
					whatText = TH.ItxtSoundOff;
				}
				if (p==1) if (GV.opts.musicOn) {
					whatKind=TH.ItxtBtnGrn;  
					whatText = TH.ItxtMusOn;
				} else {
					whatKind=TH.ItxtBtnRed;
					whatText = TH.ItxtMusOff;
				}

				if (GV.isAndroid) {
					if (p==2) if (GV.opts.loginGP) {
						whatKind=TH.ItxtBtnRed;  
						whatText = TH.ItxtLogout;

					} else {
						whatKind=TH.ItxtBtnGrn;
						whatText = TH.ItxtLogin;
					}

					if (p==3) {
						whatKind=TH.ItxtBtnBlu;
						whatText = TH.ItxtDone;
					}
				} else {
					if (p==2) {
						whatKind=TH.ItxtBtnBlu;
						whatText = TH.ItxtDone;						
					}
				}


				if (selectBlobI==p && selectBlobOn) batch.setColor(0.5f,0.5f,0.5f,1.0f); else batch.setColor (Color.WHITE); 
				batch.draw(TH.texts[whatKind], btnsX, btnsY[p], TH.textsW[whatKind], TH.textsH[whatKind]);
				batch.draw(TH.texts[whatText], btnsTX[p], btnsTY[p], TH.textsW[whatText], TH.textsH[whatText]);

			}
			batch.setColor (Color.WHITE);

			/*
			bfD.setScale(optionsTextScale);

			for (p=0;p<numTexts;p++) {
				batch.setColor (22.0f/255.0f,45.0f/255.0f,73.0f/255.0f,0.7f);
				batch.draw(TH.texts[TH.ItxtPixel], textX[p], textY[p], textW[p],textH[p]);
				batch.setColor (Color.WHITE);

				if (selectBlobOn && selectBlobI==p) TH.bf.setColor(Color.DARK_GRAY); else TH.bf.setColor(Color.GOLD);
				TH.bf.draw(batch, optionsText[p], textX[p], textY[p]);
			}
			 */
		}
		if (selectBlobOn && selectBlobI==20) batch.setColor (0.5f,0.5f,0.5f,1.0f);
		batch.draw(TH.texts[TH.ItxtBackArrow],0.0f,GV.h-TH.textsH[TH.ItxtBackArrow], TH.textsW[TH.ItxtBackArrow], TH.textsH[TH.ItxtBackArrow]);
		batch.setColor (Color.WHITE);
		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked) {
			clicked=false;
			
			if (selection==20) doBack=true;
			
			if (selection==0) {
				GV.opts.sfxOn=!GV.opts.sfxOn;
				saveOptions.save(GV.opts);
			}

			if (selection==1) {
				GV.opts.musicOn=!GV.opts.musicOn;
				saveOptions.save(GV.opts);
			}


			if (GV.isAndroid) { 
				if (selection==2) {
					GV.opts.loginGP=!GV.opts.loginGP;
					saveOptions.save(GV.opts);
					if (GV.opts.loginGP) game.myRequestHandler.sendMsg("loginGP"); else game.myRequestHandler.sendMsg("logoutGP");
				}
				if (selection==3) doBack=true;
			} else {
				if (selection==2) doBack=true;
			}

			if (selection==1) {
				if (GV.opts.musicOn) {
					if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
				}
				else {
					for (p=0;p<TH.numMusic;p++) if (TH.loopingMusic!=null) if (TH.loopingMusic[p]!=null) TH.loopingMusic[p].stop();				
				}
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

		sizes();		
		clicked=false;
		doBack=false;

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
		selectBlobI=10;

		if (!paused) {
			for (p=0;p<numButtons;p++) {
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>btnsY[p] && y<btnsY[p]+TH.textsH[TH.ItxtBtnBlu]) {
					selectBlobI=p;
					selectBlobOn=true;
				}

			}
		}
		
		if (x<TH.textsW[TH.ItxtBackArrow] && y>GV.h-TH.textsH[TH.ItxtBackArrow]) {
			selectBlobI=20;
			selectBlobOn=true;
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
				selectBlobOn=false;
				for (p=0;p<numButtons;p++) {
					if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>btnsY[p] && y<btnsY[p]+TH.textsH[TH.ItxtBtnBlu] && selectBlobI==p) {
						selectBlobOn=true;
					}
				}

		}
		
		if (x<TH.textsW[TH.ItxtBackArrow] && y>GV.h-TH.textsH[TH.ItxtBackArrow] && selectBlobI==20) {
			selectBlobOn=true;
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




		pausedX=(GV.w-TH.textsW[TH.ItxtPaused]) / 2.0f;
		pausedY=(GV.h-TH.textsH[TH.ItxtPaused]) / 2.0f;

		numButtons=3;
		btnsTxt = new int [] {TH.ItxtSoundOn, TH.ItxtMusOn, TH.ItxtDone};

		if (GV.isAndroid) {
			numButtons=4;
			btnsTxt = new int [] {TH.ItxtSoundOn, TH.ItxtMusOn, TH.ItxtLogin, TH.ItxtDone};
		}

		optTX = (GV.w-TH.textsW[TH.ItxtOptT]) / 2.0f;
		optTY = GV.h*0.05f;

		btnsX = (GV.w-TH.textsW[TH.ItxtBtnBlu]) / 2.0f;
		btnsY = new float [numButtons];
		btnsTX = new float [numButtons];
		btnsTY = new float [numButtons];

		btnSpac = (GV.h - optTY - TH.textsH[TH.ItxtOptT]) / (numButtons+1);

		for (p=0;p<numButtons;p++) {
			btnsY[p] = optTY + TH.textsH[TH.ItxtOpt] + ((p+1) * btnSpac);
			btnsTX[p] = (GV.w - TH.textsW[btnsTxt[p]]) / 2.0f;
			btnsTY[p] = btnsY[p] + ((TH.textsH[TH.ItxtBtnBlu]-TH.textsH[btnsTxt[p]]) / 2.0f); 
		}


	}

	private void resumeGame () {
		paused=false;
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}



}