/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_board_game_ui;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

/**
 *
 * @author ProgrammingSection
 */
public class JTEDice {

    private ArrayList<Image> dice;
    private int numSteps;
    private int numberOfIterations;
    private int x;
    private int y;
    private Random rand = new Random();

    public JTEDice(ArrayList<Image> dice, int x, int y) {
        this.dice = dice;
        this.x = x;
        this.y = y;
    }

    public boolean completedTrowAnimation() {
        return numberOfIterations == 20;
    }

    public void resetNumberOfIterations() {
        this.numberOfIterations = 0;
    }

    public Image calculateRandomDice() {
        int randomIndex = rand.nextInt(6) + 0;
        numSteps = randomIndex + 1;
        numberOfIterations++;
        return dice.get(randomIndex);
    }

    public int getNumSteps() {
        return this.numSteps;
    }

    public ArrayList<Image> getDice() {
        return dice;
    }

    public void setDice(ArrayList<Image> dice) {
        this.dice = dice;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

}
