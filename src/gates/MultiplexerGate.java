package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Label;
import util.Point;
import util.Rectangle;

public class MultiplexerGate extends Gate {
	
	public MultiplexerGate(CircuitPanel panel, Point position, int size) {
		super(panel, new Rectangle(position,new Point(200,200)), Color.DARK_GRAY, size, "MULTIPLEXER");
		
		Label lbl = new Label(this, new Rectangle(0,0,getWidth(),getHeight()),"");
		lbl.setBackground(Color.DARK_GRAY);
		lbl.setFontSize(40);
		
		if (size == 1) {
			lbl.setText("MUL");
		} else {
			lbl.setText(size + " MUL");
		}
		
		this.addComponent(lbl);
		
		this.addPort(new Port(this, new Point(90,30), Port.OUTPUT, getBitSize()));
		this.addPort(new Port(this,new Point(45,170), Port.SINGLE_INPUT, 1));
		this.addPort(new Port(this,new Point(100,170), Port.SINGLE_INPUT, getBitSize()));
		this.addPort(new Port(this,new Point(155,170), Port.SINGLE_INPUT, getBitSize()));
		this.setBordered(true);
		this.setBorder(Color.GRAY);
	}

	@Override
	public boolean[] getOutputState(int index) {
		boolean[] A = getInputStates(2);
		boolean[] B = getInputStates(3);
		
		return (getInputStates(1)[0]) ? A : B;
	}

	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new MultiplexerGate(panel,location, size);
	}
}
