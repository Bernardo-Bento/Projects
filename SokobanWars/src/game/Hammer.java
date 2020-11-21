package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;

public class Hammer extends AbstractObject implements InteractiveObject {

    public Hammer(Point2D initialPosition) {
	super(initialPosition, "Hammer", 5);
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
      if(object instanceof Player) {
         
          SokobanWars.getInstance().removeObject(direction);
          ((Player) object).setHammer();
          ((Player) object).move(direction);
          ImageMatrixGUI.getInstance().setMessage("You found chewie! Now you can destroy grey walls");
          System.out.println("has hammer");
      }
      if (object instanceof MovableMovable) {
      	return;
      }
        
    }

  
}
