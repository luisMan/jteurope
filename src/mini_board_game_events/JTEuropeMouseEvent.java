/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_events;

import java.util.concurrent.locks.ReentrantLock;
import javafx.scene.input.MouseEvent;
import mini_board_game_ui.JTEuropeUI;

/**
 *
 * @author ProgrammingSection
 */
public class JTEuropeMouseEvent {
     private JTEuropeUI game;
     private ReentrantLock lock;
     
    public JTEuropeMouseEvent(JTEuropeUI game)
    {this.game =  game; lock =  new ReentrantLock();}
    
    public void MousePressed(MouseEvent e)
    {
        try{
            lock.lock();
             // GET THE COORDINATES
            int x = (int)e.getX();
            int y = (int)e.getY();
            System.out.println("mouse clicked "+e.getX()+"  "+e.getY());
            
            // FIRST CHECK THE GUI BUTTONS
            boolean buttonClicked = game.processButtonPress(x, y);
            boolean cityClicked =  game.processButtonPressOnCity(x,y);

            // IF IT WAS NOT A GUI BUTTON, THEN WE SHOULD
            // EXECUTE THE CUSTOM GAME RESPONSE
            if (!buttonClicked && game.getDataModel().inProgress())
            {
               
               game.checkMousePressOnSprites(game, x, y);
           
            }
            if (!cityClicked && game.getDataModel().inProgress())
            {
               game.checkMousePressOnCity(game, x,y);
            }
            
        }finally{
            lock.unlock();
        }
        
    }
    
    public void MouseClicked(MouseEvent e)
    {
          try{
            lock.lock();
            System.out.println("mouse clicked "+e.getX()+"  "+e.getY());
            
        }finally{
            lock.unlock();
        }
    }
    
    public void MouseMoved(MouseEvent e)
    {
        
        try{
            lock.lock();
            int x = (int)e.getX();
            int y = (int)e.getY();

           

            // IF IT WAS NOT A GUI BUTTON, THEN WE SHOULD
            // EXECUTE THE CUSTOM GAME RESPONSE
            if (game.getDataModel().inProgress())
            {
               
               game.checkMouseOverOnSprites(game, x, y);
              
            }
            
        }finally{
            lock.unlock();
        }
    }
    
    public void MouseReleased(MouseEvent e)
    {
         try{
            lock.lock();
             // GET THE COORDINATES
            int x = (int)e.getX();
            int y = (int)e.getY();
            System.out.println("mouse clicked "+e.getX()+"  "+e.getY());
            
          
            boolean cityClicked =  game.processButtonPressOnCity(x,y);

           
            if (!cityClicked && game.getDataModel().inProgress())
            {
               game.checkMousePressOnCity(game, x,y);
            }
            
        }finally{
            lock.unlock();
        }
        
    }
    public void MouseDragged(MouseEvent e)
    {
        
          try{
            lock.lock();
            System.out.println("mouse Dragged "+e.getX()+"  "+e.getY());
            
        }finally{
            lock.unlock();
        }
    }
}
