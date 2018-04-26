package spiel;

import figuren.Figur;

public class Feld {

	private int x;
	private int y;
	private Figur besetzt;
	
	public int getx() {
		return this.x;
	}

	public void setx(int x) {
		this.x =x;
	}
	
	public int gety() {
		return this.y;
	}

	public void sety(int y) {
		this.y =y;
	}
	
	
	
	
	public boolean istBesetzt() {	
	return besetzt != null;
	}
}


