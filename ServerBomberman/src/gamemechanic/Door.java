package gamemechanic;

public class Door {

	private Board board;
	private int x, y;

	public Door(Board board, int x, int y){
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