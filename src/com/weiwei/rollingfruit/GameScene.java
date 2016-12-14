package com.weiwei.rollingfruit;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;


import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.weiwei.rollingfruit.SceneManager.SceneType;
import com.weiwei.rollingfruit.XMLLevelLoader.GameLevelLoader;
import com.weiwei.rollingfruit.foods.FoodManager;
import com.weiwei.rollingfruit.popups.GameOpeningPanel;
import com.weiwei.rollingfruit.popups.GameOverPanel;
import com.weiwei.rollingfruit.popups.GameWinPanel;


public class GameScene extends BaseScene implements IOnSceneTouchListener{
	
	public final float BLOCK_WIDTH = 50;
	public final int BLOCK_SIZE = 4;
	private Sprite backgroundSprite;
	private Sprite btn1Sprite;
	private Sprite emptySprite;
	private FoodManager foodPanel;
	public HUDPanel gameHUD;
	private GameOverPanel gameOverPanel;
	private GameWinPanel gameWinPanel;
	private GameOpeningPanel gameOpeningPanel;
	private GameLevelLoader levelLoader;

	@Override
	public void createScene() {
		foodPanel = new FoodManager(this); 
		levelLoader = new GameLevelLoader(this);
		backgroundSprite = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.gameBackgroundTextureRegion, vertexBufferObjectManager);
		attachChild(backgroundSprite);
		setOnSceneTouchListener(this);
		
		foodPanel.setFoodList(levelLoader.getFoodList());
		attachChild(foodPanel);
		
		btn1Sprite = new Sprite(SCREEN_WIDTH/8, SCREEN_HEIGHT/8, resourceManager.btn1TextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				return true;
			};
		};
		emptySprite = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.emptyTextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				return true;
			};
		};;
		registerTouchArea(btn1Sprite);
		attachChild(btn1Sprite);
		attachChild(emptySprite);
		
		//gameHUD = new HUDPanel(1000, 10);
		
	    
	    gameOverPanel = new GameOverPanel(this);
	    gameWinPanel = new GameWinPanel(this);
	    gameOpeningPanel = new GameOpeningPanel(this);
	    registerTouchArea(emptySprite);
	    engine.registerUpdateHandler(new TimerHandler(MainActivity.LOADING_TIME, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	gameOpeningPanel.popup();
            	gameHUD = levelLoader.getHUD();
            	camera.setHUD(gameHUD);
            }
        }));
	}

	@Override
	public void onBackKeyPressed() {
		//foodPanel.removeRandom();
		Log.d("KEY DOWN", "key back");
		//sceneManager.loadLastScene();
	}
	
	

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		
	}

	private float angle_background = 0;
	private float prevX = 0;
	private float prevY = 0;
	private float rotateSpeed = 0;
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
	    	preProcess();
	    	registerTouchArea(emptySprite);
	    }
		prevX = pSceneTouchEvent.getX();
		prevY = pSceneTouchEvent.getY();
		
		return false;
	}
	


	private void preProcess(){
		if(Math.abs(rotateSpeed) < 0.001) {
			foodPanel.nextStage();
			gameHUD.decreaseMove();
			process();
			return;
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
            		engine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback() 
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
		Log.d("UNREGISTER", "TRUE");
		int gameStatus = gameHUD.getGameStatus();
		if(gameStatus >= HUDPanel.GAME_WIN){
			gameWinPanel.showWinPanel();
		}else if(gameStatus == HUDPanel.GAME_LOST){
			gameOverPanel.popup();
		}else{
			unregisterTouchArea(emptySprite);
		}
	}
	
	public void reset(){
		backgroundSprite.setRotation(0);
    	foodPanel.reset();
		gameHUD.reset();
		engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	unregisterTouchArea(emptySprite);
            }
        }));
	}
}
