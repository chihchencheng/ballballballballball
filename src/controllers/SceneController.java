package controllers;

import java.awt.Graphics;

import scenes.Scene;
import util.CommandSolver.*;

public class SceneController {
	private Scene currentScene;
	private KeyListener kl;
	private MouseCommandListener ml;

	public void update() {
		currentScene.sceneUpdate();
	}

	public void changeScene(Scene nextScene) {
		if (currentScene != null) {
			currentScene.sceneEnd();
		}
		currentScene = nextScene;
		currentScene.sceneBegin(); // 要先呼叫SceneBegin才可以指定kl、ml
		this.kl = currentScene.getKeyListener();
		this.ml = currentScene.getMouseListener();
	}

	public void paint(Graphics g) {
		currentScene.paint(g);
	}

	// -------------------------------------------------------for CommandSolver
	public KeyListener getKL() {
		return this.kl;
	}

	public MouseCommandListener getML() {
		return this.ml;
	}
	// -------------------------------------------------------for CommandSolver
}// end of class
