package com.weiwei.rollingfruit;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

import com.weiwei.rollingfruit.SceneManager.SceneType;

public abstract class BaseScene extends Scene {
	
	protected int SCREEN_WIDTH;
    protected int SCREEN_HEIGHT;
    
	protected BaseGameActivity activity;
	protected Engine engine;
	protected Camera camera;
	protected VertexBufferObjectManager vertexBufferObjectManager;
	protected ResourceManager resourceManager;
	protected SceneManager sceneManager;
	
	public BaseScene() {
        resourceManager = ResourceManager.getInstance();
        activity = resourceManager.activity;
        vertexBufferObjectManager = activity.getVertexBufferObjectManager();
        engine = activity.getEngine();
        camera = engine.getCamera();
        SCREEN_WIDTH = (int) camera.getWidth();
        SCREEN_HEIGHT = (int) camera.getHeight();
        sceneManager = SceneManager.getInstance();
        createScene();
    }
	
	public abstract void createScene();
    public abstract void onBackKeyPressed();
    public abstract SceneType getSceneType();
    public abstract void disposeScene();
}
