import java.util.Arrays;

public class QuatroCel {

	int valNum;
	
	private int valX;
	private int valY;
	private int numLijnen=0;
	private boolean lijn[] = new boolean[4];
	private boolean hasNeighbor[] = new boolean[4];
	private int valNeighbor[] = new int[4];
	private String wieGezet[]  = new String[4];
	private String strQG = "";
	private boolean blDone = false; 
	
	public QuatroCel() {
		Arrays.fill(hasNeighbor, Boolean.TRUE);
	}
	
	// Set/Get het nummer
	public void setNum(int xx) { valNum = xx; }
	public int getNum() { return valNum; }
	
	// Set/Get Wie heeft het Quatro gehaald?
	public void setBehaaldDoor(String G) { strQG = G; }
	public String getBehaaldDoor() { return strQG; }
	
	// Set/Get of de lijn gezet is
	public void setLijn(int pos, boolean L) { lijn[pos]=L; }
	public boolean getLijn(int pos) {return lijn[pos]; }
		
	// Set/Get  wie de lijn heeft gezet
	public void setWie(int pos, String W) { wieGezet[pos]=W; }
	public String getWie(int pos) { return wieGezet[pos]; }

	// Set/Get of er een aanliggende cel is
	public void setHasNeighbor(int pos, boolean H) {hasNeighbor[pos]=H; }
	public boolean getHasNeighbor(int pos) {return hasNeighbor[pos]; }
	
	// Set/Get wat de aanliggende cel is (bij geen aanliggende cel, waarde 0)
	public void setAan(int pos, int xx) { valNeighbor[pos]=xx; }
	public int getAan(int pos) { return valNeighbor[pos]; }
	
	// Geef valNum als string
	public String valNumS() { return Integer.toString(valNum); }
		
	// Set/Get het x-coordinaat
	public void setX(int xx) { valX = xx; }
	public int getX() { return valX; }
	
	// Set/Get het y-coordinaat
	public void setY(int y) { valY = y; }
	public int getY() { return valY; }
	
	// =========
	//  methods
	// =========
	
	// Bereken en get het aantal lijnen
	void calcNumLijnen() {
		numLijnen=0;
		for (boolean i : lijn) {
			if (i) {numLijnen++;}
		}
	}
	
	// Bepaal het aantal gevulde lijnen
	public int getNumLijnen() {
		calcNumLijnen();
		return numLijnen;
	}
	
	// Get Is het Quatro vol?
	public boolean getDone() { 
		if (getNumLijnen()==4) { 
			blDone=true; 
		}
		return blDone; 
	}
	
	// Get Is het Quatro net vol gemaakt?
	public boolean getNewDone() {
		if (blDone == false) {
			if (getNumLijnen()==4) { 
				blDone=true; 
				return true;
			}
		}
		return false;
	}
	
		
}
