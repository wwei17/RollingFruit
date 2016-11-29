package com.weiwei.rollingfruit;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;




public class ResourceManager {
	private static final ResourceManager INSTANCE = new ResourceManager();
	public BaseGameActivity activity;
	
	public static ResourceManager getInstance() {
        return INSTANCE;
    }
	
	public void prepare(BaseGameActivity activity) {
        INSTANCE.activity = activity;
    }
	
	private BitmapTextureAtlas splashTextureAtlas;
	public TiledTextureRegion splashTextureRegion;
	
	public void loadSplashResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(splashTextureAtlas, activity, "logo.png", 0, 0, 1, 3);
        splashTextureAtlas.load();
    }
	
	public void unloadSplashResources() {
		splashTextureAtlas.unload();
        splashTextureRegion = null;
    }
	
	private BitmapTextureAtlas MenuTextureAtlas;
	public ITextureRegion backgroundTextureRegion;
	public ITextureRegion groundTextureRegion;
	public ITextureRegion entryTextureRegion;
	public void loadMenuResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		MenuTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024);
		backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuTextureAtlas, activity, "background.png", 0, 0);
		groundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuTextureAtlas, activity, "ground.png", 512, 0);
		entryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(MenuTextureAtlas, activity, "entry.png", 0,512);
		MenuTextureAtlas.load();
	}
	
	public void unloadMenuResources() {
		MenuTextureAtlas.unload();
		backgroundTextureRegion = null;
		
	}
}
