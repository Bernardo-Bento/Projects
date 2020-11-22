package game;

import utils.Point2D;
import utils.Vector2D;
import utils.Direction;
import gui.ImageTile;


public abstract class AbstractObject implements ImageTile {

    private Point2D position;
    private String imageName;
    private int level;

    public AbstractObject(Point2D initialPosition, String imageName, int layer) {
        this.position = initialPosition;
        this.imageName = imageName;
        this.level = layer;
    }

    @Override
    // Getters
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public String getName() {
        return this.imageName;
    }

    @Override
    public int getLayer() {
        return this.level;
    }

    // Setters
    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

 
    public boolean isInteractive() {
        return this instanceof InteractiveObject;

    }
    
/*
    public boolean canInteract(Direction direction) {
        Point2D position = this.position.plus(direction.asVector());

        if (SokobanGame.getInstance().getObject(position) != null && SokobanGame.getInstance().getObject(position).isInteractive()) {
            return true;
        }
        return false;
    }
    */

}
