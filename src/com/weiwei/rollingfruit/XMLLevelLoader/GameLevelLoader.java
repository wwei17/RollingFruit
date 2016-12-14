package com.weiwei.rollingfruit.XMLLevelLoader;

import java.io.IOException;

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
import com.weiwei.rollingfruit.foods.FoodManager;

public class GameLevelLoader extends SimpleLevelLoader{
	public static int GAME_LEVEL = 0;
	public static final String TAG_LEVEL = "level";
	public static final String TAG_FRUIT = "fruit";
	public static final String TAG_FRUIT_TYPE= "type";
	public static final String TAG_FRUIT_NUMBER = "number";
	public static final String TAG_GAME_MOVES = "moves";
	public static final String TAG_GAME_TARGET = "target";
	
	private RandomList<BaseFood> fruitsList;
	private HUDPanel hud;
	
	public GameLevelLoader(BaseScene baseScene){
		super(baseScene.vertexBufferObjectManager);
		fruitsList = new RandomList<BaseFood>();
		registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_LEVEL) {
			@Override
			public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
				final int score = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_GAME_TARGET);
	            final int moves = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_GAME_MOVES);
	            hud = new HUDPanel(score, moves);
	            return null;
			}
	    });
		registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_FRUIT) {
			@Override
			public IEntity onLoadEntity(String pEntityName, IEntity pParent, Attributes pAttributes, SimpleLevelEntityLoaderData pEntityLoaderData) throws IOException {
				final int number = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_FRUIT_NUMBER);
	            final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_FRUIT_TYPE);
	            for(int i = 0 ; i< number; i++){
	            	BaseFood food = BaseFood.createTagFood(type);
        			food.setVisible(false);
	            	fruitsList.add(food);
	            }
	            return null;
			}
	    });
		loadLevelFromAsset(baseScene.activity.getAssets(), "level/" + GAME_LEVEL + ".lvl");
	}
	
	public HUDPanel getHUD(){
		return hud;
	}
	
	public RandomList<BaseFood> getFoodList(){
		return fruitsList;
	}
	
}
