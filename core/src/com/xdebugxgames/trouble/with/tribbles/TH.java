package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class TH {
	public static AssetManager manager;
	public static TextureAtlas atlasGame,atlasMenu;
	private static Array <AtlasRegion>  aa;
	
	 public static final int numTextures = 25, numAnimations = 18, numMusic=1, numSfxs=20;
	 public static TextureRegion texts[],strips[];
	 public static float textsW[],textsH[],stripsW[],stripsH[],animW[],animH[];
	 public static BitmapFont bf;
     public static Music loopingMusic[];
	 public static Sound sfxs[];
	 public static final int ImusicMM=0;
	 public static final String loopingMusicFN[] = {"Pamgaea.mp3"};
	 public static final String sfxsFN[] = {"woot_01.mp3","woot_02.mp3","woot_03.mp3","woot_04.mp3","woot_05.mp3","woot_06.mp3","woot_07.mp3","woot_08.mp3","woot_09.mp3","woot_10.mp3","woot_11.mp3","woot_12.mp3","woot_13.mp3","woot_14.mp3","woot_15.mp3","woot_16.mp3","woot_17.mp3","woot_18.mp3","woot_19.mp3","transport.mp3"};
	 public static final int IsfxWoots=0,IsfxNumWoots=19,IsfxTransport=19;
	 public static final String animFN[] = {"BlueBall_blink","RedBall_blink","OrangeBall_blink","GreenBall_blink",
		 									"BlueBall_spawn","RedBall_spawn","OrangeBall_spawn","GreenBall_spawn",
		 									"BlueBall_wiggle","RedBall_wiggle","OrangeBall_wiggle","GreenBall_wiggle",
		 									"BlueBall_shuteyes","RedBall_shuteyes","OrangeBall_shuteyes","GreenBall_shuteyes",
		 									"transporter","laser"};
	 
	 
	 public static final int ItxtAsteroids=0,ItxtComets=5,ItxtUfo=9,ItxtPlanet=10,ItxtSatt=11,ItxtBallsIdle=12,ItxtCorBack=16,ItxtDoorL=17,ItxtDoorR=18,ItxtTop=19,ItxtBot=20,ItxtSideR=21,ItxtDoorF=22,ItxtWindow=23,ItxtPixel=24;
	 public static final int IanimBlink=0,IanimSpawn=4,IanimWiggle=8,IanimShutEyes=12,IanimTransport=16,IanimLaser=17;
	 public static final String textsFN[]={"Asteroid1","Asteroid2","Asteroid3","Asteroid4","Asteroid5","Comet1","Comet2","Comet3","Comet4","ufo","planet","satt","BlueBall_idle","RedBall_idle","OrangeBall_idle","GreenBall_idle",
		 								   "corridorBack","doorLeft","doorRight","top","bottom","sideRight","doorFrame","window","pixel"};
	 public static final String stripsFN[]={"20","30","40"};
	 public static final int menuItems[] ={};
	 public static final int multiples[] ={11,12,13};
	 
	 public static Animation Anims[];
	 public static final float animationLength [] = {0.25f,0.25f,0.25f,0.25f,//blink
		 											 1.0f,1.0f,1.0f,1.0f,//spawn
		 											 1.5f,1.5f,1.5f,1.5f,//wiggle
		 											 0.5f,0.5f,0.5f,0.5f,//shuteyes
		 											 1.35f,1.35f,1.35f,1.35f, //transporter
		 											 0.375f //laser
	 };
	 public static float animationDuration[];
	 
	 public TH(){
 		
 }
	 
	 public static void sizes () {	
		 	int p;	
		 	for (p=0;p<numTextures;p++) {
		 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatL;
		 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatL;
		 	}
		 	
		 	textsW[TH.ItxtSatt]=texts[TH.ItxtSatt].getRegionWidth()*GV.aspRatL*2.5f;
		 	textsH[TH.ItxtSatt]=texts[TH.ItxtSatt].getRegionHeight()*GV.aspRatL*2.5f;
		 	
		 	for (p=0;p<numAnimations;p++) {
		 		animW[p]=Anims[p].getKeyFrame(0).getRegionWidth()*GV.aspRatL;
		 		animH[p]=Anims[p].getKeyFrame(0).getRegionHeight()*GV.aspRatL;
		 	}
		 	
		 	p=TH.IanimLaser;
	 		animW[p]=Anims[p].getKeyFrame(0).getRegionWidth()*GV.aspRatW;
	 		animH[p]=Anims[p].getKeyFrame(0).getRegionHeight()*GV.aspRatW;
		 	

		 	for (p=0;p<30;p++) {
		 	stripsW[p]=strips[p].getRegionWidth()*GV.aspRatW;
		 	stripsH[p]=strips[p].getRegionHeight()*GV.aspRatW;
		 	}	
 	
		 	for (p=ItxtCorBack;p<ItxtWindow+1;p++) {
		 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
		 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatW;	 		
		 	}
		 	/*
		 	p=ItxtCorBack;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatH;

		 	p=ItxtDoorL;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatH;

		 	p=ItxtDoorR;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatH;

		 	p=ItxtDoorF;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatH;

		 	p=ItxtSideR;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatM;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatL;
*/

		 	
	 }
	 public static void dispose () {
		 if (manager!=null) manager.dispose();
		 manager=null;
	 }
	 
	 public static void loadSounds () {
		 int p=0;
		 	TH.loopingMusic = new Music [TH.numMusic];
			TH.sfxs = new Sound [TH.numSfxs];

			for (p=0;p<TH.numMusic;p++) {
				TH.loopingMusic[p] = TH.manager.get(TH.loopingMusicFN[p],Music.class);
				TH.loopingMusic[p].setLooping(true);
			}
			// Sound.class
			for (p=0;p<TH.numSfxs;p++) TH.sfxs[p] = TH.manager.get(TH.sfxsFN[p],Sound.class);
		
	 }
	 
	 public static void loadGraphics () {
			
		 int p=0,t=0;
		 boolean menu;
		 bf=manager.get("david.fnt",BitmapFont.class);	
		 bf.getRegion().getTexture().setFilter(
	              TextureFilter.Linear,
	              TextureFilter.Linear);

	 	atlasGame = manager.get("game.pack", TextureAtlas.class);
//	 	atlasMenu = manager.get("menu.pack", TextureAtlas.class);
	 	
	 	texts=new TextureRegion [numTextures];
		textsW=new float [numTextures];
		textsH=new float [numTextures];
		animW=new float[numAnimations];
		animH=new float[numAnimations];
		Anims = new Animation [numAnimations];
		animationDuration = new float [numAnimations];
		strips = new TextureRegion [30];
		stripsW = new float [30];
		stripsH = new float [30];
		
	 
	 	for (p=0;p<numTextures;p++) {
	 		menu=false;
	 			 		
	 		//for (t=0;t<menuItems.length;t++) if (menuItems[t]==p) menu=true;
	 		
	 		if (menu==false) texts[p] = atlasGame.findRegion(textsFN[p]); else texts[p] = atlasMenu.findRegion(textsFN[p]);
	 		
	 		texts[p].flip(false, true);
	 		
	 		}

	 	int i=0;
	 	for (p=0;p<stripsFN.length;p++) {
		 	aa=atlasGame.findRegions(stripsFN[p]);
			for (t=0;t<aa.size;t++) {
				strips[i]=aa.get(t);
				strips[i++].flip(false, true);
			}
	 	}
	 	
		for (p=0;p<numAnimations;p++) {
		 	aa=atlasGame.findRegions(animFN[p]);
			for (t=0;t<aa.size;t++) {
				aa.get(t).flip(false, true);
			}
			animationDuration[p]=(float) (animationLength[p] / (float) (aa.size));
			Anims[p] = new Animation (animationDuration[p],aa);
			
		}
		
		if (aa!=null) {
		aa.clear();
		aa=null;
		}
		
		
	 }
	 
}
