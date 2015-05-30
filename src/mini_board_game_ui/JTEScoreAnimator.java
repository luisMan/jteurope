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

public class JTEScoreAnimator {
    private String message;
    private int x, y;
    private String nodeType;
    
    public JTEScoreAnimator(String msg, int x, int y,String nodeT)
    {
    	message = msg;
    	this.x =  x;
    	this.y =  y;
    	nodeType = nodeT;
    }
    
    //setters 
    public void setString(String msg)
    {message=msg;}
    public void setStringType(String msg)
    {nodeType=msg;}
    public void setX(int x)
    {this.x =  x;}
    public void setY(int y)
    {this.y =  y;}
    
    
    
    //getters
    public String getString()
    {return message;}
    public String getNodeT()
    {return nodeType; }
    public int getX()
    {return x;}
    public int getY()
    {return y;}
    
    public void upDateYUp()
    {  y-=10;}
    public void upDateYDown()
    { y+=10;}

    public void upDateXright()
    {  x+=10;}
    public void upDateXleft()
    { x-=10;}
    
    
    
   
}
