import javax.swing.JOptionPane;

public class Test {

	public static void main(String[] args) {
		String p1Name = JOptionPane.showInputDialog("Please enter name for Player 1: ");
		Player p1 = new Player(p1Name);
		p1.setPlayerNum(1);
		String p2Name = JOptionPane.showInputDialog("Please enter name for Player 2: ");
		Player p2 = new Player(p2Name);
		p2.setPlayerNum(2);
		
		TempBoard test = new TempBoard(p1Name, p2Name);
		Monopoly game = new Monopoly(test, p1, p2);

	}

}
