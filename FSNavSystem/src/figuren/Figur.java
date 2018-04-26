package figuren;

import spiel.Feld;

public abstract class Figur 
{
	private Feld position;
	private String team;

	public Figur()
	{
		
	}
	
	public boolean move(Feld f)
	{
		boolean moveDone = false;
		return moveDone;
	}
	
	public abstract boolean checkMovement(Feld f);
	
}
