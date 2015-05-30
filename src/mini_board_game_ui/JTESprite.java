/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_ui;



import java.awt.event.ActionListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import jteurope.JTEurope;
import mini_board_game_ui.JTEuropeUI.JTEuropeScreenState;


public class JTESprite
{

    private JTESpriteType spriteType;
    
 
    private boolean enabled;
    
  
    private float x;
    private float y;
    

    private float vX;
    private float vY;
   
    private String state;
    
    // AABB REFERS TO AXIS-ALIGNED-BOUNDING-BOX, WHICH IS A LONG
    // WAY OF SAYING A SQUARE COLLISION BOX. IN SOME GAMES THE
    // COLLISION BOX MAY NEED TO BE SMALLER THAN THE ARTWORK, AND
    // MAY EVEN CHANGE PERIODICALLY.
    private float aabbX;
    private float aabbY;
    private float aabbWidth;
    private float aabbHeight;
    

    // ID NUMBER OF THIS Sprite, THIS IS AUTOMATICALLY ASSIGNED UPON
    // Sprite CONSTRUCTION. NOTE THAT THIS IS A READ-ONLY VALUE
    private int id;
    
    // USED FOR ASSIGNING ID NUMBERS TO SPRITES. EACH TIME ANOTHER
    // Sprite OBJECT IS CONSTRUCTED, WE INCREMENT THIS COUNTER, THUS
    // GETTING A NEW, UNIQUE NUMBER
    private static int idCounter;

    
    public JTESprite(JTESpriteType initSpriteType,
            float initX, float initY,
            float initVx, float initVy,
            String initState)
    {
        // INIT WITH THE PARAMETERS PROVIDED
        spriteType = initSpriteType;
        x = initX;
        y = initY;
        vX = initVx;
        vY = initVy;
        state = initState;
        id = idCounter;

        // IT IS ENABLED BY DEFAULT
        enabled = true;

        // CHANGE THE ID COUNTER FOR THE NEXT Sprite
        idCounter++;

        // BY DEFAULT THE AABB FITS THE SPRITE. NOTE THAT
        // ONE MAY CHANGE THIS USING THE SET METHODS
        aabbX = 0;
        aabbY = 0;
        aabbWidth = (float)spriteType.getWidth();
        aabbHeight = (float)spriteType.getHeight();
    }

    
    //this contructor is just to get info from the coordinates selector to render 
    //graph card shufflers
    public JTESprite(float x,float y)
    {
        this.x =  x; this.y = y;
    }
    
    // ACCESSOR METHODS
        // getActionCommand
        // getSpriteType
        // getAABBx
        // getAABBy
        // getAABBwidth
        // getAABBheight
        // getX
        // getY
        // getVx
        // getVy
        // getState
        // getID
        // isEnabled
    
    /**
     * For accessing the axis-aligned bounding box' x-axis coordinate
     *
     * @return the aabb's x value
     */
    public float getAABBx()
    {
        return aabbX;
    }

    /**
     * For accessing the axis-aligned bounding box' y-axis coordinate
     *
     * @return the aabb's y value
     */
    public float getAABBy()
    {
        return aabbY;
    }

    /**
     * For accessing the axis-aligned bounding box' width
     *
     * @return the aabb's width
     */
    public float getAABBwidth()
    {
        return aabbWidth;
    }

    /**
     * For accessing the axis-aligned bounding box' height
     *
     * @return the aabb's height
     */
    public float getAABBheight()
    {
        return aabbHeight;
    }
    
  
    /**
     * For accessing the SpriteType object.
     *
     * return the SpriteType of this sprite.
     */
    public JTESpriteType getSpriteType()
    {
        return spriteType;
    }

    /**
     * For accessing the X-axis coordinate of this Sprite.
     *
     * return the x-axis location of this Sprite according to the top-left
     * corner of the image used.
     */
    public float getX()
    {
        return x;
    }

    /**
     * For accessing the Y-axis coordinate of this Sprite.
     *
     * @return the y-axis location of this Sprite according to the top-left
     * corner of the image used.
     */
    public float getY()
    {
        return y;
    }

    /**
     * For accessing the X-axis velocity of this Sprite. Note that velocity
     * refers to pixels (game world units) moved each frame.
     *
     * @return the x-axis velocity of this Sprite.
     */
    public float getVx()
    {
        return vX;
    }

    /**
     * For accessing the Y-axis velocity of this Sprite. Note that velocity
     * refers to pixels (game world units) moved each frame.
     *
     * @return the y-axis velocity of this Sprite.
     */
    public float getVy()
    {
        return vY;
    }

    /**
     * For accessing the current state of this Sprite. The state determines
     * which image is used for rendering.
     *
     * @return the current Sprite state.
     */
    public String getState()
    {
        return state;
    }

    /**
     * For accessing the automatically generated ID number of this Sprite
     * object.
     *
     * @return the ID number of this Sprite. Note that each Sprite has a unique
     * id, which may or many not be used by the game application developer for
     * things like event handling.
     */
    public int getID()
    {
        return id;
    }

    /**
     * This method checks to see if this sprite is enabled or not. If it is
     * enabled, true is returned, else false.
     *
     * @return true if this sprite is enabled, else false.
     */
    public boolean isEnabled()
    {
        return enabled;
    }



    /**
     * Mutator method for setting the type of Sprite this is.
     *
     * @param initSpriteType the SpriteType to use for rendering this sprite.
     */
    public void setSpriteType(JTESpriteType initSpriteType)
    {
        spriteType = initSpriteType;
    }

    /**
     * Mutator method for setting the x-axis location of this Sprite.
     *
     * @param initX the x-axis location to move this Sprite to. Note that if a
     * value less than 0 or greater than the game world width is provided the
     * Sprite may no longer be visible, since it won't be in the game canvas.
     */
    public void setX(float initX)
    {
        x = initX;
    }

    /**
     * Mutator method for setting the y-axis location of this Sprite.
     *
     * @param initY the y-axis location to move this Sprite to. Note that if a
     * value less than 0 or greater than the game world height is provided the
     * Sprite may no longer be visible, since it won't be in the game canvas.
     */
    public void setY(float initY)
    {
        y = initY;
    }

    /**
     * Mutator method for setting the x-axis velocity of this Sprite.
     *
     * @param initVx the x-axis velocity to be used. Note that this refers to
     * the amount to move this Sprite each frame in the x-axis.
     */
    public void setVx(float initVx)
    {
        vX = initVx;
    }

    /**
     * Mutator method for setting the y-axis velocity of this Sprite.
     *
     * @param initVy the y-axis velocity to be used. Note that this refers to
     * the amount to move this Sprite each frame in the y-axis.
     */
    public void setVy(float initVy)
    {
        vY = initVy;
    }

    /**
     * Mutator method for setting the state of this Sprite.
     *
     * @param initState the state to use for this Sprite, which will determine
     * which image to use for rendering. Note that one should only set the state
     * to one of the states made available by this Sprite's SpriteType. Using a
     * different will result in error.
     */
    public void setState(String initState)
    {
        state = initState;
    }

    /**
     * Mutator method for enabling and disabling this sprite.
     *
     * @param initEnabled This value enables and disables this sprite.
     */
    public void setEnabled(boolean initEnabled)
    {
        enabled = initEnabled;
    }

    // ADDITIONAL METHODS
        // aabbsOverlap
        // calculateDistanceToSprite
        // containsPoint
        // testForClick
        // update
    
    /**
     * This method tests to see if the the testSprite's AABB overlaps this
     * Sprite's AABB. true is returned if they overlap, false otherwise.
     *
     * return true if this Sprite's bounding box overlaps the testSprite's
     * bounding box. false is returned otherwise.
     */
    public boolean aabbsOverlap(JTESprite testSprite)
    {
        if ((x + aabbX) > (testSprite.x + testSprite.aabbX + testSprite.aabbWidth))
        {
            return false;
        } else if ((x + aabbX + aabbWidth) < (testSprite.x + testSprite.aabbX))
        {
            return false;
        } else if ((y + aabbY) > (testSprite.y + testSprite.aabbY + testSprite.aabbHeight))
        {
            return false;
        } else if ((y + aabbY + aabbHeight) < (testSprite.y + testSprite.aabbY))
        {
            return false;
        } else
        {
            return true;
        }
    }

    public float calculateDistanceToSprite(JTESprite targetSprite)
    {
        float targetSpriteCenterX = targetSprite.x + targetSprite.aabbX + (targetSprite.aabbWidth / 2);
        float targetSpriteCenterY = targetSprite.y + targetSprite.aabbY + (targetSprite.aabbHeight / 2);

        float centerX = x + aabbX + (aabbWidth / 2);
        float centerY = y + aabbY + (aabbHeight / 2);

        float deltaX = targetSpriteCenterX - centerX;
        float deltaY = targetSpriteCenterY - centerY;

        return (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public boolean containsPoint(float pointX, float pointY)
    {
        // FIRST MOVE THE POINT TO LOCAL COORDINATES
        pointX = pointX - x;
        pointY = pointY - y;

        boolean inXRange = false;
        if ((pointX > aabbX) && (pointX < (aabbX + aabbWidth)))
        {
            inXRange = true;
        }
        boolean inYRange = false;
        if ((pointY > aabbY) && (pointY < (aabbY + aabbHeight)))
        {
            inYRange = true;
        }
        return inXRange && inYRange;
    }


    public boolean testForClick(JTEuropeUI game, int x, int y)
    {
        if (!enabled)
        {
            return false;
        }

        if (containsPoint(x, y))
        {
           game.ReceivedEventHandles(this);
          
            return true;
        } else
        {
            return false;
        }
    }

   
    public void update(JTEuropeUI game)
    {
        // MOVE THE SPRITE USING ITS VELOCITY
        x += vX;
        y += vY;
    }
    
    
    public String toString()
    {return "x= "+x+" y= "+y+" spriteT= "+getSpriteType()+" enable = "+enabled+" state ="+state+" img ="
            +this.getSpriteType().getStateImage(this.getSpriteType().getSpriteTypeID())+" spriteId = "+this.getSpriteType().getSpriteTypeID();}
}