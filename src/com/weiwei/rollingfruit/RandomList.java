package com.weiwei.rollingfruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import com.weiwei.rollingfruit.foods.FoodManager.FoodType;
import com.weiwei.rollingfruit.foods.BaseFood;

public class RandomList{
	private Random r = new Random();
	private ArrayList<LinkedList<BaseFood>> lists;
	private HashMap<FoodType, Integer> map;
	private int size;
	public RandomList(){
		lists = new ArrayList<LinkedList<BaseFood>>();
		map = new HashMap<FoodType, Integer>();
		size = 0;
	}
	
	public synchronized boolean add(BaseFood baseFood){
		LinkedList<BaseFood> list = null;
		FoodType type = baseFood.getFoodType();
		if(map.containsKey(type)){
			list =lists.get(map.get(type));
		}else{
			list = new LinkedList<BaseFood>();
			map.put(type, lists.size());
			lists.add(list);
		}
		list.offer(baseFood);
		size++;
		return true;
	}
	
	//O(1) remove
	public synchronized BaseFood removeRandom(){
		if(size == 0){
			return null;
		}else{
			int index = r.nextInt(lists.size());
			LinkedList<BaseFood> list = lists.get(index);
			if(list.size() > 0){
				size--;
				return list.poll();
			}else{
				return removeRandom();
			}
		}
	}
	
	public BaseFood get(int index){
		for(int i = 0 ; i < lists.size(); i++){
			if(index < lists.get(i).size()){
				return lists.get(i).get(index);
			}else{
				index -= lists.get(i).size();
			}
		}
		return null;
	}
	
	public int size(){
		return size;
	}
}
