/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_ui;

import javafx.scene.image.Image;

/**
 *
 * @author ProgrammingSection
 */
public class JTESpriteCards {
    private String CardName;
    private Image cityName;
    private Image cityDesc;
    private String CardDesc;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private boolean isVisible;
    private boolean isCardEnable;
    
    public JTESpriteCards(String city,Image cityName, Image cityDesc,String cardDec)
    { this.CardName = city;
      this.cityName = cityName;
      this.cityDesc = cityDesc;
      this.CardDesc = cardDec;
      isVisible =  true;
      isCardEnable = true;
      x=y=vx=vy=0;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }
    
    public void setX(float x)
    {this.x = x;}
    public void setY(float y)
    {this.y = y;}
    public void setVX(float s)
    {this.vx = s;}
    public void setVY(float s)
    {this.vy = s;}
    public void setCityImg(Image n)
    {this.cityName = n;}
    public void setCityDImg(Image n)
    {this.cityDesc = n;}
    public void setCityDescription(String d)
    {this.CardDesc = d;}
    public void setVisible(boolean v)
    {this.isVisible = v;}
    public void setCardEnabledToShuffle(boolean t)
    {this.isCardEnable =  t;}
    
    public float getX()
    {return x;}
    public float getY()
    {return y;}
    public float getvX()
    {return vx;}
    public float getvY()
    {return vy;}
    public boolean getCardEnableToShuffle()
    {return this.isCardEnable;}
    public Image getCityImg()
    {return this.cityName;}
    public Image getCityDescImg()
    {return this.cityDesc;}
    public String getCityInfo()
    {return this.CardDesc;}
    public boolean getVisible()
    {return this.isVisible;}
    
    public String toString()
    {return "x ="+
            x+" y ="+y+" cityName :"+this.CardName
            +" cityImg :"+this.cityName+" cityDescImg :"+this.cityDesc
            +" cityDescription :"+"\n"+this.CardDesc;
            }

}
