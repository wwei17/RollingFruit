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

import com.weiwei.rollingfruit.SceneManager.SceneType;


public class MainActivity extends SimpleBaseGameActivity {

	
	private Camera camera;
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	@Override
	public EngineOptions onCreateEngineOptions() {
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
		resourceManager.loadSplashResources();
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                resourceManager.loadMenuResources();
                sceneManager.setScene(SceneType.SCENE_MENU);
                resourceManager.unloadSplashResources();
            }
        }));
		return sceneManager.createSplashScene();
	}

}
