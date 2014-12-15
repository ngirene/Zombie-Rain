package com.zombier;

//just some starting code for the item parent class

public abstract class Item
{
	//item ID will decide which type of item it is
	//0 will be Invincibility
	//1 will be Wipe Out
	//2 will be Ground Level Wipe Out
	//3 will be Extra Heart
	//4 will be Unicorn Horn
	//5 will be Sidekick
	//6 will be Red Bull
	//7 will be 'MURICA/CHUCK NORRIS
	//10 will be Take Away Heart
	//11 will be Weak Shot
	//12 will be Immobilize
	//13 will be Decreased Range
	//20 will be Knives
	//21 will be Machine Gun
	//22 will be Bazooka
	private int itemID;
	
	public static final int DEFAULT_ID = 0;
	
	public Item()
	{
		itemID = DEFAULT_ID;
	}
	
	public abstract void displayItemSprite();
	public abstract void doItemAbility();
	
	public int getItemID()
	{
		return itemID;
	}
}
