import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

public class Field extends JComponent{
	private int[][] map;

	public void paintComponent(Graphics g){
		if (map == null) return;

		Graphics2D g2 = (Graphics2D)g;

		int r = map.length;
		int c = map[0].length;
		Toolkit kit = Toolkit.getDefaultToolkit();
		for (int i = 0; i < r; i++){
			for (int j = 0; j < c; j++){
				Image img;

				if (map[i][j] == -1){
					img = kit.getImage("asset/field_mine.png");
				}else if (map[i][j] == -2){
					img = kit.getImage("asset/field_unopened.png");
				}else if (map[i][j] == -3){
					img = kit.getImage("asset/field_minehit.png");
				}else if (map[i][j] == -4){
					img = kit.getImage("asset/field_flag.png");
				}else{
					img = kit.getImage("asset/field_" + map[i][j] + ".png");
				}

				g2.drawImage(img, 28*j, 28*i, this);
				g2.finalize();
			}
		}
	}	

	public void draw(int[][] map){
		this.map = map;
		repaint();
	}
}