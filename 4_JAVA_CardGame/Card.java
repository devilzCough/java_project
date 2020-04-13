// Card Class
// This Class Stores Information for each Card.

public class Card {

	private String cardName;
	private String cardCategory;
	private String[] cardInfo;

	private boolean isSuperTrump;

	// Mineral Card
	public Card (String name, String hardnessValue, String specificGravityValue, String cleavageValue,
	             String crustalAbundanceValue, String economicValue) {

		cardName = name;

		cardInfo = new String[GameConstants.NUM_OF_CATE];
		cardInfo[0] = hardnessValue;
		cardInfo[1] = specificGravityValue;
		cardInfo[2] = cleavageValue;
		cardInfo[3] = crustalAbundanceValue;
		cardInfo[4] = economicValue;

		isSuperTrump = false;
	} // Card()

	// SuperTrump Card
	public Card (String name, String category) {
		cardName = name;
		cardCategory = category;
		isSuperTrump = true;
	} // Card()

	public String getCardName() {
		return cardName;
	} // getCardName()

	// if this Card is Super Trump, just return 'Super Trump'
	// else just Mineral Card, return the 'Value' of the card corresponding to 'cateindex'
	public String getCardInfo(int cateIndex) {

		if (isSuperTrump)
			return "Super Trump";
		else
			return cardInfo[cateIndex];
	} // getCardInfo()
} // Card Class
