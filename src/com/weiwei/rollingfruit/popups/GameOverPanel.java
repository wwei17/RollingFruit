package com.weiwei.rollingfruit.popups;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import com.weiwei.rollingfruit.GameScene;

public class GameOverPanel extends BasePanel{
	private Sprite backBtn;
	private Sprite resetBtn;
	private Sprite background;
	private Text text;
	
	public GameOverPanel(GameScene gs){
		super(gs);
		background = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2, gameScene.resourceManager.popupTextureRegion, gameScene.vertexBufferObjectManager);
		backBtn = new Sprite(gameScene.SCREEN_WIDTH/2-100, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.backTextureRegion, gameScene.vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				gameScene.camera.setHUD(null);
				gameScene.sceneManager.loadLastScene();
				return true;
			};
		};;
		resetBtn = new Sprite(gameScene.SCREEN_WIDTH/2+100, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.resetTextureRegion, gameScene.vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				fade();
				gameScene.reset();
				return true;
			};
		};
	    text = new Text(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2+50, gameScene.resourceManager.fontBigWhite, "GAME OVER", new TextOptions(HorizontalAlign.CENTER), gameScene.vertexBufferObjectManager);
	    gameScene.registerTouchArea(backBtn);
	    gameScene.registerTouchArea(resetBtn);
	    //setVisible(false);
		attachChild(background);
		attachChild(text);
		attachChild(backBtn);
		attachChild(resetBtn);
		gameScene.attachChild(this);
	}

	
}
