package gameobj;

import util.ImgPath;

public class Baseball extends Ball {

	public Baseball(int x, int y) {
		super(ImgPath.BASEBALL, ImgPath.BASEBALL,"Baseball", x, y);
	}
	public Baseball() {
		super(ImgPath.BASEBALL, ImgPath.BASEBALL,"Baseball");
	}

}
