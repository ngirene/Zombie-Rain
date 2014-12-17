package com.zombier;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import org.cocos2d.sound.SoundEngine;

public class MainActivity extends Activity {

	protected CCGLSurfaceView _glSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		_glSurfaceView = new CCGLSurfaceView(this);
		setContentView(_glSurfaceView);

		//
		CCDirector director = CCDirector.sharedDirector();
		director.attachInView(_glSurfaceView);
		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft); // set orientation
		CCDirector.sharedDirector().setDisplayFPS(true);  //display fps
		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);  //set frame rate

		CCScene scene = MainMenuLayer.scene(); //
		CCDirector.sharedDirector().runWithScene(scene);

	}
	
	@Override
	public void onStart()
	{
		SoundEngine.sharedEngine().preloadSound(CCDirector.sharedDirector().getActivity(), R.raw.main_menu_background_music);
		SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), R.raw.main_menu_background_music, true);
        super.onStart();
    }

	@Override
	public void onPause()
	{
		SoundEngine.sharedEngine().pauseSound();
		super.onPause();
		CCDirector.sharedDirector().pause();
	}

	@Override
	public void onResume()
	{
		SoundEngine.sharedEngine().resumeSound();
		super.onResume();
		
		CCDirector.sharedDirector().resume();
	}

	@Override
	public void onStop()
	{
		SoundEngine.sharedEngine().realesAllEffects();
        SoundEngine.sharedEngine().realesAllSounds();
        SoundEngine.purgeSharedEngine();
		super.onStop();
		CCDirector.sharedDirector().end();
	}

}
