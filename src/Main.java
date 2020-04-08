import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import controllers.ImageResourceController;
import gameobj.Ball;
import util.CommandSolver;
import util.CommandSolver.Builder;
import util.Global;
import util.ImgPath;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		GameJPanel jp = new GameJPanel();
		f.setTitle("Game test");
		f.setSize((int)(Global.FRAME_X*Global.CHARACTER_SIZE_ADJ),
				(int)(Global.FRAME_Y*Global.CHARACTER_SIZE_ADJ));
		f.add(jp);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		CommandSolver cs = new CommandSolver.Builder(jp, Global.MILLISEC_PER_UPDATE,
			new int[][] {
			{KeyEvent.VK_UP},
			{KeyEvent.VK_DOWN},
			{KeyEvent.VK_LEFT},
			{KeyEvent.VK_RIGHT},
			}).enableMouseTrack(jp).keyTypedMode().trackChar().gen();
			cs.start();
		
		long startTime = System.currentTimeMillis();
		long passedUpdated = 0;
		long lastRepaintTime = System.currentTimeMillis();
		int paintTimes = 0;
		long timer = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();
			long totalTime = currentTime - startTime; 
			long targetTotalUpdated = totalTime / Global.MILLISEC_PER_UPDATE;
			// input
			// input end
			while (passedUpdated < targetTotalUpdated) {
				
				cs.update();
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
