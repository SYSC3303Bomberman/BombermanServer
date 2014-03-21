package gamemechanic;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Random;


public class Board extends Thread {

	public static final int DEFAULT_BOARD_LENGTH = 21;
	public static final int DEFAULT_BOARD_WIDTH = 15;
	public static final int DEFAULT_POWERUP_NUMBER = 3;
	public static final int DEFAULT_BOX_NUMBER = 15;
	public static final int DEFAULT_ENEMY_NUMBER = 5;
	public Door door;
	public ArrayList<Obstacle> obstacles;
	public ArrayList<PowerUp> powerups;
	public ArrayList<Box> boxes;
	public ArrayList<Enemy> enemies;
	public ArrayList<Player> players;
	public ArrayList<Bomb> bombs;
	private int tempX, tempY;
	public char[][] table;

	public Board() throws InterruptedException {
		obstacles = new ArrayList<Obstacle>();
		powerups = new ArrayList<PowerUp>();
		boxes = new ArrayList<Box>();
		enemies = new ArrayList<Enemy>();
		players = new ArrayList<Player>();
		bombs = new ArrayList<Bomb>();
		/* ADD OBSTACLES ON TOP AND BOTTOM EDGES */
		for (int j = 0; j < DEFAULT_BOARD_LENGTH ; j++) {
			this.addObstacle(0, j);
			this.addObstacle((DEFAULT_BOARD_WIDTH-1), j);
		}
		/* ADD OBSTACLES ON RIGHT AND LEFT EDGES */
		for (int i = 0; i < DEFAULT_BOARD_WIDTH ; i++) {
			this.addObstacle(i, 0);
			this.addObstacle(i, (DEFAULT_BOARD_LENGTH-1));
		}
		/* ADD ALL OTHER OBSTACLES */
		for (int i = 2; i < DEFAULT_BOARD_WIDTH ; i+=2) {
			for (int j = 2; j < DEFAULT_BOARD_LENGTH; j+=2) {
				this.addObstacle(i, j);
			}
		}
		/* OBSTACLES INITIALIZATION DONE */

		Random ran = new Random();
		do{
			tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
			tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
		}while(this.hasObstacleAt(tempX, tempY));
		door = new Door(this, tempX, tempY);	//door places at random place
		/* DOOR INITIALIZATION DONE */
		this.addBox(tempX, tempY);
		/* DOOR IS COVERED BY ONE BOX */

		for(int i = 1; i < DEFAULT_POWERUP_NUMBER; i++){
			do{
				tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
				tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
			}while(this.hasObstacleAt(tempX, tempY)||this.hasDoorAt(tempX, tempY));
			this.addPowerUp(tempX, tempY);	//enemies start at random places
			this.addBox(tempX, tempY);
		}
		/* POWERUP INITIALIZATION DONE */

		for(int i = (1 + DEFAULT_POWERUP_NUMBER); i < DEFAULT_BOX_NUMBER; i++){
			do{
				tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
				tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
			}while(this.hasObstacleAt(tempX, tempY)||this.hasBoxAt(tempX, tempY));
			this.addBox(tempX, tempY);	//enemies start at random places
		}
		/* BOX INITIALIZATION DONE */

		for(int i = 0; i < DEFAULT_ENEMY_NUMBER; i++){
			do{
				tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
				tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
			}while(this.hasObstacleAt(tempX, tempY)||this.hasBoxAt(tempX, tempY)||this.hasEnemyAt(tempX, tempY));
			this.addEnemy(tempX, tempY);	//boxes place at ranom places
		}
		/* ENEMY INITIALIZATION DONE */

		/* Obsatacles,door, powerups, boxes, enemies can be displayed on GUI*/
	}

	public void addObstacle(int x, int y){
		Obstacle obstacle = new Obstacle(this, x, y);
		obstacles.add(obstacle);
	}	

	public void addPowerUp(int x, int y){
		PowerUp powerup = new PowerUp(this, x, y);
		powerups.add(powerup);
	}

	public void addBox(int x, int y){
		Box box = new Box(this, x, y);
		boxes.add(box);
	}

	public void addEnemy(int x, int y) throws InterruptedException{
		Enemy enemy = new Enemy(this, x, y);
		enemies.add(enemy);
	}

	public void addPlayer(SocketAddress clientAddress, String name){
		Random ran = new Random();
		do{
			tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
			tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
		}while(this.hasObstacleAt(tempX, tempY)||this.hasBoxAt(tempX, tempY)||this.hasEnemyAt(tempX, tempY)||this.hasPlayerAt(tempX, tempY));
		Player player = new Player(this, tempX, tempY, clientAddress, name);	//player starts at random place
		players.add(player);
	}

	public void addBomb(Bomb bomb){
		bombs.add(bomb);
	}

	public boolean hasDoorAt(int x, int y){
		if((door.getX() == x)&&(door.getY() == y)){
			return true;
		}else{
			return false;
		}
	}

	public boolean hasObstacleAt(int x, int y){
		for(int i = 0; i < obstacles.size(); i++){
			if((obstacles.get(i).getX() == x)&&(obstacles.get(i).getY() == y)){return true;}
		}
		return false;
	}

	public boolean hasBoxAt(int x, int y){
		for(int i = 0; i < boxes.size(); i++){
			if((boxes.get(i).getX() == x)&&(boxes.get(i).getY() == y)){return true;}
		}
		return false;
	}
	
	public boolean hasEnemyAt(int x, int y){
		for(int i = 0; i < enemies.size(); i++){
			if((enemies.get(i).getX() == x)&&(enemies.get(i).getY() == y)){return true;}
		}
		return false;
	}
	
	public boolean hasPlayerAt(int x, int y){
		for(int i = 0; i < players.size(); i++){
			if((players.get(i).getX() == x)&&(players.get(i).getY() == y)){return true;}
		}
		return false;
	}
	
	public boolean hasBombAt(int x, int y){
		for(int i = 0; i < bombs.size(); i++){
			if((bombs.get(i).getX() == x)&&(bombs.get(i).getY() == y)){return true;}
		}
		return false;
	}

	public boolean hasPowerUpAt(int x, int y){
		for(int i = 0; i < powerups.size(); i++){
			if((powerups.get(i).getX() == x)&&(powerups.get(i).getY() == y)){return true;}
		}
		return false;
	}
	
	public int distinguish(int x, int y){
		for(int i = 0; i < players.size(); i++){
			if((players.get(i).getX() == x)&&(players.get(i).getY() == y)){
				return i;
			}
		}
		return -1;
	}
	
	public String toString(){
		String str = "";
		int p;
		for(int i = 0; i < DEFAULT_BOARD_WIDTH; i++){
			for(int j = 0; j < DEFAULT_BOARD_LENGTH; j++){
				if(this.hasObstacleAt(i,j)){
					str += 'O';
				}else if(this.hasBoxAt(i,j)){
					str += 'B';
				}else if(this.hasEnemyAt(i,j)){
					str += 'E';
				}else if(this.hasPlayerAt(i,j)){
					p = distinguish(i,j);
					if (p==0) {
						str += 'P';
					}else if (p==1){
						str += 'p';
					}else{

					}
				}else if(this.hasBombAt(i,j)){
					str += 'X';
				}else if(this.hasPowerUpAt(i,j)) {
					str += 'U';
				}else if(this.hasDoorAt(i,j)){
					str += 'D';
				}else{
					str += ' ';
				}
			}
			str += '\n';
		}
		return str;
	}
	
	public void form(){
		int p;
		for(int i = 0; i < DEFAULT_BOARD_WIDTH; i++){
			for(int j = 0; j < DEFAULT_BOARD_LENGTH; j++){
				if(this.hasObstacleAt(i,j)){
					table[i][j] = 'O';
				}else if(this.hasBoxAt(i,j)){
					table[i][j] = 'B';
				}else if(this.hasEnemyAt(i,j)){
					table[i][j] = 'E';
				}else if(this.hasPlayerAt(i,j)){
					p = distinguish(i,j);
					if (p==0) {
						table[i][j] = 'P';
					}else if (p==1){
						table[i][j] = 'p';
					}else{

					}
				}else if(this.hasBombAt(i,j)){
					table[i][j] = 'X';
				}else if(this.hasPowerUpAt(i,j)) {
					table[i][j] = 'U';
				}else if(this.hasDoorAt(i,j)){
					table[i][j] = 'D';
				}else{
					table[i][j] = ' ';
				}
			}
		}
	}

	public void printBoard(){
		System.out.println(this.toString());
	}
	
	/***********************************
	 * Vlad addition
	 **********************************/
	public Player hasPlayer(SocketAddress clientAddress){
		Player player = new Player();
		for(int i=0; i<players.size();i++){
			player = players.get(i);
			if(player.getPlayerAddress().equals(clientAddress)){
				break;
			}
		}
		return player;
	}

}