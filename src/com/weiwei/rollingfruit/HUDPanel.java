package com.weiwei.rollingfruit;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;

import com.weiwei.rollingfruit.foods.FoodManager.FoodType;

public class HUDPanel extends HUD{
	public static final int GAME_WIN = 1;
	public static final int GAME_MASTER_WIN = 2;
	public static final int GAME_LOST = -1;
	public static final int GAME_CONTINUE = 0;
	private Engine engine;
	private ResourceManager resourceManager;
	private VertexBufferObjectManager vertexBufferObjectManager;
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	public int currentScore;
	public int targetScore;
	public int ultimateTarget;
	private boolean increasing;
	private Text scoreText;
	private Text moveText;
	private int moves;
	public int moveLeft;
	public HashMap<FoodType, Target> targetMap;
	
	public HUDPanel(){
		resourceManager = ResourceManager.getInstance();
		BaseGameActivity activity = resourceManager.activity;
        vertexBufferObjectManager = activity.getVertexBufferObjectManager();
        engine = activity.getEngine();
        Camera camera = engine.getCamera();
        SCREEN_WIDTH = (int) camera.getWidth();
        SCREEN_HEIGHT = (int) camera.getHeight();
		
		scoreText = new Text(20, SCREEN_HEIGHT-60, resourceManager.fontMedian, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
	    scoreText.setAnchorCenter(0, 0);  
	    moveText = new Text(20, SCREEN_HEIGHT-100, resourceManager.fontMedian, "Move: 100", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
	    moveText.setAnchorCenter(0, 0);  
	    attachChild(scoreText);
	    attachChild(moveText);
	    targetMap = new HashMap<FoodType, Target>();
	    increasing = false;
	}
	
	public void setTarget(String s, int v){
		if(s.equals("moves")){
			moveLeft = moves = v;
			moveText.setText("Move: "+moveLeft);
		}else if(s.equals("score")){
			currentScore = targetScore = 0;
			ultimateTarget = v;
			scoreText.setText("Score: "+currentScore);
		}else{
			FoodType type = FoodType.getFoodTypeFromString(s);
			int size = targetMap.size();
			Text text = new Text(70, SCREEN_HEIGHT-140-size*40, resourceManager.fontMedian, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
			Sprite sp = new Sprite(20, SCREEN_HEIGHT-140-size*40, resourceManager.getRegionByString(s), vertexBufferObjectManager);
			Target t = new Target(s, v, text, sp);
			targetMap.put(type, t);
			attachChild(text);
			attachChild(sp);
		}
	}
	
	public void updateScore(int increaseAmount){
		targetScore += increaseAmount;
		if(!increasing){
			increasing = true;
			approachTarget();
		}
	}
	
	public void updateScore2(FoodType type) {
		if(targetMap.containsKey(type)){
			Target target = targetMap.get(type);
			target.increment();
		}
	}
	
	public void decreaseMove(){
		moveLeft--;
		moveText.setText("Move: "+moveLeft);
	}
	
	private void approachTarget(){
		if(currentScore < targetScore){
			int diff = targetScore - currentScore;
			if(diff > 555) currentScore += 555;
			else if(diff > 55) currentScore += 55;
			else currentScore += 5;
			scoreText.setText("Calories: "+currentScore);
			engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	        {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	approachTarget();
	            }
            }));
		}else{
			increasing = false;
		}
	}
	
	public int getGameStatus(){
		boolean win = true;
		if(ultimateTarget > 0){
			win = currentScore >= ultimateTarget;
		}else if(currentScore > -ultimateTarget){
			return GAME_LOST;	//lost
		}
		for(FoodType type : targetMap.keySet()){
			Target t = targetMap.get(type);
			win = win && t.currValue >= t.value;
		}
		if(win){
			if(moveLeft > moves/3){
				return GAME_MASTER_WIN;	//win
			}else{
				return GAME_WIN;	//win
			}
		}else if(moveLeft == 0){
			return GAME_LOST;	//lost
		}else{
			return GAME_CONTINUE;	//game continue
		}
	}
	
	public void reset(){
		currentScore = targetScore = 0;
		scoreText.setText("Calories: "+0);
		moveLeft = moves;
		moveText.setText("Move: "+moves);
		for(FoodType type : targetMap.keySet()){
			targetMap.get(type).reset();
		}
	}
	
	public class Target{
		public String type;
		public int value;
		int currValue;
		Text text;
		Sprite sp;
		Target(String s, int v, Text t, Sprite p){
			type = s;
			value = v;
			text = t;
			text.setAnchorCenter(0, 0);
			sp = p;
			sp.setWidth(40);
			sp.setHeight(40);
			sp.setAnchorCenter(0, 0);
			reset();
		}
		void reset(){
			currValue = 0;
			text.setText(": 0");
		}
		void increment(){
			currValue++;
			text.setText(": "+currValue);
		}
	}

	
}


