package com.weiwei.rollingfruit;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;


import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.weiwei.rollingfruit.SceneManager.SceneType;
import com.weiwei.rollingfruit.dataloader.GameLevelLoader;
import com.weiwei.rollingfruit.foods.FoodManager;
import com.weiwei.rollingfruit.popups.GameOpeningPanel;
import com.weiwei.rollingfruit.popups.GameOverPanel;
import com.weiwei.rollingfruit.popups.GamePausePanel;
import com.weiwei.rollingfruit.popups.GameWinPanel;
import com.weiwei.rollingfruit.popups.NextAction;
import com.weiwei.rollingfruit.popups.StoryPanel;


public class GameScene extends BaseScene implements IOnSceneTouchListener, NextAction{
	
	public final float BLOCK_WIDTH = 50;
	public int BLOCK_SIZE = 4;
	private Sprite backgroundSprite;
	private Sprite pauseSprite;
	private Sprite emptySprite;
	private FoodManager foodPanel;
	public HUDPanel gameHUD;
	private StoryPanel storyPanel;
	private GameOverPanel gameOverPanel;
	private GameWinPanel gameWinPanel;
	private GameOpeningPanel gameOpeningPanel;
	private GamePausePanel gamePausePanel;
	private GameLevelLoader levelLoader;
	private boolean gamePaused;

	@Override
	public void createScene() {
		
		levelLoader = new GameLevelLoader(this);
		BLOCK_SIZE = GameLevelLoader.GAME_SIZE;
		foodPanel = new FoodManager(this); 
		backgroundSprite = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.gameBackgroundTextureRegion, vertexBufferObjectManager);
		attachChild(backgroundSprite);
		setOnSceneTouchListener(this);
		
		foodPanel.setFoodList(levelLoader.getFoodMap());
		storyPanel = levelLoader.getStory();
		attachChild(foodPanel);
		
		emptySprite = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.emptyTextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			};
		};
		pauseSprite = new Sprite(SCREEN_WIDTH/8, SCREEN_HEIGHT/8, resourceManager.pauseTextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					unregisterTouchArea(pauseSprite);					
					togglePauseState();
				}
				return true;
			};
		};
		pauseSprite.setVisible(false);
		storyPanel.setNextMove(this);
		if(storyPanel.showStory()){
			attachChild(storyPanel);
			registerTouchArea(storyPanel);
		}
		
		attachChild(pauseSprite);
		attachChild(emptySprite);	
		
		gameHUD = levelLoader.getHUD();
		
	    gameOverPanel = new GameOverPanel(this);
	    gameWinPanel = new GameWinPanel(this);
	    gameOpeningPanel = new GameOpeningPanel(this);
	    gamePausePanel = new GamePausePanel(this);
	    
	    registerTouchArea(emptySprite);
	    registerTouchArea(pauseSprite);
	   
	    
	    angle_background = 0;
	    upSide = 0;
	}
	
	@Override
	public boolean nextMove(boolean delay) {
		pauseSprite.setVisible(true);
		float delayTime = (float) (delay? MainActivity.LOADING_TIME : 0.1);
		engine.registerUpdateHandler(new TimerHandler(delayTime, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	gameOpeningPanel.popup();
            	camera.setHUD(gameHUD);
            }
        }));

		
		return false;
	}

	@Override
	public void onBackKeyPressed() {
		//foodPanel.removeRandom();
		Log.d("KEY DOWN", "key back");
		//sceneManager.loadLastScene();
		//togglePauseState();
	}
	
	public void togglePauseState(){
		if(gamePaused){
			gamePausePanel.fade();
			Log.d("UNREGISTER", "PAUSE FADE");
			unregisterTouchArea(emptySprite);
			registerTouchArea(pauseSprite);
		}else{
			Log.d("REGISTER", "PAUSE SHOW");
			gamePausePanel.popup();
			registerTouchArea(emptySprite);
			unregisterTouchArea(pauseSprite);
			
		}
		gamePaused = !gamePaused;
	}
	
	

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		
	}

	private float angle_background;
	public int upSide;
	private float prevX;
	private float prevY;
	private float rotateSpeed;
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.isActionDown()) {
			rotateSpeed = 0;
			Log.d("KEY DOWN", "DOWN");
		}else if (pSceneTouchEvent.isActionMove()){
	    	rotateSpeed = MyMath.calculateAngle(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, prevX, prevY, pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			angle_background += rotateSpeed;
			backgroundSprite.setRotation(angle_background);
			foodPanel.setRotation(angle_background);
	    }else if(pSceneTouchEvent.isActionUp()){
	    	Log.d("REGISTER", "TRUE");
	    	registerTouchArea(emptySprite);
	    	if(Math.abs(rotateSpeed) < 1){
	    		rotateSpeed = (angle_background%360+360)%360 - MyMath.getUpSide(angle_background)*90 > 0? -2: 2;
	    	}
	    	preProcess();
	    	
	    }
		prevX = pSceneTouchEvent.getX();
		prevY = pSceneTouchEvent.getY();
		
		return false;
	}
	

	
	private void preProcess(){
		if(Math.abs(rotateSpeed) < 0.001) {
			int up = MyMath.getUpSide(angle_background);
			if(up != upSide){
				upSide = up;
				gameHUD.decreaseMove();
				process();
				return;
			}else{
				postProcess();
			}
		}else{
			engine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() 
	        {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	if(rotateSpeed > 2){
	            		rotateSpeed -= 0.5;
	            	}else if(rotateSpeed < -2){
	            		rotateSpeed += 0.5; 
	            	}else if(Math.abs(angle_background%90) <= 2){
	        			angle_background = MyMath.getUpSide(angle_background)*90;
	        			rotateSpeed = 0;
	            	}else{
	            		rotateSpeed = rotateSpeed>0? 2 : -2;
	            	}
	            	angle_background += rotateSpeed;
	            	backgroundSprite.setRotation(angle_background);
	            	foodPanel.setRotation(angle_background);
	            	preProcess();
	            }
	        }));
		}
	}

	private void process() {
		
		engine.registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	int getScore =  foodPanel.removeReadyFoods();
            	gameHUD.updateScore(getScore);
            	if(getScore > 0){
            		engine.registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback() 
                    {
                        public void onTimePassed(final TimerHandler pTimerHandler) 
                        {
                        	foodPanel.dropFoods();
                    		process() ;
                        }
                    }));
            	}else{
            		postProcess();    
            	}
            }
        }));
		
	}
	
	private void postProcess() {
		
		int gameStatus = gameHUD.getGameStatus();
		if(gameStatus >= HUDPanel.GAME_WIN){
			gameWinPanel.showWinPanel();
			try {
				MainActivity.userFile.setPlayerScoreByLevel(GameLevelLoader.GAME_LEVEL, gameStatus);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("SAVE", "FAILED");
			}
		}else if(gameStatus == HUDPanel.GAME_LOST || !foodPanel.anyMoveAvailable()){
			unregisterTouchArea(pauseSprite);
			gameOverPanel.popup();
		}else{
			Log.d("UNREGISTER", "2");
			unregisterTouchArea(emptySprite);
		}
	}
	
	public void reset(){
		backgroundSprite.setRotation(0);
		angle_background = 0;
		upSide = 0;
    	foodPanel.reset();
		gameHUD.reset();
		registerTouchArea(pauseSprite);
		engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	Log.d("UNREGISTER", "1");
            	unregisterTouchArea(emptySprite);
            	
            }
        }));
	}


}
