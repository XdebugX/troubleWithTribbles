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
	
	public long futureUseLongs[];
	
	public float futureUseFloats[];
	
	public int board [] [],level,spawnRow[],boardW,boardH,numTribTypes,spawnRowI,spawnRowState[],boardState [] [],wTX,wTY,neededForMatch,tribsToPopX[],tribsToPopY[],numTribsToPop,numPoppedTribs,poppedTribType[],gameType,numLaser;
	
	public long spawnRowTimer,spawnInterval,wiggleInt,shutInt,score,levelThresholds[],transportInt,pauseTime;
	
	public float spawnRowStateTimer[],boardStateTimer[][],boardX[][],boardY[][],boardSpeed[][],poppedTribX[],poppedTribY[],poppedTribRotation[],poppedTRotationSpd[],poppedTribXD[],poppedTribYD[],poppedTRotationSpdInc[],poppedTribXDInc[],poppedTribYDInc[],spawnFade[][],spawnRowFade[],laserAniTimer[];
	
	public boolean tribTouched,tribBreeding,doClearAni,clearAniDone,paused;
	
	
	
	
	public SavedGame () {		
	}		

}
