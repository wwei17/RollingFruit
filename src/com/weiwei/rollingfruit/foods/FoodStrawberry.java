package com.weiwei.rollingfruit.foods;

import com.weiwei.rollingfruit.foods.FoodManager.FoodType;

public class FoodStrawberry extends BaseFood{

	public FoodStrawberry(float pX, float pY, int i, int j) {
		super(pX, pY, i , j, BaseFood.foodPanel.pScene.resourceManager.strawberryTextureRegion);
		final_stage = 2;
		Calories = 40;
	}

	@Override
	public FoodType getFoodType() {
		return FoodType.FOOD_STRAWBERRY;
	}

	@Override
	public boolean removeReady() {
		if(stage >= final_stage){
			popupMsg("+40");
			remove();
			setStage(0);
			return true;
		}else{
			return false;
		}
	}
}
