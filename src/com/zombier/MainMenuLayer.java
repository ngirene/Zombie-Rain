package com.zombier;

import java.util.ArrayList;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

public class MainMenuLayer extends CCLayer{

	private static CGSize screenSize;
	private float generalscalefactor;
	protected ArrayList<CCSprite> _targets;
	CGPoint location;
	
	public MainMenuLayer() {
		
		screenSize = CCDirector.sharedDirector().winSize();
		generalscalefactor  = CCDirector.sharedDirector().winSize().height / 500 ;
		_targets = new ArrayList<CCSprite>();

		this.setIsTouchEnabled(true);

		CCSprite background = CCSprite.sprite("start.jpg");
		background.setScale(screenSize.width / background.getContentSize().width);
		background.setAnchorPoint(CGPoint.ccp(0f,1f)) ;
		background.setPosition(CGPoint.ccp(0, screenSize.height));

		CCSprite button_start = CCSprite.sprite("menu-new-game.png");
		button_start.setPosition(CGPoint.ccp(screenSize.width - 100*generalscalefactor, 100*generalscalefactor));

		addChild(button_start);
		_targets.add(button_start);
		
		CCSprite button_quit = CCSprite.sprite("you-lose.png");
		button_quit.setPosition(CGPoint.ccp(screenSize.width - 100*generalscalefactor, 35*generalscalefactor));
		
		addChild(button_quit);
		_targets.add(button_quit);
		
		CCSprite name = CCSprite.sprite("game-name.png");
		name.setPosition(CGPoint.ccp(screenSize.width / 2 , screenSize.height /4* generalscalefactor));

		addChild(name);
		_targets.add(name);

		addChild(background, -5);
	}
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event){

		location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		if(CGRect.containsPoint((_targets.get(0).getBoundingBox()), location)){
			System.out.println("new game apertado");
			CCScene scene = LevelOneLayer.scene(); //
			CCDirector.sharedDirector().replaceScene(scene);
		}
		else if(CGRect.containsPoint((_targets.get(1).getBoundingBox()), location)){
			System.out.println("quitting game");
			CCDirector.sharedDirector().end();
			android.os.Process.killProcess(android.os.Process.myPid());
			
		}

		return true;

	}
	
	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new MainMenuLayer();
		scene.addChild(layer);
		return scene;
	}

}
