package com.weiwei.rollingfruit.popups;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;

import com.weiwei.rollingfruit.MainActivity;
import com.weiwei.rollingfruit.MenuScene;
import com.weiwei.rollingfruit.GameScene;
import com.weiwei.rollingfruit.HUDPanel;

public class GameWinPanel extends BasePanel{
	private Sprite backBtn;
	private Sprite resetBtn;
	private Sprite background;
	private TiledSprite star1;
	private TiledSprite star2;
	
	public GameWinPanel(GameScene gs) {
		super(gs);
		gameScene = gs;
		background = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2, gameScene.resourceManager.popupTextureRegion, gameScene.vertexBufferObjectManager);
		backBtn = new Sprite(gameScene.SCREEN_WIDTH/2-100, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.backTextureRegion, gameScene.vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					gameScene.camera.setHUD(null);
					gameScene.sceneManager.loadLastScene();
					gameScene.engine.registerUpdateHandler(new TimerHandler((float) (MainActivity.LOADING_TIME+0.3), new ITimerCallback() 
			        {
			            public void onTimePassed(final TimerHandler pTimerHandler) 
			            {
			            	((MenuScene)(gameScene.sceneManager.getCurrentScene())).backFromGame();
			            }
			        }));
				}
				return true;
			};
		};;
		resetBtn = new Sprite(gameScene.SCREEN_WIDTH/2+100, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.resetTextureRegion, gameScene.vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					fade();
					if(MenuScene.getInstance().decreaseHP()){
						gameScene.reset();
					}else{
						gameScene.camera.setHUD(null);
						gameScene.sceneManager.loadLastScene();
					}
				}
				return true;
			};
		};
	    star1 = new TiledSprite(gameScene.SCREEN_WIDTH/2-100, gameScene.SCREEN_HEIGHT/2+50, gameScene.resourceManager.starTextureRegion, gameScene.vertexBufferObjectManager);
	    star2 = new TiledSprite(gameScene.SCREEN_WIDTH/2+100, gameScene.SCREEN_HEIGHT/2+50, gameScene.resourceManager.starTextureRegion, gameScene.vertexBufferObjectManager);
	    star1.setCurrentTileIndex(0);
	    star2.setCurrentTileIndex(1);
	    //setVisible(false);
	    gameScene.registerTouchArea(backBtn);
	    gameScene.registerTouchArea(resetBtn);
		attachChild(background);
		attachChild(backBtn);
		attachChild(resetBtn);
		attachChild(star1);
		attachChild(star2);
		gameScene.attachChild(this);
	}
	
	public void showWinPanel(){
		if(gameScene.gameHUD.getGameStatus() > HUDPanel.GAME_WIN){
			star2.setCurrentTileIndex(0);
		}
		popup();
	}
	
}
