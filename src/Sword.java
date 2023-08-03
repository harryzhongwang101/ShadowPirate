import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Sword {
    private final static Image SWORD = new Image("res/items/sword.png");
    private final static int DAMAGE_INCREASE = 15;
    private final int x;
    private final int y;

    public Sword(int startX, int startY){
        this.x = startX;
        this.y = startY;
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if(!Sailor.getSword()) {
            SWORD.draw(x, y);
        }
    }

    public int getDAMAGE_INCREASE() {
        return DAMAGE_INCREASE;
    }

    public Rectangle getBoundingBox(){
        return SWORD.getBoundingBoxAt(new Point(x, y));
    }
}
