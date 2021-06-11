package circuits;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import engine.GameState;
import gui.Component;
import util.Point;

public class DragState extends InputState {

	private List<Component> selected;
	private List<Point> initialPoints;
	private Point initialMouseLocation;

	public DragState(GameState state, CircuitPanel panel, Point p, List<Component> selected) {
		super(state, panel, "DRAG_STATE");
		this.selected = selected;
		this.initialMouseLocation = getPanel().transformToLocalCoordinates(p);
		
		setupInitialPoints();
	}

	public DragState(CircuitRunningState state, CircuitPanel panel, Point p, Component comp) {
		super(state, panel, "DRAG_STATE");
		this.selected = new ArrayList<Component>();
		this.selected.add(comp);
		this.initialMouseLocation = getPanel().transformToLocalCoordinates(p);
		
		setupInitialPoints();
	}

	public void setupInitialPoints() {
		this.initialPoints = new ArrayList<Point>(selected.size());
		
		for (Component comp : selected) {
			initialPoints.add(comp.getLocation());
		}
	}

	@Override
	public void mouseMoved(Point p) {
	}

	@Override
	public void mousePressed(Point p) {
		
	}

	public void resetLocations() {
		for (int i = 0; i < selected.size(); i++) {
			Component comp = selected.get(i);
			comp.setLocation(initialPoints.get(i));
			comp.setSelectionColor(Color.green);
		}
	}
	
	@Override
	public void mouseReleased(Point p) {
		for (int i = 0; i < selected.size(); i++) {
			Component comp = selected.get(i);
			if (getPanel().intersectsComponent(comp)) {
				resetLocations();
			}
			
			comp.setSelectionColor(Color.GREEN);
		}

		getPanel().switchState(new DefaultState(getState(),getPanel()));
	}

	@Override
	public void mouseDragged(Point p) {
		Point t = getPanel().transformToLocalCoordinates(p);

		for (int i = 0; i < selected.size(); i++) {
			Component comp = selected.get(i);
			if (comp.isMoveable()) {
				comp.setLocation(t.subtract(initialMouseLocation).add(initialPoints.get(i)));
			}

			if (getPanel().intersectsComponent(comp, selected)) {
				comp.setSelectionColor(Color.RED);
			} else {
				comp.setSelectionColor(Color.GREEN);
			}
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
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void escape() {
		// TODO Auto-generated method stub
		
	}

}
