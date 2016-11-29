package com.weiwei.rollingfruit;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.weiwei.rollingfruit.SceneManager.SceneType;

public class SplashScene extends BaseScene {

	@Override
	public void createScene() {
		AnimatedSprite splash = new AnimatedSprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.splashTextureRegion, vertexBufferObjectManager) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
            {
               super.preDraw(pGLState, pCamera);
               pGLState.enableDither();
            }
        };
        splash.animate(200); 
        splash.setScale(3f);
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

}
