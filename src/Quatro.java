//UTIL
import java.util.Random;
import java.util.Arrays;
import java.util.Objects;
//AWT
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

//SWING
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout;

//SWING.text
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

@SuppressWarnings("serial")
public class Quatro extends JFrame implements KeyListener {
	
	// ===========================
	//  Variabelen en componenten
	// ===========================
	
	// Swing componenten
	public JTextField txtInput;
	public JLabel lblAntwoord;
	public JLabel lblTitel;
	public JLabel lblScore;
	public JScrollPane scrollOutput;
	public JScrollPane scrollCom;
	public JTextPane txtOutput;
	public JTextPane txtCom;
	
	// Eigen class
	public QuatroVeld QVeld;
	
	// Spelvariabelen
	public String naamSpeler;
	public int bordFormaat;
	public String veldQuatro;
	public int gameStap;
	public String zetSpel;
    public String strOutput;
    
	// Namen die al een functie hebben in Java (keywords zijn)
	public String keyWords[] = { "abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while" };
	
	// Beschikbare groottes voor het speelveld
	public String arrVelden[] = {"2", "3", "4", "5", "6"};
	
	// Beschikbare zetten (Boven, Onder, Links, Rechts)
	public String strAllowed[] = {"B", "O", "L", "R"};
	
	public enum LijnOpties {
		L, R, O, B
	};
	
	// ==================
	//  Main componenten
	// ==================
	
	public static void main(String[] args) {
		
		/**
		 * In de main method wordt Quatro opgestart als GUI.
		 */
		
		// Start Quatro
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Quatro().setVisible(true);
            }
        });
		
	}
	
	public Quatro()  {
		
		/**
		 * Quatro zet het Window klaar, geeft de openingswoorden weer en zorgt dat de speler input kan geven.
		 */
		
		// Zet de GUI klaar
		maakWindow();
		
		// Zet het spel klaar
		gameStap = 0;
		outputNaarPane(txtCom, "Dag speler! Wat leuk dat je meespeelt.\n", Color.RED);
		outputNaarPane(txtCom, "Mag ik om te beginnen je naam weten?", Color.RED);
		outputNaarPane(txtCom, "\n > ", Color.BLUE);
		
		// Zorg dat de speler met 'enter' het spel verder speelt
		txtInput.addKeyListener(this);
		
	}
	
	public void maakWindow() {
			
		/**
         * In maakWindow wordt de GUI opgezet. Een textveld waarin het programma communiceert, 
         * een textveld waarin het veld wordt opgezet en een inputbox waarin de speler antwoord geeft.
         */
		
		// Creeer GUI-componenten
		txtCom = new JTextPane();
		scrollCom = new JScrollPane(txtCom);
		
		txtOutput = new JTextPane();
		txtOutput.setFont(new Font("Courier New", Font.PLAIN, 14));
		scrollOutput = new JScrollPane(txtOutput);
        
		txtInput = new JTextField();
		
		lblTitel = new JLabel();
		lblTitel.setFont(new Font("Courier New", Font.BOLD, 36));
		lblTitel.setText("QUATRO");
        
		lblScore = new JLabel();
		lblScore.setFont(new Font("Courier New", Font.PLAIN, 24));
		lblScore.setText("Speler | 0 : 0 | Spel");
        
		lblAntwoord = new JLabel();
		lblAntwoord.setText("Antwoord:");
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quatro");
        
        // Afmetingen txtOutput
        int widthN = 800;
        int heightN = 400;
  
        // Maak de grouplayout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
	    
        // Maak de horizontale groep
        layout.setHorizontalGroup(
		   layout.createSequentialGroup()
		   	  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		   			.addGroup(layout.createSequentialGroup()
		   					.addComponent(lblTitel)
		   					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
		 		           .addComponent(lblScore))
		   			.addComponent(scrollCom)
		   			.addGroup(layout.createSequentialGroup()
		   				.addComponent(lblAntwoord)
		   				.addComponent(txtInput))
		   			.addComponent(scrollOutput, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, widthN, Short.MAX_VALUE))
		);
        
        // Maak de verticale groep
        layout.setVerticalGroup(
           layout.createSequentialGroup()
           	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		           .addComponent(lblTitel)
		           .addComponent(lblScore))
           .addComponent(scrollCom, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
		   	  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		           .addComponent(lblAntwoord)
		           .addComponent(txtInput))
		      .addComponent(scrollOutput, GroupLayout.DEFAULT_SIZE, heightN, Short.MAX_VALUE)
		);
        
	    pack();
	    
	    // Zet het scherm bovenaan, in het midden van het scherm
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	    int x = (int) ((rect.getMaxX() - getWidth()) / 2);
	    setLocation(x, 0);
	    
	    // De focus zetten op txtInput (waar de speler de antwoorden typt)
	    txtInput.requestFocusInWindow();
	        
	}
	
	// ============
	//  key Events
	// ============
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
	    if (key == KeyEvent.VK_ENTER) {
	    	// Bij 'Enter' gaat het spel verder met de nieuwe invoer
	    	playGame(txtInput.getText());
	    }
	}
	
	// ==================
	//  Game componenten
	// ==================
	
	public void playGame(String strInput) {
		
		/**
		 * playGame verzorgt het verloop van het spel. Eerst worden de benodigde gegevens 
		 * verzameld (naam, grootte van het bord) en daarna wordt het bord opgezet. Daarna wordt
		 * om de beurt de speler en het spel aan zet gelaten. Tenslotte wordt het spel afgesloten 
		 * als er geen zetten meer mogelijk zijn en - zo gewenst - het spel opnieuw gestart of het
		 * spel afgesloten.
		 */
	
		strOutput = "";
		
		// Geef de gegeven input weer
		outputNaarPane(txtCom, strInput + "\n", Color.BLUE);
		
		// Kies het juiste spelonderdeel op basis van de variabele gameStap
		switch (gameStap) {
		
			// Opslaan naam van de speler
			case 0: 
				
				// Controleren of naam is toegestaan
				if (!inArray(keyWords, strInput)) { 
					// naamSpeler opslaan, output maken op basis van input en vraag stellen voor volgende stap
					naamSpeler = strInput;
					strOutput = "Dag " + naamSpeler + "! \n";
					strOutput += "Hoe groot wil je dat het bord is waarop we vandaag spelen? \n ";
					strOutput += "Voer een cijfer van " + arrVelden[0] + " tot " + arrVelden[arrVelden.length-1] +" in, het bord krijgt deze afmetingen.";
					// Naar de volgende stap
					gameStap++;
				} else { 
					// Incorrecte invoer, output maken, niet naar volgende stap
					strOutput = "Oei, die naam is al in gebruik door de overlords. Heb je nog een alias?";
				}
				break;
				
			// Opslaan grootte van het bord
			case 1: 
				
				// Controleren of grootte is toegestaan
				if (inArray(arrVelden,strInput)) {
					
					// bordFormat opslaan, output maken op basis van input en vraag stellen voor volgende stap
					bordFormaat = Integer.parseInt(strInput);
					strOutput = "Ok, we spelen vandaag op een bord van " + bordFormaat + "x" + bordFormaat + ".\n";
					
					startSpel();
					
				} else {
					
					// Incorrecte invoer, output maken, niet naar volgende stap
					strOutput = "Oei, dat is niet een toegestaan nummer (was het eigenlijk wel een nummer?)";
				}
				break;
			
			// Begin van het spel
			case 2: 
				
				String strChk;
				String strZet = langZet(strInput);
				
				// Controleren of zet toegestaan is 
				if (!zetToegestaan(strZet)) {
					
					strOutput = "Oei, die zet is niet toegestaan.";
				
				} else {
					
					//  Controleren of zet vrij is
					if (!zetVrij(strZet)) {
						
						strOutput = "Oei, die zet is al eerder gedaan.";
						
					} else {
					
			    		// Lijn zetten
			    		int zetN = Integer.parseInt(langZet(strInput).split("_")[1]);
			    		String zetL = langZet(strInput).split("_")[2];
			    		QVeld.setLijn(zetN, zetL, "p");
			    		
			    		// Controleren of er een of meerdere Quatro's gemaakt zijn
			    		strChk = QVeld.checkVeld("p");
			    		if (!Objects.equals(strChk,"")) {
			    			
			    			// Resultaat weergeven
			    			strOutput += strChk;
			    			
			    			// Controleer of het veld vol is, zo ja veld weergeven en naar volgende stap (einde spel)
	    					checkEindSpel();
			    					
		    			} else {
		    			
			    			// Geen punt gescoord door speler, spel aan zet
			    		
			    			// Controleer of het veld vol is, zo ja veld weergeven en naar volgende stap (einde spel)
		    				checkEindSpel();
			    			
				    		// Het spel is aan de beurt
					    	do {
				    			
					    		// Nieuwe zet bepalen
					    		zetSpel = QVeld.advancedZet();
						    	
					    		// Lijn zetten
					    		int zetNN = Integer.parseInt(zetSpel.split("_")[1]);
					    		String zetLL = zetSpel.split("_")[2];
					    		QVeld.setLijn(zetNN, zetLL, "g");
					    		
					    		// Gekozen zet weergeven
					    		strOutput += "Het spel kiest de zet: " + kortZet(zetSpel);
						    	
					        	// Controleren of er een of meerdere Quatro's gemaakt zijn
					        	strChk = QVeld.checkVeld("g");
					    		if (!Objects.equals(strChk,"")) {
					    			
					    			// Resultaat weergeven
					    			strOutput += "\n" + strChk;
					    			
					    			// Controleer of het veld vol is, zo ja veld weergeven en naar volgende stap (einde spel)
					    			checkEindSpel();
					        	
						    	}
						    	
					    	} while (strChk!="");
					    	
			    		}
				
				    	// Veld weergeven
			    		veldQuatro = QVeld.outVeld(bordFormaat);
			    		weergaveQuatro(txtOutput,veldQuatro);
			    		
			    		// Score weergeven
			    		lblScore.setText("Speler | "+ QVeld.getScoreS("p") + " : " + QVeld.getScoreS("g") + " | Spel");
		    			
					}
				}
	        	break;
			
		}
		
		// Weergeven output en resetten txtInput
		outputNaarPane(txtCom, strOutput, Color.RED);
		outputNaarPane(txtCom, "\n > ", Color.BLUE);
		txtInput.setText("");
		txtInput.requestFocusInWindow();
		
	}	
	
	public void startSpel() {
		
		/**
		 * startSpel zet de spelvariabelen (weer) terug naar de beginpositie, om een spel te starten
		 */
		
		// Nieuw Veld klaarzetten
		QVeld = new QuatroVeld(bordFormaat);
		
		// Klaarzetten/resetten output
		strOutput = "Ok! Veel succes, jij mag beginnen. \n";
		strOutput += "Geef steeds je zet als een cijfer en een lijn (O=Onder, B=Boven, L=Links, R=Rechts), bv. 1B of 23R";
		txtCom.setText("");
		
		// Weergeven beginscore
		lblScore.setText("Speler | "+ QVeld.getScoreS("p") + " : " + QVeld.getScoreS("g") + " | Spel");
		
		// Weergeven leeg veld
		veldQuatro = QVeld.outVeld(bordFormaat);
		weergaveQuatro(txtOutput, veldQuatro);
		
		// Door naar stap 2 (stap 0 en 1 zijn al gedaan)
		gameStap=2;
		
	}
	
	public void checkEindSpel() {
	
		/**
		 * eindSpel controleert of het spel uitgespeeld is. Zo ja, dan worden de eindstand 
		 * en winnaar wordt bekendgemaakt en de optie wordt geboden om opnieuw te spelen.
		 */
		
		if (QVeld.getNumQuatroVrij()==0) {
			
			veldQuatro = QVeld.outVeld(bordFormaat);
			weergaveQuatro(txtOutput,veldQuatro);
    		
			int scoreSpeler = QVeld.getScore("p");
			int scoreSpel = QVeld.getScore("g");
			
			String strEindresultaat="";
	    	if (scoreSpeler>scoreSpel) {
	    		strEindresultaat = "Je hebt gewonnen!";
	    	} else {
	    		strEindresultaat = (scoreSpeler==scoreSpel) ? "Het is een gelijkspel." : "Je hebt verloren!";
	    	}
	    	int antwoordDialog =JOptionPane.showConfirmDialog(null, "Het spel is klaar! " + strEindresultaat + " Wil je nog een keer spelen?");
	
			if(antwoordDialog == JOptionPane.YES_OPTION){
			  startSpel();
			} else {
				System.exit(0);
			}	
			
		}
		
	}
	
	public String berekenZetSpel() {
		
		/**
		 * In de methode berekenZetSpel wordt een willekeurige zet gekozen 
		 * (binnen de afmetingen en opties van het bord) en gecontroleerd of deze nog vrij is.
		 */
		
		String strM = "";
		Random rand1 = new Random();
		Random rand2 = new Random();
		int n1, n2;
		
		// Met twee random-variabelen zoeken naar een vrije zet
		do {
			n1 = rand1.nextInt(bordFormaat*bordFormaat)+1;
			n2 = rand2.nextInt(4);
			strM = "_" + Integer.toString(n1) + "_" + strAllowed[n2];
		} while (!zetVrij(strM));
		
		//String strZetAlt = QVeld.advancedZet();
		
		return strM;
		//return strZetAlt;
		
	}
	
	public String langZet(String move) {
		
		/**
		 * langZet maakt van een 'simpele' move (bv. 1L of 4B) een String 
		 * waarbij het nummer en de lijn gescheiden zijn door een underscore.
		 */
		
		// Controleer of move niet leeg of te kort is
		if (move.length() < 2) {
			return move;
		}
		move.trim();
		return "_" + move.substring(0, move.length()-1) + "_" + move.substring(move.length()-1, move.length());
		
	}
	
	public String kortZet(String move) {
		
		/**
		 * kortZet maakt van een langZet (bv. _1_L of _14_B) een String 
		 * waarbij het nummer en de lijn direct achter elkaar staan.
		 */
		
		// Controleer of move niet leeg of te kort is
		if (move.length() < 4) {
			return move;
		}
		move.trim();
		String arrM[];
		arrM = move.split("_");
		return arrM[1]+arrM[2];
		
	}
	
	public boolean inArray(String[] arrIn, String elemIn) {
		
		/**
		 * inArray gecontroleert of elemIn een element is van arrIn.
		 */
		
		elemIn.trim();
		// De elementen in arrIn doorlopen
		for (int i = 0; i < arrIn.length; i++ ) {
			if (Objects.equals(arrIn[i].toString(), elemIn)) {
				return true;
			}
		}
		return false;
		
	}
	
	public boolean zetToegestaan(String strMove) {
		
		/**
		 * zetToegestaan controleert of een move toegestaan is 
		 * (nummer valt niet buiten het bord, lijn valt binnen de opties (O,B,L,R). 
		 */
		
		// Controleer of strMove het juiste format ("_X_X") heef
		String strNew[] = (strMove.trim()).split("_");
		if (strNew.length != 3) { return false; }
		
		// Controleer of moveNummer een numerieke waarde is
		int moveNummer;
		try {
			moveNummer = Integer.parseInt(strNew[1].toString());
		}
		catch (Exception e) {
			return false;
		}
		
		// Controleer of moveNummer toegestaan is in het veld (niet te klein)
		if (moveNummer < 1) { return false; }
		// Controleer of moveNummer toegestaan is in het veld (niet te groot)
		if (moveNummer > (bordFormaat*bordFormaat)) {return false; }
				
		// Controleer of moveLijn toegestaan is
		String moveLijn = strNew[2];
		return Arrays.stream(LijnOpties.values()).anyMatch((t) -> t.name().equals(moveLijn));
		
	}
	
	public boolean zetVrij(String strMove) {
		
		/**
		 * zetVrij controleert of de move niet al gedaan is, 
		 * zowel de move zelf als de (indien van toepassing) corresponderende move in het aangrenzende veld.
		 */
		
		// Controleer of er al zetten zijn gedaan (zo niet, dan kan de rest overgeslagen worden)
		if (QVeld.getTotalNum()==0) { return true; }
		
		// strMove ontleden
		String arrM[] = strMove.split("_");
		int intN = Integer.parseInt(arrM[1]);
		String strL = arrM[2];
				
		// Controleer of dezelfde zet al is gedaan
		if (QVeld.getLijn(intN, strL)) { return false; }
		
		// All clear
		return true; 
		
	}
		
	// ====================
	//  Output componenten
	// ====================
	
	public void weergaveQuatro(JTextPane tp, String qtr) {
	
		/**
		 * weergaveQuatro schrijft het raster van het spel naar een JTextPane. 
		 * Daarbij worden de lijnen en Quatro's van de spelers in verschillende 
		 * kleuren weergegeven.
		 */
		
		// Maak de JTextPane leeg
		tp.setText(""); 
		
		// Vul de JTextPane, gebruik kleuren voor de verschillende spelers' lijnen en Quatro's
		for (int y=0; y<(qtr.length()); y++) {
			
			String strT = qtr.substring(y, y+1);
			switch (strT) {
			case "p":
				outputNaarPane(tp, "#", Color.BLUE);
				break;
			case "g":
				outputNaarPane(tp, "#", Color.RED);
				break;
			default:
				outputNaarPane(tp, strT.toString(), Color.BLACK);
				break;
			}
		}

	}
	
	public void outputNaarPane(JTextPane tp, String msg, Color c) {
		
		/**
		 * outputNaarPane schrijft de String 'msg' naar het einde van JTextPane 'tp'. Dit gebeurt in kleur 'c'.
		 */
		
		// Stel de kleur in 
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet kleur = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        // Voeg msg toe aan het eind van tp, in kleur c
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(kleur, false);
        tp.replaceSelection(msg);
        
    }
	
}