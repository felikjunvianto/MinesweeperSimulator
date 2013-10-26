public class Agent{
	/////////////////////////////////////
	// some built-in stuff for you
	/////////////////////////////////////
	private final int MINE = -1;
	private final int UNOPENED = -2;

	private boolean isNumber(int value){
		return (0 <= value) && (value <= 8);
	}
	private boolean isMine(int value){
		return (value == MINE);
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
	private Point randomPick(int[][] map){
		int r = getRow(map);
		int c = getColumn(map);
		for (int i = 0; i < r; i++){
			for (int j = 0; j < c; j++){
				if (isUnopened(map[i][j])){
					return new Point(i,j);
				}
			}
		}
	}
	/////////////////////////////////////


	/////////////////////////////////////
	// TODO: here is your implementation 
	/////////////////////////////////////
	public Point getNextOpenedField(int[][] map){
		// random demo, just for fun :)
		return randomPick(map);
	}
}
