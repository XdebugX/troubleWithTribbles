package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class GV {
	public static float w,h,aspRatW,aspRatH,aspRatL;
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
	public static int adH=0;
	
	public GV(){
	}
	public static void doSizes () {
		  GV.w = Gdx.graphics.getWidth();
	  	  GV.h = Gdx.graphics.getHeight();
	 	  GV.aspRatW=GV.w/480.0f;
	  	  GV.aspRatH=GV.h/640.0f;
	  	  if (GV.aspRatW<GV.aspRatH) GV.aspRatL=GV.aspRatW; else GV.aspRatL=GV.aspRatH;	
	}
	
	public static void spawnAsteroid (Background b) {
		b.asteroidHV=MathUtils.randomBoolean();
		b.asteroidDX=MathUtils.randomBoolean();
		b.asteroidDY=MathUtils.randomBoolean();
		b.asteroidSpeed = MathUtils.random (GV.w/15.0f,GV.w/5.0f);
		b.asteroidType = MathUtils.random (0,4);
		
		if (b.asteroidHV) {
			b.asteroidY=MathUtils.random(GV.h);
			if (b.asteroidDX) b.asteroidX=0.0f-TH.textsW[TH.ItxtAsteroids+b.asteroidType]; else b.asteroidX=GV.w;
		}
		else {
			b.asteroidX=MathUtils.random(GV.w);
			if (b.asteroidDY) b.asteroidY=0.0f-TH.textsH[TH.ItxtAsteroids+b.asteroidType]; else b.asteroidY=GV.h;
		}
		
		b.asteroidOnScreen=true;
		
		
	}
	
	public static void spawnComet (Background b) {
		b.cometDX = MathUtils.randomBoolean();
		b.cometSpeed = MathUtils.random(GV.w/15.0f,GV.w/5.0f);
		b.cometType = MathUtils.random(0,3);
		
		if (b.cometDX) b.cometX=0.0f-TH.textsW[TH.ItxtComets+b.cometType]; else b.cometX=GV.w;
		b.cometY=MathUtils.random(GV.h);
		b.cometOnScreen=true;
	
	}
	
	public static void moveAsteroid (Background b, float delta) {
		if (b.asteroidHV) {
			if (b.asteroidDX) {
				b.asteroidX+=b.asteroidSpeed*delta;
				if (b.asteroidX>GV.w) {
					b.asteroidOnScreen=false;
					b.nextAsteroid = System.currentTimeMillis()+MathUtils.random(Background.asteroidInterval); 
				}
			} else {
				b.asteroidX-=b.asteroidSpeed*delta;
				if (b.asteroidX<0-TH.textsW[TH.ItxtAsteroids+b.asteroidType]) {
					b.asteroidOnScreen=false;
					b.nextAsteroid = System.currentTimeMillis()+MathUtils.random(Background.asteroidInterval); 						
				}
			}
		} else {
			if (b.asteroidDY) {
				b.asteroidY+=b.asteroidSpeed*delta;
				if (b.asteroidY>GV.h) {
					b.asteroidOnScreen=false;
					b.nextAsteroid = System.currentTimeMillis()+MathUtils.random(Background.asteroidInterval);
				}
				}
			else{
				b.asteroidY-=b.asteroidSpeed*delta;
				if (b.asteroidY<0-TH.textsH[TH.ItxtAsteroids+b.asteroidType]) {
					b.asteroidOnScreen=false;
					b.nextAsteroid = System.currentTimeMillis()+MathUtils.random(Background.asteroidInterval);
				}
			}
		}
			
	}
	
	public static void moveComet (Background b, float delta) {
		if (b.cometDX) {
			b.cometX+=b.cometSpeed*delta;
			if (b.cometX>GV.w) {
				b.cometOnScreen=false;
				b.nextComet = System.currentTimeMillis()+MathUtils.random(Background.asteroidInterval);
			}
			}
		else {
			b.cometX-=b.cometSpeed*delta;
			if (b.cometX<0-(TH.textsW[TH.ItxtComets+b.cometType])) {
				b.cometOnScreen=false;
				b.nextComet = System.currentTimeMillis()+MathUtils.random(Background.asteroidInterval);
			}
		}
		
		b.cometY+=b.cometSpeed*b.cometSlope[b.cometType]*delta;
	
	}
	
}
