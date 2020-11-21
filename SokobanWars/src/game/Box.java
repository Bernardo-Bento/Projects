package game;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;


public class Box extends MovableMovable implements InteractiveObject {

    public Box(Point2D initialPosition) {
        super(initialPosition, "Box", 4);
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
        Point2D newPosition = this.getPosition().plus(direction.asVector());
        
        if (object instanceof Player && this.isMovable(direction)) {
            this.move(direction);
            ((Player) object).move(direction);
            
            if (SokobanWars.getInstance().getStaticObject(newPosition) instanceof Target){
                this.setImageName("Box_On_Target");
            }
            else {
                this.setImageName("Box");
            }
            

        }
        
     
        

        
                
        else {

            
            this.setPosition(newPosition);
        }
        
        
      

    }

}