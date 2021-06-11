package circuits;

import engine.GameState;
import gates.ANDGate;
import gates.Gate;
import gui.Component;
import util.Point;

public class WireState extends InputState {

	private Wire wire;
	private Gate tempGate;
	private Port tempPort;
	private Point startMouseLocation;
	private Point initialTempGate;

	public WireState(GameState state, CircuitPanel panel, Port port) {
		super(state, panel, "WIRE_STATE");

		this.tempGate = new ANDGate(panel, port.getAbsoluteCenter(), 1);
		this.tempPort = new Port(tempGate, new Point(0, 0), Port.SINGLE_INPUT, port.getBitSize());
		this.wire = new Wire(panel, port, tempPort, port.getBitSize());
		
		port.addWire(wire);
		panel.addComponent(wire);
		wire.update();
		startMouseLocation = port.getAbsoluteCenter();
		initialTempGate = port.getAbsoluteCenter();
	}

	@Override
	public void mouseMoved(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Point p) {
		Point t = getPanel().transformToLocalCoordinates(p);

		Port port = null;
		Component comp = getPanel().getSelected(t, wire);

		if (comp instanceof Gate) {
			Component portComp = ((Gate) comp).getSelected(t.subtract(comp.getLocation()));
			if (portComp instanceof Port) {
				port = (Port) portComp;
			}
		}

		if (port != null && port.canConnect(wire)) {
			port.linkInputWire(wire);
		} else {
			wire.kill();
		}

		getPanel().switchState(new DefaultState(getState(), getPanel()));
	}

	@Override
	public void mouseDragged(Point p) {
		tempGate.setLocation(
				getPanel().transformToLocalCoordinates(p).subtract(startMouseLocation).add(initialTempGate));
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

	}

	@Override
	public void escape() {
		// TODO Auto-generated method stub
		
	}
}
