package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import util.Point;
import util.Rectangle;

public class SplitGate extends Gate {
	public SplitGate(CircuitPanel panel, Point location, int size) {
		super(panel, new Rectangle(location, new Point(size*50+10, 110)), Color.DARK_GRAY, size, "SPLIT");
		
		for (int i = 0; i < size; i++) {
			super.addPort(new Port(this, new Point(30+50*i,30), Port.OUTPUT, 1));
		}
		
		super.addPort(new Port(this, new Point(getWidth()/2,80), Port.SINGLE_INPUT, size));
		
		this.setBordered(true);
		this.setBorder(Color.GRAY);
	}
	
	@Override
	public boolean[] getOutputState(int index) {
		return new boolean[] {getInputStates(getBitSize())[getBitSize()-1-index]};
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new SplitGate(panel,location, size);
	}
}
