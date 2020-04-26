package gameobj;

import java.util.ArrayList;

import util.ImgPath;

public class Numbers {
	private ArrayList<Number> numbers;
	
	public Numbers() {
		this.numbers = new ArrayList<>();
		genNum();
		setPos();
	}
	
	private void genNum() {
		

	}
	
	private void setPos() {
		for(int i=0;i<9;i++) {
			this.numbers.get(i).setX(10);
			this.numbers.get(i).setY(10);
		}
	}
	
	
	


}// end of class
