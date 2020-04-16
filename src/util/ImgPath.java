package util;

public class ImgPath {
	private static final String ROOT = "/resources/";
	public static final String MAINSCENE = ROOT + "MainScene.jpg";
	
	//Background file
    private static final String BK = ROOT + "background/";
    public static final String GAMESTART = BK + "bk_main.png";
    //Component file
    private static final String COM = ROOT + "component/";
    public static final String TITLE = COM + "title.png";
    //-----ball file
    private static final String BALL = COM + "ball/";
    public static final String BASKETBALL = BALL + "Basketball.png";
    public static final String BASEBALL = BALL + "Baseball.png";
    public static final String CHEERBALL = BALL + "Cheerball.png";
    public static final String VOLLEYBALL = BALL + "Volleyball.png";
    public static final String SHUTTLECOCK = BALL + "Shuttlecock.png";
	
  //------button file
    private static final String BUTTON = COM + "button/";
    public static final String B_GAME = BUTTON + "B_game.png";
    public static final String B_GAME2 = BUTTON + "B_game2.png";
    public static final String B_HOME = BUTTON + "B_home.png";
    public static final String B_HOME2 = BUTTON + "B_home2.png";
    public static final String B_INFO = BUTTON + "B_info.png";
    public static final String B_INFO2 = BUTTON + "B_info2.png";
    public static final String B_SHOP = BUTTON + "B_shop.png";
    public static final String B_SHOP2 = BUTTON + "B_shop2.png";
    public static final String BB_GAMESTART = BUTTON + "BB_gameStart.png";
    public static final String BB_GAMESTART2 = BUTTON + "BB_gameStart2.png";
    public static final String BB_INFO = BUTTON + "BB_info.png";
    public static final String BB_INFO2 = BUTTON + "BB_info2.png";
    public static final String BB_RECORD = BUTTON + "BB_record.png";
    public static final String BB_RECORD2 = BUTTON + "BB_record2.png";
    public static final String BB_SHOP = BUTTON + "BB_shop.png";
    public static final String BB_SHOP2 = BUTTON + "BB_shop2.png";
    public static final String BB_TIPS = BUTTON + "BB_tips.png";
    public static final String BB_TIPS2 = BUTTON + "BB_tips2.png";
    
  //GameScene file
    private static final String GC = ROOT + "GameScene/";
    public static final String LEFT_PANEL = GC + "leftPanel.png";
    public static final String PAUSE = GC + "pause.png";
    public static final String PLAY_BUTTON = GC + "playButton.png";
    public static final String REHEARSE = GC + "rehearse.png";
    public static final String RIGHT_PANEL = GC + "rightPanel.png";
    public static final String SKILL_BANNER = GC + "skillBanner.png";
    public static final String TIME_PANEL = GC + "timePanel.png";
    
	public static final String[] numbers(){
		String[] numbers = new String[10];
		for(int i=0;i<10;i++) {
			numbers[i] = ROOT + i +".png";
		}
		return numbers;
	}

	
	
}
