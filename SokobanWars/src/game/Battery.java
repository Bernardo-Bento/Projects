package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;

public class Battery extends AbstractObject implements InteractiveObject {

    public Battery(Point2D initialPosition) {
	super(initialPosition, "Battery", 5);
    }

    public void interaction(AbstractObject object, Direction direction) {
        if (object instanceof Player) {
            SokobanWars.getInstance().removeObject(direction);
            ((Player) object).move(direction); 
            ((Player) object).resetEnergy();
            ImageMatrixGUI.getInstance().setMessage("Master Yoda helped you and restored your Force!");
            System.out.println("has battery");
        }
        if (object instanceof MovableMovable) {
        	return;
        }
    }



}
