import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MinesweeperSimulator extends JFrame{
	public MinesweeperSimulator(){

	}

	public static void main(String args[]){
		MinesweeperSimulator frame = new MinesweeperSimulator();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(700, 500);
		
		Field a = new Field();
		frame.add(a);
	}
}