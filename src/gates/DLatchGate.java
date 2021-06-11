package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class DLatchGate extends Gate {
	private static final Color DColor = new Color(0.5f,0.7f,0.8f,1.0f); 

	public static Color getDColor() {
		return DColor;
	}
	
	public DLatchGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position,new Point(180,200)), DColor, size, "DLATCH");
		
		Label lbl = new Label(this, new Rectangle(0,0,getWidth(),getHeight()),"");
		lbl.setBackground(DColor);
		lbl.setFontSize(40);
		
		if (size == 1) {
			lbl.setText("DL");
		} else {
			lbl.setText(size + " DL");
		}
		
		this.addComponent(lbl);
		
		this.addPort(new Port(this, new Point(90,30), Port.OUTPUT, getBitSize()));
		
		Label lbl1 = new Label(this, new Rectangle(45,130,30,20),"D");
		lbl1.setBackground(DColor);
		lbl1.setFontSize(15);
		lbl1.setTextColor(Color.WHITE);
		this.addComponent(lbl1);
		
		Label lbl2 = new Label(this, new Rectangle(135,130,30,20),"E");
		lbl2.setBackground(DColor);
		lbl2.setFontSize(15);
		lbl2.setTextColor(Color.WHITE);
		this.addComponent(lbl2);
		
		this.addPort(new Port(this,new Point(45,170), Port.SINGLE_INPUT, getBitSize()));
		this.addPort(new Port(this,new Point(135,170), Port.SINGLE_INPUT, 1));
		
		this.state = new boolean[size];
	}

	private boolean[] state;
	
	@Override
	public boolean[] getOutputState(int index) {
		boolean[] A = getInputStates(1);
		boolean[] B = getInputStates(2);
		
		if (B[0]) {
			state = A;
		}
		
		return state;
	}
	
	protected void setState(boolean[] state) {
		this.state = Util.packArray(state, getBitSize());
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		DLatchGate gt = new DLatchGate(panel,location, size);
		gt.setState(Util.toBinaryArray(other));
		return gt;
	}
	
	protected String saveContents() {
		return Util.toString(state)+"";
	}
}
