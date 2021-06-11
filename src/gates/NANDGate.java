package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class NANDGate extends Gate {
	private static final Color NANDColor = new Color(1f,0.8f,0.6f,1.0f); 

	public static Color getNANDColor() {
		return NANDColor;
	}
	
	public NANDGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position,new Point(180,200)), NANDColor, size, "NAND");
		
		Label lbl = new Label(this, new Rectangle(30,0,getWidth()-60,getHeight()),"");
		lbl.setBackground(NANDColor);
		lbl.setFontSize(40);
		
		if (size == 1) {
			lbl.setText("NAND");
		} else {
			lbl.setText(size + " NAND");
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
		
		return Util.NOT(Util.AND(A, B));
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new NANDGate(panel,location, size);
	}
}
