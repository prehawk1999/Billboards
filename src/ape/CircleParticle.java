/*
APE (Actionscript Physics Engine) is an AS3 open source 2D physics engine
Copyright 2006, Alec Cove 

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Contact: ape@cove.org

Converted to Java by Theo Galanakis theo.galanakis@hotmail.com
 */
package ape;

import java.awt.geom.*; // For Ellipse2D

/**
 * A circle shaped particle.
 */
public class CircleParticle extends AbstractParticle {

	private double _radius;

	/**
	 * @param x
	 *            The initial x position of this particle.
	 * @param y
	 *            The initial y position of this particle.
	 * @param radius
	 *            The radius of this particle.
	 * @param fixed
	 *            Determines if the particle is fixed or not. Fixed particles
	 *            are not affected by forces or collisions and are good to use
	 *            as surfaces. Non-fixed particles move freely in response to
	 *            collision and forces.
	 * @param mass
	 *            The mass of the particle.
	 * @param elasticity
	 *            The elasticity of the particle. Higher values mean more
	 *            elasticity or 'bounciness'.
	 * @param friction
	 *            The surface friction of the particle.
	 */
	public CircleParticle(double x, double y, double radius, boolean fixed,
			double mass, double elasticity, double friction) {
		super(x, y, fixed, mass, elasticity, friction);
		_radius = radius;

		// TG TODO cannot have this before calling a super.
		/*
		 * if (Double.valueOf(mass) == null) mass = 1; if
		 * (Double.valueOf(elasticity) == null) elasticity = 0.3; if
		 * (Double.valueOf(friction) == null) friction = 0;
		 */

	}

	/**
	 * The radius of the particle.
	 */
	public double getRadius() {
		return _radius;
	}

	/**
	 * @private
	 */
	public void setRadius(double r) {
		_radius = r;
	}

	/**
	 * The default paint method for the particle. Note that you should only use
	 * the default painting methods for quick prototyping. For anything beyond
	 * that you should always write your own particle classes that either extend
	 * one of the APE particle and constraint classes, or is a composite of
	 * them. Then within that class you can define your own custom painting
	 * method.
	 */
	public void paint() {

		if (dc == null)
			dc = getDefaultContainer();

		if (!getVisible())
			return;

		Ellipse2D.Double circle = new Ellipse2D.Double(curr.x - getRadius(),
				curr.y - getRadius(), (double) getRadius() * 2,
				(double) getRadius() * 2);
		dc.draw(circle);

	}

	// REVIEW FOR ANY POSSIBILITY OF PRECOMPUTING
	/**
	 * @private
	 */
	public Interval getProjection(Vector axis) {
		double c = curr.dot(axis);
		interval.min = c - _radius;
		interval.max = c + _radius;
		return interval;
	}

	/**
	 * @private
	 */
	public Interval getIntervalX() {
		interval.min = curr.x - _radius;
		interval.max = curr.x + _radius;
		return interval;
	}

	/**
	 * @private
	 */
	public Interval getIntervalY() {
		interval.min = curr.y - _radius;
		interval.max = curr.y + _radius;
		return interval;
	}
}
