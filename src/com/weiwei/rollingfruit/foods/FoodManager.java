package com.weiwei.rollingfruit.foods;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
	private RandomList availableFoods;
	public static int upSide; //0:¡ü 1:¡ú 2:¡ý 3:¡û
	
	public enum FoodType {FOOD_APPLE, FOOD_BANANA, FOOD_STRAWBERRY, FOOD_ORANGE, FOOD_GRAPE;
		private static final List<FoodType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
		public static FoodType randomFoodType()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		};
		public static FoodType getFoodTypeFromString(String s)  {
			if(s.equals("apple")) return FOOD_APPLE;
			else if(s.equals("banana")) return FOOD_BANANA;
			else if(s.equals("strawberry")) return FOOD_STRAWBERRY;
			else if(s.equals("orange")) return FOOD_ORANGE;
			else if(s.equals("grape")) return FOOD_GRAPE;
			else return null;
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
		upSide = 0;
	}

	public void setFoodList(HashMap<String, Integer> map) {
		availableFoods = new RandomList();
		for(String type : map.keySet()){
			int number = map.get(type);
			for(int i = 0 ; i< number; i++){
            	BaseFood food = BaseFood.createTagFood(type);
    			food.setVisible(false);
    			availableFoods.add(food);
    			attachChild(food);
            }
		}
//		for(int i = 0 ; i < availableFoods.size(); i++){
//			attachChild(availableFoods.get(i));
//		}
	}

	public void dropFoods() {
		upSide = pScene.upSide;
		int i=0, j=0, k = 0;
		float tempX = 0, tempY = 0;
		if(upSide == 0){
			for(j =  foods[i].length-1 ; j >=0; j--){
				for(k = i = foods.length-1 ; i >=0 ; i--){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], k, j);
						if(k!=i) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-k*BLOCK_WIDTH);
						foods[k][j].moveTo(tempX, tempY, false);
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
					foods[k][j].moveTo(tempX, tempY, false);
				}
			}
		}else if(upSide == 1){
			for(i = 0 ; i < foods.length; i++){
				for(k = j = foods[i].length-1; j >= 0; j--){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], i, k);
						if(k!=j) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+k*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
						foods[i][k].moveTo(tempX, tempY, false);
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
					foods[i][k].moveTo(tempX, tempY, false);
				}
			}
		}else if(upSide == 2){
			for(j =  foods[i].length-1 ; j >=0; j--){
				for(k = i = 0; i < foods.length; i++){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], k, j);
						if(k!=i) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+j*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-k*BLOCK_WIDTH);
						foods[k][j].moveTo(tempX, tempY, false);
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
					foods[k][j].moveTo(tempX, tempY, false);
				}
			}
		}else if(upSide == 3){
			for(i = 0 ; i < foods.length; i++){
				for(k = j = 0; j < foods[i].length; j++){
					if(foods[i][j] != null){
						BaseFood.updateFoods(foods[i][j], i, k);
						if(k!=j) BaseFood.updateFoods(null, i, j);
						tempX = (float) ((pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*(BLOCK_SIZE-0.5))+k*BLOCK_WIDTH);
						tempY = (float) ((pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*(BLOCK_SIZE-0.5))-i*BLOCK_WIDTH);
						foods[i][k].moveTo(tempX, tempY, false);
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
					foods[i][k].moveTo(tempX, tempY, false);
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
		for(int i = 0 ; i < availableFoods.size(); i++){
			availableFoods.get(i).setRotation(-angle);
		}
	}

	public int removeReadyFoods() {
		upSide = pScene.upSide;
		int scored = 0; int gain = 0;
		BaseFood food = null;
		for(int i = 0 ; i < BLOCK_SIZE*2; i++){
			for(int j = 0 ; j < BLOCK_SIZE*2; j++){
				if(upSide == 0){
					food = foods[BLOCK_SIZE*2-1-i][j];
				}else if(upSide == 1){
					food = foods[i][BLOCK_SIZE*2-1-j];
				}else{
					food = foods[i][j];
				}
				gain = food.removeReady();
				if(gain > 0){
					scored += gain;
					BaseFood.updateFoods(null, food.indexI, food.indexJ);
					availableFoods.add(food);
				}
			}
		}
		if(scored > 0){
			return scored;
		}else{
			for(int i = 0 ; i < BLOCK_SIZE*2; i++){
				for(int j = 0 ; j < BLOCK_SIZE*2; j++){
					food = foods[i][j];
					gain = food.removeFinal();
					if(gain > 0){
						pScene.gameHUD.updateScore2(food.getFoodType());
						scored += gain;
						BaseFood.updateFoods(null, food.indexI, food.indexJ);
						availableFoods.add(food);
						
					}
				}
			}
			return scored;
		}
	}
	
	public void reset(){
		super.setRotation(0);
		for(int i = 0 ; i < BLOCK_SIZE*2; i++){
			for(int j = 0 ; j < BLOCK_SIZE*2; j++){
				if(foods[i][j] != null){
					availableFoods.add(foods[i][j]);
					foods[i][j] = null;
				}
			}
		}
		for(int i = 0 ; i < availableFoods.size(); i++){
			BaseFood food = availableFoods.get(i);
			food.setStage(1);
			food.setVisible(false);
			food.setRotation(0);
		}
		dropFoods();
	}
	
	public boolean anyMoveAvailable(){
		boolean anyAvailableMove = false;
		for(int i = 0 ; i < BLOCK_SIZE*2; i++){
			for(int j = 0 ; j < BLOCK_SIZE*2; j++){
				anyAvailableMove = anyAvailableMove || (i > 0 && foods[i][j].getFoodType() == foods[i-1][j].getFoodType());
				anyAvailableMove = anyAvailableMove || (j > 0 && foods[i][j].getFoodType() == foods[i][j-1].getFoodType());
				anyAvailableMove = anyAvailableMove || (i < BLOCK_SIZE*2-1 && foods[i][j].getFoodType() == foods[i+1][j].getFoodType());
				anyAvailableMove = anyAvailableMove || (j < BLOCK_SIZE*2-1 && foods[i][j].getFoodType() == foods[i][j+1].getFoodType());
			}
		}
		return anyAvailableMove;
	}
}
