package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class DecoderGate extends Gate {

	public DecoderGate(CircuitPanel panel, Point location, int size) {
		super(panel, new Rectangle(location, new Point((int) (Math.pow(2, size)) * 70 + 20, 200)), Color.DARK_GRAY,
				size, "DECODER");
		Label lbl = new Label(this, new Rectangle(0, 0, getWidth(), getHeight()), "");
		lbl.setBackground(Color.DARK_GRAY);
		lbl.setFontSize(40);

		if (size == 1) {
			lbl.setText("DECODER");
		} else {
			lbl.setText(size + " DECODER");
		}
		this.addComponent(lbl);

		for (int i = 0; i < (int) (Math.pow(2, size)); i++) {
			super.addPort(new Port(this, new Point(45 + 70 * i, 30), Port.OUTPUT, 1));
		}

		super.addPort(new Port(this, new Point(getWidth() / 2, 170), Port.SINGLE_INPUT, size));

		this.setBordered(true);
		this.setBorder(Color.GRAY);
	}

	public boolean[] getOutputState(int index) {
		int num = (int) Util.toDecimal(getInputStates((int) (Math.pow(2, getBitSize()))));
		return new boolean[] { num == (int) (Math.pow(2, getBitSize())) - 1 - index };
	}

	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new DecoderGate(panel, location, size);
	}
}
