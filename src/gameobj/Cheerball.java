package gameobj;

import util.ImgPath;

public class Cheerball extends Ball {

	public Cheerball(int x, int y) {
		super(ImgPath.CHEERBALL, ImgPath.CHEERBALL, "Cheerball", x, y);
	}
	
	public Cheerball() {
		super(ImgPath.CHEERBALL,ImgPath.CHEERBALL,"Cheerball");
	}

}
