package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class SRLatchGate extends Gate {
	private static final Color SRColor = new Color(0.7f, 0.5f, 0.45f); 

	public static Color getSRColor() {
		return SRColor;
	}
	
	public SRLatchGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position,new Point(180,200)), SRColor, size, "SRLATCH");
		
		Label lbl = new Label(this, new Rectangle(0,0,getWidth(),getHeight()),"");
		lbl.setBackground(SRColor);
		lbl.setFontSize(40);
		
		if (size == 1) {
			lbl.setText("SRL");
		} else {
			lbl.setText(size + " SRL");
		}
		
		this.addComponent(lbl);
		
		Label lbl1 = new Label(this, new Rectangle(45,130,30,20),"S");
		lbl1.setBackground(SRColor);
		lbl1.setFontSize(15);
		lbl1.setTextColor(Color.WHITE);
		this.addComponent(lbl1);
		
		Label lbl2 = new Label(this, new Rectangle(135,130,30,20),"R");
		lbl2.setBackground(SRColor);
		lbl2.setFontSize(15);
		lbl2.setTextColor(Color.WHITE);
		this.addComponent(lbl2);
		
		this.addPort(new Port(this, new Point(90,30), Port.OUTPUT, getBitSize()));
		this.addPort(new Port(this,new Point(45,170), Port.SINGLE_INPUT, getBitSize()));
		this.addPort(new Port(this,new Point(135,170), Port.SINGLE_INPUT, getBitSize()));
		
		this.state = new boolean[size];
	}

	private boolean[] state;
	
	@Override
	public boolean[] getOutputState(int index) {
		boolean[] S = getInputStates(1);
		boolean[] R = getInputStates(2);
		
		for (int i = 0; i < getBitSize(); i++) {
			if (S[i]) {
				state[i] = true;
			}
			
			if (R[i]) {
				state[i] = false;
			}
		}
		
		return state;
	}
	
	protected void setState(boolean[] state) {
		this.state = Util.packArray(state, getBitSize());
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		SRLatchGate gt = new SRLatchGate(panel,location, size);
		gt.setState(Util.toBinaryArray(other));
		return gt;
	}
	
	protected String saveContents() {
		return Util.toString(state)+"";
	}
}
