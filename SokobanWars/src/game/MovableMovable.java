package game;

import gui.ImageMatrixGUI;
import utils.Direction;
import utils.Point2D;


public abstract class MovableMovable extends AbstractObject implements InteractiveObject {

    public MovableMovable(Point2D initialPosition, String imageName, int layer) {
        super(initialPosition, imageName, layer);

    }

    public boolean isMovable(Direction direction) {
        Point2D position = SokobanWars.getInstance().getPlayer().getPosition();
        Point2D newPosition = getPosition().plus(direction.asVector());
        Point2D nextPosition = newPosition.plus(direction.asVector());
        Object object = SokobanWars.getInstance().getObject(nextPosition);

        if (SokobanWars.getInstance().getObject(newPosition) instanceof Wall
                
                || (SokobanWars.getInstance().getObject(newPosition) instanceof MovableMovable && SokobanWars.getInstance().getObject(nextPosition) instanceof MovableMovable)
                || (SokobanWars.getInstance().getObject(newPosition) instanceof MovableMovable && SokobanWars.getInstance().getObject(nextPosition) instanceof Wall)
                || (SokobanWars.getInstance().getObject(newPosition) instanceof MovableMovable && SokobanWars.getInstance().getObject(nextPosition) instanceof Battery)
                || (SokobanWars.getInstance().getObject(newPosition) instanceof MovableMovable && SokobanWars.getInstance().getObject(nextPosition) instanceof Hammer)
                || (SokobanWars.getInstance().getObject(newPosition) instanceof Hole && SokobanWars.getInstance().hasObjects(newPosition) )
                || (SokobanWars.getInstance().getObject(newPosition) instanceof MovableMovable && SokobanWars.getInstance().getObject(nextPosition) instanceof Hole && !((Hole) object).isOpened())
                || (SokobanWars.getInstance().getObject(newPosition) instanceof Player && SokobanWars.getInstance().getObject(nextPosition) instanceof Hole && !((Hole) object).isOpened())
                || (SokobanWars.getInstance().getObject(position) instanceof Player
                       && SokobanWars.getInstance().getObject( position.plus(direction.asVector()) ) instanceof MovableMovable 
                       && SokobanWars.getInstance().getObject(position.plus(direction.asVector()).plus(direction.asVector()) ) instanceof MovableMovable   )
                || SokobanWars.getInstance().levelState()            ) 
                 {
            return false;
        }
        else {
            return true;
        }
    }

   
    
    
    public void move(Direction direction) {
       
        
        Point2D newPosition = this.getPosition().plus(direction.asVector());
        Point2D nextPosition = newPosition.plus(direction.asVector());

        if (isMovable(direction) && (SokobanWars.getInstance().getObject(newPosition) == null )) {
            this.setPosition(newPosition);
            return;
        }
        
        if (isMovable(direction) && SokobanWars.getInstance().getObject(newPosition) != null ) {
           ((InteractiveObject) SokobanWars.getInstance().getObject(newPosition)).interaction(this, direction);
           return;
        }
        
  
        
      
        
        
       
        ImageMatrixGUI.getInstance().update();

        }
    
    
   
        

    
}
