package engine;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.GraphicsPlus;
import util.Point;
import util.Rectangle;

public class GameLoop implements Threadable {
	private Component component;
	private Game game;
	private Rectangle boundBox;

	private static GameLoop gameLoop;

	private Point mouseLocation = new Point(0, 0);

	private GameThread thread;
	
	public Point getMouseLocation() {
		return mouseLocation;
	}

	public void start(Component component) {
		this.game = new Game(this);
		this.component = component;
		this.boundBox = new Rectangle(0, 0, WIDTH, HEIGHT);

		component.addMouseListener(game);
		component.addMouseMotionListener(game);
		component.addKeyListener(game);
		component.addMouseWheelListener(game);

		thread = new GameThread(this);
		thread.start();
	}
	
	public Rectangle getBoundBox() {
		return boundBox;
	}

	private long ticks = 0;
	
	public GameThread getThread() {
		return thread;
	}
	
	@Override
	public void update() {
		this.game.update();
		((Panel) component).grabFocus();
	}

	@Override
	public void render() {
		component.repaint();
	}

	public long getTicks() {
		return ticks;
	}

	public static GameLoop getGameLoop() {
		return gameLoop;
	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);


		GraphicsPlus g2 = new GraphicsPlus(g);
		game.render(g2);
//		g2.setColor(Color.GREEN);
//		g2.fillCircle(new Point(300+50*(float)Math.sin(i/10f),200), 10);
	}

	public static class Panel extends JPanel {
		private static final long serialVersionUID = 1L;

		private final GameLoop game;

		public Panel(GameLoop game) {
			this.game = game;
		}

		@Override
		protected void paintComponent(Graphics g) {
			game.paint(g);
		}
	}

	private static final int WIDTH = 1000 + 900;
	private static final int HEIGHT = 1020 + 30;

	private static final String TITLE = "Digital Circuit Simulator";

	private static JFrame frame;
	
	public static void main(String[] args) {
		gameLoop = new GameLoop();

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Panel component = new Panel(gameLoop);

				frame = new JFrame();

				frame.setTitle(TITLE);
				frame.setSize(WIDTH, HEIGHT);

				frame.setLayout(new BorderLayout());
				frame.getContentPane().add(component, BorderLayout.CENTER);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setResizable(true);
				frame.setFocusable(true);
				component.setFocusable(true);
				component.grabFocus();

				gameLoop.start(component);
			}
		});
	}

	public void exit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public Component getFrame() {
		return frame;
	}
}
