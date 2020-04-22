package audio;

import java.applet.AudioClip;
import controllers.AudioResourceController;

public class AudioObject {
	private String name;
    protected AudioClip audio;


    public AudioObject(String name, String audioPath) {
    	this.name = name;
    	try {
    		this.audio = AudioResourceController.getInstance().tryGetAudio(audioPath);
    	}catch(Exception e) {
    		
    	}
    	
    }// end of constructor
    
    public String getName() {
    	return this.name;
    }

    public AudioClip getAudio() {
        return this.audio;
    }

}// end of class
