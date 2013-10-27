import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class MinesweeperSimulator extends JFrame {
	public final int BASE_WIDTH = 250;
	public final int BASE_HEIGHT = 28;
	private final int NUM_MENU = 5;
	private final int UNOPENED = -2;

	public Field grid;
	public JTextField field[];
	public JTextField currentSeedDisplay;
	public JTextField currentOpenedField;
	public JLabel label[];
	public JButton startButton;
	public int mineField[][];
	public boolean containsMine[][];

	// in game properties
	public int mines;
	public int width;
  	public int height;
	public Long seed;
	public int interval;

	private boolean firstOpening;
	private Timer timer;
	private Random random;
	private StartButtonListener startButtonListener;
	private TimerListener timerListener;

	public MinesweeperSimulator() {
		field = new JTextField[NUM_MENU];
		label = new JLabel[NUM_MENU];

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(280 + BASE_WIDTH, 280 + BASE_HEIGHT);
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

		// default values
		field[0].setText("10");
		field[1].setText("10");
		field[2].setText("20");
		field[3].setText("-1");
		field[4].setText("1000");

		JPanel actionMenu = new JPanel();
		actionMenu.setLayout(new GridLayout(3, 1));

		startButton = new JButton("Start");
		startButtonListener = new StartButtonListener(this);
		startButton.addActionListener(startButtonListener);

		currentSeedDisplay = new JTextField("current seed: __________________________");
		currentSeedDisplay.setEditable(false);

		currentOpenedField = new JTextField("opened: ?");
		currentOpenedField.setEditable(false);

		actionMenu.add(startButton);
		actionMenu.add(currentSeedDisplay);
		actionMenu.add(currentOpenedField);

		ret.add(textFieldMenu, BorderLayout.CENTER);
		ret.add(actionMenu, BorderLayout.SOUTH);

		return ret;
	}

	private Field makeField(){
		grid = new Field();
		return grid;
	}

	public void startSolving(){
		if (timerListener != null){
			timer.removeActionListener(timerListener);
		}

		timerListener = new TimerListener(this);
		timer = new Timer(interval, timerListener);
		mineField = new int[height][width];
		containsMine = new boolean[height][width];
		firstOpening = true;
		random = new Random(seed);

		// init
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				mineField[i][j] = -2;
				containsMine[i][j] = false;
			}
		}

		currentSeedDisplay.setText("current seed: " + seed);
		currentSeedDisplay.updateUI();

		System.out.println("game start!");
		timer.start();
	}

	public void openField(Point location){
		int i = location.x;
		int j = location.y;
		if (firstOpening){
			// pour mines
			for (int k = 0; k < mines; k++){
				int r,c;

				do{
					r = Math.abs(random.nextInt()) % height;
					c = Math.abs(random.nextInt()) % width;
				//	System.out.println(r + " " + c);
				} while (containsMine[r][c] || ((r == i) && (c == j)));

				// here is mine!
				containsMine[r][c] = true;
			}
			firstOpening = false;
		}

		// open normally, just when it is unopened
		if (mineField[i][j] == -2){
			if (containsMine[i][j]){
				// game ends
				timer.stop();		
				timer.removeActionListener(timerListener);
				timerListener = null;

				// display all mines
				for (int ii = 0; ii < height; ii++){
					for (int jj = 0; jj < width; jj++){
						if (containsMine[ii][jj]){
							mineField[ii][jj] = -1;
						}
					}
				}
				// show where did it hit
				mineField[i][j] = -3;

			}else{	
				mineField[i][j] = getValue(i, j);
				if (mineField[i][j] == 0){
					// do flood fill
					ff(i,j);
				}
			}
		}

		// update opened field
		int total = 0;
		for (int ii = 0; ii < height; ii++){
			for (int jj = 0; jj < width; jj++){
				if (mineField[ii][jj] != UNOPENED && !containsMine[ii][jj]){
					total++;
				}
			}
		}
		currentOpenedField.setText("Opened: " + total);

		// redraw
		grid.draw(mineField);
	}

	private int getValue(int i, int j){
		int ret = 0;
		for (int di = -1; di <= 1; di++){
			for (int dj = -1; dj <= 1; dj++){
				int ni = i + di;
				int nj = j + dj;

				if ((0 <= ni) && (ni < height) && (0 <= nj) && (nj < width)){
					if (containsMine[ni][nj]){
						ret++;
					}
				}
			}
		}
		return ret;
	}

	private void ff(int i, int j){
		mineField[i][j] = getValue(i, j);

		if (mineField[i][j] == 0){
			for (int di = -1; di <= 1; di++){
				for (int dj = -1; dj <= 1; dj++){
					int ni = i + di;
					int nj = j + dj;

					if ((0 <= ni) && (ni < height) && (0 <= nj) && (nj < width) && (mineField[ni][nj] == UNOPENED)){
						ff(ni, nj);
					}
				}
			}
		}
	}

	public static void main(String args[]){
		MinesweeperSimulator frame = new MinesweeperSimulator();

		Field grid = frame.makeField();
		JPanel menu = frame.makeMenu();
		frame.add(grid, BorderLayout.CENTER); grid.updateUI();
		frame.add(menu, BorderLayout.EAST); menu.updateUI();
	}

}