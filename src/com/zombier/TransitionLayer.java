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

public class TransitionLayer extends CCLayer{
	
	private static CGSize screenSize;
	protected ArrayList<CCSprite> _targets;
	CGPoint location;
	private static int nextLevel;
	
	public TransitionLayer(int level){
		nextLevel = level;
		screenSize = CCDirector.sharedDirector().winSize();
		_targets = new ArrayList<CCSprite>();

		this.setIsTouchEnabled(true);
		
		CCSprite background = CCSprite.sprite("next_level.jpg");
		background.setScale(screenSize.width / background.getContentSize().width);
		background.setAnchorPoint(CGPoint.ccp(0f,1f)) ;
		background.setPosition(CGPoint.ccp(0, screenSize.height));
		
		CCSprite next = CCSprite.sprite("next.png");
		next.setPosition(CGPoint.ccp(screenSize.width / 2, 3*screenSize.height/ 4));

		CCSprite return_main_menu = CCSprite.sprite("return_main_menu.png");
		return_main_menu.setPosition(CGPoint.ccp(screenSize.width / 2, screenSize.height/ 2));
		
		CCSprite quit = CCSprite.sprite("quit.png");
		quit.setPosition(CGPoint.ccp(screenSize.width / 2, screenSize.height/ 4));
		
		//0
		addChild(next);
		_targets.add(next);
		
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
			System.out.println("next");
			if(nextLevel == 2)
			{
				CCDirector.sharedDirector().replaceScene(LevelTwoLayer.scene());
			}
			else if(nextLevel == 3)
			{
				CCDirector.sharedDirector().replaceScene(LevelThreeLayer.scene());
			}
			else if(nextLevel == 4)
			{
				CCDirector.sharedDirector().replaceScene(LevelFourLayer.scene());
			}
			else if(nextLevel == 5)
			{
				CCDirector.sharedDirector().replaceScene(LevelFiveLayer.scene());
			}
			
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
	
	public static CCScene scene(int level)
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new TransitionLayer(level);
		scene.addChild(layer);
		return scene;
	}

}
