package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import application.controller.movement;
import application.model.bricks;
import application.model.levels;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		Circle ball = new Circle(500, 540, 5, Color.ALICEBLUE);
		Rectangle rect = new Rectangle(485, 550, 30, 10);
		rect.setFill(Color.ORANGE);
		Rectangle player2 = new Rectangle(485, 560, 30, 10);
		player2.setStroke(Color.HOTPINK);
		player2.setStrokeType(StrokeType.INSIDE);
		player2.setStrokeWidth(3);
		player2.setFill(Color.WHITE);
		
		Text score = new Text();
		score.setX(25);
		score.setY(25);
		score.setFill(Color.WHITE);
		
		Image gameDun = new Image("/application/media/Game_Over_Z.jpg");
		ImageView dunScale = new ImageView(gameDun);
		dunScale.setX(310);
		dunScale.setY(280);
		dunScale.setVisible(false);
		
		Image bimg = new Image("/application/media/background.jpg");
		ImageView bg = new ImageView(bimg);
		bg.setScaleX(2.4);
		bg.setScaleY(2.6);
		
		
		Text gameOver = new Text();
		gameOver.setX(180);
		gameOver.setY(450);
		gameOver.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
		gameOver.setFill(Color.WHITE);
		
		movement moveStuff = new movement(2);
		
		moveStuff.setMe(rect);
		
		EventHandler<KeyEvent> wasdPressedHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				
				//System.out.println(e.getCode());
				if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.D || e.getCode() == KeyCode.SPACE ||
						e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
					//System.out.println("[" + e.getCode() + "] key pressed!");
					moveStuff.going(e.getCode());
				}
			}
		};
		
		
		EventHandler<KeyEvent> wasdReleasedHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				
				//System.out.println(e.getCode());
				if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.D || e.getCode() == KeyCode.SPACE ||
						e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
					//System.out.println("[" + e.getCode() + "] key released!");
					moveStuff.stopping(e.getCode());
				}
			}
		};
		
		

		Thread t1 = new Thread(moveStuff);
		t1.start();
		
		levels lvlobj = new levels();
		lvlobj.LevelOne();
		
		
		moveStuff.setMe(rect);
		moveStuff.setYou(player2);
		moveStuff.setBall(ball);
		moveStuff.setStats(score, gameOver, dunScale);
	
		
		Group root = new Group();
		
		root.getChildren().add(bg);
		root.getChildren().add(rect);
		root.getChildren().add(player2);
		root.getChildren().add(ball);
		root.getChildren().add(score);
		root.getChildren().add(gameOver);
		root.getChildren().add(dunScale);
		
		
		for (int i = 0; i < lvlobj.getLevel().size(); i++) {
			root.getChildren().add((Node) lvlobj.getLevel().get(i));
		}
		
		
		moveStuff.setGroup(root);
		
		//root.getChildren().remove(12);
		
		try {
			Scene scene = new Scene(root,1000,600);
			
			scene.addEventFilter(KeyEvent.KEY_PRESSED, wasdPressedHandler);
			scene.addEventFilter(KeyEvent.KEY_RELEASED, wasdReleasedHandler);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread t0 = Thread.currentThread();
		movement.setMainThread(t0);
		launch(args);
	}
}
