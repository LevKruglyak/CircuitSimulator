package gates;

import java.awt.Color;

import circuits.CircuitPanel;
import circuits.Port;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;
import util.Util;

public class SegmentDisplayGate extends Gate {

	public SegmentDisplayGate(CircuitPanel panel, Point position, int bitSize) {
		super(panel, null, Color.DARK_GRAY, bitSize, "SEGMENTDISPLAY");
		numSegments = Util.numDigits((int)(Math.pow(2, bitSize)-1), 10);
		
		this.setBordered(true);
		this.setBorder(Color.GRAY);
		this.setBoundBox(new Rectangle(position, new Point(130*numSegments + 30,270)));
		this.addPort(new Port(this, new Point(getBoundBox().getWidth()/2f, 240), Port.SINGLE_INPUT, bitSize));
		
		this.digits = new int[numSegments];
	}

	private int numSegments;
	private int[] digits;
	
	public void update() {
		long num = Util.toDecimal(getInputStates(0));
		digits = Util.packArray(Util.getDigits(num, 10), numSegments);
	}
	
	public void render(GraphicsPlus g) {
		super.render(g);
		
		g.translate(getLocation());
		for (int i = 0; i < numSegments; i++) {
			g.drawSegment(new Point(130*i + 30, 10), digits[numSegments -1-i]);
		}
		g.translate(getLocation().times(-1));
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		return new SegmentDisplayGate(panel,location, size);
	}
}
