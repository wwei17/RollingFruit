package com.weiwei.rollingfruit.dataloader;

import java.io.IOException;
import java.util.HashMap;

import org.andengine.entity.IEntity;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import com.weiwei.rollingfruit.BaseScene;
import com.weiwei.rollingfruit.HUDPanel;
import com.weiwei.rollingfruit.RandomList;
import com.weiwei.rollingfruit.foods.BaseFood;
import com.weiwei.rollingfruit.popups.StoryPanel;

public class GameLevelLoader extends SimpleLevelLoader{
	public static int GAME_LEVEL = 0;
	public static int GAME_SIZE = 0;
	
	private RandomList fruitsList;
	private HUDPanel hud;
	private StoryPanel storyPanel;
	private HashMap<String, Integer> fruitsMap;
	
	public GameLevelLoader(BaseScene baseScene){
		super(baseScene.vertexBufferObjectManager);
		fruitsList = new RandomList();
		hud = new HUDPanel();
		storyPanel = new StoryPanel(baseScene);
		fruitsMap = new HashMap<String, Integer>();
		registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>("level") {
			@Override
			public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
				final int score = SAXUtils.getIntAttributeOrThrow(pAttributes, "score");
	            final int moves = SAXUtils.getIntAttributeOrThrow(pAttributes, "moves");
	            final int size = SAXUtils.getIntAttributeOrThrow(pAttributes, "size");
	            hud.setTarget("moves", moves);
	            hud.setTarget("score", score);
	            GAME_SIZE = size;
	            return null;
			}
	    });
		registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>("opening") {
			@Override
			public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
	            final String sprite = SAXUtils.getAttributeOrThrow(pAttributes, "sprite");
	            final String description = SAXUtils.getAttributeOrThrow(pAttributes, "description");
	            storyPanel.addStoryByText(sprite, description);
	            return null;
			}
	    });
		registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>("target") {
			@Override
			public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
				final int number = SAXUtils.getIntAttributeOrThrow(pAttributes, "number");
	            final String type = SAXUtils.getAttributeOrThrow(pAttributes, "type");
	            final String description = SAXUtils.getAttributeOrThrow(pAttributes, "description");
	            hud.setTarget(type, number);
	            return null;
			}
	    });
		registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>("fruit") {
			@Override
			public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
				final int number = SAXUtils.getIntAttributeOrThrow(pAttributes, "number");
	            final String type = SAXUtils.getAttributeOrThrow(pAttributes, "type");
	            fruitsMap.put(type, number);
//	            for(int i = 0 ; i< number; i++){
//	            	BaseFood food = BaseFood.createTagFood(type);
//        			food.setVisible(false);
//	            	fruitsList.add(food);
//	            }
	            return null;
			}
	    });
		loadLevelFromAsset(baseScene.activity.getAssets(), "level/" + GAME_LEVEL + ".lvl");
	}
	
	public HUDPanel getHUD(){
		return hud;
	}
	
	public StoryPanel getStory(){
		return storyPanel;
	}
	
	public HashMap<String, Integer> getFoodMap(){
		return fruitsMap;
	}
	
}
