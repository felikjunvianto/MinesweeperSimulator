import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartButtonListener implements ActionListener{
	private final int BASE_WIDTH = 120;
	private final int BASE_HEIGHT = 20;
	private MinesweeperSimulator container;

	public StartButtonListener(MinesweeperSimulator container){
		this.container = container;
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
			container.seed = seed;

			// invoke timer event
			container.startTimer();

		}catch (Exception e){
			// do nothing
		}
	}
}
