package com.weiwei.rollingfruit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.text.exception.OutOfCharactersException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;

import com.weiwei.rollingfruit.SceneManager.SceneType;
import com.weiwei.rollingfruit.dataloader.GameLevelLoader;
import com.weiwei.rollingfruit.popups.NextAction;

public class MenuScene extends BaseScene implements IOnSceneTouchListener{
	private static MenuScene instance;
	
	public int ENTRY_NUMBER;
	public int LEVEL_NUMBER = 20;
	public final float ENTRY_INTERVAL = 150;
	public final float ENTRY_WIDTH = 80;
	public static final float BUTTON_SIZE = 50;
	private ParallaxBackground menuBackgound;
	private EntrySpritePanel entryList;
	private float distanceTravelled;
	private MonsterFuFu fufu;
	private HealthPoint hp;
	private Sprite muteSound;
	private Sprite slash;
	
	@Override
	public void createScene() {
		
		setOnSceneTouchListener(this);
		menuBackgound = new ParallaxBackground(0,0,0);
		Sprite backgroundSprite = new Sprite(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, resourceManager.backgroundTextureRegion, vertexBufferObjectManager);
		backgroundSprite.setScale(1.67f);
		menuBackgound.attachParallaxEntity(new ParallaxEntity(5.0f, backgroundSprite));
		Sprite groundSprite = new Sprite(SCREEN_WIDTH/2, resourceManager.groundTextureRegion.getHeight()/2, resourceManager.groundTextureRegion, vertexBufferObjectManager);
		menuBackgound.attachParallaxEntity(new ParallaxEntity(10.0f, groundSprite));
		groundSprite.setScale(1.43f);
		setBackground(menuBackgound);
		ENTRY_NUMBER = MainActivity.userFile.getPlayerLevels();
		entryList = new EntrySpritePanel(ENTRY_NUMBER+1, 150);
		attachChild(entryList);
		distanceTravelled = -entryList.rightBorder;
		menuBackgound.setParallaxValue(distanceTravelled/10);
		
		fufu = new MonsterFuFu(this, entryList.getLastSecondX()-MonsterFuFu.FUFU_SIZE/2, entryList.getLastSecondY());
		attachChild(fufu);
		
		hp = new HealthPoint(this);
		hp.setCD(MainActivity.userFile.getPlayerTimeStamp());
		attachChild(hp);
		
		instance = this;
		
		slash = new Sprite(SCREEN_WIDTH-50, SCREEN_HEIGHT-50,BUTTON_SIZE,BUTTON_SIZE, resourceManager.slashTextureRegion, vertexBufferObjectManager);
		muteSound =  new Sprite(SCREEN_WIDTH-50, SCREEN_HEIGHT-50,BUTTON_SIZE,BUTTON_SIZE, resourceManager.muteTextureRegion, vertexBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if(MainActivity.Mute){
						resourceManager.bgMusic.play();
						slash.setVisible(false);
					}else{
						resourceManager.bgMusic.pause();
						slash.setVisible(true);
					}
					MainActivity.Mute = !MainActivity.Mute;
				}
				return true;
			};
		};
		attachChild(muteSound);
		attachChild(slash);
		registerTouchArea(muteSound);
	}
	
	public static MenuScene getInstance(){
		return instance;
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
	    	//Log.d("MOVING", ""+distanceTravelled);
	    	if(	(pSceneTouchEvent.getX()-prevX > 0 && distanceTravelled <= entryList.leftBorder) ||
    			(pSceneTouchEvent.getX()-prevX < 0 && distanceTravelled > -entryList.rightBorder)){
		    	distanceTravelled += pSceneTouchEvent.getX()-prevX;
		    	menuBackgound.setParallaxValue(distanceTravelled/10);
		    	entryList.setX(entryList.getX()+pSceneTouchEvent.getX()-prevX);
		    	fufu.adjustX(pSceneTouchEvent.getX()-prevX);
	    	}
	    }
		prevX = pSceneTouchEvent.getX();
		return false;
	}
	
	public void backFromGame(){
		int currLevel = MainActivity.userFile.getPlayerLevels();
		entryList.setEntryStar();
		if(currLevel > ENTRY_NUMBER){
			ENTRY_NUMBER = currLevel;
			entryList.addOneEntry(); 
		}
	}
	
	public boolean decreaseHP(){
		if(hp.decreaseCD()){
			try {
				MainActivity.userFile.setPlayerTimeStamp(System.currentTimeMillis()+hp.getCD()*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
	}

	class EntrySpritePanel extends Entity{
		private ArrayList<EntrySprite> entries;
		private float leftBorder;
		private float rightBorder;
		
		private EntrySpritePanel(int n, int interval){
			super(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH, SCREEN_HEIGHT);
			leftBorder = 0;
			entries = new ArrayList<EntrySprite>(); 
			for(int i = 0; i < n; i++){
				addOneEntry();
			}
			setX(SCREEN_WIDTH/2-rightBorder);
			//Log.d("BORDER",leftBorder+","+rightBorder);
			MenuScene.this.setTouchAreaBindingOnActionDownEnabled(true);
		}
		
		public void addOneEntry(){
			int index = entries.size();
			float currX = 0;
			float currY = 0;
			if(index == 0){
				currX = SCREEN_WIDTH/2 - ENTRY_INTERVAL;
				currY = SCREEN_HEIGHT/2 + ENTRY_INTERVAL;
			}else{
				currX = entries.get(index-1).getX();
				currY = entries.get(index-1).getY();
			}
			int currI = index%6;
			if(currI == 0){
				currX += ENTRY_INTERVAL ;rightBorder = currX - SCREEN_WIDTH/2;
			}else if(currI == 5){
				currY += ENTRY_INTERVAL;
			}else if(currI == 2){
				currY -= ENTRY_INTERVAL;
			}else if(currI == 3){
				currX += ENTRY_INTERVAL; rightBorder = currX - SCREEN_WIDTH/2;
			}else if(currI == 1){
				currY -= ENTRY_INTERVAL;
			}else if(currI == 4){
				currY += ENTRY_INTERVAL;
			}
			EntrySprite sp = new EntrySprite(index+1, currX, currY);
			entries.add(sp);
			MenuScene.this.registerTouchArea(sp);
			attachChild(sp);
		}
		
		public void setEntryStar(){
			entries.get(GameLevelLoader.GAME_LEVEL-1).setStars();
		}
		
		public float getLastSecondX(){
			if(entries.size() > 1)
				return entries.get(entries.size()-2).getX()+getX()-SCREEN_WIDTH/2;
			else
				return SCREEN_WIDTH/4;
		}
		public float getLastSecondY(){
			if(entries.size() > 1)
				return entries.get(entries.size()-2).getY();
			else
				return SCREEN_HEIGHT/2;
		}
		
		class EntrySprite extends Sprite implements NextAction{
			private int stars;
			private TiledSprite[] starSprites = new TiledSprite[2];
			private int level;
			private Text text;
			
			public EntrySprite(int l,final float pX, final float pY){
				super(pX, pY, ENTRY_WIDTH, ENTRY_WIDTH, resourceManager.entryTextureRegion, vertexBufferObjectManager);
				level = l;
				text = new Text(ENTRY_WIDTH/2, ENTRY_WIDTH/2, resourceManager.fontTinyWhite, "skdhflkwahkfjhkaew", new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
				text.setText(""+level);
				attachChild(text);
				stars = MainActivity.userFile.getPlayerScoreByLevel(level);
				starSprites[0] = new TiledSprite(ENTRY_WIDTH/5, (float) (ENTRY_WIDTH*1.2), 30, 30, resourceManager.starTextureRegion, vertexBufferObjectManager);
				starSprites[0].setCurrentTileIndex(0);
				starSprites[0].setVisible(false);
				attachChild(starSprites[0]);
				starSprites[1] = new TiledSprite(ENTRY_WIDTH*4/5, (float) (ENTRY_WIDTH*1.2), 30, 30, resourceManager.starTextureRegion, vertexBufferObjectManager);
				starSprites[1].setCurrentTileIndex(0);
				starSprites[1].setVisible(false);
				attachChild(starSprites[1]);
				setStars();
			}
			
			public void setStars(){
				stars = MainActivity.userFile.getPlayerScoreByLevel(level);
				if(stars >= 1){
					starSprites[0].setVisible(true);
					starSprites[1].setVisible(true);
					if(stars == 1) starSprites[1].setCurrentTileIndex(1);
				}
			}
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if (pSceneTouchEvent.isActionDown()) {
					fufu.moveTo(EntrySpritePanel.this.getX()-SCREEN_WIDTH/2+getX()-MonsterFuFu.FUFU_SIZE/2, getY(), this);
				}
				return true;
			}

			@Override
			public boolean nextMove(boolean delay){
				GameLevelLoader.GAME_LEVEL = level;
				try {
					if(Arrays.asList(activity.getApplicationContext().getAssets().list("level")).contains(level+".lvl")){
						if(decreaseHP()){
							sceneManager.setScene(SceneType.SCENE_GAME);
						}else{
							fufu.popupDialog("Take a break");
							Log.d("HP", "low");
						}
					}else{
						fufu.popupDialog("Coming Soon...");
						//text.setText("Coming Soon...");
						Log.d("LEVEL NOT FOUND", ""+level);
					}
				} catch (OutOfCharactersException | IOException e) {
					e.printStackTrace();
					
				}
				return false;
			}
		}
	}
}

