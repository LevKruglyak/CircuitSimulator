package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;

public class LEDGate extends Gate {
	private Label[] leds;
	
	public LEDGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position, new Point(10 + 110 * size, 140)), Color.DARK_GRAY, size, "LED");

		leds = new Label[size];
		for (int i = 0; i < size; i++) {
			Label led = new Label(panel, new Rectangle(10 + 110 * i, 10, 100, 100), "");
			led.setBackground(Color.BLUE);
			this.addComponent(led);
			leds[i] = led;
		}

		super.addPort(new Port(this, new Point(this.getWidth() / 2, 110), Port.SINGLE_INPUT, size));
		this.setBordered(true);
		this.setBorder(Color.GRAY);
	}

	public void update() {
		boolean[] input = getInputStates(0);
		for (int i = 0; i < getBitSize(); i++) {
			if (input[getBitSize()-i-1]) {
				if (!leds[i].getBackground().equals(Color.RED))
				leds[i].setBackground(Color.RED);
			} else {
				if (!leds[i].getBackground().equals(Color.BLUE))
				leds[i].setBackground(Color.BLUE);
			}
		}
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new LEDGate(panel,location, size);
	}
}
