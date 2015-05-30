/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_events;

import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mini_board_game_data.JTEuropeDataModel;
import mini_board_game_ui.JTECity;
import mini_board_game_ui.JTEuropeUI;

/**
 *
 * @author ProgrammingSection
 */
public class JTEuropeKeyEvents {
     private JTEuropeUI game;
     private ReentrantLock  lock;
    public JTEuropeKeyEvents(JTEuropeUI game)
    {this.game =  game; lock =  new ReentrantLock();}
    
    public void keyPressed(KeyEvent e)
    {
       
             KeyCode k =  e.getCode();
             System.out.println(k);
            if(game.getState()==JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE){
                if(e.getCode()==KeyCode.UP){
                  game.movingUp(30);System.out.println("up");}
                if(e.getCode()==KeyCode.DOWN){
                  game.movingDown(30);System.out.println("down");}
                if(e.getCode()==KeyCode.LEFT){
                  game.movingLeft(30);System.out.println("left");}
                if(e.getCode()==KeyCode.RIGHT){
                  game.movingRight(30);System.out.println("right");}
      
                if(e.getCode()==KeyCode.Z)
                {
                   game.getViewPort().setGameWorldSize(game.getViewPort().getViewPortGameWidth()+30,game.getViewPort().getViewPortGameHeight()+50);
                   game.zoomIn(2,7);
               }
                 if(e.getCode()==KeyCode.O)
                {
                   game.getViewPort().setGameWorldSize(game.getViewPort().getViewPortGameWidth()-30,game.getViewPort().getViewPortGameHeight()-50);
                   game.zoomOut(2,7);
                }
               
            }
                 if(e.getCode()==KeyCode.L && game.getState()!=JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE){
                     //LOAD DATA
                     try{this.game.getFileManager().loadJTERecord(); this.game.getRenderer().setIsComingFromLoadData(true); this.game.initRealTimeJTEGameScreen();  this.game.getDataModel().setGameState(JTEuropeDataModel.JTEGameState.GAME_PLAYING_HANDS);}catch(Exception u){u.printStackTrace();}
                 }
                 if(e.getCode()==KeyCode.S){
                     //stats
                     this.game.initJTEStatsPane(); this.game.getRenderer().setStatPaneMovingUp(true);
                 }
                 if(e.getCode()==KeyCode.E){
                     //exit
                     System.exit(0);
                 }
                
                  if(e.getCode()==KeyCode.D && this.game.getDataModel().inHandState()){
                     //exit
                      this.game.getRenderer().setDiceAnimation(true);
                 }
                 if(e.getCode()==KeyCode.C && this.game.getDataModel().inHandState()){
                     //save
                     try{this.game.getFileManager().saveLevelRecord();}catch(Exception d){d.printStackTrace();}
                 }
                 
                  if(e.getCode()==KeyCode.H){
                     //help
                      this.game.getRenderer().setHelpPaneMovingLeft(true);this.game.initJTEHelpPane();
                 }
            
                  
                  //cheat to look up for city location
                  if(e.getCode()==KeyCode.CONTROL && this.game.getDataModel().inHandState())
                  {
                      String city = javax.swing.JOptionPane.showInputDialog("Name of City :");
                      JTECity cit=null;
                      for(int i=0; i<this.game.getAllCities().size(); i++)
                      {
                          if(this.game.getAllCities().get(i).getCityName().equals(city))
                          {
                              cit=this.game.getAllCities().get(i);
                          }
                      }
                      if(cit==null)
                      {this.game.getRenderer().getRobot().text("City not found host");}
                      else{ 
                        this.game.getRenderer().cheatNextVisitedCity(cit);
                        this.game.getRenderer().setCanFixViewPort(true);
                      }
                      
                  }
                  
                  if(e.getCode()==KeyCode.T && this.game.getDataModel().inHandState())
                  {
                      
                  }
                  
                 
        
    }
    
    public void keyReleased(KeyEvent e)
    {
       
             KeyCode k =  e.getCode();
           
       
        
    }
}
