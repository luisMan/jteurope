/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_ui;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author ProgrammingSection
 */

public class JTESpriteType
{
  
    private String spriteTypeID;
    
    private HashMap<String, Image> states;
    
 
    private double width;
    private double height;

    public JTESpriteType(String initSpriteTypeID)
    {
        spriteTypeID = initSpriteTypeID;
        states = new HashMap();
        width = -1;
        height = -1;
    }

    public String getSpriteTypeID()
    {
        return spriteTypeID;
    }

    public Image getStateImage(String stateName)
    {
        return states.get(stateName);
    }

  
    public double getHeight()
    {
        return height;
    }

 
    public double getWidth()
    {
        return width;
    }

    public void addState(String stateName, Image img)
    {
        if (states.isEmpty())
        {
            width = img.getWidth();
            height = img.getHeight();
        }
       
      
        states.put(stateName,img);
   
    }
    
    public void setDimensions(int initWidth, int initHeight)
    {
        width = initWidth;
        height = initHeight;
    }
    
   
    
}
