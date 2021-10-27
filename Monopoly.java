import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Monopoly {
	private static boolean rolled = false; private static boolean paidRent = false; private static boolean paidTax = false;
	private static Player p1; private static Player p2; private static Player currentPlayer; private static Player otherPlayer;
	private static int rollAmnt = 0;
	private int turn = 0;
	private static TempBoard board;
	private static Random rng = new Random();
	private static ArrayList<Integer> diceNums = new ArrayList<Integer>(6);
	
	public Monopoly(TempBoard board, Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.board = board;
		currentPlayer = p1;
		otherPlayer = p2;
		board.output(currentPlayer.getName() + "'s turn!");
		for (int i = 1; i <= 6; i++) {
			diceNums.add(i);
		}
	}
	public static int rollDice() {
		Collections.shuffle(diceNums);
		return diceNums.get(0).intValue();
	}
	
	public static void movePlayer(int steps) {
		board.output("*" + Monopoly.getCurrentPlayer().getName() + " proceeds " + steps + " tiles forward*\n");
		int currPos = currentPlayer.getPos();
		if (currentPlayer.getPos() + steps > 39) {
			currentPlayer.setPos((currentPlayer.getPos() + steps) - 40);
			currPos = currentPlayer.getPos();
			currentPlayer.addMoney(200);
			board.output(currentPlayer.getName() + " has passed \"Go\", collecting $200.");
		}
		else {
			currentPlayer.movePlayer(steps);
			currPos = currentPlayer.getPos();
		}
		board.output(currentPlayer.getName() + " has landed on " + Squares.getName(currPos) + ".");
		evaluateBoard(steps);
	}
	
	public static void evaluateBoard(int steps) {
		int currPos = currentPlayer.getPos();
		int rent;
		board.updateMoney();
		board.updatePos();
		//Rent
		if (Squares.getOwner(currPos) == otherPlayer.getPlayerNum() && paidRent == false) {
			if (currPos == 12 || currPos == 28) {
				if (Squares.getOwner(12) == Squares.getOwner(28)) {
					rent = steps * 10;
				}
				else
					rent = steps * 4;
			}
			else {
				rent = Squares.getTileProp(currentPlayer.getPos(), 2);
			}
			currentPlayer.subtractMoney(rent);
			otherPlayer.addMoney(rent);
			board.output(currentPlayer.getName() + " has paid $" + rent + " to " + otherPlayer.getName() + " because of "
					+ "their property \"" + Squares.getName(currPos) + "\".");
			paidRent = true;
		}
		//Chance cards
		else if (currPos == 2 || currPos == 7 || currPos == 17 || currPos == 22 || currPos == 33 || currPos == 36) {
			chanceCard();
		}
		
		//Luxury Tax
		else if (currPos == 38 && paidTax == false) {
			board.output("Luxury Tax! *" + currentPlayer.getName()  + " pays a fine of $100*");
			currentPlayer.subtractMoney(100);
			paidTax = true;
		}
		//Income Tax
		else if (currPos == 4 && paidTax == false) {
			board.output("Income Tax! *" + currentPlayer.getName()  + " pays a fine of $100*");
			currentPlayer.subtractMoney(100);
			paidTax = true;
		}
		
		//Jail
		else if (currPos == 10) {
			if (currentPlayer.checkJailStatus() == false) {
				if (otherPlayer.checkJailStatus() == false) {
					board.output(currentPlayer.getName() + " is now in jail.");
					currentPlayer.changeJailStatus(true);
				}
			}
			if (currentPlayer.jailTurnCount() < 3)
				jail();
		}
		//Go to jail
		else if (currPos == 30) {
			if (otherPlayer.checkJailStatus() == false) {
				board.output(currentPlayer.getName() + " is now in jail.");
				currentPlayer.changeJailStatus(true);
			}
			if (currentPlayer.jailTurnCount() < 3)
				jail();
		}
		board.updateMoney();
		board.updatePos();
	}
	
	public static void buyProperty(int tileNum) {
		int owner = Squares.getOwner(tileNum);
		if (owner == -1) {
			board.output("Cannot purchase this property!");
		}
		else if (owner == Monopoly.getOtherPlayer().getPlayerNum()) {
			board.output("This property belongs to " + Monopoly.getOtherPlayer().getName() + "!");
		}
		else if (owner == Monopoly.getCurrentPlayer().getPlayerNum()) {
			board.output("You already own this property!");
		}
		else {
			if (Monopoly.getCurrentPlayer().getMoney() < Squares.getTileProp(tileNum, 1)) {
				board.output("You cannot afford this property!");
			}
			else {
				doubleRentPrice(tileNum);
				getCurrentPlayer().subtractMoney(Squares.getTileProp(tileNum, 1));
				getCurrentPlayer().addProperty(tileNum);
				board.output(Monopoly.getCurrentPlayer().getName() + " has purchased " + Squares.getName(tileNum));
				Squares.setOwner(Monopoly.getCurrentPlayer().getPlayerNum(), tileNum);
				board.updateMoney();
			}
		}
	}
	
	public static boolean doubleRentPrice(int tileNum) {
		if (tileNum == 1 || tileNum == 3) {
			if (Squares.getOwner(1) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(3) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(1, 3);
				return true;
			}
		}
		else if (tileNum == 6 || tileNum == 8 || tileNum == 9) {
			if (Squares.getOwner(6) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(8) == getCurrentPlayer().getPlayerNum() 
					&& Squares.getOwner(9) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(6, 8, 9);
				return true;
			}
		}
		else if (tileNum == 11 || tileNum == 13 || tileNum == 14) {
			if (Squares.getOwner(11) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(13) == getCurrentPlayer().getPlayerNum() 
					&& Squares.getOwner(14) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(11, 13, 14);
				return true;
			}
		}
		else if (tileNum == 16 || tileNum == 18 || tileNum == 19) {
			if (Squares.getOwner(16) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(18) == getCurrentPlayer().getPlayerNum() 
					&& Squares.getOwner(19) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(16, 18, 19);
				return true;
			}
		}
		else if (tileNum == 21 || tileNum == 23 || tileNum == 24) {
			if (Squares.getOwner(21) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(23) == getCurrentPlayer().getPlayerNum() 
					&& Squares.getOwner(24) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(21, 23, 24);
				return true;
			}
		}
		else if (tileNum == 26 || tileNum == 27 || tileNum == 29) {
			if (Squares.getOwner(26) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(27) == getCurrentPlayer().getPlayerNum() 
					&& Squares.getOwner(29) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(26, 27, 29);
				return true;
			}
		}
		else if (tileNum == 31 || tileNum == 32 || tileNum == 34) {
			if (Squares.getOwner(31) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(32) == getCurrentPlayer().getPlayerNum() 
					&& Squares.getOwner(34) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(31, 32, 34);
				return true;
			}
		}
		else if (tileNum == 37 || tileNum == 39) {
			if (Squares.getOwner(37) == getCurrentPlayer().getPlayerNum() && Squares.getOwner(39) == getCurrentPlayer().getPlayerNum()) {
				Squares.doubleRentPrice(37, 39);
				return true;
			}
		}
		return false;
	}
	
	
	public static void buyHouses(int tileNum) {
		int owner = Squares.getOwner(tileNum);
		if (owner == -1) {
			board.output("Cannot purchase this property!");
		}
		else if (owner == Monopoly.getOtherPlayer().getPlayerNum()) {
			board.output("This property belongs to " + Monopoly.getOtherPlayer().getName() + "!");
		}
		else if (owner == Monopoly.getCurrentPlayer().getPlayerNum()) {
			board.output("You already own this property!");
		}
		else {
			if (Monopoly.getCurrentPlayer().getMoney() < 50) 
				board.output("You cannot afford a house at this property!");
			else if (doubleRentPrice(tileNum) != true) {
				Squares.resetRentPrice(tileNum);
				board.output("You cannot purchase houses until all the lots in that color-group are owned!");
			}
			else if (Squares.getNumOfHouses(tileNum) == 5) {
				board.output(Squares.getName(tileNum) + " is already a hotel!");
			}
			else {
				if (Squares.getNumOfHouses(tileNum) == 0) {
					Squares.resetRentPrice(tileNum);
				}
				board.output(Monopoly.getCurrentPlayer().getName() + " has purchased a house at" + Squares.getName(tileNum));
				Squares.addHouse(tileNum);
				Squares.setRentPrice(tileNum, Squares.getNumOfHouses(tileNum));
				if (Squares.getNumOfHouses(tileNum) == 5)
					board.output(Squares.getName(tileNum) + " has turned into a hotel!");
			}
		}
	}
	
	public static void endTurn(boolean doubleRoll) {
		if (rolled) {
			board.output(currentPlayer.getName() + " has ended their turn.");
			paidRent = false;
			paidTax = false;
			if (currentPlayer.getName() == p1.getName()) {
				p1 = currentPlayer;
				p2 = otherPlayer;
			}
			else {
				p2 = currentPlayer;
				p1 = otherPlayer;
			}
				
			//Double dice rolls:
			if (doubleRoll == true) {
				board.output(currentPlayer.getName() + " has another turn because of their double roll!");
			}
			//Switch current player
			else {
				if (currentPlayer.getName() == p1.getName()) {
					currentPlayer = p2;
					otherPlayer = p1;
				}
				else {
					currentPlayer = p1;
					otherPlayer = p2;
				}
			}
			rolled = false;
			board.output(currentPlayer.getName() + "'s turn!");
			if (currentPlayer.checkJailStatus())
				jail();
		}
		else 
			board.output(currentPlayer.getName() + " has not rolled the dice yet!");
	}
	
	public static void jail() {
		if (otherPlayer.checkJailStatus()) 
			board.output(currentPlayer.getName() + " is only visiting the jail since " + otherPlayer.getName() + " is already in jaiL!");
		else {
			currentPlayer.lessenJailSentence();
			if (currentPlayer.getJailFreeCard()) {
				board.output(currentPlayer.getName() + " has a get out of jail free card!" + currentPlayer.getName() + " is now out of jail.");
				currentPlayer.changeJailStatus(false);
			}
			if (currentPlayer.jailTurnCount() == -1) {
				board.output(currentPlayer.getName() +  " has paid a $50 fine to get out of jail!");
				currentPlayer.changeJailStatus(false);
			}
			else {
				board.output(currentPlayer.getName() + " has " + (currentPlayer.jailTurnCount() + 1) + " turn(s) left in jail");
				board.jailGUI();
			}
		}

	}
	
	public static void chanceCard() {
		board.output("");
		int cardNum = rng.nextInt(9);
		if (cardNum == 0) {
			board.output("Chance Card: Advance to \"Go\". (Collect $200)\n"); 
			currentPlayer.addMoney(200);
			currentPlayer.setPos(0);
		}
		else if (cardNum == 1) {
			board.output("Chance Card: Advance to Illinois Ave.\n");
			if (currentPlayer.getPos() > 24)
				currentPlayer.addMoney(200);
			currentPlayer.setPos(24);
		}
		else if (cardNum == 2) {
			board.output("Chance Card: Advance to St. Charles Place.\n");
			if (currentPlayer.getPos() > 11)
				currentPlayer.addMoney(200);
			currentPlayer.setPos(11);
		}
		else if (cardNum == 3) {
			board.output("Chance Card: The bank pays you a dividend of $50.\n");
			currentPlayer.addMoney(50);
		}
		else if (cardNum == 4) {
			board.output("Chance Card: " + currentPlayer.getName() + " has earned a get out of jail free card!\n");
			currentPlayer.addJailFreeCard();
		}
		else if (cardNum == 5) {
			board.output("Chance Card: Go back three spaces\n");
			movePlayer(-3);
			evaluateBoard(-3);
		}
		else if (cardNum == 6) {
			board.output("Chance Card: GO TO JAIL!\n");
			if (otherPlayer.checkJailStatus() == false) {
				board.output(currentPlayer.getName() + " is now in jail.");
				currentPlayer.changeJailStatus(true);
			}
			else
				board.output(currentPlayer.getName() + "is visiting jail.");
		}
		else if (cardNum == 7) {
			board.output("Chance Card: You have been elected Chairman of the Board. Pay " + otherPlayer.getName() + "$50.\n");
			currentPlayer.subtractMoney(50);
			otherPlayer.addMoney(50);
		}
		else if (cardNum == 8) {
			board.output("Chance Card: Pay a poor tax of $15.\n");
			currentPlayer.subtractMoney(15);
		}
		else if (cardNum == 9) {
			board.output("Chance Card: Your building loan matures. Receive $150.\n");
			currentPlayer.addMoney(150);
		}
		board.updateMoney();
		board.updatePos();
	}

	public static Player getCurrentPlayer() {
		return currentPlayer;
	}
	public static Player getOtherPlayer() {
		return otherPlayer;
	}
	public static Player getPlayer1() {
		if (currentPlayer.getPlayerNum() == p1.getPlayerNum())
			p1 = currentPlayer;
		else
			p1 = otherPlayer;
		return p1;
	}
	public static Player getPlayer2() {
		if (currentPlayer.getPlayerNum() == p2.getPlayerNum())
			p2 = currentPlayer;
		else
			p2 = otherPlayer;
		return p2;
	}
	
	public static void playerRolled(boolean r) {
		rolled = r;
	}
	public static boolean hasRolled() {
		return rolled;
	}
}
