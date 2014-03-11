package main;

import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import ape.*;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import main.Ball;
import main.Display;

@SuppressWarnings("serial")
public class Main extends Canvas implements Table {

	private APEngine APEngine = new APEngine();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<Ball> balls = new ArrayList();
	public int num_ballsInHole = 0;

	private BufferStrategy strategy;
	private BufferedImage IMG_BACKGROUND;
	private BufferedImage IMG_RIGHT;
	private BufferedImage IMG_LEFT;
	private BufferedImage IMG_STICK;

	private Player player1;
	private Player player2;
	private Player nowPlaying;
	private Timer enlargeForce;
	private Timer shrinkForce;
	private static int tempForce = 0;
	private static boolean hitState = false;
	private boolean roundState = false;
	private int mouse_x;
	private int mouse_y;

	public static void main(String[] args) {
		Main inv = new Main();
		inv.game();
	}

	public Main() {

		JFrame ventana = new JFrame("billiards");
		player1 = new Player();
		player2 = new Player();
		nowPlaying = player1;
		enlargeForce = new Timer(10, new EnlargeForce());
		shrinkForce = new Timer(10, new ShrinkForce());
		player1.setName("Player1");
		player2.setName("Player2");

		try {
			IMG_BACKGROUND = ImageIO.read(new File("Pictures/Background.jpg"));
			IMG_RIGHT = ImageIO.read(new File("Pictures/Right.jpg"));
			IMG_LEFT = ImageIO.read(new File("Pictures/Left.jpg"));
			IMG_STICK = ImageIO.read(new File("Pictures/Stick.jpg"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		/**
		 * Mouse and keyboard listener
		 */
		this.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseMoved(MouseEvent e) {
				setMouse_x(e.getX());
				setMouse_y(e.getY());
			}
		});

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {

				enlargeForce.start();
			}

			public void mouseReleased(MouseEvent e) {
				enlargeForce.stop();
				Main.setHitState(false);
				shrinkForce.start();
			}

		});

		ventana.add(this);
		/* ventana.add(new Display()); */
		ventana.setTitle("Billiards");
		ventana.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);

		createBufferStrategy(3);
		strategy = getBufferStrategy();
		requestFocus();
	}

	/**
	 * mouse coordinate setters
	 * 
	 * @return
	 */
	public double getMouse_x() {
		return mouse_x;
	}

	public void setMouse_x(int mouse_x) {
		this.mouse_x = mouse_x;
	}

	public double getMouse_y() {
		return mouse_y;
	}

	public void setMouse_y(int mouse_y) {
		this.mouse_y = mouse_y;
	}

	public static int getTempForce() {
		return Main.tempForce;
	}

	// 不能从一个非零值设置为另一个非零值
	public static void setTempForce(int tempForce) {
		if (tempForce == 0 || Main.tempForce == 0)
			Main.tempForce = tempForce;
	}

	public static boolean isHitState() {
		return hitState;
	}

	public static void setHitState(boolean hitState) {
		Main.hitState = hitState;
	}

/*	public void playMusic() {// experimental

		try {
			File file = new File(
					"C:\\users\\Prehawk\\Documents\\JavaProjects\\Billiards\\sounds\\123.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

	@SuppressWarnings("static-access")
	public void ape_init(Graphics2D g) {

		APEngine.init((double) 1 / 10);
		APEngine.setAirforce(0.3);
		APEngine.setCollisionResponseMode(APEngine.SELECTIVE);
		APEngine.setDefaultContainer(g);

	}

	public void paint(Graphics2D g) {

		Display.paintBackground(g, IMG_BACKGROUND, this);
		Display.paintPointers(g, player1.getRound(), IMG_LEFT,
				player2.getRound(), IMG_RIGHT, this);
		Display.paintScore(g, player1.getScore(), player2.getScore(), this);

		// Display balls
		for (Ball i : balls)
			i.paint();

		// paint stick
		Display.paintStick(g, IMG_STICK, (int) balls.get(0).curr.x,
				(int) (balls.get(0).curr.y), mouse_x, mouse_y,
				nowPlaying.getRound(), this);

		strategy.show();

	}

	@SuppressWarnings("static-access")
	public void ape_SetupTable() {

		// Table Blocks
		APEngine.addParticle(new RectangleParticle(LEFT_EDGE, MIDLINE_ROW, 0,
				BLOCK_LENGTH, 0, true, 1, 0.3, 0));

		APEngine.addParticle(new RectangleParticle(
				(MIDLINE_COL + LEFT_EDGE) / 2, TOP_EDGE, BLOCK_LENGTH, 0, 0,
				true, 1, 0.3, 0));

		APEngine.addParticle(new RectangleParticle(
				(MIDLINE_COL + LEFT_EDGE) / 2, BOTTOM_EDGE, BLOCK_LENGTH, 0, 0,
				true, 1, 0.3, 0));

		APEngine.addParticle(new RectangleParticle(
				(RIGHT_EDGE + MIDLINE_COL) / 2, TOP_EDGE, BLOCK_LENGTH, 0, 0,
				true, 1, 0.3, 0));

		APEngine.addParticle(new RectangleParticle(
				(RIGHT_EDGE + MIDLINE_COL) / 2, BOTTOM_EDGE, BLOCK_LENGTH, 0,
				0, true, 1, 0.3, 0));

		APEngine.addParticle(new RectangleParticle(RIGHT_EDGE, MIDLINE_ROW, 0,
				BLOCK_LENGTH, 0, true, 1, 0.3, 0));

	}

	@SuppressWarnings("static-access")
	public void ape_InitialBalls() {

		for (int i = 0; i != 16; i++) {
			Ball c = new Ball(BALL_POINTS[i].getX(), BALL_POINTS[i].getY(), i);
			balls.add(c);
			APEngine.addParticle(c);
		}
	}

	public boolean game_Balls_Still() {

		double zeroSpeed = 0.005;
		boolean b = true;
		for (int i = 0; i != balls.size(); i++) {

			// set Out-Of-Range still
			if (balls.get(i).getState()) {
				if (i == 0) {// reset the white ball if it's in the hole
					balls.get(i).setpx((RIGHT_EDGE + MIDLINE_COL) / 2);
					balls.get(i).setpy(MIDLINE_ROW);
				} else {
					balls.get(i).setVelocity(new Vector(0, 0));
					balls.get(i).setVisible(false);
					balls.get(i).setCollidable(false);
				}
			}

			// detect the state that all balls still and return it
			double x = balls.get(i).getVelocity().x;
			double y = balls.get(i).getVelocity().y;
			if ((x >= zeroSpeed || x <= -zeroSpeed)
					&& (y >= zeroSpeed || y <= -zeroSpeed)) // equivalent to no
															// speed
				b = false;
		}
		return b;
	}

	/*
	 * public boolean game_ResetWhite(){
	 * 
	 * if( balls.get(0).getState() ){ balls.get(0).setpx((RIGHT_EDGE +
	 * MIDLINE_COL) / 2); balls.get(0).setpy(MIDLINE_ROW); return true; } return
	 * false; }
	 */

	public int game_CountInHole() {

		int b = 0;
		for (int i = 1; i != balls.size(); i++) {
			if (balls.get(i).getState()) {
				b += 1;
			}
		}
		return b;
	}

	public void game_SwitchPlayer() {

		if (nowPlaying == player1) {
			nowPlaying = player2;
		} else {
			nowPlaying = player1;
		}
	}

	@SuppressWarnings("static-access")
	public void game() {// main circle

		int n;
		boolean b;
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		ape_init(g);
		ape_SetupTable();
		ape_InitialBalls();

		/* nowPlaying = player1; */
		while (true) {
			APEngine.step();

			/**
			 * gaming logic circle (real time speculate)
			 */
			// shut down the shrinkforce
			if (Main.isHitState())
				shrinkForce.stop();

			/**
			 * round time speculate 该if clause当且仅当b值改变时运行一次
			 */
			if ((b = game_Balls_Still())) {
				if (isLock(b)) {

					// normally switch to another player because he don't hit a
					// ball in a hole
					if ((n = game_CountInHole() - num_ballsInHole) > 0) {
						nowPlaying.addScore(n);
						num_ballsInHole = game_CountInHole();
					} else
						game_SwitchPlayer();

					// win condition
					if (nowPlaying.IsWin()) {
						JOptionPane
								.showMessageDialog(
										null,
										String.format("%s wins!",
												nowPlaying.getName()));
						System.exit(0);
					} else if (game_CountInHole() == 15) {
						String s = (player1.getScore() > player2.getScore()) ? "player1 wins!"
								: "player2 wins";
						JOptionPane.showMessageDialog(null, s);
						System.exit(0);
					}

					nowPlaying.setRound(true);// 打开球局
					System.out.println(String.format(
							("Game_CounInHole(): %d and n: %d"),
							game_CountInHole(), n));
				}
			} else {
				if (isLock(b)) {
					nowPlaying.setRound(false);// 关闭球局
				}
			}

			/**
			 * slow down the program
			 */
			paint(g);// for temp
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}

	}

	// surrounded by if clause and the part will only be runned once since the
	// state of b changed a time
	public boolean isLock(boolean b) {

		boolean t = (this.roundState != b) ? true : false;
		this.roundState = b;
		return t;
	}

	class EnlargeForce implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int a = Player.getForceLevel();
			if (a <= 1000)
				Player.setForceLevel(a + 10);
		}

	}

	class ShrinkForce implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Main.setTempForce(Player.getForceLevel());
			if (Player.getForceLevel() >= 20)
				Player.setForceLevel((int) (Player.getForceLevel() - Player
						.getForceLevel() * 0.5));
			else {
				Player.setForceLevel(Main.getTempForce());
				player1.Hit(balls.get(0), new Vector(mouse_x, mouse_y));
				player2.Hit(balls.get(0), new Vector(mouse_x, mouse_y));
				Main.setTempForce(0);
				Main.setHitState(true);
				Player.setForceLevel(20);

			}
		}
	}

}