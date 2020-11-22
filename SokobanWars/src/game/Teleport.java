package game;

import java.io.File;
import java.util.ArrayList;

import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;

public class Teleport extends AbstractObject implements InteractiveObject {

    public Teleport(Point2D initialPosition) {
	super(initialPosition, "Portal", 2);
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
        int aux = 0;
     
        Point2D portalPosition = null;
        for (AbstractObject portal: SokobanWars.getInstance().getObjectsList() ) {
            if ( !(portal.getPosition().equals(this.getPosition())) && portal instanceof Teleport  ){
                 portalPosition = portal.getPosition();
            }
        }
        for (AbstractObject portal: SokobanWars.getInstance().getObjectsList() ) {
             if (portal.getPosition().equals(portalPosition))   {
                 aux++;
             }
        }
                
                
            
        
      
        if (aux <= 1) {
            object.setPosition(portalPosition);   
                       }
   
        else {
            
            ((MovableMovable) object).interaction(SokobanWars.getInstance().getObject(portalPosition), direction);
            object.setPosition(portalPosition);   
        }
        
    }

  

    

}
