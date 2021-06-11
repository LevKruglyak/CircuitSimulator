package gates;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import circuits.CircuitPanel;
import circuits.Port;
import gui.Button;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;
import util.Util;

public class SegmentInputGate extends Gate {
	public SegmentInputGate(CircuitPanel panel, Point position, int bitSize) {
		super(panel, null, Color.DARK_GRAY, bitSize, "SEGMENTINPUT");
		numSegments = Util.numDigits((int)(Math.pow(2, bitSize)-1), 10);
		
		this.setBordered(true);
		this.setBorder(Color.GRAY);
		this.setBoundBox(new Rectangle(position, new Point(130*numSegments + 30,330)));
		this.addPort(new Port(this, new Point(getBoundBox().getWidth()/2f, 30), Port.OUTPUT, bitSize));
		
		for (int i = 0; i < numSegments; i++) {
			Button inc = new Button(this, new Rectangle(130*i + 33, 70, 100, 30), "");
			inc.setBackground(Color.LIGHT_GRAY);
			inc.setEnabled(true);
			int tempI = i;
			inc.addEventListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int j = numSegments - 1-tempI;
					int mult = 1;
					if (Util.getDigit(num,10,j) == 9) {
						mult = -9;
					}
					num += mult *(int) (Math.pow(10, j));
					
					if (num > max) {
						num = max;
					}
					recalculate();
				}
			});
			this.addComponent(inc);
			
			Button dec = new Button(this, new Rectangle(130*i + 33, 280, 100, 30), "");
			dec.setBackground(Color.LIGHT_GRAY);
			dec.setEnabled(true);
			dec.addEventListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int j = numSegments - 1-tempI;
					int mult = 1;
					if (Util.getDigit(num,10,j) == 0) {
						mult = -9;
					}
					num -= mult *(int) (Math.pow(10, j));
					
					if (num > max) {
						num = max;
					}
					recalculate();
				}
			});
			this.addComponent(dec);
		}
		
		this.digits = new int[numSegments];
		this.max = (long)(Math.pow(2, bitSize))-1;
		this.binDigits = new boolean[bitSize];
	}

	private int numSegments;
	private long num;
	private long max;
	private int[] digits;
	private boolean[] binDigits;
	
	public void recalculate() {
		this.binDigits = Util.toBinary(num);
		this.digits = Util.packArray(Util.getDigits(num, 10), numSegments);
	}
	
	public boolean[] getOutputState(int index) {
		return binDigits;
	}
	
	public void render(GraphicsPlus g) {
		super.render(g);
		
		g.translate(getLocation());
		for (int i = 0; i < numSegments; i++) {
			g.drawSegment(new Point(130*i + 30, 100), digits[numSegments -1-i]);
		}
		g.translate(getLocation().times(-1));
	}
	
	protected void setNum(int num) {
		this.num = num;
	}
	
	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		SegmentInputGate gt = new SegmentInputGate(panel,location, size);
		gt.setNum(Integer.parseInt(other));
		gt.recalculate();
		return gt;
	}
	
	protected String saveContents() {
		return num+"";
	}
}
