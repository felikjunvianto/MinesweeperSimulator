import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

public class Field extends JComponent{
	public Field(){

	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;

		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 4; j++){
				Image img = Toolkit.getDefaultToolkit().getImage("asset/field_unopened.png");
				g2.drawImage(img, 28*i, 28*j, this);
				g2.finalize();
			}
		}
	}	
}