package engine;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import gui.Form;
import util.GraphicsPlus;
import util.Point;

public abstract class GameState implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	private List<Form> forms;
	private Game game;

	public GameState(Game game) {
		this.game = game;
		this.mouseLocation = new Point(0, 0);
		this.forms = new ArrayList<Form>(3);
		
		pressedKeys = new ArrayList<Integer>();
		typedKeys = new ArrayList<Integer>();
	}

	public void render(GraphicsPlus g) {
		for (int i = 0; i < forms.size(); i++) {
			Form form = forms.get(i);
			if (!form.equals(getFocusedForm())) {
				form.render(g);
			}
		}

		if (forms.size() > 1) {
			g.setColor(new Color(0f, 0f, 0f, 0.5f));
			g.fillRect(game.getBoundBox());
		}

		getFocusedForm().render(g);
	}

	private List<Integer> pressedKeys;
	private List<Integer> typedKeys;

	@Override
	public void keyReleased(KeyEvent e) {
		Integer code = new Integer(e.getKeyCode());
		pressedKeys.remove(code);
		
		
		if (!typedKeys.contains(code)) {
			typedKeys.add(code);
		}
	}

	public boolean keyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}
	
	public boolean keyTyped(int keyCode) {
		return typedKeys.contains(keyCode);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Integer code = new Integer(e.getKeyCode());
		if (!pressedKeys.contains(code)) {
			pressedKeys.add(code);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void update() {
		typedKeys.clear();
	}

	public void addForm(Form form) {
		for (int i = 0; i < forms.size(); i++) {
			forms.get(i).unClick();
		}

		forms.add(form);
	}

	public void removeForm(Form form) {
		forms.remove(form);
	}

	public Form getFocusedForm() {
		return forms.get(forms.size() - 1);
	}

	public Game getGame() {
		return this.game;
	}

	public Point getMouseLocation() {
		return mouseLocation;
	}

	private Point mouseLocation;

	public List<Form> getForms() {
		return forms;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		getFocusedForm().mouseDragged(e);
		mouseLocation.edit(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getFocusedForm().mouseMoved(e);
		mouseLocation.edit(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		getFocusedForm().mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		getFocusedForm().mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		getFocusedForm().mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		getFocusedForm().mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getFocusedForm().mouseReleased(e);
	}
}
