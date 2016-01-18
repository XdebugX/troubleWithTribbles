package com.xdebugxgames.trouble.with.tribbles;

import java.io.Serializable;

public class SavedGame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean futureUseBools[];
	
	public int futureUseInts[];
	
	public String futureUseStrings[];
	
	public int futureUseLongs[];
	
	public float futureUseFloats[];
	
	public int board [] [],level,spawnRow[],boardW,boardH,numTribTypes,spawnRowI,spawnRowState[],boardState [] [],wTX,wTY,neededForMatch,tribsToPopX[],tribsToPopY[],numTribsToPop,gameType,numLaser,totalTribTrans;
	
	public int spawnRowTimer,spawnInterval,wiggleInt,shutInt,score,levelThresholds[],transportInt,pauseTime,expType,expAniI;
	
	public float spawnRowStateTimer[],boardStateTimer[][],boardX[][],boardY[][],boardSpeed[][],spawnFade[][],spawnRowFade[],laserX[],laserW[],expAniTimer;	
	public boolean tribTouched,tribBreeding,doClearAni,clearAniDone,paused,laserD[],gameOver,boardIsExp[][],spawnRowIsExp[],doExp,expImp;
	
	
	
	

}
