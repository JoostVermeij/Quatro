import java.util.Arrays;

public class QuatroStructure {

	//public ArrayList<QuatroCel> arrStructures = new ArrayList<QuatroCel>();
	
	private int numCellen;
	private int intCellen[];
	private int intVulling[];
	private boolean isRound;
	private int intType;
	
	public QuatroStructure(int intC, int intV) {
		numCellen = 1;
		intCellen = new int[1];
		intCellen[0] = intC;
		intVulling = new int[1];
		intVulling[0] = intV;
		isRound=false;
		intType=0;
	}
	
	public boolean containsNum(int num) {
		for(int strc : intCellen) {
			if (strc == num) { return true; }
		}
		return false;
	}
	
	public void addCellBack(int intC, int intV) {
		int tmpC[] = new int[numCellen];
		int tmpV[] = new int[numCellen];
		for (int i=0; i<numCellen; i++) {
			tmpC[i] = intCellen[i];
			tmpV[i] = intVulling[i];
		}
		numCellen++;
		intCellen = new int[numCellen];
		intVulling = new int[numCellen];
		for (int i=0; i<(numCellen-1); i++) {
			intCellen[i] = tmpC[i];
			intVulling[i] = tmpV[i];
		}
		intCellen[numCellen-1] = intC;
		intVulling[numCellen-1] = intV;
	}
	
	public void addCellFront(int intC, int intV) {
		int tmpC[] = new int[numCellen];
		int tmpV[] = new int[numCellen];
		for (int i=0; i<numCellen; i++) {
			tmpC[i] = intCellen[i];
			tmpV[i] = intVulling[i];
		}
		numCellen++;
		intCellen = new int[numCellen];
		intVulling = new int[numCellen];
		intCellen[0] = intC;
		intVulling[0] = intV;
		for (int i=1; i<numCellen; i++) {
			intCellen[i]=tmpC[i-1];
			intVulling[i]=tmpV[i-1];
		}
	}
	
	public void setType(int T) {intType = T;}
	public int getType() {return intType; }
	
	public String getString() {
		return Arrays.toString(intCellen) + " - " + Arrays.toString(intVulling) + " - " + getType();
	}
	
	// Set/Get the number of Cells in this structure
	public void setNumCellen(int nC) {numCellen=nC; }
	public int getNumCellen() { return numCellen; }
	
	// Get an integer array, representing the numbers of the cells in this tructure
	public int[] getIntCellen() { return intCellen; }
	
	// Get an integer array, representing the content of the cells in this tructure
	public int[] getIntVullign() { return intVulling; }
	
	public void setIsRound(boolean iR) {isRound=iR; }
	public boolean getIsRound() {return isRound; }
	
}
