package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import circuits.Wire;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;

public class Panel extends Component implements ComponentCarrier {

	public Panel(Rectangle getBoundBox, ComponentCarrier parent) {
		super(getBoundBox, parent);

		this.components = new ArrayList<Component>();

		this.background = Color.DARK_GRAY;
		this.border = Color.WHITE;

		this.setBorderThickness(5);
		this.setBordered(true);
	}

	public Panel(Panel panel) {
		super(panel.getBoundBox(), panel.getParent());

		this.components = panel.getComponents();

		this.background = panel.getBackground();
		this.border = panel.getBorder();
		this.setBorderThickness(panel.getBorderThickness());
	}

	private Color background;
	private Color border;

	private boolean bordered;

	private int borderThickness;

	private List<Component> components;

	public List<Component> getComponents() {
		return this.components;
	}

	public Component getSelected(Point p, Component except) {
		for (int i = components.size() - 1; i >= 0; i--) {
			Component comp = components.get(i);
			if (!(comp instanceof Wire)) {
				if (comp.intersects(p) && !comp.equals(except)) {
					return comp;
				}
			}
		}

		return this;
	}

	public Component getSelected(Point p) {
		for (int i = components.size() - 1; i >= 0; i--) {
			Component comp = components.get(i);
			if (!(comp instanceof Wire)) {
				if (comp.intersects(p)) {
					return comp;
				}
			}
		}

		return this;
	}

	public boolean intersectsComponent(Component comp) {
		for (int i = 0; i < components.size(); i++) {
			if (!comp.equals(components.get(i))) {
				if (comp.intersects(components.get(i))) {
					return true;
				}
			}
		}

		return false;
	}

	public void update() {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).update();
			;
		}
	}

	public boolean intersectsComponent(Component comp, List<Component> except) {
		for (int i = 0; i < components.size(); i++) {
			Component toIntersect = components.get(i);

			if (!comp.equals(toIntersect) && !except.contains(toIntersect)) {
				if (comp.intersects(toIntersect)) {
					return true;
				}
			}
		}

		return false;
	}

	public void render(GraphicsPlus g) {
		g.setColor(background);
		g.fillRect(getBoundBox());

		renderComponents(g);

		if (bordered) {
			g.setStroke(new BasicStroke(borderThickness));
			g.setColor(border);
			g.drawRect(getBoundBox());
		}

		super.render(g);
	}

	public void renderComponents(GraphicsPlus g) {
		g.translate(getLocation());
		for (int i = 0; i < components.size(); i++) {
			Component comp = components.get(i);
			comp.render(g);
		}
		g.translate(getLocation().times(-1));
	}

	public Color getBackground() {
		return background;
	}

	public Color getBorder() {
		return border;
	}

	public Point transformToLocalCoordinates(Point p) {
		return p.subtract(getLocation());
	}

	@Override
	public void mouseDragged(Point p) {
		super.mouseDragged(p);
		if (isEnabled()) {
			p = transformToLocalCoordinates(p);

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
	}

	@Override
	public void mouseMoved(Point p) {
		super.mouseMoved(p);

		if (isEnabled()) {
			p = transformToLocalCoordinates(p);

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
	}

	@Override
	public void mouseClicked(Point p) {
		super.mouseClicked(p);
		if (isEnabled()) {
			p = transformToLocalCoordinates(p);

			for (int i = 0; i < components.size(); i++) {
				Component comp = components.get(i);

				if (comp.intersects(p)) {
					comp.mouseClicked(p);
				}
			}
		}
	}

	@Override
	public void mousePressed(Point p) {
		super.mousePressed(p);
		if (isEnabled()) {
			p = transformToLocalCoordinates(p);

			for (int i = 0; i < components.size(); i++) {
				Component comp = components.get(i);

				if (comp.intersects(p)) {
					comp.mousePressed(p);
				}
			}
		}
	}

	@Override
	public void mouseReleased(Point p) {
		super.mouseReleased(p);
		if (isEnabled()) {
			p = transformToLocalCoordinates(p);

			for (int i = 0; i < components.size(); i++) {
				Component comp = components.get(i);

				if (comp.intersects(p)) {
					comp.mouseReleased(p);
				}
			}
		}
	}

	public void bringToFront(Component comp) {
		this.components.remove(comp);
		this.components.add(comp);
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

	public void addComponent(int i, Component comp) {
		this.components.add(i, comp);
	}

	@Override
	public void removeComponent(Component comp) {
		this.components.remove(comp);
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	public boolean isBordered() {
		return bordered;
	}

	public void setBordered(boolean bordered) {
		this.bordered = bordered;
	}
}
