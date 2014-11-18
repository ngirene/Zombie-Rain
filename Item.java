//just some starting code for the item parent class

public abstract class Item
{
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
