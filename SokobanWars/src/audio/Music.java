package audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.SokobanWars;

public class Music {

    private final static File levelUp = new File("music/aaf.mov");
    private final static File move = new File("music/aaf.mov");
    private static String playlist[] = new String[] {"./sounds/main.wav","./sounds/Start.wav"};
    private static Music INSTANCE;


    private Music() {
        //throw new IllegalStateException("Utility class"); 
    }


    public static Music getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Music();
        }
        return INSTANCE;
    }

    public static void backingTrack() {

    }

    public static void levelUp() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(levelUp));
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void move() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(move));
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void test() {


        try {

            int i = 0;
            while (true) {

                File file = new File(playlist[i]);
                if (i > playlist.length ) {
                    i = 0;
                }

                else {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(file));
                    clip.start();

                    while(clip.getMicrosecondLength() != clip.getMicrosecondPosition()) {}
                    // Wait until clip ends 
                    
                    i++;
                }
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
