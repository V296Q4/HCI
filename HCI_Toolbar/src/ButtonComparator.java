import java.util.Comparator;


public class ButtonComparator implements Comparator<ButtonItem>{

	public int compare(ButtonItem b1, ButtonItem b2) {
		int value = 0;
		if(b1.GetDistanceToCursor() > b2.GetDistanceToCursor()){
			value = 1;
		}
		else{
			value = -1;
		}
		return value;
	}

}
