package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;

public class Target extends AbstractObject implements InteractiveObject {

	public Target(Point2D initialPosition) {
		super(initialPosition, "Target", 2);
	}

    @Override
    public void interaction(AbstractObject object, Direction direction) {
  
        
    }

	

}
  