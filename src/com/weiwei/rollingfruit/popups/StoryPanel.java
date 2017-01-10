package com.weiwei.rollingfruit.popups;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import com.weiwei.rollingfruit.BaseScene;

public class StoryPanel extends Entity{
	private ArrayList<Sprite> spList;
	private ArrayList<Text> textList;
	private int currStoryIndex;
	
	protected BaseScene scene;
	private NextAction nextMoveCaller;
	
	public StoryPanel(BaseScene gs){
		super(gs.SCREEN_WIDTH/2, gs.SCREEN_HEIGHT/2,gs.SCREEN_WIDTH, gs.SCREEN_HEIGHT);
		scene = gs;
		spList = new ArrayList<Sprite>();
		textList = new ArrayList<Text>();
		currStoryIndex = 0;
		Sprite sp = new Sprite(scene.SCREEN_WIDTH/2, scene.SCREEN_HEIGHT/2, scene.resourceManager.storyTextureRegion, scene.vertexBufferObjectManager);
		attachChild(sp);
	}
	
	public void addStoryByText(String spText, String s){
		Sprite sp = new Sprite(scene.SCREEN_WIDTH/2, scene.SCREEN_HEIGHT/2, scene.resourceManager.getRegionByString(spText), scene.vertexBufferObjectManager);
		attachChild(sp);
		sp.setVisible(false);
		Text text = new Text(scene.SCREEN_WIDTH/2, scene.SCREEN_HEIGHT-100, scene.resourceManager.fontMedian, "Score: 0123456789sdfsfsasdfasdfdfasdfasdfasfsadfasfdasfdfasdfsadfasfasfa", new TextOptions(AutoWrap.WORDS, scene.SCREEN_WIDTH-100, HorizontalAlign.LEFT), scene.vertexBufferObjectManager);
		text.setText(s);
		attachChild(text);
		text.setVisible(false);
		spList.add(sp);
		textList.add(text);
	}
	
	public boolean showStory(){
		if(textList.size() == 0){
			nextMoveCaller.nextMove(true);
			return false;
		}else{
			spList.get(0).setVisible(true);
			textList.get(0).setVisible(true);
			return true;
		}
		
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			if(currStoryIndex < textList.size()){
				spList.get(currStoryIndex).setVisible(false);
				textList.get(currStoryIndex).setVisible(false);
				currStoryIndex++;
			}
			if(currStoryIndex < textList.size()){ 
				spList.get(currStoryIndex).setVisible(true);
				textList.get(currStoryIndex).setVisible(true);
			}else{
				//fade this
				this.setVisible(false);
				scene.unregisterTouchArea(this);
				nextMoveCaller.nextMove(false);
			}
		}
		return true;
		
		
	}

	public void setNextMove(NextAction caller) {
		nextMoveCaller = caller;
		
	}
	
}
