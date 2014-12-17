package com.zombier;

import java.util.ArrayList;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class PauseMenuLayer extends CCLayer{
	
	private static final int PAUSE_OVERLAY_TAG = 25;
	private static CGSize screenSize;
	private float generalscalefactor;
	protected ArrayList<CCSprite> _targets;
	CGPoint location;
	
	public PauseMenuLayer(){
	
		screenSize = CCDirector.sharedDirector().winSize();
		generalscalefactor  = CCDirector.sharedDirector().winSize().height / 500 ;
		_targets = new ArrayList<CCSprite>();

		this.setIsTouchEnabled(true);
		
		CCSprite background = CCSprite.sprite("pause_background.jpg");
		background.setScale(screenSize.width / background.getContentSize().width);
		background.setAnchorPoint(CGPoint.ccp(0f,1f)) ;
		background.setPosition(CGPoint.ccp(0, screenSize.height));
		
		CCSprite resume = CCSprite.sprite("resume.png");
		resume.setPosition(CGPoint.ccp(screenSize.width / 2, 3*screenSize.height/ 4));

		CCSprite return_main_menu = CCSprite.sprite("return_main_menu.png");
		return_main_menu.setPosition(CGPoint.ccp(screenSize.width / 2, screenSize.height/ 2));
		
		CCSprite quit = CCSprite.sprite("quit.png");
		quit.setPosition(CGPoint.ccp(screenSize.width / 2, screenSize.height/ 4));
		
		//0
		addChild(resume);
		_targets.add(resume);
		
		//1
		addChild(return_main_menu);
		_targets.add(return_main_menu);
		
		//2
		addChild(quit);
		_targets.add(quit);
		
		addChild(background, -5);
	}
	
	public boolean ccTouchesBegan(MotionEvent event){

		location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		if(CGRect.containsPoint((_targets.get(0).getBoundingBox()), location)){
			System.out.println("resume");
			CCDirector.sharedDirector().popScene();
		}
		else if(CGRect.containsPoint((_targets.get(1).getBoundingBox()), location)){
			System.out.println("return to main menu");
			CCScene scene = MainMenuLayer.scene(); //
			CCDirector.sharedDirector().replaceScene(scene);
		}
		else if(CGRect.containsPoint((_targets.get(2).getBoundingBox()), location)){
			System.out.println("quitting game");
			CCDirector.sharedDirector().end();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		return true;
	}
	
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new PauseMenuLayer();
		scene.addChild(layer);
		return scene;
	}

}
