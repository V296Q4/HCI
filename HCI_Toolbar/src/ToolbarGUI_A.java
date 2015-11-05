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
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ToolbarGUI_A{

	private JFrame frmHciFeature;
	private String[] imageNames = new String[]{"A","B","C","D","E","F","G","H","I","J"};
	private ButtonItem[] buttonItems = new ButtonItem[10];
	private JLabel textClickOn;
	private ButtonItem currentTarget;
	private boolean hasStarted = false;
	private int clickCount = 0;
	private Log log;
	private int screenHeight, screenWidth;
	private float targetTime;
	private final int ITERATIONS = 50;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ToolbarGUI_A window = new ToolbarGUI_A();
					window.frmHciFeature.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ToolbarGUI_A() throws IOException {
		initialize();
	}

	private void initialize() throws IOException {
		frmHciFeature = new JFrame();
		frmHciFeature.setTitle("HCI Feature 1a - Toolbar");
		frmHciFeature.setBounds(100, 100, 450, 300);
		frmHciFeature.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmHciFeature.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		screenHeight = (int) tk.getScreenSize().getHeight();
		screenWidth = (int) tk.getScreenSize().getWidth();
		frmHciFeature.getContentPane().setLayout(null);
		
		textClickOn = new JLabel();
		textClickOn.setBounds(screenWidth/2-50, 50, 350, 50);
		textClickOn.setText("Click on");
		frmHciFeature.getContentPane().add(textClickOn);
		
		int sumOfPriors = 0;
		for(int i = 0; i < 10; i++){
			
			URL url = ToolbarGUI_A.class.getResource("/resources/image" + imageNames[i] + ".png");
			ImageIcon image = new ImageIcon(url);

			buttonItems[i] = new ButtonItem(new JButton(image), i, imageNames[i]);
			buttonItems[i].SetCenterPoint(new Point(0,i*50));
			buttonItems[i].GetButtonObject().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ButtonObjectClicked((JButton) arg0.getSource());
				}
			});
			buttonItems[i].SetCenterPoint(new Point((int)(screenWidth-buttonItems[i].GetSize().getX()/2), (int)(buttonItems[i].GetSize().getY()/2 + sumOfPriors)));
			sumOfPriors += buttonItems[i].GetSize().getY();
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
					log = new Log("toolbarA");
					log.nout("ToolbarGUI_A");
					SetItemTarget();
				}
			}
		});
		frmHciFeature.getContentPane().add(panel);
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

	
	private int GetRandomInt(int min, int max){
		return (int)(Math.random()*(max+1) + min);
	}
	
}
