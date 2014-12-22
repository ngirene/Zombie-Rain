package com.zombier;

import java.util.Random;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

public abstract class Zombie
{
	//zombie ID will decide which type of zombie it is
	//0 will be the default "normal" zombie
	//1 will be the "armored" zombie
	//2 will be the "slender" zombie
	//3 will be the "rocket" zombie
	//4 will be the "boss" zombie
	private int zombieTypeID;
	private double zombieHealth;
	private double zombieSpeed;
	private boolean onGround;
	private float spawnTime;
	private boolean spawned;
	
	public static final int DEFAULT_ZOMBIE = 0;
	public static final double DEFAULT_HEALTH = 1.0;
	public static final double DEFAULT_SPEED = 1.0;
	
	CCSprite zombie;
	
	public Zombie()
	{
		zombieTypeID = DEFAULT_ZOMBIE;
		zombieHealth = DEFAULT_HEALTH;
		zombieSpeed = DEFAULT_SPEED;
		onGround = false;
		spawned = false;
		zombie = CCSprite.sprite("zombie.png");
	}
	
	public void spawnZombie(float xPos, float yPos)
	{
		zombie.setPosition(xPos, yPos);
	}
	
	public abstract void doZombieAbility();
	
	public abstract void walkTowards(Player p);
	
	//setters and getters
	public void setZombieTypeID(int newID)
	{
		zombieTypeID = newID;
	}
	
	public int getZombieTypeID()
	{
		return zombieTypeID;
	}
	
	public void setZombieHealth(int newHealth)
	{
		zombieHealth = newHealth;
	}
	
	public void setSpawnTime(float newSpawnTime)
	{
		spawnTime = newSpawnTime;
	}
	
	public void setSpawned(boolean value)
	{
		spawned = value;
	}
	
	public void setOnGround(boolean value)
	{
		onGround = value;
	}
	
	public double getZombieHealth()
	{
		return zombieHealth;
	}
	
	public CCSprite getZombieSprite()
	{
		return zombie;
	}
	
	public boolean getSpawned()
	{
		return spawned;
	}
	
	public float getSpawnTime()
	{
		return spawnTime;
	}
	
	public boolean getOnGround()
	{
		return onGround;
	}
}
