package circuits;

import java.awt.Color;
import java.util.Arrays;

import engine.GameState;
import gui.Component;
import util.Point;

public class PlaceState extends InputState {
	private Component selected;
	private Point initialPoint;
	private Point initialMouseLocation;

	public PlaceState(GameState state, CircuitPanel panel, Point p, Component selected) {
		super(state, panel, "PLACE_STATE");
		this.selected = selected;
		this.initialMouseLocation = getPanel().transformToLocalCoordinates(p);

		setupInitialPoints();
	}

	public void setupInitialPoints() {
		this.initialPoint = selected.getLocation();
	}

	@Override
	public void mouseDragged(Point p) {
	}

	@Override
	public void mousePressed(Point p) {

	}

	public void delete() {
		getPanel().removeComponent(selected);
		getPanel().switchState(new DefaultState(getState(), getPanel()));
	}

	@Override
	public void mouseReleased(Point p) {
		selected.setSelectionColor(Color.GREEN);
		if (getPanel().intersectsComponent(selected) || !getPanel().isMouseOver()) {
			delete();
		} else {
			getPanel().switchState(new DefaultState(getState(), getPanel()));
		}
	}

	@Override
	public void mouseMoved(Point p) {
		Point t = getPanel().transformToLocalCoordinates(p);

		if (selected.isMoveable()) {
			selected.setLocation(t.subtract(initialMouseLocation).add(initialPoint));
		}

		if (getPanel().intersectsComponent(selected, Arrays.asList(selected))) {
			selected.setSelectionColor(Color.RED);
		} else {
			selected.setSelectionColor(Color.GREEN);
		}
	}

	@Override
	public void mouseWheelMoved(int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void zoom(float size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shift(Point del) {
		// TODO Auto-generated method stub

	}

	@Override
	public void escape() {
		delete();
	}
}
