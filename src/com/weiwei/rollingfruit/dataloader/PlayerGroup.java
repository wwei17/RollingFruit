package com.weiwei.rollingfruit.dataloader;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

import android.util.Log;


@Root
@Default(DefaultType.FIELD)
public class PlayerGroup{
	@ElementList(inline=true)
	private ArrayList<Player> players;
	
	public PlayerGroup(){
		players = new ArrayList<Player>();
	}
	
	public PlayerGroup(int id){
		//players = new HashMap<Integer, Player>();
		players = new ArrayList<Player>();
		Log.d("NEW PLAYER", "id:"+id); 
		players.add(new Player(id+""));

	}
	
	public Player get(int i){
		return players.get(i);
	}
	
	public int size(){
		return players.size();
	}
}

@Default(DefaultType.FIELD)
class Player {
	
	@Attribute
    private String id;
	
	@Transient
    private String password = "aaaa";
	
	@Element
	private long timeStamp;	//since 1970

	@ElementList(inline=true)
	private ArrayList<Level> levels;
	
    public Player(){
    	levels = new ArrayList<Level>();
    	timeStamp = 0;
    }
    
    public void addLevel(int score){
    	levels.add(new Level(score));
    }
    
    public long getTimeStamp(){
    	return timeStamp;
    }
    
    public void setTimeStamp(long t){
    	timeStamp = t;
    }
    
    public int getLevelScore(int lv){
    	if(lv - 1 < levels.size()){
    		return levels.get(lv-1).getScore();
    	}else{
    		return 0;
    	}
    }
    public void setLevelScore(int lv, int score){
    	if(lv - 1 < levels.size()){
    		levels.get(lv-1).setScore(score);
    	}else{
    		levels.add(new Level(score));
    	}
    }
    
    public int getLevels(){
    	return levels.size();
    }
    
    public Player(String id){
    	this();
    	this.id = id;
//    	for(int i = 0 ;i < 5; i++){
//			levels.add(new Level(i));
//		}
    }
}

@Default(DefaultType.FIELD)
class Level{
	@Attribute
	private int id;
	
	@Element
	private int score;
	
	public void setScore(int s){
		if(s > score) score = s;
	}
	
	public int getScore(){
		return score;
	}

	public Level(){};
	
	public Level(int s){
		score = s;
	}
}

