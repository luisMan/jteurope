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
 * 
 */
public class JTECurrentRule {

    private JTEPlayers currentPlayer;
    private boolean lostTurn;
    private boolean takeTopRedCard;
    private boolean goAgain;

    
    public JTECurrentRule(JTEPlayers player)
    {this.currentPlayer = player;
     this.lostTurn =  false;
     this.takeTopRedCard =  false;
     this.goAgain =  false;
  
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

   
    public boolean isTakeTopRedCard() {
        return takeTopRedCard;
    }

    public void setTakeTopRedCard(boolean takeTopRedCard) {
        this.takeTopRedCard = takeTopRedCard;
    }

    public boolean isGoAgain() {
        return goAgain;
    }

    public void setGoAgain(boolean goAgain) {
        this.goAgain = goAgain;
    }

   
}
