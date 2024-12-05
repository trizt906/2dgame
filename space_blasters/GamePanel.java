package space_blasters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {

    private Timer timer;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<Asteroid> asteroids; 
    private String mode;

    private int score; 
    private long startTime; 
    private int highScore;

    private static final int MAX_ASTEROIDS = 3; 
    private static final double ASTEROID_SPAWN_PROBABILITY = 0.05; 
    private static final int ASTEROID_SPAWN_THRESHOLD = 20;
    
	private static final int MIN_DISTANCE_BETWEEN_ENEMIES = 100; // Minimum distance between enemies

	public GamePanel(String mode) {
		this.mode = mode;
		player = new Player(375, 500); // Position player near bottom of screen
		enemies = new ArrayList<>();
		powerUps = new ArrayList<>();
		bullets = new ArrayList<>();
		enemyBullets = new ArrayList<>();
		asteroids = new ArrayList<>(); 

		score = 0;
		highScore = 0; 
		startTime = System.currentTimeMillis(); 

		setFocusable(true);
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				player.moveToMouse(e.getX(), e.getY());
			}
		});

		timer = new Timer(16, this);
	}

	public void startGame() {
		timer.start();
		spawnEnemies();
		
		new Timer(150, e -> player.autoShoot(bullets)).start();
		
		new Timer(500, e -> shootEnemies()).start(); // Enemies will shoot every half second
	}

	private void shootEnemies() {
		for (Enemy enemy : enemies) {
			if (Math.random() < .5) { // Adjust shooting probability to increase frequency
				enemy.shoot(enemyBullets, player); // Make the enemy shoot at random intervals
			}
		}
	}

	private boolean isSpawnLocationValid(int x) {
	    for (Enemy enemy : enemies) {
	        if (Math.abs(enemy.getX() - x) < MIN_DISTANCE_BETWEEN_ENEMIES) {
	            return false; // Too close to another enemy
	        }
	    }
	    return true;
	}

	private void spawnEnemies() { 
    	int x;
    	do {
        	x =(int)(Math.random()*750 );  
    	} while (!isSpawnLocationValid(x)); // Ensure valid spawn location

    	enemies.add(new Enemy(x ,0));  
	}

	private void spawnAsteroids() {   
    	int x =(int)(Math.random()*750 );   
    	asteroids.add(new Asteroid(x ,0));     
   }

   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);

       g.setColor(Color.BLACK);
       g.fillRect(0, 0, getWidth(), getHeight());
       drawStars(g);

       draw(g);

       g.setColor(Color.WHITE);
       g.drawString("Health: " + player.getHealth(), 10, 40);

       g.setColor(Color.WHITE);
       String scoreText = "Score: " + score;
       String highScoreText = "High Score: " + highScore;

       int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
       int highScoreWidth = g.getFontMetrics().stringWidth(highScoreText);

       g.drawString(scoreText, getWidth() - scoreWidth - 10, 25); 
       g.setColor(Color.YELLOW);
       g.drawString(highScoreText, getWidth() - highScoreWidth - 10, 45); 
   }

   private void draw(Graphics g) {
       player.draw(g);

       for (Bullet bullet : bullets) {
           bullet.draw(g);
       }

       for (Bullet bullet : enemyBullets) {
           bullet.draw(g);
       }

       for (Enemy enemy : enemies) {
           enemy.draw(g); // Draw each enemy including their health bar
       }

       for (Asteroid asteroid : asteroids) { 
           asteroid.draw(g);
       }

       for (PowerUp powerUp : powerUps) {
           powerUp.draw(g);
       }

       g.setColor(Color.WHITE);
       g.drawString("Mode: " + mode, 10, 20);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
       updateGame();
       repaint();
   }

   private void updateGame() {
       if (!player.isAlive()) {
           System.out.println("Game Over!");
           updateHighScore(); 
           timer.stop();

           JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
           topFrame.remove(this); 
           topFrame.add(new GameOverPanel((SpaceBlasters) topFrame, score)); 
           topFrame.revalidate();
           topFrame.repaint();

           return;
       }

       score = (int) ((System.currentTimeMillis() - startTime) / 1000); 

       player.update();

       for (int i=0;i<bullets.size();i++){  
           Bullet bullet=bullets.get(i);  
           bullet.update();  

           if(bullet.isOffScreen()){  
               bullets.remove(i);  
               i--;  
           }  

           for(int j=0;j<enemies.size();j++){  
               Enemy enemy=enemies.get(j);  

               if(bullet.collidesWith(enemy)){  
                   System.out.println("Enemy hit by bullet!");  
                   enemy.takeDamage();  
                   bullets.remove(i);  
                   i--;  

                   if(!enemy.isAlive()){  
                       enemies.remove(j);  
                       j--;  
                   }  
                   break;  
               }  
           }  

           for(int j=0;j<asteroids.size();j++){  
               Asteroid asteroid=asteroids.get(j);  

               if(bullet.collidesWith(asteroid)){  
                   System.out.println("Asteroid hit by bullet!");  
                   asteroid.takeDamage();  
                   bullets.remove(i);  
                   i--;  

                   if(!asteroid.isAlive()){  
                       String[] powerUpTypes={"health","speed","spread","shield"};  
                       String randomPowerUpType=powerUpTypes[(int)(Math.random()*powerUpTypes.length)];
                       powerUps.add(new PowerUp(asteroid.getX(), asteroid.getY(), randomPowerUpType));   
                       asteroids.remove(j);   
                       j--;   
                   }  
                   break;  
               }  
           }  
       }  

       for(int i=0;i<enemyBullets.size();i++){   
           Bullet bullet=enemyBullets.get(i);   
           bullet.update();   

           if(bullet.isOffScreen()){   
               enemyBullets.remove(i);   
               i--;   
           } else if(bullet.collidesWith(player)){   
               System.out.println("Player hit by bullet!");   
               player.takeDamage();   
               enemyBullets.remove(i);   
               i--;   
           }   
       }  

       for(int i=0;i<enemies.size();i++){   
           Enemy enemy=enemies.get(i);   
           enemy.update(); 

           if(enemy.collidesWith(player)){   
               System.out.println("Player hit by enemy!");   
               player.takeDamage();   
               enemies.remove(i);   
               i--;   
           }   

           if(enemy.isOffScreen()){   
               enemies.remove(i);   
               i--;   
           }   
       }  

       for(int i=0;i<asteroids.size();i++){   
           Asteroid asteroid=asteroids.get(i);   
           asteroid.update();   

           if(asteroid.isOffScreen()){   
               asteroids.remove(i);    
               i--;    
           }    
       }  

       for(int i=0;i<powerUps.size();i++){    
           PowerUp powerUp=powerUps.get(i);    
           powerUp.update();    

           if(powerUp.isCollected(player)){    
               switch(powerUp.getType()){    
                   case "health":    
                       player.restoreHealth();    
                       System.out.println("Health Restored!");    
                       break;    
                   case "speed":    
                       System.out.println("Speed Power-Up Collected!");    
                       break;    
                   case "spread":    
                       player.increaseBulletSpread(1);    
                       System.out.println("Spread Power-Up Collected!");    
                       break;    
                   case "shield":    
                       player.activateShield();    
                       System.out.println("Shield Activated!");    
                       break;    
               }    

               powerUps.remove(i);     
               i--;     
           }    

           if(powerUp.isOffScreen()){     
               powerUps.remove(i);     
               i--;     
           }     
       }     

      // Spawn asteroids only when score is a multiple of 20
      if(score >= ASTEROID_SPAWN_THRESHOLD && score % ASTEROID_SPAWN_THRESHOLD == 0 && asteroids.size() < MAX_ASTEROIDS) { 
            spawnAsteroids(); 
        }
        
        // Spawn enemies based on a random condition
        if(Math.random()<.02 && enemies.size()<5 ) spawnEnemies();     
   }

   private void drawStars(Graphics g) {   
      g.setColor(Color.WHITE);   
      for(int i=0;i<100;i++){   
          int x=(int)(Math.random()*getWidth());   
          int y=(int)(Math.random()*getHeight());   
          g.fillRect(x,y ,2 ,2 );     
      }    
   }

   private void updateHighScore(){     
      if(score > highScore){     
          highScore = score ;     
      }     
   }
}
