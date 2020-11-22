package game;

import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.awt.Canvas;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import audio.Music;
import gui.ImageMatrixGUI;
import menu.Menu;
import observer.Observed;
import observer.Observer;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;
import score.Score;

public class SokobanWars extends Canvas implements Observer {

    private ArrayList<AbstractObject> objects = new ArrayList<>();
    private ArrayList<File> levels = new ArrayList<>();

    private File currentLevel;
    private Iterator<File> iterator;

    private int levelNumber;
    private Player player;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private boolean levelReseted;
    private String username;
    private static SokobanWars INSTANCE;
    private Menu menu;
    ImageIcon test = new ImageIcon("./icon/Game_Icon.png");





    private SokobanWars() {

        this.username = JOptionPane.showInputDialog("Jedi Name:");

        settings();

        buildFileLevel(currentLevel);

        if (player == null) {
            throw new IllegalArgumentException();
        }

       

        setInfo();

        /**
         * if (State == STATE.GAME) {
         * 
         * setInfo(); }
         */

        // render();
       
       // Music.test();
        ImageMatrixGUI.getInstance().update();
        ImageMatrixGUI.getInstance().setIcon("./icon/Game_Icon.png");
    }

    public static SokobanWars getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SokobanWars();
        }
        return INSTANCE;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    private void setInfo() {

        // + " | " + " Max. Level Score: " + Score.getLevelScore(levelNumber)
        String info;

        info = ("Level: " + levelNumber + " | " + " Moves: " + player.getMoves() + " | " + " Force: "
                + player.getEnergy() + "%");

        ImageMatrixGUI.getInstance().setStatusMessage(info);

    }

    private void checkObjective() {
        ArrayList<AbstractObject> boxes = new ArrayList<>();
        ArrayList<AbstractObject> targets = new ArrayList<>();

        for (AbstractObject object : objects) {
            if (object instanceof Box) {
                boxes.add(object);
            }
            if (object instanceof Target) {
                targets.add(object);
            }
        }

        if (boxes.size() != targets.size()) {
            resetCurrentLevel();
        }

    }

    public void resetEnergy(Direction direction) {

        Point2D newPosition = player.getPosition().plus(direction.asVector());
        ImageMatrixGUI.getInstance().removeImage(getObject(newPosition));
        objects.remove(getObject(newPosition));
        player.resetEnergy();
    }

    public void removeObject(Direction direction) {

        Point2D newPosition = player.getPosition().plus(direction.asVector());
        ImageMatrixGUI.getInstance().removeImage(getObject(newPosition));
        objects.remove(getObject(newPosition));

    }

    private void levelCheck(Direction direction) {

        checkObjective();
        levelReseted = false;

        if (levelComplete() && iterator.hasNext()) {

            currentLevel = iterator.next();

            Score.getInstance().WriteScoresIntoArray();
            Score.getInstance().writeScore();
            ImageMatrixGUI.getInstance().setMessage("You completed level " + levelNumber + ", Congratulations, "
                    + this.username + "!" + "\nScore: " + Score.getInstance().getPlayerScore());
            buildFileLevel(currentLevel);
            setInfo();

            levelReseted = true;

        }

        if (levelComplete() && !iterator.hasNext()) {
            ImageMatrixGUI.getInstance().setMessage("Congratulations " + "Jedi " + this.username
                    + ", you escaped the maze! \nScore: " + Score.getInstance().getPlayerScore());
            levelReseted = true;
            ImageMatrixGUI.getInstance().setMessage("END?");
            Score.getInstance().WriteScoresIntoArray();
            Score.getInstance().writeScore();
            ImageMatrixGUI.getInstance().dispose();

        }

        if (!levelComplete() && player.isMovable(direction) && player.getEnergy() < 1) {

            resetCurrentLevel();
            ImageMatrixGUI.getInstance().setMessage("You're out of Force!");
            setInfo();
            levelReseted = true;

        }

    }

    public void resetCurrentLevel() {

        buildFileLevel(currentLevel);
        levelNumber--;

    }

    private boolean levelComplete() {
        ArrayList<AbstractObject> boxes = new ArrayList<>();
        ArrayList<AbstractObject> targets = new ArrayList<>();

        int count = 0;

        for (AbstractObject object : objects) {
            if (object instanceof Box) {
                boxes.add(object);
            }
            if (object instanceof Target) {
                targets.add(object);
            }
        }

        for (int i = 0; i != boxes.size(); i++) {
            for (int j = 0; j != boxes.size(); j++) {
                if (boxes.get(i).getPosition().equals(targets.get(j).getPosition())) {
                    count++;
                }
            }
        }

        return count == boxes.size();
    }

    private void buildFileLevel(File file) {

    

        Scanner scanner;

        ImageMatrixGUI.getInstance().clearImages();

        objects.clear();

        try {
            scanner = new Scanner(file);
            for (int y = 0; y < HEIGHT; y++) {

                String levelFile = scanner.nextLine();

                for (int x = 0; x < WIDTH; x++) {

                    if (levelFile.charAt(x) == 'b') {
                        objects.add(new Battery(new Point2D(x, y)));
                        objects.add(new Floor(new Point2D(x, y)));

                    }

                    if (levelFile.charAt(x) == 'C') {

                        objects.add(new Box(new Point2D(x, y)));
                        objects.add(new Floor(new Point2D(x, y)));

                    }

                    if (levelFile.charAt(x) == 'O') {
                        objects.add(new Hole(new Point2D(x, y)));
                    }

                    if (levelFile.charAt(x) == 'p') {
                        objects.add(new SmallStone(new Point2D(x, y)));
                        objects.add(new Floor(new Point2D(x, y)));
                    }
                    if (levelFile.charAt(x) == 'P') {
                        objects.add(new BigStone(new Point2D(x, y)));
                        objects.add(new Floor(new Point2D(x, y)));
                    }

                    if (levelFile.charAt(x) == 'E') {
                        this.player = new Player(new Point2D(x, y));
                        objects.add(player);
                        objects.add(new Floor(new Point2D(x, y)));
                    }

                    if (levelFile.charAt(x) == 'X') {
                        objects.add(new Target(new Point2D(x, y)));

                    }

                    if (levelFile.charAt(x) == 'F') {
                        Box box = new Box(new Point2D(x, y));
                        box.setImageName("Box_On_Target");
                        objects.add(box);
                        objects.add(new Target(new Point2D(x, y)));

                    }

                    if (levelFile.charAt(x) == '#') {
                        objects.add(new Wall(new Point2D(x, y)));

                    }

                    if (levelFile.charAt(x) == ' ') {
                        objects.add(new Floor(new Point2D(x, y)));
                    }

                    if (levelFile.charAt(x) == 'g') {
                        objects.add(new Ice(new Point2D(x, y)));
                    }

                    if (levelFile.charAt(x) == '%') {
                        objects.add(new CrackedWall(new Point2D(x, y)));
                        objects.add(new Floor(new Point2D(x, y)));
                    }

                    if (levelFile.charAt(x) == 'm') {
                        objects.add(new Hammer(new Point2D(x, y)));
                        objects.add(new Floor(new Point2D(x, y)));
                    }

                    if (levelFile.charAt(x) == 't') {
                        objects.add(new Teleport(new Point2D(x, y)));

                    }
                    if (levelFile.charAt(x) == 'D') {
                        objects.add(new DeathStar(new Point2D(x, y)));

                    }
                    if (levelFile.charAt(x) == 'S') {
                        objects.add(new Space(new Point2D(x, y)));
 
                    }

                }
            }

            scanner.close();

            player.resetMoves();
            player.resetEnergy();

            levelNumber++;

            for (AbstractObject object : objects) {
                ImageMatrixGUI.getInstance().addImage(object);
            }
            ImageMatrixGUI.getInstance().update();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        // }
    }

    public List<AbstractObject> getObjectsList() {

        List<AbstractObject> objectsList = Collections.unmodifiableList(objects);
        return objectsList;
    }

    private ArrayList<AbstractObject> buildSampleLevel() {

        objects.clear();

        for (int y = 0; y != 10; y++) {
            for (int x = 0; x != 10; x++) {
                objects.add(new Space(new Point2D(x, y)));
            }
        }

        this.player = new Player(new Point2D(1, 1));
        // objects.add(player);

        for (AbstractObject object : objects) {
            ImageMatrixGUI.getInstance().addImage(object);
        }

        return objects;
    }

    public AbstractObject getObject(Point2D position) {

        for (AbstractObject object : objects)
            if (object.getPosition().equals(position) && !(object instanceof Floor) && !(object instanceof Player)
                    && !(object instanceof Target)) {
                return object;
            }

        return null;
    }

    public AbstractObject getStaticObject(Point2D position) {

        for (AbstractObject object : objects)
            if (object.getPosition().equals(position) && !(object instanceof MovableMovable)) {
                return object;
            }

        return null;
    }

    /**
     * private void render() { BufferStrategy bs = this.getBufferStrategy(); if( bs
     * == null) { this.createBufferStrategy(3); return; } Graphics g =
     * bs.getDrawGraphics(); if(State == STATE.MENU) { menu.render(g); }
     * 
     * g.dispose(); bs.show();
     * 
     * }
     */

    public Boolean hasObjects(Point2D position) {
        int aux = 0;
        for (AbstractObject object : objects) {
            if (object.getPosition().equals(position)) {
                aux++;
            }
        }
        return (aux > 1);
    }

    private void settings() {

        levelNumber = 0;
        levelReseted = false;

        File files = new File("levels");

        for (File level : files.listFiles()) {
            levels.add(level);
        }

        Collections.sort(levels);

        iterator = levels.listIterator(12);

        currentLevel = iterator.next();

    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean levelState() {
        return levelReseted;
    }

    public String getUsername() {

        return this.username;
    }

    public int getLevelNumber() {

        return this.levelNumber;
    }

    @Override
    public void update(Observed arg0) {

        // if (State == STATE.GAME) {

        int lastKeyPressed = ((ImageMatrixGUI) arg0).keyPressed();
        Direction direction = null;

        if (Direction.isDirection(lastKeyPressed)) {
            direction = Direction.directionFor(lastKeyPressed);
        }

        levelCheck(direction);
        if (!levelReseted) {

            player.move(direction);
            setInfo();

        }

        ImageMatrixGUI.getInstance().update();
        levelCheck(direction);

    }
    // }

}
