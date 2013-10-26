import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MinesweeperSimulator extends JFrame {

	private final int NUM_MENU = 5;

	public JTextField field[];
	public JLabel label[];
	public int mineField[][];

	public MinesweeperSimulator() {
		field = new JTextField[NUM_MENU];
		label = new JLabel[NUM_MENU];

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(700, 500);
		setTitle("Minesweeper Simulator");
		setLayout(new BorderLayout());
	}

	private JPanel makeMenu() {
		JPanel ret = new JPanel();
		ret.setLayout(new BorderLayout());
		
		JPanel textFieldMenu = new JPanel();
		textFieldMenu.setLayout(new GridLayout(NUM_MENU, 2));
		
		String labelName[] = {"Width", "Height", "Mines", "Seed", "Interval (ms)"};

		for(int i = 0; i < NUM_MENU; i++) {
			field[i] = new JTextField();
			label[i] = new JLabel(labelName[i]);

			textFieldMenu.add(label[i]);
			textFieldMenu.add(field[i]);
		}

		ret.add(textFieldMenu, BorderLayout.CENTER);
		ret.add(new JButton("Start"), BorderLayout.SOUTH);

		return ret;
	}

	public static void main(String args[]){
		MinesweeperSimulator frame = new MinesweeperSimulator();

		Field grid = new Field();
		JPanel menu = frame.makeMenu();
		frame.add(grid, BorderLayout.CENTER); grid.updateUI();
		frame.add(menu, BorderLayout.EAST); menu.updateUI();
	}
}