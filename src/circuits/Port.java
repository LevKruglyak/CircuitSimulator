package circuits;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gates.Gate;
import gui.Component;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;
import util.Util;

public class Port extends Component {

	private static final float PORT_RADIUS = 20;

	public static final int SINGLE_INPUT = 0;
	public static final int OUTPUT = 1;
	public static final int MULTIPLE_INPUT = 2;
	public static final int INPUT_OUTPUT = 3;

	private int bitSize;
	private int portType;

	private boolean[] state;

	private List<Wire> wires;

	private Gate gate;
	
	public Port(Gate gate, Point location, int portType, int bitSize) {
		super(new Rectangle(location.subtract(new Point(PORT_RADIUS, PORT_RADIUS)),
				new Point(PORT_RADIUS, PORT_RADIUS).times(2)), gate);
		this.gate = gate;
		this.bitSize = bitSize;
		this.portType = portType;
		this.state = new boolean[bitSize];
		this.wires = new ArrayList<Wire>();
	}

	public void addWire(Wire wr) {
		this.wires.add(wr);
	}

	@Override
	public boolean intersects(Point p) {
		return p.distanceTo(getBoundBox().getCenter()) <= PORT_RADIUS * 1.2f;
	}

	public void linkInputWire(Wire wire) {
		if (portType == SINGLE_INPUT && wires.size() == 1) {
			wires.get(0).kill();
		}

		boolean canAdd = true;
		for (Wire wire1 : wires) {
			if (wire1.getInput().equals(wire.getInput())) {
				canAdd = false;
				break;
			}
		}

		if (wire.getInput().equals(wire.getOutput())) {
			canAdd = false;
		}

		if (canAdd) {
			wires.add(wire);
			wire.setOutput(this);
		} else {
			wire.kill();
		}
	}

	public boolean canConnect(Wire wire) {
		boolean existsAlready = false;
		for (Wire currentWire : wires) {
			if (currentWire.getInput().equals(wire.getInput())) {
				existsAlready = true;
				break;
			}
		}

		return !existsAlready
				&& (((portType == SINGLE_INPUT) || (portType == MULTIPLE_INPUT) || (portType == INPUT_OUTPUT)));
	}

	@Override
	public void render(GraphicsPlus g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillCircle(getBoundBox().getCenter(), PORT_RADIUS);

		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(8));
		g.drawCircle(getBoundBox().getCenter(), PORT_RADIUS - 2);
		g.setStroke(new BasicStroke(6));

		if (bitSize == 1) {
			if (state[0]) {
				g.setColor(new Color(1f, 0f, 0f, 0.9f));
			} else {
				g.setColor(new Color(0f, 0f, 1f, 0.9f));
			}
		} else {
			float red = 0;
			for (int i = 0; i < bitSize; i++) {
				if (state[i])
					red++;
			}

			g.setColor(new Color(red / bitSize, 0f, 1 - red / bitSize, 0.9f));
		}

		g.fillCircle(getBoundBox().getCenter(), 6);

		if (bitSize > 1) {
			g.setColor(Color.GREEN);
			g.drawString("" + bitSize, getBoundBox().getCenter().add(new Point(15, 15)));
		}
	}

	@Override
	public void update() {
		if (isMultInput()) {
			this.state = new boolean[bitSize];
			for (Wire wire : wires) {
				if (wire.getOutput().equals(this)) {
					for (int i = 0; i < bitSize; i++) {
						boolean[] wireOutput = Util.packArray(wire.getInput().getPortState(), bitSize);
						this.state = Util.OR(state, wireOutput);
					}
				}
			}
		}

		if (isOutputPort()) {
			for (Wire wire : wires) {
				wire.update();
			}
		}
	}

	public int getPortType() {
		return portType;
	}

	public Gate getGate() {
		return gate;
	}

	public Point getAbsoluteCenter() {
		return getGate().getLocation().add(getBoundBox().getCenter());
	}

	public void removeWire(Wire wire) {
		this.wires.remove(wire);
	}

	public int getBitSize() {
		return bitSize;
	}

	public boolean isOutputPort() {
		return (portType == OUTPUT) || (portType == INPUT_OUTPUT);
	}

	public boolean[] getPortState() {
		return state;
	}

	public void setPortState(boolean[] state) {
		this.state = Util.packArray(state, bitSize);
	}

	public boolean isMultInput() {
		return (portType == MULTIPLE_INPUT) || (portType == INPUT_OUTPUT);
	}

	public boolean isInputPort() {
		return (portType == SINGLE_INPUT) || (portType == MULTIPLE_INPUT) || (portType == INPUT_OUTPUT);
	}

	public void kill() {
		for (int i = 0; i < wires.size(); i++) {
			Wire wire = wires.get(i);
			wire.kill();
			i--;
		}
	}
}
