package circuits;

import engine.GameState;
import util.GraphicsPlus;
import util.Point;

public abstract class InputState {
	private CircuitPanel panel;
	private GameState state;
	private String type;
	
	public InputState(GameState state, CircuitPanel panel, String type) {
		this.panel = panel;
		this.state = state;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public CircuitPanel getPanel() {
		return panel;
	}
	
	public abstract void mouseMoved(Point p);
	
	public abstract void mousePressed(Point p);
	
	public abstract void mouseReleased(Point p);
	
	public abstract void mouseDragged(Point p);
	
	public abstract void mouseWheelMoved(int size);
	
	public abstract void zoom(float size);
	
	public abstract void shift(Point del);
	
	public void render(GraphicsPlus g) {
		
	}

	public GameState getState() {
		return state;
	}

	public abstract void escape();
	
	public abstract void delete();
}
