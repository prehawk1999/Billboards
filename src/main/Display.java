package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public final class Display implements Table {

	/**
	 * 
	 * @param g
	 * @param l
	 *            player1's score
	 * @param r
	 *            player2's score
	 * @param IO
	 */
	public static final void paintScore(Graphics2D g, int l, int r,
			ImageObserver IO) {

		g.setFont(new Font("SanSerif", Font.BOLD, 15));
		g.setColor(Color.yellow);
		FontMetrics fm = g.getFontMetrics();

		int stringWidth = fm.stringWidth(String.format("%d", l)) / 2;
		int stringAscent = fm.getAscent() / 2;

		g.drawString(String.format("PLAYER1: %d", l),
				(MESSAGE_PLAYER1_X - stringWidth), (MESSAGE_Y + stringAscent));

		g.drawString(String.format("PLAYER2: %d", r),
				(MESSAGE_PLAYER2_X - stringWidth), (MESSAGE_Y + stringAscent));

		g.setColor(Color.black);
	}

	/**
	 * 
	 * @param g
	 * @param l
	 *            the state that img left should display
	 * @param left
	 *            is an img of left pointer
	 * @param r
	 *            the state that img right should display
	 * @param right
	 *            is an img of right pointer
	 * @param IO
	 */
	public static final void paintPointers(Graphics2D g, boolean l, Image left,
			boolean r, Image right, ImageObserver IO) {

		// paint pointers
		if (l)
			g.drawImage(left, FLAG_LEFT, 40, IO);
		if (r)
			g.drawImage(right, FLAG_RIGHT, 37, IO);
	}

	/**
	 * 
	 * @param g
	 * @param img
	 *            background image
	 * @param IO
	 *            usually this
	 */
	public static final void paintBackground(Graphics2D g, Image img,
			ImageObserver IO) {

		g.drawImage(img, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, IO);
	}

	/**
	 * @param bx
	 *            棒子的顶端X坐标
	 * @param by
	 *            棒子的顶端y坐标
	 * @param mx
	 *            鼠标x坐标
	 * @param my
	 *            鼠标y坐标
	 */
	public static final void paintStick(Graphics2D g, BufferedImage img,
			int bx, int by, int mx, int my, boolean round, ImageObserver IO) {

		Graphics2D g2d = (Graphics2D) g.create();
		/* Display.ignore(img, Color.white); */
		double radians = Math.atan2(my - by, mx - bx);

		double bxt = bx - Player.getForceLevel() * Math.cos(radians) * 0.15;
		double byt = by - Player.getForceLevel() * Math.sin(radians) * 0.15;
		g2d.translate(bxt, byt);
		g2d.rotate(radians - Math.PI);
		if (round)
			g2d.drawImage(img, 0, 0, IO);
		/*
		 * double rotation = Math.atan2(my - by, mx - by); int ratio =
		 * (int)Math.toDegrees(rotation); int fx = bx - (int)(img.getWidth(IO) *
		 * Math.cos(rotation)); int fy = by - (int)(img.getWidth(IO) *
		 * Math.sin(rotation)); int sx = fx - (int)(img.getWidth(IO)); int sy =
		 * fy + (int)(img.getHeight(IO) / 2); Display.revolve(g, img, sx, sy,
		 * fx, fy, ratio, IO);
		 */
	}

	/**
	 * 绘制旋转的图片<br />
	 * x,y 绘制起始坐标<br />
	 * rx,ry 旋转中心<br />
	 * ratio 旋转角度(0~360)
	 */
	public static final void revolve(Graphics g, Image img, int x, int y,
			int rx, int ry, int ratio, ImageObserver IO) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.translate(x + rx, y + ry);
		g2d.rotate(Math.toRadians(ratio));
		g2d.drawImage(img, -rx, -ry, IO);
	}

	/** 设置颜色透明度(0~255) **/
	public static final Color newColor(Color c, int alp) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), alp);
	}

	/** 忽略图片中的颜色，让该颜色呈现透明 **/
	public static final void ignore(BufferedImage img, Color c) {

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				img.setRGB(x, y, new Color(255, 255, 255, 255).getRGB());
				/*
				 * if (img.getRGB(x, y) == c.getRGB()) { Color cc = new
				 * Color(img.getRGB(x, y)); img.setRGB(x, y, new
				 * Color(cc.getRed(), cc.getGreen(), cc.getBlue(), 0).getRGB());
				 * }
				 */
			}
		}
	}

	// constructor unused
	private Display() {

	}
}
