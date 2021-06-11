package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class CounterGate extends Gate {
	private long num;

	public CounterGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position, new Point(400, 200)), Color.DARK_GRAY, size, "COUNTER");

		Label lbl = new Label(this, new Rectangle(70, 0, getWidth() - 140, getHeight()), "");
		lbl.setBackground(Color.DARK_GRAY);
		lbl.setFontSize(40);

		if (size == 1) {
			lbl.setText("COUNTER");
		} else {
			lbl.setText(size + " COUNTER");
		}
		this.addComponent(lbl);

		super.addPort(new Port(this, new Point(200, 30), Port.OUTPUT, size));

		Label lbl1 = new Label(this, new Rectangle(80, 130, 30, 20), "CE");
		lbl1.setBackground(Color.DARK_GRAY);
		lbl1.setFontSize(15);
		lbl1.setTextColor(Color.WHITE);
		this.addComponent(lbl1);

		Label lbl2 = new Label(this, new Rectangle(280, 130, 30, 20), "D");
		lbl2.setBackground(Color.DARK_GRAY);
		lbl2.setFontSize(15);
		lbl2.setTextColor(Color.WHITE);
		this.addComponent(lbl2);

		Label lbl3 = new Label(this, new Rectangle(350, 130, 30, 20), "E");
		lbl3.setBackground(Color.DARK_GRAY);
		lbl3.setFontSize(15);
		lbl3.setTextColor(Color.WHITE);
		this.addComponent(lbl3);

		super.addPort(new Port(this, new Point(80, 170), Port.SINGLE_INPUT, 1));
		super.addPort(new Port(this, new Point(280, 170), Port.SINGLE_INPUT, size));
		super.addPort(new Port(this, new Point(350, 170), Port.SINGLE_INPUT, 1));

		this.setBordered(true);
		this.num = 0;
		this.setBorder(Color.GRAY);
		this.canIncrement = true;
	}

	private boolean canIncrement;

	public boolean[] getOutputState(int index) {
		boolean[] inc = getInputStates(1);

		if (inc[0]) {
			if (canIncrement) {
				num++;
				canIncrement = false;
			}
		} else {
			canIncrement = true;
		}

		boolean[] dat = getInputStates(2);
		boolean[] wri = getInputStates(3);

		if (wri[0]) {
			this.num = Util.toDecimal(dat);
		}

		return Util.toBinary(num);
	}
	
	protected String saveContents() {
		return num+"";
	}
	
	private void setNumber(int num) {
		this.num = num;
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		CounterGate cg = new CounterGate(panel,location,size);
		cg.setNumber(Integer.parseInt(other));
		return cg;
	}
}
