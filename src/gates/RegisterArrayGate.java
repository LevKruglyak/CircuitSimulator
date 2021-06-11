package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class RegisterArrayGate extends Gate {

	private int addSize;

	public RegisterArrayGate(CircuitPanel panel, Point position, int dataSize, int addSize) {
		super(panel, new Rectangle(position, new Point(400, 250)), Color.DARK_GRAY, dataSize, "REGISTERARRAY");

		Label lbl = new Label(this, new Rectangle(20, 0, getWidth() - 40, getHeight()), "");
		lbl.setBackground(Color.DARK_GRAY);
		lbl.setFontSize(40);

		this.addSize = addSize;

		lbl.setText((int) Math.pow(2, addSize) + "x" + dataSize + ((addSize == 0) ? " REGISTER" : " REGISTER ARRAY"));
		this.addComponent(lbl);

		super.addPort(new Port(this, new Point(200, 30), Port.OUTPUT, dataSize));

		Label lbl1 = new Label(this, new Rectangle(50, 180, 30, 20), "D");
		lbl1.setBackground(Color.DARK_GRAY);
		lbl1.setFontSize(15);
		lbl1.setTextColor(Color.WHITE);
		this.addComponent(lbl1);

		Label lbl2 = new Label(this, new Rectangle(210, 180, 30, 20), "AD");
		lbl2.setBackground(Color.DARK_GRAY);
		lbl2.setFontSize(15);
		lbl2.setTextColor(Color.WHITE);
		this.addComponent(lbl2);

		Label lbl3 = new Label(this, new Rectangle(280, 180, 30, 20), "W");
		lbl3.setBackground(Color.DARK_GRAY);
		lbl3.setFontSize(15);
		lbl3.setTextColor(Color.WHITE);
		this.addComponent(lbl3);

		Label lbl4 = new Label(this, new Rectangle(350, 180, 30, 20), "R");
		lbl4.setBackground(Color.DARK_GRAY);
		lbl4.setFontSize(15);
		lbl4.setTextColor(Color.WHITE);
		this.addComponent(lbl4);

		super.addPort(new Port(this, new Point(50, 220), Port.SINGLE_INPUT, dataSize));
		super.addPort(new Port(this, new Point(210, 220), Port.SINGLE_INPUT, addSize));
		super.addPort(new Port(this, new Point(280, 220), Port.SINGLE_INPUT, 1));
		super.addPort(new Port(this, new Point(350, 220), Port.SINGLE_INPUT, 1));
		
		this.setBordered(true);
		this.setBorder(Color.GRAY);
		this.data = new boolean[(int) (Math.pow(2, addSize))][dataSize];
	}

	private boolean[][] data;

	@Override
	public boolean[] getOutputState(int index) {
		int address = (int) Util.toDecimal(getInputStates(2));

		if (getInputStates(3)[0]) {
			data[address] = getInputStates(1);
		}

		if (getInputStates(4)[0]) {
			return data[address];
		} else {
			return new boolean[getBitSize()];
		}
	}

	protected String saveContents() {
		return addSize + "," + Util.toString2D(data);
	}

	public void setData(boolean[][] data) {
		this.data = data;
	}

	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		String[] data = other.split(",");
		RegisterArrayGate gt = new RegisterArrayGate(panel, location, size, Integer.parseInt(data[0]));
		gt.setData(Util.toBinary2D(data[1], (int) (Math.pow(2, Integer.parseInt(data[0]))), size));
		return gt;
	}
}
