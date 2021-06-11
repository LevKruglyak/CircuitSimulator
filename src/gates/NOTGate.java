package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;

public class NOTGate extends Gate {
	private static final Color NOTColor = new Color(1f,0.8f,1f,1.0f);

	public static Color getNOTColor() {
		return NOTColor;
	}

	public NOTGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position, new Point(120, 200)), NOTColor, size, "NOT");

		Label lbl = new Label(this, new Rectangle(10, 0, getWidth()-20, getHeight()), "");
		lbl.setBackground(NOTColor);
		lbl.setFontSize(40);

		if (size == 1) {
			lbl.setText("NOT");
		} else {
			lbl.setText(size + " NOT");
		}

		this.addComponent(lbl);

		this.addPort(new Port(this, new Point(60, 30), Port.OUTPUT, getBitSize()));
		this.addPort(new Port(this, new Point(60, 170), Port.SINGLE_INPUT, getBitSize()));
	}
	
	@Override
	public boolean[] getOutputState(int index) {
		boolean[] A = getInputStates(1);

		boolean[] C = new boolean[A.length];
		for (int i = 0; i < A.length; i++) {
			C[i] = !A[i];
		}

		return C;
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new NOTGate(panel,location, size);
	}
}
