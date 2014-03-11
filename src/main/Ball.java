package main;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.awt.*;
import ape.CircleParticle;

public class Ball extends CircleParticle implements Table {

	private boolean State;// false represent it's in the play zone and true
							// means it's in the hole
	private int id;
	private ImageObserver IO;
	private static Color cl[] = {
			Color.white,
			Color.blue // 1
			,
			Color.cyan // 2
			, Color.darkGray, Color.green, Color.lightGray,
			Color.magenta,
			Color.orange // 7
			,
			Color.black // 8
			, Color.pink, Color.red, Color.yellow,
			Color.getHSBColor(180, 20, 120) // 12
			, Color.getHSBColor(180, 120, 20) // 13
			, Color.getHSBColor(80, 100, 130) // 14
			, Color.getHSBColor(120, 255, 100) // 15
	};

	public ImageObserver getIO() {
		return IO;
	}

	public void setIO(ImageObserver iO) {
		IO = iO;
	}

	public boolean getState() {

		if (!TABLE_WIDTH.IsClamped(this.getpx())
				|| !TABLE_HEIGHT.IsClamped(this.getpy()))
			State = true;
		else
			State = false;

		return State;
	}

	public Ball(double x, double y, int id) {
		super(x, y, RADIUS, false, 1, 0.5, 0);
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	@Override
	public void paint() {

		if (dc == null)
			dc = getDefaultContainer();

		if (!getVisible())
			return;

		Ellipse2D.Double circle = new Ellipse2D.Double(curr.x - getRadius(),
				curr.y - getRadius(), (double) getRadius() * 2,
				(double) getRadius() * 2);
		dc.setColor(cl[id]);
		dc.fill(circle);
		dc.setColor(Color.black);
		dc.setFont(new Font("TimesRoman", Font.BOLD, 7));
		if (id != 0)
			dc.drawString(String.format("%d", id), ((int) curr.x - 2),
					((int) curr.y) + 2);
		/*
		 * boolean b = dc.drawImage(IMG_LEFT, 0, 0, IO); System.out.println(b);
		 */
	}

}
