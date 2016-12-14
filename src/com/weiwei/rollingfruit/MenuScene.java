package com.weiwei.rollingfruit;

import java.util.ArrayList;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.weiwei.rollingfruit.SceneManager.SceneType;
import com.weiwei.rollingfruit.XMLLevelLoader.GameLevelLoader;

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
		
		createEntries(20, 150);
		
	}
	
	private void createEntries(int n, int interval){
		int currX = SCREEN_WIDTH/2 - interval - (n-1)/3*interval;
		int currY = SCREEN_HEIGHT/2 + interval;
		boolean reachBorder = true;
		int currI = 0;
		for(int i = 0; i < n; i++){
			currI = i%6;
			if(currI == 0 || currI == 5){
				if(reachBorder){currX += interval;reachBorder = false;}
				else{ currY += interval; reachBorder = true;}
			}else if(currI == 2 || currI == 3){
				if(reachBorder){ currX += interval; reachBorder = false;}
				else{currY -= interval;reachBorder = true;}
			}else if(currI == 1){
				currY -= interval;
			}else if(currI == 4){
				currY += interval;
			}
			Sprite sp = new Sprite(currX, currY, resourceManager.entryTextureRegion, vertexBufferObjectManager){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					GameLevelLoader.GAME_LEVEL = 1;
					sceneManager.setScene(SceneType.SCENE_GAME);
					return false;
				}
			};
			entries.add(sp);
			registerTouchArea(sp);
			attachChild(sp);
		}
		setTouchAreaBindingOnActionDownEnabled(true);
	}

	@Override
	public void onBackKeyPressed() {
		 activity.moveTaskToBack(true);
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
	    }else if (pSceneTouchEvent.isActionMove()){
	    	distanceTravelled += pSceneTouchEvent.getX()-prevX;
	    	menuBackgound.setParallaxValue(distanceTravelled/10);
	    	for(Sprite sp : entries) sp.setX(sp.getX()+pSceneTouchEvent.getX()-prevX);
	    }
		prevX = pSceneTouchEvent.getX();
		return false;
	}

}
