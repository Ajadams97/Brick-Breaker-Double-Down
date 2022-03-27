package application.model;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class bricks {
	
	public Rectangle brick;
	
	public bricks() {
		brick = new Rectangle(50, 50, 30, 10);
		brick.setFill(Color.AQUA);
	}

	public bricks(int posX, int posY, int width, int height, Color fill) {
		brick = new Rectangle(posX, posY, width, height);
		brick.setFill(fill);
	}
	
	public Rectangle getShape() {
		return this.brick;
	}
	
	
}
