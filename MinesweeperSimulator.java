import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MinesweeperSimulator extends JFrame{

	private static JTextField field[];
	private static JLabel label[];

	public MinesweeperSimulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(700, 500);
		setTitle("Minesweeper Simulator");
		setLayout(new BorderLayout());
	}

	private static JPanel makeMenu() {
		JPanel ret = new JPanel();
		ret.setLayout(new BorderLayout());
		
		JPanel textFieldMenu = new JPanel();
		textFieldMenu.setLayout(new GridLayout(5, 2));
		field = new JTextField[5];
		label = new JLabel[5];
		String labelName[] = {"Width", "Height", "Mines", "Seed", "Interval (ms)"};

		for(int i = 0; i < 5; i++) {
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
		JPanel menu = makeMenu();
		frame.add(grid, BorderLayout.CENTER); grid.updateUI();
		frame.add(menu, BorderLayout.EAST); menu.updateUI();

	}
}