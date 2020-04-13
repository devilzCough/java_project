// Player Class
// This Class has Play Information for each Player.

import java.util.ArrayList;

public class Player {

	private ArrayList<Card> presentCards;
	private int playerNum;
	private boolean isPlaying;

	public Player (int i) {

		playerNum = i;
		presentCards = new ArrayList<>();
		isPlaying = true;
	} // Player()

	public int getPlayerNum() {
		return playerNum;
	} // getPlayerNum()

	public boolean getIsPlaying() {
		return isPlaying;
	} // getIsPlaying()

	public ArrayList<Card> getPresentCards() {
		return presentCards;
	} // getPresentCards()

	// to get Information of each Card
	public Card getCard(int index) {
		return presentCards.get(index);
	} // getCard()

	public void setPresentCards (Card newCard) {
		presentCards.add(newCard);
	} // setPresentCards()

	public void setPlayingState (boolean nowState) {
		isPlaying = nowState;
	} // setPlayingState()

	public void removeCard (int index) {
		presentCards.remove(index);
	} // removeCard()

	public boolean hasMagnetite () {
		for (Card card : presentCards) {
			if (card.getCardName().equals("Magnetite"))
				return true;
		}
		return false;
	} // hasMagnetite()

} // Player Class
