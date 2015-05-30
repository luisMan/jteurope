/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_ui;

/**
 *
 * @author ProgrammingSection
 */
public class JTEViewPort {
    private JTEuropeUI game;
    private float x, y;
    private float zoom;
    private long width, height;
    private int speed;
    
    public JTEViewPort(JTEuropeUI game)
    {
        this.game =  game;
        x=0; y=0;
        zoom = 0.0f;
        width =  0 ; height =  0;
        speed = 0;
    }
    
    public void setScrollSpeed(int speed)
    {this.speed =  speed;}
    public void setX(int x)
    {this.x=x;}
    public void setY(int y)
    {this.y=y;}
    public void setZoom(float z)
    {this.zoom =  z;}
    
    
    //getters
    public int getViewPortSpeed()
    {return this.speed;}
    public float getViewPortX()
    {return this.x;}
    public float getViewPortY()
    {return this.y;}
    public float getViewPortZoom()
    {return  this.zoom;}
    public long getViewPortGameWidth()
    {return this.width;}
    public long getViewPortGameHeight()
    {return this.height;}
    
    
    
    
    
    
    
    
    public void setGameWorldSize(long width, long height)
    {this.width =  width; this.height=height;}
    public void scrollViewPortLeft()
    {if(x>(-1290)){x-=speed;}}    
    public void scrollViewPortRight()
    {if(x<0){x+=speed;}}
    public void scrollViewPortTop()
    {if(y>(-3420)){y-=speed;}}
    public void  scrollViewPortDown()
    {if(y<(0)){y+=speed;}}
}
