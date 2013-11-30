import java.awt.Point;
import java.util.*;

public class Agent{
	private Scanner reader;
	private int _row;
	private int _column;
	
	private final int UNOPENED = -2;
	private final int UNKNOWN = 0;
	private final int MINE = 1;
	private final int SAFE = 2;
	private final double EPS = 1e-8;
	private final boolean DEBUG = false;

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
					System.out.println("Opening " + i + " " + j);
					return new Point(i,j);
				}
			}
		}
		// default
		return new Point(1, 1);
	}
	/////////////////////////////////////

	private Point firstToOpen;
	private ArrayList<Point> safePool;
	private int[][] stat;
	private int r,c;
	private Random random;

	public Agent(){
		safePool = new ArrayList<Point>();
		firstToOpen = new Point(0, 0);
		random = new Random();
	}

	private int toLinear(int x, int y){
		return c*x + y;
	}

	public Point getNextOpenedField(int[][] map){
		Point toOpen = null;
		
		// first opening?
		if (stat == null){
			r = getRow(map);
			c = getColumn(map);

			stat = new int[r][c];
			return firstToOpen;

		}else if (safePool.size() == 0){
			// generates new elements to pool
			// updates information
			for (int i = 0; i < r; i++){
				for (int j = 0; j < c; j++){
					if (map[i][j] != UNOPENED){
						stat[i][j] = SAFE;
					}
				}
			}

			// transforms map into equation
			double[][] equation = new double[r*c][r*c + 1];
			int id = 0;
			for (int i = 0; i < r; i++){
				for (int j = 0; j < c; j++){
					if ((map[i][j] != UNOPENED) && (map[i][j] != 0)){
						fillEquation(equation[id], i,j, map[i][j]);
						id++;
					}
				}
			}

			// get informations before
			extractInfo(equation);

			// do elemination
			reduce(equation);

			// get informations
			extractInfo(equation);


			if (DEBUG){
				System.out.println("---");
				for (int i = 0; i < r*c; i++){
					for (int j = 0; j <= r*c; j++){
						System.out.print(equation[i][j] + " ");
					}
					System.out.println();
				}
			}	

			// still zero? insert random perimeter
			if (safePool.size() == 0){
				ArrayList<Point> perimeter = new ArrayList<Point>();
				ArrayList<Point> nonPerimeter = new ArrayList<Point>();
				for (int i = 0; i < r; i++){
					for (int j = 0; j < c; j++){
						if (isPerimeter(i,j,map)){
							perimeter.add(new Point(i,j));
						}else{
							nonPerimeter.add(new Point(i,j));
						}
					}
				}

				if (perimeter.size() > 0){
					int di = Math.abs(random.nextInt()) % perimeter.size();
					safePool.add(perimeter.get(di));
				}else{
					int di = Math.abs(random.nextInt()) % nonPerimeter.size();
					safePool.add(nonPerimeter.get(di));
				}
			}
		}

		int lastId = safePool.size()-1;
		toOpen = safePool.get(lastId);
		safePool.remove(lastId);

		if (DEBUG){
			System.out.println("pool: ");
			for (int i = 0; i < safePool.size(); i++){
				System.out.println(safePool.get(i));
			}
		}
	
		return toOpen;
	}

	private void fillEquation(double[] vector, int i, int j, int number){
		int val = number;
		for (int ii = -1; ii <= 1; ii++){
			for (int jj = -1; jj <= 1; jj++){
				if ((ii == 0) && (jj == 0)) continue;

				int ni = i + ii;
				int nj = j + jj;
				if ((0 <= ni) && (ni < r) && (0 <= nj) && (nj < c)){
					vector[toLinear(ni,nj)] = 0;
					if (stat[ni][nj] == UNKNOWN){
						vector[toLinear(ni,nj)] = 1;
					}else if (stat[ni][nj] == MINE){
						val--;
					}
				}
			}
		}
		vector[vector.length - 1] = val;
	}

	private void reduce(double[][] m){
		int l = 0;
		int lastTreated = -1;
		int p = 0;
		boolean[] sure = new boolean[r*c];

		// for each column
		for (int j = 0; j < r*c; j++){
			boolean empty = true;
			for (int i = p; i < r*c; i++){
				if (Math.abs(m[i][j]) > EPS){
					l = i;
					empty = false;
					break;
				}
			}
			if (empty){
				// nothing to do here
				continue;
			}
			
			// swap row
			for (int k = j; k <= r*c; k++){
				double t = m[p][k];
				m[p][k] = m[l][k];
				m[l][k] = t;
			}

			for (int i = p+1; i < r*c; i++){
				for (int k = r*c; k >= j; k--){
					m[i][k] -= m[p][k] * m[i][j] / m[p][j];
				}
			}

			lastTreated = p;
			p++;
		}
		// can proceed?
		boolean singeSolution = false;
		if (lastTreated != -1){
			int cnt = 0;
			for (int j = 0; j < r*c; j++){
				if (Math.abs(m[lastTreated][j]) > EPS){
					cnt++;
				}
			}
			if (cnt == 1){
				singeSolution = true;
			}
		}

		if (singeSolution){
			// push up
			for (int j = lastTreated; j >= 0; j--){
				int mainId = -1;
				for (int k = 0; k < r*c; k++){
					if (Math.abs(m[j][k]) > EPS){
						mainId = k;
						break;
					}
				}

				// normalize
				double norm = 1/m[j][mainId];
				for (int k = mainId; k <= r*c; k++){
					m[j][k] *= norm;
				}

				int cnt = 0;
				for (int k = 0; k < r*c; k++){
					if (Math.abs(m[j][k]) > EPS){
						cnt++;
					}
				}

				if (cnt == 1){
					// nullify upper part
					for (int j2 = j-1; j2 >= 0; j2--){
						m[j2][r*c] -= m[j2][mainId] * m[j][r*c];
						m[j2][mainId] = 0;
					}
				}
			}
		}
	}

	private void extractInfo(double[][] m){
		
	}

	private boolean isPerimeter(int i, int j, int[][] m){
		if (stat[i][j] != UNKNOWN) return false;

		for (int ii = -1; ii <= 1; ii++){
			for (int jj = -1; jj <= 1; jj++){
				if (Math.abs(ii) + Math.abs(jj) <= 1){
					int ni = i + ii;
					int nj = j + jj;
					if ((0 <= ni) && (ni < r) && (0 <= nj) && (nj < c)){
						if (stat[ni][nj] != UNKNOWN){
							return true;
						}
					}
				}
			}
		}
		return false;
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
}
