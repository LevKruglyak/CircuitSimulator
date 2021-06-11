package circuits;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import engine.GameState;
import gui.Component;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;

public class SelectionState extends InputState {

	private Rectangle selectionBox;

	public SelectionState(GameState state, CircuitPanel panel, Point mouseLocation) {
		super(state, panel, "SELECTION_STATE");

		this.selectionBox = new Rectangle(mouseLocation.subtract(getPanel().getLocation()), new Point(0, 0));
	}

	@Override
	public void mouseMoved(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Point p) {
		List<Component> comps = getPanel().getComponents();
		List<Component> selected = new ArrayList<Component>();
		
		Point location = getPanel().transformToLocalCoordinates(selectionBox.getLocation().add(getPanel().getLocation()));
		Rectangle transformedRectangle = new Rectangle(location, selectionBox.getBounds().times(1f/getPanel().getScale()));

		for (int i = 0; i < comps.size(); i++) {
			Component comp = comps.get(i);

			if (comp.getBoundBox().overlaps(transformedRectangle) && comp.isSelectable()) {
				selected.add(comp);

				comp.setSelected(true);
			}
		}

		getPanel().switchState(new DefaultState(getState(), getPanel()));
	}

	@Override
	public void mouseDragged(Point p) {
		selectionBox.setDimensions(p.subtract(selectionBox.getLocation().add(getPanel().getLocation())));
	}

	@Override
	public void mouseWheelMoved(int size) {
		// TODO Auto-generated method stub

	}

	public void render(GraphicsPlus g) {
		g.setStroke(new BasicStroke(1));
		g.setColor(Color.GREEN);

		if (selectionBox.getWidth() > 0 || selectionBox.getHeight() > 0) {
			g.drawRect(selectionBox);

			g.setColor(new Color(0f, 1f, 0f, 0.2f));
			g.fillRect(selectionBox);
		}
	}

	@Override
	public void zoom(float size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shift(Point del) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void escape() {
		getPanel().switchState(new DefaultState(getState(), getPanel()));
	}

}
