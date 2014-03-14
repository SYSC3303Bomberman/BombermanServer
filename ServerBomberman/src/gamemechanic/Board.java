package gamemechanic;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Random;

public class Board {

	public static final int DEFAULT_BOARD_LENGTH = 10;
	public static final int DEFAULT_BOARD_WIDTH = 50;
	public static final int DEFAULT_BOX_NUMBER = 5;
	public static final int DEFAULT_ENEMY_NUMBER = 5;
	public Door door;
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	public ArrayList<Box> boxes = new ArrayList<Box>();
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private int tempX, tempY;

	public Board(){
		for (int j = 0; j < DEFAULT_BOARD_LENGTH; j++) {
			this.addObstacle(0, j);
			this.addObstacle((DEFAULT_BOARD_WIDTH-1), j);
		}
		for (int i = 0; i < DEFAULT_BOARD_WIDTH ; i++) {
			this.addObstacle(i, 0);
			this.addObstacle(i, (DEFAULT_BOARD_LENGTH-1));
		}
		for (int i = 2; i < DEFAULT_BOARD_WIDTH ; i+=2) {
			for (int j = 2; j < DEFAULT_BOARD_LENGTH; j+=2) {
				this.addObstacle(i, j);
			}
		}
		/* OBSTACLES INITIALIZATION DONE*/

		Random ran = new Random();
		do{
			tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
			tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
		}while(this.hasObstacleAt(tempX, tempY)||this.hasBoxAt(tempX, tempY));
		door = new Door(this, tempX, tempY);	//door places at random place
		/* DOOR INITIALIZATION DONE*/
		this.addBox(tempX, tempY);
		/* DOOR IS COVERED BY ONE BOX*/
		for(int i = 1; i < DEFAULT_BOX_NUMBER; i++){
			do{
				tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
				tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
			}while(this.hasObstacleAt(tempX, tempY)||this.hasBoxAt(tempX, tempY));
			this.addBox(tempX, tempY);	//enemies start at random places
		}
		/* BOX INITIALIZATION DONE*/
		for(int i = 0; i < DEFAULT_ENEMY_NUMBER; i++){
			do{
				tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
				tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
			}while(this.hasObstacleAt(tempX, tempY)||this.hasBoxAt(tempX, tempY)||this.hasEnemyAt(tempX, tempY));
			this.addEnemy(tempX, tempY);	//boxes place at ranom places
		}
		/* ENEMY INITIALIZATION DONE*/
		/* Obsatacles,door boxes, enemies can be displayed on GUI*/
	}

	public void addObstacle(int x, int y){
		Obstacle obstacle = new Obstacle(this, x, y);
		obstacles.add(obstacle);
	}	

	public void addBox(int x, int y){
		Box box = new Box(this, x, y);
		boxes.add(box);
	}

	public void addEnemy(int x, int y){
		Enemy enemy = new Enemy(this, x, y);
		enemies.add(enemy);
	}

	public void addPlayer(SocketAddress clientAddress){
		Random ran = new Random();
		do{
			tempX = ran.nextInt(Board.DEFAULT_BOARD_WIDTH); 
			tempY = ran.nextInt(Board.DEFAULT_BOARD_LENGTH);	
		}while(this.hasObstacleAt(tempX, tempY)||this.hasBoxAt(tempX, tempY)||this.hasEnemyAt(tempX, tempY)||this.hasPlayerAt(tempX, tempY));
		
		Player player = new Player(this, tempX, tempY, clientAddress);	//player starts at random place
		players.add(player);
	}

	public void addBomb(Bomb bomb){
		bombs.add(bomb);
	}

	public void bombExplode(Bomb bomb){
		bomb.getPlayer().loadBomb();	// player loads one bomb at the time his bomb explodes
		bomb.explode();
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
		for(int i = 0; i < players.size(); i++){
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
		for(int i = 0; i < players.size(); i++){
			if((bombs.get(i).getX() == x)&&(bombs.get(i).getY() == y)){return true;}
		}
		return false;
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
	
	public void printBoard(){
		for(int j=0;j<Board.DEFAULT_BOARD_LENGTH;j++){
			for(int i=0;i<Board.DEFAULT_BOARD_WIDTH;i++){
				if(hasDoorAt(i,j)){
					System.out.print("d");
				}else if(hasObstacleAt(i,j)){
					System.out.print(".");
				}else if(hasBoxAt(i,j)){
					System.out.print("b");
				}else if(hasEnemyAt(i,j)){
					System.out.print("e");
				}else if(hasPlayerAt(i,j)){
					System.out.print("p");
				}else{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
	
}
