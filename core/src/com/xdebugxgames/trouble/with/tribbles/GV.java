package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class GV {
	public static float w,h,aspRatW,aspRatH,aspRatL,aspRatM,trueH;
	public static final float radX[]={90,89,89,89,89,89,89,89,89,88,88,88,88,87,87,86,86,86,85,85,84,84,83,82,82,81,80,80,79,78,77,77,76,75,74,73,72,71,70,69,68,67,66,65,64,63,62,61,60,59,57,56,55,54,52,51,50,49,47,46,45,43,42,40,39,38,36,35,33,32,30,29,27,26,24,23,21,20,18,17,15,14,12,10,9,7,6,4,3,1,0,-1,-3,-4,-6,-7,-9,-10,-12,-14,-15,-17,-18,-20,-21,-23,-24,-26,-27,-29,-30,-32,-33,-35,-36,-38,-39,-40,-42,-43,-44,-46,-47,-49,-50,-51,-52,-54,-55,-56,-57,-59,-60,-61,-62,-63,-64,-65,-66,-67,-68,-69,-70,-71,-72,-73,-74,-75,-76,-77,-77,-78,-79,-80,-80,-81,-82,-82,-83,-84,-84,-85,-85,-86,-86,-86,-87,-87,-88,-88,-88,-88,-89,-89,-89,-89,-89,-89,-89,-89,-90,-89,-89,-89,-89,-89,-89,-89,-89,-88,-88,-88,-88,-87,-87,-86,-86,-86,-85,-85,-84,-84,-83,-82,-82,-81,-80,-80,-79,-78,-77,-77,-76,-75,-74,-73,-72,-71,-70,-69,-68,-67,-66,-65,-64,-63,-62,-61,-60,-59,-57,-56,-55,-54,-52,-51,-50,-49,-47,-46,-45,-43,-42,-40,-39,-38,-36,-35,-33,-32,-30,-29,-27,-26,-24,-23,-21,-20,-18,-17,-15,-14,-12,-10,-9,-7,-6,-4,-3,-1,0,1,3,4,6,7,9,10,12,14,15,17,18,20,21,23,24,26,27,29,30,32,33,35,36,38,39,40,42,43,44,46,47,49,50,51,52,54,55,56,57,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,77,78,79,80,80,81,82,82,83,84,84,85,85,86,86,86,87,87,88,88,88,88,89,89,89,89,89,89,89,89,90};
	public static final float radY[]={0,1,3,4,6,7,9,10,12,14,15,17,18,20,21,23,24,26,27,29,30,32,33,35,36,38,39,40,42,43,44,46,47,49,50,51,52,54,55,56,57,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,77,78,79,80,80,81,82,82,83,84,84,85,85,86,86,86,87,87,88,88,88,88,89,89,89,89,89,89,89,89,90,89,89,89,89,89,89,89,89,88,88,88,88,87,87,86,86,86,85,85,84,84,83,82,82,81,80,80,79,78,77,77,76,75,74,73,72,71,70,69,68,67,66,65,64,63,62,61,60,59,57,56,55,54,52,51,50,49,47,46,45,43,42,40,39,38,36,35,33,32,30,29,27,26,24,23,21,20,18,17,15,14,12,10,9,7,6,4,3,1,0,-1,-3,-4,-6,-7,-9,-10,-12,-14,-15,-17,-18,-20,-21,-23,-24,-26,-27,-29,-30,-32,-33,-35,-36,-38,-39,-40,-42,-43,-44,-46,-47,-49,-50,-51,-52,-54,-55,-56,-57,-59,-60,-61,-62,-63,-64,-65,-66,-67,-68,-69,-70,-71,-72,-73,-74,-75,-76,-77,-77,-78,-79,-80,-80,-81,-82,-82,-83,-84,-84,-85,-85,-86,-86,-86,-87,-87,-88,-88,-88,-88,-89,-89,-89,-89,-89,-89,-89,-89,-90,-89,-89,-89,-89,-89,-89,-89,-89,-88,-88,-88,-88,-87,-87,-86,-86,-86,-85,-85,-84,-84,-83,-82,-82,-81,-80,-80,-79,-78,-77,-77,-76,-75,-74,-73,-72,-71,-70,-69,-68,-67,-66,-65,-64,-63,-62,-61,-60,-59,-57,-56,-55,-54,-52,-51,-50,-49,-47,-46,-45,-43,-42,-40,-39,-38,-36,-35,-33,-32,-30,-29,-27,-26,-24,-23,-21,-20,-18,-17,-15,-14,-12,-10,-9,-7,-6,-4,-3,-1,0};
	public static int numIntsShown=0;
	public static boolean exit=false,firstMM=false;
	public static boolean launched=false;
	public static Options opts;
	public static SavedGame s;
	public static long lastAdShown=0;
	public static final boolean isAmazon=false;
	public static final boolean debugBuild=false;
	public static boolean warpIn=false;
	public static int adH=0;
	public static float tribW,tribH,planetX,planetY,sattX,sattY;
	public static boolean isAndroid;
	
	public GV(){
	}
	public static void doSizes () {
		
		isAndroid = (Gdx.app.getType()==Application.ApplicationType.Android);
		//isAndroid=true;
		
		  GV.w = Gdx.graphics.getWidth();
	  	  GV.h = Gdx.graphics.getHeight()-adH;
	  	  GV.trueH = Gdx.graphics.getHeight();
	 	  GV.aspRatW=GV.w/750.0f;
	  	  GV.aspRatH=GV.h/1334.0f;
	  	  if (GV.aspRatW<GV.aspRatH) {
	  		  GV.aspRatL=GV.aspRatW; 
	  		  GV.aspRatM=GV.aspRatH;
	  	  }
	  	  else {
	  		  GV.aspRatL=GV.aspRatH;
	  		  GV.aspRatM=GV.aspRatW;
	  	  }
	  	 
	  	  planetX=300.0f*GV.aspRatW;
	  	  planetY=500.0f*GV.aspRatH;
	  	  sattX=20.0f*GV.aspRatW;
	  	  sattY=800.0f*GV.aspRatH;
	  	 
	}
	
	
	public static void doNewGame () {
		int p,t;
		
		GV.s.futureUseBools = new boolean [1000];
		GV.s.futureUseFloats = new float [1000];
		GV.s.futureUseInts = new int [1000];
		GV.s.futureUseLongs = new int [1000];
		GV.s.futureUseStrings = new String [1000];
		
		GV.s.paused=false;
		GV.s.pauseTime=0;
		
		s.boardW=8;
		s.boardH=13;
		s.numTribTypes=4;
		s.score=0;
		s.level=1;
		GV.s.totalTribTrans=0;
		GV.s.gameOver=false;
		GV.s.levelThresholds = new int [1000];
		for (p=0;p<1000;p++) GV.s.levelThresholds[p] = (int) ((p+1)*100) * ((p+1)*3);
		s.board = new int [s.boardW] [s.boardH];
		s.boardIsExp = new boolean [s.boardW] [s.boardH];
		s.boardState = new int [s.boardW] [s.boardH];
		s.boardStateTimer = new float [s.boardW] [s.boardH];
		s.boardX = new float [s.boardW] [s.boardH];
		s.boardY = new float [s.boardW] [s.boardH];
		s.boardSpeed = new float [s.boardW] [s.boardH];
		s.spawnFade = new float [s.boardW*3] [s.boardH*3];
		s.spawnRow = new int [s.boardW];
		s.spawnRowIsExp = new boolean [s.boardW];
		s.spawnRowState = new int [s.boardW];
		s.spawnRowStateTimer = new float [s.boardW];
		s.spawnRowFade = new float [s.boardW];

		s.spawnInterval = 1000;
		s.wiggleInt = 1500;
		s.shutInt = 500;
		GV.s.transportInt = 1350;
		s.spawnRowTimer=(int) (System.currentTimeMillis()-s.spawnInterval);
		s.spawnRowI=0;
		for (p=0;p<s.boardW;p++) for (t=0;t<s.boardH;t++)  s.board[p][t]=s.numTribTypes+1;
		for (p=0;p<s.boardW;p++) s.spawnRow[p] = s.numTribTypes+1;
		s.neededForMatch=2;
	
		
		GV.s.tribsToPopX = new int[s.boardW*s.boardH];
		GV.s.tribsToPopY = new int[s.boardW*s.boardH];
		
		GV.s.laserW = new float [GV.s.boardW*GV.s.boardH];
		GV.s.laserX = new float [GV.s.boardW*GV.s.boardH];
		GV.s.laserD = new boolean [GV.s.boardW*GV.s.boardH];
		
		if (GV.s.gameType==1) {
			float scoreBarH = GV.h/40.0f;	
			float h=GV.h-scoreBarH;

			tribW=GV.w/GV.s.boardW;
			tribH=h/(GV.s.boardH);

			if (tribW<tribH) tribH=tribW; else tribW=tribH;
	
			for (p=0;p<GV.s.boardW;p++) {
			GV.s.board[p][GV.s.boardH-1]=MathUtils.random(0,GV.s.numTribTypes-1);
			GV.s.boardStateTimer[p][GV.s.boardH-1]=0.0f;
			GV.s.boardState[p][GV.s.boardH-1]=1;
			GV.s.boardX[p][GV.s.boardH-1]=(p*GV.tribW);
			GV.s.boardY[p][GV.s.boardH-1]=((GV.s.boardH)*GV.tribH);
			GV.s.boardSpeed[p][GV.s.boardH-1]=10*GV.aspRatL;
		}
		}
	
		
		
		
	}
	
}
