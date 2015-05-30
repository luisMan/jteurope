/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_board_game_data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import mini_board_game_ui.JTECity;
import mini_board_game_ui.JTECityEdges;
import mini_board_game_ui.JTEPlayers;
import mini_board_game_ui.JTEuropeUI;

/**
 *
 * @author ProgrammingSection
 */
public class JTEuropePathFinding {

    private ArrayList<JTECity> path;
    private Queue<JTECity> queue;
    private Iterator<JTECityEdges> edges;
    private JTEuropeDataModel model;
    private int queueLength = 0;
    private JTEPlayers currentPlayer;
    private JTEuropeUI game;
    private ArrayList<ArrayList<edgesCities>> cities;
    private ArrayList<edgesCities> edgesToCity;

    public JTEuropePathFinding(JTEuropeUI game) {
        this.game = game;
        this.model = model;
        path = new ArrayList();
        edges = this.game.getCityEdgesIterator();
        currentPlayer = null;
        initDFS();

    }

    public void initDFS() {
        cities = new ArrayList();
        for (int i = 0; i < this.game.getAllCities().size(); i++) {
            JTECity city = this.game.getAllCities().get(i);
            boolean isAdded = false;
            for (int b = 0; b < cities.size(); b++) {
                for (int d = 0; d < cities.get(b).size(); d++) {
                    if (city.x == cities.get(b).get(0).edge.x
                            && city.y == cities.get(b).get(0).edge.y) {
                        isAdded = true;
                    }
                }
            }

            if (!isAdded) {
                edgesToCity = new ArrayList();
                edgesCities node = new edgesCities(city);
                edgesToCity.add(node);

                for (int j = 0; j < this.game.getAllEdges().size(); j++) {

                    if (city.x == this.game.getAllEdges().get(j).getNode1().x
                            && city.y == this.game.getAllEdges().get(j).getNode1().y) {
                        //node 2 is a strong edge

                        JTECity nodeToLook = this.game.getAllEdges().get(j).getNode2();
                        for (int c = 0; c < this.game.getAllCities().size(); c++) {

                            if (this.game.getAllCities().get(c).x == nodeToLook.x
                                    && this.game.getAllCities().get(c).y == nodeToLook.y) {
                                if (this.game.getAllCities().get(c).x != city.x
                                        && this.game.getAllCities().get(c).y != city.y) {
                                    edgesCities toAdd = new edgesCities(this.game.getAllCities().get(c));
                                    double x = city.x - nodeToLook.x;
                                    double y = city.y - nodeToLook.y;
                                    double distance = Math.cos(y / x);
                                    if (distance < 0) {
                                        distance = distance * -1;
                                    }
                                    toAdd.setDistance(distance);
                                    boolean goodToAdd = true;
                                    for (int z = 0; z < edgesToCity.size(); z++) {
                                        if (edgesToCity.get(z).edge.x == toAdd.edge.x
                                                && edgesToCity.get(z).edge.y == toAdd.edge.y) {
                                            goodToAdd = false;
                                        }
                                    }
                                    if (goodToAdd) {
                                        edgesToCity.add(toAdd);
                                    }

                                }
                            }
                        }
                    }

                }

                cities.add(edgesToCity);

            }

        }

        //outAdjacent
        outAdjacent();
    }

    public void outAdjacent() {
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(cities.get(i));
        }
    }

    //GETTERS METHOD 
    public ArrayList<JTECity> getPath() {
        return path;
    }

    public boolean Empty() {
        return queueLength == 0;
    }

    public void clearPath() {
        path.clear();
    }

    public void setCurrentPlayer(JTEPlayers player) {
        this.currentPlayer = player;
    }

    public JTEPlayers getCurrentPlayer() {
        return this.currentPlayer;
    }

//this is the edgest class that will contain all the edgest to a visited city
    private class edgesCities {

        private JTECity edge;
        private double distance;

        public edgesCities(JTECity edg) {
            this.edge = edg;
        }

        public boolean isVisited() {
            return this.edge.isIsVisited();
        }

        public void setUnvisited(boolean c) {
            this.edge.setIsVisited(c);
        }

        public void setDistance(double d) {
            this.distance = d;
        }

        public double getDistance() {
            return this.distance;
        }

        public String toString() {
            return "" + this.edge + " distance " + this.distance;
        }
    }

    public void setAllUnvisited()
    {
        for(int i=0; i<this.game.getAllCities().size(); i++)
        {
            this.game.getAllCities().get(i).setIsVisited(false);
        }
    }
    //INIT QUEUE ALGORITHNM TO ADD TO THE LINKEDLIST CLASS using B first search
    public ArrayList<JTECity> BSF(JTECity from) {
        queue = new LinkedList();
        setAllUnvisited();
        path.clear();
        queue.add(from);
        JTECity destination = null;
         //find the index edge of current city node
        System.out.println("Destination = "+currentPlayer.getPlayerCards().get(currentPlayer.getPlayerCards().size()-1).getCardName()+" city = "+currentPlayer.getPlayerCards().get(currentPlayer.getPlayerCards().size()-1));
        for(int i=0; i<this.game.getAllCities().size(); i++)
        {
        if(currentPlayer.getPlayerCards().get(currentPlayer.getPlayerCards().size()-1).getCardName().equals(this.game.getAllCities().get(i).getCityName()))
        {destination  =  this.game.getAllCities().get(i);break;}
        }
        
        int indexVertice = -1;
        while (!queue.isEmpty()) {
            JTECity node = queue.remove();
            if(node!=null){
            path.add(node);
            node.setIsVisited(true);
            if (node.x ==  destination.x && node.y == destination.y) {
                return path;
            }
            //find the index edge of current city node
            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < cities.get(i).size(); j++) {
                    if (cities.get(i).get(0).edge.x == node.x
                            && cities.get(i).get(0).edge.y == node.y) {
                        indexVertice = i;
                    }
                }
            }//close nested loops

            //find the vest node with more distance
            JTECity nodeToAdd = null;
            double dis = 99;
            for (int i = 0; i < cities.get(indexVertice).size(); i++) {
                if (!cities.get(indexVertice).get(i).isVisited() && cities.get(indexVertice).get(i).distance<dis) {
                   if(currentPlayer.getVisitedCities().size()>=2){
                        if(currentPlayer.getVisitedCities().get(currentPlayer.getVisitedCities().size()-2).x!=cities.get(indexVertice).get(i).edge.x
                                &&currentPlayer.getVisitedCities().get(currentPlayer.getVisitedCities().size()-2).y!=cities.get(indexVertice).get(i).edge.y)
                        {
                             dis = cities.get(indexVertice).get(i).distance;
                             nodeToAdd = cities.get(indexVertice).get(i).edge;
                        }
                    }else{
                    dis = cities.get(indexVertice).get(i).distance;
                    nodeToAdd = cities.get(indexVertice).get(i).edge;
                    }
                }
            }

            queue.add(nodeToAdd);
            
            }
        }//end of whileloop

        return path;
    }}
    