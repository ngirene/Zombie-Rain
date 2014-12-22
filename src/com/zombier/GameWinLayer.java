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

public class GameWinLayer extends CCLayer{
	
	private static CGSize screenSize;
	protected ArrayList<CCSprite> _targets;
	CGPoint location;
	
	public GameWinLayer(){
	
		screenSize = CCDirector.sharedDirector().winSize();
		_targets = new ArrayList<CCSprite>();

		this.setIsTouchEnabled(true);
		
		CCSprite background = CCSprite.sprite("win_game.jpg");
		background.setScale(screenSize.width / background.getContentSize().width);
		background.setAnchorPoint(CGPoint.ccp(0f,1f)) ;
		background.setPosition(CGPoint.ccp(0, screenSize.height));
		
		CCSprite return_main_menu = CCSprite.sprite("return_main_menu.png");
		return_main_menu.setPosition(CGPoint.ccp(screenSize.width / 2, screenSize.height/ 2));
		
		CCSprite quit = CCSprite.sprite("quit.png");
		quit.setPosition(CGPoint.ccp(screenSize.width / 2, screenSize.height/ 4));
		
		CCSprite win = CCSprite.sprite("You-Win.png");
		win.setPosition(CGPoint.ccp(screenSize.width / 2, 3*screenSize.height/ 4));
		
		//0
		
		addChild(return_main_menu);
		_targets.add(return_main_menu);
		
		//1
		addChild(quit);
		_targets.add(quit);
		
		//2
		addChild(win);
		_targets.add(win);
		
		addChild(background, -5);
	}
	
	public boolean ccTouchesBegan(MotionEvent event){

		location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));

		if(CGRect.containsPoint((_targets.get(0).getBoundingBox()), location)){
			System.out.println("return to main menu");
			CCScene scene = MainMenuLayer.scene(); //
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
		CCLayer layer = new GameWinLayer();
		scene.addChild(layer);
		return scene;
	}

}
