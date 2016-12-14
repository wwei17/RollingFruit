package com.weiwei.rollingfruit;

import java.util.ArrayList;
import java.util.Random;

public class RandomList<T> extends ArrayList<T>{
	private Random r = new Random();
	
	public RandomList(){
		super();
	}
	
	//O(1) remove
	public T removeRandom(){
		if(this.size() == 0){
			return null;
		}else{
			int index = r.nextInt(this.size());
			T obj = get(index);
			set(index,  get(this.size()-1));
			remove(this.size()-1);
			return obj;
		}
		
		
	}
}
