package com.weiwei.rollingfruit;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import com.weiwei.rollingfruit.SceneManager.SceneType;

public class SplashScene extends BaseScene {
	private int tileCount;
	
	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));	
		tileCount = resourceManager.splashTextureRegion.getTileCount();
		AnimatedSprite splash = new AnimatedSprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.splashTextureRegion, vertexBufferObjectManager) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
            {
               super.preDraw(pGLState, pCamera);
               pGLState.enableDither();
            }
        };
        splash.animate(100); 
        splash.setScale(1f);
        attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
		activity.finish();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {

	}

	public int getTileCount(){
		return tileCount;
	}
}
