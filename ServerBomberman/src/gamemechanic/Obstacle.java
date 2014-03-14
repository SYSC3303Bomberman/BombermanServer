package gamemechanic;

public class Obstacle {

	private Board board;
	private int x, y;
	
	public Obstacle(Board board, int x, int y){
		this.x = x;
		this.y = y;
		this.board = board;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
}
