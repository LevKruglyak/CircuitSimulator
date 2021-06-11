package circuits;

import java.util.ArrayList;
import java.util.List;

import engine.GameState;
import gates.Gate;
import gui.Component;
import gui.Panel;
import util.Point;

public class DefaultState extends InputState {

	public DefaultState(GameState state, CircuitPanel panel) {
		super(state, panel, "DEFAULT_STATE");
	}

	@Override
	public void mouseMoved(Point p) {
		// TODO Auto-generated method stub

	}

	public void deselectAll() {
		for (int i = 0; i < getPanel().getComponents().size(); i++) {
			Component comp = getPanel().getComponents().get(i);
			comp.setSelected(false);
		}
	}
	
	@Override
	public void mousePressed(Point p) {
		Point t = getPanel().transformToLocalCoordinates(p);

		List<Component> selected = new ArrayList<Component>();
		if (!getPanel().isRunning()) {
			for (int i = 0; i < getPanel().getComponents().size(); i++) {
				Component comp = getPanel().getComponents().get(i);

				if (comp instanceof Panel) {
					Component port = ((Panel) comp).getSelected(t.subtract(comp.getLocation()));
					if (port instanceof Port) {
						if (((Port) port).isOutputPort()) {
							getPanel().switchState(new WireState(getState(), getPanel(), (Port) port));
							deselectAll();
							return;
						}
					}
				}

				if (comp.intersects(t) && comp.isSelectable()) {
					comp.setSelected(true);
					selected.add(comp);
				} else {
					// VK_SHIFT
					if (!getState().keyPressed(16)) {
						comp.setSelected(false);
					} else {
						if (comp.isSelected()) {
							selected.add(comp);
						}
					}
				}
			}
		}
		
		if (selected.size() == 0) {
			// VK_G
			if (getState().keyPressed(71)) {
				if (!getPanel().isRunning())
				getPanel().switchState(new SelectionState(getState(), getPanel(), p));
			} else {
				getPanel().switchState(new MoveState(getState(), getPanel(), p));
			}
		} else {
			getPanel().switchState(new DragState(getState(), getPanel(), p, selected));
		}
	}

	@Override
	public void mouseReleased(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void zoom(float size) {
		getPanel().updateZoom(size);
	}

	@Override
	public void shift(Point del) {
		getPanel().updateShift(del);
	}

	@Override
	public void mouseWheelMoved(int size) {
		getPanel().zoom(size);
	}

	@Override
	public void delete() {
		List<Component> toRemove = new ArrayList<Component>(); 
		
		for (int i = 0; i < getPanel().getComponents().size(); i++) {
			Component comp = getPanel().getComponents().get(i);
			if (comp.isSelected() && (comp instanceof Gate || comp instanceof Wire)) {
				toRemove.add(comp);
			}
		}
		
		for (Component comp : toRemove) {
			if (comp instanceof Wire) {
				((Wire) comp).kill();
			}
			
			if (comp instanceof Gate) {
				((Gate) comp).kill();
			}
		}
	}

	@Override
	public void escape() {
		// TODO Auto-generated method stub
		
	}
}
