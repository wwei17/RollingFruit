package com.weiwei.rollingfruit.popups;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import com.weiwei.rollingfruit.GameScene;
import com.weiwei.rollingfruit.MenuScene;

public class GameOverPanel extends BasePanel{
	private Sprite backBtn;
	private Sprite resetBtn;
	private Sprite background;
	private Text text;
	private Text text2;
	private AnimatedSprite cryingMonster;
	
	public GameOverPanel(GameScene gs){
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
	    text = new Text(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2+70, gameScene.resourceManager.fontTinyBlack, "GAME OVER", new TextOptions(HorizontalAlign.CENTER), gameScene.vertexBufferObjectManager);
	    text2 = new Text(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2+30, gameScene.resourceManager.fontTinyBlack, "kaghlakflwejflasdfasdfasdf", new TextOptions(HorizontalAlign.CENTER), gameScene.vertexBufferObjectManager);
	    gameScene.registerTouchArea(backBtn);
	    gameScene.registerTouchArea(resetBtn);
	    //setVisible(false);
		attachChild(background);
		attachChild(text);
		attachChild(text2);
		attachChild(backBtn);
		attachChild(resetBtn);
		gameScene.attachChild(this);
		
		cryingMonster = new AnimatedSprite(gameScene.SCREEN_WIDTH/2+120, gameScene.SCREEN_HEIGHT/2+70, gameScene.resourceManager.cryingMonsterTextureRegion, gameScene.vertexBufferObjectManager);
		cryingMonster.animate(200); 
		cryingMonster.setScale(0.5f);
        attachChild(cryingMonster);
	}

	public void popup(String s){
		text2.setText(s);
		super.popup();
	}
}
