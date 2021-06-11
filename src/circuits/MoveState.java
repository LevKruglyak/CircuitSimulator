package circuits;

import engine.GameState;
import util.Point;

public class MoveState extends InputState {

	private Point initialMouseLocation;
	private Point initialShift;

	public MoveState(GameState state, CircuitPanel panel, Point p) {
		super(state, panel, "MOVE_STATE");

		this.initialMouseLocation = p;
		this.initialShift = getPanel().getShift();
	}

	public void mouseReleased(Point p) {
		getPanel().switchState(new DefaultState(getState(),getPanel()));
	}

	@Override
	public void mouseDragged(Point p) {
		getPanel().setShift(initialShift.add(p.subtract(initialMouseLocation).times(1f / getPanel().getScale())));
	}

	@Override
	public void mousePressed(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Point p) {
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
	public void mouseWheelMoved(int size) {
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
