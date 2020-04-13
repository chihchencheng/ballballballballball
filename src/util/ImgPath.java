package util;

public class ImgPath {

    private static final String ROOT = "/resources/";
    
    //Component file
    private static final String COM = ROOT+"component/";
    public static final String BASKETBALL = COM + "Basketball.png";
    public static final String BASEBALL = COM + "Baseball.png";
    public static final String CHEERBALL = COM + "Cheerball.png";
    public static final String VOLLEYBALL = COM + "Volleyball.png";
    public static final String SHUTTLECOCK =  COM + "shuttlecock.png";

//    public static final String MAINSCENE = ROOT + "MainScene.jpg";

    //Background file
    public static final String GAMESTART = ROOT + "background/bk_main.png";

    //GameScene file
    private static final String GC = ROOT+"GameScene/";
    public static final String GAME_LEFT_PANEL = GC + "leftPanel.png";
    public static final String GAME_RIGHT_PANEL = GC + "rightPanel.png";
    public static final String SKILL_BUTTON =  GC + "playButton.png";
    public static final String PAUSE_BUTTON = GC + "pause.png";
    public static final String REHEARSE_BUTTON = GC + "rehearse.png";

    public static final String[] numbers() {
        String[] numbers = new String[10];
        for (int i = 0; i < 10; i++) {
            numbers[i] = ROOT + i + ".png";
        }
        return numbers;
    }

}
