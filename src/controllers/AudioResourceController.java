package controllers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.util.ArrayList;

import util.AudioPath;
import util.Global;

public class AudioResourceController {

    private static class KeyPair {// inner class

        private String path;
        private AudioClip audio;

        public KeyPair(String path, AudioClip audio) {
            this.path = path;
            this.audio = audio;
        }
    }// end of inner class

    // 
    private static AudioResourceController arc;

    // 
    private ArrayList<KeyPair> audioPairs;

    private AudioResourceController() {
        audioPairs = new ArrayList<>();
    }// for unable to create a new

    // the only method to get the only object
    public static AudioResourceController getInstance() {
        if (arc == null) {
            arc = new AudioResourceController();
        }
        return arc;
    }

    public AudioClip tryGetAudio(String path) throws IOException {
        KeyPair pair = findKeyPair(path);
        if (pair == null) {
            return addAudio(path);
        }
        return pair.audio;
    }

    private AudioClip addAudio(String path) throws IOException {
        if (Global.IS_DEBUG) {
		    Global.log("load audio from:" + path);
		}
		
		AudioClip audio = Applet.newAudioClip(getClass().getResource(path));
		audioPairs.add(new KeyPair(path, audio));
		return audio;
    }

    public void tryRemoveAudio(String path) {
        for (int i = 0; i < audioPairs.size(); i++) {
            if (audioPairs.get(i).path.equals(path)) {
                audioPairs.remove(i);
                i--;
            }
        }
    }

    public String getPath(int index) {
        return audioPairs.get(index).path;
    }

    private KeyPair findKeyPair(String path) {
        for (int i = 0; i < audioPairs.size(); i++) {
            KeyPair pair = audioPairs.get(i);
            if (pair.path.equals(path)) {
                return pair;
            }
        }
        return null;
    }

    public int length() {
        return audioPairs.size();
    }

}
