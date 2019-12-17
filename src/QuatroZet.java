
public class QuatroZet {

	private int cellNum;
	private int cellLijn;
	private boolean hasNeighbor;
	private int cellNeighbor;
	
	public int cellZelf;
	public int cellNaast;
	private int cellNumZelf;
	private int cellNumNaast;
	
	public QuatroZet(int nC, int nL, boolean hasN, int nN) {
		setCellNum(nC);
		setCellLijn(nL);
		setCellHasNeighbor(hasN);
		if (hasNeighbor==true) { setCellNeighbor(nN); }
	}
	
	// Set/Get het nummer van de cel zelf
	public void setCellNum(int cN) { cellNum=cN; }
	public int getCellNum() { return cellNum; }
	
	// Set/Get het nummer van de lijn van de cel
	public void setCellLijn(int cL) { cellLijn=cL; }
	public int getCellLijn() { return cellLijn; }
	
	// Set/Get of de cel een aanliggende cel heeft (bij de kant van de lijn)
	public void setCellHasNeighbor(boolean cHN) { hasNeighbor = cHN; }
	public boolean getCellHasNeighbor() { return hasNeighbor; }
	
	// Set/Get het nummer van de aanliggende cel (0=geen)
	public void setCellNeighbor(int nN) { cellNeighbor = nN; }
	public int getCellNeighbor() { return cellNeighbor; }
	
	// Set/Get het type structuur waarbij de cell zelf hoort
	public void setCellZelf(int nZ) { cellZelf = nZ; }
	public int getCellZelf() { return cellZelf; }
	
	// Set/Get het type structuur waarbij de cell ernaast hoort
	public void setCellNaast(int nNa) { cellNaast = nNa; }
	public int getCellNaast() { return cellNaast; }
	
	// Set/Get het aantal lijnen van de cel zelf
	public void setCellNumZelf(int nZ) { cellNumZelf = nZ; }
	public int getCellNumZelf() { return cellNumZelf; }
	
	// Set/Get het type structuur waarbij de cell ernaast hoort
	public void setCellNumNaast(int nNa) { cellNumNaast = nNa; }
	public int getCellNumNaast() { return cellNumNaast; }
	
}
