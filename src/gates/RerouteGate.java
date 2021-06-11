package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import util.Point;
import util.Rectangle;

public class RerouteGate extends Gate {

	public RerouteGate(CircuitPanel panel, Point location, int bitSize) {
		super(panel, new Rectangle(location, new Point(90,90)), Color.DARK_GRAY, bitSize, "REROUTE");
		this.addPort(new Port(this, new Point(45,45), Port.INPUT_OUTPUT, bitSize));
		this.setBordered(true);
		this.setBorder(Color.GRAY);
	}
	
	@Override
	public boolean[] getOutputState(int index) {
		return getInputStates(0);
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new RerouteGate(panel,location, size);
	}
}
