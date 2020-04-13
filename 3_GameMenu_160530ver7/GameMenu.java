public class GameMenu
{
	private String 		strTitle;
	private String[] 	strMenu;
	private int			nMenuCount;
	
	public GameMenu() {
		strTitle = "";
		nMenuCount = 0;
		strMenu = new String[100];	
	} // default

	public GameMenu(String title, String[] menu, int count) {
		strTitle = title;
		nMenuCount = count;
		strMenu = new String[100];
		for (int i=0; i<nMenuCount; i++) {
			strMenu[i] = menu[i];
		}	
	} // store data
	
	public String 	getTitle() 		{ return strTitle; }
	public String[] getMenu() 		{ return strMenu; }
	public String   getMenu(int index) { return strMenu[index]; }
	public int 		getMenuCount() 	{ return nMenuCount; }
	public void setTitle(String title) { strTitle = title; }
	public void setMenu(String[] menu) {
		for (int i=0; i<nMenuCount; i++) {
			strMenu[i] = menu[i];	
		}
	}
	public void setMenuCount(int count) { nMenuCount = count; }
	// set/get for Menu 
}