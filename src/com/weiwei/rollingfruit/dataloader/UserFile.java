package com.weiwei.rollingfruit.dataloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;

public class UserFile {
	
	private static final String FILE_NAME = "db.db";
	private File file;
	private AESEncryption aes;
	private PlayerGroup pGroup;
	private Serializer serializer;
	private Player currentPlayer;
	
	public UserFile(Context context) throws Exception{
		file = new File(context.getFilesDir(), FILE_NAME);
		aes = new AESEncryption();
		serializer =new Persister();
		if(file.exists()){
			pGroup = readGroupDataFromFile();
			Log.d("LOADING DATA", "FILE FOUND");
		}else{
			pGroup = new PlayerGroup(973);
			Log.d("LOADING DATA", "CREATING NEW");
		}
		currentPlayer = pGroup.get(0);
		Log.d("PLAYER NUMBER", ""+pGroup.size());
		Log.d("PLAYER", ""+currentPlayer.getLevels());
	}
	
	public int getPlayerLevels(){
		return currentPlayer.getLevels();
	}
	
	public int getPlayerScoreByLevel(int lv){
		return currentPlayer.getLevelScore(lv);
	}
	
	public void setPlayerScoreByLevel(int lv, int score) throws Exception{
		currentPlayer.setLevelScore(lv, score);
		update();
	}
	
	public long getPlayerTimeStamp(){
		return currentPlayer.getTimeStamp();
	}
	
	public void setPlayerTimeStamp(long t) throws Exception{
		currentPlayer.setTimeStamp(t);
		update();
	}
	
	private void update() throws Exception{
		writeGroupDataToFile();
	}

	private PlayerGroup readGroupDataFromFile() throws Exception {
		int size = (int) file.length();
    	byte[] text = new byte[size];
    	BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        buf.read(text, 0, text.length);
        buf.close();
    	text = aes.decrypt(text);
    	InputStream stream = new ByteArrayInputStream(text);
		PlayerGroup pg = serializer.read(PlayerGroup.class,stream);
        return pg;
	}
	
	private void writeGroupDataToFile() throws Exception{
		StringWriter sw=new StringWriter();
		serializer.write(pGroup,sw);
		System.out.println(sw.toString());
		byte[] text = aes.encrypt(sw.toString().getBytes("UTF-8"));
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(text);
        fos.close();
	}
}
