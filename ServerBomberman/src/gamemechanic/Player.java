package gamemechanic;

import java.net.SocketAddress;
import java.util.ArrayList;


public class Player {

	SocketAddress clientAddress;
	private Board board;
	private int x, y;
	private int bombMaxNumber;
	private int bombsNumber;
	private String command;	//command is the character received from clinet

	public Player(Board board, int x, int y, SocketAddress clientAddress){
		this.x = x;
		this.y = y;
		this.board = board;
		this.clientAddress = clientAddress;
	}

	/* method to convert character command to movement*/
	/*public void move(){
		if ( command == 'u') {
			this.moveUp();
		}else if ( command == 'd') {
			this.moveDown();
		}else if ( command == 'r') {
			this.moveRight();
		}else if ( command == 'l') {
			this.moveLeft();
		}
	}*/

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getBombsNumber() {
		return bombsNumber;		
	}

	//public void moveUp(){
	public void moveLeft(){
		if(x > 0){
			if(!board.hasPlayerAt((x-1),y)&&
					!board.hasObstacleAt((x-1),y)&&
					!board.hasBoxAt((x-1),y)&&
					!board.hasEnemyAt((x-1),y)&&
					!board.hasPlayerAt((x-1),y)){
				x--;
			}
		}
	}

	//public void moveDown(){
	public void moveRight(){
		if(x < Board.DEFAULT_BOARD_WIDTH){
			if(!board.hasPlayerAt((x+1),y)&&
					!board.hasObstacleAt((x+1),y)&&
					!board.hasBoxAt((x+1),y)&&
					!board.hasEnemyAt((x+1),y)&&
					!board.hasPlayerAt((x+1),y)){
				x++;
			}
		}
	}

	//public void moveRight(){
	public void moveUp(){
		if(y < Board.DEFAULT_BOARD_LENGTH){
			if(!board.hasPlayerAt(x,(y+1))&&
					!board.hasObstacleAt(x,(y+1))&&
					!board.hasBoxAt(x,(y+1))&&
					!board.hasEnemyAt(x,(y+1))&&
					!board.hasPlayerAt(x,(y+1))){
				y++;
			}
		}
	}

	//public void moveLeft(){
	public void moveDown(){
		if(y > 0){
			if(!board.hasPlayerAt(x,(y-1))&&
					!board.hasObstacleAt(x,(y-1))&&
					!board.hasBoxAt(x,(y-1))&&
					!board.hasEnemyAt(x,(y-1))&&
					!board.hasPlayerAt(x,(y-1))){
				y--;
			}
		}
	}

	public void loadBomb(){
		bombsNumber++;
	}

	public void placeBomb(){
		if (bombsNumber > 0) {
			bombsNumber--;
			Bomb bomb = new Bomb(board, this, x, y);
			board.addBomb(bomb);
		}
	}
	/***********************************
	 * Vlad addition
	 **********************************/
	public Player(){
		
	}
	
	public SocketAddress getPlayerAddress(){
		return this.clientAddress;
	}
	
	public void move(String movement){
		command = movement;
		if (command.equals("u")) {
			this.moveUp();
		}else if (command.equals("d")) {
			this.moveDown();
		}else if (command.equals("r")) {
			this.moveRight();
		}else if (command.equals("l")) {
			this.moveLeft();
		}
	}

}
