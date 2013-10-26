public class StartButtonListener implements ActionListener{
	private final int BASE_WIDTH = 120;
	private final int BASE_HEIGHT = 20;
	private MinesweeperSimulator container;

	public StartButtonListener(MinesweeperSimulator container){
		this.container = container;
	}

	public void actionPerformed(ActionEvent event){
		try{
			int width = Integer.parseInt(widthField.getText());
			int height = Integer.parseInt(heightField.getText());
			int mines = Integer.parseInt(minesField.getText());
			long seed = Long.parseLong(seedField.getText());
			int interval = int.parseInt(intervalField.getText());

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
