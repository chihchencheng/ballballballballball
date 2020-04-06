package controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.Global;

public class ImageResourceController {

	private static class KeyPair {// inner class
		private String path;
		private BufferedImage img;

		public KeyPair(String path, BufferedImage img) {
			this.path = path;
			this.img = img;
		}
	}// end of inner class

	// ³æ¨Ò
	private static ImageResourceController irc;

	// ¤º®e
	private ArrayList<KeyPair> imgPairs;

	private ImageResourceController() {
		imgPairs = new ArrayList<>();
	}// for unable to create a new

	public static ImageResourceController getInstance() {
		if (irc == null) {
			irc = new ImageResourceController();
		}
		return irc;
	}

	public BufferedImage tryGetImage(String path) {
		KeyPair pair = findKeyPair(path);
		if (pair == null) {
			return addImage(path);
		}
		return pair.img;
	}

	private BufferedImage addImage(String path) {
		try {
			if(Global.IS_DEBUG) {
				System.out.println("load img from:"+path);
			}
			BufferedImage img = ImageIO.read(getClass().getResource(path));
			imgPairs.add(new KeyPair(path,img));
			return img;
		} catch (IOException e) {
		}
		return null;
	}

	private KeyPair findKeyPair(String path) {
		for (int i = 0; i < imgPairs.size(); i++) {
			KeyPair pair = imgPairs.get(i);
			if (pair.equals(path)) {
				return pair;
			}
		}
		return null;
	}

}
