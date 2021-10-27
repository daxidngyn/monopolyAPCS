public abstract class SquareType {
	private String name;
	private String tileType;
	private int owner;
	
	private static int properties[][] = {
			/* 0 Column: Ownership
			 * 1 Column: Property cost
			 * 2 Column: Property rent (-1 if not applicable)
			 * 3 Column: Mortgage
			 * 4 Column: TileType (-1 = useless, 0 = brown, 1 = light blue, 2 = magTileenta, 3 = orange,
			 * 						4 = red, 5 = yellow, 6 = green, 7 = blue, 8 = railroad, 9 = utility
			 * 5 Column: Mortgage status (-1 if not applicable)
			 * 6 Column: Houses (-1 if not applicable)
			 */	
			{-1, 0, 0, 0, -1, -1, -1},		//Go0
			{0, 60, 2, 30, 0, 0, 0},		//Mediterr1
			{-1, 0, -1, 0, -1, -1, -1},	  //Chance2
			{0, 60, 4, 30, 0, 0, 0},		//Baltic Ave3
			{-1, 0, -200, 0, -1, -1, -1},	//Tax4
			{0, 200, 50, 100, 8, 0, 0},  //Reading Railroad5             
			{0, 100, 6, 50, 1, 0, 0},		//OrientalAvenue6
			{-1, 0, -1, 0, -1, -1, -1},		//Chance7
			{0, 100, 6, 50, 1, 0, 0},		//VermontAvenue8
			{0, 120, 8, 60, 1, 0, 0},		//ConnecticutAvenue9
			{-1, 0, 0, 0, -1, -1, 0},			//Jail10
			{0, 140, 10, 70, 2, 0, 0},		//St.CharlesPlace11
			{0, 150, 4, 75, 9, 0, 0},		//ElectricCompany12
			{0, 140, 10, 70, 2, 0, 0},		//StatesAvenue13
			{0, 160, 12, 80, 2, 0, 0},		//VirginiaAvenue14
			{0, 200, 50, 100, 8, 0, 0},		//PennsylvaniaRailroad15
			{0, 180, 14, 90, 3, 0, 0},		//St.JamesPlace16
			{-1, 0, -1, 0, -1, -1, -1},		//Chance17
			{0, 180, 14, 90, 3, 0, 0},		//TennesseeAvenue18
			{0, 200, 16, 100, 3, 0, 0},		//NewYorkAvenue19
			{-1, 0, 500, 0, -1, -1, -1},		//FreeParking20				
			{0, 220, 18, 110, 4, 0, 0},		//KentuckyAvenue21
			{-1, 0, -1, 0, -1, -1, -1},		//Chance22
			{0, 220, 18, 110, 4, 0, 0},		//IndianaAvenue23
			{0, 240, 20, 120, 4, 0, 0},		//IllinoisAvenue24
			{0, 200, 50, 100, 8, 0, 0},		//B&ORailRoad25
			{0, 260, 22, 130, 5, 0, 0},		//AtlanticAvenue26
			{0, 260, 22, 130, 5, 0, 0},		//VentnorAvenue27
			{0, 150, 4, 75, 9, 0, 0},		//WaterWorks28
			{0, 280, 24, 140, 5, 0, 0},		//MarvinGardens29
			{-1, 0, -50, 0, -1, -1, -1},		//GotoJail30				
			{0,  300, 26, 150, 6, 0, 0},		//PacificAvenue31
			{0, 300, 26, 150, 6, 0, 0},		//NorthCarolinaAvenue32
			{-1, 0, -1, 0, -1, -1, -1},		//Chance33
			{0, 320, 28, 160, 6, 0, 0},		//PennsylvaniaAvenue34
			{0, 200, 50, 100, 8, 0, 0},		//ShortLineRailroad35
			{-1, 0, -1, 0, -1, -1, -1},		//Chance36
			{0, 350, 35, 175, 7, 0, 0},		//ParkPlace37
			{-1, 0, -75, 0, -1, -1, -1},		//LuxuryTax38				
			{0, 400, 50, 200, 7, 0, 0}		//Boardwalk39
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
	
	private static int housePrices[][] = {
			//6
			{-1, -1, -1, -1, -1, -1},		//Go0
			{0, 10, 30, 90, 160, 250},		//Mediterr1
			{-1, -1, -1, -1, -1, -1},	  //Chance2
			{0, 20, 60, 180, 320, 450},		//Baltic Ave3
			{-1, 0, -200, 0, -1, -1, -1},	//Tax4
			{0, 200, 50, 100, 8, 0, 0},  //Reading Railroad5             
			{0, 30, 90, 270, 400, 550},		//OrientalAvenue6
			{-1, -1, -1, -1, -1, -1, -1},		//Chance7
			{0, 30, 90, 270, 400, 550},		//VermontAvenue8
			{0, 40, 100, 300, 450, 600},		//ConnecticutAvenue9
			{-1, 0, 0, 0, -1, -1, 0},			//Jail10
			{0, 50, 150, 450, 625, 750},		//St.CharlesPlace11
			{0, 150, 4, 75, 9, 0, 0},		//ElectricCompany12
			{0, 50, 150, 450, 625, 750},		//StatesAvenue13
			{0, 60, 180, 500, 700, 900}, 		//VirginiaAvenue14
			{0, 200, 50, 100, 8, 0, 0},		//PennsylvaniaRailroad15
			{0, 70, 200, 550, 750, 950}, 	//St.JamesPlace16
			{-1, -1, -1, 0, -1, -1, -1},		//Chance17
			{0, 70, 200, 550, 750, 950},		//TennesseeAvenue18
			{0, 80, 220, 600, 800, 1000}, 		//NewYorkAvenue19
			{-1, 0, 500, 0, -1, -1, -1},		//FreeParking20				
			{0, 90, 250, 700, 875, 1050}, 		//KentuckyAvenue21
			{-1, 0, -1, 0, -1, -1, -1},		//Chance22
			{0, 90, 250, 700, 875, 1000}, 		//IndianaAvenue23
			{0, 100, 300, 750, 925, 1100}, 		//IllinoisAvenue24
			{0, 200, 50, 100, 8, 0, 0},		//B&ORailRoad25
			{0, 110, 330, 800, 975, 1150}, 	//AtlanticAvenue26
			{0, 110, 330, 800, 975, 1150}, 		//VentnorAvenue27
			{0, 150, 4, 75, 9, 0, 0},		//WaterWorks28
			{0, 120, 360, 850, 1025, 1200}, 	//MarvinGardens29
			{-1, 0, -50, 0, -1, -1, -1},		//GotoJail30				
			{0, 130, 390, 900, 1100, 1275}, 		//PacificAvenue31
			{0, 130, 390, 900, 1100, 1275}, 		//NorthCarolinaAvenue32
			{-1, 0, -1, 0, -1, -1, -1},		//Chance33
			{0, 150, 450, 1000, 1200, 1400}, 		//PennsylvaniaAvenue34
			{0, 200, 50, 100, 8, 0, 0},		//ShortLineRailroad35
			{-1, 0, -1, 0, -1, -1, -1},		//Chance36
			{0, 175, 500, 1100, 1300, 1500}, 	//ParkPlace37
			{-1, 0, -75, 0, -1, -1, -1},		//LuxuryTax38				
			{0, 200, 600, 1400, 1700, 2000}		//Boardwalk39
	};
	
	public static String getName(int tileNum) {
		return tileNames[tileNum];
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTileType(int tileNum) {
		return properties[tileNum][4];
	}
	public void setTileType(String tileType) {
		this.tileType = tileType;
	}
	
	public static int getOwner(int tileNum) {
		return properties[tileNum][0];
	}
	public static void setOwner(int owner, int tileNum) {
		properties[tileNum][0] = owner;
	}
	
	public static int[][] syncTiles() {
		return properties;
	}
	public static String[] syncNames() {
		return tileNames;
	}

	public static int getNumOfHouses(int tileNum) {
		properties = syncTiles();
		tileNames = syncNames();
		return properties[tileNum][6];
	}
	public static boolean sellHouses(int tileNum) {
		if (properties[tileNum][6] < 1) {
			return false;
		}
		properties[tileNum][6]--;
		return true;
	}
	public static void addHouse(int tileNum) {
		properties[tileNum][6]++;
	}
	
	public static void doubleRentPrice(int tileNum1, int tileNum2, int tileNum3) {
		
	}
	public static void doubleRentPrice(int tileNum1, int tileNum2) {
		
	}
	
	public static void setRentPrice(int tileNum, int houseAmnt) {
		properties[tileNum][2] = housePrices[tileNum][houseAmnt];
	}
	public static void resetRentPrice(int tileNum) {
		properties[tileNum][2] /= 2;
	}
	
	
}
