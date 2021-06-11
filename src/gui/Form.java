package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import engine.GameState;
import util.GraphicsPlus;
import util.IDObject;
import util.Point;
import util.Rectangle;

public class Form extends IDObject implements MouseListener, MouseMotionListener, ComponentCarrier {

	private Rectangle boundBox;
	private GameState state;

	private Color background;
	private Color border;

	private int borderThickness;

	private ArrayList<Component> components;

	public Form(Rectangle rect, GameState state) {
		super("FORM");

		this.state = state;
		this.boundBox = rect;
		this.components = new ArrayList<Component>();

		this.background = Color.DARK_GRAY;
		this.border = Color.WHITE;
		this.setBorderThickness(5);
	}

	public void setBoundBox(Rectangle rect) {
		this.boundBox = rect;
	}

	public List<Component> getComponents() {
		return this.components;
	}

	public void unClick() {
		Point p = new Point(-10000, -10000);

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);
			comp.mouseExited(p);
		}
	}

	public void render(GraphicsPlus g) {
		g.setColor(background);
		g.fillRect(boundBox);

		g.translate(getLocation());

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);
			comp.render(g);
		}
		g.translate(getLocation().times(-1));

		g.setStroke(new BasicStroke(borderThickness));
		g.setColor(border);
		g.drawRect(boundBox);
	}

	public void closeForm() {
		this.state.removeForm(this);
	}

	public void update() {

	}

	public GameState getState() {
		return state;
	}

	public Color getBackground() {
		return background;
	}

	public Color getBorder() {
		return border;
	}

	public Rectangle getBoundBox() {
		return boundBox;
	}

	public Point getLocation() {
		return boundBox.getLocation();
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	public Point getDimensions() {
		return boundBox.getBounds();
	}

	public boolean intersects(Point p) {
		return boundBox.contains(p);
	}

	public float getWidth() {
		return boundBox.getWidth();
	}

	public float getHeight() {
		return boundBox.getHeight();
	}

	public float getX() {
		return boundBox.getX();
	}

	public float getY() {
		return boundBox.getY();
	}

	public Point transformToLocalCoordinates(Point p) {
		return p.subtract(getLocation());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = transformToLocalCoordinates(new Point(e));

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);

			boolean intersects = comp.intersects(p);
			
			if (intersects) {
				comp.mouseDragged(p);
			}
			
			if (!intersects && comp.isMouseOver()) {
				comp.mouseExited(p);
			}
			

			if (intersects && !comp.isMouseOver()) {
				comp.mouseEntered(p);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = transformToLocalCoordinates(new Point(e));

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);

			boolean intersects = comp.intersects(p);
			
			if (intersects) {
				comp.mouseMoved(p);
			}
			
			if (!intersects && comp.isMouseOver()) {
				comp.mouseExited(p);
			}
			

			if (intersects && !comp.isMouseOver()) {
				comp.mouseEntered(p);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = transformToLocalCoordinates(new Point(e));

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);

			if (comp.intersects(p)) {
				comp.mouseClicked(p);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = transformToLocalCoordinates(new Point(e));

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);

			if (comp.intersects(p)) {
				comp.mousePressed(p);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point p = transformToLocalCoordinates(new Point(e));

		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);

			if (comp.intersects(p)) {
				comp.mouseReleased(p);
			}
		}
	}

	public int getBorderThickness() {
		return borderThickness;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
	}

	@Override
	public void addComponent(Component comp) {
		this.components.add(comp);
	}

	@Override
	public void removeComponent(Component comp) {
		this.components.remove(comp);
	}

}
