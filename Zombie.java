//just some starting code for the zombie parent class

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
	
	public static final int DEFAULT_ZOMBIE = 0;
	public static final double DEFAULT_HEALTH = 1;
	
	public Zombie()
	{
		zombieTypeID = DEFAULT_ZOMBIE;
		zombieHealth = DEFAULT_HEALTH;
	}
	
	public abstract void doZombieAbility();
	
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
		rzombieHealth = newHealth;
	}
	
	public double getZombieHealth()
	{
		return zombieHealth;
	}
}
