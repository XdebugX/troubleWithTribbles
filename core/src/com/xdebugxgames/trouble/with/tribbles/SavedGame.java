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
	
	public Long futureUseLongs[];
	
	public Float futureUseFloats[];
	
	public int board [] [],level,spawnRow[],boardW,boardH,numTribTypes,spawnRowI,spawnRowState[],boardState [] [],wTX,wTY,neededForMatch,tribsToPopX[],tribsToPopY[],numTribsToPop,numPoppedTribs,poppedTribType[];
	
	public long spawnRowTimer,spawnInterval;
	
	public float spawnRowStateTimer[],boardStateTimer[][],boardX[][],boardY[][],boardSpeed[][],poppedTribX[],poppedTribY[],poppedTribRotation[],poppedTRotationSpd[],poppedTribXD[],poppedTribYD[],poppedTRotationSpdInc[],poppedTribXDInc[],poppedTribYDInc[];
	
	public boolean tribTouched;
	
	
	
	
	public SavedGame () {		
	}		

}
