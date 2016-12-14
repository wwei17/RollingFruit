package com.weiwei.rollingfruit.foods;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.weiwei.rollingfruit.GameScene;
import com.weiwei.rollingfruit.foods.FoodManager.FoodType;




public abstract class BaseFood extends Entity{
	
	protected static FoodManager foodPanel;
	public static BaseFood[][] foods;
	//protected AnimatedSprite aSprite;
	protected Sprite aSprite;
	protected GameScene scene;
	protected float BLOCK_WIDTH;
	protected int BLOCK_SIZE;
	protected float X_DELTA;
	protected float Y_DELTA;
	protected int stage;
	protected int final_stage;
	protected Text text;
	protected Text popup;
	protected int indexI;
	protected int indexJ;
	public int Calories;
	
	public static void setFoodManager(FoodManager f){
		foodPanel = f;
		foods = foodPanel.foods;
	}

	//public BaseFood(float pX, float pY, int i, int j, ITiledTextureRegion pTiledTextureRegion) {
	public BaseFood(float pX, float pY, int i, int j, ITextureRegion pTextureRegion) {
		super(pX, pY, foodPanel.BLOCK_WIDTH, foodPanel.BLOCK_WIDTH);
		BLOCK_WIDTH = BaseFood.foodPanel.BLOCK_WIDTH;
		BLOCK_SIZE = BaseFood.foodPanel.BLOCK_SIZE;
		X_DELTA = BaseFood.foodPanel.X_DELTA;
		Y_DELTA = BaseFood.foodPanel.Y_DELTA;
		scene = BaseFood.foodPanel.pScene;
		stage = 0;
		foods = BaseFood.foodPanel.foods;
		//aSprite = new AnimatedSprite(BLOCK_WIDTH/2, BLOCK_WIDTH/2, pTiledTextureRegion, scene.vertexBufferObjectManager);
		aSprite = new Sprite(BLOCK_WIDTH/2, BLOCK_WIDTH/2, pTextureRegion, scene.vertexBufferObjectManager);
		aSprite.setWidth(BLOCK_WIDTH);
		aSprite.setHeight(BLOCK_WIDTH);
		//aSprite.animate(200);
		attachChild(aSprite);
		indexI = i;
		indexJ = j;
		text = new Text(BLOCK_WIDTH-10, 10, scene.resourceManager.fontTinyBlack, "loooooooooooooooong", scene.vertexBufferObjectManager);
		popup = new Text(BLOCK_WIDTH/2, BLOCK_WIDTH/2, scene.resourceManager.fontTinyWhite, "loooooooooooooooong", scene.vertexBufferObjectManager);
		text.setText(""+stage);
		aSprite.attachChild(text);
		attachChild(popup);
		popup.setVisible(false);

	}
	
	@Override
	public void setVisible(final boolean pVisible) {
		super.setVisible(pVisible);
		aSprite.setVisible(pVisible);
		text.setVisible(pVisible);
	}
	
	public void popupMsg(String s){
		popup.setText(s);
		popup.setVisible(true);
		scene.engine.registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	popup.setVisible(false);
            }
        }));
	}
	
	public void increaseStage(){
		stage++;
		text.setText(""+stage);
	}
	
	public void setStage(int s){
		stage = s;
		text.setText(""+stage);
	}
	
	public void remove(){
		float w = aSprite.getWidth();
		float h = aSprite.getHeight();
		if(w < 5 && h < 5){
			aSprite.setVisible(false);
			aSprite.setWidth(BLOCK_WIDTH);
			aSprite.setHeight(BLOCK_WIDTH);
			text.setVisible(false);
		}else{
			aSprite.setWidth(w/2);
			aSprite.setHeight(h/2);
			scene.engine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() 
	        {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	remove();
	            }
	        }));
		}
	}
	
	public void moveTo(final float x_goal, final float y_goal){
		boolean reachGoal = true;
		if(Math.abs(x_goal - getX()) > X_DELTA){
			setX(getX() + (x_goal - getX()>0? X_DELTA:-X_DELTA));
			reachGoal = false;
		}else{
			setX(x_goal);
		}
		if(Math.abs(y_goal - getY()) > Y_DELTA){
			setY(getY() + (y_goal - getY()>0? Y_DELTA:-Y_DELTA));
			reachGoal = false;
		}else{
			setY(y_goal);
		}
		if(!reachGoal){
			scene.engine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() 
	        {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	moveTo(x_goal, y_goal);
	            }
	        }));
		}
	}
	
	public abstract boolean removeReady();
	public abstract FoodType getFoodType();
	
	public static BaseFood randomFood(float pX, float pY, int i, int j){
		FoodType type = FoodType.randomFoodType();
		if(type == FoodType.FOOD_APPLE){
			return new FoodApple(pX, pY, i, j);
		}else if(type == FoodType.FOOD_BANANA){
			return new FoodBanana(pX, pY , i, j);
		}else if(type == FoodType.FOOD_STRAWBERRY){
			return new FoodStrawberry(pX, pY, i, j);
		}
		
		return null;
	}
	
	public static BaseFood createTagFood(String type){
		if(type.equals("apple")){
			return new FoodApple(0,0,0,0);
		}else if(type.equals("banana")){
			return new FoodBanana(0,0,0,0);
		}else if(type.equals("strawberry")){
			return new FoodStrawberry(0,0,0,0);
		}
		
		return null;
	}
	
	public static void updateFoods(BaseFood food, int i, int j){
		foods[i][j] = food;
		if(food != null){
			food.indexI = i;
			food.indexJ = j;
		}
	}
	
}






