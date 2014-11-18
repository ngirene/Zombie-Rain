//just some starting code for the zombie parent class

public abstract class Zombie
{
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
	
	public int getZombieTypeID()
	{
		return zombieTypeID;
	}
	
	public double getZombieHealth()
	{
		return zombieHealth;
	}
}
