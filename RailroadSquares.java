
public class RailroadSquares extends SquareType {
	private int ownedRailroads = 0;;
	private Player p;
	
	public RailroadSquares(Player p) {
		this.p = p;
	}
	
	public int getOwnedRailRoads() {
		if (Squares.getTileProp(5, 1) == p.getPlayerNum())
			ownedRailroads++;
		if (Squares.getTileProp(15, 0) == p.getPlayerNum())
			ownedRailroads++;
		if (Squares.getTileProp(25, 0) == p.getPlayerNum())
			ownedRailroads++;
		if (Squares.getTileProp(35, 0) == p.getPlayerNum())
			ownedRailroads++;
		return ownedRailroads;
	}
}
