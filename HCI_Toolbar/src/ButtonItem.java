import java.awt.Point;
import javax.swing.JButton;

public class ButtonItem {
	private JButton buttonObject;
	private int id;
	private String stringId;
	private Point centerPoint = new Point(0,0);
	private Point size = new Point(50,50);
	private float distanceToCursor = 1000;
	private float yDistance = 1000;
	
	ButtonItem(JButton buttonObject, int id, String stringId){
		this.buttonObject = buttonObject;
		this.id = id;
		this.stringId = stringId;
	}
	
	public JButton GetButtonObject(){
		return buttonObject;
	}
	
	public int GetId(){
		return id;
	}
	
	public String GetStringId(){
		return stringId;
	}
	
	public Point GetCenterPoint(){
		return centerPoint;
	}
	
	public Point GetSize(){
		return size;
	}
	
	public float GetDistanceToCursor(){
		return distanceToCursor;
	}
	
	public float GetYDistance(){
		return yDistance;
	}
	
	public void SetCenterPoint(Point newCenterPoint){
		centerPoint = newCenterPoint;
		buttonObject.setBounds((int)(centerPoint.getX() - (size.getX()/2)), (int)(centerPoint.getY() - size.getY()/2), (int)size.getX(), (int)size.getY());
	}
	
	public void SetSize(Point newSize){
		size = newSize;
		buttonObject.setSize((int)size.getX(), (int)size.getY());
	}
	
	public void SetDistanceToCursor(float newDistance){
		distanceToCursor = newDistance;
	}
	
	public void SetYDistance(float newY){
		yDistance = newY;
	}

}
