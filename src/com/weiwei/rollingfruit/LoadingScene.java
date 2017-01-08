package com.weiwei.rollingfruit;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import com.weiwei.rollingfruit.SceneManager.SceneType;


public class LoadingScene extends BaseScene{

	@Override
	public void createScene() {
		setBackground(new Background(Color.WHITE));	
		AnimatedSprite splash = new AnimatedSprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.splashTextureRegion, vertexBufferObjectManager) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
            {
               super.preDraw(pGLState, pCamera);
               pGLState.enableDither();
            }
        };
        splash.animate(50); 
        splash.setScale(1f);
        attachChild(splash);
		attachChild(new Text(SCREEN_WIDTH/2, SCREEN_HEIGHT/2-200, resourceManager.font, "Loading...", vertexBufferObjectManager));
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
