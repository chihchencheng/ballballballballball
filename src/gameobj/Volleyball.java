package gameobj;

import util.ImgPath;

public class Volleyball extends Ball {

	public Volleyball(int x, int y) {
		super(ImgPath.VOLLEYBALL, ImgPath.VOLLEYBALL,"Volleyball", x, y);
	}
	public Volleyball() {
		super(ImgPath.VOLLEYBALL, ImgPath.VOLLEYBALL,"Volleyball");
	}

}
