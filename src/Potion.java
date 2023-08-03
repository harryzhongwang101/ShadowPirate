import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Potion {
    private final static Image POTION = new Image("res/items/potion.png");
    private final static int HEALTH_INCREASE = 25;
    private final int x;
    private final int y;

    public Potion(int startX, int startY){
        this.x = startX;
        this.y = startY;
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if(!Sailor.getPotion()) {
            POTION.draw(x, y);
        }
    }

    public int getHEALTH_INCREASE() {
        return HEALTH_INCREASE;
    }

    public Rectangle getBoundingBox(){
        return POTION.getBoundingBoxAt(new Point(x, y));
    }
}

