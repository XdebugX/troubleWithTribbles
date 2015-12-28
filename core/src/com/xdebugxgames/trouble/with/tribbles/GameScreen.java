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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;


public class GameScreen implements Screen, InputProcessor {

	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private static int p,t;
	private ShapeRenderer sr;
	private GlyphLayout gl;
	private BitmapFontData bfD;
	private Background b;
	private long pauseTime;
	private static int whichTribX,whichTribY,highestTrib,lastWootPlayed,woot,randomCardD;
	private static float bX,bY,srX,srY,tribW,tribH,bW,bH,boardSpeedInc,startSpeed,poppedTribSpeed,poppedTribRotationSpeed,scoreBarH,scoreTextScale,levelW;

	private static boolean doBack,tribbleTouched,isATrib,noTribs;


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
		if (b.asteroidOnScreen) batch.draw(TH.texts[TH.ItxtAsteroids+b.asteroidType], b.asteroidX, b.asteroidY, TH.textsW[TH.ItxtAsteroids+b.asteroidType],TH.textsH[TH.ItxtAsteroids+b.asteroidType]);
		if (b.cometOnScreen) {			
			if (!b.cometDX) batch.draw(TH.texts[TH.ItxtComets+b.cometType], b.cometX, b.cometY, TH.textsW[TH.ItxtComets+b.cometType], TH.textsH[TH.ItxtComets+b.cometType]);								
			else batch.draw(TH.texts[TH.ItxtComets+b.cometType], b.cometX, b.cometY, TH.textsW[TH.ItxtComets+b.cometType]/2.0f, TH.textsH[TH.ItxtComets+b.cometType]/2.0f, TH.textsW[TH.ItxtComets+b.cometType], TH.textsH[TH.ItxtComets+b.cometType],1.0f,1.0f,270.0f);
		}
		if (b.isSatt) batch.draw(TH.texts[TH.ItxtSatt], b.sattX, b.sattY, TH.textsW[TH.ItxtSatt], TH.textsH[TH.ItxtSatt]);


		for (p=0;p<GV.s.spawnRowI;p++) {
			if (GV.s.spawnRowState[p]==0) {
				batch.setColor (1.0f,1.0f,1.0f,GV.s.spawnRowFade[p]);
				batch.draw(TH.Anims[TH.IanimSpawn+GV.s.spawnRow[p]].getKeyFrame(GV.s.spawnRowStateTimer[p] += delta, false ), srX+(p*tribW), srY, tribW, tribH);
			} 
			batch.setColor (Color.WHITE);

			if (GV.s.spawnRowState[p]==1) {
				batch.draw(TH.texts[TH.ItxtBallsIdle+GV.s.spawnRow[p]], srX+(p*tribW), srY, tribW, tribH);											
			}
		}

		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) {
			if (GV.s.board[p][t]<GV.s.numTribTypes) 
				if (GV.s.boardState[p][t]==0) {
					batch.setColor(1.0f,1.0f,1.0f,GV.s.spawnFade[p][t]);
					batch.draw(TH.Anims[TH.IanimSpawn+GV.s.board[p][t]].getKeyFrame(GV.s.boardStateTimer[p][t] += delta, false), bX+GV.s.boardX[p][t], bY+GV.s.boardY[p][t], tribW, tribH);							
					batch.setColor(Color.WHITE);
				} 

			if (GV.s.board[p][t]<GV.s.numTribTypes) 
				if (GV.s.boardState[p][t]==1) {
					batch.draw(TH.texts[TH.ItxtBallsIdle+GV.s.board[p][t]], bX+GV.s.boardX[p][t], bY+GV.s.boardY[p][t], tribW, tribH);											
				}

			if (GV.s.board[p][t]<GV.s.numTribTypes) 
				if (GV.s.boardState[p][t]==2) {
					batch.draw(TH.Anims[TH.IanimBlink+GV.s.board[p][t]].getKeyFrame(GV.s.boardStateTimer[p][t] += delta, false), bX+GV.s.boardX[p][t], bY+GV.s.boardY[p][t], tribW, tribH);							
				}

			if (GV.s.board[p][t]<GV.s.numTribTypes) 
				if (GV.s.boardState[p][t]==3) {
					batch.draw(TH.Anims[TH.IanimWiggle+GV.s.board[p][t]].getKeyFrame(GV.s.boardStateTimer[p][t] += delta, false), bX+GV.s.boardX[p][t], bY+GV.s.boardY[p][t], tribW, tribH);							
				}

			if (GV.s.board[p][t]<GV.s.numTribTypes) 
				if (GV.s.boardState[p][t]==4) {
					batch.setColor(1.0f,1.0f,1.0f,GV.s.spawnFade[p][t]);
					batch.draw(TH.Anims[TH.IanimShutEyes+GV.s.board[p][t]].getKeyFrame(GV.s.boardStateTimer[p][t] += delta, false), bX+GV.s.boardX[p][t], bY+GV.s.boardY[p][t], tribW, tribH);
					batch.setColor(Color.WHITE);
				}


		}

		for (p=0;p<GV.s.numPoppedTribs;p++) {
			batch.draw(TH.texts[TH.ItxtBallsIdle+GV.s.poppedTribType[p]], GV.s.poppedTribX[p], GV.s.poppedTribY[p], tribW/2.0f, tribH/2.0f, tribW, tribH, 1.0f, 1.0f, GV.s.poppedTribRotation[p]);															
		}
		TH.bf.setColor(Color.GOLD);
		TH.bf.draw(batch, "Score "+GV.s.score, 0, 0);
	
		
		TH.bf.draw(batch, "Level "+GV.s.level, GV.w-levelW, 0);
		
		
		batch.end();




		//update game

		if (doBack) {
			doBack=false;
			game.setScreen(game.mmScreen);
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


		///////////////// do game stuff



		///////////////////Spawn Row
		if (System.currentTimeMillis()>GV.s.spawnRowTimer+GV.s.spawnInterval) {
			if (GV.s.spawnRowI==GV.s.boardW) {
				GV.s.spawnRowI=0;

				for (p=0;p<GV.s.boardW;p++) {
					moveUp (p,GV.s.boardH-1);
					GV.s.board[p][GV.s.boardH-1]=GV.s.spawnRow[p];
					GV.s.boardStateTimer[p][GV.s.boardH-1]=0.0f;
					GV.s.boardState[p][GV.s.boardH-1]=1;
					GV.s.boardX[p][GV.s.boardH-1]=(p*tribW);
					GV.s.boardY[p][GV.s.boardH-1]=((GV.s.boardH)*tribH);
					GV.s.boardSpeed[p][GV.s.boardH-1]=startSpeed;
				}


			} else {
				GV.s.spawnRow[GV.s.spawnRowI]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.spawnRowState[GV.s.spawnRowI]=0;
				GV.s.spawnRowStateTimer[GV.s.spawnRowI]=0.0f;
				GV.s.spawnRowFade[GV.s.spawnRowI] = 0.33f;
				GV.s.spawnRowTimer=System.currentTimeMillis();
				GV.s.spawnRowI++;
			}
		}

		for (p=0;p<GV.s.spawnRowI;p++) {
			if (GV.s.spawnRowState[p]==0) {
				GV.s.spawnRowFade[p] = 0.33f+(((GV.s.spawnRowStateTimer[p]*1000.0f)/GV.s.spawnInterval)/1.5f);
				if (GV.s.spawnRowStateTimer[p]*1000>=GV.s.spawnInterval) GV.s.spawnRowState[p]=1;
			}
		}


		/////////////////board 
		//do random blinks
		/*for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==1) if (MathUtils.random(60*10)==0) {
			GV.s.boardState[p][t]=2;
			GV.s.boardStateTimer[p][t]=0.0f;
		}
		*/
		/// set state back to zero when blick animation is finished
		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==2) if (TH.Anims[TH.IanimBlink+GV.s.board[p][t]].isAnimationFinished(GV.s.boardStateTimer[p][t])) {
			GV.s.boardState[p][t]=1;
			GV.s.boardStateTimer[p][t]=0.0f;
		}

		/// move the tribbles x or y location towards the spot they should be if they are not in the correct spot
		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) {

			if (GV.s.boardX[p][t]!=p*tribW || GV.s.boardY[p][t]!=t*tribH) {

				GV.s.boardSpeed[p][t]+=boardSpeedInc;
				if (GV.s.boardSpeed[p][t]>GV.h/3.0f) GV.s.boardSpeed[p][t]-=boardSpeedInc;

				if (GV.s.boardX[p][t]<p*tribW) GV.s.boardX[p][t]+=GV.s.boardSpeed[p][t]*delta;
				if (GV.s.boardX[p][t]>p*tribW) GV.s.boardX[p][t]-=GV.s.boardSpeed[p][t]*delta;
				if (GV.s.boardY[p][t]<t*tribH) GV.s.boardY[p][t]+=GV.s.boardSpeed[p][t]*delta;
				if (GV.s.boardY[p][t]>t*tribH) GV.s.boardY[p][t]-=GV.s.boardSpeed[p][t]*delta;

				if (GV.s.boardY[p][t]>t*tribH-(10.0f*GV.aspRatL) && GV.s.boardY[p][t]<t*tribH+(10.0f*GV.aspRatL)) GV.s.boardY[p][t] = t*tribH;
				if (GV.s.boardX[p][t]>p*tribW-(10.0f*GV.aspRatL) && GV.s.boardX[p][t]<p*tribW+(10.0f*GV.aspRatL)) GV.s.boardX[p][t] = p*tribW;



			} else GV.s.boardSpeed[p][t]=startSpeed;

		}

		//check for gaps and move tribbles down if there is any below the tribbles
		for (p=0;p<GV.s.boardW;p++) {
			isATrib=false;
			for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) {
				highestTrib=t;
				t=GV.s.boardH;
				isATrib=true;
			}

			if (isATrib) if (highestTrib<GV.s.boardH-1) {
				for (t=GV.s.boardH-1;t>highestTrib;t--) if (GV.s.board[p][t]>=GV.s.numTribTypes) moveDown (p,t,highestTrib);
			}
		}

		//check for entire column of tribbles is empty and move colums to the left or right towards the center;
		for (p=0;p<GV.s.boardW;p++) {
			noTribs=true;
			for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) noTribs=false;
			if (noTribs) {
				if (p>GV.s.boardW/2) moveColsLeft (p); else moveColsRight (p);
			}
		}


		//////////////////////user controls

		if (tribbleTouched) {
			GV.s.tribTouched=true;
			tribbleTouched=false;
			GV.s.wTX=whichTribX;
			GV.s.wTY=whichTribY;
		}

		if (GV.s.tribTouched) {
			if (GV.s.board[GV.s.wTX][GV.s.wTY]<GV.s.numTribTypes) { 
				if (findSurroundTribs (GV.s.wTX,GV.s.wTY)>=GV.s.neededForMatch) {
					if (GV.opts.sfxOn) {
						do {
							woot=MathUtils.random(TH.IsfxWoots,TH.IsfxWoots+TH.IsfxNumWoots-1);
						} while (woot==lastWootPlayed);
						TH.sfxs[woot].play();
						lastWootPlayed=woot;
					}
					popTribs();
				}
			}
			GV.s.tribTouched=false;
		}


		////////////////////// Popped Tribbles

		for (p=0;p<GV.s.numPoppedTribs;p++) {
			GV.s.poppedTribXD[p]+=GV.s.poppedTribXDInc[p];
			GV.s.poppedTribYD[p]+=GV.s.poppedTribYDInc[p];
			GV.s.poppedTRotationSpd[p]+=GV.s.poppedTRotationSpdInc[p];

			GV.s.poppedTribX[p]+=GV.s.poppedTribXD[p]*delta;
			GV.s.poppedTribY[p]+=GV.s.poppedTribYD[p]*delta;
			GV.s.poppedTribRotation[p]+=GV.s.poppedTRotationSpd[p]*delta;

			if (GV.s.poppedTribX[p]<0.0-tribW || GV.s.poppedTribX[p]>GV.w || GV.s.poppedTribY[p]<0-tribH || GV.s.poppedTribY[p]>GV.h) {
				for (t=p;t<GV.s.numPoppedTribs-1;t++) {
					GV.s.poppedTribRotation[t]=GV.s.poppedTribRotation[t+1];
					GV.s.poppedTRotationSpd[t]=GV.s.poppedTRotationSpd[t+1];
					GV.s.poppedTRotationSpdInc[t]=GV.s.poppedTRotationSpdInc[t+1];
					GV.s.poppedTribType[t]=GV.s.poppedTribType[t+1];
					GV.s.poppedTribX[t]=GV.s.poppedTribX[t+1];
					GV.s.poppedTribXD[t]=GV.s.poppedTribXD[t+1];
					GV.s.poppedTribY[t]=GV.s.poppedTribY[t+1];
					GV.s.poppedTribYD[t]=GV.s.poppedTribYD[t+1];
					GV.s.poppedTribXDInc[t]=GV.s.poppedTribXDInc[t+1];
					GV.s.poppedTribYDInc[t]=GV.s.poppedTribYDInc[t+1];

				}
				GV.s.numPoppedTribs--;
				p--;
			}

		}
		
		
		
		//set spawn tribbles to idle state when spawn animation finished
		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==0) {
			//set fade amount to animation timer
			GV.s.spawnFade[p][t] = 0.33f + ((GV.s.boardStateTimer[p][t] / TH.Anims[TH.IanimSpawn+GV.s.board[p][t]].getAnimationDuration())/1.5f);
			//reset animation if finished
			if (TH.Anims[TH.IanimSpawn+GV.s.board[p][t]].isAnimationFinished(GV.s.boardStateTimer[p][t])) {
				GV.s.boardState[p][t]=1;
				GV.s.boardStateTimer[p][t]=0.0f;
			}
		}
		//set tribbles to idle when shuteye animation finished
		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==4) {
			//set fade amount to animation timer
			GV.s.spawnFade[p][t] = 1.0f - ((GV.s.boardStateTimer[p][t] / TH.Anims[TH.IanimShutEyes+GV.s.board[p][t]].getAnimationDuration())/1.5f);		
			
			if (TH.Anims[TH.IanimShutEyes+GV.s.board[p][t]].isAnimationFinished(GV.s.boardStateTimer[p][t])) {
			GV.s.boardState[p][t]=1;
			GV.s.boardStateTimer[p][t]=0.0f;
		}
		}


		/////////////////////Tribbles Breed
		//do random Wiggles
		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==1) if (MathUtils.random((60*30)-GV.s.level)==0) {
			GV.s.boardState[p][t]=3;
			GV.s.boardStateTimer[p][t]=0.0f;
		}
		/// set state to shut eye when wiggle animation is finished and spawn a new tribble
		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==3) if (TH.Anims[TH.IanimWiggle+GV.s.board[p][t]].isAnimationFinished(GV.s.boardStateTimer[p][t])) {
			GV.s.boardState[p][t]=4;
			GV.s.boardStateTimer[p][t]=0.0f;
			GV.s.spawnFade[p][t]=1.0f;
			
			randomCardD=MathUtils.random(0,3);
			if (randomCardD==0 && p==0) randomCardD=1;
			if (randomCardD==1 && p==GV.s.boardW-1) randomCardD=0;
			if (randomCardD==2 && t==0) randomCardD=3;
			if (randomCardD==3 && t==GV.s.boardH-1) randomCardD=4;

			if (randomCardD==0) {
				moveUp (p-1,t);
				GV.s.board[p-1][t]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.boardStateTimer[p-1][t]=0.0f;
				GV.s.boardState[p-1][t]=0;
				GV.s.boardX[p-1][t]=(p*tribW);
				GV.s.boardY[p-1][t]=(t*tribH);
				GV.s.boardSpeed[p-1][t]=startSpeed;
				GV.s.spawnFade [p-1] [t] = 0.33f;
			}

			if (randomCardD==1) {
				moveUp (p+1,t);
				GV.s.board[p+1][t]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.boardStateTimer[p+1][t]=0.0f;
				GV.s.boardState[p+1][t]=0;
				GV.s.boardX[p+1][t]=(p*tribW);
				GV.s.boardY[p+1][t]=(t*tribH);
				GV.s.boardSpeed[p+1][t]=startSpeed;
				GV.s.spawnFade [p+1] [t] = 0.33f;

			}

			if (randomCardD==2) {
				moveUp (p,t-1);
				GV.s.board[p][t-1]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.boardStateTimer[p][t-1]=0.0f;
				GV.s.boardState[p][t-1]=0;
				GV.s.boardX[p][t-1]=(p*tribW);
				GV.s.boardY[p][t-1]=(t*tribH);
				GV.s.boardSpeed[p][t-1]=startSpeed;
				GV.s.spawnFade [p] [t-1] = 0.33f;

			}

			if (randomCardD==3) {
				moveUp (p,t);
				GV.s.board[p][t]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.boardStateTimer[p][t]=0.0f;
				GV.s.boardState[p][t]=0;
				GV.s.boardX[p][t]=(p*tribW);
				GV.s.boardY[p][t]=(t*tribH);
				GV.s.boardSpeed[p][t]=startSpeed;
			}
		}

		
		//level
		if (GV.s.score>GV.s.levelThresholds[GV.s.level]) doLevel ();

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

		sizes();

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
		Save.saveGame(game, GV.s);
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
			Save.saveGame(game, GV.s);
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

		int p,t;
		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (x>GV.s.boardX[p] [t] && x<GV.s.boardX[p] [t]+tribW && y>GV.s.boardY[p][t] && y<GV.s.boardY[p][t]+tribH) {
			tribbleTouched=true;
			whichTribX=p;
			whichTribY=t;
		}
		/*
		if (x>bX && x<bX+(tribW*GV.s.boardW) && y>bY && y<bY+(tribH*GV.s.boardH)) {
			tribbleTouched=true;
			whichTribX = (int) ((x-bX)/tribW);
			whichTribY = (int) ((y-bY)/tribH);
		}
		 */
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
		int p=0;
		b=new Background();

		
		scoreBarH = GV.h/40.0f;
		
		bfD = TH.bf.getData();

		float z=0.1f,a=0.0f;
		int breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,"Score: 10000");
			a=gl.height;
			breaker++;
		} while (a<scoreBarH && breaker<1500);

		scoreTextScale = z;
		
		gl = new GlyphLayout (TH.bf, "Level: 1000");
		levelW=gl.width;
		
		float h=GV.h-scoreBarH;

		tribW=GV.w/GV.s.boardW;
		tribH=h/(GV.s.boardH+1);

		if (tribW<tribH) tribH=tribW; else tribW=tribH;

		bW=tribW*GV.s.boardW;
		bH=tribH*GV.s.boardH;

		bX=(GV.w-bW)/2.0f;
		bY=(h-(bH+tribH))/2.0f;

		boardSpeedInc = 5*GV.aspRatL;
		startSpeed = 10*GV.aspRatL;
		poppedTribSpeed = 5.0f*GV.aspRatL;
		poppedTribRotationSpeed = 3.6f*4.0f;

		srX=bX;
		srY=bY+(tribH*(GV.s.boardH));

		updateAnimSpeeds();


	}

	private void updateAnimSpeeds () {
		int p;
		for (p=0;p<GV.s.numTribTypes;p++) {
			TH.Anims[TH.IanimSpawn+p].setFrameDuration((GV.s.spawnInterval / (float) (TH.Anims[TH.IanimSpawn+p].getKeyFrames().length))/1000.0f);
			TH.Anims[TH.IanimWiggle+p].setFrameDuration((GV.s.wiggleInt / (float) (TH.Anims[TH.IanimWiggle+p].getKeyFrames().length))/1000.0f);
			TH.Anims[TH.IanimShutEyes+p].setFrameDuration((GV.s.shutInt / (float) (TH.Anims[TH.IanimShutEyes+p].getKeyFrames().length))/1000.0f);

		}
	}

	private void moveUp (int col, int row) {

		for (int t=0;t<row;t++) {
			GV.s.board[col] [t] = GV.s.board[col] [t+1];
			GV.s.boardState[col] [t] = GV.s.boardState [col] [t+1];
			GV.s.boardStateTimer [col] [t] = GV.s.boardStateTimer [col] [t+1];
			GV.s.boardX [col] [t] = GV.s.boardX [col] [t+1];
			GV.s.boardY [col] [t] = GV.s.boardY [col] [t+1];	
			GV.s.boardSpeed [col] [t] = GV.s.boardSpeed [col] [t+1];
			GV.s.spawnFade [col] [t] = GV.s.spawnFade [col] [t+1];

		}

	}

	private void moveDown (int p, int t, int highest) {
		int i; 
		for (i=t;i>highest;i--) {
			GV.s.board[p] [i] = GV.s.board[p] [i-1];
			GV.s.boardState[p] [i] = GV.s.boardState [p] [i-1];
			GV.s.boardStateTimer [p] [i] = GV.s.boardStateTimer [p] [i-1];
			GV.s.boardX [p] [i] = GV.s.boardX [p] [i-1];
			GV.s.boardY [p] [i] = GV.s.boardY [p] [i-1];	
			GV.s.boardSpeed [p] [i] = GV.s.boardSpeed [p] [i-1];
			GV.s.spawnFade [p] [i] = GV.s.spawnFade [p] [i-1];

		}

		GV.s.board[p] [i] = GV.s.numTribTypes+1;
		GV.s.boardState[p] [i] = 0;
		GV.s.boardStateTimer [p] [i] = 0;
		GV.s.boardX [p] [i] = 0;
		GV.s.boardY [p] [i] = 0;	
		GV.s.boardSpeed [p] [i] = 0;
		GV.s.spawnFade [p] [i] = 0.33f;

	}

	private void moveColsLeft (int p) {
		int t,i;
		for (i=p;i<GV.s.boardW-1;i++) {
			for (t=0;t<GV.s.boardH;t++) {
				GV.s.board[i] [t] = GV.s.board[i+1] [t];
				GV.s.boardState[i] [t] = GV.s.boardState [i+1] [t];
				GV.s.boardStateTimer [i] [t] = GV.s.boardStateTimer [i+1] [t];
				GV.s.boardX [i] [t] = GV.s.boardX [i+1] [t];
				GV.s.boardY [i] [t] = GV.s.boardY [i+1] [t];	
				GV.s.boardSpeed [i] [t] = GV.s.boardSpeed [i+1] [t];
				GV.s.spawnFade [i] [t] = GV.s.spawnFade [i+1] [t];

			}
		}
		for (t=0;t<GV.s.boardH;t++) {	
			GV.s.board[i] [t] = GV.s.numTribTypes+1;
			GV.s.boardState[i] [t] = 0;
			GV.s.boardStateTimer [i] [t]=0;
			GV.s.boardX [i] [t] = 0;
			GV.s.boardY [i] [t] = 0;	
			GV.s.boardSpeed [i] [t] = 0;
			GV.s.spawnFade [i] [t] = 0.33f;
		}		
	}

	private void moveColsRight (int p) {
		int t,i;
		for (i=p;i>0;i--) {
			for (t=0;t<GV.s.boardH;t++) {
				GV.s.board[i] [t] = GV.s.board[i-1] [t];
				GV.s.boardState[i] [t] = GV.s.boardState [i-1] [t];
				GV.s.boardStateTimer [i] [t] = GV.s.boardStateTimer [i-1] [t];
				GV.s.boardX [i] [t] = GV.s.boardX [i-1] [t];
				GV.s.boardY [i] [t] = GV.s.boardY [i-1] [t];	
				GV.s.boardSpeed [i] [t] = GV.s.boardSpeed [i-1] [t];
				GV.s.spawnFade [i] [t] = GV.s.spawnFade [i-1] [t];
			}
		}
		for (t=0;t<GV.s.boardH;t++) {	
			GV.s.board[i] [t] = GV.s.numTribTypes+1;
			GV.s.boardState[i] [t] = 0;
			GV.s.boardStateTimer [i] [t]=0;
			GV.s.boardX [i] [t] = 0;
			GV.s.boardY [i] [t] = 0;	
			GV.s.boardSpeed [i] [t] = 0;
			GV.s.spawnFade [i] [t] = 0;
		}		
	}

	private int findSurroundTribs (int x, int y) {
		int numSur=0;

		int tribsFoundX [] = new int [GV.s.boardW*GV.s.boardH];
		int tribsFoundY [] = new int [GV.s.boardW*GV.s.boardH];

		tribsFoundX[0]=x;
		tribsFoundY[0]=y;
		int numTribsFound=1;

		int numTribsSearched=0;

		int boardX,boardY,i;

		int type = GV.s.board[x][y];

		if (type<GV.s.numTribTypes) {

			while (numTribsFound>numTribsSearched) {

				i=numTribsSearched;

				boardX = tribsFoundX[i]-1;
				if (boardX>-1 && boardX<GV.s.boardW) {
					if (GV.s.board[boardX][tribsFoundY[i]]==type) if (notInFound(boardX+(tribsFoundY[i]*GV.s.boardW),tribsFoundX,tribsFoundY,numTribsFound)) {
						tribsFoundX[numTribsFound]=boardX;
						tribsFoundY[numTribsFound]=tribsFoundY[i];
						numTribsFound++;
					}
				}

				boardX = tribsFoundX[i]+1;
				if (boardX>-1 && boardX<GV.s.boardW) {
					if (GV.s.board[boardX][tribsFoundY[i]]==type) if (notInFound(boardX+(tribsFoundY[i]*GV.s.boardW),tribsFoundX,tribsFoundY,numTribsFound)) {
						tribsFoundX[numTribsFound]=boardX;
						tribsFoundY[numTribsFound]=tribsFoundY[i];
						numTribsFound++;
					}
				}

				boardY = tribsFoundY[i]-1;
				if (boardY>-1 && boardY<GV.s.boardH) {
					if (GV.s.board[tribsFoundX[i]][boardY]==type) if (notInFound(tribsFoundX[i]+(boardY*GV.s.boardW),tribsFoundX,tribsFoundY,numTribsFound)) {
						tribsFoundX[numTribsFound]=tribsFoundX[i];
						tribsFoundY[numTribsFound]=boardY;
						numTribsFound++;
					}
				}

				boardY = tribsFoundY[i]+1;
				if (boardY>-1 && boardY<GV.s.boardH) {
					if (GV.s.board[tribsFoundX[i]][boardY]==type) if (notInFound(tribsFoundX[i]+(boardY*GV.s.boardW),tribsFoundX,tribsFoundY,numTribsFound)) {
						tribsFoundX[numTribsFound]=tribsFoundX[i];
						tribsFoundY[numTribsFound]=boardY;
						numTribsFound++;
					}
				}

				numTribsSearched++;

			}

		}

		numSur=numTribsFound;
		if (numSur>=GV.s.neededForMatch) {
			for (int p=0;p<numTribsFound;p++) {
				GV.s.tribsToPopX[p]=tribsFoundX[p];
				GV.s.tribsToPopY[p]=tribsFoundY[p];
			}
			GV.s.numTribsToPop=numSur;
		}

		return (numSur);

	}

	private boolean notInFound (int x, int tribsX[], int tribsY[], int numTribs) {
		boolean notFound = true;

		for (int p=0;p<numTribs;p++) if (tribsX[p]+(tribsY[p]*GV.s.boardW)==x) notFound=false;

		return notFound;
	}

	private void popTribs () {
		int p;
		
		GV.s.score += (GV.s.numTribsToPop*5)*GV.s.level;
		
		for (p=0;p<GV.s.numTribsToPop;p++) {
			GV.s.poppedTribType[GV.s.numPoppedTribs+p]=GV.s.board[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]];
			GV.s.poppedTribX[GV.s.numPoppedTribs+p]=GV.s.boardX[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]];
			GV.s.poppedTribY[GV.s.numPoppedTribs+p]=GV.s.boardY[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]];
			GV.s.poppedTribRotation[GV.s.numPoppedTribs+p]=0.0f;
			if (MathUtils.randomBoolean()) GV.s.poppedTribXDInc [GV.s.numPoppedTribs+p]=MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed); else GV.s.poppedTribXDInc [GV.s.numPoppedTribs+p]=0.0f-MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed);
			if (MathUtils.randomBoolean()) GV.s.poppedTribYDInc [GV.s.numPoppedTribs+p]=MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed); else GV.s.poppedTribYDInc [GV.s.numPoppedTribs+p]=0.0f-MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed);
			if (MathUtils.randomBoolean()) GV.s.poppedTRotationSpdInc [GV.s.numPoppedTribs+p]=MathUtils.random (poppedTribRotationSpeed*0.25f,poppedTribRotationSpeed); else GV.s.poppedTRotationSpdInc [GV.s.numPoppedTribs+p]=0.0f-MathUtils.random (poppedTribRotationSpeed*0.25f,poppedTribRotationSpeed);

			if (MathUtils.randomBoolean()) GV.s.poppedTribXD [GV.s.numPoppedTribs+p]=(MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed)*20.0f); else GV.s.poppedTribXD [GV.s.numPoppedTribs+p]=0.0f-(MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed)*20.0f);
			if (MathUtils.randomBoolean()) GV.s.poppedTribYD [GV.s.numPoppedTribs+p]=(MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed)*20.0f); else GV.s.poppedTribYD [GV.s.numPoppedTribs+p]=0.0f-(MathUtils.random (poppedTribSpeed*0.25f,poppedTribSpeed)*20.0f);
			if (MathUtils.randomBoolean()) GV.s.poppedTRotationSpd [GV.s.numPoppedTribs+p]=(MathUtils.random (poppedTribRotationSpeed*0.25f,poppedTribRotationSpeed)*20.0f); else GV.s.poppedTRotationSpd [GV.s.numPoppedTribs+p]=0.0f-(MathUtils.random (poppedTribRotationSpeed*0.25f,poppedTribRotationSpeed)*20.0f);


		}

		GV.s.numPoppedTribs+=GV.s.numTribsToPop;

		for (p=0;p<GV.s.numTribsToPop;p++) {
			GV.s.board[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]]=GV.s.numTribTypes+1;
		}

		GV.s.numTribsToPop=0;


	}

	private static void doLevel () {
		int p,t;
		GV.s.level ++;
		GV.s.spawnInterval =1000 - (GV.s.level * 7);
		GV.s.wiggleInt = 1500 - (GV.s.level * 14);
		GV.s.shutInt = 500 - (GV.s.level * 3);
		
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

		GV.s.spawnRowTimer+=t;
	}


}