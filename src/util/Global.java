package util;

public class Global {
	// Debug Mode
	public static final boolean IS_DEBUG = true;

	public static void log(String str) {
		if (IS_DEBUG) {
			System.out.println(str);
		}
	}

	public static final int FRAME_X = 1206 + 8 + 8;
	public static final int FRAME_Y = 621 + 31 + 8;
	public static final int SCREEN_X = FRAME_X - 8 - 8;
	public static final int SCREEN_Y = FRAME_Y - 31 - 8;

	public static final int UNIT_X = 105;
	public static final int UNIT_Y = 105;
	public static final double CHARACTER_SIZE_ADJ = 0.75;

	public static final int UPDATE_TIMES_PER_SEC = 60;
	public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC;

	public static final int FRAME_LIMIT = 60;
	public static final long LIMIT_DELTA_TIME = 1000 / FRAME_LIMIT;
	
	// ¤è¦V
    public static final int DOWN = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    
    //ball table max
    public static final int LIMIT = 42;
    public static final int startPoint = 603;// for locate 1st ball x

	
	public static int random(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public static boolean random(int rate) {
		return random(1, 100) <= rate;
	}
}
