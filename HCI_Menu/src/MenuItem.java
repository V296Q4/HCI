import javax.swing.JMenuItem;


public class MenuItem {

	private JMenuItem menuObject;
	private int count;
	private int lastUsed = 0;
	private int absolutePosition = 0;
	
	MenuItem(JMenuItem menuObject, int count, int absolutePosition){
		this.menuObject = menuObject;
		this.count = count;
		this.absolutePosition = absolutePosition;
	}
	
	public JMenuItem GetMenuObject(){
		return menuObject;
	}
	
	public int GetCount(){
		return count;
	}
	
	public int GetLastUsed(){
		return lastUsed;
	}
	
	public int GetAbsolutePosition(){
		return absolutePosition;
	}

	public void SetName(JMenuItem newName){
		menuObject = newName;
	}
	
	public void SetCount(int newCount){
		count = newCount;
	}
	
	public void SetLastUsed(int newLastUsed){
		lastUsed = newLastUsed;
	}
	
	public void SetAbsolutePosition(int newAbsolutePosition){
		absolutePosition = newAbsolutePosition;
	}

}
