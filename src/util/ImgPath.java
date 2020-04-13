package util;

public class ImgPath {
	private static final String ROOT = "/resources/";
	public static final String BASKETBALL = ROOT + "Basketball.png";
	public static final String BASEBALL = ROOT + "Baseball.png";
	public static final String CHEERBALL = ROOT + "Cheerball.png";
	public static final String VOLLEYBALL = ROOT + "Volleyball.png";
	public static final String SHUTTLECOCK = ROOT + "shuttlecock.png";
	public static final String MAINSCENE = ROOT + "MainScene.jpg";
	public static final String GAMESTART = ROOT + "background/bk_main.png";
	
	
	public static final String[] numbers(){
		String[] numbers = new String[10];
		for(int i=0;i<10;i++) {
			numbers[i] = ROOT + i +".png";
		}
		return numbers;
	}

	
	
}
