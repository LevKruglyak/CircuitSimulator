package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class ADDERGate extends Gate {
private static final Color ADDERColor = new Color(0.8f,0.4f,0.4f,1.0f); 
	
	public static Color getADDERColor() {
		return ADDERColor;
	}

	public ADDERGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position,new Point(240,220)), ADDERColor, size, "ADDER");
		
		Label lbl = new Label(this, new Rectangle(70,0,getWidth()-140,getHeight()),"");
		lbl.setBackground(ADDERColor);
		lbl.setFontSize(40);
		
		if (size == 1) {
			lbl.setText("ADD");
		} else {
			lbl.setText(size + " ADD");
		}
		this.addComponent(lbl);
		
		super.addPort(new Port(this, new Point(120,30), Port.OUTPUT, size));
		super.addPort(new Port(this, new Point(30,110), Port.OUTPUT, 1));
		
		super.addPort(new Port(this, new Point(80,190), Port.SINGLE_INPUT, size));
		super.addPort(new Port(this, new Point(150,190), Port.SINGLE_INPUT, size));
		
		super.addPort(new Port(this, new Point(210,110), Port.SINGLE_INPUT, 1));
	}
	
	public boolean[] getOutputState(int index) {
		boolean[] A = getInputStates(2);
		boolean[] B = getInputStates(3);
		
		boolean[] cin = getInputStates(4);
		
		long sum = Util.toDecimal(A) + Util.toDecimal(B) + Util.toDecimal(cin);
		boolean[] C = Util.packArray(Util.toBinary(sum), getBitSize()+1);
		
		if (index == 0) {
			return C;
		} else {
			return new boolean[] { C[getBitSize()] };
		}
	}

	public static Gate loadGate(CircuitPanel panel, Point location, String other, int bitSize) {
		return new ADDERGate(panel,location, bitSize);
	}
}
