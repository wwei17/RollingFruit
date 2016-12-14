package com.weiwei.rollingfruit.popups;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import com.weiwei.rollingfruit.GameScene;

public class GameOpeningPanel extends BasePanel{
		private Sprite playBtn;
		private Sprite background;
		private Text text;
		
		public GameOpeningPanel(GameScene gs){
			super(gs);
			background = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2, gameScene.resourceManager.popupTextureRegion, gameScene.vertexBufferObjectManager);
			playBtn = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.playTextureRegion, gameScene.vertexBufferObjectManager){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					gameScene.reset();
					fade();
					return true;
				};
			};;

		    text = new Text(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2+50, gameScene.resourceManager.fontBigWhite, "Target ", new TextOptions(HorizontalAlign.CENTER), gameScene.vertexBufferObjectManager);
		    gameScene.registerTouchArea(playBtn);
		    //setVisible(false);
			attachChild(background);
			attachChild(text);
			attachChild(playBtn);
			gameScene.attachChild(this);
		}

		
	}
