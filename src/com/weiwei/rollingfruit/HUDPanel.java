package com.weiwei.rollingfruit;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;

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
	private int targetScore;
	private int ultimateTarget;
	private boolean increasing;
	private Text scoreText;
	private Text moveText;
	private int moves;
	public int moveLeft;
	
	public HUDPanel(int ut, int ml){
		resourceManager = ResourceManager.getInstance();
		BaseGameActivity activity = resourceManager.activity;
        vertexBufferObjectManager = activity.getVertexBufferObjectManager();
        engine = activity.getEngine();
        Camera camera = engine.getCamera();
        SCREEN_WIDTH = (int) camera.getWidth();
        SCREEN_HEIGHT = (int) camera.getHeight();
		
		scoreText = new Text(SCREEN_WIDTH/2-20, SCREEN_HEIGHT-70, resourceManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
	    scoreText.setAnchorCenter(0, 0);  
	    moveText = new Text(20, SCREEN_HEIGHT-70, resourceManager.font, "Move: 100", new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
	    moveText.setAnchorCenter(0, 0);  
	    attachChild(scoreText);
	    attachChild(moveText);
	    currentScore = targetScore = 0;
	    ultimateTarget = ut;
	    moveLeft = moves = ml;
	    scoreText.setText("Score: "+currentScore);
	    moveText.setText("Move: "+moveLeft);
	    increasing = false;
	}
	
	public void updateScore(int increaseAmount){
		targetScore += increaseAmount;
		if(!increasing){
			increasing = true;
			approachTarget();
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
			scoreText.setText("Score: "+currentScore);
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
		if(currentScore >= ultimateTarget){
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
		scoreText.setText("Score: "+0);
		moveLeft = moves;
		moveText.setText("Move: "+moves);
	}
}
