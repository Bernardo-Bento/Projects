package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;;

public class Ice extends AbstractObject implements InteractiveObject {

    public Ice(Point2D initialPosition) {
	super(initialPosition, "Ice", 3);
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
        Point2D newPosition = object.getPosition().plus(direction.asVector());
        Point2D position = this.getPosition();
        System.out.println(position);
        
       
        
         if(SokobanWars.getInstance().getObject(newPosition) instanceof Ice && SokobanWars.getInstance().getObject(newPosition.plus(direction.asVector())) instanceof MovableMovable )
        {
            //((Movable) object).interaction(null, direction);
            ((InteractiveObject) SokobanWars.getInstance().getObject(position.plus(direction.asVector()))).interaction(object, direction);
           return;
        }
         
         if(SokobanWars.getInstance().getObject(newPosition) instanceof MovableMovable && SokobanWars.getInstance().getObject(newPosition.plus(direction.asVector())) instanceof Ice )
         {
             ((MovableMovable) object).interaction(null, direction);
             ((InteractiveObject) SokobanWars.getInstance().getObject(position.plus(direction.asVector()))).interaction(object, direction);
             ((MovableMovable) object).interaction(object, direction);
            return;
         }
       
            
            
        else {
        while (SokobanWars.getInstance().getObject(position) instanceof Ice) {
            ((MovableMovable) object).interaction(null, direction);
            position = position.plus(direction.asVector());
            
         
        }
        ((MovableMovable) object).interaction(SokobanWars.getInstance().getObject(position), direction);
        
       
        
            
        }
        
       
       
      
        System.out.println(position);
        
      
        
        
       
            
            
            
            
    
    }

}
