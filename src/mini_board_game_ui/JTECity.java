/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_ui;
/**
 * This class represents an intersection in a level. Note that an intersection
 * connects roads and can be thought of as a node on a graph.
 * 
 * @author Richard McKenna
 */
public class JTECity
{
    public int x;
    public int y;
    String cityName;
    public boolean isFlyPlanAvailable;
    public boolean isShipAvailable;
    public int stepToadd;
    public int stepTosub;
    public long score ;

   
    public boolean makeLoseATurn;
    public boolean anExtraDiceThrow;
    public boolean hasDescription;
    public int flyPlan;
    public boolean hasPlayer = false;
    private boolean isVisited =  false;

  
        /**
     * Constructor allows for a custom location, note that all
     * intersections start as open.
     */
    public JTECity(String cityN, int initX, int initY, int stepToadd, int stepTosub,
            String isFlyPlan,String isShip,String loseTurn, String extraDice, String hasDesc)
    {
        this.cityName = cityN;
        x = initX;
        y = initY;
        if(hasDesc.equals("true"))
        this.hasDescription = true;
        else
        this.hasDescription = false;
        this.hasPlayer =  false;
        this.stepToadd =  stepToadd;
        this.stepTosub = stepTosub;
        if(isFlyPlan.equals("true"))
        this.isFlyPlanAvailable =  true;
        else
        this.isFlyPlanAvailable =  false; 
        
        if(isShip.equals("true"))
         this.isShipAvailable =  true;
        else
         this.isShipAvailable =  false; 
       
         if(loseTurn.equals("true"))
         this.makeLoseATurn  =  true;
        else
         this.makeLoseATurn  =  false; 
        
         
          if(extraDice.equals("true"))
         this.anExtraDiceThrow  =  true;
        else
         this.anExtraDiceThrow  =  false; 
      
        score = 0;
        isVisited = false;
           // BY DEFAULT THE AABB FITS THE SPRITE. NOTE THAT
        // ONE MAY CHANGE THIS USING THE SET METHODS
      
    }
    
    public JTECity(int x, int y)
    {
        this.x =  x;
        this.y =  y;
    }

    public boolean isIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean itHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
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

    public boolean isHasDescription() {
        return hasDescription;
    }

    public void setHasDescription(boolean hasDescription) {
        this.hasDescription = hasDescription;
    }

    public boolean isIsShipAvailable() {
        return isShipAvailable;
    }

    public void setIsShipAvailable(boolean isShipAvailable) {
        this.isShipAvailable = isShipAvailable;
    }

    

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public boolean isIsFlyPlanAvailable() {
        return isFlyPlanAvailable;
    }

    public void setIsFlyPlanAvailable(boolean isFlyPlanAvailable) {
        this.isFlyPlanAvailable = isFlyPlanAvailable;
    }

    public int getStepToadd() {
        return stepToadd;
    }

    public void setStepToadd(int stepToadd) {
        this.stepToadd = stepToadd;
    }

    public int getStepTosub() {
        return stepTosub;
    }

    public void setStepTosub(int stepTosub) {
        this.stepTosub = stepTosub;
    }

    public boolean isMakeLoseATurn() {
        return makeLoseATurn;
    }

    public void setMakeLoseATurn(boolean makeLoseATurn) {
        this.makeLoseATurn = makeLoseATurn;
    }

    public boolean isAnExtraDiceThrow() {
        return anExtraDiceThrow;
    }

    public void setAnExtraDiceThrow(boolean anExtraDiceThrow) {
        this.anExtraDiceThrow = anExtraDiceThrow;
    }
    // INTERSECTION LOCATION
 
     public int getFlyPlan() {
        return flyPlan;
    }

    public void setFlyPlan(int flyPlan) {
        this.flyPlan = flyPlan;
    }
     public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
    

     public boolean containsPoint(float pointX, float pointY)
    {
       
        // FIRST MOVE THE POINT TO LOCAL COORDINATES
        pointX = pointX - (x);
        pointY = pointY - (y);
       
        boolean inXRange = false;
        if ((pointX > -60) && (pointX < (60)))
        {
            inXRange = true;
        }
        boolean inYRange = false;
        if ((pointY > -60) && (pointY < (60)))
        {
            inYRange = true;
        }
        return inXRange && inYRange;
    }


    public boolean testForClickOnCity(JTEuropeUI game, int x, int y)
    {
     
        if (containsPoint(x, y))
        {
           game.ReceivedEventHandlesForCity(this);
            return true;
        } else
        {
            System.out.println("this = "+this+" not contain point");
            return false;
        }
    }
    /**
     * Returns a textual representation of this intersection.
     */
    @Override
    public String toString()
    {
      return "(" + x + ", " + y + " name ="+this.cityName+")";
    }
}