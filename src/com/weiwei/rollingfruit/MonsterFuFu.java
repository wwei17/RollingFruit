package com.weiwei.rollingfruit;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;


import com.weiwei.rollingfruit.popups.NextAction;

public class MonsterFuFu extends Entity{
	private static final float X_DELTA = 5;
	private static final float Y_DELTA = 5;
	public final static float FUFU_SIZE = 100;
	private AnimatedSprite fufu;
	private MenuScene menuScene;
	private LinkedList<Destination> moveToQueue;
	public MonsterFuFu(MenuScene ms, float initX, float initY){
		super(initX, initY,FUFU_SIZE,FUFU_SIZE);
		menuScene = ms;
		fufu = new AnimatedSprite(FUFU_SIZE/2,FUFU_SIZE/2, menuScene.resourceManager.movingMonsterTextureRegion, menuScene.vertexBufferObjectManager);
		fufu.animate(200); 
		fufu.setHeight(FUFU_SIZE);
		fufu.setWidth(FUFU_SIZE);
		fufu.setIgnoreUpdate(true);
		attachChild(fufu);
		moveToQueue = new LinkedList<Destination>();
	}
	
	public void moveToHelper(final Destination d){
		float x_goal = d._x;
		float y_goal = d._y;
		NextAction na = d._na;
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
			menuScene.engine.registerUpdateHandler(new TimerHandler(0.02f, new ITimerCallback() 
	        {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	moveToHelper(d);
	            }
	        }));
		}else{
			fufu.setWidth(FUFU_SIZE);
			moveToQueue.poll();
			if(moveToQueue.size() > 0){
				Destination d2 = moveToQueue.peek();
				moveToHelper(d2);
			}else{
				fufu.setIgnoreUpdate(true);
				if(na != null){
					na.nextMove(false);
					//Log.d("FUFU",getX()+","+getY());
				}
			}
		}
		
	}
	
	public void moveTo(final float x_goal, final float y_goal, NextAction na){
		fufu.setIgnoreUpdate(false);
		Destination d = new Destination(x_goal, y_goal, na);
		moveToQueue.offer(d);
		if(moveToQueue.size() == 1){
			if(x_goal < getX()) fufu.setWidth(-FUFU_SIZE);
			moveToHelper(d);
		}
	}
	
	class Destination{
		float _x;
		float _y;
		NextAction _na;
		public Destination(float x, float y, NextAction na){
			_x = x;
			_y = y;
			_na = na;
		}
		
		public void adjustX(float dx){
			_x += dx;
		}
	}

	public void adjustX(float f) {
		setX(getX()+f);
		Iterator<Destination> i = moveToQueue.iterator();
		while (i.hasNext()) {
			i.next().adjustX(f);
		}
	}
}
