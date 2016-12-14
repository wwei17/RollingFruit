package com.weiwei.rollingfruit;

import java.util.Stack;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;


public class SceneManager {
	private static final SceneManager INSTANCE = new SceneManager();
	public BaseGameActivity activity;
	
	public enum SceneType {SCENE_SPLASH, SCENE_MENU, SCENE_GAME, SCENE_LOADING};
	
	public void prepare(BaseGameActivity  activity) {
		INSTANCE.activity = activity;
	}
	
	public static SceneManager getInstance() {
        return INSTANCE;
    }
	
	private BaseScene splashScene;
	private BaseScene loadingScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene currentScene;
    
    public BaseScene getCurrentScene(){
    	return currentScene;
    }
    private Stack<BaseScene> sceneStack = new Stack<BaseScene>();
    
    public void setScene(SceneType sceneType) {
    	sceneStack.push(currentScene);
        switch (sceneType) {
        case SCENE_MENU:
            setScene(createMenuScene());
            break;
        case SCENE_GAME:
            setScene(createGameScene());
            break;
        case SCENE_SPLASH:
            setScene(createSplashScene());
            break;
        }

    }
    
    private void setScene(final BaseScene scene) {
    	Log.d("SCENE CHANGE", scene.getSceneType().name());
    	final Engine eng = activity.getEngine();
    	eng.setScene(createLoadingScene());
    	eng.registerUpdateHandler(new TimerHandler(MainActivity.LOADING_TIME, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	currentScene = scene;
            	eng.setScene(currentScene);
            }
        }));
    } 
    
    public void loadLastScene(){
    	currentScene = sceneStack.pop();
    	setScene(currentScene);
    }
    
	private BaseScene createLoadingScene() {
		if(loadingScene == null) loadingScene = new LoadingScene();
		return loadingScene;
	}
    
	private BaseScene createMenuScene() {
		ResourceManager.getInstance().loadMenuResources();
		if(menuScene == null) menuScene = new MenuScene();
		return menuScene;
	}

	private BaseScene createGameScene() {
		ResourceManager.getInstance().loadGameResources();
		gameScene = new GameScene();
		return gameScene;
	}

	public BaseScene createSplashScene() {
		ResourceManager.getInstance().loadSplashResources();
		splashScene = new SplashScene();
        return splashScene;
	}


}
