package com.xdebugxgames.trouble.with.tribbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SplashScreen implements Screen {



	Tribbles game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture texture;
	private TextureRegion TxRegion,txrProg;
	float progress,progress2,txtW,txtH;
	private static boolean done,done2;


	// constructor to keep a reference to the main Game class
	public SplashScreen(Tribbles game) {
		this.game = game;
		done=false;
		done2=false;

	}

	@Override
	public void render(float delta) {
		
		if (done2) {
			//game.setScreen(game.mmScreen);
			game.setScreen (game.startScreen);
		} else {

		// update and draw stuff
			
		if(TH.manager.update()) {

			if (!done) {

				TH.loadSounds();
				TH.loadGraphics();
				TH.sizes();
					
				done=true;
			} 

		}

		
		progress = TH.manager.getProgress();
		
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  
		batch.begin();
		batch.draw(TxRegion, (GV.w-txtW)/2,(GV.h-txtH)/2,txtW,txtH);
		
		progress2+=progress*delta;
		if (progress2>progress) progress2=progress;
		if (progress2>=1.0f) done2=true;
		
		batch.draw(txrProg, GV.w/10.0f,  GV.h - ((GV.h/20.f)*3.0f), (GV.w-(GV.w/10.0f*2.0f))*progress2, GV.h/20.0f);
		
		batch.end();
		
		}
				
	}


	@Override
	public void resize(int width, int height) {
	}


	@Override
	public void show() {
		int p=0;
		GV.doSizes();

		camera = new OrthographicCamera(GV.w, GV.trueH);
		camera.setToOrtho(true, GV.w, GV.trueH);
		camera.position.set(GV.w / 2, GV.trueH / 2, 0);
		camera.update();

		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		texture = new Texture(Gdx.files.internal("debugLogo.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		TxRegion = new TextureRegion(texture, 0, 0, 1024, 460);
		TxRegion.flip(false, true);
		
		txrProg = new TextureRegion(texture, 0, 491, 480, 511);
		txrProg.flip(false, true);
		

		TH.manager = new AssetManager();
	
		txtW=GV.w;
		txtH=(GV.w/1024.0f) * 460.0f;
		
		for (p=0;p<TH.numMusic;p++) TH.manager.load(TH.loopingMusicFN[p], Music.class);
		for (p=0;p<TH.numSfxs;p++) TH.manager.load(TH.sfxsFN[p],Sound.class);
		TH.manager.load("menu.pack", TextureAtlas.class);
		TH.manager.load("game.pack", TextureAtlas.class);
		
		BitmapFontParameter param = new BitmapFontParameter();
		param.flip = true;
		TH.manager.load("david.fnt", BitmapFont.class, param);
				

		done=false;
		done2=false;

		
	}


	@Override
	public void hide() {
		// called when current screen changes from this to a different screen
	}


	@Override
	public void pause() {
	}


	@Override
	public void resume() {
	}


	@Override
	public void dispose() {
		if (batch!=null) batch.dispose();
		if (texture!=null) texture.dispose();
	}
}
