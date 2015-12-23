package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
	
	 public static final int numTextures = 20, numAnimations = 32;	
	 public static final int numMusic=0;
	 public static final int numSfxs=0;
	 public static TextureRegion texts[],strips[];
	 public static float textsW[],textsH[],stripsW[],stripsH[],animW[],animH[];
	 public static BitmapFont bf;
     public static Music loopingMusic[];
	 public static Sound sfxs[];
	 public static final int ImusicMM=0;
	 public static final String loopingMusicFN[] = {""};
	 public static final String sfxsFN[] = {};
	 public static final String animFN[] = {"BlueBall_blink","RedBall_blink","YellowBall_blink","OrangeBall_blink","GreenBall_blink","PurpleBall_blink","BrownBall_blink","BlackBall_blink",
		 									"BlueBall_shuteyes","RedBall_shuteyes","YellowBall_shuteyes","OrangeBall_shuteyes","GreenBall_shuteyes","PurpleBall_shuteyes","BrownBall_shuteyes","BlackBall_shuteyes",
		 									"BlueBall_spawn","RedBall_spawn","YellowBall_spawn","OrangeBall_spawn","GreenBall_spawn","PurpleBall_spawn","BrownBall_spawn","BlackBall_spawn",
		 									"BlueBall_wiggle","RedBall_wiggle","YellowBall_wiggle","OrangeBall_wiggle","GreenBall_wiggle","PurpleBall_wiggle","BrownBall_wiggle","BlackBall_wiggle"};
	 public static final int ItxtShip=0,ItxtTurret=1;
	 public static final int ItxtAsteroids=0,ItxtComets=5,ItxtUfo=9,ItxtPlanet=10,ItxtSatt=11,ItxtBallsIdle=12;
	 public static final String textsFN[]={"Asteroid1","Asteroid2","Asteroid3","Asteroid4","Asteroid5","Comet1","Comet2","Comet3","Comet4","ufo","planet","satt","BlueBall_idle","RedBall_idle","YellowBall_idle","OrangeBall_idle","GreenBall_idle","PurpleBall_idle","BrownBall_idle","BlackBall_idle"};
	 public static final String stripsFN[]={"20","30","40"};
	 public static final int menuItems[] ={};
	 public static final int multiples[] ={11,12,13};
	 
	 public static Animation Anims[];
	 public static final float animationLength [] = {0.4f,0.4f,0.4f,0.4f,0.4f,0.4f,0.4f,0.4f, 
		 											 0.2f,0.2f,0.2f,0.2f,0.2f,0.2f,0.2f,0.2f,
		 											 0.3f,0.3f,0.3f,0.3f,0.3f,0.3f,0.3f,0.3f,
		 											 0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f};
	 public static float animationDuration[];
	 
	 public TH(){
 		
 }
	 
	 public static void sizes () {	
		 	int p;	
		 	for (p=0;p<numTextures;p++) {
		 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatL;
		 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatL;
		 	}
		 	
		 	for (p=0;p<numAnimations;p++) {
		 		animW[p]=Anims[p].getKeyFrame(0).getRegionWidth()*GV.aspRatL;
		 		animH[p]=Anims[p].getKeyFrame(0).getRegionHeight()*GV.aspRatL;
		 	}

		 	for (p=0;p<30;p++) {
		 	stripsW[p]=strips[p].getRegionWidth()*GV.aspRatW;
		 	stripsH[p]=strips[p].getRegionHeight()*GV.aspRatW;
		 	}	
 	
	 }
	 public static void dispose () {
		 if (manager!=null) manager.dispose();
		 manager=null;
	 }
	 
	 public static void loadSounds () {
		 int p=0;
		 	TH.loopingMusic = new Music [TH.numMusic];
			TH.sfxs = new Sound [TH.numSfxs];

			for (p=0;p<TH.numMusic;p++) TH.loopingMusic[p] = TH.manager.get(TH.loopingMusicFN[p],Music.class);
			// Sound.class
			for (p=0;p<TH.numSfxs;p++) TH.sfxs[p] = TH.manager.get(TH.sfxsFN[p],Sound.class);
		
	 }
	 
	 public static void loadGraphics () {
			
		 int p=0,t=0;
		 boolean menu;
		 bf=manager.get("david.fnt",BitmapFont.class);	
	 
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
