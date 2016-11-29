package com.weiwei.rollingfruit;

import java.util.ArrayList;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.weiwei.rollingfruit.SceneManager.SceneType;

public class MenuScene extends BaseScene implements IOnSceneTouchListener{

	private ParallaxBackground menuBackgound;
	private float distanceTravelled;
	private ArrayList<Sprite> entries;
	@Override
	public void createScene() {
		entries = new ArrayList<Sprite>(); 
		setOnSceneTouchListener(this);
		distanceTravelled = 0;
		menuBackgound = new ParallaxBackground(0,0,0);
		Sprite backgroundSprite = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.backgroundTextureRegion, vertexBufferObjectManager);
		backgroundSprite.setScale(1.67f);
		menuBackgound.attachParallaxEntity(new ParallaxEntity(5.0f, backgroundSprite));
		Sprite groundSprite = new Sprite(SCREEN_WIDTH/2, resourceManager.groundTextureRegion.getHeight()/2, resourceManager.groundTextureRegion, vertexBufferObjectManager);
		menuBackgound.attachParallaxEntity(new ParallaxEntity(10.0f, groundSprite));
		groundSprite.setScale(1.43f);
		setBackground(menuBackgound);
		
		Sprite sp = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.entryTextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				//Log.d("AreaTouched","123");
				return true;
			}
		};
		entries.add(sp);
		registerTouchArea(sp);
		setTouchAreaBindingOnActionDownEnabled(true);
		attachChild(sp);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

	private float prevX = 0;
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown()) {
			//Log.d("Action Down", "X,Y:"+pSceneTouchEvent.getX()+","+pSceneTouchEvent.getY());
	    }else if (pSceneTouchEvent.isActionMove()){
	    	distanceTravelled += pSceneTouchEvent.getX()-prevX;
	    	menuBackgound.setParallaxValue(distanceTravelled/10);
	    	for(Sprite sp : entries) sp.setX(sp.getX()+pSceneTouchEvent.getX()-prevX);
	    }
		prevX = pSceneTouchEvent.getX();
		return false;
	}

}
