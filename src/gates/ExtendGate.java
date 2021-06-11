package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import util.Point;
import util.Rectangle;

public class ExtendGate extends Gate {
	public ExtendGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position, new Point(60, 120)), Color.DARK_GRAY, size, "EXTEND");

		this.setBordered(true);
		this.setBorder(Color.GRAY);
		this.addPort(new Port(this, new Point(30, 30), Port.OUTPUT, getBitSize()));
		this.addPort(new Port(this, new Point(30, 90), Port.SINGLE_INPUT, 1));
	}

	@Override
	public boolean[] getOutputState(int index) {
		boolean[] A = getInputStates(1);

		boolean[] C = new boolean[getBitSize()];
		if (A[0]) {
			for (int i = 0; i < C.length; i++) {
				C[i] = true;
			}
		}

		return C;
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new ExtendGate(panel,location, size);
	}
}
