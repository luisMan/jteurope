/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_ui;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author ProgrammingSection
 */
public class JTEPlayers {
    
    private String playerName;
    private ArrayList<Image> playerDice;
    private ArrayList<Image>boat;
    private ArrayList<Image>air;
    private Image playerFlag;
    private ArrayList<JTESpriteCards>card;
    private int numSteps;
    private int numOfCards;
    private boolean onAir =false;
    private boolean onBoat =false;
   
    private JTESprite coord;
    private boolean PlayerTurn ;
    private Image flyP;

  
    private int flyPlanType =-1;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private ArrayList<JTECity>cities;
    private JTECity StartLocation;
    private JTECity DestinationLocation;
    private JTECity currentCity;
    private long score;
    private int imgC,imgR;
    private int animationIndex=0;
    private int animationMax =0;

  
    
    public JTEPlayers(String name, Image img,ArrayList<Image>dice,Image flyPlan,JTESprite coord)
    {
        playerName = name;
        playerFlag =  img;
        playerDice=  dice;
        flyP = flyPlan;
        this.coord =  coord;
        card =  new ArrayList();
        cities =  new ArrayList();
        air  =  new ArrayList();
        boat =  new ArrayList();
        numSteps =  0;
        PlayerTurn  = false;
        x=0; y=0;
        vx = 0; vy=0;
        score = 0;numOfCards = 0;
        currentCity = null;
        this.animationIndex =0;
        animationMax =0;
        onAir =onBoat=false;
      
    }
    
   public Image getFlyP() {
        return flyP;
    }

    public void setFlyP(Image flyP) {
        this.flyP = flyP;
    }
    public int getImgC() {
        return imgC;
    }

    public void setImgC(int imgC) {
        this.imgC = imgC;
    }

    public int getImgR() {
        return imgR;
    }
        public ArrayList<Image> getBoat() {
        return boat;
    }

    public void setBoat(ArrayList<Image> boat) {
        this.boat = boat;
    }

    public ArrayList<Image> getAir() {
        return air;
    }

    public void setAir(ArrayList<Image> air) {
        this.air = air;
    }

    public void setPlayerOnAir(boolean d)
    {this.onAir =  d;}
    public void setPlayerOnBoat(boolean d)
    {this.onBoat = d;}
    public boolean getPlayerOnAir()
    {return this.onAir;}
    public boolean getPlayerOnBoat()
    {return this.onBoat;}

    public void setImgR(int imgR) {
        this.imgR = imgR;
    }
    public void setPlayerName(String n)
    {this.playerName =  n;}
    public void setPlayerImg(ArrayList<Image> img)
    {this.playerDice =  img;}
    public void setPlayerPreviousCardRecord(ArrayList<JTESpriteCards>record)
    {this.card =  record;}
    public void setPlayerNumSteps(int x)
    {this.numSteps = x;}
    public void addPlayerStep(int a)
    {this.numSteps+=a;}
    public void subPlayerStep(int s)
    {this.numSteps-=s;}
    public void incStep()
    {this.numSteps++;}
    public void decStep()
    {this.numSteps--;}
    public void setPlayerTurn(boolean t)
    {this.PlayerTurn =t;}
    public void setPlayerX(float x)
    {this.x =x;}
    public void setPlayerY(float y)
    {this.y =y;}
    public void setVx(float x)
    {this.vx = x;}
    public void setVy(float y)
    {this.vy = y;}
    public void addToScore(long score)
    {this.score+=score;}
    public void subToScore(long score)
    {this.score-=score;}
    public long getScore()
    {return this.score;}
    public void incCardNum()
    {numOfCards+=1;}
    public void decCardNum()
    {numOfCards-=1;}
    public int getNumOfCard()
    {return numOfCards;}
    public void setNumOfCard(int x)
    {this.numOfCards =x;}
    //getters
    public String  getPlayerName()
    {return this.playerName;}
    public ArrayList<Image> getPlayerImg()
    {return this.playerDice;}
    public ArrayList<Image> getPlayerAir()
    {return this.air;}
    public ArrayList<Image>getPlayerBoat()
    {return this.boat;}
    public Image getPlayerFlag()
    {return this.playerFlag;}
    public int getPlayerNumSteps()
    {return this.numSteps;}
    public void setPlayerCurrentCity(JTECity current)
    {this.currentCity=current;}
    
    public int getFlyPlanType() {
        return flyPlanType;
    }

    public void setFlyPlanType(int flyPlanType) {
        this.flyPlanType = flyPlanType;
    }
    
    
   public JTECity getPlayersCurrentCity()
   {return this.currentCity;}
    public boolean getPlayerTurn()
    {return this.PlayerTurn;}
    public float getPlayerX()
    {return x;}
    public float getPlayerY()
    {return this.y;}
    public float getVX()
    {return this.vx;}
    public float getVY()
    {return this.vy;}
     public int getAnimationIndex() {
        return animationIndex;
    }

    public void setAnimationIndex(int animationIndex) {
        animationMax  = animationIndex;
    }
    
    public void addCity(JTECity c)
    {
        cities.add(c);
    }
    public void removeCity(JTECity c)
    { 
        cities.remove(c);
    }
    
    public ArrayList<JTECity>getVisitedCities()
    {return cities;}
    public void setVisitedCities(ArrayList<JTECity>getVisitedCities)
    {cities =  getVisitedCities;}

     public void setCard(ArrayList<JTESpriteCards> card) {
        this.card = card;
    }
    public void setStartingNodeLocation(JTECity start)
    {this.StartLocation =  start;}
    public void setDestinationNodeLocation(JTECity end)
    {this.DestinationLocation =  end;}
    public JTECity getStartingNodeLocation()
    {return this.StartLocation;}
    public JTECity getDestinationNodeLocation()
    {return this.DestinationLocation;}
    public JTESprite getCardRendererCoord()
    {return this.coord;}
    
    public void resetPlayerPlay()
    {
        this.PlayerTurn =  false;
        this.numSteps = 0;
    }
    public boolean isNodeAllow(JTECity node)
    { 
        if(cities.size()>2)
        {  
           if(cities.get(cities.size()-2).x == node.x&&cities.get(cities.size()-2).y==node.y)
               return false;
        }
     return true;
    }
    
    public void animateCharacterWalking()
    {
        if(this.animationIndex<animationMax+2)
        { if(this.animationIndex<11){this.animationIndex+=1;}}
        else{
              this.animationIndex = this.animationIndex-2;
        }
        
   
    }
    public void addPlayerCard(JTESpriteCards card)
    {this.card.add(card);}
    public ArrayList<JTESpriteCards>getPlayerCards()
    {return card;}
     public void removePlayerCard(JTESpriteCards card)
    {this.card.remove(card);}
    public String toString()
    {return "name = "+this.playerName
            +"\nNumberOfSteps = "+this.numSteps;
            }
}

