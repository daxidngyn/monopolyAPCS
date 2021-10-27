public class Squares extends SquareType {
	
	private static int properties[][] = {			
			{-1, 0, 0, 0, -1},		//Go0
			{0, 60, 2, 30, 0},		//Mediterr1
			{-1, 0, -1, 0, -1},		//Chance2
			{0, 60, 4, 30, 0},		//Baltic Ave3
			{-1, 0, -200, 0, -1},	//Tax4
			{0, 200, 50, 100, 8},  //Reading Railroad5             
			{0, 100, 6, 50, 1},		//OrientalAvenue6
			{-1, 0, -1, 0, -1},		//Chance7
			{0, 100, 6, 50, 1},		//VermontAvenue8
			{0, 120, 8, 60, 1},		//ConnecticutAvenue9
			{-1, 0, 0, 0, -1},			//Jail10
			{0, 140, 10, 70, 2},		//St.CharlesPlace11
			{0, 150, 4, 75, 9},		//ElectricCompany12
			{0, 140, 10, 70, 2},		//StatesAvenue13
			{0, 160, 12, 80, 2},		//VirginiaAvenue14
			{0, 200, 50, 100, 8},		//PennsylvaniaRailroad15
			{0, 180, 14, 90, 3},		//St.JamesPlace16
			{-1, 0, -1, 0, -1},		//Chance17
			{0, 180, 14, 90, 3},		//TennesseeAvenue18
			{0, 200, 16, 100, 3},		//NewYorkAvenue19
			{-1, 0, 500, 0, -1},		//FreeParking20				
			{0, 220, 18, 110, 4},		//KentuckyAvenue21
			{-1, 0, -1, 0, -1},		//Chance22
			{0, 220, 18, 110, 4},		//IndianaAvenue23
			{0, 240, 20, 120, 4},		//IllinoisAvenue24
			{0, 200, 50, 100, 8},		//B&ORailRoad25
			{0, 260, 22, 130, 5},		//AtlanticAvenue26
			{0, 260, 22, 130, 5},		//VentnorAvenue27
			{0, 150, 4, 75, 9},		//WaterWorks28
			{0, 280, 24, 140, 5},		//MarvinGardens29
			{-1, 0, -50, 0, -1},		//GotoJail30				
			{0,  300, 26, 150, 6},		//PacificAvenue31
			{0, 300, 26, 150, 6},		//NorthCarolinaAvenue32
			{-1, 0, -1, 0, -1},		//Chance33
			{0, 320, 28, 160, 6},		//PennsylvaniaAvenue34
			{0, 200, 50, 100, 8},		//ShortLineRailroad35
			{-1, 0, -1, 0, -1},		//Chance36
			{0, 350, 35, 175, 7},		//ParkPlace37
			{-1, 0, -75, 0, -1},		//LuxuryTax38				
			{0, 400, 50, 200, 7}		//Boardwalk39
	};
	private static String tileNames[] =  {
			"Go [0]",
			"Mediterranean Avenue [1]",
			"Chance [2]",
			"Baltic Avenue [3]",
			"Income Tax [4]",
			"Reading Railroad [5]",
			"Oriental Avenue [6]",
			"Chance [7]",
			"Vermont Avenue [8]",
			"Connecticut Avenue [9]",
			"Jail [10]",
			"St. Charles Place [11]",
			"Electric Company [12]",
			"States Avenue [13]",
			"Virginia Avenue [14]",
			"Pennsylvania Railroad [15]",
			"St. James Place [16]",
			"Chance [17]",
			"Tennessee Avenue [18]",
			"New York Avenue [19]",
			"Free Parking [20]",
			"Kentucky Avenue [21]",
			"Chance [22]",
			"Indiana Avenue [23]",
			"Illinois Avenue [24]",
			"B & O RailRoad [25]",
			"Atlantic Avenue [26]",
			"Ventnor Avenue [27]",
			"Water Works [28]",
			"Marvin Gardens [29]",
			"Go to Jail [30]",
			"Pacific Avenue [31]",
			"North Carolina Avenue [32]",
			"Chance [33]",
			"Pennsylvania Avenue [34]",
			"Short Line Railroad [35]",
			"Chance [36]",
			"Park Place [37]",
			"Luxury Tax [38]",
			"Boardwalk [39]"
		};
	public static int getTileProp(int tileNum, int col) {
		properties = syncTiles();
		tileNames = syncNames();
		return properties[tileNum][col];
	}
	public static void tileProp(int tileNum, int col, int val) {
		properties = syncTiles();
		tileNames = syncNames();
		properties[tileNum][col] = val;
	}
	public static int getTilePrice(int tileNum) {
		properties = syncTiles();
		tileNames = syncNames();
		return properties[tileNum][1];
	}
	
	public static void mortgage(int tileNum) {
		properties = syncTiles();
		tileNames = syncNames();
		String temp = tileNames[tileNum] + " (Mortgaged)";
		tileNames[tileNum] = temp;
		properties[tileNum][5] = 1;
	}
	public void unMortgage(int tileNum) {
		properties = syncTiles();
		tileNames = syncNames();
		if (tileNames[tileNum].contains("(Mortgaged)")) {
			tileNames[tileNum] = tileNames[tileNum].replaceAll("(Mortgaged)", "");
			properties[tileNum][5] = 0;
		}
	}
	

}
