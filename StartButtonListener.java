import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

public class StartButtonListener implements ActionListener{
	private final int BASE_WIDTH = 180;
	private final int BASE_HEIGHT = 40;
	private MinesweeperSimulator container;
	private Random random;

	public StartButtonListener(MinesweeperSimulator container){
		this.container = container;
		random = new Random();
	}

	public void actionPerformed(ActionEvent event){
		try{
			int width = Integer.parseInt(container.field[0].getText());
			int height = Integer.parseInt(container.field[1].getText());
			int mines = Integer.parseInt(container.field[2].getText());
			long seed = Long.parseLong(container.field[3].getText());
			int interval = Integer.parseInt(container.field[4].getText());

			if (!valid(width, height, mines, seed, interval)){
				throw new Exception();
			}

			// resize window
			container.setSize(BASE_WIDTH + 28*width, BASE_HEIGHT + 28*height);
			
			// send current information
			container.mines = mines;
			container.width = width;
			container.height = height;
			container.interval = interval;

			if (seed < 0){
				container.seed = random.nextLong();	
			}
			
			// invoke timer event
			container.startSolving();

		}catch (Exception e){
			// do nothing
		}
	}

	private boolean valid(int row, int column, int mines, Long seed, int interval){
		return ((3 <= row ) && (row <= 100) &&
			    (3 <= column) && (column <= 100) &&
			    (1 <= mines) && (mines <= row*column-10) &&
			    (100 <= interval));
	}
}
