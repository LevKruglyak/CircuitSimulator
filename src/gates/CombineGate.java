package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import util.Point;
import util.Rectangle;

public class CombineGate extends Gate {
	public CombineGate(CircuitPanel panel, Point location, int size) {
		super(panel, new Rectangle(location, new Point(size*50+10, 110)), Color.DARK_GRAY, size, "COMBINE");
		
		super.addPort(new Port(this, new Point(getWidth()/2,30), Port.OUTPUT, size));
		
		for (int i = 0; i < size; i++) {
			super.addPort(new Port(this, new Point(30+50*i,80), Port.SINGLE_INPUT, 1));
		}
		
		this.setBordered(true);
		this.setBorder(Color.GRAY);
	}
	
	@Override
	public boolean[] getOutputState(int index) {
		boolean[] C = new boolean[getBitSize()];
		for (int i = 0; i < getBitSize(); i++) {
			C[i] = getInputStates(getBitSize()-i)[0];
		}
		return C;
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new CombineGate(panel,location, size);
	}
}
