package View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Control.ButtonClickListener;
import Control.KeyController;
import Control.TimerListener;
import Model.Food;
import Model.Snake;
import Model.ObserverPatteren.SnakeObserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Random;

import javax.swing.Timer;

public class GameBoard {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	public static final int CELL_SIZE = 20;
	public static final int FPS = 4;
	public static final int DELAY = 1000 / FPS; //milliseconds
	private Timer timer;

	private Snake snake =  new Snake();


	private JFrame window;
	private MyCanvas canvas;
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");
	private JButton exiButton = new JButton("Exit");
	private JLabel scoreDisplay = new JLabel();
	private int score = 0;
	private boolean gameOver;


	public GameBoard(JFrame window){
		this.window = window;
	}

	public void init(){
		Container cp = window.getContentPane();
		canvas = new MyCanvas(this, WIDTH, HEIGHT);
		cp.add(BorderLayout.CENTER, canvas);

		JPanel northPanel = new JPanel();
		JLabel label = new JLabel("Score: ");
		northPanel.add(label);
		scoreDisplay.setText("" + score);
		northPanel.add(scoreDisplay);
		cp.add(BorderLayout.NORTH, northPanel);

		JPanel southPanel = new JPanel();
		southPanel.add(startButton);
		southPanel.add(stopButton);
		southPanel.add(exiButton);
		cp.add(BorderLayout.SOUTH, southPanel);

		Text t1 = new Text("Click <Start> to Play", 100, 100);
		t1.color = Color.yellow;
		canvas.getFigures().add(t1);

		ButtonClickListener buttonListener = new ButtonClickListener(this);
		startButton.addActionListener(buttonListener);
		stopButton.addActionListener(buttonListener);
		exiButton.addActionListener(buttonListener);

		KeyController keycontroller = new KeyController(this);
		canvas.addKeyListener(keycontroller);
		canvas.requestFocusInWindow();
		canvas.setFocusable(true);

		//disable focusable in all other components
		startButton.setFocusable(false);
		stopButton.setFocusable(false);
		exiButton.setFocusable(false);
		label.setFocusable(false);
		scoreDisplay.setFocusable(false);

		SnakeObserver observer = new SnakeObserver(this);
		snake.addSnakeListener(observer);



		timer = new Timer(DELAY, new TimerListener(this));
		timer.start();

	}

	public void createFood(){
		Random random = new Random();
		int xloc, yloc;
		do{
			xloc = random.nextInt(GameBoard.WIDTH / CELL_SIZE) * CELL_SIZE;
			yloc = random.nextInt(GameBoard.HEIGHT / CELL_SIZE) * CELL_SIZE;
		} while (xloc == snake.x && yloc == snake.y);

		Food food = new Food(xloc, yloc, Color.pink);
		canvas.getFigures().add(food);
	}

	public MyCanvas getCanvas() {
		return canvas;
	}

	public Snake getSnake() {
		return snake;
	}

	public JButton getStartButton() {
		return startButton;
	}

	public JButton getStopButton() {
		return stopButton;
	}
	public JButton getExiButton() {
		return exiButton;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public JLabel getScoreDisplay() {
		return scoreDisplay;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public Timer getTimer() {
		return timer;
	}
	
}
