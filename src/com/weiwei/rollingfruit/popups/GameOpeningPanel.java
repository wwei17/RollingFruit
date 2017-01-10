package com.weiwei.rollingfruit.popups;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import com.weiwei.rollingfruit.GameScene;
import com.weiwei.rollingfruit.HUDPanel.Target;
import com.weiwei.rollingfruit.foods.FoodManager.FoodType;

public class GameOpeningPanel extends BasePanel{
	private static float TEXT_INTERVAL = 35;
	private Sprite playBtn;
	private Sprite background;
	private Text text;
	
	public GameOpeningPanel(GameScene gs){
		super(gs);
		background = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2, gameScene.resourceManager.popupTextureRegion, gameScene.vertexBufferObjectManager);
		playBtn = new Sprite(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2-50, gameScene.resourceManager.playTextureRegion, gameScene.vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					gameScene.reset();
					fade();
				}
				return true;
				
			};
		};;

	    text = new Text(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2+80, gameScene.resourceManager.fontTinyBlack, "Target skehgnrghiwscerhgwechgrwsngcbjrseg", new TextOptions(AutoWrap.WORDS, gameScene.SCREEN_WIDTH-250, HorizontalAlign.LEFT), gameScene.vertexBufferObjectManager);
	    gameScene.registerTouchArea(playBtn);
	    //setVisible(false);
		attachChild(background);
		attachChild(text);
		attachChild(playBtn);
		gameScene.attachChild(this);
					
		setTargetText();
	}

	@Override
	public void fade(){
		super.fade();
	}
	
	@Override
	public void popup(){
		super.popup();			
	}
	
	public void setTargetText(){
		StringBuilder sb = new StringBuilder();
		int targetScore = gameScene.gameHUD.ultimateTarget;
		sb.append("1. Gain ");
		if(targetScore < 0){
			sb.append("no more than ");
			targetScore = -targetScore;
		}
		sb.append(targetScore);
		sb.append(" calories");
		text.setText(sb.toString());
		int i = 1;
		for(FoodType type : gameScene.gameHUD.targetMap.keySet()){
			sb.setLength(0);
		    Text text2 = new Text(gameScene.SCREEN_WIDTH/2, gameScene.SCREEN_HEIGHT/2+80-TEXT_INTERVAL*i, gameScene.resourceManager.fontTinyBlack, "Target skehgnrghiwscerhgwechgrwsngcbjrseg", new TextOptions(AutoWrap.WORDS, gameScene.SCREEN_WIDTH-250, HorizontalAlign.LEFT), gameScene.vertexBufferObjectManager);
		    attachChild(text2);
			Target t = gameScene.gameHUD.targetMap.get(type);
			sb.append(i+1);
			sb.append(". Gain ");
			sb.append(t.value);
			sb.append(" super ");
			sb.append(t.type);
			if(t.value > 1) sb.append("s");
			text2.setText(sb.toString());
			i++;
		}
	}
	
}
