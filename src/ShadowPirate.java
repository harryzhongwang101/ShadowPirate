import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * @Harry Wang
 */
public class ShadowPirate extends AbstractGame{
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private static int TopLeftX;
    private static int TopLeftY;
    private static int BottomRightX;
    private static int BottomRightY;

    private static double TreasureX;
    private static double TreasureY;

    private final static String GAME_TITLE = "ShadowPirate";
    private final Image BACKGROUND0_IMAGE = new Image("res/background0.png");
    private final Image BACKGROUND1_IMAGE = new Image("res/background1.png");
    private final static String LEVEL_0 = "res/level0.csv";
    private final static String LEVEL_1 = "res/level1.csv";
    private final Image TREASURE = new Image("res/treasure.png");
    private static int timeCounter = 0;

    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    private final static String INSTRUCTION_MESSAGE = "USE ARROW KEYS TO FIND LADDER";
    private final static String TREASURE_MESSAGE = "FIND THE TREASURE";
    private final static String END_MESSAGE = "GAME OVER";
    private final static String LEVEL1_WIN = "LEVEL COMPLETE!";
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";

    private final static int INSTRUCTION_OFFSET = 70;
    private final static int FONT_SIZE = 55;
    private final static int FONT_Y_POS = 402;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);

    private final static int MAX_ARRAY_SIZE = 49;
    private final static ArrayList<Block> blocks = new ArrayList<>();
    private final static ArrayList<Pirate> pirates = new ArrayList<>();

    private final static double FRAMES_PER_SECOND = 60/1000;
    private final static double WIN_RENDER_TIME = 3000;

    private Sailor sailor;
    private Blackbeard blackbeard;
    private Potion potion;
    private Sword sword;
    private Elixir elixir;
    private boolean gameOn;
    private boolean game2On;
    private boolean gameEnd;
    private boolean level0Win;
    private boolean level1Start;
    private boolean level1Win;
    private boolean level0Read = false;
    private boolean level1Read = false;

    public ShadowPirate(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        level0Win = false;
        gameEnd = false;
        gameOn = false;
        game2On = false;
        level1Start = false;
        level1Win = false;
    }

    /**
     * Entry point for program
     */
    public static void main(String[] args){
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line;
            if ((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                if (sections[0].equals("Sailor")){
                    sailor = new Sailor(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }
            }

            while((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                if (sections[0].equals("Block")){
                    blocks.add(new Block(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                }
                if (sections[0].equals("Pirate")){
                    pirates.add(new Pirate(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                }
                if (sections[0].equals("TopLeft")) {
                    ShadowPirate.TopLeftX = Integer.parseInt(sections[1]);
                    ShadowPirate.TopLeftY = Integer.parseInt(sections[2]);
                }
                if (sections[0].equals("BottomRight")) {
                    ShadowPirate.BottomRightX = Integer.parseInt(sections[1]);
                    ShadowPirate.BottomRightY = Integer.parseInt(sections[2]);
                }
                if (sections[0].equals("Treasure")) {
                    ShadowPirate.TreasureX = Integer.parseInt(sections[1]);
                    ShadowPirate.TreasureY = Integer.parseInt(sections[2]);
                }
                if (sections[0].equals("Elixir")) {
                    elixir = new Elixir(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }
                if (sections[0].equals("Sword")) {
                    sword = new Sword(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }
                if (sections[0].equals("Potion")) {
                    potion = new Potion(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }
                if (sections[0].equals("Blackbeard")){
                    blackbeard = new Blackbeard(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }

            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Performs a state update. Pressing escape key,
     * allows game to exit.
     */
    @Override
    public void update(Input input){
        if(!level0Read){
            readCSV(LEVEL_0);
            level0Read = true;
        }
        if(level1Start && !level1Read){
            blocks.clear();
            pirates.clear();
            readCSV(LEVEL_1);
            level1Read = true;
        }

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        if (input.wasPressed(Keys.ENTER)){
            level0Win = true;
        }

        if (!gameOn){
            drawStartScreen1(input);
        }

        if (gameEnd){
            drawEndScreen(END_MESSAGE);
        }

        if (level0Win && !level1Win){
            timeCounter += 1;
            if(timeCounter<=1){
                drawEndScreen(LEVEL1_WIN);
            }
            else {
                drawStartScreen2(input);
                level1Start = true;
            }
        }
        if (level1Win){
            drawEndScreen(WIN_MESSAGE);
        }


        // when game is running
        if (gameOn && !gameEnd && !level0Win){
            BACKGROUND0_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            for (Block block : blocks) {
                block.update();
            }
            for (Pirate pirate : pirates) {
                pirate.update(blocks);
            }
            sailor.update(input, blocks);

            if (sailor.isDead()){
                gameEnd = true;
            }

            if (sailor.hasWon0()){
                level0Win = true;
            }
        }
        if (game2On && !gameEnd && !level1Win){
            BACKGROUND1_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            TREASURE.draw(TreasureX, TreasureY);

            for (Block block : blocks) {
                block.update();
            }
            for (Pirate pirate : pirates) {
                pirate.update(blocks);
            }
            sailor.update(input, blocks);

            if (sailor.isDead()){
                gameEnd = true;
            }

            if (sailor.checkTreasure()){
                level1Win = true;
            }
        }

    }

    /**
     * Method used to draw the start screen instructions
     */
    private void drawStartScreen1(Input input){
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                FONT_Y_POS - 2 * INSTRUCTION_OFFSET);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                (FONT_Y_POS - INSTRUCTION_OFFSET));
        FONT.drawString(INSTRUCTION_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(INSTRUCTION_MESSAGE)/2.0)),
                (FONT_Y_POS));
        if (input.wasPressed(Keys.SPACE)){
            gameOn = true;
        }
    }

    private void drawStartScreen2(Input input){
        FONT.drawString(START_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(START_MESSAGE)/2.0)),
                FONT_Y_POS - 2 * INSTRUCTION_OFFSET);
        FONT.drawString(ATTACK_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(ATTACK_MESSAGE)/2.0)),
                (FONT_Y_POS - INSTRUCTION_OFFSET));
        FONT.drawString(TREASURE_MESSAGE, (Window.getWidth()/2.0 - (FONT.getWidth(TREASURE_MESSAGE)/2.0)),
                (FONT_Y_POS));
        if (input.wasPressed(Keys.SPACE)){
            game2On = true;
        }
    }

    /**
     * Method used to draw end screen messages
     */
    private void drawEndScreen(String message){
        FONT.drawString(message, (Window.getWidth()/2.0 - (FONT.getWidth(message)/2.0)), FONT_Y_POS);
    }

    public static int getTopLeftX(){
        return TopLeftX;
    }
    public static int getTopLeftY(){
        return TopLeftY;
    }
    public static int getBottomRightX(){
        return BottomRightX;
    }
    public static int getBottomRightY(){
        return BottomRightY;
    }
    public static double getTreasureX(){
        return TreasureX;
    }
    public static double getTreasureY() {
        return TreasureY;
    }

}
