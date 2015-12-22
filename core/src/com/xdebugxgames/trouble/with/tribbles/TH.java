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
	
	 public static final int numTextures = 11, numAnimations = 0;	
	 public static final int numMusic=0;
	 public static final int numSfxs=0;
	 public static TextureRegion texts[],strips[];
	 public static float textsW[],textsH[],stripsW[],stripsH[],animW[],animH[];
	 public static BitmapFont bf;
     public static Music loopingMusic[];
	 public static Sound sfxs[];
	 public static final int ImusicMM=0;
	 public static final String loopingMusicFN[] = {"showMoves.mp3"};
	 public static final String sfxsFN[] = {};
	 public static final String animFN[] = {};
	 public static final int ItxtShip=0,ItxtTurret=1;
	 public static final int ItxtAsteroids=0,ItxtComets=4,ItxtUfo=8,ItxtPlanet=9,ItxtSatt=10;
	 public static final String textsFN[]={"Asteroid1","Asteroid2","Asteroid3","Asteroid4","Comet1","Comet2","Comet3","Comet4","ufo","planet","satt"};
	 public static final String stripsFN[]={"20","30","40"};
	 public static final int menuItems[] ={};
	 public static final int multiples[] ={11,12,13};
	 
	 public static Animation Anims[];
	 public static final float animationLength [] = {0.4f,1.7f};
	 public static float animationDuration[] = {0.4f,1.7f};
	 
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
