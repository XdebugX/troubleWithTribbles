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
	
	 public static final int numTextures = 64, numAnimations = 17, numMusic=1, numSfxs=21;
	 public static TextureRegion texts[],strips[];
	 public static float textsW[],textsH[],stripsW[],stripsH[],animW[],animH[];
	 public static BitmapFont bf;
     public static Music loopingMusic[];
	 public static Sound sfxs[];
	 public static final int ImusicMM=0;
	 public static final String loopingMusicFN[] = {"Pamgaea.mp3"};
	 public static final String sfxsFN[] = {"woot_01.mp3","woot_02.mp3","woot_03.mp3","woot_04.mp3","woot_05.mp3","woot_06.mp3","woot_07.mp3","woot_08.mp3","woot_09.mp3","woot_10.mp3","woot_11.mp3","woot_12.mp3","woot_13.mp3","woot_14.mp3","woot_15.mp3","woot_16.mp3","woot_17.mp3","woot_18.mp3","woot_19.mp3","transport.mp3","ut_oh.mp3"};
	 public static final int IsfxWoots=0,IsfxNumWoots=19,IsfxTransport=19,IsfxUtOh=20;
	 public static final String animFN[] = {"BlueBall_blink","RedBall_blink","OrangeBall_blink","GreenBall_blink",
		 									"BlueBall_spawn","RedBall_spawn","OrangeBall_spawn","GreenBall_spawn",
		 									"BlueBall_wiggle","RedBall_wiggle","OrangeBall_wiggle","GreenBall_wiggle",
		 									"BlueBall_shuteyes","RedBall_shuteyes","OrangeBall_shuteyes","GreenBall_shuteyes",
		 									"transporter"};
	 
	 
	 public static final int ItxtBallsIdle=0,ItxtCorBack=4,ItxtDoorL=5,ItxtDoorR=6,ItxtTop=7,ItxtBot=8,ItxtSideR=9,ItxtDoorF=10,ItxtWindow=11,ItxtScore=12,ItxtCounter=13,Itxtlevel=14,ItxtlevelClear=15,ItxtPaused=16,ItxtN=17,ItxtPixel=27;
	 public static final int startMenus=28;
	 public static final int IanimBlink=0,IanimSpawn=4,IanimWiggle=8,IanimShutEyes=12,IanimTransport=16;
	 public static final String textsFN[]={"BlueBall_idle","RedBall_idle","OrangeBall_idle","GreenBall_idle",
		 								   "corridorBack","doorLeft","doorRight","top","bottom","sideRight","doorFrame","window","score","counter","level","levelClear","paused","n0","n1","n2","n3","n4","n5","n6","n7","n8","n9","pixel"};
	 
	 public static final String textsMFN [] = {"achv","back","btnBlue","btnGreen","btnRed","cont","credits","leader","login","logout","musicOff","musicOn","new","no","opt","optionsTitle","our","share","sndOff","sndOn","sure","yes","planet","satt","logo","startBtn","startDoorL","startDoorR","startWall","gameplayType","classic","ctl","done","finalScore","totalTribbles","gameOver"};
	 public static final int ItxtAch=startMenus,ItxtBack=startMenus+1,ItxtBtnBlu=startMenus+2,ItxtBtnGrn=startMenus+3,ItxtBtnRed=startMenus+4,ItxtCont=startMenus+5,ItxtCred=startMenus+6,ItxtLdr=startMenus+7,ItxtLogin=startMenus+8,ItxtLogout=startMenus+9,ItxtMusOff=startMenus+10,ItxtMusOn=startMenus+11,ItxtNew=startMenus+12,ItxtNo=startMenus+13,ItxtOpt=startMenus+14,ItxtOptT=startMenus+15,ItxtOur=startMenus+16,ItxtShare=startMenus+17,ItxtSoundOff=startMenus+18,ItxtSoundOn=startMenus+19,ItxtSure=startMenus+20,ItxtYes=startMenus+21,ItxtPlanet=startMenus+22,ItxtSatt=startMenus+23,ItxtLogo=startMenus+24,ItxtStartBtn=startMenus+25,ItxtStartDoorL=startMenus+26,ItxtStartDoorR=startMenus+27,ItxtStartWall=startMenus+28,ItxtGameType=startMenus+29,ItxtClassic=startMenus+30,ItxtCTL=startMenus+31,ItxtDone=startMenus+32,ItxtFinalScore=startMenus+33,ItxtTotalTrib=startMenus+34,ItxtGameOver=startMenus+35;
	 public static final String stripsFN[]={"20","30","40"};
	 public static final int menuItems[] ={};
	 public static final int multiples[] ={11,12,13};
	 
	 public static Animation Anims[];
	 public static final float animationLength [] = {0.25f,0.25f,0.25f,0.25f,//blink
		 											 1.0f,1.0f,1.0f,1.0f,//spawn
		 											 1.5f,1.5f,1.5f,1.5f,//wiggle
		 											 0.5f,0.5f,0.5f,0.5f,//shuteyes
		 											 1.35f,1.35f,1.35f,1.35f //transporter
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
		 	
		 	
		 	for (p=0;p<numAnimations;p++) {
		 		animW[p]=Anims[p].getKeyFrame(0).getRegionWidth()*GV.aspRatL;
		 		animH[p]=Anims[p].getKeyFrame(0).getRegionHeight()*GV.aspRatL;
		 	}
		 		

		 	p=TH.ItxtBack;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatW;	 		

		 	p=TH.ItxtStartBtn;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatW;	 		

		 	p=TH.ItxtStartWall;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatW;	 		

		 	p=TH.ItxtStartDoorL;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatW;	 		

		 	p=TH.ItxtStartDoorR;
	 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
	 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatW;	 		

	 		
		 	for (p=ItxtCorBack;p<ItxtPixel;p++) {
		 		textsW[p]=texts[p].getRegionWidth()*GV.aspRatW;
		 		textsH[p]=texts[p].getRegionHeight()*GV.aspRatW;	 		
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

			for (p=0;p<TH.numMusic;p++) {
				TH.loopingMusic[p] = TH.manager.get(TH.loopingMusicFN[p],Music.class);
				TH.loopingMusic[p].setLooping(true);
			}
			// Sound.class
			for (p=0;p<TH.numSfxs;p++) TH.sfxs[p] = TH.manager.get(TH.sfxsFN[p],Sound.class);
		
	 }
	 
	 public static void loadGraphics () {
			
		 int p=0,t=0;
		 bf=manager.get("david.fnt",BitmapFont.class);	
		 bf.getRegion().getTexture().setFilter(
	              TextureFilter.Linear,
	              TextureFilter.Linear);

	 	atlasGame = manager.get("game.pack", TextureAtlas.class);
	 	atlasMenu = manager.get("menu.pack", TextureAtlas.class);
	 	
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
	 		if (p<startMenus) texts[p] = atlasGame.findRegion(textsFN[p]); else texts[p] = atlasMenu.findRegion(textsMFN[p-startMenus]);
	 		texts[p].flip(false, true);
	 		
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
