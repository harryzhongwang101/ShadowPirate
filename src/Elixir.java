import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Elixir {
    private final static Image ELIXIR = new Image("res/items/elixir.png");
    private final static int HEALTH_INCREASE = 35;
    private final int x;
    private final int y;

    public Elixir(int startX, int startY){
        this.x = startX;
        this.y = startY;
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if(!Sailor.getElixir()) {
            ELIXIR.draw(x, y);
        }
    }

    public int getDAMAGE_INCREASE() {
        return HEALTH_INCREASE;
    }

    public Rectangle getBoundingBox(){
        return ELIXIR.getBoundingBoxAt(new Point(x, y));
    }
}
