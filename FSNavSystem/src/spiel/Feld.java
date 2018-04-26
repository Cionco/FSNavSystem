package spiel;

import figuren.Figur;

public class Feld {

	private int x;
	private int y;

	private Figur besetzt;
	
	public boolean istBesetzt() {	
	return besetzt != null;
	}
}


