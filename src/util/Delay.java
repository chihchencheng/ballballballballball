package util;

public class Delay {
	private int delayFrame;
	private int counter;
	private boolean isPause;

	public Delay(int delayFrame) {
		this.delayFrame = delayFrame;
		this.counter = 0;
		this.isPause = false;
	}

	public void start() {
		this.isPause = false;
	}

	public void pause() {
		this.isPause = true;
	}
	
	public boolean isPause() {
		return isPause;
		
	}

	public void stop() {
		pause();
		this.counter = 0;
	}

	public void restart() {
		stop();
		start();
	}

	public boolean isTrig() {
		if (!this.isPause && this.counter++ == this.delayFrame) {
			this.counter = 0;
			return true;
		}
		return false;
	}
}
