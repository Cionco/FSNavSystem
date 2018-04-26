package figuren;

import spiel.Feld;

public class Bauer extends Figur
{

	public Bauer()
	{
		
	}
	
	public boolean checkMovement(Feld f)
	{
		boolean moveValid = false;
		Feld aktuellesFeld = getPosition();
		String team = getTeam();
		int multiplikator = 0;
		
		//Multiplikator f�r y-Koordinate (schwarze Bauern laufen in die entgegengesetzte Richtung wie wei�e Bauern)
		if (team == "schwarz")
		{
			multiplikator = 1;
		}
		else if (team == "wei�")
		{
			multiplikator = -1;
		}
		//Normaler Zug (eins nach vorne)
//		if()
//		{
//			moveValid = true;
//		}
//		
//		//Doppelter Zug (zwei nach vorne)
//		else if()
//		{
//			moveValid = true;
//		}
//		
//		//Schlag (diagonal nach vorne rechts / vorne links)
//		else if()
//		{
//			moveValid = true;
//		}
//		
//		else
//		{
//			moveValid = false;
//		}
		
		return moveValid;
	}
	
}
