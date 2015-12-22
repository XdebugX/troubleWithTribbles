package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.math.MathUtils;



public class Background {
	
	public float stripSegH,stripY[],planetX,planetY,sattX,sattY;
	public int p,numStrips,stripNum40[],stripNum20[],stripNum10[];
	public boolean isPlanet,isSatt;
	public long nextComet,nextAsteroid,nextUFO;
	
	
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

			nextComet = MathUtils.random(10000);
			nextAsteroid = MathUtils.random(10000);
			nextUFO = MathUtils.random(10000);

	}		

}
