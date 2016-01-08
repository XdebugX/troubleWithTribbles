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
	private int p,t,pp;
	private ShapeRenderer sr;
	private GlyphLayout gl;
	private BitmapFontData bfD;
	private static int whichTribX,whichTribY,highestTrib,lastWootPlayed,woot,randomCardD,wiggleRate,tribsFoundX[],tribsFoundY[],numSur,numTribsFound,numTribsSearched,boardX,boardY,tribsOnBoard[],numTribsOnBoard,tribToBreed,breaker,breeder;
	private static float bX,bY,srX,srY,tribW,tribH,bW,bH,boardSpeedInc,startSpeed,poppedTribSpeed,poppedTribRotationSpeed,scoreBarH,scoreTextScale,levelX,pausedX,pausedY,pausedScale,levelClearX,levelClearY,levelClearW,levelClearH,levelClearScale,
	corBX,corBY,doorRX,doorRY,doorFX,doorFY,topX,topY,botX,botY,doorLX,doorLY,sideRX,sideRY,laserX,laserY,laserAngle,windowX,windowY,pausedW,pausedH;
	private static String scoreString,levelString;
	private static final String pausedString = "Paused",levelClearString = "Level Clear!";
	private static boolean doBack,tribbleTouched,isATrib,noTribs,levelClear,matchAvailable,movingDone,prevTribs;


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

		batch.draw(TH.texts[TH.ItxtWindow], windowX, windowY, TH.textsW[TH.ItxtWindow], TH.textsH[TH.ItxtWindow]);
		for (p=0;p<GV.s.numLaser;p++) {
			batch.draw(TH.Anims[TH.IanimLaser].getKeyFrame(GV.s.laserAniTimer[p] += delta, false ), laserX, laserY, TH.animW[TH.IanimLaser]/2.0f, TH.animH[TH.IanimLaser]/2.0f, TH.animW[TH.IanimLaser], TH.animH[TH.IanimLaser], 1.0f, 1.0f, laserAngle);
		}
		batch.draw(TH.texts[TH.ItxtSideR], sideRX, sideRY, TH.textsW[TH.ItxtSideR], TH.textsH[TH.ItxtSideR]);
		batch.draw(TH.texts[TH.ItxtBot], botX, botY, TH.textsW[TH.ItxtBot], TH.textsH[TH.ItxtBot]);
		batch.draw(TH.texts[TH.ItxtTop], topX, topY, TH.textsW[TH.ItxtTop], TH.textsH[TH.ItxtTop]);		
		batch.draw(TH.texts[TH.ItxtCorBack], corBX, corBY, TH.textsW[TH.ItxtCorBack], TH.textsH[TH.ItxtCorBack]);		
		batch.draw(TH.texts[TH.ItxtDoorL], doorLX, doorLY, TH.textsW[TH.ItxtDoorL], TH.textsH[TH.ItxtDoorL]);		
		batch.draw(TH.texts[TH.ItxtDoorR], doorRX, doorRY, TH.textsW[TH.ItxtDoorR], TH.textsH[TH.ItxtDoorR]);	
		batch.draw(TH.texts[TH.ItxtDoorF], doorFX, doorFY, TH.textsW[TH.ItxtDoorF], TH.textsH[TH.ItxtDoorF]);	




		if (GV.s.paused) {
			
			batch.setColor (0.0f,0.0f,0.0f,0.7f);
			batch.draw(TH.texts[TH.ItxtPixel], pausedX, pausedY, pausedW, pausedH);
			batch.setColor(Color.WHITE);

			bfD.setScale(pausedScale);
			TH.bf.setColor(Color.GOLD);
			TH.bf.draw(batch, pausedString, pausedX, pausedY);

		} else {


			if (GV.s.gameType==0) {
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
				if (GV.s.boardState[p][t]==5) {
					batch.setColor(1.0f,1.0f,1.0f,GV.s.spawnFade[p][t]);					
					batch.draw(TH.texts[TH.ItxtBallsIdle+GV.s.board[p][t]], bX+GV.s.boardX[p][t], bY+GV.s.boardY[p][t], tribW, tribH);
					batch.setColor(Color.WHITE);
					batch.draw(TH.Anims[TH.IanimTransport].getKeyFrame(GV.s.boardStateTimer[p][t] += delta, false), bX+GV.s.boardX[p][t], bY+GV.s.boardY[p][t], tribW, tribH);
				}


			}

			for (p=0;p<GV.s.numPoppedTribs;p++) {
				batch.draw(TH.texts[TH.ItxtBallsIdle+GV.s.poppedTribType[p]], GV.s.poppedTribX[p], GV.s.poppedTribY[p], tribW/2.0f, tribH/2.0f, tribW, tribH, 1.0f, 1.0f, GV.s.poppedTribRotation[p]);															
			}
			bfD.setScale(scoreTextScale);
			TH.bf.setColor(Color.GOLD);
			TH.bf.draw(batch, scoreString, 0, 0);		
			TH.bf.draw(batch, levelString, levelX, 0);

			if (GV.s.doClearAni) {
				
				batch.setColor (0.0f,0.0f,0.0f,0.8f);
				batch.draw(TH.texts[TH.ItxtPixel], levelClearX, levelClearY, levelClearW, levelClearH);
				batch.setColor(Color.WHITE);
				
				bfD.setScale(levelClearScale);
				TH.bf.setColor(Color.GOLD);
				TH.bf.draw(batch, levelClearString, levelClearX, levelClearY);		
			}

		}//not paused

		batch.end();




		//update game

		if (doBack) {
			doBack=false;
			if (GV.s.paused) resumeGame(); else {
				Save.saveGame(game, GV.s);
				game.setScreen(game.mmScreen);				
			}
		}

		if (!GV.s.paused) {
			//////////////////do background stuff


			///////////////// do game stuff



			///////////////////Spawn Row
			if (GV.s.gameType==0) {
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

			}//if gametype endless

			//////////////////////user controls

			if (tribbleTouched) {
				if (GV.s.boardState[whichTribX][whichTribY]!=5) {
					GV.s.tribTouched=true;
					GV.s.wTX=whichTribX;
					GV.s.wTY=whichTribY;
				}
				tribbleTouched=false;
			}

			if (GV.s.tribTouched) {
				if (GV.s.board[GV.s.wTX][GV.s.wTY]<GV.s.numTribTypes) { 
					if (findSurroundTribs (GV.s.wTX,GV.s.wTY)>=GV.s.neededForMatch) {
						for (p=0;p<numTribsFound;p++) {
							GV.s.tribsToPopX[p]=tribsFoundX[p];
							GV.s.tribsToPopY[p]=tribsFoundY[p];
						}
						GV.s.numTribsToPop=numTribsFound;
						
						if (GV.opts.sfxOn) TH.sfxs[TH.IsfxTransport].play();
						popTribs();
					} 
				}
				GV.s.tribTouched=false;
			}


			/////////////////board 
			//do random blinks
			for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==1) if (MathUtils.random(60*10)==0) {
				GV.s.boardState[p][t]=2;
				GV.s.boardStateTimer[p][t]=0.0f;
			}

			/// set state back to zero when blick animation is finished
			for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==2) if (TH.Anims[TH.IanimBlink+GV.s.board[p][t]].isAnimationFinished(GV.s.boardStateTimer[p][t])) {
				GV.s.boardState[p][t]=1;
				GV.s.boardStateTimer[p][t]=0.0f;
			}

			/// move the tribbles x or y location towards the spot they should be if they are not in the correct spot
			for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) {

				if (GV.s.boardX[p][t]!=p*tribW || GV.s.boardY[p][t]!=t*tribH) {

					GV.s.boardSpeed[p][t]+=boardSpeedInc;
					if (GV.s.boardSpeed[p][t]>GV.h/2.0f) GV.s.boardSpeed[p][t]-=boardSpeedInc;

					if (GV.s.boardX[p][t]<p*tribW) GV.s.boardX[p][t]+=GV.s.boardSpeed[p][t]*delta;
					if (GV.s.boardX[p][t]>p*tribW) GV.s.boardX[p][t]-=GV.s.boardSpeed[p][t]*delta;
					if (GV.s.boardY[p][t]<t*tribH) GV.s.boardY[p][t]+=GV.s.boardSpeed[p][t]*delta;
					if (GV.s.boardY[p][t]>t*tribH) GV.s.boardY[p][t]-=GV.s.boardSpeed[p][t]*delta;

					if (GV.s.boardY[p][t]>=t*tribH-(GV.s.boardSpeed[p][t]*delta)-1 && GV.s.boardY[p][t]<=t*tribH+(GV.s.boardSpeed[p][t]*delta)+1) GV.s.boardY[p][t] = t*tribH;
					if (GV.s.boardX[p][t]>=p*tribW-(GV.s.boardSpeed[p][t]*delta)-1 && GV.s.boardX[p][t]<=p*tribW+(GV.s.boardSpeed[p][t]*delta)+1) GV.s.boardX[p][t] = p*tribW;



				} else GV.s.boardSpeed[p][t]=startSpeed;

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

			//transport tribbles to klingon ship
			for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==5) {
				GV.s.spawnFade[p][t] = 1.0f - (GV.s.boardStateTimer [p][t] / TH.Anims[TH.IanimTransport].getAnimationDuration());
				if (TH.Anims[TH.IanimTransport].isAnimationFinished(GV.s.boardStateTimer[p][t])) {
					GV.s.board[p] [t]=GV.s.numTribTypes+1;
					GV.s.boardState[p] [t]=0;
					GV.s.boardStateTimer[p][t]=0;
					GV.s.spawnFade[p][t]=0;
					GV.s.laserAniTimer[GV.s.numLaser]=0.0f;
					GV.s.numLaser++;
				}
			}

			//check for laser ani finished
			for (p=0;p<GV.s.numLaser;p++) {
				if (TH.Anims[TH.IanimLaser].isAnimationFinished(GV.s.laserAniTimer[p])) {
					for (t=p;t<GV.s.numLaser-1;t++) GV.s.laserAniTimer[t]=GV.s.laserAniTimer[t+1];
					GV.s.numLaser--;
					p--;
				}
			}

			fillGaps();


			/////////////////////Tribbles Breed
			//do random Wiggles

			if (GV.s.gameType==1) {

				wiggleRate = (int) ((float) (15*noLess(15,200-((int) ((float) (GV.s.level)*1.75f))))*((0.033333/delta)));

				for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==1) if (MathUtils.random(wiggleRate+(numTribsOnBoard*5))==0) {
					GV.s.boardState[p][t]=3;
					GV.s.boardStateTimer[p][t]=0.0f;
				}

				/// set state to shut eye when wiggle animation is finished and spawn a new tribble
				for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) if (GV.s.boardState[p][t]==3) {
					if (TH.Anims[TH.IanimWiggle+GV.s.board[p][t]].isAnimationFinished(GV.s.boardStateTimer[p][t])) {
						GV.s.boardState[p][t]=4;
						GV.s.boardStateTimer[p][t]=0.0f;
						GV.s.spawnFade[p][t]=1.0f;

						randomCardD=MathUtils.random(0,3);
						if (randomCardD==0 && p==0) randomCardD=1;
						if (randomCardD==1 && p==GV.s.boardW-1) randomCardD=0;
						if (randomCardD==2 && t==0) randomCardD=3;
						//if (randomCardD==3 && t==GV.s.boardH-1) randomCardD=2;

						breeder = GV.s.board[p][t];

						if (randomCardD==0) {
							moveUp (p-1,t);
							if (breeder<2) GV.s.board[p-1][t]=MathUtils.random(0,1); else GV.s.board[p-1][t]=MathUtils.random(2,3);
							GV.s.boardStateTimer[p-1][t]=0.0f;
							GV.s.boardState[p-1][t]=0;
							GV.s.boardX[p-1][t]=(p*tribW);
							GV.s.boardY[p-1][t]=(t*tribH);
							GV.s.boardSpeed[p-1][t]=startSpeed;
							GV.s.spawnFade [p-1] [t] = 0.33f;
						}

						if (randomCardD==1) {
							moveUp (p+1,t);
							if (breeder<2) GV.s.board[p+1][t]=MathUtils.random(0,1); else GV.s.board[p+1][t]=MathUtils.random(2,3);
							GV.s.boardStateTimer[p+1][t]=0.0f;
							GV.s.boardState[p+1][t]=0;
							GV.s.boardX[p+1][t]=(p*tribW);
							GV.s.boardY[p+1][t]=(t*tribH);
							GV.s.boardSpeed[p+1][t]=startSpeed;
							GV.s.spawnFade [p+1] [t] = 0.33f;

						}

						if (randomCardD==2) {
							moveUp (p,t-1);
							if (breeder<2) GV.s.board[p][t-1]=MathUtils.random(0,1); else GV.s.board[p][t-1]=MathUtils.random(2,3);
							GV.s.boardStateTimer[p][t-1]=0.0f;
							GV.s.boardState[p][t-1]=0;
							GV.s.boardX[p][t-1]=(p*tribW);
							GV.s.boardY[p][t-1]=(t*tribH);
							GV.s.boardSpeed[p][t-1]=startSpeed;
							GV.s.spawnFade [p] [t-1] = 0.33f;

						}

						if (randomCardD==3) {
							moveUp (p,t);
							if (breeder<2) GV.s.board[p][t]=MathUtils.random(0,1); else GV.s.board[p][t]=MathUtils.random(2,3);
							GV.s.boardStateTimer[p][t]=0.0f;
							GV.s.boardState[p][t]=0;
							GV.s.boardX[p][t]=(p*tribW);
							GV.s.boardY[p][t]=(t*tribH);
							GV.s.boardSpeed[p][t]=startSpeed;
						}

						fillGaps();
						GV.s.tribBreeding=false;


					}
				}

			}

			//if game type 1 and no matches breed a tribble
			if (GV.s.gameType==1 && !GV.s.tribBreeding) {
				matchAvailable=false;
				for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) {
					if (GV.s.board[p][t]<GV.s.numTribTypes) if (findSurroundTribs(p,t)>=GV.s.neededForMatch) matchAvailable=true;
				}

				if (!matchAvailable) {
					numTribsOnBoard=0;
					for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) {
						if (GV.s.board[p][t]<GV.s.numTribTypes) {
							tribsOnBoard[numTribsOnBoard++]=p+(t*GV.s.boardW);
						}
					}

					if (numTribsOnBoard>0) {

						breaker=0;
						do {
							tribToBreed=MathUtils.random(0,numTribsOnBoard-1);

							t=(int) (tribsOnBoard[tribToBreed]/GV.s.boardW);
							p=tribsOnBoard[tribToBreed] % GV.s.boardW;
						}
						while (GV.s.boardState[p][t]==4 && breaker<500);

						GV.s.boardState[p][t]=3;
						GV.s.boardStateTimer[p][t]=0.0f;
						GV.s.tribBreeding=true;
					}
				}
			}

			//level
			if (GV.s.gameType==0) if (GV.s.score>GV.s.levelThresholds[GV.s.level-1]) {
				playWoot();
				doLevel ();
			}

			// do clear the level game type
			if (GV.s.gameType==1) {
				if (!GV.s.doClearAni && !GV.s.clearAniDone) {
					levelClear=true;
					numTribsOnBoard=0;
					for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) {
						numTribsOnBoard++;
						levelClear=false;
					}

					if (levelClear) {
						playWoot();
						GV.s.doClearAni=true;
					}
					levelClear=false;
				}
			}

			// clear ani done
			if (GV.s.clearAniDone) {
				GV.s.clearAniDone=false;
				doLevel();
			}

		}// not paused

	}

	@Override
	public void resize(int width, int height) {
		GV.doSizes();
		camera = new OrthographicCamera(GV.w, GV.h);
		camera.setToOrtho(true, GV.w, GV.h);
		camera.position.set(GV.w / 2, GV.h / 2, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		sr=new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		TH.sizes();
		sizes();


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

		scoreString="Score "+GV.s.score;
		levelString = "Level "+GV.s.level;

		tribsFoundX = new int [GV.s.boardW*GV.s.boardH];
		tribsFoundY = new int [GV.s.boardW*GV.s.boardH];

		tribsOnBoard = new int [GV.s.boardW*GV.s.boardH];

		sizes();

		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}

	@Override
	public void hide() {
		for (int p=0;p<TH.numMusic;p++) if (TH.loopingMusic!=null) if (TH.loopingMusic[p]!=null) TH.loopingMusic[p].pause();
	}

	@Override
	public void pause() {

		int p=0;
		for (p=0;p<TH.numMusic;p++) if (TH.loopingMusic!=null) if (TH.loopingMusic[p]!=null) TH.loopingMusic[p].pause();

		GV.s.pauseTime=System.currentTimeMillis();
		GV.s.paused=true;

		Save.saveGame(game, GV.s);

	}

	@Override
	public void resume() {
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
		int j,k;
		if (!GV.s.paused && !GV.s.doClearAni) {
			for (j=0;j<GV.s.boardW;j++) for (k=0;k<GV.s.boardH;k++) if (GV.s.board[j][k]<GV.s.numTribTypes) if (x>bX+GV.s.boardX[j] [k] && x<bX+GV.s.boardX[j] [k]+tribW && y>bY+GV.s.boardY[j][k] && y<bY+GV.s.boardY[j][k]+tribH) {
				tribbleTouched=true;
				whichTribX=j;
				whichTribY=k;
			}
		}

		return true;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		if (GV.s.paused) resumeGame (); else {

			if (GV.s.doClearAni) {
				GV.s.doClearAni=false;
				GV.s.clearAniDone=true;
			} 

		}//notPaused
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
		int p,t;

		scoreBarH = GV.h/40.0f;

		bfD = TH.bf.getData();

		float a,z;
		int breaker;

		a=0.0f;
		z=0.1f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,levelClearString);
			a=gl.width;
			breaker++;
		} while (a<GV.w/3 && breaker<1500);

		levelClearScale = z;

		levelClearX=(GV.w-gl.width)/2.0f;
		levelClearY=(GV.h-gl.height)/2.0f;
		levelClearW=gl.width;
		levelClearH=bfD.lineHeight;


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


		z=0.1f;
		a=0.0f;
		breaker=0;

		do {
			z=z+0.1f;
			bfD.setScale(z);
			gl = new GlyphLayout (TH.bf,"Score: 10000");
			a=gl.height;
			breaker++;
		} while (a<scoreBarH && breaker<1500);

		scoreTextScale = z;

		gl = new GlyphLayout (TH.bf, levelString);
		levelX=GV.w-gl.width;

		float h=GV.h-scoreBarH;

		tribW=GV.w/GV.s.boardW;
		if (GV.s.gameType==0) tribH=h/(GV.s.boardH+1); else tribH=h/(GV.s.boardH);

		if (tribW<tribH) tribH=tribW; else tribW=tribH;

		bW=tribW*GV.s.boardW;
		bH=tribH*GV.s.boardH;

		bX=(GV.w-bW)/2.0f;
		if (GV.s.gameType==0) bY=(h-(bH+tribH))/2.0f; else bY=(h-bH)/2.0f;

		boardSpeedInc = (10.0f+(GV.s.level/10.0f))*GV.aspRatL;
		startSpeed = (50.0f+(GV.s.level*0.5f))*GV.aspRatL;

		poppedTribSpeed = 5.0f*GV.aspRatL;
		poppedTribRotationSpeed = 3.6f*4.0f;

		srX=bX;
		srY=bY+(tribH*(GV.s.boardH));

		for (p=0;p<GV.s.boardW;p++) for (t=0;t<GV.s.boardH;t++) {
			if (GV.s.board[p][t]<GV.s.numTribTypes) {
				GV.s.boardX[p][t]=p*tribW;
				GV.s.boardY[p][t]=t*tribH;
			}
		}

		corBX=0.0f*GV.aspRatW;
		corBY=350.0f*GV.aspRatW;

		doorLX=(190.0f*GV.aspRatW)-TH.textsW[TH.ItxtDoorL];
		doorLY=397.0f*GV.aspRatW;

		doorRX=190.0f*GV.aspRatW;
		doorRY=397.0f*GV.aspRatW;

		topX=0.0f*GV.aspRatW;
		topY=0.0f*GV.aspRatW;

		botX=0.0f*GV.aspRatW;
		botY=936*GV.aspRatW;
		sideRX=379.0f*GV.aspRatW;
		sideRY=-5.0f*GV.aspRatW;

		windowX=(155.0f+379.0f)*GV.aspRatW;
		windowY=(338.0f-5.0f)*GV.aspRatW;


		doorFX=0.0f*GV.aspRatW;
		doorFY=350.0f*GV.aspRatW;

		laserX=(379.0f+85.0f)*GV.aspRatW;
		laserY=(-5.0f+540.0f)*GV.aspRatW;

		laserAngle=135.0f;


		updateAnimSpeeds();


	}

	private void updateAnimSpeeds () {
		int p;

		GV.s.spawnInterval =noLess (375,1000 - (GV.s.level * 5));
		GV.s.wiggleInt = noLess (475,1500 - (GV.s.level * 14));
		GV.s.shutInt = noLess (50,500 - (GV.s.level * 3));
		GV.s.transportInt = noLess (350,1350 - (GV.s.level*13));

		//if (GV.s.gameType==0) wiggleRate = 60*noLess(5,200-GV.s.level); else wiggleRate = 15*noLess(15,200-((int) ((float) (GV.s.level)*1.75f)));

		boardSpeedInc = (10.0f+(GV.s.level/10.0f))*GV.aspRatL;
		startSpeed = (50.0f+(GV.s.level*0.5f))*GV.aspRatL;


		for (p=0;p<GV.s.numTribTypes;p++) {
			TH.Anims[TH.IanimSpawn+p].setFrameDuration((GV.s.spawnInterval / (float) (TH.Anims[TH.IanimSpawn+p].getKeyFrames().length))/1000.0f);
			TH.Anims[TH.IanimWiggle+p].setFrameDuration((GV.s.wiggleInt / (float) (TH.Anims[TH.IanimWiggle+p].getKeyFrames().length))/1000.0f);
			TH.Anims[TH.IanimShutEyes+p].setFrameDuration((GV.s.shutInt / (float) (TH.Anims[TH.IanimShutEyes+p].getKeyFrames().length))/1000.0f);
		}

		TH.Anims[TH.IanimTransport].setFrameDuration((GV.s.transportInt / (float) (TH.Anims[TH.IanimTransport].getKeyFrames().length))/1000.0f);
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
		GV.s.boardSpeed [p] [i] = startSpeed;
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
			GV.s.boardSpeed [i] [t] = startSpeed;
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
			GV.s.boardSpeed [i] [t] = startSpeed;
			GV.s.spawnFade [i] [t] = 0;
		}		
	}

	private int findSurroundTribs (int x, int y) {
		numSur=0;

		tribsFoundX[0]=x;
		tribsFoundY[0]=y;
		numTribsFound=1;

		numTribsSearched=0;

		int i;

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

		return (numSur);

	}

	private boolean notInFound (int x, int tribsX[], int tribsY[], int numTribs) {
		boolean notFound = true;

		for (int p=0;p<numTribs;p++) if (tribsX[p]+(tribsY[p]*GV.s.boardW)==x) notFound=false;

		return notFound;
	}

	private void popTribs () {
		int p;

		GV.s.score += (GV.s.numTribsToPop*5)*(GV.s.level*5);
		scoreString="Score "+GV.s.score;
		/*
		for (p=0;p<GV.s.numTribsToPop;p++) {
			GV.s.poppedTribType[GV.s.numPoppedTribs+p]=GV.s.board[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]];
			GV.s.poppedTribX[GV.s.numPoppedTribs+p]=bX+GV.s.boardX[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]];
			GV.s.poppedTribY[GV.s.numPoppedTribs+p]=bY+GV.s.boardY[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]];
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


		 */

		for (p=0;p<GV.s.numTribsToPop;p++) {
			GV.s.boardState[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]]=5;
			GV.s.spawnFade[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]]=1.0f;
			GV.s.boardStateTimer[GV.s.tribsToPopX[p]] [GV.s.tribsToPopY[p]]=0;
		}
		GV.s.numTribsToPop=0;


	}

	private void doLevel () {
		int p,t;
		GV.s.level ++;

		levelString = "Level "+GV.s.level;

		bfD.setScale (scoreTextScale);
		gl = new GlyphLayout (TH.bf, levelString);
		levelX=GV.w-gl.width;

		updateAnimSpeeds();

		if (GV.s.gameType==1) {
			for (p=0;p<GV.s.boardW;p++) {
				GV.s.board[p][GV.s.boardH-1]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.boardStateTimer[p][GV.s.boardH-1]=0.0f;
				GV.s.boardState[p][GV.s.boardH-1]=1;
				GV.s.boardX[p][GV.s.boardH-1]=(p*tribW);
				GV.s.boardY[p][GV.s.boardH-1]=((GV.s.boardH-1)*tribH);
				GV.s.boardSpeed[p][GV.s.boardH-1]=startSpeed;
			}

			int a = GV.s.level;
			if (a>(GV.s.boardH/2)*GV.s.boardW) a = (int) (GV.s.boardH/2*GV.s.boardW);

			int y=a/GV.s.boardW;
			int x=a-(y*GV.s.boardW);

			for (t=0;t<y;t++) for (p=0;p<GV.s.boardW;p++) {
				GV.s.board[p][GV.s.boardH-2-t]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.boardStateTimer[p][GV.s.boardH-2-t]=0.0f;
				GV.s.boardState[p][GV.s.boardH-2-t]=1;
				GV.s.boardX[p][GV.s.boardH-2-t]=(p*tribW);
				GV.s.boardY[p][GV.s.boardH-2-t]=((GV.s.boardH-2-t)*tribH);
				GV.s.boardSpeed[p][GV.s.boardH-2-t]=startSpeed;				
			}

			for (p=0;p<x;p++) {
				GV.s.board[p][GV.s.boardH-2-t]=MathUtils.random(0,GV.s.numTribTypes-1);
				GV.s.boardStateTimer[p][GV.s.boardH-2-t]=0.0f;
				GV.s.boardState[p][GV.s.boardH-2-t]=1;
				GV.s.boardX[p][GV.s.boardH-2-t]=(p*tribW);
				GV.s.boardY[p][GV.s.boardH-2-t]=((GV.s.boardH-2-t)*tribH);
				GV.s.boardSpeed[p][GV.s.boardH-2-t]=startSpeed;								
			}


		}

	}


	private void fillGaps () {
		//move tribbles to fill gaps
		for (pp=0;pp<GV.s.boardH;pp++) {
			movingDone=true;
			for (p=0;p<GV.s.boardW;p++) {
				isATrib=false;
				for (t=0;t<GV.s.boardH-1;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) {
					highestTrib=t;
					t=GV.s.boardH;
					isATrib=true;
				}

				if (isATrib) if (highestTrib<GV.s.boardH-1) {
					for (t=GV.s.boardH-1;t>highestTrib;t--) if (GV.s.board[p][t]>=GV.s.numTribTypes) {
						movingDone=false;
						moveDown (p,t,highestTrib);
					}
				}
			}

			//check for entire column of tribbles is empty and move colums to the left or right towards the center;
			prevTribs=false;
			for (p=0;p<GV.s.boardW/2;p++) {
				noTribs=true;
				for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) {
					noTribs=false;
					prevTribs=true;
				}
				if (noTribs && prevTribs) {
					moveColsRight (p);
					movingDone=false;
				}
			}

			prevTribs=false;
			for (p=GV.s.boardW-1;p>=GV.s.boardW/2;p--) {
				noTribs=true;
				for (t=0;t<GV.s.boardH;t++) if (GV.s.board[p][t]<GV.s.numTribTypes) {
					noTribs=false;
					prevTribs=true;
				}
				if (noTribs && prevTribs) {
					moveColsLeft (p);
					movingDone=false;
				}
			}

			if (movingDone) pp=GV.s.boardH;
		}
	}

	private int abs (int x) {
		if (x>=0) return (x); else return (x*-1);
	}

	private float abs (float x) {
		if (x>=0) return (x); else return (x*-1);
	}

	private int noLess (int min, int x) {
		if (x<min) return min; else return x;
	}

	private void resumeGame () {
		updateTimers (System.currentTimeMillis()-GV.s.pauseTime);
		GV.s.paused=false;
		if (GV.opts.musicOn) if (!TH.loopingMusic[TH.ImusicMM].isPlaying()) TH.loopingMusic[TH.ImusicMM].play();
	}

	private void updateTimers (long t) {
		GV.s.spawnRowTimer+=t;
	}

	private void playWoot () {
		if (GV.opts.sfxOn) {
			do {
				woot=MathUtils.random(TH.IsfxWoots,TH.IsfxWoots+TH.IsfxNumWoots-1);
			} while (woot==lastWootPlayed);
			TH.sfxs[woot].play();
			lastWootPlayed=woot;
		}

	}
	

}