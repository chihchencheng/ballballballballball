package gameobj;

import util.ImgPath;

public class Shuttlecock extends Ball {

	public Shuttlecock(int x, int y) {
		super(ImgPath.SHUTTLECOCK, ImgPath.SHUTTLECOCK,"Shuttlecock", x, y);
	}
	public Shuttlecock() {
		super(ImgPath.SHUTTLECOCK, ImgPath.SHUTTLECOCK,"Shuttlecock");
	}

}
