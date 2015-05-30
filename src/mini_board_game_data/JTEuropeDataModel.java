/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_data;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.image.Image;
import mini_board_game_ui.JTECity;
import mini_board_game_ui.JTEPlayers;
import mini_board_game_ui.JTESprite;
import mini_board_game_ui.JTESpriteCards;
import mini_board_game_ui.JTEuropeUI;

/**
 *
 * @author ProgrammingSection
 */
public class JTEuropeDataModel {
    private JTEuropeUI game;
    private JTEGameState state;
     public enum JTEGameState {
        GAME_NOT_STARTED, GAME_IN_PROGRESS, GAME_OVER,GAME_WIN,GAME_PLAYING_HANDS
        
    }
    public JTEuropeDataModel(JTEuropeUI game)
    {this.game =  game; state =  JTEGameState.GAME_IN_PROGRESS;}
    
    public boolean inProgress()
    {return state==JTEGameState.GAME_IN_PROGRESS;}
    public boolean inHandState()
    {return state==JTEGameState.GAME_PLAYING_HANDS;}
    public boolean gameOver()
    {return state==JTEGameState.GAME_OVER;}
    public boolean gameWin()
    {return state==JTEGameState.GAME_WIN;}
    public JTEuropeUI getGame()
    {return this.game;}
    
    public void setGameState(JTEGameState state)
    {this.state =  state;}
    
      public byte[] toByteArray() throws IOException {
        Iterator<String> keysIt = this.game.getPlayers().keySet().iterator(); //lol here you get each data from our hash map
        int numplayer = this.game.getPlayers().keySet().size();  //get the length of file 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //the byte array for output 
        DataOutputStream dos = new DataOutputStream(baos); //here write to file
        dos.writeInt(numplayer);
        dos.writeInt(this.game.getMaxNumOfPlayer());
        while (keysIt.hasNext()) {
            // PACK IT WITH ALL THE DATA FOR THE RECORDS
            String key = keysIt.next();
            dos.writeUTF(key);
            JTEPlayers rec = this.game.getPlayers().get(key);
            //going throw players on tree
            dos.writeUTF(rec.getPlayerName());
            dos.writeInt(rec.getImgR());
            dos.writeInt(rec.getImgC());
            //player card render coord
            JTESprite coord =  this.game.getPlayers().get(key).getCardRendererCoord();
            dos.writeFloat(coord.getX());
            dos.writeFloat(coord.getY()); 
            dos.writeInt(rec.getNumOfCard());
            dos.writeInt(rec.getPlayerNumSteps());
            dos.writeLong(rec.getScore());
          
            
            //starting and destination city
            JTECity city =  this.game.getPlayers().get(key).getStartingNodeLocation();  
            dos.writeUTF(city.getCityName());
         
            //player current city on grid
            JTECity currentCity =  this.game.getPlayers().get(key).getPlayersCurrentCity();
            dos.writeUTF(currentCity.getCityName());
          
            
            //all player visited cities on journey
            ArrayList<JTECity>visitedCities = this.game.getPlayers().get(key).getVisitedCities();
            dos.writeInt(visitedCities.size());
            for(int i=0; i<visitedCities.size(); i++)
            {
            currentCity =  visitedCities.get(i);
            dos.writeUTF(currentCity.getCityName());
          
            
            }
            //get players cards name
            ArrayList<JTESpriteCards> card = this.game.getPlayers().get(key).getPlayerCards();
            dos.writeInt(card.size());
            for(int i=0; i<card.size(); i++)
            {
                 JTESpriteCards c =  card.get(i);
                 dos.writeUTF(c.getCardName());
                 dos.writeUTF(c.getCityInfo());
                 dos.writeBoolean(c.getCardEnableToShuffle());
            
            }
     
        }
        
        Iterator<String> keysSec= this.game.getPlayerPlaying().iterator();
        dos.writeInt(this.game.getPlayerPlaying().size());
        while(keysSec.hasNext())
        {
            String pla =  keysSec.next();
            dos.writeUTF(pla);
        }
        
        // AND THEN RETURN IT
        return baos.toByteArray();
    }

    
}
