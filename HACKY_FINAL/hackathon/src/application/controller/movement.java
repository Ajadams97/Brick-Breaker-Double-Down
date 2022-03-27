package application.controller;

import application.Main;
import application.model.bricks;
import application.model.levels;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class movement extends Thread{

		private int threadSpeed;
		public static Thread t0;
		byte a, d, space, left, right;
		
		int ballUP, ballDOWN, ballLEFT, ballRIGHT;
		
		int remove;

		public String done;
		
		public Text stats;
		public Text gameOver;
		
		boolean ballGo, spaceOff, loaded, lost, reset;
		
		ImageView gmovr;
		
		public int pull;
		
		public double paddleX;
		public double player2X;
		
		public double ballX;
		public double ballY;
		
		public Rectangle noMe;
		public Rectangle noYou;
		public Circle ball;
		public Group rewt;
		

		//constructor
		public movement(int threadSpeed) {
			this.threadSpeed = threadSpeed;
			this.a = 0;
			this.d = 0;
			this.left = 0;
			this.right = 0;
			this.pull = 0;
			this.space = 0;
			this.ballGo = false;
			this.spaceOff = true;
			this.loaded = false;
			this.lost = false;
			this.ballUP = 0;
			this.ballDOWN = 0;
			this.ballLEFT = 0;
			this.ballRIGHT = 0;
			this.remove = 0;
			this.done = "";
		}
		
		
		//setter
		public static void setMainThread(Thread main) {
			t0 = main;
		}
		
		public void setMe(Rectangle rect) {
			this.noMe = rect;
			this.paddleX = noMe.getX();
		}
		
		public void setYou(Rectangle rect) {
			this.noYou = rect;
			this.player2X = noYou.getX();
		}
		
		public void setBall(Circle ball) {
			this.ball = ball;
			this.ballX = ball.getCenterX();
			this.ballY = ball.getCenterY();
		}
		
		public void setGroup(Group g) {
			this.rewt = g;
		}
		
		public void setStats(Text t, Text otherT, ImageView d) {
			this.stats = t;
			this.gameOver = otherT;
			this.gmovr = d;
		}
		
		public void setImg(ImageView i) {
			this.gmovr = i;
		}
		
		
		
		
		
		public void going(KeyCode d) {
			switch (d) {
			case A:
				this.a = 1;
				break;
			case D:
				this.d = 1;
				break;
			case LEFT:
				this.left = 1;
				break;
			case RIGHT:
				this.right = 1;
				break;
			case SPACE:
				this.space = 1;
				break;
			}
		}
		
		
		
		
		
		
		
		
		public void stopping(KeyCode d) {
			switch (d) {
			case A: 
				this.a = 0;
				break;
			case D:
				this.d = 0;
				break;
			case LEFT:
				this.left = 0;
				break;
			case RIGHT:
				this.right = 0;
				break;
			case SPACE:
				this.space = 0;
				break;
			}
		}
		
		public boolean checkBrick(Node raw) {
			
			Rectangle brick = (Rectangle) raw;
			
			if (this.ballX > brick.getX() && this.ballX < brick.getX() + brick.getWidth()) {
			//Approach from bottom
			if (this.ballUP == 1) {
				if(this.ballY < brick.getY() + brick.getHeight() && this.ballY > brick.getY()) {
					this.ballUP = 0;
					this.ballDOWN = 1;
					
					return false;
				}
			}
			
			
			//Approach from top
			if (this.ballDOWN == 1) {
				if(this.ballY > brick.getY() && this.ballY < brick.getY() + brick.getHeight()) {
					this.ballUP = 1;
					this.ballDOWN = 0;
					
					return false;
				}
			}
			
			}
			
			if (this.ballY > brick.getY() && this.ballY < brick.getY() - brick.getHeight()) {
			//Approach from left
				if (this.ballLEFT == 1) {
					if(this.ballX > brick.getX() && this.ballX < brick.getX() + brick.getWidth()) {
						this.ballLEFT = 0;
						this.ballRIGHT = 1;
						
						return false;
					}
				}				
			
			//Approach from right
				if (this.ballRIGHT == 1) {
					if(this.ballX < brick.getX() + brick.getWidth() && this.ballX > brick.getX()) {
						this.ballLEFT = 1;
						this.ballRIGHT = 0;
						
						return false;
					}
				}
			}
			
			return true;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void run() {
			
			//poppy.play();
			
			int count = 0;
			
			while(t0.isAlive()) {
				
				if(!loaded) {
					 count +=1;
				}

				pull = 0;
				
				if ( paddleX < 970 ) {
					paddleX += 2 * d;
				}
				
				if ( paddleX > 0 ) {
					paddleX -= 2 * a;
				}
				
				if ( player2X < 970 ) {
					player2X += 2 * right;
				}
				
				if ( player2X > 0 ) {
					player2X -= 2 * left;
				}
				
				if (space == 1)
					ballGo = true;
				
				if (space == 1 && lost)
				{
					reset = true;
				}
				
				if(ballGo && spaceOff) {
					ballUP = 1;
					spaceOff = false;
				}

				//hits bottom
				if(ballY > 600 && ballGo && !lost) {
					ballUP = 1;
					ballDOWN = 0;
					done = "press space to restart";
					lost = true;
				}
				
				//hits top
				if(ballY < 10 && ballGo && !lost) {
					ballUP = 0;
					ballDOWN = 1;
				}
				
				//hits left
				if(ballX > 990 && ballGo && !lost) {
					ballLEFT = 1;
					ballRIGHT = 0;
				}
				
				//hits right
				if(ballX < 10 && ballGo && !lost) {
					ballLEFT = 0;
					ballRIGHT = 1;
				}
				
				if(ballY > 550 && ballX > paddleX && ballX < paddleX + 30 && ballGo) {
					if(a == 1) {
						ballLEFT = 1;
						ballRIGHT = 0;
					}
					
					if(d == 1) {
						ballLEFT = 0;
						ballRIGHT = 1;
					}
					
					ballUP = 1;
					ballDOWN = 0;
				}
				
				if(ballY > 560 && ballX > player2X && ballX < player2X + 30 && ballGo) {
					if(left == 1) {
						ballLEFT = 1;
						ballRIGHT = 0;
					}
					
					if(right == 1) {
						ballLEFT = 0;
						ballRIGHT = 1;
					}
					
					ballUP = 1;
					ballDOWN = 0;
				}
				
				if (count > 1000)
					loaded = true;
				
				if (lost) {
					ballRIGHT = 0;
					ballLEFT = 0;
					ballUP = 0;
					ballDOWN = 0;
				}
				
				ballX += ballRIGHT;
				ballX -= ballLEFT;
				ballY += ballDOWN;
				ballY -= ballUP;
				
				if (loaded) {
					for(int i = 7; i < rewt.getChildren().size(); i++)
					{
						if(!(checkBrick(rewt.getChildren().get(i))))
						{

							remove = i;
						}
					}
					count = 0;
				}



				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						if(remove > 0) {
							rewt.getChildren().remove(remove);
							remove = 0;
						}
						
						stats.setText("remaining bricks: " + (rewt.getChildren().size() - 7));
						gameOver.setText(done);
						//poppy.play();
						//soundBreak();
						
						if(lost) {
							gmovr.setVisible(true);
						}
						
						if(reset == true) {
								rewt.getChildren().remove(5, rewt.getChildren().size() -1);
								
								levels lvlobj = new levels();
								lvlobj.LevelOne();
								for (int i = 0; i < lvlobj.getLevel().size(); i++) {
									rewt.getChildren().add((Node) lvlobj.getLevel().get(i));
								}
								
								ballX = 500;
								ballY = 540;
								paddleX = 485;
								player2X = 485;
								reset = false;
								ballGo = false;
								spaceOff = true;
								lost = false;
								done = "";
								gmovr.setVisible(false);
							
						}
						
						ball.setCenterX(ballX);
						ball.setCenterY(ballY);
						noMe.setX(paddleX);
						noYou.setX(player2X);
						//push new location variables here
					}
 				});
				
				//Slow the application down
				try {
					Thread.sleep(threadSpeed);
				} catch (InterruptedException e) {
					System.out.println("uh oh");
				}
			}
		}
		
}
