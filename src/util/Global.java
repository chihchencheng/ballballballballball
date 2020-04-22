package util;

public class Global {
	// Debug Mode
	public static final boolean IS_DEBUG = false;

	public static void log(String str) {
		if (IS_DEBUG) {
			System.out.println(str);
		}
	}
	
	public static final int FRAME_X = 1608 + 8 + 8;//
	public static final int FRAME_Y = 828 + 31 + 8;//
	public static final int SCREEN_X = FRAME_X - 8 - 8;
	public static final int SCREEN_Y = FRAME_Y - 31 - 8;

	public static final int UNIT_X = 105;
	public static final int UNIT_Y = 105;
	public static final double ADJ = 0.75;
	
	// logic update time
	public static final int UPDATE_TIMES_PER_SEC = 120;
//	public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC;
	public static final long NANO_PER_UPDATE = 1000000000 / UPDATE_TIMES_PER_SEC;

	public static final int FRAME_LIMIT = 120;
	public static final long LIMIT_DELTA_TIME = 1000000000 / FRAME_LIMIT;
	
	// 
    public static final int COLUMN = 7;
    public static final int ROW = 6;
    
    
    //ball table max
    public static final int LIMIT = 42;
    public static final int XstartPoint = 620;// for locate 1st ball x
    public static final int XendPoint = 1175;
    public static final int YstartPoint = 0;
    public static final int YendPoint = 540;

    //
    public static final int SPEED = 4;
    
    //levelUpSet
    
    public static final int[] BASKETBALL_LEVEL_SET={100,200,200,700,600,600,700,700,800,1200};
    public static final int[] BADMINTON_LEVEL_SET={100,300,300,800,600,700,700,900,1000,1700};
    public static final int[] BASKBALL_LEVEL_SET={300,300,300,900,600,700,700,900,1000,2000};
    public static final int[] VOLLEYBALL_LEVEL_SET={400,500,600,1500,1000,1200,1700,1900,2500,4000};
	
	public static int random(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public static boolean random(int rate) {
		return random(1, 100) <= rate;
	}
}
