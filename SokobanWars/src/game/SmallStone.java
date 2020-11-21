package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;

public class SmallStone extends MovableMovable implements InteractiveObject {

    public SmallStone(Point2D initialPosition) {
        super(initialPosition, "SmallStone", 4);
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
        if (object instanceof Player && isMovable(direction)) {
            
          
        
            this.move(direction);
            ((Player) object).move(direction);
        }

    }

}
