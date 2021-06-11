package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import util.Point;
import util.Rectangle;

public class ClockGate extends Gate {

	private static final Color CLOCKColor = new Color(0.39f,0.7f,0.3f,1.0f); 
	
	public static Color getCLOCKColor() {
		return CLOCKColor;
	}
	
	public ClockGate(CircuitPanel panel, Point location) {
		super(panel, new Rectangle(location, new Point(90,90)), CLOCKColor, 1, "CLOCK");
		this.addPort(new Port(this, new Point(45,45), Port.OUTPUT, 1));
	}
	
	public boolean[] getOutputState(int index) {
		int CLOCK_SPEED = getPanel().getState().getCLOCK_SPEED();
		return new boolean[] { (getPanel().getTicks()%CLOCK_SPEED) >= (CLOCK_SPEED/2) };
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new ClockGate(panel,location);
	}
}
