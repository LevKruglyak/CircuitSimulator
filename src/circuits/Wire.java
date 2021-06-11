package circuits;

import java.awt.BasicStroke;
import java.awt.Color;

import gui.Component;
import gui.ComponentCarrier;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;
import util.Util;

public class Wire extends Component {

	private float wireThickness;

	private Port input;
	private Port output;

	private int bitSize;

	private boolean[] state;

	public Wire(ComponentCarrier carrier, Port input, Port output, int bitSize) {
		super(new Rectangle(new Point(-10000, -10000), new Point(1, 1)), carrier);
		this.setInput(input);
		this.setOutput(output);

		this.state = new boolean[bitSize];
		this.bitSize = bitSize;
		this.setWireThickness(9 + bitSize);
		this.setSelectable(true);
		this.setOverlaySelectable(false);
	}

	public void clear() {
		this.state = new boolean[bitSize];
	}

	@Override
	public void update() {
		state = input.getPortState();

		if (!output.isMultInput()) {
			output.setPortState(state);
			output.update();
		}
	}

	public Port getInput() {
		return input;
	}

	public void setInput(Port input) {
		this.input = input;
	}

	public Port getOutput() {
		return output;
	}

	public void setOutput(Port output) {
		this.output = output;
	}

	public int getBitSize() {
		return bitSize;
	}

	public void render(GraphicsPlus g) {
		g.setStroke(new BasicStroke(wireThickness));
		Point p1 = input.getAbsoluteCenter();
		Point p2 = output.getAbsoluteCenter();

		if (bitSize == 1) {
			if (state[0]) {
				g.setColor(new Color(1f, 0f, 0f, 0.5f));
			} else {
				g.setColor(new Color(0f, 0f, 1f, 0.5f));
			}
		} else {
			float red = 0.f;
			for (int i = 0; i < bitSize; i++) {
				if (state[i])
					red++;
			}

			g.setColor(new Color(red / bitSize, 0f, 1 - red / bitSize, 0.5f));
		}

		if (isSelected()) {
			g.setColor(new Color(0f, 1f, 0f, 0.5f));
		}

		g.drawLine(p1, p2);

		Point center = (p1.add(p2)).times(0.5f);

		if (bitSize > 1) {
			g.setColor(Color.WHITE);
			g.drawString("" + Util.toDecimal(state), center);
		}
	}

	public boolean intersects(Point p) {
		if (p.distanceTo(input.getAbsoluteCenter(), output.getAbsoluteCenter()) < wireThickness / 2f) {
			return p.isProjInside(input.getAbsoluteCenter(), output.getAbsoluteCenter());
		}
		return false;
	}

	public float getWireThickness() {
		return wireThickness;
	}

	public void setWireThickness(float wireThickness) {
		this.wireThickness = wireThickness;
	}

	public void kill() {
		this.input.removeWire(this);
		this.output.removeWire(this);
		this.input.getGate().getPanel().removeComponent(this);
		
		this.output.setPortState(new boolean[bitSize]);
	}
}
