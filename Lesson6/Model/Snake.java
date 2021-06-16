package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Model.ObserverPatteren.Observer;
import Model.ObserverPatteren.Subject;
import Model.StrategyPattern.SnakeMoveAliveStrategy;
import Model.StrategyPattern.SnakeMoveDeadStrategy;
import Model.StrategyPattern.SnakeMoveStrategy;
import Model.StrategyPattern.SnakeRenderAliveStrategy;
import Model.StrategyPattern.SnakeRenderDeadStrategy;
import Model.StrategyPattern.SnakeRenderStrategy;
import View.GameBoard;

public class Snake extends GameElement implements Subject {

	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	public enum Event {
		AteFood, AtePoision, LeftScene, SelfCollision
	}

	public ArrayList<GameElement> composite = new ArrayList<>();
	private ArrayList<Observer> observers = new ArrayList<>();
	private final int INIT_XLOC = GameBoard.CELL_SIZE * 7;
	private final int INIT_YLOC = GameBoard.CELL_SIZE * 3;
	private final int INIT_BODY_SIZE = 3;

	public Direction direction = Direction.RIGHT;

	private SnakeMoveStrategy moveStrategy;
	private SnakeRenderStrategy renderStrategy;

	public void init() {
		direction = Direction.RIGHT;
		composite.clear();
		super.x = INIT_XLOC;
		super.y = INIT_YLOC;
		var head = new SnakeHead(super.x, super.y);
		head.color = Color.yellow;
		composite.add(head);
		for (int i = 1; i <= INIT_BODY_SIZE; i++) {
			int x = INIT_XLOC - GameBoard.CELL_SIZE * i;
			int y = INIT_YLOC;
			var body = new SnakeBody(x, y);
			body.color = Color.white;
			composite.add(body);
		}

		moveStrategy = new SnakeMoveAliveStrategy(this);
		// moveStrategy = new SnakeMoveDeadStrategy(this);
		renderStrategy = new SnakeRenderAliveStrategy(this);
		// renderStrategy = new SnakeRenderDeadStrategy(this);
	}

	@Override
	public void render(Graphics2D g2) {
		this.renderStrategy.renderAlgorithm(g2);
	}

	@Override
	public void move() {
		this.moveStrategy.moveAlgorithm();
	}

	public void setMoveStrategy(SnakeMoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}

	public void setRenderStrategy(SnakeRenderStrategy renderStrategy) {
		this.renderStrategy = renderStrategy;
	}

	public ArrayList<GameElement> getComposite() {
		return composite;
	}

	@Override
	public void addSnakeListener(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeSnakeListener(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Event event) {
		switch(event) {
			case AteFood:
				for(var o: observers)
				o.snakeAtefood();
				break;
			case AtePoision:
				break;
			case LeftScene:
				for(var o: observers)
				o.snakeLeftScene();
				break;
			case SelfCollision:
			for(var o: observers)
			o.snakeSelfCollision();
				break;
		}
	}

	public boolean selfCollision(){
		GameElement head = composite.get(0);
		for (int i = 1; i < composite.size(); i++){
			var body = composite.get(i);
			if (head.collideWith(body)) return true;
		}
		return false;
	}

}
