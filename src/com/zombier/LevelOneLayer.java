package com.zombier;

import java.util.ArrayList;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.utils.javolution.MathLib;

import android.util.Log;
import android.view.MotionEvent;

public class LevelOneLayer extends CCLayer
{

	private static CGSize screenSize;
	private float generalscalefactor;
	protected ArrayList<CCSprite> _targets, hearts;
	CGPoint location;
	private float cordx, cordy;
	private Player p;
	private ArrayList<Zombie> zombies;
	CCScheduler curTime;
	
	private float previousSpawnTime;
	private long beginSystemTime;
	private long elapsedTime;
	
	private int numberOfZombies;

	public LevelOneLayer () 
	{
		p = new Player();
		numberOfZombies = 10;
		
		previousSpawnTime = 0;
		
		zombies = new ArrayList<Zombie>();
		
		for (int i = 0; i < numberOfZombies; i++)
		{
			zombies.add(new NormalZombie());
			float newSpawnTime = Math.abs(previousSpawnTime + (MathLib.random(1,5)));
			zombies.get(i).setSpawnTime(Math.abs(previousSpawnTime));
			Log.wtf("Spawn Time", "Spawn time set to " + Math.abs(previousSpawnTime));
			previousSpawnTime = newSpawnTime;
		}
		
		screenSize = CCDirector.sharedDirector().winSize();
		generalscalefactor  = CCDirector.sharedDirector().winSize().height / 500 ;
		_targets = new ArrayList<CCSprite>();
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.gunshot);
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.zombie_moan);

		this.setIsTouchEnabled(true);

		CCSprite background = CCSprite.sprite("back.jpg");
		background.setScale(screenSize.width / background.getContentSize().width);
		background.setAnchorPoint(CGPoint.ccp(0f,1f)) ;
		background.setPosition(CGPoint.ccp(0, screenSize.height));

		CCSprite _player = CCSprite.sprite("stop.png");
		_player.setPosition(CGPoint.ccp(screenSize.width / 2, 50));

		CCSprite button_left = CCSprite.sprite("left.png");
		button_left.setPosition(CGPoint.ccp(25*generalscalefactor, 25*generalscalefactor));

		CCSprite button_right = CCSprite.sprite("right.png");
		button_right.setPosition(CGPoint.ccp(screenSize.width - 25*generalscalefactor, 25*generalscalefactor));
		
		CCSprite srl = CCSprite.sprite("srl.png");
		CCSprite std = CCSprite.sprite("std.png");
		
		srl.setPosition(CGPoint.ccp(-100, -100));
		std.setPosition(CGPoint.ccp(-100, -100));
		
		CCSprite pause = CCSprite.sprite("pause.png");
		pause.setPosition(CGPoint.ccp(screenSize.width - 100*generalscalefactor, 25*generalscalefactor));
		
		CCSprite level = CCSprite.sprite("Level-1.png");
		level.setPosition(CGPoint.ccp(screenSize.width /2, screenSize.height - level.getBoundingBox().size.height-50));
		
		//CCSprite vd = CCSprite.sprite("vd.png");
		//CCSprite vt = CCSprite.sprite("vt.png");

		//0
		addChild(_player);
		_targets.add(_player);

		//1
		addChild(button_left);
		_targets.add(button_left);

		//2
		addChild(button_right);
		_targets.add(button_right);
		
		//3
		addChild(srl);
		_targets.add(srl);
		
		//4
		addChild(std);
		_targets.add(std);
		
		//5
		addChild(pause);
		_targets.add(pause);
		
		//6
		addChild(level);
		_targets.add(level);
		
		//5
		//addChild(vt);
		//_targets.add(vt);
		
		//6
		//addChild(hl);
		//_targets.add(hl);

		addChild(background, -5);
		generateHearts();

		beginSystemTime = System.nanoTime();
		Log.wtf("Begin Time", "Got beginning time");
		
		this.schedule("calculateElapsedTime", 0.5f);
		this.schedule("checkZombieSpawn", 1.0f);
		this.schedule("checkHearts", 0.5f);
	}
	
	public void moveZombie(CCSprite zombieSprite)
	{
		CCMoveTo actionMove = CCMoveTo.action(10, CGPoint.ccp(zombieSprite.getPosition().x, zombieSprite.getBoundingBox().size.height - 70 ));
        zombieSprite.runAction(actionMove);
		//CCAction actionTo = CCMoveTo.action(5.0f, CGPoint.make(sprite.getAnchorPoint().x, 0));
		//sprite.runAction(actionTo);
	}
	
	public void checkHearts(float dt)
	{
		if(zombies.size()==0 && p.getHearts()>0)
		{
			CCScene scene = TransitionLayer.scene(2); //
			CCDirector.sharedDirector().replaceScene(scene);
		}
		
		for(int i = 0; i<zombies.size(); i++)
		{
			if(CGRect.intersects(zombies.get(i).getZombieSprite().getBoundingBox(), _targets.get(0).getBoundingBox()))
			{
				if(p.getHearts()>0)
				{
					p.setHearts(p.getHearts()-1);
					generateHearts();
					removeZombies(i);
					if(p.getHearts()==0) //game over
					{
						CCScene scene = GameOverLayer.scene(); //
						CCDirector.sharedDirector().replaceScene(scene);
					}
				}
			}
		}
	}
	
	public void removeZombies(int z)
	{
		zombies.get(z).getZombieSprite().removeFromParentAndCleanup(true);
		zombies.remove(z);
	}

	public void checkZombieSpawn(float dt)
	{
		//Log.wtf("Check", "Checking Zombie Spawn . . .");
		for (int i = 0; i < numberOfZombies; i++)
		{
			if ((elapsedTime >= zombies.get(i).getSpawnTime()) && (zombies.get(i).getSpawned() == false))
			{
				zombies.get(i).setSpawned(true);
				zombies.get(i).spawnZombie(MathLib.random(zombies.get(i).getZombieSprite().getBoundingBox().size.width, screenSize.width), screenSize.height);
				addChild(zombies.get(i).getZombieSprite());
				_targets.add(zombies.get(i).getZombieSprite());
				SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.zombie_moan);
				moveZombie(zombies.get(i).getZombieSprite());	
				
			}
		}
	}

	public void calculateElapsedTime(float dt)
	{
		long currentSystemTime = System.nanoTime();
		
		elapsedTime = ((currentSystemTime - beginSystemTime) / 10000000) / 100;
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event){
		
		location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		cordx = location.x;
		cordy = location.y;
		
		if(CGRect.containsPoint((_targets.get(1).getBoundingBox()), location)){
			System.out.println("esquerdo_apertado");
			if(_targets.get(0).getPosition().x < 35){
				System.out.println("Stop_esquerdo_walk");
			}
			else{
				CCMoveTo move_left = CCMoveTo.action(0.5f, CGPoint.ccp(_targets.get(0).getPosition().x - 50, 50));
				_targets.get(0).runAction(move_left);
				System.out.println("esquerdo_walk");
			}
		}
		else if(CGRect.containsPoint((_targets.get(2).getBoundingBox()), location)){
			System.out.println("direito_apertado");
			if(_targets.get(0).getPosition().x > screenSize.width - 35){
				System.out.println("Stop_Direita_walk");
			}
			else{
				CCMoveTo move_right = CCMoveTo.action(0.5f, CGPoint.ccp(_targets.get(0).getPosition().x + 50, 50));
				_targets.get(0).runAction(move_right);
				System.out.println("direita_walk");
			}
		}
		else if(CGRect.containsPoint((_targets.get(5).getBoundingBox()), location)){
			System.out.println("launch pause menu");
			CCScene scene = PauseMenuLayer.scene(); //
			CCDirector.sharedDirector().pushScene(LevelOneLayer.scene());
			CCDirector.sharedDirector().replaceScene(scene);
		}
		else{
			//_targets.get(3).setVisible(true);
			//_targets.get(4).setVisible(true);
			
			CCSprite srl = CCSprite.sprite("srl.png");
			CCSprite std = CCSprite.sprite("std.png");
			
			srl.setPosition(CGPoint.ccp(-100, -100));
			std.setPosition(CGPoint.ccp(-100, -100));
			
			//3
			addChild(srl);
			_targets.set(3, srl);
			
			//4
			addChild(std);
			_targets.set(4, std);
		}

		return true;

	}

    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
    	// TODO Auto-generated method stub
    	
    	location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		if(cordy - 7.5 >= location.y){
			System.out.println("desceu");
			_targets.get(3).setVisible(false);
			_targets.get(4).setVisible(true);
			CCMoveTo move_vd = CCMoveTo.action(0.1f, CGPoint.ccp(location.x, location.y));
			_targets.get(4).runAction(move_vd);
			//_targets.get(4).setPosition(CGPoint.ccp(location.x, location.y));
	
		}else if(cordy + 7.5 <= location.y ){
			System.out.println("subiu");
			_targets.get(3).setVisible(false);
			_targets.get(4).setVisible(true);
			CCMoveTo move_vt = CCMoveTo.action(0.1f, CGPoint.ccp(location.x, location.y));
			_targets.get(4).runAction(move_vt);
			//_targets.get(4).setPosition(CGPoint.ccp(location.x, location.y));
		}
		if(cordx - 7.5 >= location.x){
			System.out.println("esquerda");
			_targets.get(3).setVisible(true);
			_targets.get(4).setVisible(false);
			CCMoveTo move_hl = CCMoveTo.action(0.1f, CGPoint.ccp(location.x, location.y));
			_targets.get(3).runAction(move_hl);
			//_targets.get(4).setPosition(CGPoint.ccp(location.x, location.y));

		}else if(cordx + 7.5 <= location.x ){
			System.out.println("direita");
			_targets.get(3).setVisible(true);
			_targets.get(4).setVisible(false);
			CCMoveTo move_hr = CCMoveTo.action(0.1f, CGPoint.ccp(location.x, location.y));
			_targets.get(3).runAction(move_hr);
			//_targets.get(4).setPosition(CGPoint.ccp(location.x, location.y));

		}
		cordx = location.x;
    	cordy = location.y;
    	
    	for(int i = 0; i<zombies.size(); i++)
    	{
    		if(zombies.get(i).getZombieSprite().getBoundingBox().contains(cordx, cordy))
    		{
    			removeZombies(i);
    			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.gunshot);
    			
    		}
    	}
    	
		//_targets.get(3).setPosition(CGPoint.ccp(location.x, location.y));
    	return super.ccTouchesMoved(event);
    	
    }
    
    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
    	// TODO Auto-generated method stub
    	location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
		_targets.get(4).removeSelf();
		_targets.get(3).removeSelf();
    	return super.ccTouchesEnded(event);
    }
    
    public void generateHearts()
    {
    	if(hearts != null)
    	{
    		for(int i = 0; i<hearts.size(); i++)
    		{
    			removeChild(hearts.get(i),true);
    		}
    	}
    	
    	hearts = new ArrayList<CCSprite>();
    	
    	for(int i = 0; i<p.getHearts(); i++)
    	{
    		System.out.println("hearts: " + p.getHearts());
    		CCSprite heart = CCSprite.sprite("heart.png");
    		heart.setPosition(CGPoint.ccp(heart.getBoundingBox().size.width*(i+1)+10, screenSize.height - heart.getBoundingBox().size.height));
    		addChild(heart);
    		hearts.add(heart);
    	}
    }

	public static CCScene scene()
	{
		CCScene scene = CCScene.node();
		CCLayer layer = new LevelOneLayer();
		scene.addChild(layer);
		return scene;
	}

}
