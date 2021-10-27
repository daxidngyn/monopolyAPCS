import java.util.ArrayList;
import java.util.List;

public class Player {
	private int money;
	private String name;
	private int pos = 0;
	private int playerNum;
	private List<String> properties = new ArrayList();
	private boolean jailFreeCard = false; private boolean jailStatus = false; private int jailTurnCount = -1;
	
	public Player(String name) {
		money = 1500;
		this.name = name;
	}
	
	public int getMoney() {	
		return money;
	}
	public void addMoney(int money) {
		this.money += money;
	}
	public void subtractMoney(int money) {
		this.money -= money;
	}
	
	public String getName() {
		return name;
	}
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	public int getPlayerNum() {
		return playerNum;
	}
	
	public void movePlayer(int pos) {
		this.pos += pos;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public List<String> getPropertiesList(){
		return properties;
	}
	public void addProperty(int tileNum) {
		properties.add(Squares.getName(tileNum));
	}
	public void removeProperty(int tileNum) {
		String propName = Squares.getName(tileNum);
		properties.remove(propName);
	}
	
	public boolean getJailFreeCard() {
		return jailFreeCard;
	}
	public void addJailFreeCard() {
		jailFreeCard = true;
	}
	public void changeJailStatus(boolean jailStatus) {
		if (jailStatus == true) {
			jailTurnCount = 3;
			setPos(10);
		}
		this.jailStatus = jailStatus;
	}
	public boolean checkJailStatus() {
		return jailStatus;
	}
	public int jailTurnCount() {
		return jailTurnCount;
	}
	public void lessenJailSentence() {
		--jailTurnCount;
	}
}
