import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TempBoard extends JFrame{
	
	private gameBoardP gameBoard;
	private JSplitPane split;
	private JLayeredPane lp;
	private static JTextArea outputArea;
	private JScrollPane outputPane;
	private JPanel bottomButtonsP;
	private JPanel bottomInfoP;
		
	private JButton rollB;
	private JButton endTurnB;
	private JButton buyB;
	private JButton propListB;
	private JButton sellB;
	private JButton mortgageB;
	GridBagConstraints c = new GridBagConstraints();
	private static JLabel p1MoneyL; private static JLabel p2MoneyL;
	private static JLabel p1PosL;	private static JLabel p2PosL;

	private static String p1Name; private static String p2Name;
	private JLabel dice1L; private JLabel dice2L;
	
	private static int dice1; private static int dice2; private boolean doubleDice;
	
	public TempBoard(String p1Name, String p2Name) {
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		
		createButtons();
		
		//Frame
		setSize(1300, 1250);
		setTitle("Monopoly");
		setLayout(new BorderLayout());
		add(split, BorderLayout.CENTER);
		add(bottomButtonsP, BorderLayout.SOUTH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameBoard.setMinimumSize(new Dimension(1000, 1000));
		outputPane.setMinimumSize(new Dimension(150, 1000));
		bottomButtonsP.setPreferredSize(new Dimension(1000, 150));
		
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0; c.gridy = 0;
		bottomButtonsP.add(p1PosL, c);
		c.gridx = 0; c.gridy = -1;
		bottomButtonsP.add(p1MoneyL,c);
		c.gridx = 7; c.gridy = 0;
		bottomButtonsP.add(p2PosL, c);
		c.gridx = 7; c.gridy = -1;
		bottomButtonsP.add(p2MoneyL,c);
		
		c.gridx = 1; c.gridy = 1;	//ROLL BUTTON
		bottomButtonsP.add(rollB,c);
		rollB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dice1 = Monopoly.rollDice();
			    dice2 = Monopoly.rollDice();
			    int steps = dice1 + dice2;

				if (Monopoly.hasRolled() == true) {
					output(Monopoly.getCurrentPlayer().getName() + " has already rolled!");
				}
				else {
					output("Dice 1: " + dice1);
					output("Dice 2: " + dice2);
					if (dice1 == dice2) {
						doubleDice = true;
						output(Monopoly.getCurrentPlayer().getName() + " has rolled a double!");
					}
					Monopoly.movePlayer(steps);
				}
				Monopoly.playerRolled(true);
			}
		});
		
		//END TURN BUTTON
		c.gridx = 6; c.gridy = 1;	bottomButtonsP.add(endTurnB,c);
		endTurnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Monopoly.endTurn(doubleDice);
				doubleDice = false;
			}
		});
		
		//BUY PROPERTIES BUTTON
		c.gridx = 2; c.gridy = 1;	bottomButtonsP.add(buyB, c);
		buyB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currPos = Monopoly.getCurrentPlayer().getPos();
				GridBagConstraints s = new GridBagConstraints();
				
				JFrame buyWindow = new JFrame();
				buyWindow.setSize(500, 250);
				buyWindow.setVisible(true);
				buyWindow.setTitle("Purchase Properties/Houses/Hotels");
				
				JPanel buyPanel = new JPanel(new GridBagLayout());
				buyPanel.setBackground(new Color(0xcce4d2));
				JLabel options = new JLabel("Please select an option for Player " + Monopoly.getCurrentPlayer().getName());
				JButton buyCurrentTile = new JButton("Purchase current tile");
				JButton buyHouses = new JButton("Purchase houses");
				JButton cancel = new JButton("Cancel");
				s.insets = new Insets(5, 5, 5, 5);
				s.gridx = 0; s.gridy = 1; buyPanel.add(buyCurrentTile, s);
				s.gridx = 1; s.gridy = 0; buyPanel.add(options, s);
				s.gridx = 1; s.gridy = 1; buyPanel.add(buyHouses, s);
				s.gridx = 2; s.gridy = 1;buyPanel.add(cancel, s);
				buyWindow.add(buyPanel);

				JFrame buyWindow2 = new JFrame();
				buyWindow2.setSize(800, 250);
				buyWindow2.setVisible(false);
				JPanel buyPanel2 = new JPanel(new GridBagLayout()); buyPanel2.setBackground(new Color(0xcce4d2));
				buyPanel2.setMinimumSize(new Dimension(500, 250));
				JLabel buyLabel = new JLabel("Enter tile # of property you wish to buy houses for: ");
				JTextField buyInput = new JTextField(2);
				JButton confirmBuy = new JButton("Ok");
				JLabel errorMSG = new JLabel("");
				errorMSG.setVisible(false);
				s.gridx = 0; s.gridy = 0; buyPanel2.add(buyLabel, s);
				s.gridx = 0; s.gridy = 1; buyPanel2.add(buyInput, s);
				s.gridx = 1; s.gridy = 1; buyPanel2.add(confirmBuy, s);
				s.gridx = 0; s.gridy = 2; buyPanel2.add(errorMSG, s);
				JTextArea buyOutputArea = new JTextArea(Monopoly.getCurrentPlayer().getName() + "'s Properties:\n");
				buyOutputArea.setEditable(false);
				buyOutputArea.setLineWrap(true);
				JScrollPane buyOutputPane = new JScrollPane(buyOutputArea);
				JSplitPane splitSell = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buyPanel2, buyOutputPane);
				buyWindow2.add(splitSell);
				
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buyWindow.setVisible(false);
					}
				});
				buyCurrentTile.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buyWindow.setVisible(false);
						int owner = Squares.getOwner(currPos);
						Monopoly.buyProperty(currPos);
					}
				});
				buyHouses.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							buyWindow.setVisible(false);
							buyWindow2.setTitle("Buying houses/hotels");
							buyWindow2.setVisible(true);
							JButton cancel1 = new JButton("Cancel");
							for (String propName : Monopoly.getCurrentPlayer().getPropertiesList()) {
								buyOutputArea.append(propName + "\n");
							}
							int tileNum = Integer.parseInt(buyInput.getText());
							int owner = Squares.getOwner(tileNum);
							Monopoly.buyHouses(tileNum);
						}
						catch (NumberFormatException x){
							errorMSG.setText("Please enter TILE NUMBER of property you wish to purchase a house for.");
							errorMSG.setVisible(true);
						}
					}
				});
				
				

			}
		});
		
		//SELL BUTTON
		c.gridx = 4;
		c.gridy = 1;	
		bottomButtonsP.add(sellB,c);
		sellB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GridBagConstraints s = new GridBagConstraints();
				JFrame sellWindow = new JFrame();
				sellWindow.setSize(500, 250);
				sellWindow.setVisible(true);
				sellWindow.setTitle("Selling Properties");
				
				JPanel sellPanel = new JPanel(new GridBagLayout());
				sellPanel.setBackground(new Color(0xcce4d2));
				JLabel options = new JLabel("Please select an option for Player " + Monopoly.getCurrentPlayer().getName());
				JButton sellToBank = new JButton("Sell to Bank");
				JButton sellToPlayer = new JButton("Sell to Other Player");
				JButton cancel = new JButton("Cancel");
				s.insets = new Insets(5, 5, 5, 5);
				s.gridx = 0; s.gridy = 1; sellPanel.add(sellToBank, s);
				s.gridx = 1; s.gridy = 0; sellPanel.add(options, s);
				s.gridx = 1; s.gridy = 1; sellPanel.add(sellToPlayer, s);
				s.gridx = 2; s.gridy = 1;sellPanel.add(cancel, s);
				sellWindow.add(sellPanel);
				
				JFrame sellWindow2 = new JFrame();
				sellWindow2.setSize(800, 250);
				sellWindow2.setVisible(false);
				JPanel sellPanel2 = new JPanel(new GridBagLayout()); sellPanel2.setBackground(new Color(0xcce4d2));
				sellPanel2.setMinimumSize(new Dimension(500, 250));
				JLabel sellLabel = new JLabel("Enter tile # of property you wish to sell: ");
				JTextField sellInput = new JTextField(2);
				JButton confirmSell = new JButton("Ok");
				JLabel errorMSG = new JLabel("SLATT");
				errorMSG.setVisible(false);
				s.gridx = 0; s.gridy = 0; sellPanel2.add(sellLabel, s);
				s.gridx = 0; s.gridy = 1; sellPanel2.add(sellInput, s);
				s.gridx = 1; s.gridy = 1; sellPanel2.add(confirmSell, s);
				s.gridx = 0; s.gridy = 2; sellPanel2.add(errorMSG, s);
				

				JTextArea sellOutputArea = new JTextArea(Monopoly.getCurrentPlayer().getName() + "'s Properties:\n");
				sellOutputArea.setEditable(false);
				sellOutputArea.setLineWrap(true);
				JScrollPane sellOutputPane = new JScrollPane(sellOutputArea);
				JSplitPane splitSell = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sellPanel2, sellOutputPane);
				sellWindow2.add(splitSell);
				
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sellWindow.setVisible(false);
					}
				});
				sellToBank.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sellWindow.setVisible(false);
						sellWindow2.setTitle("Selling to Bank");
						sellWindow2.setVisible(true);
						JButton cancel1 = new JButton("Cancel");
						for (String propName : Monopoly.getCurrentPlayer().getPropertiesList()) {
							sellOutputArea.append(propName + "\n");
						}
						confirmSell.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									int input = Integer.parseInt(sellInput.getText());
									if (Squares.getOwner(input) != Monopoly.getCurrentPlayer().getPlayerNum()) {
										errorMSG.setVisible(true);
										if (Squares.getOwner(input) == Monopoly.getOtherPlayer().getPlayerNum())
											errorMSG.setText(Squares.getName(input) + " belongs to " + Monopoly.getOtherPlayer().getName());
										else
											errorMSG.setText(Squares.getName(input) + " is unowned or cannot be sold!");
									}
									else {
										output(Monopoly.getCurrentPlayer().getName() + " has sold " + Squares.getName(input) + " to the bank for "
												+ "$" + Squares.getTilePrice(input)/2);
										Squares.setOwner(0, input);
										Monopoly.getCurrentPlayer().addMoney(Squares.getTilePrice(input)/2);
										updateMoney();
										sellWindow2.setVisible(false);
									}
								}
								catch (NumberFormatException err) {
									errorMSG.setVisible(true);
									errorMSG.setText("Please enter TILE NUMBER of property you want to sell!");
								}
							}
						});
						cancel1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								sellWindow2.setVisible(false);
							}
						});
					}
				});
				sellToPlayer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sellWindow.setVisible(false);
						sellWindow2.setTitle("Selling to Player");
						sellWindow2.setVisible(true);
						for (String propName : Monopoly.getCurrentPlayer().getPropertiesList()) {
							sellOutputArea.append(propName + "\n");
						}
						JTextField sellInput2 = new JTextField(3);
						JLabel sellLabel2 = new JLabel("Enter listing price: ");
						s.gridx = 0; s.gridy = 0; sellPanel2.add(sellLabel, s);
						s.gridx = 1; s.gridy = 0; sellPanel2.add(sellLabel2, s);
						s.gridx = 0; s.gridy = 1; sellPanel2.add(sellInput, s);
						s.gridx = 1; s.gridy = 1; sellPanel2.add(sellInput2, s);
						s.gridx = 2; s.gridy = 1; sellPanel2.add(confirmSell, s);
						s.gridx = 0; s.gridy = 2; sellPanel2.add(errorMSG, s);
						
						JFrame sellWindow3 = new JFrame();	sellWindow3.setTitle("Confirm"); sellWindow3.setSize(500, 250);
						JPanel sellPanel3 = new JPanel(new GridBagLayout()); sellPanel3.setBackground(new Color(0xcce4d2));
						JLabel confirmSellLabel = new JLabel();
						JButton acceptButton = new JButton("Accept Offer");
						JButton declineButton = new JButton("Decline Offer");
						s.gridx = 0; s.gridy = 0; sellPanel3.add(confirmSellLabel, s);
						s.gridx = 0; s.gridy = 1; sellPanel3.add(acceptButton, s);
						s.gridx = 1; s.gridy = 1; sellPanel3.add(declineButton, s);
						sellWindow3.add(sellPanel3);
						confirmSell.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								sellWindow2.setVisible(false);
								try {
									int input = Integer.parseInt(sellInput.getText());
									int inputPrice = Integer.parseInt(sellInput2.getText());
									if (Squares.getOwner(input) != Monopoly.getCurrentPlayer().getPlayerNum()) {
										errorMSG.setVisible(true);
										if (Squares.getOwner(input) == Monopoly.getOtherPlayer().getPlayerNum())
											errorMSG.setText(Squares.getName(input) + " belongs to " + Monopoly.getOtherPlayer().getName());
										else
											errorMSG.setText(Squares.getName(input) + " is unowned or cannot be sold!");
									}
									else {
										confirmSellLabel.setText(Monopoly.getCurrentPlayer().getName() + " wants to sell " + Squares.getName(input) + 
												" for $" + inputPrice);
										sellWindow3.setVisible(true);
										acceptButton.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												sellWindow3.setVisible(false);
												output(Monopoly.getCurrentPlayer().getName() + " has sold " + Squares.getName(input) + " to " + 
														Monopoly.getOtherPlayer().getName() + " for $" + inputPrice);
												Monopoly.getCurrentPlayer().addMoney(inputPrice);
												Monopoly.getOtherPlayer().subtractMoney(inputPrice);
												Squares.setOwner(Monopoly.getOtherPlayer().getPlayerNum(), input);
												Monopoly.getOtherPlayer().addProperty(input);
												Monopoly.getCurrentPlayer().removeProperty(input);
												updateMoney();
											}
										});
										declineButton.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												sellWindow3.setVisible(false);
												output(Monopoly.getOtherPlayer().getName() + " has declined " + Monopoly.getCurrentPlayer().getName() +
														"'s offer of " + Squares.getName(input) + " for $" + inputPrice);
											}
										});
									}
								}
								catch (NumberFormatException err) {
									errorMSG.setVisible(true);
									errorMSG.setText("Please enter the NUMBER of the tile you want to sell!");
								}
							}
						});
					}
				});
			}
		});
		
		//MORTGAGE BUTTON
		c.gridx = 3; 
		c.gridy = 1;	
		bottomButtonsP.add(mortgageB,c);
		mortgageB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GridBagConstraints m = new GridBagConstraints();
				JFrame mortgageWindow = new JFrame();
				mortgageWindow.setSize(800, 250);
				mortgageWindow.setVisible(true);
				JPanel mortgagePanel = new JPanel(new GridBagLayout()); 
				mortgagePanel.setBackground(new Color(0xcce4d2));
				mortgagePanel.setMinimumSize(new Dimension(500, 250));
				JLabel mortgageLabel = new JLabel("Enter tile # of property you wish to mortgage: ");
				JTextField mortgageInput = new JTextField(2);
				JButton confirmMortgage = new JButton("Ok");
				JButton cancelButton = new JButton("Cancel");
				JLabel errorMSG = new JLabel("null");
				errorMSG.setVisible(false);
				m.gridx = 0;
				m.gridy = 0;
				mortgagePanel.add(mortgageLabel, m);
				m.gridx = 0;
				m.gridy = 1;
				mortgagePanel.add(mortgageInput, m);
				m.gridx = 1;
				m.gridy = 1;
				mortgagePanel.add(confirmMortgage, m);
				m.gridx = 2;
				m.gridy = 1;
				mortgagePanel.add(cancelButton, m);
				m.gridx = 0;
				m.gridy = 0;
				mortgagePanel.add(errorMSG, m);
				

				JTextArea mortgageOutputArea = new JTextArea(Monopoly.getCurrentPlayer().getName() + "'s Properties:\n");
				mortgageOutputArea.setEditable(false);
				mortgageOutputArea.setLineWrap(true);
				JScrollPane mortgageOutputPane = new JScrollPane(mortgageOutputArea);
				JSplitPane splitmortgage = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mortgagePanel, mortgageOutputPane);
				mortgageWindow.add(splitmortgage);
				for (String propName : Monopoly.getCurrentPlayer().getPropertiesList()) {
					mortgageOutputArea.append(propName + "\n");
				}
				
				confirmMortgage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							mortgageWindow.setVisible(false);
							int input = Integer.parseInt(mortgageInput.getText());
							if (Squares.getTileProp(input, 5) == -1) {
								output("Cannot mortgage this tile!");
							}
							else if (Squares.getTileProp(input, 5) == 1) {
								output(Squares.getName(input) + " is already mortgaged!");
							}
							else {
								output(Monopoly.getCurrentPlayer().getName() + " has mortgaged " + Squares.getName(input) + " and has received "
										+ "$" + Squares.getTilePrice(input)/2);
								Squares.mortgage(input);
								Monopoly.getCurrentPlayer().addMoney(Squares.getTilePrice(input)/2);
								updateMoney();
							}
						}
						catch (NumberFormatException err){
							errorMSG.setText("Please enter TILE NUMBER of property to mortgage!");
							errorMSG.setVisible(true);
						}
						
					}
				});
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mortgageWindow.setVisible(false);
					}
				});
				
			}
		});
		//VIEW PROPERTIES BUTTON
		c.gridx = 5; c.gridy = 1;	bottomButtonsP.add(propListB, c);
		propListB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				output(Monopoly.getCurrentPlayer().getName() + "'s owned properties: ");
				for (String propName : Monopoly.getCurrentPlayer().getPropertiesList()) {
					output(propName);
				}
				output("");
			}
		});
		
	}
	
	private void createButtons() {
		gameBoard = new gameBoardP("MonopolyBoard.gif");
		
		bottomButtonsP = new JPanel(new GridBagLayout());
		lp = getLayeredPane();
		outputArea = new JTextArea("Monopoly Semester Project\nCreated by David Nguyen, Period 1\n\n");
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputPane = new JScrollPane(outputArea);
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gameBoard, outputPane);
		rollB = new JButton("Roll Dice");
		endTurnB = new JButton("End Turn");
		buyB = new JButton("Purchase Property");
		propListB = new JButton("View Owned Properties");
		sellB = new JButton("Sell Property");
		mortgageB = new JButton("Mortgage Property");
		p1MoneyL = new JLabel(p1Name + " $: 1500");
		p1PosL = new JLabel(p1Name + " is at \"Go\".");
		p2MoneyL = new JLabel(p2Name + " $: 1500");
		p2PosL = new JLabel(p2Name + " is at \"Go\".");
	}
	
	public void updateMoney() {
		p1MoneyL.setText(p1Name + "$: " + Monopoly.getPlayer1().getMoney());
		p2MoneyL.setText(p2Name + "$: " + Monopoly.getPlayer2().getMoney());
	}
	public void updatePos() {
		p1PosL.setText(p1Name + " is at " + Squares.getName(Monopoly.getPlayer1().getPos()));
		p2PosL.setText(p2Name + " is at " + Squares.getName(Monopoly.getPlayer2().getPos()));
	}
	
	public void jailGUI() {
		GridBagConstraints j = new GridBagConstraints();
		JFrame jailWindow = new JFrame();
		jailWindow.setSize(500, 250);
		jailWindow.setVisible(true);
		jailWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jailWindow.setTitle(Monopoly.getCurrentPlayer().getName() + " is currently in jail.");
		
		JPanel jailPanel = new JPanel(new GridBagLayout());
		JTextField jailInput =  new JTextField(30);
		JLabel options = new JLabel("Select an option: (Enter 1 or 2)");
		JLabel option1 = new JLabel("1: Pay a $50 fine to get out of jail");
		JLabel option2 = new JLabel("2: Roll dice (If a double is rolled, player is released from jail)");
		JButton enter = new JButton("Enter");
		JLabel error = new JLabel("Please enter 1 or 2!"); 
		j.gridx = 0; j.gridy = 0; jailPanel.add(options, j);
		j.gridx = 0; j.gridy = 1; jailPanel.add(option1, j);
		j.gridx = 0; j.gridy = 2; jailPanel.add(option2,j);
		j.gridx = 0; j.gridy = 3; jailPanel.add(jailInput, j);
		j.gridx = 1; j.gridy = 3; jailPanel.add(enter, j);
		j.gridx = 0; j.gridy = 4; jailPanel.add(error, j);
		jailWindow.add(jailPanel);

		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {
				try {
					int input = Integer.parseInt(jailInput.getText());
					if (input != 2 && input != 1) {
						jailGUI();
					}
					else {
						if (input == 1) {
							output(Monopoly.getCurrentPlayer().getName() + " has paid a $50 fine to get out of jail.");
							Monopoly.getCurrentPlayer().subtractMoney(50);
							Monopoly.getCurrentPlayer().changeJailStatus(false);
							p1MoneyL.setText(p1Name + "$: " + Monopoly.getPlayer1().getMoney());
							p2MoneyL.setText(p2Name + "$: " + Monopoly.getPlayer2().getMoney());
						}
						else if (input == 2) {
							dice1 = Monopoly.rollDice();
						    dice2 = Monopoly.rollDice();
						    output("Dice 1: " + dice1);
						    output("Dice 2: " + dice2);
						    Monopoly.playerRolled(true);
						    if (dice1 == dice2) {
						    	output(Monopoly.getCurrentPlayer().getName() + " has rolled a double and is free from jail!");
						    	Monopoly.getCurrentPlayer().changeJailStatus(false);
						    	Monopoly.movePlayer(dice1 + dice2);
						    }
						    else
						    	output(Monopoly.getCurrentPlayer().getName() + " failed to roll a double and stays in jail.");
						}	
					}
					jailWindow.setVisible(false);
				}
				catch (NumberFormatException err) {}
			}
		});
	}
	
	public void output(String txt) {
		outputArea.append(txt + "\n");
	}

	
	class gameBoardP extends JPanel {
		private Image image;
		public gameBoardP(String img) {
			image = new ImageIcon(img).getImage();
		}
		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}
	

	
	
}
