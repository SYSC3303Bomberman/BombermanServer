package gamemechanic;

public class Bomb {

	private Board board;
	private int x, y;
	private Player player;

	public Bomb(Board board, Player player, int x, int y){
		this.x = x;
		this.y = y;
		this.board = board;
		this.player = player;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Player getPlayer(){
		return player;
	}

	public void explode(){
		this.burn((x + 1), y);
		this.burn((x - 1), y);
		this.burn(x, (y + 1));
		this.burn(x, (y - 1));
	}

	public void burn(int x, int y){
		if (board.hasPlayerAt(x, y)) {
			//player died
			for (int i = 0; i < board.players.size() ; i++ ) {
				board.players.remove(i);
			}
		}else if (board.hasBoxAt(x, y)) {
			//box destroyed
			for (int i = 0; i < board.boxes.size() ; i++ ) {
				board.boxes.remove(i);
			}
		}else if (board.hasEnemyAt(x, y)) {
			//enemy killed
			for (int i = 0; i < board.enemies.size() ; i++ ) {
				board.enemies.remove(i);
			}
		}
	}
}



