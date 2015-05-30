/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javafx.scene.image.Image;
import jteurope.JTEurope.JTEuropePropertyType;
import mini_board_game_data.JTEuropeDataModel;
import mini_board_game_ui.JTECity;
import mini_board_game_ui.JTEConstant;
import mini_board_game_ui.JTEPlayers;
import mini_board_game_ui.JTESprite;
import mini_board_game_ui.JTESpriteCards;
import mini_board_game_ui.JTEuropeUI;
import properties_manager.PropertiesManager;
/**
 *
 * @author ProgrammingSection
 */
public class JTEuropeFileManager {
     private JTEuropeUI game;
    public JTEuropeFileManager(JTEuropeUI game)
    {this.game =  game;}
    
    
      public void createFilePlayerData()
    {
        /* File file = new File(JTEConstant.DATA_PATH+"PlayerData.jte");
              try{
	      if (file.createNewFile()){
	         this.saveLevelRecord();
	      }else{
	         loadProfileRecord();
	      }
              }catch(IOException e){ e.printStackTrace();}*/
    }
      
      
      
          public static String loadTextFile(String textFile) throws IOException
   {
       // ADD THE PATH TO THE FILE
       PropertiesManager props = PropertiesManager.getPropertiesManager();
       textFile = JTEConstant.DATA_PATH + textFile;
       System.out.println(textFile);
       // WE'LL ADD ALL THE CONTENTS OF THE TEXT FILE TO THIS STRING
       String textToReturn = "";
      
       // OPEN A STREAM TO READ THE TEXT FILE
       FileReader fr = new FileReader(textFile);
       BufferedReader reader = new BufferedReader(fr);
           
       // READ THE FILE, ONE LINE OF TEXT AT A TIME
       String inputLine = reader.readLine();
       while (inputLine != null)
       {
           // APPEND EACH LINE TO THE STRING
           textToReturn += inputLine + "\n";
           // READ THE NEXT LINE
           inputLine = reader.readLine();        
       }
       
       // RETURN THE TEXT
       return textToReturn;
   }
          
         public void saveLevelRecord() throws IOException
	 {
		try{
	
         byte [] rec = this.game.getDataModel().toByteArray();
         File file   = new File(JTEConstant.DATA_PATH+"PlayerData.jte");
         //ByteArrayInputStream baos = new ByteArrayInputStream(rec);
         //DataInputStream dis = new DataInputStream(baos);
         FileOutputStream  fos =  new FileOutputStream(file);
         DataOutputStream  dos =  new DataOutputStream(fos);
         
         dos.write(rec);
         dos.close();
		}catch(IOException e){System.out.println("Exception"+ e.getMessage());}
	 }
    
    
     
	
	 public void loadJTERecord()
	    {
	          try
	        {
	          
	            File fileToOpen = new File(JTEConstant.DATA_PATH+"PlayerData.jte");

	            // LET'S USE A FAST LOADING TECHNIQUE. WE'LL LOAD ALL OF THE
	            // BYTES AT ONCE INTO A BYTE ARRAY, AND THEN PICK THAT APART.
	            // THIS IS FAST BECAUSE IT ONLY HAS TO DO FILE READING ONCE
	            byte[] bytes = new byte[Long.valueOf(fileToOpen.length()).intValue()];
	            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
	            FileInputStream fis = new FileInputStream(fileToOpen);
	            BufferedInputStream bis = new BufferedInputStream(fis);
	            
	            // HERE IT IS, THE ONLY READY REQUEST WE NEED
	            bis.read(bytes);
	            bis.close();
	            
	            // NOW WE NEED TO LOAD THE DATA FROM THE BYTE ARRAY
	            DataInputStream dis = new DataInputStream(bais);
	            
	            // NOTE THAT WE NEED TO LOAD THE DATA IN THE SAME
	            // ORDER AND FORMAT AS WE SAVED IT
	            // FIRST READ THE NUMBER OF LEVELS
	            int numPlayers = dis.readInt();
                    this.game.setMaxNumberOfPlayers(dis.readInt());
                    TreeMap<String,JTEPlayers>player = new TreeMap();
           
	            for (int i = 0; i < numPlayers; i++)
	            {
	                
                        String key = dis.readUTF();
                        String name =  dis.readUTF();
                        int r = dis.readInt(); int c =  dis.readInt();
                        float coordx =  dis.readFloat(); float coordy =  dis.readFloat();
                        JTESprite spr =  new JTESprite(coordx,coordy);
                        JTEPlayers play = new JTEPlayers(name,
                        this.game.getFlagButtonImg()[r][c],
                        this.game.loadSpriteSheetImages(JTEConstant.IMG+this.game.getPieceButtonImg()[r][c], 12,3,4,0,0),
                        null,spr);
                        play.setNumOfCard(dis.readInt());
                        play.setPlayerNumSteps(dis.readInt());
                        play.addToScore(dis.readLong());
                        play.setBoat(this.game.getBoat());
                        play.setAir(this.game.getAir());
                        //read start and end city for player
                        Iterator<JTECity>allCities =  this.game.getCityIterator();
                        String cityName =  dis.readUTF();
                        while(allCities.hasNext())
                        {
                           JTECity city = allCities.next();
                           if(city.getCityName().equals(cityName))
                           {
                               play.setStartingNodeLocation(city);
                               play.setDestinationNodeLocation(city);
                               break;
                           }
                        }
                        
                        //read player current city
                        allCities =  this.game.getCityIterator();
                        cityName =  dis.readUTF();
                        while(allCities.hasNext())
                        {
                           JTECity city = allCities.next();
                           if(city.getCityName().equals(cityName))
                           {
                               play.setPlayerCurrentCity(city);
                               break;
                           }
                        }
                        
                       /* JTECity city =  new JTECity(dis.readUTF(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "");
                        city.setIsFlyPlanAvailable(dis.readBoolean());
                        city.setIsShipAvailable(dis.readBoolean());
                        city.setMakeLoseATurn(dis.readBoolean());
                        city.setAnExtraDiceThrow(dis.readBoolean());
                        city.setHasDescription(dis.readBoolean());
                        play.setStartingNodeLocation(city);
                        play.setDestinationNodeLocation(city);*/
                                
                        //player current city
                      
                        /*city =  new JTECity(dis.readUTF(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "");
                        city.setIsFlyPlanAvailable(dis.readBoolean());
                        city.setIsShipAvailable(dis.readBoolean());
                        city.setMakeLoseATurn(dis.readBoolean());
                        city.setAnExtraDiceThrow(dis.readBoolean());
                        city.setHasDescription(dis.readBoolean());
                        play.setPlayerCurrentCity(city);*/
                        
                         /*city =  new JTECity(dis.readUTF(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    dis.readInt(),
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "");
                        city.setIsFlyPlanAvailable(dis.readBoolean());
                        city.setIsShipAvailable(dis.readBoolean());
                        city.setMakeLoseATurn(dis.readBoolean());
                        city.setAnExtraDiceThrow(dis.readBoolean());
                        city.setHasDescription(dis.readBoolean());
                        visitedC.add(city);*/
                                
                       //all player visited cities on journey
                        ArrayList<JTECity>visitedC =  new ArrayList();
                        int max=dis.readInt();
                        for(int v=0; v<max; v++)
                        {
                            allCities =  this.game.getCityIterator();
                            String readedCity =  dis.readUTF();
                            while(allCities.hasNext())
                            {
                                JTECity city =  allCities.next();
                                 if(city.getCityName().equals(readedCity))
                                 {visitedC.add(city); break;}
                          
                            }
                            
                        }
                     
                        play.setVisitedCities(visitedC);
                        
                       //player cards
                        ArrayList<JTESpriteCards>card = new ArrayList();
                        int maxCards =  dis.readInt();
                        for(int z=0; z<maxCards; z++)
                        {
                            String cardName = dis.readUTF();
                            String cardInfp = dis.readUTF();
                            JTESpriteCards cards = new JTESpriteCards(cardName,
                                                                     game.getCards().get(cardName).getCityImg(),
                                                                     game.getCards().get(cardName).getCityDescImg(),
                                                                     cardInfp );
                            cards.setCardEnabledToShuffle(dis.readBoolean());
                            card.add(cards);
                        }
	                play.setCard(card);
                        player.put(key, play);
	               
                    }//close loop
                   
                      //now lets extract the arraylist of players as well because i am using it on my render class
                      int max =  dis.readInt();
                      ArrayList<String>PlayersPlaying  = new ArrayList();
                      for(int i=0; i<max; i++)
                      {
                          PlayersPlaying.add(dis.readUTF());
                      }
                      this.game.setPlayers(player);
                      this.game.setPlayerNamesToPlay(PlayersPlaying);
                      
                      
                      System.out.println("players "+player);
                      
     	        }
	        catch(Exception e)
	        {
                    e.printStackTrace();
	            
	        } 

            }
         
	    
          

}
