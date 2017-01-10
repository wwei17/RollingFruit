package com.weiwei.rollingfruit;

import java.io.IOException;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.weiwei.rollingfruit.SceneManager.SceneType;
import com.weiwei.rollingfruit.dataloader.UserFile;


public class MainActivity extends SimpleBaseGameActivity {

	
	private Camera camera;
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 720;
	public static final float LOADING_TIME = 1f;
	public static UserFile userFile = null;
	public static boolean Mute;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		try {
			userFile = new UserFile(this.getApplicationContext());
		} catch (Exception e) {
			userFile = null;
			Log.d("LOADING FAILED", "excpetion");
			e.printStackTrace();
		}
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
	    engineOptions.getAudioOptions().setNeedsSound(true).setNeedsMusic(true);
	    
	    Mute = true;
	    return engineOptions;
	}
	
	private AdView adView;
	@Override 
	protected void onSetContentView() {
		final FrameLayout frameLayout = new FrameLayout(this);
		final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                             FrameLayout.LayoutParams.MATCH_PARENT);
		this.adView = new AdView(this);
		this.adView.setAdSize(AdSize.SMART_BANNER);
		//this.adView.setAdUnitId("ca-app-pub-5107030736280288/1356011254");
		this.adView.setAdUnitId("ca-app-pub-5107030736280288/5675132854");
		
		final FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                             FrameLayout.LayoutParams.WRAP_CONTENT,
                                             Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
		
		AdRequest adRequest = new AdRequest.Builder().build();
		
		this.adView.loadAd(adRequest);
		
		this.mRenderSurfaceView = new RenderSurfaceView(this);
        this.mRenderSurfaceView.setRenderer(mEngine, this);
        
        final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams = new FrameLayout.LayoutParams(super.createSurfaceViewLayoutParams());
	
        frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
        frameLayout.addView(adView, adViewLayoutParams);
        
        this.setContentView(frameLayout, frameLayoutLayoutParams);
	}

	
	private ResourceManager resourceManager;
	@Override
	protected void onCreateResources() throws IOException {
		resourceManager = ResourceManager.getInstance();
		resourceManager.prepare(this);
		
	}

	private SceneManager sceneManager;
	@Override
	protected Scene onCreateScene() {
		sceneManager = SceneManager.getInstance();
		sceneManager.prepare(this);
		SplashScene splashScene = (SplashScene) sceneManager.createSplashScene();
		int tileCount = splashScene.getTileCount();
		Log.d("TILE COUNT",""+tileCount);
		mEngine.registerUpdateHandler(new TimerHandler((float) (tileCount*0.1), new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                sceneManager.setScene(SceneType.SCENE_MENU);
            }
        }));
		return splashScene;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}
	
	@Override
	protected void onPause() {
	    if (ResourceManager.getInstance().bgMusic!=null && ResourceManager.getInstance().bgMusic.isPlaying()) {
	    	ResourceManager.getInstance().bgMusic.pause();
	    }
	    super.onPause();
	}
	
	@Override
	protected void onResume() {
	    if (ResourceManager.getInstance().bgMusic!=null && !ResourceManager.getInstance().bgMusic.isPlaying() && !Mute) {
	    	ResourceManager.getInstance().bgMusic.play();
	    }
	    super.onResume();
	}
}
