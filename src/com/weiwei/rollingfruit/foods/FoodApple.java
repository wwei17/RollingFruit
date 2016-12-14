package com.weiwei.rollingfruit.foods;

import com.weiwei.rollingfruit.foods.FoodManager.FoodType;

public class FoodApple extends BaseFood{

	public FoodApple(float pX, float pY, int i, int j) {
		super(pX, pY,i, j, BaseFood.foodPanel.pScene.resourceManager.appleTextureRegion);
		final_stage = 4;
		Calories = 55;
	}

	@Override
	public FoodType getFoodType() {
		return FoodType.FOOD_APPLE;
	}

	@Override
	public boolean removeReady() {
		if(stage >= final_stage){
			if(indexI > 0) {
				if(indexJ > 0 && BaseFood.foods[indexI-1][indexJ-1] != null){
					BaseFood.foods[indexI-1][indexJ-1].increaseStage();
				}
				if(indexJ < foods[indexI].length-1 && BaseFood.foods[indexI-1][indexJ+1] != null){
					BaseFood.foods[indexI-1][indexJ+1].increaseStage();
				}
			}
			if(indexI < foods.length-1 ){
				if(indexJ > 0 && BaseFood.foods[indexI+1][indexJ-1] != null){
					BaseFood.foods[indexI+1][indexJ-1].increaseStage();
				}
				if(indexJ < foods[indexI].length-1 && BaseFood.foods[indexI+1][indexJ+1] != null){
					BaseFood.foods[indexI+1][indexJ+1].increaseStage();
				}
			}
			popupMsg("+55");
			remove();
			setStage(0);
			return true;
		}else{
			return false;
		}
	}
}
