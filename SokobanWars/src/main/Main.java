
package main;

import java.io.FileNotFoundException;

import audio.Music;
import game.SokobanWars;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;
import gui.ImageMatrixGUI;

public class Main {

    public static void main(String[] args) {

        SokobanWars Game = SokobanWars.getInstance();

        ImageMatrixGUI.getInstance().setSize(10, 10);
        ImageMatrixGUI.getInstance().setName("Sokoban Wars");

        ImageMatrixGUI.getInstance().registerObserver(Game);
        ImageMatrixGUI.getInstance().go();

        ImageMatrixGUI.getInstance().setIcon("./icon/Game_Icon.png");

        ImageMatrixGUI.getInstance().update();
        
        Music.test();
        
        //Music music = Music.getInstance();
      //  Music.test();
    }

}
