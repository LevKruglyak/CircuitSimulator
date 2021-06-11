package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class XORGate extends Gate {

	private static final Color XORColor = new Color(0.9f,0.9f,0.4f,1.0f); 

	public static Color getXORColor() {
		return XORColor;
	}
	
	public XORGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position,new Point(180,200)), XORColor, size, "XOR");
		
		Label lbl = new Label(this, new Rectangle(0,0,getWidth(),getHeight()),"");
		lbl.setBackground(XORColor);
		lbl.setFontSize(40);
		
		if (size == 1) {
			lbl.setText("XOR");
		} else {
			lbl.setText(size + " XOR");
		}
		
		this.addComponent(lbl);
		
		this.addPort(new Port(this, new Point(90,30), Port.OUTPUT, getBitSize()));
		this.addPort(new Port(this,new Point(45,170), Port.SINGLE_INPUT, getBitSize()));
		this.addPort(new Port(this,new Point(135,170), Port.SINGLE_INPUT, getBitSize()));
	}

	@Override
	public boolean[] getOutputState(int index) {
		boolean[] A = getInputStates(1);
		boolean[] B = getInputStates(2);
		
		return Util.XOR(A, B);
	}

	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new XORGate(panel,location, size);
	}
}
