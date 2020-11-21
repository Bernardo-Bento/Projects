package game;

import gui.ImageMatrixGUI;
import utils.Direction;
import utils.Point2D;

public interface InteractiveObject {

    abstract void interaction(AbstractObject object, Direction direction);

}
