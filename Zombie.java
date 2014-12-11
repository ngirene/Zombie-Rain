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
	private double zombieSpeed;
	private boolean onGround;
	
	public static final int DEFAULT_ZOMBIE = 0;
	public static final double DEFAULT_HEALTH = 1.0;
	public static final double DEFAULT_SPEED = 1.0;
	
	public Zombie()
	{
		zombieTypeID = DEFAULT_ZOMBIE;
		zombieHealth = DEFAULT_HEALTH;
		zombieSpeed = DEFAULT_SPEED;
		onGround = false;
	}
	
	public abstract void doZombieAbility();
	
	public void walkTowards(Object player);
	
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
	
	public double getZombieHealth()
	{
		return zombieHealth;
	}
}
