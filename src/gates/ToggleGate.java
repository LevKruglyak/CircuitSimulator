package gates;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Button;
import util.Point;
import util.Rectangle;
import util.Util;

public class ToggleGate extends Gate {
	private boolean[] state;
	private Button[] btns;

	public ToggleGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position, new Point(10 + 110 * size, 140)), Color.DARK_GRAY, size, "TOGGLE");

		this.btns = new Button[size];
		for (int i = 0; i < size; i++) {
			Button btn = new Button(panel, new Rectangle(10 + 110 * i, 30, 100, 100), "");
			btns[i] = btn;
			btn.setBackground(Color.BLUE);
			btn.setExpandOnHover(false);
			int q = i;
			btn.addEventListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int j = size - 1 - q;
					if (state[j]) {
						btn.setBackground(Color.BLUE);
					} else {
						btn.setBackground(Color.RED);
					}

					state[j] = !state[j];
				}

			});
			this.addComponent(btn);
		}

		super.addPort(new Port(this, new Point(this.getWidth() / 2, 30), Port.OUTPUT, size));
		this.state = new boolean[size];
		this.setBordered(true);
		this.setBorder(Color.GRAY);
	}

	@Override
	public boolean[] getOutputState(int index) {
		return state;
	}
	
	protected void setState(boolean[] state) {
		this.state = Util.packArray(state, getBitSize());
		
		for (int i = 0; i < state.length; i++) {
			if (state[i]) {
				btns[getBitSize()-i-1].setBackground(Color.RED);
			}
		}
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		ToggleGate gt = new ToggleGate(panel,location, size);
		gt.setState(Util.toBinaryArray(other));
		return gt;
	}
	
	protected String saveContents() {
		return Util.toString(state)+"";
	}
}
