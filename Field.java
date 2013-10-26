import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.JComponent;

public class Field extends JComponent{
	public Field(){

	}

	public void paintComponent(Graphics g){
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 4; j++){
				Image img = Image.createImage("asset/field_unopened.png");
				g.drawImage(img, 28*i, 28*j, 0);
			}
		}
	}	
}