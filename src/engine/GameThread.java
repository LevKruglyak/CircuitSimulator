package engine;

public class GameThread extends Thread implements Runnable {

	private final int UPDATES_PER_SECOND = 60;
	private final int FRAMES_PER_SECOND = 60;

	private int UPS = UPDATES_PER_SECOND;
	private int FPS = FRAMES_PER_SECOND;

	private Threadable game;

	public GameThread(Threadable game) {
		this.game = game;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = .0 + TARGET_FPS;
		double amountOfRenders = 1000. / TARGET_UPS;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		long timer2 = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				game.update();
				updates++;
				delta--;
			}

			if (System.currentTimeMillis() - timer2 > amountOfRenders) {
				timer2 += amountOfRenders;
				game.render();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				UPS = updates;
				FPS = frames;
				frames = 0;
				updates = 0;
			}
		}
	}

	int fps = 0;
	long lastFpsTime = 0;
	long lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 60;
	final int TARGET_UPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	public int getUPS() {
		return UPS;
	}

	public int getFPS() {
		return FPS;
	}

}
