package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class ORGate extends Gate {

	private static final Color ORColor = new Color(0.7f,0.9f,0.7f,1.0f);

	public static Color getORColor() {
		return ORColor;
	}
	
	public ORGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position,new Point(180,200)), ORColor, size, "OR");
		
		Label lbl = new Label(this, new Rectangle(0,0,getWidth(),getHeight()),"");
		lbl.setBackground(ORColor);
		lbl.setFontSize(40);
		
		if (size == 1) {
			lbl.setText("OR");
		} else {
			lbl.setText(size + " OR");
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
		
		return Util.OR(A, B);
	}

	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new ORGate(panel,location, size);
	}
}