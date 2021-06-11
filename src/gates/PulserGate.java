package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;

public class PulserGate extends Gate {
	private static final Color PULSERColor = new Color(0.68f,0.75f,0.6f,1.0f);

	public static Color getPULSERColor() {
		return PULSERColor;
	}

	public PulserGate(CircuitPanel panel, Point position) {
		super(panel, new Rectangle(position, new Point(120, 200)), PULSERColor, 1, "PULSER");

		Label lbl = new Label(this, new Rectangle(10, 0, getWidth()-20, getHeight()), "");
		lbl.setBackground(PULSERColor);
		lbl.setFontSize(40);

		lbl.setText("PUL");

		this.addComponent(lbl);

		this.addPort(new Port(this, new Point(60, 30), Port.OUTPUT, 1));
		this.addPort(new Port(this, new Point(60, 170), Port.SINGLE_INPUT, 1));
		
		canPulse = true;
	}
	
	private boolean canPulse;
	
	@Override
	public boolean[] getOutputState(int index) {
		boolean inp = getInputStates(1)[0];
		
		if (inp && canPulse) {
			canPulse = false;
			return new boolean[] {true};
		}
		
		if (!inp) {
			canPulse = true;
		}
		
		return new boolean[] {false};
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new PulserGate(panel,location);
	}
}
