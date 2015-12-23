package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.math.MathUtils;



public class Background {
	
	public float stripSegH,stripY[],planetX,planetY,sattX,sattY,asteroidX,asteroidY,asteroidSpeed,cometX,cometY,cometSpeed;
	public int p,numStrips,stripNum40[],stripNum20[],stripNum10[],asteroidType,cometType;
	public boolean isPlanet,isSatt,asteroidDX,asteroidDY,asteroidHV,asteroidOnScreen,cometDX,cometOnScreen;
	public long nextComet,nextAsteroid,nextUFO;
	public static final int asteroidInterval=10000;
	public static final float cometSlope[] = {0.9f,1.25f,0.5f,1.0f};
	
	
	public Background () {
		 	stripSegH=TH.stripsH[0]+TH.stripsH[10]+TH.stripsH[20];
			numStrips=(int) (GV.h/(stripSegH))+2;
			stripNum40 = new int [numStrips];
			stripNum20 = new int [numStrips];
			stripNum10 = new int [numStrips];
			stripY = new float [numStrips];
			for (p=0;p<numStrips;p++) {
				stripNum40[p]=MathUtils.random(0,9);
				stripNum20[p]=MathUtils.random(0,9);
				stripNum10[p]=MathUtils.random(0,9);
				stripY[p]=GV.h-((float) (p)*stripSegH);
			}
			
			isPlanet = MathUtils.randomBoolean();
			if (isPlanet) {
				planetX=MathUtils.random(0.0f,GV.w);
				planetY=MathUtils.random(0.0f,GV.h);
			}

			isSatt = MathUtils.randomBoolean();
			if (isSatt) {
				sattX=MathUtils.random(0.0f,GV.w);
				sattY=MathUtils.random(0.0f,GV.h);
			}

			nextComet = System.currentTimeMillis()+MathUtils.random(asteroidInterval);
			nextAsteroid = System.currentTimeMillis()+MathUtils.random(asteroidInterval);
			nextUFO = System.currentTimeMillis()+MathUtils.random(asteroidInterval);
			
			asteroidOnScreen=false;
			cometOnScreen=false;

	}		

}
