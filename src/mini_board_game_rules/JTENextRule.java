/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_rules;

import mini_board_game_ui.JTEPlayers;

/**
 *
 * @author ProgrammingSection
 */
public class JTENextRule {

  
    private JTEPlayers currentPlayer;
    private boolean lostTurn;
    private int numOfMissTurns;
    private boolean rollTwiceNextTurn;
    private int numOfRollsForNextTurn;
    private boolean flyNextTurn;
    public JTENextRule(JTEPlayers player)
    { 
         this.currentPlayer =  player;
         this.lostTurn =  false;
         this.numOfMissTurns = 0;
         this.rollTwiceNextTurn =  false;
         this.numOfRollsForNextTurn =0;
         this.flyNextTurn =  false;
    }
    
      public JTEPlayers getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(JTEPlayers currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isLostTurn() {
        return lostTurn;
    }

    public void setLostTurn(boolean lostTurn) {
        this.lostTurn = lostTurn;
    }

    public int getNumOfMissTurns() {
        return numOfMissTurns;
    }

    public void setNumOfMissTurns(int numOfMissTurns) {
        this.numOfMissTurns = numOfMissTurns;
    }

    public boolean isRollTwiceNextTurn() {
        return rollTwiceNextTurn;
    }

    public void setRollTwiceNextTurn(boolean rollTwiceNextTurn) {
        this.rollTwiceNextTurn = rollTwiceNextTurn;
    }

    public int getNumOfRollsForNextTurn() {
        return numOfRollsForNextTurn;
    }

    public void setNumOfRollsForNextTurn(int numOfRollsForNextTurn) {
        this.numOfRollsForNextTurn = numOfRollsForNextTurn;
    }

    public boolean isFlyNextTurn() {
        return flyNextTurn;
    }

    public void setFlyNextTurn(boolean flyNextTurn) {
        this.flyNextTurn = flyNextTurn;
    }
}
