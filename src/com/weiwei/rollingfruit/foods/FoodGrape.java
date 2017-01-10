package com.weiwei.rollingfruit.foods;

import android.util.Log;

import com.weiwei.rollingfruit.foods.FoodManager.FoodType;

public class FoodGrape extends BaseFood{

	public FoodGrape(float pX, float pY, int i, int j) {
		super(pX, pY,i, j, BaseFood.foodPanel.pScene.resourceManager.grapeTextureRegion);
		final_stage = 20;
		calories = 10;
	}

	@Override
	public FoodType getFoodType() {
		return FoodType.FOOD_GRAPE;
	}

	//@Override
//	public boolean removeReady() {
//		if(stage >= final_stage){
//			if(indexI > 0) {
//				if(indexJ > 0 && BaseFood.foods[indexI-1][indexJ-1] != null){
//					BaseFood.foods[indexI-1][indexJ-1].increaseStage();
//				}
//				if(indexJ < foods[indexI].length-1 && BaseFood.foods[indexI-1][indexJ+1] != null){
//					BaseFood.foods[indexI-1][indexJ+1].increaseStage();
//				}
//			}
//			if(indexI < foods.length-1 ){
//				if(indexJ > 0 && BaseFood.foods[indexI+1][indexJ-1] != null){
//					BaseFood.foods[indexI+1][indexJ-1].increaseStage();
//				}
//				if(indexJ < foods[indexI].length-1 && BaseFood.foods[indexI+1][indexJ+1] != null){
//					BaseFood.foods[indexI+1][indexJ+1].increaseStage();
//				}
//			}
//			popupMsg("+55");
//			remove();
//			setStage(0);
//			
//			return true;
//		}else{
//			return false;
//		}
//
//	}
}
