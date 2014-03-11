package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ape.Vector;

public class Player implements Table, ActionListener {

	private boolean Round;
	private static int forceLevel;
	private int score;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {

		this.score = score;
	}

	public void addScore(int a) {

		this.score += a;
	}

	public static int getForceLevel() {
		return forceLevel;
	}

	public static void setForceLevel(int forceLevel) {

		Player.forceLevel = forceLevel;
	}

	public boolean IsWin() {

		return (score >= 8) ? true : false;
	}

	public void Hit(Ball b, Vector v) {

		if (!this.Round)
			return;
		b.addForce(v.minusEquals(new Vector(b.getpx(), b.getpy())).unitize()
				.multEquals(getForceLevel()));
	}

	public void setRound(boolean b) {
		this.Round = b;
	}

	public boolean getRound() {

		return this.Round;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (forceLevel <= 1000)
			forceLevel += 10;
	}

}
