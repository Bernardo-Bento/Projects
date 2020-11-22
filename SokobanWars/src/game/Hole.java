package game;

import java.io.FileNotFoundException;
import gui.ImageMatrixGUI;
import utils.Point2D;
import utils.Vector2D;
import utils.Direction;

public class Hole extends AbstractObject implements InteractiveObject {
    private boolean opened;

    public Hole(Point2D initialPosition) {

        super(initialPosition, "Hole", 9);
        opened = true;

    }

    public boolean isOpened() {
        return this.opened;
    }

    @Override
    public void interaction(AbstractObject object, Direction direction) {
        if (!(SokobanWars.getInstance().hasObjects(this.getPosition()))) {
            if (object instanceof Player && opened) {
                SokobanWars.getInstance().resetCurrentLevel();
                return;

            }

            if (object instanceof Player && !opened) {
                return;
            }

            if (object instanceof MovableMovable && !opened) {
                return;
            }

            if (object instanceof BigStone) {
        

                ((BigStone) object).interaction(null, direction);
                this.setImageName("Wall");
                this.opened = false;
                ImageMatrixGUI.getInstance()
                        .setMessage("You have sent the Mandalorian to space. Now the Hole is closed");
                
                return;
            }
            if (object instanceof MovableMovable && opened) {
                SokobanWars.getInstance().removeObject(direction);
              

            }
        }

    }

}
