package com.weiwei.rollingfruit;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class HealthPoint extends Entity {
	public final static float HP_SIZE = 50;
	public final static int HP_FULL = 5;	//unit
	public final static int HP_COUNTDOWN = 300;	//seconds
	private MenuScene menuScene;
	private Text timeText;
	private Text hpText;
	private int currentHp;
	private long currentCD;
	public HealthPoint(MenuScene ms){
		super(HP_SIZE, ms.SCREEN_HEIGHT-HP_SIZE, HP_SIZE, HP_SIZE);
		menuScene = ms;
		Sprite heart = new Sprite(HP_SIZE/2,HP_SIZE/2,HP_SIZE, HP_SIZE, menuScene.resourceManager.hpTextureRegion, menuScene.vertexBufferObjectManager);
		Sprite frame = new Sprite(HP_SIZE+HP_SIZE/2, HP_SIZE/2,HP_SIZE*2, HP_SIZE/2, menuScene.resourceManager.hpFrameTextureRegion, menuScene.vertexBufferObjectManager);
		timeText = new Text(HP_SIZE+HP_SIZE/2+5, HP_SIZE/2-2, menuScene.resourceManager.fontTinyBlack, "loooooooooooooooong", menuScene.vertexBufferObjectManager);
		timeText.setText("full");
		hpText = new Text(HP_SIZE/2, HP_SIZE/2, menuScene.resourceManager.fontTinyBlack, "lifekahfuiahelwfhakseui", menuScene.vertexBufferObjectManager);
		hpText.setText("5");
		attachChild(frame);
		attachChild(timeText);
		attachChild(heart);
		attachChild(hpText);
		currentHp = 0;
		currentCD = 2000;
		runTimer();
	}
	
	public void runTimer(){
		menuScene.engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	if(currentCD == 0){
            		hpText.setText(""+HP_FULL);
        			timeText.setText("full");
            	}else{
            		currentCD -- ;
                	if(currentCD%HP_COUNTDOWN == 0) currentHp++;
                	int a = (int) (currentCD%HP_COUNTDOWN);
                	int b = a/60;
                	int c = a%60;
                	timeText.setText((b>=10?(b+""):("0"+b))+":"+(c>=10?(c+""):("0"+c)));
                	hpText.setText(""+(HP_FULL-(currentCD+HP_COUNTDOWN-1)/HP_COUNTDOWN));
            	}
            	
            	runTimer();
            }
        }));
		
	}
	
	public void setCD(long timeStampToFull){
		currentCD = Math.max(timeStampToFull - System.currentTimeMillis(), 0)/1000;
		if(currentCD > HP_FULL*HP_COUNTDOWN){
			currentCD = HP_FULL*HP_COUNTDOWN;
		}
	}
	
	public boolean decreaseCD(){
		if(currentCD+HP_COUNTDOWN <= HP_FULL*HP_COUNTDOWN){
			currentCD+=HP_COUNTDOWN;
			return true;
		}else{
			return false;
		}
		
	}
	
	public long getCD(){
		return currentCD;
	}
}
