package scenes;

import java.awt.Graphics;

import controllers.SceneController;
import util.CommandSolver.*;

public abstract class Scene {
	protected SceneController sceneController;

	public Scene(SceneController sceneController) {
		this.sceneController = sceneController;
	}

	public abstract void sceneBegin();

	public abstract void sceneUpdate();

	public abstract void sceneEnd();

	public abstract void paint(Graphics g);

	// 設定讓每個場景都能取得該場景設定的Listener
	public abstract KeyListener getKeyListener();

	public abstract MouseCommandListener getMouseListener();
}
