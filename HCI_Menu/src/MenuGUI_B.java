import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MenuGUI_B {
	private Log log;
	private JMenuBar menuBar;
	private boolean hasStarted = false;
	private int clickCount = 0;
	private int curX, curY;
	private float newTargetTime = 0;
	private MenuItem currentTarget;
	private String[] menuTitles = {"Fruits", "Vegetables", "Occupations", "Sciences", "Numbers", "Letters"};
	private JMenu[] menuObjects = new JMenu[6];
	private JMenuItem[][] menuItems = new JMenuItem[6][10];
	private MenuItem[][] mio = new MenuItem[6][10];
	private PriorityQueue<MenuItem> pQ;
	private String[][] menuItemStrings = {{"Apple", "Banana", "Grape", "Mango", "Orange", "Peach", "Pineapple", "Raspberry", "Strawberry", "Watermelon"},
			{"Carrot", "Celery", "Cucumber", "Garlic", "Mushroom", "Onion", "Pea", "Potato", "Spinach", "Tomato"},
			{"Cashier", "Chef", "Dentist", "Doctor", "Farmer", "Janitor", "Salesperson", "Secretary", "Teacher", "Waiter"},
			{"Anthropology", "Astronomy", "Biology", "Botany", "Chemistry", "Cosmology", "Geology", "Physics", "Psychology", "Zoology"},
			{"Eight","Five","Four","Nine","One","Seven","Six","Three","Two","Zero"},
			{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}};
	private JFrame frame;
	private JLabel textClickOn;
	private final int ITERATIONS = 50;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuGUI_B window = new MenuGUI_B();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuGUI_B() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("HCI Feature 2b - Menu");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		int height = (int) tk.getScreenSize().getHeight();
		int width = (int) tk.getScreenSize().getWidth();

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(null);

		textClickOn = new JLabel();
		textClickOn.setBounds(width/2-25, height-125, 350, 50);
		textClickOn.setText("Click on");
		frame.getContentPane().add(textClickOn);
		
		Panel panel = new Panel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(width/2-25, height/2-25, 50, 50);
		panel.setLayout(null);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!hasStarted){
					log = new Log("menuB");
					log.nout("MenuGUI_B");
					hasStarted = true;
					SetItemTarget();
				}
			}
		});
		frame.getContentPane().add(panel);
		
		for(int j = 0; j <= 5; j++){
			menuObjects[j] = new JMenu(menuTitles[j]);
			menuBar.add(menuObjects[j]);
			for(int i = 0; i <= 9; i++){
				menuItems[j][i] = new JMenuItem(menuItemStrings[j][i]);
				mio[j][i] = new MenuItem(menuItems[j][i], 0, i);
				menuItems[j][i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						MenuItemClicked(arg0);
					}
				});
				menuObjects[j].add(menuItems[j][i]);
			}
		}
	}
	
	
	private void SetItemTarget(){
		curX = GetRandomInt(0,5);
		curY = GetRandomInt(0,9);
		currentTarget = mio[curX][curY];
		textClickOn.setText("Click on " + currentTarget.GetMenuObject().getText() + " as fast as possible.");
		newTargetTime = System.nanoTime();
	}
	
	private void MenuItemClicked(ActionEvent ae){
		JMenuItem jmi = (JMenuItem) ((JMenuItem) ae.getSource()).getComponent();
		if(hasStarted && (jmi.getText()).equals(currentTarget.GetMenuObject().getText())){
			clickCount++;
			int time = Math.round((System.nanoTime() - newTargetTime)/1000000);

			if(clickCount >= ITERATIONS){
				textClickOn.setText("Click on ");
				hasStarted = false;
				clickCount = 0;
				log.out(time+"");
				log.EndLog();
				menuBar = new JMenuBar();
				frame.setJMenuBar(menuBar);
				for(int j = 0; j <= 5; j++){
					menuObjects[j] = new JMenu(menuTitles[j]);
					menuBar.add(menuObjects[j]);
					for(int i = 0; i <= 9; i++){
						menuItems[j][i] = new JMenuItem(menuItemStrings[j][i]);
						mio[j][i] = new MenuItem(menuItems[j][i], 0, i);
						menuItems[j][i].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								MenuItemClicked(arg0);
							}
						});
						menuObjects[j].add(menuItems[j][i]);
					}
				}
			}
			else{
				mio[curX][currentTarget.GetAbsolutePosition()].SetCount(mio[curX][currentTarget.GetAbsolutePosition()].GetCount() + 1);
				mio[curX][currentTarget.GetAbsolutePosition()].SetLastUsed(clickCount);
				
				Comparator<MenuItem> comp = new ItemComparator();
				pQ = new PriorityQueue<MenuItem>(10, comp);
				
				for(int i = 0; i < 10; i++){
					pQ.add(mio[curX][i]);
				}
				
				menuObjects[curX].removeAll();
				
				for(int i = 0; i < 10; i++){
					MenuItem mi = pQ.remove();
					menuItems[curX][i] = new JMenuItem(menuItemStrings[curX][mi.GetAbsolutePosition()]);
					menuItems[curX][i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							MenuItemClicked(arg0);
						}
					});
					menuObjects[curX].add(menuItems[curX][i]);
				}
				log.out(time + ", ");
				SetItemTarget();
			}
		}
	}
	
	private int GetRandomInt(int min, int max){
		return (int)(Math.random()*(max+1) + min);
	}

}
