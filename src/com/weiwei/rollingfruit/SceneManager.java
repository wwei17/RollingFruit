package com.weiwei.rollingfruit;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;


public class SceneManager {
	private static final SceneManager INSTANCE = new SceneManager();
	
	public enum SceneType {SCENE_SPLASH, SCENE_MENU, SCENE_GAME};
	
	public static SceneManager getInstance() {
        return INSTANCE;
    }
	
	private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    
    private BaseScene currentScene;
    
    public void setScene(SceneType sceneType) {
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
    
    private void setScene(BaseScene scene) {
        ResourceManager.getInstance().activity.getEngine().setScene(scene);
        currentScene = scene;
    } 

	private BaseScene createMenuScene() {
		menuScene = new MenuScene();
		
		
		return menuScene;
	}

	private BaseScene createGameScene() {
		return null;
	}

	public BaseScene createSplashScene() {
		splashScene = new SplashScene();
        return splashScene;
	}
}
