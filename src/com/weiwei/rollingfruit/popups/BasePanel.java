package com.weiwei.rollingfruit.popups;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;

import com.weiwei.rollingfruit.GameScene;

public class BasePanel extends Entity{
	protected GameScene gameScene;
	public BasePanel(GameScene gs){
		super(gs.SCREEN_WIDTH/2, -gs.SCREEN_HEIGHT/2,gs.SCREEN_WIDTH, gs.SCREEN_HEIGHT);
		gameScene = gs;
	}
	
	public void popup(){
		setVisible(true);
		float Y_DELTA = 20;
		float currentY = getY();
		float y_goal = gameScene.SCREEN_HEIGHT/2;
		boolean reachGoal = true;
		if(y_goal - currentY > Y_DELTA){
			setY(currentY+Y_DELTA);
			reachGoal = false;
		}else{
			setY(y_goal);
		}
		if(!reachGoal){
			gameScene.engine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() 
	        {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	popup();
	            }
	        }));
		}
	}
	
	public void fade(){
		float Y_DELTA = 20;
		float currentY = getY();
		float y_goal = -gameScene.SCREEN_HEIGHT/2;
		boolean reachGoal = true;
		if(currentY - y_goal > Y_DELTA){
			setY(currentY-Y_DELTA);
			reachGoal = false;
		}else{
			setVisible(false);
			setY(y_goal);
		}
		if(!reachGoal){
			gameScene.engine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() 
	        {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	fade();
	            }
	        }));
		}
	}
}
