import javax.swing.JFrame;

import controllers.ImageResourceController;
import gameobj.Ball;
import util.Global;
import util.ImgPath;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		GameJPanel jp = new GameJPanel();
		f.setTitle("Game test");
		f.setSize(Global.FRAME_X, Global.FRAME_Y);
		f.add(jp);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		
		long startTime = System.currentTimeMillis();
		long passedUpdated = 0;
		long lastRepaintTime = System.currentTimeMillis();
		int paintTimes = 0;
		long timer = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();// 系統當前時間
			long totalTime = currentTime - startTime;// 從開始到現在經過的時間
			long targetTotalUpdated = totalTime / Global.MILLISEC_PER_UPDATE;// 開始到現在應該更新的次數
			// input
			// input end
			while (passedUpdated < targetTotalUpdated) {// 如果當前經過的次數小於實際應該要更新的次數
				// update 更新追上當前次數
				jp.update();
				passedUpdated++;
			}
			if (currentTime - timer >= 1000) {
				Global.log("FPS: " + paintTimes);
				paintTimes = 0;
				timer = currentTime;
			}

			if (Global.LIMIT_DELTA_TIME <= currentTime - lastRepaintTime) {
				lastRepaintTime = currentTime;
				f.repaint();
				paintTimes++;
			}
		}


	}// end of main

}// end of class
