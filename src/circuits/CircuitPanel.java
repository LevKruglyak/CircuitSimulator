package circuits;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import gates.Gate;
import gui.Component;
import gui.ComponentCarrier;
import gui.Panel;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;

public class CircuitPanel extends Panel {
	private final static float ZOOM_INTENSITY = 0.2f;
	private final static float MAX_ZOOM = 10;
	private final static float MIN_ZOOM = -20;

	private float scaleCount;
	private float scale;

	private Point shift;

	private BufferedImage image;
	private InputState currentState;

	private CircuitRunningState state;

	private boolean running;

	private long ticks;

	public long getTicks() {
		return ticks;
	}

	public void run() {
		this.running = true;
		this.setBorder(Color.GREEN);

		for (int i = 0; i < getComponents().size(); i++) {
			Component comp = getComponents().get(i);
			comp.setEnabled(true);
		}
		this.ticks = 0;
	}

	public void stop() {
		this.running = false;
		this.setBorder(Color.WHITE);

		for (int i = 0; i < getComponents().size(); i++) {
			Component comp = getComponents().get(i);
			if (comp instanceof Gate) {
				if (((Gate) comp).isNeedsDisable()) {
					comp.setEnabled(false);
				}
			} else {
				comp.setEnabled(false);
			}

		}
	}

	public CircuitRunningState getState() {
		return state;
	}

	public CircuitPanel(CircuitRunningState state, ComponentCarrier parent, Rectangle boundBox) {
		super(boundBox, parent);
		this.state = state;

		setupVariables();
	}

	private void setupVariables() {
		this.image = (new BufferedImage((int) getWidth(), (int) getHeight(), BufferedImage.TYPE_4BYTE_ABGR));
		this.shift = new Point(0, 0);
		this.scaleCount = 0;
		this.scale = toScale(scaleCount);
		this.setBackground(Color.BLACK);
		this.switchState(new DefaultState(getState(), this));
		this.splitIndex = 0;
	}

	public CircuitPanel(CircuitRunningState state, ComponentCarrier parent, Rectangle boundBox, String str) {
		super(boundBox, parent);
		this.state = state;
		setupVariables();

		if (!str.equals("")) {
			String[] gw = str.split("=");
			String g1 = gw[0];
			String[] g2 = g1.split("g:");
			List<Gate> gates = new ArrayList<Gate>();

			for (String g : g2) {
				if (!g.equals("")) {
					Gate gate = Gate.loadGate(this, g);
					gates.add(gate);
					addComponent(gate);
				}
			}

			if (gw.length != 1) {
				for (String w : gw[1].split("w:")) {
					if (!w.equals("")) {
						String input = w.split("-")[0];
						String output = w.split("-")[1];
						String size = w.split("-")[2];

						Gate ga1 = gates.get(Integer.parseInt(input.split(",")[0]));
						Port po1 = ga1.getPorts().get(Integer.parseInt(input.split(",")[1]));

						Gate ga2 = gates.get(Integer.parseInt(output.split(",")[0]));
						Port po2 = ga2.getPorts().get(Integer.parseInt(output.split(",")[1]));

						Wire w1 = new Wire(this, po1, po2, Integer.parseInt(size));
						po1.addWire(w1);
						po2.addWire(w1);

						addComponent(w1);
					}
				}
			}
		}

		this.switchState(new DefaultState(state, this));
	}

	public void switchState(InputState state) {
		this.currentState = state;
	}

	private int splitIndex;

	@Override
	public void addComponent(Component comp) {
		if (comp instanceof Gate) {
			super.addComponent(splitIndex, comp);
			splitIndex++;
		} else {
			super.addComponent(comp);
		}
		if (comp instanceof Gate) {
			if (((Gate) comp).isNeedsDisable()) {
				comp.setEnabled(running);
			}
		} else {
			comp.setEnabled(running);
		}
	}

	@Override
	public void removeComponent(Component comp) {
		if (comp instanceof Gate) {
			splitIndex--;
		}
		super.removeComponent(comp);
	}

	@Override
	public void update() {
		if (isRunning()) {
			ticks++;
		}
		
		for (int i = 0; i < getComponents().size(); i++) {
			Component comp = getComponents().get(i);
			comp.update();
		}
	}

	public void updateImage() {
		this.image.flush();
		GraphicsPlus g = new GraphicsPlus(image.createGraphics());

		Point center = getBoundBox().getCenter();
		Stroke str = g.getStroke();
		AffineTransform def = g.getAffineTransform();

		Rectangle bounds = new Rectangle(new Point(0, 0), getBoundBox().getBounds());

		g.setColor(getBackground());
		g.fillRect(bounds);

		g.translate(center);
		g.scale(scale);
		g.translate(shift);

		AffineTransform curr = g.getAffineTransform();
		for (int i = 0; i < getComponents().size(); i++) {
			Component comp = getComponents().get(i);
			comp.render(g);
			g.setAffineTransform(curr);
		}

		g.setAffineTransform(def);

		this.currentState.render(g);

		g.setStroke(new BasicStroke(2 * getBorderThickness()));
		g.setColor(getBorder());
		g.drawRect(bounds);
		g.setStroke(str);

		g.getGraphics().dispose();
	}

	public Point transformToLocalCoordinates(Point p) {
		Point v1 = new Point(p).add(new Point(getBoundBox().getCenter().times(-1)));
		Point v2 = v1.subtract(new Point(getLocation()));
		Point v3 = v2.times(1f / scale).subtract(shift);

		return v3;
	}

	@Override
	public void mouseDragged(Point p) {
		super.mouseDragged(p);
		this.currentState.mouseDragged(p);
	}

	@Override
	public void mouseMoved(Point p) {
		super.mouseMoved(p);
		this.currentState.mouseMoved(p);
	}

	@Override
	public void mousePressed(Point p) {
		super.mousePressed(p);
		this.currentState.mousePressed(p);
	}

	@Override
	public void mouseReleased(Point p) {
		super.mouseReleased(p);
		this.currentState.mouseReleased(p);
	}

	@Override
	public void render(GraphicsPlus g) {
		updateImage();
		g.drawImage(image, getBoundBox());
	}

	public void zoom(float size) {
		this.currentState.zoom(size);
	}

	public void shift(Point del) {
		this.currentState.shift(del);
	}

	protected void updateZoom(float size) {
		scaleCount += size;
		scaleCount = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, scaleCount));
		scale = toScale(scaleCount);
	}

	protected void updateShift(Point del) {
		this.shift = shift.add(del.times(1f / scale));
	}

	private static float toScale(float scaleCount) {
		return (float) Math.exp(ZOOM_INTENSITY * scaleCount);
	}

	public Point getShift() {
		return this.shift;
	}

	public void setShift(Point shift) {
		this.shift = shift;
		;
	}

	public float getScale() {
		return scale;
	}

	public void mouseWheelMoved(int wheelRotation) {
		this.currentState.mouseWheelMoved(wheelRotation);
		;
	}

	public boolean isRunning() {
		return running;
	}

	public void deselectableAll() {
		for (int i = 0; i < getComponents().size(); i++) {
			getComponents().get(i).setSelected(false);
			getComponents().get(i).setSelectable(false);
		}
	}

	public void selectableAll() {
		for (int i = 0; i < getComponents().size(); i++) {
			getComponents().get(i).setSelectable(true);
		}
	}

	public void toggleRun() {
		if (isRunning()) {
			stop();
		} else {
			run();
		}
	}

	public void deselectAll() {
		for (int i = 0; i < getComponents().size(); i++) {
			getComponents().get(i).setSelected(false);
		}
	}

	public InputState getInputState() {
		return this.currentState;
	}

	public void delete() {
		this.currentState.delete();
	}

	public void escape() {
		this.currentState.escape();
	}

	public String save() {
		String str = "";

		List<Gate> gates = new ArrayList<Gate>();
		List<Wire> wires = new ArrayList<Wire>();

		for (Component comp : getComponents()) {
			if (comp instanceof Gate) {
				gates.add((Gate) comp);
			}

			if (comp instanceof Wire) {
				wires.add((Wire) comp);
			}
		}
		for (Gate gate : gates) {
			str += gate.save();
		}
		str += "=";
		for (Wire wire : wires) {
			Gate g1 = wire.getInput().getGate();
			Gate g2 = wire.getOutput().getGate();

			str += "w:" + gates.indexOf(g1) + "," + g1.getPorts().indexOf(wire.getInput()) + "-" + gates.indexOf(g2)
					+ "," + g2.getPorts().indexOf(wire.getOutput()) + "-" + wire.getBitSize();
		}

		return str;
	}
}
