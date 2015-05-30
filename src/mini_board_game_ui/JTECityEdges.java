/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_ui;

public class JTECityEdges
{
    // THESE ARE THE EDGE'S NODES
    JTECity node1;
    JTECity node2;
    

    // ACCESSOR METHODS
    public JTECity getNode1()  {   return node1;       }
    public JTECity getNode2()  {   return node2;       }

    
    // MUTATOR METHODS
    public void setNode1(JTECity node1)    {   this.node1 = node1;             }
    public void setNode2(JTECity node2)    {   this.node2 = node2;             }
  
    /**
     * Builds and returns a textual representation of this road.
     */
    @Override
    public String toString()
    {
        return node1 + " - " + node2;
    }
}