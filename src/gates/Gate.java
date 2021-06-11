package gates;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Panel;
import util.Point;
import util.Rectangle;
import util.Util;

public abstract class Gate extends Panel {

	private CircuitPanel panel;

	private List<Port> ports;

	private int bitSize;

	private boolean needsDisable;
	
	public Gate(CircuitPanel panel, Rectangle boundBox, Color color, int bitSize, String type) {
		super(boundBox, panel);
		this.panel = panel;
		this.setBackground(color);
		this.setBordered(true);
		this.setBorderThickness(5);
		this.setBorder(Color.DARK_GRAY);
		this.bitSize = bitSize;
		this.setSelectable(true);
		this.setMoveable(true);
		this.ports = new ArrayList<Port>();
		this.type = type;
		this.setNeedsDisable(true);
	}

	public int getBitSize() {
		return bitSize;
	}

	public boolean[] getInputStates(int index) {
		return Util.packArray(ports.get(index).getPortState(), ports.get(index).getBitSize());
	}
	
//	public List<boolean[]> getInputStates() {
//		List<boolean[]> inputStates = new ArrayList<boolean[]>();
//		for (Port port : ports) {
//			if (port.isInputPort()) {
//				inputStates.add(Util.packArray(port.getPortState(), port.getBitSize()));
//			}
//		}
//
//		return inputStates;
//	}

	public void kill() {
		for (Port port : ports) {
			port.kill();
		}

		this.getPanel().removeComponent(this);
	}

	public boolean[] getOutputState(int index) {
		return Util.packArray(new boolean[ports.get(index).getBitSize()], ports.get(index).getBitSize());
	}

	@Override
	public void update() {
		for (int i = 0; i < ports.size(); i++) {
			Port port = ports.get(i);
			if (port.isOutputPort()) {
				port.setPortState(getOutputState(i));
			}

			port.update();
		}
	}

	public void addPort(Port port) {
		this.ports.add(port);
		this.addComponent(port);
	}

	public CircuitPanel getPanel() {
		return panel;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public void setPanel(CircuitPanel panel) {
		this.panel = panel;
	}

	public static Gate loadGate(CircuitPanel panel, String str) {
		String[] data = str.split(",");

		Point location = new Point(data[0]);
		String type = data[1];
		String other = "";
		int size = Integer.parseInt(data[2]);
		for (int i = 3; i < data.length; i++) {
			other += data[i];
			if (!(i == data.length-1)) {
				other += ",";
			}
		}

		switch (type) {
		case "ADDER":
			return ADDERGate.loadGate(panel, location, other, size);
		case "AND":
			return ANDGate.loadGate(panel, location, other, size);
		case "CLOCK":
			return ClockGate.loadGate(panel, location, other, size);
		case "COMBINE":
			return CombineGate.loadGate(panel, location, other, size);
		case "COUNTER":
			return CounterGate.loadGate(panel, location, other, size);
		case "DECODER":
			return DecoderGate.loadGate(panel, location, other, size);
		case "DFF":
			return DFlipFlopGate.loadGate(panel, location, other, size);
		case "DLATCH":
			return DLatchGate.loadGate(panel, location, other, size);
		case "EQUAL":
			return EqualGate.loadGate(panel, location, other, size);
		case "EXTEND":
			return ExtendGate.loadGate(panel, location, other, size);
		case "LED":
			return LEDGate.loadGate(panel, location, other, size);
		case "MULTIPLEXER":
			return MultiplexerGate.loadGate(panel, location, other, size);
		case "NAND":
			return NANDGate.loadGate(panel, location, other, size);
		case "NOR":
			return NORGate.loadGate(panel, location, other, size);
		case "NOT":
			return NOTGate.loadGate(panel, location, other, size);
		case "OR":
			return ORGate.loadGate(panel, location, other, size);
		case "PULSER":
			return PulserGate.loadGate(panel, location, other, size);
		case "REGISTERARRAY":
			return RegisterArrayGate.loadGate(panel, location, other, size);
		case "REROUTE":
			return RerouteGate.loadGate(panel, location, other, size);
		case "SEGMENTDISPLAY":
			return SegmentDisplayGate.loadGate(panel, location, other, size);
		case "SEGMENTINPUT":
			return SegmentInputGate.loadGate(panel, location, other, size);
		case "SPLIT":
			return SplitGate.loadGate(panel, location, other, size);
		case "SRLATCH":
			return SRLatchGate.loadGate(panel, location, other, size);
		case "TABLE":
			return TableGate.loadGate(panel, location, other, size);
		case "TOGGLE":
			return ToggleGate.loadGate(panel, location, other, size);
		case "XNOR":
			return XNORGate.loadGate(panel, location, other, size);
		case "XOR":
			return XORGate.loadGate(panel, location, other, size);
		}

		return null;
	}

	private String type;

	protected String saveContents() {
		return "";
	}

	public String save() {
		return "g:" + getLocation().toString() + "," + type + "," + bitSize + "," + saveContents();
	}

	public boolean isNeedsDisable() {
		return needsDisable;
	}

	public void setNeedsDisable(boolean needsDisable) {
		this.needsDisable = needsDisable;
	}
}
