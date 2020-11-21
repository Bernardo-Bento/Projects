package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;

public class BigStone extends MovableMovable implements InteractiveObject {

    public BigStone(Point2D initialPosition) {
        super(initialPosition, "BigStone", 4);
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
        /*
         * for (AbstractObject obj: SokobanGame.getInstance().getObjectsList() ) {
         * 
         * if (obj instanceof Hole && obj.getPosition().equals(this.getPosition())) {
         * return; } }
         */

        if (object instanceof Player) {

            this.move(direction);
            ((Player) object).move(direction);
        }

        else {

            Point2D newPosition = this.getPosition().plus(direction.asVector());
            this.setPosition(newPosition);
        }

    }

}
