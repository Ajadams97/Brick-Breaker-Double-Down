package application.model;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class levels {
	
	ArrayList<Node> brix = new ArrayList<Node>();
	
	public levels() {
		
	}

	public void addBrick(bricks brick) {
		brix.add(brick.getShape());
	}
	
	public void LevelOne() {
		
		Rectangle temp;
		
		for (int i = 0; i < 10; i++) {
			for (int p = 0; p < 22; p++) {
				
				temp = new Rectangle(p*40 + 65, i*20 + 40 , 30, 10);
				if (i < 5) {
					temp.setFill(Color.AQUA);
				}
				
				if (i > 4) {
					temp.setFill(Color.CHARTREUSE);
				}

				brix.add(temp);
			}
		}
	}
	
	public ArrayList getLevel() {
		return this.brix;
	}
	
}
