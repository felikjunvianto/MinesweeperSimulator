import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

public class StartButtonListener implements ActionListener{
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
			container.setSize(container.BASE_WIDTH + 28*width, container.BASE_HEIGHT + 28*height);
			
			// send current information
			container.mines = mines;
			container.width = width;
			container.height = height;
			container.interval = interval;

			if (seed == -1){
				container.seed = random.nextLong();	
			}else{
				container.seed = seed;
			}
			
			// invoke timer event
			container.startSolving();

		}catch (Exception e){
			System.out.println("some constraint are not valid!");
		}
	}

	private boolean valid(int row, int column, int mines, Long seed, int interval){
		return ((3 <= row ) && (row <= 30) &&
			    (3 <= column) && (column <= 30) &&
			    (1 <= mines) && (mines <= row*column-10) &&
			    (100 <= interval));
	}
}
