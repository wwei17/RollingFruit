package com.weiwei.rollingfruit;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Walls extends Entity{
	public float BLOCK_WIDTH;
	public int BLOCK_SIZE;
	
	private PhysicsWorld physicsWorld;
	private GameScene pScene;
	private ArrayList<Rectangle> borders = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> externalWalls = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> internalWallsH = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> internalWallsV = new ArrayList<Rectangle>();
	private ArrayList<Body> externalWallBodies = new ArrayList<Body>();
	private ArrayList<Body> internalWallBodiesV = new ArrayList<Body>();
	private ArrayList<Body> internalWallBodiesH = new ArrayList<Body>();
	
	
	public Walls(GameScene scene, PhysicsWorld pw){
		super(scene.SCREEN_WIDTH/2, scene.SCREEN_HEIGHT/2,scene.SCREEN_WIDTH, scene.SCREEN_HEIGHT);
		pScene = scene;
		physicsWorld = pw;
		BLOCK_WIDTH = pScene.BLOCK_WIDTH;
		BLOCK_SIZE = pScene.BLOCK_SIZE;
		borders.add(new Rectangle(pScene.SCREEN_WIDTH/2, pScene.SCREEN_HEIGHT, pScene.SCREEN_WIDTH, 1, pScene.vertexBufferObjectManager));
		borders.add(new Rectangle(pScene.SCREEN_WIDTH/2, 0, pScene.SCREEN_WIDTH, 1, pScene.vertexBufferObjectManager));
		borders.add(new Rectangle(0, pScene.SCREEN_HEIGHT/2, 1, pScene.SCREEN_HEIGHT, pScene.vertexBufferObjectManager));
		borders.add(new Rectangle(pScene.SCREEN_WIDTH, pScene.SCREEN_HEIGHT/2 ,1, pScene.SCREEN_HEIGHT, pScene.vertexBufferObjectManager));
		
		float block_length = BLOCK_WIDTH* BLOCK_SIZE;
		externalWalls.add(new Rectangle(pScene.SCREEN_WIDTH/2, pScene.SCREEN_HEIGHT/2+block_length, block_length*2, 2, pScene.vertexBufferObjectManager));
		externalWalls.add(new Rectangle(pScene.SCREEN_WIDTH/2-block_length, pScene.SCREEN_HEIGHT/2, 2, block_length*2, pScene.vertexBufferObjectManager));
		externalWalls.add(new Rectangle(pScene.SCREEN_WIDTH/2, pScene.SCREEN_HEIGHT/2-block_length, block_length*2, 2, pScene.vertexBufferObjectManager));
		externalWalls.add(new Rectangle(pScene.SCREEN_WIDTH/2+block_length, pScene.SCREEN_HEIGHT/2, 2, block_length*2, pScene.vertexBufferObjectManager));
		
		for(int i = 0; i < BLOCK_SIZE; i++){
			internalWallsH.add(new Rectangle(pScene.SCREEN_WIDTH/2, pScene.SCREEN_HEIGHT/2+BLOCK_WIDTH*i, block_length*2, 1, pScene.vertexBufferObjectManager));
			internalWallsH.add(new Rectangle(pScene.SCREEN_WIDTH/2, pScene.SCREEN_HEIGHT/2-BLOCK_WIDTH*i, block_length*2, 1, pScene.vertexBufferObjectManager));
			internalWallsV.add(new Rectangle(pScene.SCREEN_WIDTH/2+BLOCK_WIDTH*i, pScene.SCREEN_HEIGHT/2, 1, block_length*2, pScene.vertexBufferObjectManager));
			internalWallsV.add(new Rectangle(pScene.SCREEN_WIDTH/2-BLOCK_WIDTH*i, pScene.SCREEN_HEIGHT/2, 1, block_length*2, pScene.vertexBufferObjectManager));
		}
		
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		
		for(int i = 0 ; i<borders.size(); i++){
			PhysicsFactory.createBoxBody(physicsWorld, borders.get(i), BodyType.StaticBody, wallFixtureDef);
		}
		for(int i = 0 ; i<externalWalls.size(); i++){
			externalWallBodies.add(PhysicsFactory.createBoxBody(physicsWorld, externalWalls.get(i), BodyType.StaticBody, wallFixtureDef));
			this.attachChild(externalWalls.get(i));
		}
		for(int i = 0 ; i<internalWallsH.size(); i++){
			internalWallBodiesH.add(PhysicsFactory.createBoxBody(physicsWorld, internalWallsH.get(i), BodyType.StaticBody, wallFixtureDef));
			internalWallBodiesV.add(PhysicsFactory.createBoxBody(physicsWorld, internalWallsV.get(i), BodyType.StaticBody, wallFixtureDef));
			this.attachChild(internalWallsH.get(i));
			this.attachChild(internalWallsV.get(i));
			
		}
		
		
	}
	
	public void toggleInteralWalls(){
		for(int i = 0 ; i<internalWallBodiesH.size(); i++){
			internalWallBodiesH.get(i).setActive(false);
			internalWallBodiesV.get(i).setActive(false);
		}
	}
	
	
	@Override
	public void setRotation(float angle){
		super.setRotation(angle);
		float[] tempPoints;
		for(int i = 0 ; i<externalWallBodies.size(); i++){
			tempPoints = convertLocalCoordinatesToSceneCoordinates(externalWalls.get(i).getX(), externalWalls.get(i).getY());
			externalWallBodies.get(i).setTransform(tempPoints[0]/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, tempPoints[1]/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (float) (-angle*Math.PI/180));
		}
		for(int i = 0 ; i<internalWallBodiesH.size(); i++){
			tempPoints = convertLocalCoordinatesToSceneCoordinates(internalWallsH.get(i).getX(), internalWallsH.get(i).getY());
			internalWallBodiesH.get(i).setTransform(tempPoints[0]/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, tempPoints[1]/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (float) (-angle*Math.PI/180));
			tempPoints = convertLocalCoordinatesToSceneCoordinates(internalWallsV.get(i).getX(), internalWallsV.get(i).getY());
			internalWallBodiesV.get(i).setTransform(tempPoints[0]/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, tempPoints[1]/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (float) (-angle*Math.PI/180));
		}
	}

	public void toggleLidAndInternalWalls() {
		int lid_side = (((int)(this.getRotation()+1))/90)%4;
		Log.d("LID UP", ""+lid_side);
		externalWallBodies.get(lid_side).setActive(!externalWallBodies.get(lid_side).isActive());
		externalWalls.get(lid_side).setVisible(!externalWalls.get(lid_side).isVisible());
		for(int i = 0 ; i<internalWallBodiesH.size(); i++){
			internalWallBodiesH.get(i).setActive(!internalWallBodiesH.get(i).isActive());
			internalWallsH.get(i).setVisible(!internalWallsH.get(i).isVisible());
		}
		for(int i = 0 ; i<internalWallBodiesV.size(); i++){
			internalWallBodiesV.get(i).setActive(!internalWallBodiesV.get(i).isActive());
			internalWallsV.get(i).setVisible(!internalWallsV.get(i).isVisible());
		}

	}

}
