import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;

public class TimerListener implements ActionListener{
	private MinesweeperSimulator container;
	private Agent userAI;

	public TimerListener(MinesweeperSimulator container){
		this.container = container;
		userAI = new Agent();
	}

	public void actionPerformed(ActionEvent event){
		Point location = userAI.getNextOpenedField(container.mineField);
		container.openField(location);
	}
}