import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class CommandSimulator {
	private final int UNOPENED = -2;

	// main attribute
	public int mineField[][];
	public boolean containsMine[][];

	// in game properties
	public int mines;
	public int width;
  	public int height;
	public Long seed;

	private boolean firstOpening;
	private Timer timer;
	private Random random;

	private Scanner reader;

	private boolean openField(Point location){
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
				} while (containsMine[r][c] || ((Math.abs(r - i) <= 1) && (Math.abs(c-j) <= 1)));

				// here is mine!
				containsMine[r][c] = true;
			}
			firstOpening = false;
		}

		// open normally, just when it is unopened
		boolean ended = false;
		if (mineField[i][j] == -2){
			if (containsMine[i][j]){
				// game ends
				ended = true;
		
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

		// win?
		if (total == width*height-mines){
			ended = true;

			// display all mines, flagged
			for (int ii = 0; ii < height; ii++){
				for (int jj = 0; jj < width; jj++){
					if (containsMine[ii][jj]){
						mineField[ii][jj] = -4;
					}
				}
			}
		}

		// signal
		System.out.println(ended);
		if (ended){
			double all = width*height-mines;

			try{
				PrintWriter writer;
				writer = new PrintWriter(".tempo");
				writer.println("score: " + total/all);
				writer.close();
			}catch (Exception e){
				
			}
		}

		return ended;
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

	private Point getLocation(){
		int r = reader.nextInt();
		int c = reader.nextInt();

		return new Point(r, c);
	}

	private void solve(String[] args){
		reader = new Scanner(System.in);
		height = Integer.parseInt(args[0]);
		width = Integer.parseInt(args[1]);
		mines = Integer.parseInt(args[2]);
		seed = Long.parseLong(args[3]);

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

		System.out.println(height + " " + width);
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				System.out.print(mineField[i][j] + " ");
			}
			System.out.println();
		}
		while (!openField(getLocation())){
			for (int i = 0; i < height; i++){
				for (int j = 0; j < width; j++){
					System.out.print(mineField[i][j] + " ");
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] args) throws Exception{
		CommandSimulator p = new CommandSimulator();
		p.solve(args);
	}
}