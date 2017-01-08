package com.weiwei.rollingfruit.dataloader;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.text.Text;

import com.weiwei.rollingfruit.BaseScene;

public class Target{
	private String type;
	private int targetValue;
	private int currentValue;
	private int currentApproachingValue;
	private Text text;
	private boolean increasing;
	private BaseScene scene;
	public Target(String s, int v, BaseScene baseScene){
		type = s;
		targetValue = v;
		scene = baseScene;
		reset();
	}
	
	public void reset(){
		currentValue = 0;
		currentApproachingValue = 0;
		text = null;
		increasing = false;
	}

	public void setText(Text t) {
		text = t;
		text.setAnchorCenter(0, 0);
	}
	public void setText(String t) {
		text.setText(t);
	}

	public Text getText() {
		return text;
	}
	
	public void updateValue(int increaseAmount){
		currentApproachingValue += increaseAmount;
		if(!increasing){
			increasing = true;
			approachTarget();
		}
	}
	
	private void approachTarget(){
		if(currentValue < currentApproachingValue){
			int diff = currentApproachingValue - currentValue;
			if(diff > 555) currentValue += 555;
			else if(diff > 55) currentValue += 55;
			else if(diff > 5)currentValue += 5;
			else currentValue +=1;
			text.setText(type+": "+currentValue);
			scene.engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
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
}
