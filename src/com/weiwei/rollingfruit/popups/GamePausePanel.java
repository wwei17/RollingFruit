package com.weiwei.rollingfruit.popups;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import com.weiwei.rollingfruit.GameScene;

public class GamePausePanel extends BasePanel{
	
	private Sprite backBtn;
	private Sprite resetBtn;
	private Sprite playBtn;
	private Sprite background;
	private Text text;
	
	public GamePausePanel(GameScene gs) {
		super(gs);
		background = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2, gameScene.resourceManager.popupTextureRegion, gameScene.vertexBufferObjectManager);
		backBtn = new Sprite(gameScene.SCREEN_WIDTH/2-100, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.backTextureRegion, gameScene.vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					gameScene.camera.setHUD(null);
					gameScene.sceneManager.loadLastScene();
				}
				return true;
			};
		};
//		resetBtn = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.resetTextureRegion, gameScene.vertexBufferObjectManager){
//			@Override
//			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//				if (pSceneTouchEvent.isActionDown()) {
//					fade();
//					gameScene.reset();
//				}
//				return true;
//			};
//		};
		playBtn = new Sprite(gameScene.SCREEN_WIDTH/2+100, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.playTextureRegion, gameScene.vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					fade();
					gameScene.togglePauseState();
					
				}
				return true;
			};
		};
	    text = new Text(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2+50, gameScene.resourceManager.fontTinyBlack, "GAME PAUSED", new TextOptions(HorizontalAlign.CENTER), gameScene.vertexBufferObjectManager);
	    gameScene.registerTouchArea(backBtn);
	    //gameScene.registerTouchArea(resetBtn);
	    gameScene.registerTouchArea(playBtn);
		attachChild(background);
		attachChild(text);
		attachChild(backBtn);
		//attachChild(resetBtn);
		attachChild(playBtn);
		
		
		gameScene.attachChild(this);
	}
}
