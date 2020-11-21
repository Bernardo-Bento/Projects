package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;;

public class CrackedWall extends AbstractObject implements InteractiveObject {

    public CrackedWall(Point2D initialPosition) {
        super(initialPosition, "CrackedWall", 5);
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
        System.out.println( ((Player) object).getHammer() );
        if (object instanceof Player && ((Player) object).getHammer()) {
            SokobanWars.getInstance().removeObject(direction);
            ((Player) object).move(direction);
            System.out.println("wall");
            

        }

    }

}
