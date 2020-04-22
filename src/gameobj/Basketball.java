package gameobj;

import util.ImgPath;

public class Basketball extends Ball {

	public Basketball(int x, int y) {
		super(ImgPath.BASKETBALL,ImgPath.BASKETBALL2,"Basketball", x, y);
	}
	public Basketball() {
		super(ImgPath.BASKETBALL,ImgPath.BASKETBALL2,"Basketball");
	}
	

}
