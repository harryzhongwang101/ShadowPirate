import bagel.Image;

public class Blackbeard extends Pirate{
    private final static Image BLACKBEARD_LEFT = new Image("res/blackbeard/blackbeardLeft.png");
    private final static Image BLACKBEARD_RIGHT = new Image("res/blackbeard/blackbeardRight.png");
    private final static Image BLACKBEARD_HIT_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final static Image BLACKBEARD_HIT_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");
    private final static Image BLACKBEARD_PROJECTILE = new Image("res/blackbeard/blackbeardProjectile.png");

    private final static int MAX_HEALTH_POINTS = 90;
    private final static int DAMAGE_POINTS = 20;
    private final static int ATTACK_RANGE = 200;
    private double blackX;
    private double blackY;


    public Blackbeard(double startX, double startY) {
        super(startX, startY);
        PIRATE_LEFT = BLACKBEARD_LEFT;
        currentImage = BLACKBEARD_RIGHT;
        healthPoints = MAX_HEALTH_POINTS;
        damage = DAMAGE_POINTS;
        attackRange = ATTACK_RANGE;
        blackX = startX;
        blackY = startY;
        x = blackX;
        y = blackY;
    }
    public int getHealthPoints(){
        return healthPoints;
    }
    public double getXCords(){
        return blackX;
    }
    public double getYCords(){
        return blackY;
    }

}
