package engine;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import circuits.CircuitRunningState;
import util.GraphicsPlus;
import util.Rectangle;

public class Game implements MouseListener, MouseMotionListener,MouseWheelListener,KeyListener {

	private GameLoop loop;
	private long ticks;
	private GameState state;

	public Game(GameLoop loop) {
		this.loop = loop;
		
		//TEMPORARY
		switchState(new CircuitRunningState(this));
	}
	
	public Component getFrame() {
		return loop.getFrame();
	}
	
	public void update() {
		this.state.update();
		ticks++;
	}
	
	public void render(GraphicsPlus g) {
		this.state.render(g);
	}
	
	public void switchState(GameState state) {
		this.state = state;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		this.state.keyPressed(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		this.state.keyReleased(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		this.state.keyTyped(arg0);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		this.state.mouseWheelMoved(arg0);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		this.state.mouseDragged(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.state.mouseMoved(arg0);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.state.mouseClicked(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.state.mouseEntered(arg0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.state.mouseExited(arg0);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.state.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.state.mouseReleased(arg0);
	}
	
	public GameState getState() {
		return state;
	}
	
	public GameLoop getLoop() {
		return loop;
	}

	public long getTicks() {
		return ticks;
	}

	public Rectangle getBoundBox() {
		return loop.getBoundBox();
	}

}
