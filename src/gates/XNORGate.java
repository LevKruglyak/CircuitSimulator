package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class XNORGate extends Gate {
	private static final Color XNORColor = new Color(0.3f, 0.8f, 0.7f, 1.0f);

	public static Color getXNORColor() {
		return XNORColor;
	}

	public XNORGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position, new Point(180, 200)), XNORColor, size, "XNOR");

		Label lbl = new Label(this, new Rectangle(30, 0, getWidth()-60, getHeight()), "");
		lbl.setBackground(XNORColor);
		lbl.setFontSize(40);

		if (size == 1) {
			lbl.setText("XNOR");
		} else {
			lbl.setText(size + " XNOR");
		}

		this.addComponent(lbl);

		this.addPort(new Port(this, new Point(90, 30), Port.OUTPUT, getBitSize()));
		this.addPort(new Port(this, new Point(45, 170), Port.SINGLE_INPUT, getBitSize()));
		this.addPort(new Port(this, new Point(135, 170), Port.SINGLE_INPUT, getBitSize()));
	}

	@Override
	public boolean[] getOutputState(int index) {
		boolean[] A = getInputStates(1);
		boolean[] B = getInputStates(2);

		return Util.NOT(Util.XOR(A, B));
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new XNORGate(panel,location, size);
	}
}
