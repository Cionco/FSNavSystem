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
		boolean moveExecutable = checkMovement(f);
		
		if (moveExecutable = true)
		{
			setPosition(f);
			moveDone = true;
		}
		
		else 
		{
			moveDone = false;
		}
		
		return moveDone;
	}
	
	public abstract boolean checkMovement(Feld f);

	public Feld getPosition() {
		return position;
	}

	public void setPosition(Feld position) {
		this.position = position;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
}
