package com.weiwei.rollingfruit.foods;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.andengine.entity.Entity;

import com.weiwei.rollingfruit.GameScene;
import com.weiwei.rollingfruit.MyMath;
import com.weiwei.rollingfruit.RandomList;

import android.util.Log;

public class FoodManager extends Entity{
	public float BLOCK_WIDTH;
	public int BLOCK_SIZE;
	public float X_DELTA = 10;
	public float Y_DELTA = 10;
	public float BACKUP_FOODS_SIZE;
	public GameScene pScene;
	public BaseFood[][] foods;
	private RandomList<BaseFood> availableFoods;
	public enum FoodType {FOOD_APPLE, FOOD_BANANA, FOOD_STRAWBERRY;
		private static final List<FoodType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
		public static FoodType randomFoodType()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		};
	}
	
	public FoodManager(GameScene scene){
		super(scene.SCREEN_WIDTH/2, scene.SCREEN_HEIGHT/2,scene.SCREEN_WIDTH, scene.SCREEN_HEIGHT);
		pScene = scene;
		BLOCK_WIDTH = pScene.BLOCK_WIDTH;
		BLOCK_SIZE = pScene.BLOCK_SIZE;
		BACKUP_FOODS_SIZE = (BLOCK_SIZE*2)*(BLOCK_SIZE*2);
		foods = new BaseFood[BLOCK_SIZE*2][BLOCK_SIZE*2];
		BaseFood.setFoodManager(this);
//		for(int i = 0 ; i < BLOCK_SIZE*2; i++){
//			for(int j = 0 ; j < BLOCK_SIZE*2; j++){
//				float x = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
//				float y = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
//				final BaseFood food = BaseFood.randomFood(x, y, i, j);
//				attachChild(food);
//				BaseFood.updateFoods(food, i , j);
//			}
//		}
//		for(int i = 0; i < BACKUP_FOODS_SIZE; i++){
//			final BaseFood food = BaseFood.randomFood(0, 0, -1, -1);
//			food.setVisible(false);
//			availableFoods.add(food);
//			attachChild(food);
//		}
	}

	public void setFoodList(RandomList<BaseFood> list) {
		availableFoods = list;
		for(int i = 0 ; i < availableFoods.size(); i++){
			attachChild(availableFoods.get(i));
		}
	}
	
	public void removeRandom(){

	}

	public void dropFoods() {
		int up_side = MyMath.getUpSide(this.getRotation());
		int i=0, j=0, k = 0;
		float tempX = 0, tempY = 0;
		if(up_side == 0){
			for(j =  foods[i].length-1 ; j >=0; j--){
				for(k = i = foods.length-1 ; i >=0 ; i--){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], k, j);
						if(k!=i) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-k*BLOCK_WIDTH);
						foods[k][j].moveTo(tempX, tempY);
						k--;
					}
				}
				for(i=0 ; k >= 0 ; k--, i++){
					tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
					tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))+i*BLOCK_WIDTH);
					BaseFood food = availableFoods.removeRandom();
					food.setVisible(true);
					food.setPosition(tempX, tempY);
					BaseFood.updateFoods(food, k, j);
					tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-k*BLOCK_WIDTH);
					foods[k][j].moveTo(tempX, tempY);
				}
			}
		}else if(up_side == 1){
			for(i = 0 ; i < foods.length; i++){
				for(k = j = foods[i].length-1; j >= 0; j--){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], i, k);
						if(k!=j) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+k*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
						foods[i][k].moveTo(tempX, tempY);
						k--;
					}
				}
				for(j=0 ; k >= 0; k--, j++){
					tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))-j*BLOCK_WIDTH);
					tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
					BaseFood food = availableFoods.removeRandom();
					food.setVisible(true);
					food.setPosition(tempX, tempY);
					BaseFood.updateFoods(food, i, k);
					tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+k*BLOCK_WIDTH);
					foods[i][k].moveTo(tempX, tempY);
				}
			}
		}else if(up_side == 2){
			for(j =  foods[i].length-1 ; j >=0; j--){
				for(k = i = 0; i < foods.length; i++){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], k, j);
						if(k!=i) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-k*BLOCK_WIDTH);
						foods[k][j].moveTo(tempX, tempY);
						k++;
					}
				}
				for(i=0 ; k < foods.length ; k++, i++){
					tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
					tempY = (float) ((pScene.SCREEN_HEIGHT/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
					BaseFood food = availableFoods.removeRandom();
					food.setVisible(true);
					food.setPosition(tempX, tempY);
					BaseFood.updateFoods(food, k, j);
					tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-k*BLOCK_WIDTH);
					foods[k][j].moveTo(tempX, tempY);
				}
			}
		}else if(up_side == 3){
			for(i = 0 ; i < foods.length; i++){
				for(k = j = 0; j < foods[i].length; j++){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], i, k);
						if(k!=j) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+k*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
						foods[i][k].moveTo(tempX, tempY);
						k++;
					}
				}
				for(j=0 ; k < foods[i].length; k++, j++){
					tempX = (float) ((pScene.SCREEN_WIDTH/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
					tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
					BaseFood food = availableFoods.removeRandom();
					food.setVisible(true);
					food.setPosition(tempX, tempY);
					BaseFood.updateFoods(food, i, k);
					tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+k*BLOCK_WIDTH);
					foods[i][k].moveTo(tempX, tempY);
				}
			}
		}
	}

	public void setRotation(float angle){
		super.setRotation(angle);
		for(int i = 0 ; i < BLOCK_SIZE*2; i++){
			for(int j = 0 ; j < BLOCK_SIZE*2; j++){
				foods[i][j].setRotation(-angle);
			}
		}
		ListIterator<BaseFood> interator = availableFoods.listIterator();
		while(interator.hasNext()){
			interator.next().setRotation(-angle);
		}
	}

	public int removeReadyFoods() {
		int scored = 0;
		for(int i = 0 ; i < BLOCK_SIZE*2; i++){
			for(int j = 0 ; j < BLOCK_SIZE*2; j++){
				if(foods[i][j].removeReady()){
					scored += foods[i][j].Calories;
					availableFoods.add(foods[i][j]);
					BaseFood.updateFoods(null, i, j);
				}
			}
		}
		return scored;
	}

	public void nextStage() {
		for(int i = 0 ; i < foods.length; i++){
			for(int j = 0 ; j < foods[i].length; j++){
				foods[i][j].increaseStage();
			}
		}
	}
	
	public void reset(){
		super.setRotation(0);
		for(int i = 0 ; i < BLOCK_SIZE*2; i++){
			for(int j = 0 ; j < BLOCK_SIZE*2; j++){
				if(foods[i][j] != null){
//					float x = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
//					float y = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
//					foods[i][j].setStage(0);
//					foods[i][j].setRotation(0);
//					foods[i][j].setVisible(false);
					availableFoods.add(foods[i][j]);
					foods[i][j] = null;
				}
				
//				foods[i][j] = availableFoods.removeRandom();
//				foods[i][j].setStage(0);
//				foods[i][j].setVisible(true);
//				foods[i][j].setPosition(x, y);
//				foods[i][j].setRotation(0);
//				BaseFood.updateFoods(foods[i][j], i , j);
			}
		}
		for(int i = 0 ; i < availableFoods.size(); i++){
			BaseFood food = availableFoods.get(i);
			food.setStage(0);
			food.setVisible(false);
			food.setRotation(0);
		}
		dropFoods();
	}
}
