import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ToolbarGUI_B implements MouseMotionListener{

	private JFrame frmHciFeature;
	private String[] imageNames = new String[]{"A","B","C","D","E","F","G","H","I","J"};
	private ButtonItem[] buttonItems = new ButtonItem[10];
	private JLabel textClickOn;
	private ButtonItem currentTarget;
	private boolean hasStarted = false;
	private int clickCount = 0;
	private Log log;
	private int screenHeight, screenWidth;
	private float mouseX, mouseY;
	private float targetTime;
	private final int ITERATIONS = 50;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ToolbarGUI_B window = new ToolbarGUI_B();
					window.frmHciFeature.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ToolbarGUI_B() throws IOException {
		initialize();
	}

	private void initialize() throws IOException {
		frmHciFeature = new JFrame();
		frmHciFeature.setTitle("HCI Feature 1b - Toolbar");
		frmHciFeature.setBounds(100, 100, 450, 300);
		frmHciFeature.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmHciFeature.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHciFeature.addMouseMotionListener(this);
		Toolkit tk = Toolkit.getDefaultToolkit();
		screenHeight = (int) tk.getScreenSize().getHeight();
		screenWidth = (int) tk.getScreenSize().getWidth();
		frmHciFeature.getContentPane().setLayout(null);
		
		textClickOn = new JLabel();
		textClickOn.setBounds(screenWidth/2-50, 50, 350, 50);
		textClickOn.setText("Click on");
		frmHciFeature.getContentPane().add(textClickOn);
		
		for(int i = 0; i < 10; i++){
			
			URL url = ToolbarGUI_B.class.getResource("/resources/image" + imageNames[i] + ".png");
			ImageIcon image = new ImageIcon(url);

			buttonItems[i] = new ButtonItem(new JButton(image), i, imageNames[i]);
			buttonItems[i].SetCenterPoint(new Point(0,i*50));
			buttonItems[i].GetButtonObject().addMouseMotionListener(this);
			buttonItems[i].GetButtonObject().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ButtonObjectClicked((JButton) arg0.getSource());
				}
			});
			frmHciFeature.getContentPane().add(buttonItems[i].GetButtonObject());
		}
		
		Panel panel = new Panel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(screenWidth/6-25, screenHeight/2-25, 50, 50);
		panel.setLayout(null);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!hasStarted){
					hasStarted = true;
					log = new Log("toolbarB");
					log.nout("ToolbarGUI_B");
					SetItemTarget();
				}
			}
		});
		frmHciFeature.getContentPane().add(panel);
		
		CalculatePositions();
	}
	
	private void SetItemTarget(){
		int x = GetRandomInt(0,9);
		currentTarget = buttonItems[x];
		textClickOn.setText("Click on " + currentTarget.GetStringId() + " as fast as possible.");
		targetTime = System.nanoTime();
	}
	
	private void ButtonObjectClicked(JButton jb){
		if(hasStarted && jb.equals(currentTarget.GetButtonObject())){
			clickCount++;
			int time = Math.round((System.nanoTime() - targetTime)/1000000);
			if(clickCount >= ITERATIONS){
				hasStarted = false;
				clickCount = 0;
				log.out(time + "");
				log.EndLog();
				textClickOn.setText("Click on ");
			}
			else{
				log.out(time + ", ");
				SetItemTarget();
			}
		}
	}
	
	private void CalculatePositions(){
		SetDistances();
		for(int i = 0; i < 10; i++){
			float modifier = (float) (buttonItems[i].GetDistanceToCursor() + Math.pow(buttonItems[i].GetYDistance(), 0.7));
			int size = (int) Math.max(120 - modifier, 50);
			if(buttonItems[i].GetDistanceToCursor() < 20){
				buttonItems[i].SetSize(new Point(size, size));
			}
			else{
				buttonItems[i].SetSize(new Point(50, 50));
			}
			int sumOfPriors = 0;
			for(int j = 0; j < i; j++){
				sumOfPriors += buttonItems[j].GetSize().getY();
			}
			buttonItems[i].SetCenterPoint(new Point((int)(screenWidth-buttonItems[i].GetSize().getX()/2), (int)(buttonItems[i].GetSize().getY()/2 + sumOfPriors)));
		}
	}
	

	@Override
	public void mouseDragged(MouseEvent arg0) {
		//Do nothing
	}

	@Override
	public void mouseMoved(MouseEvent eve) {
		mouseX = (float) eve.getLocationOnScreen().getX();
		mouseY = (float) eve.getLocationOnScreen().getY();
		CalculatePositions();
	}
	
	private int GetRandomInt(int min, int max){
		return (int)(Math.random()*(max+1) + min);
	}
	
	private void SetDistances(){
		float distance = 0;
		for(int i = 0; i < 10; i++){
			Point p = buttonItems[i].GetCenterPoint();
			distance = (float) Math.pow(Math.abs(p.getX() - mouseX) + Math.abs(p.getY() - mouseY), 1f/2f);
			buttonItems[i].SetDistanceToCursor(distance);
			buttonItems[i].SetYDistance((float)Math.abs(p.getY() - mouseY));
		}
	}
}
