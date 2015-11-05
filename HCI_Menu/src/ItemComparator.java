import java.util.Comparator;

public class ItemComparator implements Comparator<MenuItem>{
	@Override
	public int compare(MenuItem x, MenuItem y){
		int value = 0;
		if(x.GetCount() > y.GetCount()){
			value = -1;
		}
		else if(x.GetCount() < y.GetCount()){
			value = 1;
		}
		else{
			if(x.GetLastUsed() > y.GetLastUsed()){
				value = -1;
			}
			else if(x.GetLastUsed() < y.GetLastUsed()){
				value = 1;
			}
			else{
				if(x.GetAbsolutePosition() < y.GetAbsolutePosition()){
					value = -1;
				}
				else if(x.GetAbsolutePosition() > y.GetAbsolutePosition()){
					value = 1;
				}
				else{
					value = 0;
				}
			}
		}
		return value;
	}
}
