package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameOverScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private boolean clicked,paused;

	private static float pausedX,pausedY,btnsX,btnsY,btnsTX,btnsTY,titleTxtX,titleTxtY,spacing,finalScoreX,finalScoreY,totalTribX,totalTribY,digX,digitW,levelDigX,digitY,levelDigY;
	private static int p,selectBlobI,selection,numButtons,btnsTxt,largestLevelDig,largestDig,digits[],levelDigits[];
	private static boolean doBack,selectBlobOn;



	// constructor to keep a reference to the main Game class
	public GameOverScreen (Tribbles game) {
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

				if (selectBlobI==p && selectBlobOn) batch.setColor(0.5f,0.5f,0.5f,1.0f); else batch.setColor (Color.WHITE); 
				batch.draw(TH.texts[TH.ItxtBtnBlu], btnsX, btnsY, TH.textsW[TH.ItxtBtnBlu], TH.textsH[TH.ItxtBtnBlu]);
				batch.draw(TH.texts[TH.ItxtDone], btnsTX, btnsTY, TH.textsW[TH.ItxtDone], TH.textsH[TH.ItxtDone]);

			}
		batch.setColor (Color.WHITE);
		
		batch.draw(TH.texts[TH.ItxtGameOver], titleTxtX, titleTxtY, TH.textsW[TH.ItxtGameOver], TH.textsH[TH.ItxtGameOver]);
		batch.draw(TH.texts[TH.ItxtTotalTrib], totalTribX, totalTribY, TH.textsW[TH.ItxtTotalTrib], TH.textsH[TH.ItxtTotalTrib]);
		batch.draw(TH.texts[TH.ItxtFinalScore], finalScoreX, finalScoreY, TH.textsW[TH.ItxtFinalScore], TH.textsH[TH.ItxtFinalScore]);
		
		batch.setColor (Color.BLUE);
		for (p=largestDig;p<8;p++) {
			batch.draw(TH.texts[TH.ItxtN+digits[p]], digX+(digitW*(p-largestDig))+((digitW-(TH.textsW[TH.ItxtN+digits[p]])*2.0f)/2.0f), digitY, TH.textsW[TH.ItxtN+digits[p]]*2.0f, TH.textsH[TH.ItxtN+digits[p]]*2.0f);
			}
		
		for (p=largestLevelDig;p<5;p++) {
			batch.draw(TH.texts[TH.ItxtN+levelDigits[p]], levelDigX+(digitW*(p-largestLevelDig))+((digitW-(TH.textsW[TH.ItxtN+levelDigits[p]]*2.0f))/2.0f), levelDigY, TH.textsW[TH.ItxtN+levelDigits[p]]*2.0f, TH.textsH[TH.ItxtN+levelDigits[p]]*2.0f);
		}

		batch.setColor(Color.WHITE);
		
		if (selectBlobOn && selectBlobI==20) batch.setColor (0.5f,0.5f,0.5f,1.0f);
		batch.draw(TH.texts[TH.ItxtBackArrow],0.0f,GV.h-TH.textsH[TH.ItxtBackArrow], TH.textsW[TH.ItxtBackArrow], TH.textsH[TH.ItxtBackArrow]);
		batch.setColor (Color.WHITE);
		
		batch.end();

		////////////////////////////////////////////// Update Game //////////////////////////////////////////////////////
		if (clicked) {
			
			if (selection==20) doBack=true;

			
			clicked=false;
			selectBlobI=10;
			selectBlobOn=false;
			if (selection==0) game.setScreen (game.mmScreen);
		
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

		clicked=false;
		doBack=false;
		
		digits=new int [8];
		levelDigits= new int [5];
				
		sizes();		


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
				if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>btnsY && y<btnsY+TH.textsH[TH.ItxtBtnBlu]) {
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
					if (x>btnsX && x<btnsX+TH.textsW[TH.ItxtBtnBlu] && y>btnsY && y<btnsY+TH.textsH[TH.ItxtBtnBlu] && selectBlobI==p) {
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
		pausedX=(GV.w-TH.textsW[TH.ItxtPaused]) / 2.0f;
		pausedY=(GV.h-TH.textsH[TH.ItxtPaused]) / 2.0f;

		numButtons=1;
		btnsTxt = TH.ItxtDone;

		titleTxtX = (GV.w-TH.textsW[TH.ItxtGameOver] ) / 2.0f;
		titleTxtY = GV.h*0.05f;
		
		totalTribX = (GV.w-TH.textsW[TH.ItxtTotalTrib]) / 2.0f;
		spacing = (GV.h-titleTxtY-TH.textsH[TH.ItxtBtnBlu] ) / 3.5f;
		totalTribY = titleTxtY+spacing;
		
		finalScoreX = (GV.w-TH.textsW[TH.ItxtFinalScore]) / 2.0f;
		finalScoreY = totalTribY+spacing;

		btnsX = (GV.w-TH.textsW[TH.ItxtBtnBlu]) / 2.0f;
		btnsY = finalScoreY+spacing; 
		btnsTX = (GV.w-TH.textsW[btnsTxt]) / 2.0f;
		btnsTY = btnsY + ((TH.textsH[TH.ItxtBtnBlu]-TH.textsH[TH.ItxtDone]) / 2.0f);

		calcDigits();
		calcLevelDigits();

		digitW = 84.0f*GV.aspRatW;
		
		digitY = finalScoreY+TH.textsH[TH.ItxtFinalScore]+(GV.h*0.05f);
		levelDigY = totalTribY + TH.textsH[TH.ItxtTotalTrib] + (GV.h*0.05f);
		
		digX = (GV.w - (digitW*(8-largestDig))) / 2.0f;
		levelDigX = (GV.w - (digitW*(5-largestLevelDig))) / 2.0f;


	}

	private void resumeGame () {
		paused=false;
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}

	
	private void calcDigits () {
		int p, div;
		long nScore;
		nScore=GV.s.score;
		div = 10000000;
		
		largestDig=9;
		
		for (p=0;p<8;p++) {
			if ((int) (nScore / div) > 0 || p>largestDig) {
				digits[p]=(int) (nScore / div);
				if (largestDig>p) largestDig=p;
				nScore = nScore - (digits[p] * div);
				
			}
			div = div / 10;
		}
		if (largestDig>7) largestDig=7;
	}
	
	private void calcLevelDigits () {
		int p, div;
		long nScore;
		nScore=GV.s.totalTribTrans;
		div = 10000;
		
		largestLevelDig=6;
		
		for (p=0;p<5;p++) {
			if ((int) (nScore / div) > 0 || p>largestLevelDig) {
				levelDigits[p]=(int) (nScore / div);
				if (largestLevelDig>p) largestLevelDig=p;
				nScore = nScore - (levelDigits[p] * div);
				
			}
			div = div / 10;
		}
		if (largestLevelDig==0) levelDigits[4]=GV.s.totalTribTrans;
		if (largestLevelDig>4) largestLevelDig=4;
	}


}