package main;

import java.awt.geom.Point2D;
import ape.Interval;

public interface Table {

	// raw data
	public static final int PANEL_WIDTH = 800;
	public static final int PANEL_HEIGHT = 600;

	// score and flag
	public static final int MESSAGE_Y = 30;
	public static final int MESSAGE_PLAYER1_X = 50;
	public static final int MESSAGE_PLAYER2_X = PANEL_WIDTH - MESSAGE_PLAYER1_X
			- 100;
	public static final int FLAG_LEFT = 200;
	public static final int FLAG_RIGHT = PANEL_WIDTH - FLAG_LEFT - 50;

	public static final int RADIUS = 10;
	public static final int BLOCK_LENGTH = 306;
	public static final int LEFT_EDGE = 49;
	public static final int RIGHT_EDGE = PANEL_WIDTH - LEFT_EDGE;
	public static final int TOP_EDGE = 165;
	public static final int BOTTOM_EDGE = 518;

	// midline of play zone.
	public static final int MIDLINE_ROW = (BOTTOM_EDGE + TOP_EDGE) / 2;
	public static final int MIDLINE_COL = (RIGHT_EDGE + LEFT_EDGE) / 2;

	// interval of play zone.
	public static final Interval TABLE_WIDTH = new Interval(LEFT_EDGE,
			RIGHT_EDGE);
	public static final Interval TABLE_HEIGHT = new Interval(TOP_EDGE,
			BOTTOM_EDGE);

	// define X and Y as the point of the No.1 ball.
	static final double X = (MIDLINE_COL + LEFT_EDGE) / 2;
	static final double Y = MIDLINE_ROW;

	// define distances of the balls.
	static final double TRIANGLE_WIDTH = RADIUS * 10;
	static final double TRIANGLE_HEIGHT = TRIANGLE_WIDTH / 2 * Math.sqrt(3);

	// an array of points of 16 balls
	public static final Point2D.Double BALL_POINTS[] = {
			new Point2D.Double((RIGHT_EDGE + MIDLINE_COL) / 2, MIDLINE_ROW),
			new Point2D.Double(X, Y)// 1

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.25), Y
					+ (TRIANGLE_WIDTH / 2 * 0.25))// 2

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.5), Y
					+ (TRIANGLE_WIDTH / 2 * 0.5))// 3

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.75), Y
					+ (TRIANGLE_WIDTH / 2 * 0.75))// 4

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 1), Y
					+ (TRIANGLE_WIDTH / 2 * 1))// 5

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 1), Y
					+ (TRIANGLE_WIDTH / 2 * 0.5))// 6

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.75), Y
					+ (TRIANGLE_WIDTH / 2 * 0.75) - (RADIUS * 2.4))// 7 I have
																	// no idea
																	// why it
																	// should be
																	// 2.4

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.5), Y)// 8

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.75), Y
					- (TRIANGLE_WIDTH / 2 * 0.75) + (RADIUS * 2.4))// 9 This
																	// line too.

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 1), Y
					- (TRIANGLE_WIDTH / 2 * 0.5))// 10

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 1), Y
					- (TRIANGLE_WIDTH / 2 * 1))// 11

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.75), Y
					- (TRIANGLE_WIDTH / 2 * 0.75))// 12

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.5), Y
					- (TRIANGLE_WIDTH / 2 * 0.5))// 13

			,
			new Point2D.Double(X - (TRIANGLE_HEIGHT * 0.25), Y
					- (TRIANGLE_WIDTH / 2 * 0.25))// 14

			, new Point2D.Double(X - (TRIANGLE_HEIGHT * 1), Y) // 15
	};
}
