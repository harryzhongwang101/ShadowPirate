import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Pirate {
    protected static Image PIRATE_LEFT = new Image("res/pirate/pirateLeft.png");
    protected static Image PIRATE_RIGHT = new Image("res/pirate/pirateRight.png");
    private final static Image PIRATE_HIT_LEFT = new Image("res/pirate/pirateHitLeft.png");
    private final static Image PIRATE_HIT_RIGHT = new Image("res/pirate/pirateHitRight.png");
    private final static Image PIRATE_PROJECTILE = new Image("res/pirate/pirateProjectile.png");
    private final static double MIN_SPEED = 0.2;
    private final static double MAX_SPEED = 0.7;
    private final static int MAX_HEALTH_POINTS = 45;
    private final int DAMAGE_POINTS = 10;
    private final int ATTACK_RANGE = 100;

    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final static int FONT_SIZE = 15;
    private final static Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private final static DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);


    protected int healthPoints;
    private double oldX;
    private double oldY;
    protected double x;
    protected double y;
    protected Image currentImage;
    protected Image pirateLeft;
    protected int damage;
    protected int attackRange;
    private final double moveSize;
    private DirectionEnum direction;


    public double getPirateSpeed() {
        return ((Math.random() * (MAX_SPEED - MIN_SPEED)) + MIN_SPEED);
    }

    enum DirectionEnum{
        LEFT,
        RIGHT,
        UP,
        DOWN,
    }

    public Pirate(double startX, double startY){
        this.moveSize= getPirateSpeed();
        this.direction = generateRandomDirection();
        this.healthPoints = MAX_HEALTH_POINTS;
        this.pirateLeft = PIRATE_LEFT;
        this.currentImage = PIRATE_RIGHT;
        this.damage = DAMAGE_POINTS;
        this.attackRange = ATTACK_RANGE;
        this.x = startX;
        this.y = startY;
        COLOUR.setBlendColour(GREEN);
    }

    public void update(ArrayList<Block> blocks){
        // store old coordinates every time the Pirate moves
        switch (direction){
            case LEFT:
                setOldPoints();
                move(-moveSize,0);
                currentImage = pirateLeft;
                break;
            case RIGHT:
                setOldPoints();
                move(moveSize,0);
                currentImage = PIRATE_RIGHT;
                break;
            case UP:
                setOldPoints();
                move(0, -moveSize);
                break;
            case DOWN:
                setOldPoints();
                move(0, moveSize);
                break;
        }
        currentImage.draw(x, y);
        renderHealthPoints();
        checkCollisions(blocks);
    }

    public DirectionEnum generateRandomDirection() {
        DirectionEnum[] values = DirectionEnum.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }

    private void switchDirection(){
        switch (direction){
            case LEFT:
                direction = DirectionEnum.RIGHT;
                break;
            case RIGHT:
                direction = DirectionEnum.LEFT;
                break;
            case UP:
                direction = DirectionEnum.DOWN;
                break;
            case DOWN:
                direction = DirectionEnum.UP;
                break;
        }
    }

    private void move(double xMove, double yMove){
        x += xMove;
        y += yMove;
    }

    private void renderHealthPoints(){
        double HealthX = x - pirateLeft.getWidth()/2;
        double HealthY = y - pirateLeft.getHeight()/2 - 6;
        double percentageHP = ((double) healthPoints/MAX_HEALTH_POINTS) * 100;
        if (percentageHP > ORANGE_BOUNDARY){
            FONT.drawString(Math.round(percentageHP) + "%", HealthX, HealthY, COLOUR);
        }
        if (percentageHP <= RED_BOUNDARY){
            COLOUR.setBlendColour(RED);
            FONT.drawString(Math.round(percentageHP) + "%", HealthX, HealthY, COLOUR);
        }
        else if (percentageHP <= ORANGE_BOUNDARY){
            COLOUR.setBlendColour(ORANGE);
            FONT.drawString(Math.round(percentageHP) + "%", HealthX, HealthY, COLOUR);
        }

    }

    private void checkCollisions(ArrayList<Block> blocks){
        // check collisions and print log
        Rectangle PirateBox = currentImage.getBoundingBoxAt(new Point(x, y));
        for (Block current : blocks) {
            Rectangle blockBox = current.getBoundingBox();
            if (PirateBox.intersects(blockBox) || isTouchingBound()) {
                moveBack();
                switchDirection();
            }
        }
    }

    public boolean isTouchingBound(){
        return (y > ShadowPirate.getBottomRightY()) || (y < ShadowPirate.getTopLeftY())
                || (x < ShadowPirate.getTopLeftX()) || (x > ShadowPirate.getBottomRightX());
    }

    private void moveBack(){
        x = oldX;
        y = oldY;
    }

    private void setOldPoints(){
        oldX = x;
        oldY = y;
    }

}
