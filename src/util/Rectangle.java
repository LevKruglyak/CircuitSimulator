package util;

public class Rectangle {

	private Point location;
	private Point bounds;
	
	public Rectangle(float f, float g, float h, float i) {
		this.location = new Point(f, g);
		this.bounds = new Point(h, i);
	}

	public Rectangle(Point location, Point bounds) {
		this.location = location;
		this.bounds = bounds;
	}

	public Rectangle(Rectangle rect) {
		this.location = rect.getLocation();
		this.bounds = rect.getBounds();
	}

	public Rectangle(Point center, int radius) {
		this.location = center.subtract(new Point(radius,radius));
		this.bounds = new Point(2*radius,2*radius);
	}

	public boolean overlaps(Rectangle rect) {
		Point topLeft = getLocation();
		Point bottomRight = getLocation().add(getBounds());

		Point otherTopLeft = rect.getLocation();
		Point otherBottomRight = rect.getLocation().add(rect.getBounds());

		if (topLeft.getX() > otherBottomRight.getX() || bottomRight.getX() < otherTopLeft.getX()
				|| topLeft.getY() > otherBottomRight.getY() || bottomRight.getY() < otherTopLeft.getY()) {
			return false;
		}
		
		return true;
	}

	public boolean contains(Point p) {
		return (p.getX() >= getX() && p.getY() >= getY())
				&& (p.getX() <= getX() + getWidth() && p.getY() <= getY() + getHeight());
	}

	public Point getCenter() {
		return new Point(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	public Rectangle scale(float f) {
		float dwidth = getWidth() * (f-1);
		float dheight = getHeight() * (f-1);
		
		float delta = Math.min(dwidth, dheight);
		Point loc = location.subtract(new Point(delta,delta));
		return new Rectangle(loc, new Point(getWidth() +delta*2, getHeight() +delta*2));
	}

	public Point getLocation() {
		return location;
	}

	public Point getBounds() {
		return bounds;
	}

	public float getX() {
		return location.getX();
	}

	public float getY() {
		return location.getY();
	}

	public float getWidth() {
		return bounds.getX();
	}

	public float getHeight() {
		return bounds.getY();
	}

	public Rectangle zero() {
		return new Rectangle(0,0,getWidth(), getHeight());
	}

	public void setLocation(Point p) {
		this.location = p;
	}

	public void setDimensions(Point p) {
		this.bounds = p;
	}

}
