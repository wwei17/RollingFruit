package com.weiwei.rollingfruit;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;


public class ResourceManager {
	private static final ResourceManager INSTANCE = new ResourceManager();
	public BaseGameActivity activity;
	
	public static ResourceManager getInstance() {
        return INSTANCE;
    }
	
	private BitmapTextureAtlas loadingTextureAtlas;
	public TiledTextureRegion loadingTextureRegion;
	
	public void prepare(BaseGameActivity activity) {
        INSTANCE.activity = activity;
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/loading/");
        loadingTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        loadingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loadingTextureAtlas, activity, "loading.png", 0, 0, 1, 3);
        loadingTextureAtlas.load();
    }
	
	private BitmapTextureAtlas splashTextureAtlas;
	public TiledTextureRegion splashTextureRegion;
	public Font font;
	public Font fontTinyWhite;
	public Font fontTinyBlack;
	public Font fontBigWhite;
	public void loadSplashResources() {
		if(splashTextureAtlas != null) return;
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(splashTextureAtlas, activity, "logo.png", 0, 0, 1, 3);
        splashTextureAtlas.load();
        
        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        final ITexture whiteBigFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        final ITexture blackTinyFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        final ITexture whiteTinyFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        fontTinyBlack = FontFactory.createStrokeFromAsset(activity.getFontManager(), blackTinyFontTexture, activity.getAssets(), "font.ttf", 20, true, Color.WHITE, 2, Color.BLACK);
        fontTinyWhite = FontFactory.createStrokeFromAsset(activity.getFontManager(), whiteTinyFontTexture, activity.getAssets(), "font.ttf", 20, true, Color.BLACK, 2, Color.WHITE);
        fontBigWhite = FontFactory.createStrokeFromAsset(activity.getFontManager(), whiteBigFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.BLACK, 2, Color.WHITE);
        
        font.load();
        fontBigWhite.load();
        fontTinyBlack.load();
        fontTinyWhite.load();
    }
	public void unloadSplashResources() {
		splashTextureAtlas.unload();
        splashTextureRegion = null;
    }
	
	private BitmapTextureAtlas menuTextureAtlas;
	public ITextureRegion backgroundTextureRegion;
	public ITextureRegion groundTextureRegion;
	public ITextureRegion entryTextureRegion;
	public void loadMenuResources() {
		if(menuTextureAtlas != null) return ;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024);
		backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "background.png", 0, 0);
		groundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "ground.png", 512, 0);
		entryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "entry.png", 0,512);
		menuTextureAtlas.load();
	}
	public void unloadMenuResources() {
		menuTextureAtlas.unload();
		backgroundTextureRegion = groundTextureRegion = entryTextureRegion = null;
	}

	private BitmapTextureAtlas gameTextureAtlas;
	public ITextureRegion gameBackgroundTextureRegion;
	public ITextureRegion btn1TextureRegion;
	public ITextureRegion emptyTextureRegion;
//	public TiledTextureRegion appleTextureRegion;
//	public TiledTextureRegion bananaTextureRegion;
//	public TiledTextureRegion strawberryTextureRegion;
	public ITextureRegion appleTextureRegion;
	public ITextureRegion bananaTextureRegion;
	public ITextureRegion strawberryTextureRegion;
	public ITextureRegion backTextureRegion;
	public ITextureRegion pauseTextureRegion;
	public ITextureRegion playTextureRegion;
	public ITextureRegion resetTextureRegion;
	public ITextureRegion popupTextureRegion;
	public TiledTextureRegion starTextureRegion;
	public void loadGameResources() {
		if(gameTextureAtlas != null) return ;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		gameTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 2048, 2048);
		gameBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_background.png", 0, 0);
		btn1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "btn1.png", 0, 1024);
		emptyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "empty.png", 1024, 0);
//		appleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "apple_old.png", 100, 1024, 1, 3);
//		bananaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "banana_old.png", 200, 1024, 1, 3);
//		strawberryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "strawberry_old.png", 300, 1024, 1, 3);
		appleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "apple.png", 100, 1024);
		bananaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "banana.png", 200, 1024);
		strawberryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "strawberry.png", 300, 1024);
		backTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "back.png", 400, 1024);
		pauseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pause.png", 450, 1024);
		resetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "reset.png", 500, 1024);
		playTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "play.png", 550, 1024);
		popupTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "popup.png", 0, 1024+100);
		starTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "star.png", 600, 1024, 2, 1);
		gameTextureAtlas.load();
	}
	public void unloadGameResources() {
		gameTextureAtlas.unload();
		gameBackgroundTextureRegion = null;
	}
}
