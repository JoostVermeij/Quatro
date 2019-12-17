import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class QuatroVeld {

	private int numCellen = 0;
	private int numDim;	
	
	public String strAllowed[] = {"B", "R", "O", "L"};
	
	
	public ArrayList<QuatroCel> arrCellen = new ArrayList<QuatroCel>();
	
	public ArrayList<String> arrStructures = new ArrayList<String>();
	
	public ArrayList<QuatroZet> arrZetten = new ArrayList<QuatroZet>();
	
	
	public ArrayList<QuatroStructure> arrStructrs = new ArrayList<QuatroStructure>();
	
	
	public String arrVeld[][];
	

	// =============
	//  basis class
	// =============
	
	public QuatroVeld(int dimensie) {
		
		// Eerste cel, wordt niet gebruikt (wordt gemaakt zodat index 
		// (begint bij 0) gelijk is aan weergave voor speler).
		QuatroCel cel1 = new QuatroCel();
		arrCellen.add(cel1);
		
		// Veld opzetten
		numDim = dimensie;
		for (int y=1; y<=numDim; y++) {
			for (int x=1; x<=numDim; x++) {
				numCellen++;
				QuatroCel cel = new QuatroCel();
				
				// Sla de coordinaten op
				cel.setX(x);
				cel.setY(y);
				cel.setNum(numCellen);
				
				// Sla de (eventuele) cellen links en rechts op, en of deze er zijn
				if (x==1) { 
					cel.setAan(1, numCellen+1); 
					cel.setHasNeighbor(3, false); 
				} else { 
					if (x==numDim) { 
						cel.setAan(3, numCellen-1); 
						cel.setHasNeighbor(1, false); 
					} else {
						cel.setAan(3, numCellen-1); 
						cel.setAan(1, numCellen+1); // cel.setRechts(numCellen+1);
					}
				}
				
				// Sla de (eventuele) cellen onder en boven op, en of deze er zijn
				if (y==1) { 
					cel.setAan(2, numCellen+numDim); // cel.setOnder(numCellen+numDim);
					cel.setHasNeighbor(0, false);    // cel.setHasBoven(false); 
				} else { 
					if (y==numDim) { 
						cel.setAan(0, numCellen-numDim); // cel.setBoven(numCellen-numDim);
						cel.setHasNeighbor(2, false);    // cel.setHasOnder(false);
					} else {
						cel.setAan(0, numCellen-numDim); // cel.setBoven(numCellen-numDim);
						cel.setAan(2, numCellen+numDim); // cel.setOnder(numCellen+numDim);
					}
				}
				
				arrCellen.add(cel);
				
			}
		}
		
	}
	
	// ========================
	//  geavanceerde strategie
	// ========================
	
	public int getOpen3(int n) {
		if (arrCellen.get(n).getNumLijnen()==3) {
			for (int i=0; i<4; i++) {
				if (arrCellen.get(n).getLijn(i)==false) { return i; }
			}
		}
		return -1;	
	}
	
	public int[] getOpen2(int n) {
		int ii[] = new int[2];
		int ii_n = 0;
		if (arrCellen.get(n).getNumLijnen()==2) {
			for (int i=0; i<4; i++) {
				if (arrCellen.get(n).getLijn(i)==false) { 
					ii[ii_n]=i;
					ii_n++;
				}
			}
		}
		return ii;
	}
	
	public boolean areLinked(int n1, int n2) {
		if ((arrCellen.get(n1).getAan(0)==n2) && (arrCellen.get(n1).getLijn(0)==false)) { return true; }
		if ((arrCellen.get(n1).getAan(1)==n2) && (arrCellen.get(n1).getLijn(1)==false)) { return true; }
		if ((arrCellen.get(n1).getAan(2)==n2) && (arrCellen.get(n1).getLijn(2)==false)) { return true; }
		if ((arrCellen.get(n1).getAan(3)==n2) && (arrCellen.get(n1).getLijn(3)==false)) { return true; }
		return false;
	}
	
	public String advancedZet() {
		
		String strM = "";
		Random rand1 = new Random();
		Random rand2 = new Random();
		int n1, n2;
		
		analyseVeld();
		
		
		boolean clHasNeigh;
		int typeStrctZ;
		int typeStrctN;
		int nZetten=0;
		boolean dupl;
		
		for (int x=1; x <=numCellen; x++) {
			for (int y=0; y<4; y++) {
				if (arrCellen.get(x).getLijn(y)==false) {
					
					final int clNum=x;
					final int clLijn=y;
					
					clHasNeigh = arrCellen.get(x).getHasNeighbor(y);
					
					final int clNeigh = (clHasNeigh==true) ? (arrCellen.get(x).getAan(y)) : (0);
					
					// Check of de zet al in de lijst staat (omgedraaid)
					dupl=false;
					if (clHasNeigh==true) {
						for (int z=0; z<nZetten; z++) {
							if ((arrZetten.get(z).getCellNum()==clNeigh) && (arrZetten.get(z).getCellNeighbor()==clNum)) {
								dupl=true;
							}
						}
					}
					
					if (dupl==false) {
						
						QuatroZet zet1 = new QuatroZet(clNum,clLijn, clHasNeigh, clNeigh);
		
						typeStrctZ = arrStructrs.stream()
							.filter((QuatroStructure) -> QuatroStructure.containsNum(clNum)==true)
							.map((QuatroStructure) -> QuatroStructure.getType())
							.reduce(0, (int1, int2) -> int1+int2);
						
						if (clHasNeigh==true) {
							typeStrctN = arrStructrs.stream()
								.filter((QuatroStructure) -> QuatroStructure.containsNum(clNeigh)==true)
								.map((QuatroStructure) -> QuatroStructure.getType())
								.reduce(0, (int1, int2) -> int1+int2);
						} else {
							typeStrctN=0;
						}
						
						zet1.setCellZelf(typeStrctZ);
						zet1.setCellNaast(typeStrctN);
						
						zet1.setCellNumZelf(arrCellen.get(x).getNumLijnen());
						zet1.setCellNumNaast(arrCellen.get(arrCellen.get(x).getAan(y)).getNumLijnen());
						
						arrZetten.add(zet1);
						nZetten++;
					
					}
					
				}
				
			}
			
		}
		
		for (int x=0; x<nZetten; x++) {
			if (arrZetten.get(x).getCellZelf()==1) {
				
			}
		}
		
		
		
		
		
		
		
		ArrayList<Integer> arr3 = new ArrayList<Integer>();
		
		arr3 = arrCellen.stream() 
				.filter((QuatroCel) -> (QuatroCel.getNumLijnen()==3))
				.map((QuatroCel) -> QuatroCel.getNum())
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (arr3.isEmpty()==false) {
			
			int ii = getOpen3(arr3.get(0)); 
			strM = "_" + Integer.toString(arr3.get(0));
			
			switch (ii) {
			case 0:
				strM += "_B"; 
				break;
			case 1: 
				strM += "_R"; 
				break;
			case 2:
				strM += "_O";
				break;
			case 3:
				strM += "_L";
				break;
			default:
				strM = "";
				break;
			}
			
		} else {
	
			// Met twee random-variabelen zoeken naar een vrije zet
			do {
				n1 = rand1.nextInt(numDim*numDim)+1;
				n2 = rand2.nextInt(4);
				strM = "_" + Integer.toString(n1) + "_" + strAllowed[n2];
			} while (arrCellen.get(n1).getLijn(n2)==true);
			
			
		}
		
		return strM;
	}
	
	
	// Bepaal de 4, 3, 2 en 1 velden
	public void analyseVeld() {
		
		int struc1;
		int strucLast;
		int struc2;
		int strucFirst;
		boolean addS;
		boolean addedThisLoop;
		int tmpType=0;
		
		ArrayList<Integer> arr4 = new ArrayList<Integer>();
		ArrayList<Integer> arr3 = new ArrayList<Integer>();
		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		ArrayList<Integer> arr1 = new ArrayList<Integer>();
		ArrayList<Integer> arr0 = new ArrayList<Integer>();
		
		// Lijst resetten
		arrStructrs.clear();
		
		arr4 = arrCellen.stream() 
				.filter((QuatroCel) -> (QuatroCel.getNumLijnen()==4))
				.map((QuatroCel) -> QuatroCel.getNum())
				.collect(Collectors.toCollection(ArrayList::new));
		
		arr3 = arrCellen.stream() 
				.filter((QuatroCel) -> (QuatroCel.getNumLijnen()==3))
				.map((QuatroCel) -> QuatroCel.getNum())
				.collect(Collectors.toCollection(ArrayList::new));
		
		arr2 = arrCellen.stream() 
				.filter((QuatroCel) -> (QuatroCel.getNumLijnen()==2))
				.map((QuatroCel) -> QuatroCel.getNum())
				.collect(Collectors.toCollection(ArrayList::new));
		
		arr1 = arrCellen.stream() 
				.filter((QuatroCel) -> (QuatroCel.getNumLijnen()==1))
				.map((QuatroCel) -> QuatroCel.getNum())
				.collect(Collectors.toCollection(ArrayList::new));
		
		arr0 = arrCellen.stream() 
				.filter((QuatroCel) -> (QuatroCel.getNumLijnen()==0))
				.map((QuatroCel) -> QuatroCel.getNum())
				.collect(Collectors.toCollection(ArrayList::new));
		
		// Voeg Quatro's toe aan de lijst
		for (Iterator<Integer> iterator4 = arr4.iterator(); iterator4.hasNext();) {
			struc1 = iterator4.next();
			QuatroStructure QStruct = new QuatroStructure(struc1, 4);
			QStruct.setType(1); //4
			arrStructrs.add(QStruct);
			iterator4.remove();	
		}
		
		// Voeg structuren met een '3' toe aan de lijst
		while (arr3.isEmpty()==false) {
		
			// Neem de eerste 3, sla deze op en verwijder deze uit de lijst.
			struc1 = arr3.get(0);
			tmpType = 5; //3
			QuatroStructure QStruct = new QuatroStructure(struc1, 3);
			arr3.remove(0);
			
			// Check for 3-3
			for (Iterator<Integer> iterator2 = arr3.iterator(); iterator2.hasNext();) {
				struc2 = iterator2.next();
				if (struc2!=struc1) {
					if (areLinked(struc1, struc2)) {
						QStruct.addCellBack(struc2, 3);
						iterator2.remove();
						tmpType = 2; //33
						break;
					}
				}
			}
			
			// Check for 3-2...
			strucLast=struc1;
			do {
				addedThisLoop = false;
				for (Iterator<Integer> iterator2 = arr2.iterator(); iterator2.hasNext();) {
					struc2 = iterator2.next();
					if (areLinked(strucLast,struc2)) {
						addedThisLoop = true;
						strucLast = struc2;
						iterator2.remove();
						QStruct.addCellBack(struc2, 2);
						tmpType = 4; // 32...
					}
				}
			} while (addedThisLoop == true);
			
			// Check if list of 322.. is ended with a 3
			for (Iterator<Integer> iterator2 = arr3.iterator(); iterator2.hasNext();) {
				struc2 = iterator2.next();
				if (struc2 != struc1) {
					if (areLinked(strucLast,struc2)) {
						iterator2.remove();
						QStruct.addCellBack(struc2, 3);
						tmpType = 3; //32..23
						break;
					}
				}
			}
			
			// Structure opslaan
			QStruct.setType(tmpType);
			arrStructrs.add(QStruct);	
		}
		
		// Voeg structures van 2'en toe aan de lijst
		while (arr2.isEmpty()!=true) {
		
			// Neem de eerste 2, sla deze op en verwijder deze uit de lijst.
			struc1 = arr2.get(0);
			QuatroStructure QStruct = new QuatroStructure(struc1, 2);
			tmpType = 8;
			arr2.remove(0);
			
			// Variabelen voor de eerste en laatste 2 in de rij (begint met struc1)
			strucLast = struc1;
			strucFirst = struc1;
			
			// Kijk steeds opnieuw of een van de overgebleven 2's voor of achteraan de rij past.
			do {
				addedThisLoop = false;
				
				for (Iterator<Integer> iterator2 = arr2.iterator(); iterator2.hasNext();) {
					struc2 = iterator2.next();
					
					if (struc2!=struc1) {
						
						addS = false;
						
						if (areLinked(struc2, strucLast)) {
							strucLast=struc2;
							QStruct.addCellBack(struc2, 2);
							tmpType = 6;
							iterator2.remove();
							addS = true;
							addedThisLoop = true;
						}
						
						if (strucLast!=strucFirst) {
							if (addS==false) {
								if (areLinked(struc2, strucFirst)) {
									strucFirst = struc2;
									QStruct.addCellFront(struc2, 2);
									tmpType = 6;
									iterator2.remove();
									addedThisLoop = true;
								}
							}
						}
					
					}
				}
			} while (addedThisLoop==true);
			
			// Opslaan of de voor en achterkant van de rij verbonden zijn (en het dus een cirkel is)
			if (strucFirst!=strucLast) {
				if (areLinked(strucFirst,strucLast)) {
					QStruct.setIsRound(true);
					tmpType = 7;
				}
			}
			
			// Structure toevoegen aan lijst
			QStruct.setType(tmpType);
			arrStructrs.add(QStruct);
			
		} 
		
		// 1
		for (Iterator<Integer> iterator1 = arr1.iterator(); iterator1.hasNext();) {
			struc1 = iterator1.next();
			QuatroStructure QStruct = new QuatroStructure(struc1, 1);
			QStruct.setType(9);
			arrStructrs.add(QStruct);
			iterator1.remove();	
		}
		
		
		// 0	
		for (Iterator<Integer> iterator0 = arr0.iterator(); iterator0.hasNext();) {
			struc1 = iterator0.next();
			if (struc1!=0) {
				QuatroStructure QStruct = new QuatroStructure(struc1, 0);
				QStruct.setType(10);
				arrStructrs.add(QStruct);
				iterator0.remove();
			}
		}
		
		
	}

	// Vul het veld met cellen, aan de hand van de dimensie van het veld
	
	// =========
	//  Get/Set 
	// =========
	
	// Bepaal de dimensies van het bord
	public void setDim(int dimensie) { numDim=dimensie; }
	public int getDim( ) { return numDim; }
	
	// Bepaal wie het punt gehaald heeft
	public void setPunt(int lijnCel, String strPunt) { arrCellen.get(lijnCel).setBehaaldDoor(strPunt); }
	public String getPunt(int lijnCel) { return arrCellen.get(lijnCel).getBehaaldDoor(); }
		
	// Bepaal of een lijn gevuld is
	
		
	// =========
	//  Methods
	// =========
	
	public void setLijn(int lijnCel, String lijnPlek, String wieLijn) {
		
		if (lijnCel > numCellen) { return; }
		
		int numEigen; // de variabele voor de eigen plek
		int numNaast; // de variabele voor de cel er naast
		
		switch (lijnPlek) {
		case "L":
			numEigen=3;
			numNaast=1;
			break;
		case "R":
			numEigen=1;
			numNaast=3;
			break;
		case "O":
			numEigen=2;
			numNaast=0;
			break;
		case "B":
			numEigen=0;
			numNaast=2;
			break;
		default:
			return;
		}
		
		arrCellen.get(lijnCel).setLijn(numEigen, true);
		arrCellen.get(lijnCel).setWie(numEigen, wieLijn);
		if (arrCellen.get(lijnCel).getHasNeighbor(numEigen)) {
			arrCellen.get(arrCellen.get(lijnCel).getAan(numEigen)).setLijn(numNaast, true);
			arrCellen.get(arrCellen.get(lijnCel).getAan(numEigen)).setWie(numNaast, wieLijn);
		}
		
	}
	
	public boolean getLijn(int lijnCel, String lijnPlek) {
		switch (lijnPlek) {
			case "L": 
				return arrCellen.get(lijnCel).getLijn(3);
			case "R": 
				return arrCellen.get(lijnCel).getLijn(1);
			case "O": 
				return arrCellen.get(lijnCel).getLijn(2);
			case "B":	
				return arrCellen.get(lijnCel).getLijn(0);
			default: 
				return false;
		}	
	}
	
	// Bepaal of er Quatro's zijn, output als _x_x
	public String bepaalQuatros() {
		
		/**
		 *  bepaalQuatros controleert voor elk cijfer in het veld, 
		 *  of alle lijnen gevuld zijn (bv. bij 1: 1L, 1R, 1B en 1O). 
		 *  Zowel de 'normale' als eventuele aanliggende lijnen worden 
		 *  gecontroleerd.
		 */
		
		// controleer voor elk cijfer in het veld, of alle lijnen gevuld zijn.
		return arrCellen.stream()
				.filter((QuatroCel) -> QuatroCel.getDone()==true)
				.map((QuatroCel) -> QuatroCel.valNumS())
				.reduce("", (str1, str2) -> str1 + "_" + str2);
	}
	
	// Bepaal of en hoeveel Quatro's er zojuist gevuld zijn 
	
	public String checkVeld(String speler) {
		String strStandNew = bepaalNewQuatros();
		if (strStandNew!="") {
			// Punt gescoord door speler, nog een keer!
			String puntenGescoord[] = strStandNew.split("_");
			int nPG = puntenGescoord.length-1;
			
			switch (nPG) {
			case (1): // Een Quatro
				setPunt(Integer.parseInt(puntenGescoord[1]), speler);
				return "Quatro! Je mag nog een keer!";
			case (2): // Twee Quatro's
				setPunt(Integer.parseInt(puntenGescoord[1]), speler);
				setPunt(Integer.parseInt(puntenGescoord[2]), speler);
				return "Dubbel Quatro! Je mag nog een keer!";
			}
		}
		return "";
	}
	
	
	// Bepaal of er net een Quatro gevuld is
	public String bepaalNewQuatros() {
		// controleer voor elk cijfer in het veld, of alle lijnen gevuld zijn.
				return arrCellen.stream()
						.filter((QuatroCel) -> QuatroCel.getNewDone()==true)
						.map((QuatroCel) -> QuatroCel.valNumS())
						.reduce("", (str1, str2) -> str1 + "_" + str2);
	}
	
	// Geef of het vierkant vol is
	public boolean getVol() {
		return arrCellen.stream()
				.anyMatch((QuatroCel) -> QuatroCel.getDone()==false);
	}
	
	
	// Geef het aantal lijnen van een cel, aan de hand van het nummer van de cel
	public int getNum(int lijnCel) {
		return arrCellen.get(lijnCel).getNumLijnen();
	}

	// Geef het aantal lijnen van een cel, aan de hand van de coordinaten van de cel
	
	public int getNum(int lijnCelX, int lijnCelY) {
		
		final int xx = lijnCelX;
		final int yy = lijnCelY;
		
		Optional<QuatroCel> cell = arrCellen.stream()
				.filter((QuatroCel) -> ((QuatroCel.getX()==xx) && (QuatroCel.getY()==yy)))
			    .findFirst();
		
		return cell.get().getNumLijnen();
	}
	
	// Geef het totaal aantal lijnen in het veld
	
	public int getTotalNum() {
		return arrCellen.stream()
				.map((QuatroCel) -> QuatroCel.getNumLijnen())
				.reduce(0, (int1, int2) -> int1 + int2);
	}
	
	
	// Geef het aantal door 'speler' behaalde Quatro's (als een integer)
	public int getScore(String speler) {
		return (int) arrCellen.stream()
				.filter((QuatroCel) -> Objects.equals(QuatroCel.getBehaaldDoor(), speler))
				.count();
	}
	
	// Geef het aantal door 'speler' behaalde Quatro's (als een String)
	
	public String getScoreS(String speler) {
		return Long.toString( arrCellen.stream()
				.filter((QuatroCel) -> Objects.equals(QuatroCel.getBehaaldDoor(), speler))
				.count());
		
	}
	
	// Geef het aantal Quatro's die nog niet gevuld zijn
	
	public int getNumQuatroVrij() {
		return (int) arrCellen.stream()
				.filter((QuatroCel) -> (QuatroCel.getDone()==false))
				.count()-1;
	}
	
	
	// ========
	//  Output
	// ========
	
	// Het veld omzetten in een string
	public String outVeld(int dimensie) {
	
		// Veld gereed en leeg maken
		int dimArr = (1+(dimensie*4));
		String arrVeld[][] = new String[dimArr+1][dimArr+1];
		for (String rij[] : arrVeld) {
			Arrays.fill(rij, " ");
		}
		
		// Lijnen maken
		for (int Y = 1; Y <= dimensie+1; Y++) {
			for (int X = 1; X <= dimArr; X++) {
				arrVeld[1+((Y-1)*4)][X] = ".";	// Horizontale lijnen
				arrVeld[X][1+((Y-1)*4)] = ".";	// Verticale lijnen	
			}
		}
		
		// Cijfers er in zetten
		for (int Y = 1; Y <= dimensie; Y++) {
			for (int X = 1; X <= dimensie; X++) {
				arrVeld[3+(4*(Y-1))][3+(4*(X-1))] = Integer.toString((dimensie * (Y-1))+X);	
			}
		}
		
		int x1, x2, x3, y1, y2, y3, num; // Variabelen voor de drie vakjes van een gespeelde lijn
				
		for (int y=1; y <= dimensie; y++) {
			for (int x=1; x <= dimensie; x++) {
				
				num = x+(dimensie*(y-1));
				int numLijn = 4;
				
				if (arrCellen.get(num).getLijn(0)) {
					
					numLijn=0;
					
					x1 = (4*x)-2; 
					y1 = (4*y)-3;
					arrVeld[y1][x1]=arrCellen.get(num).getWie(numLijn);
					
					x2 = (4*x)-1;
					y2 = (4*y)-3;
					arrVeld[y2][x2]=arrCellen.get(num).getWie(numLijn);
					
					x3 = (4*x);
					y3 = (4*y)-3;
					arrVeld[y3][x3]=arrCellen.get(num).getWie(numLijn);
					
				}
				
				if (arrCellen.get(num).getLijn(2)) {
					
					numLijn=2;
					
					x1 = (4*x)-2; 
					y1 = (4*y)+1;
					arrVeld[y1][x1]=arrCellen.get(num).getWie(numLijn);
					
					x2 = (4*x)-1;
					y2 = (4*y)+1;
					arrVeld[y2][x2]=arrCellen.get(num).getWie(numLijn);
					
					x3 = (4*x);
					y3 = (4*y)+1;
					arrVeld[y3][x3]=arrCellen.get(num).getWie(numLijn);
					
				}
				
				if (arrCellen.get(num).getLijn(3)) {
					
					numLijn=3;
					
					x1 = (4*x)-3; 
					y1 = (4*y)-2;
					arrVeld[y1][x1]=arrCellen.get(num).getWie(numLijn);
					
					x2 = (4*x)-3;
					y2 = (4*y)-1;
					arrVeld[y2][x2]=arrCellen.get(num).getWie(numLijn);
					
					x3 = (4*x)-3;
					y3 = (4*y);
					arrVeld[y3][x3]=arrCellen.get(num).getWie(numLijn);
					
				}
				
				if (arrCellen.get(num).getLijn(1)) {
					
					numLijn=1;
					
					x1 = (4*x)+1;
					y1 = (4*y)-2;
					arrVeld[y1][x1]=arrCellen.get(num).getWie(numLijn);
					
					x2 = (4*x)+1;
					y2 = (4*y)-1;
					arrVeld[y2][x2]=arrCellen.get(num).getWie(numLijn);
					
					x3 = (4*x)+1;
					y3 = (4*y);
					arrVeld[y3][x3]=arrCellen.get(num).getWie(numLijn);
					
				}
				
				
				x1 = (4*x)-1; 
				y1 = (4*y)-1;
				
				if (arrCellen.get(num).getDone()) {
					// Quatro opslaan
					for (int xx = -1; xx <= 1; xx++) {
						for (int yy = -1; yy <= 1; yy++) {
							arrVeld[y1+yy][x1+xx] = arrCellen.get(num).getBehaaldDoor();
						}
					}
				}
			}
		}
				
		// Het veld omzetten in een String
		String tmpVeld = "";
		for (int Y = 1; Y <= ((dimensie*4)+1); Y++) {
			tmpVeld += " ";
			for (int X = 1; X <= ((dimensie*4)+1); X++) {
				tmpVeld += arrVeld[Y][X];
				if (arrVeld[Y][X].length()==1) { //corrigeren voor cijfers >9, nemen twee vakjes in beslag
					tmpVeld += " ";
				}
			}
			tmpVeld += "\n";
		}
		
	
		tmpVeld += "\n";
		
		analyseVeld();
		//tmpVeld += arrStructures.stream()
		//		.reduce("", (str1, str2) -> str1 + "\n" + str2);;
		
		/*
		tmpVeld += arrStructrs.stream()
				.map((QuatroStructure) -> QuatroStructure.getString())
				.reduce("", (str1, str2) -> str1 + "\n" + str2);
			*/	
				
		return tmpVeld;
	
	}
	
	
	
}