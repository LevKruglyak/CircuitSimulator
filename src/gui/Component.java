package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import util.GraphicsPlus;
import util.IDObject;
import util.Point;
import util.Rectangle;

public abstract class Component extends IDObject {

	private Rectangle boundBox;
	private List<EventListener> eventListeners;

	private boolean enabled;
	private boolean isPressed;
	private boolean isMouseOver;

	private ComponentCarrier parent;

	private boolean selected;
	private boolean selectable;
	private boolean moveable;

	private boolean intersectable;
	private boolean overlaySelectable;

	public boolean isIntersectable() {
		return intersectable;
	}

	public void setIntersectable(boolean intersectable) {
		this.intersectable = intersectable;
	}

	private Color selectionColor = Color.GREEN;

	public Component(Rectangle boundBox, ComponentCarrier parent) {
		super("COMPONENT");

		this.boundBox = boundBox;
		this.eventListeners = new ArrayList<EventListener>(3);

		setupBooleans();

		this.parent = (parent);
	}

	private void setupBooleans() {
		this.enabled = true;
		this.isPressed = false;
		this.isMouseOver = false;

		this.selected = false;
		this.selectable = false;
		this.moveable = false;

		this.intersectable = true;
		this.overlaySelectable = true;
	}

	public boolean intersects(Component comp) {
		if (intersectable) {
			return boundBox.overlaps(comp.getBoundBox());
		} else {
			return false;
		}
	}

	public void renderSelection(GraphicsPlus g) {
		if (selected) {
			g.setStroke(new BasicStroke(5));
			g.setColor(selectionColor);
			g.drawRect(getBoundBox().scale(1.2f));
		}
	}

	public Component(Component component) {
		super("COMPONENT");

		this.boundBox = component.boundBox;
		this.eventListeners = component.eventListeners;
		this.enabled = component.enabled;
		this.parent = component.parent;

		setupBooleans();
	}

	public void render(GraphicsPlus g) {
		renderSelection(g);
	}

	public Point getLocation() {
		return boundBox.getLocation();
	}

	public Point getDimensions() {
		return boundBox.getBounds();
	}

	public Rectangle getBoundBox() {
		return boundBox;
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

	public void setBoundBox(Rectangle boundBox) {
		this.boundBox = boundBox;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public void setMouseOver(boolean isMouseOver) {
		this.isMouseOver = isMouseOver;
	}

	public List<EventListener> getEventListeners() {
		return eventListeners;
	}

	public void addEventListener(EventListener list) {
		this.eventListeners.add(list);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public boolean isMouseOver() {
		return isMouseOver;
	}
	
	public void update() {
		
	}

	public void mouseDragged(Point e) {

	}

	public void mouseMoved(Point e) {

	}

	public void mouseClicked(Point e) {

	}

	public void mouseEntered(Point e) {
		this.isMouseOver = true;
	}

	public void mouseExited(Point e) {
		this.isPressed = false;
		this.isMouseOver = false;
	}

	public void mousePressed(Point e) {
		this.isPressed = true;
	}

	public void mouseReleased(Point e) {
		if (isPressed && enabled) {
			for (EventListener listener : getEventListeners()) {
				if (listener instanceof ActionListener) {
					ActionListener actionListener = (ActionListener) listener;
					actionListener.actionPerformed(null);
				}
			}
		}

		this.isPressed = false;
	}

	public ComponentCarrier getParent() {
		return parent;
	}

	public void setParent(ComponentCarrier parent) {
		this.parent = parent;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public void setSelectionColor(Color selectionColor) {
		this.selectionColor = selectionColor;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public Color getSelectionColor() {
		return selectionColor;
	}

	public void setLocation(Point p) {
		this.boundBox.setLocation(p);
	}

	public boolean overlaySelectable() {
		return overlaySelectable;
	}
	
	public void setOverlaySelectable(boolean b) {
		this.overlaySelectable = b;
	}
}
