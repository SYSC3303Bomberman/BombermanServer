package gamemechanic;

import java.net.SocketAddress;
import java.util.ArrayList;


public class Player extends Thread{

	SocketAddress clientAddress;
	String name;
	private Board board;
	private int x, y;
	private int bombsMaxNumber;
	private int bombsNumber;
	private String command;	//command is the character received from clinet

	public Player(){}
	
	public Player(Board board, int x, int y, SocketAddress clientAddress, String name){
		this.x = x;
		this.y = y;
		this.board = board;
		this.clientAddress = clientAddress;
		this.name = name;
		bombsMaxNumber = 1;
		bombsNumber = 1;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public String getPlayerName(){
		return name;
	}
	
	public int getBombsNumber() {
		return bombsNumber;		
	}

	public SocketAddress getPlayerAddress(){
		return this.clientAddress;
	}
	
	/* method to convert character command to movement*/	
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
		}else if (command.equals("p")) {
			this.placeBomb();
		}
	}
	
	public void moveUp(){
		if(x > 0){
			if (board.hasDoorAt((x-1),y)&&board.enemies.size()==0) {
				System.out.println("CONGRATULATIONS");
				board.players.clear();
			}
			if (board.hasPowerUpAt((x-1),y)) {
				this.powerUp();
				for (int i = 0; i < board.powerups.size() ; i++ ) {
					if ((board.powerups.get(i).getX()==(x-1))&&(board.powerups.get(i).getY()==y)) {
						board.powerups.remove(i);
					}
				}
			}
			if(!board.hasObstacleAt((x-1),y)&&!board.hasBoxAt((x-1),y)&&!board.hasPlayerAt((x-1),y)&&!board.hasBombAt((x-1),y)){
				x--;
			}
		}
	}

	public void moveDown(){
		if(x < Board.DEFAULT_BOARD_WIDTH){
			if (board.hasDoorAt((x+1),y)&&board.enemies.size()==0) {
				System.out.println("CONGRATULATIONS");
				board.players.clear();
			}
			if (board.hasPowerUpAt((x+1),y)) {
				this.powerUp();
				for (int i = 0; i < board.powerups.size() ; i++ ) {
					if ((board.powerups.get(i).getX()==(x+1))&&(board.powerups.get(i).getY()==y)) {
						board.powerups.remove(i);
					}
				}
			}
			if(!board.hasObstacleAt((x+1),y)&&!board.hasBoxAt((x+1),y)&&!board.hasPlayerAt((x+1),y)&&!board.hasBombAt((x+1),y)){
				x++;
			}
		}
	}

	public void moveRight(){
		if(x < Board.DEFAULT_BOARD_LENGTH){
			if (board.hasDoorAt(x,(y+1))&&board.enemies.size()==0) {
				System.out.println("CONGRATULATIONS");
				board.players.clear();
			}
			if (board.hasPowerUpAt(x,(y+1))) {
				this.powerUp();
				for (int i = 0; i < board.powerups.size() ; i++ ) {
					if ((board.powerups.get(i).getX()==x)&&(board.powerups.get(i).getY()==(y+1))) {
						board.powerups.remove(i);
					}
				}
			}
			if(!board.hasObstacleAt(x,(y+1))&&!board.hasBoxAt(x,(y+1))&&!board.hasPlayerAt(x,(y+1))&&!board.hasBombAt(x,(y+1))){
				y++;
			}
		}
	}

	public void moveLeft(){
		if(y > 0){
			if (board.hasDoorAt(x,(y-1))&&board.enemies.size()==0) {
				System.out.println("CONGRATULATIONS");
				board.players.clear();
			}
			if (board.hasPowerUpAt(x,(y-1))) {
				this.powerUp();
				for (int i = 0; i < board.powerups.size() ; i++ ) {
					if ((board.powerups.get(i).getX()==x)&&(board.powerups.get(i).getY()==(y-1))) {
						board.powerups.remove(i);
					}
				}
			}
			if(!board.hasObstacleAt(x,(y-1))&&!board.hasBoxAt(x,(y-1))&&!board.hasPlayerAt(x,(y-1))&&!board.hasBombAt(x,(y-1))){
				y--;
			}
		}
	}

	public void powerUp(){
		bombsMaxNumber++;
		this.loadBomb();
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

}

