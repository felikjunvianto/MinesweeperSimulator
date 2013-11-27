import java.awt.Point;
import java.util.Scanner;

public class WrappedAgent{
	private Scanner reader;
	private int _row;
	private int _column;
	private final double EPS = 1e-8;

	/////////////////////////////////////
	// some built-in stuff for you
	/////////////////////////////////////
	private final int UNOPENED = -2;

	private boolean isNumber(int value){
		return (0 <= value) && (value <= 8);
	}
	private boolean isUnopened(int value){
		return (value == UNOPENED);
	}
	private int getRow(int[][] map){
		return map.length;
	}
	private int getColumn(int[][] map){
		return map[0].length;
	}
	// for demo purpose only
	private Point mostUpperLeft(int[][] map){
		int r = getRow(map);
		int c = getColumn(map);
		for (int i = 0; i < r; i++){
			for (int j = 0; j < c; j++){
				if (isUnopened(map[i][j])){
					return new Point(i,j);
				}
			}
		}
		// default
		return new Point(1, 1);
	}
	/////////////////////////////////////


	/////////////////////////////////////
	// TODO: here is your implementation 
	/////////////////////////////////////
	public Point getNextOpenedField(int[][] map){
		// demo, just for fun :)
		return mostUpperLeft(map);
	}

	public void work(){
		reader = new Scanner(System.in);
		_row = reader.nextInt();
		_column = reader.nextInt();

		int[][] map = new int[_row][_column];
		for (int i = 0; i < _row; i++){
			for (int j = 0; j < _column; j++){
				map[i][j] = reader.nextInt();
			}
		}
		while (true){
			Point p = getNextOpenedField(map);
			int tx = (int)(p.getX() + EPS);
			int ty = (int)(p.getY() + EPS);
			System.out.println(tx + " " + ty);

			String signal = reader.next();
			if (signal.equals("true")) break;

			for (int i = 0; i < _row; i++){
				for (int j = 0; j < _column; j++){
					map[i][j] = reader.nextInt();
				}
			}
		}
	}

	public static void main(String args[]){
		WrappedAgent agent = new WrappedAgent();
		agent.work();
	}
}
