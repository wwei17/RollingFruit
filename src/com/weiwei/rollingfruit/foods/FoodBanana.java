package com.weiwei.rollingfruit.foods;

import com.weiwei.rollingfruit.foods.FoodManager.FoodType;

public class FoodBanana extends BaseFood{

	public FoodBanana(float pX, float pY, int i, int j) {
		super(pX, pY, i, j, BaseFood.foodPanel.pScene.resourceManager.bananaTextureRegion);
		final_stage = 3;
		Calories = 60;
	}

	@Override
	public FoodType getFoodType() {
		return FoodType.FOOD_BANANA;
	}

	@Override
	public boolean removeReady() {
		if(stage >= final_stage){
			if(indexI > 0 && BaseFood.foods[indexI-1][indexJ] != null) BaseFood.foods[indexI-1][indexJ].increaseStage();
			if(indexJ > 0 && BaseFood.foods[indexI][indexJ-1] != null) BaseFood.foods[indexI][indexJ-1].increaseStage();
			if(indexI < foods.length-1 && BaseFood.foods[indexI+1][indexJ] != null) BaseFood.foods[indexI+1][indexJ].increaseStage();
			if(indexJ < foods[indexI].length-1 && BaseFood.foods[indexI][indexJ+1] != null) BaseFood.foods[indexI][indexJ+1].increaseStage();
			popupMsg("+60");
			remove();
			setStage(0);
			return true;
		}else{
			return false;
		}
	}
}
