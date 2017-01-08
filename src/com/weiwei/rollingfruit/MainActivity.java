package com.weiwei.rollingfruit;

import java.io.IOException;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.util.Log;
import android.view.KeyEvent;
import com.weiwei.rollingfruit.SceneManager.SceneType;
import com.weiwei.rollingfruit.dataloader.UserFile;


public class MainActivity extends SimpleBaseGameActivity {

	
	private Camera camera;
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 720;
	public static final float LOADING_TIME = 1f;
	public static UserFile userFile = null;
	
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
	    return engineOptions;
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
}
