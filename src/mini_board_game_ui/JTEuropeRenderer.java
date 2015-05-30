/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_board_game_ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mini_board_game_data.JTEuropeDataModel;
import mini_board_game_data.JTEuropePathFinding;
import mini_board_game_ui.JTEuropeUI.JTEuropeScreenState;

/**
 *
 * @author ProgrammingSection
 */
public class JTEuropeRenderer extends Canvas {

    private JTEuropeUI view;
    private JTEuropePathFinding ComputerPathFinder;
    private JTETalkingRobot robot;
    private long width, height;
    private GraphicsContext context;
    private RenderThread thread;
    private int[][] oceanXY = {{0, 0},
    {0, -690}};
    private int[][] aiplaneXY = {{1200, -20}};
    private int destinationNodeX = 0;
    private int destinationNodeY = 0;
    private String imgId = "", imgN = "";
    private Image over = null;
    private boolean miniGridPaneMovingRight = false;
    private boolean miniGridPaneMovingLeft = false;
    private boolean helpPaneMovingLeft = false;
    private boolean helpPaneMovingRight = false;
    private boolean statPaneMovingTop = false;
    private boolean statPaneMovingDown = false;
    private boolean cityCallActionUp = false;
    private boolean cityCallActionDown = false;
    private boolean cityShowsUp = false;
    private int gameJustStart = 0;
    private int playersTurnIndex = 0;
    private boolean canDealCards = false;
    private boolean isComingFromLoadData = false;
    private boolean distributePlayers = false;
    private ArrayList<JTEPlayers> playerRendered = new ArrayList();
    private boolean canFixViewPort = false;
    private boolean canFixKeyViewPort = false;
    private JTECity nextCheatCity = null;
    private int drawPlayerByIndex = 0;
    private int dealCardByIndex = 0;
    private int cardDealedIndex = 0;
    private boolean diceAnimation = false;
    private boolean computerDoneFindingPath = false;
    private int computerPathIterator = 1;
    private boolean canGoToPlayersTurn = false;
    private JTECity directionTurn = null;
    private JTECity nextVisitedCity = null;
    private JTECity currentPlayerCityToLineRender = null;
    private boolean playerHasCard = false;
    private boolean onAir = false;
    private boolean onShip = false;
    private String textToRead = "";
    private int cardToRemove = -1;
    private boolean canDrawPotentialLines = false;
    private boolean reachDestination = false;
    private Color[] color = {Color.RED, Color.ALICEBLUE,
        Color.ANTIQUEWHITE, Color.AQUA,
        Color.MAROON, Color.YELLOW, Color.CHARTREUSE};

    //this is the y card padding space when dealing to player coord
    private int yCardPadding = 0;
    private JTEScoreAnimator score = null;
    private ArrayList<JTECity> pathToFollow = new ArrayList();

    public JTEuropeRenderer(JTEuropeUI game) {
        this.view = game;
        this.ComputerPathFinder = new JTEuropePathFinding(game);
        robot = new JTETalkingRobot(game);

        this.width = game.getGameWidth();
        this.height = game.getGameHeight();
        System.out.println(view);
        this.setWidth(width);
        this.setHeight(height);
        context = this.getGraphicsContext2D();

        initThread();

    }

    public void initThread() {
        thread = new RenderThread(30);
        thread.start();

    }

    public Canvas getCurrentCanvas() {
        return this;
    }

    public JTETalkingRobot getRobot() {
        return robot;
    }

    public void pauseThread() {
        thread.paused();
    }

    public void resumeThread() {
        thread.resume();
    }

    public ArrayList<JTEPlayers> getPlayersRendered() {
        return this.playerRendered;
    }

    public String getTextToRead() {
        return textToRead;
    }

    public void setTextToRead(String textToRead) {
        this.textToRead = textToRead;
    }

    public boolean isIsComingFromLoadData() {
        return isComingFromLoadData;
    }

    public void setIsComingFromLoadData(boolean isComingFromLoadData) {
        this.isComingFromLoadData = isComingFromLoadData;
    }

    public void setMiniGridPaneMovindLeft(boolean left) {
        this.miniGridPaneMovingLeft = left;
    }

    public void setMiniGridPaneMovingRight(boolean right) {
        this.miniGridPaneMovingRight = right;
    }

    public void setHelpPaneMovingLeft(boolean left) {
        this.helpPaneMovingLeft = left;
    }

    public void setHelpPaneMovingRight(boolean right) {
        this.helpPaneMovingRight = right;
    }

    public void setStatPaneMovingUp(boolean left) {
        this.statPaneMovingTop = left;
    }

    public void setStatPaneMovingDown(boolean right) {
        this.statPaneMovingDown = right;
    }

    public boolean isCityCallActionUp() {
        return cityCallActionUp;
    }

    public JTEPlayers getcurrentPlayer() {
        return view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
    }

    public void setNextVisitedCity(JTECity city) {
        if (view.getDataModel().inHandState()) {
            boolean isAndEdgeCity = false;
            Iterator<JTECityEdges> potentialCities = view.getCityEdgesIterator();
            JTEPlayers player = view.getPlayers().get(view.getPlayerPlaying().get(this.playersTurnIndex));
            while (potentialCities.hasNext()) {
                JTECityEdges line = potentialCities.next();
                if (line.getNode2().x == city.x
                        && line.getNode2().y == city.y
                        && line.getNode1().x == player.getPlayersCurrentCity().x
                        && line.getNode1().y == player.getPlayersCurrentCity().y
                        || line.getNode1().x == city.x
                        && line.getNode1().y == city.y
                        && line.getNode2().x == player.getPlayersCurrentCity().x
                        && line.getNode2().y == player.getPlayersCurrentCity().y) {
                    isAndEdgeCity = true;
                }
            }//close while

            if (isAndEdgeCity) {
                this.nextVisitedCity = city;
            } else {
                //play the unreach block sound lol
                view.getSoundPool().get(0).play();
                JTEPlayers play = view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
                score = new JTEScoreAnimator("Sorry : " + play.getPlayerName() + " city is not on Range :-)",
                        city.x, city.y, "");
            }
        }//close datamodel state check
    }

    public void setNextVisitedCity(String city, boolean air, boolean ship) {
        if (view.getDataModel().inHandState()) {
            JTEPlayers player = view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
            player.setPlayerOnAir(air);
            player.setPlayerOnBoat(ship);
            Iterator<JTECity> ct = view.getCityIterator();
            while (ct.hasNext()) {
                JTECity curr = ct.next();
                if (curr.getCityName().equals(city)) {
                    this.nextVisitedCity = curr;
                    break;
                }
            }

        }//close datamodel state check
    }

    public void cheatNextVisitedCity(JTECity node) {
        this.nextCheatCity = node;
    }

    public boolean isCanFixViewPort() {
        return canFixKeyViewPort;
    }

    public void setCanFixViewPort(boolean canFixViewPort) {
        this.canFixKeyViewPort = canFixViewPort;
    }

    public void setDiceAnimation(boolean d) {
        this.diceAnimation = d;
    }

    public void initPlayerReady() {
        distributePlayers = false;
        canFixViewPort = true;
    }

    public void setCityCallActionUp(boolean cityCallAction, int x, int y) {
        this.cityCallActionUp = cityCallAction;
        destinationNodeX = x;
        destinationNodeY = y;
        this.cityShowsUp = true;
    }

    public void setCityCallActionDown(boolean cityCallAction, int x, int y) {
        this.cityCallActionDown = cityCallAction;
        destinationNodeX = x;
        destinationNodeY = y;
        this.cityShowsUp = false;
    }

    public void makePlayerCardInvisibleButIndex(int index) {
        Iterator playersOnGame = view.getPlayers().entrySet().iterator();
        JTEPlayers players = null;
        if (index <= view.getMaxNumOfPlayer() - 1) {
            players = view.getPlayers().get(view.getPlayerPlaying().get(index));
            players.setPlayerTurn(true);

            //check if there are invisible make them visible now
            if (!players.getPlayerCards().get(0).getVisible()) {
                for (JTESpriteCards card : players.getPlayerCards()) {
                    card.setVisible(true);
                }
            }
            while (playersOnGame.hasNext()) {
                Map.Entry pairs = (Map.Entry) playersOnGame.next();
                JTEPlayers spr = (JTEPlayers) pairs.getValue();
                for (JTESpriteCards cards : spr.getPlayerCards()) {
                    if (spr != players) {
                        spr.setPlayerTurn(false);
                        cards.setVisible(false);
                    }
                }
            }
            //after all player info has been provided lets fix the viewport
            this.canGoToPlayersTurn = true;

            this.directionTurn = players.getPlayersCurrentCity();
            System.out.println("directionToRenderLines" + directionTurn);
            currentPlayerCityToLineRender = directionTurn;
            if (gameJustStart < 1 && !players.getPlayerName().toLowerCase().equals("computer")) {
                this.diceAnimation = true;
                gameJustStart++;
            } else {
                gameJustStart++;
            }

            this.canDrawPotentialLines = true;
        }
    }

    public void animateMiniGridPane() {

        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE
                || view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {

            if (this.miniGridPaneMovingLeft) {

                if (view.getMiniGridPane().getTranslateX() > (view.getGameWidth() * -1)) {
                    view.getMiniGridPane().setTranslateX(view.getMiniGridPane().getTranslateX() - 200);
                } else {
                    this.miniGridPaneMovingLeft = false;
                }
            }
            if (this.miniGridPaneMovingRight) {
                System.out.println(view.getMiniGridPane().getTranslateX());
                if (view.getMiniGridPane().getTranslateX() < 0) {
                    view.getMiniGridPane().setTranslateX(view.getMiniGridPane().getTranslateX() + 200);
                } else {
                    this.miniGridPaneMovingRight = false;
                }
            }
        }
    }

    public void animateMiniStatPane() {

        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE
                || view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {

            if (this.statPaneMovingTop) {

                if (view.getMiniStatPane().getTranslateY() > 50) {
                    view.getMiniStatPane().setTranslateY(view.getMiniStatPane().getTranslateY() - 50);
                } else {
                    this.statPaneMovingTop = false;
                }
            }
            if (this.statPaneMovingDown) {

                if (view.getMiniStatPane().getTranslateY() < view.getGameHeight() + 50) {
                    view.getMiniStatPane().setTranslateY(view.getMiniStatPane().getTranslateY() + 50);
                } else {
                    this.statPaneMovingDown = false;
                }
            }
        }

    }

    public void renderCityNodes() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {
            Iterator<JTECity> cityNodes = view.getCityIterator();
            while (cityNodes.hasNext()) {
                JTECity singleNode = cityNodes.next();
                if (singleNode.hasDescription && singleNode.isFlyPlanAvailable || singleNode.hasDescription && singleNode.isShipAvailable) {
                    context.setFill(Color.DARKRED);
                    context.fillOval(singleNode.getX(), singleNode.getY(), 30, 30);
                    context.setFill(Color.DARKRED);

                }
                if (singleNode.hasDescription && !singleNode.isFlyPlanAvailable && !singleNode.isShipAvailable) {
                    context.setFill(Color.WHITE);
                    context.fillOval(singleNode.getX(), singleNode.getY(), 30, 30);
                    context.setFill(Color.WHITE);

                }
                if (singleNode.isFlyPlanAvailable || singleNode.isShipAvailable) {
                    context.setFill(Color.DARKRED);
                    context.fillOval(singleNode.getX(), singleNode.getY(), 30, 30);
                    context.setFill(Color.DARKRED);

                }
                context.setFill(Color.CYAN);
                context.fillText(singleNode.getCityName(), singleNode.getX() + 30, singleNode.getY());
            }
        }//CLOSE IF
    }

    public void renderGameInProgressMap() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {

            context.drawImage(view.getMapBackGround(), view.getViewPort().getViewPortX(), view.getViewPort().getViewPortY(), view.getViewPort().getViewPortGameWidth(), view.getViewPort().getViewPortGameHeight());
        }

    }

    public void upDatePlayerSelectionGrid() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE
                || view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {
            if (view.getPlayerSelectionProgress()) {
                view.checkPlayerSelectionGrid();
            }
        }
    }

    public void animateHelpPane() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE
                || view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {

            if (this.helpPaneMovingLeft) {

                if (view.getHelpPane().getTranslateX() > (view.getGameWidth() / 2 - 100)) {
                    view.getHelpPane().setTranslateX(view.getHelpPane().getTranslateX() - 400);
                } else {
                    this.helpPaneMovingLeft = false;
                }
            }
            if (this.helpPaneMovingRight) {

                if (view.getHelpPane().getTranslateX() < (view.getGameWidth() * 2)) {
                    view.getHelpPane().setTranslateX(view.getHelpPane().getTranslateX() + 200);
                } else {
                    this.helpPaneMovingRight = false;
                    if (view.getHelpPane() != null) {
                        view.removeHelpPane();
                    }
                }
            }
        }

    }

    public void refreshBackGround() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {
            this.requestFocus();
        }
    }

    public void renderOceanAnimated() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE) {
            if (oceanXY[0][1] > 600) {
                oceanXY[0][1] = -600;
            } else {
                oceanXY[0][1]++;
            }

            if (oceanXY[1][1] > 600) {
                oceanXY[1][1] = -600;
            } else {
                oceanXY[1][1]++;
            }

            context.drawImage(view.getOceanIMG().get(0), oceanXY[0][0], oceanXY[0][1]);
            context.drawImage(view.getOceanIMG().get(0), oceanXY[1][0], oceanXY[1][1]);
        }
    }

    public void renderAnimatedPlane() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE) {
            if (aiplaneXY[0][0] < -10) {
                aiplaneXY[0][0] = 1200;
            } else {
                aiplaneXY[0][0]--;
            }

            if (aiplaneXY[0][1] > 720) {
                aiplaneXY[0][1] = -20;
            } else {
                aiplaneXY[0][1]++;
            }

            context.drawImage(view.getAirPlane(), aiplaneXY[0][0], aiplaneXY[0][1]);
        }

    }

    public void renderSpalshBackGround() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE) {
            context.drawImage(view.getBackgroundIMG().get(0), view.getWidth(), view.getHeight());
        }

    }

    public void renderButtons() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.GAME_SCREEN_STATE
                || view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {
            Iterator imges = view.getGuiButtons().entrySet().iterator();
            while (imges.hasNext()) {
                Map.Entry pairs = (Map.Entry) imges.next();
                JTESprite spr = (JTESprite) pairs.getValue();
                if (spr.getState() == JTEuropeScreenState.VISIBLE_STATE.toString()) {
                    context.drawImage(spr.getSpriteType().getStateImage(spr.getSpriteType().getSpriteTypeID()), spr.getX(), spr.getY());
                }
            }

        }
    }

    public void updatePaneCity(int x, int y) {
        if (this.cityShowsUp) {
            view.getCityPane().setTranslateX(view.getCityPane().getTranslateX() + x);
            view.getCityPane().setTranslateY(view.getCityPane().getTranslateY() + y);
        }
    }

    public void animateCityCallAction() {
        if (this.cityCallActionUp) {
            boolean xCanStop = false;
            boolean yCanStop = false;
            if (view.getCityPane().getTranslateX() > (this.destinationNodeX - 150)) {
                view.getCityPane().setTranslateX(view.getCityPane().getTranslateX() - 100);
            } else {
                view.getCityPane().setTranslateX(this.destinationNodeX - 150);
                xCanStop = true;
            }
            if (view.getCityPane().getTranslateY() > this.destinationNodeY) {
                view.getCityPane().setTranslateY(view.getCityPane().getTranslateY() - 100);
            } else {
                view.getCityPane().setTranslateY(this.destinationNodeY);
                yCanStop = true;
            }

            if (xCanStop && yCanStop) {
                this.cityCallActionUp = false;
            }
        }
        if (this.cityCallActionDown) {
            boolean xCanStop = false;
            boolean yCanStop = false;
            if (view.getCityPane().getTranslateX() < 1300) {
                view.getCityPane().setTranslateX(view.getCityPane().getTranslateX() + 100);
            } else {
                view.getCityPane().setTranslateX(1300);
                xCanStop = true;
            }
            if (view.getCityPane().getTranslateY() < 710) {
                view.getCityPane().setTranslateY(view.getCityPane().getTranslateY() + 100);
            } else {
                view.getCityPane().setTranslateY(710);
                yCanStop = true;
            }
            if (xCanStop && yCanStop) {
                this.cityCallActionDown = false;
            }

        }
    }

    public void renderImageOver(String id, String imgN) {
        //set the atributes to render image over
        imgId = id;
        this.imgN = imgN;

    }

    public void resetOver() {
        imgId = "";
        imgN = "";
        over = null;
    }

    public void renderMouseOver() {
        over = null;
        if (imgId.length() > 5 && imgN.length() > 5) {

            over = view.getGuiButtons().get(imgId).getSpriteType().getStateImage(imgN);

            if (over != null && view.getGuiButtons().get(imgId).getState().equals(JTEuropeScreenState.VISIBLE_STATE.toString())) {
                context.drawImage(over, view.getGuiButtons().get(imgId).getX(), view.getGuiButtons().get(imgId).getY());

            }
        }
    }

    public void drawPlayersToGame() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {
            Iterator playersOnGame = view.getPlayers().entrySet().iterator();
            while (playersOnGame.hasNext()) {
                Map.Entry pairs = (Map.Entry) playersOnGame.next();
                JTEPlayers spr = (JTEPlayers) pairs.getValue();
                if (spr.getPlayerFlag() != null && spr.getStartingNodeLocation() != null) {
                    context.drawImage(spr.getPlayerFlag(), spr.getStartingNodeLocation().x - 40, spr.getStartingNodeLocation().y - 80, 80, 80);
                }
                if (spr.getPlayerOnAir()) {
                    context.drawImage(spr.getPlayerAir().get(spr.getAnimationIndex()), spr.getPlayerX() + (10), spr.getPlayerY() - 80, 80, 80);
                }
                if (spr.getPlayerOnBoat()) {
                    context.drawImage(spr.getPlayerBoat().get(spr.getAnimationIndex()), spr.getPlayerX() + (10), spr.getPlayerY() - 80, 80, 80);
                }
                if (!spr.getPlayerOnAir() && !spr.getPlayerOnBoat()) {
                    context.drawImage(spr.getPlayerImg().get(spr.getAnimationIndex()), spr.getPlayerX() + (10), spr.getPlayerY() - 80, 80, 80);

                }

            }
        }//close if
    }

    public void drawPlayersCardToGame() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {
            Iterator playersOnGame = view.getPlayers().entrySet().iterator();
            while (playersOnGame.hasNext()) {
                Map.Entry pairs = (Map.Entry) playersOnGame.next();
                JTEPlayers spr = (JTEPlayers) pairs.getValue();
                for (JTESpriteCards cards : spr.getPlayerCards()) {
                    if (cards.getVisible()) {
                        context.drawImage(cards.getCityImg(), cards.getX(), cards.getY(), cards.getCityImg().getWidth(), cards.getCityImg().getHeight());
                    }
                }
                context.setFill(Color.BLACK);
                context.fillRect(spr.getCardRendererCoord().getX(), spr.getCardRendererCoord().getY() + 80, 150, 50);
                context.setFill(Color.WHITE);
                context.setFont(Font.font("Verdana", 24));
                context.fillText("" + spr.getPlayerName(), spr.getCardRendererCoord().getX() + 20, spr.getCardRendererCoord().getY() + 100);
                context.setFont(Font.font("Verdana", 12));
                //lets draw the current player info as well
                if (view.getDataModel().inHandState()) {
                    context.setFill(Color.BLACK);
                    context.fillRect(200, 0, 830, 70);
                    context.setFont(Font.font("Verdana", 30));
                    context.setFill(Color.WHITE);
                    if (playersTurnIndex <= view.getMaxNumOfPlayer() - 1) {
                        JTEPlayers players = view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
                        context.fillText("Player turn : "
                                + players.getPlayerName()
                                + " Steps : " + players.getPlayerNumSteps()
                                + " #cards : " + players.getPlayerCards().size()
                                + " Score : " + players.getScore(), 250, 50);
                        context.setFont(Font.font("Verdana", 12));
                    }
                }

            }
        }//close if
    }

    public boolean fixViewPortForPlayerDistribution(JTECity node) {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE && canFixViewPort) {

            float destX = view.getGameWidth() / 2;
            float destY = view.getGameHeight() / 2;

            float diffX = destX - node.x;
            float diffY = destY - node.y;
            float vX, vY;

            //this formula is to animate the character
            //double dirDouble = ((Math.atan2(diffX, diffY)) / ((Math.PI/4))+ 4);
            // int direction = (int) Math.round(dirDouble) % 8; 
            float tanResult = diffY / (diffX + 2);
            float angleInRadians = (float) Math.atan(tanResult);

            // COMPUTE THE X VELOCITY COMPONENT
            vX = (float) (50 * Math.cos(angleInRadians));

            // CLAMP THE VELOCTY IN CASE OF NEGATIVE ANGLES
            if ((diffX < 0) && (vX > 0)) {
                vX *= -1;
            }
            if ((diffX > 0) && (vX < 0)) {
                vX *= -1;
            }

            // COMPUTE THE Y VELOCITY COMPONENT
            vY = (float) (50 * Math.sin(angleInRadians));

            // CLAMP THE VELOCITY IN CASE OF NEGATIVE ANGLES
            if ((diffY < 0) && (vY > 0)) {
                vY *= -1;
            }
            if ((diffY > 0) && (vY < 0)) {
                vY *= -1;
            }

            view.moveViewPort(vX, vY);

            if (diffX > -700 && diffX <= 590 && diffY > -200 && diffY <= 220) {
                return true;
            }

        }
        return false;
    }

    public void animatePlayerPlaces() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {

            if (this.drawPlayerByIndex <= view.getMaxNumOfPlayer() - 1) {
                JTEPlayers player = view.getPlayers().get(view.getPlayerPlaying().get(this.drawPlayerByIndex));
                context.setFill(Color.GREEN);
                context.fillText("Player will draw here", player.getStartingNodeLocation().x, player.getStartingNodeLocation().y);
                if (this.isIsComingFromLoadData()) {
                    if (fixViewPortForPlayerDistribution(player.getPlayersCurrentCity())) {
                        System.out.println("Viewport reach destination");
                        canFixViewPort = false;
                        this.distributePlayers = true;
                    }
                } else {
                    if (fixViewPortForPlayerDistribution(player.getStartingNodeLocation())) {
                        System.out.println("Viewport reach destination");
                        canFixViewPort = false;
                        this.distributePlayers = true;
                    }
                }

                if (this.distributePlayers) {

                    boolean destReached = animatePlayerToNode(player, player.getStartingNodeLocation());

                    System.out.println(destReached);
                    if (destReached) {
                        this.playerRendered.add(player);
                        player.setPlayerCurrentCity(player.getStartingNodeLocation());
                        if (this.drawPlayerByIndex <= view.getMaxNumOfPlayer() - 1) {
                            if (this.drawPlayerByIndex == view.getMaxNumOfPlayer() - 1) {
                                canDealCards = true;
                                this.isComingFromLoadData = false;

                            }
                            this.drawPlayerByIndex += 1;
                            canFixViewPort = true;
                            this.distributePlayers = false;

                        }
                    }
                }
            }//end of player check by index
        }//end if stage
    }

    public boolean animatePlayerToNode(JTEPlayers play, JTECity node) {
        //play walking sound
        view.getSoundPool().get(3).play();
        int destX = node.x - (int) play.getPlayerImg().get(0).getWidth();
        int destY = node.y + (int) (play.getPlayerImg().get(0).getHeight() / 2);
        float vX = 0, vY = 0;
        int diffX = (int) (destX - play.getPlayerX());
        int diffY = (int) (destY - play.getPlayerY());

        // direction = 0 up, 1 left, 2 down, 3 right,
        // animation = 9 up, 3 left, 0 down, 6 right
        double dirDouble = ((Math.atan2(diffX, diffY)) / ((Math.PI / 2)) + 2);
        int direction = (int) Math.round(dirDouble) % 4;
        if (direction == 0) {
            play.setAnimationIndex(9);
        }
        if (direction == 1) {
            play.setAnimationIndex(3);
        }
        if (direction == 2) {
            play.setAnimationIndex(0);
        }
        if (direction == 3) {
            play.setAnimationIndex(6);
        }

        play.animateCharacterWalking();

        float tanResult;
        float angleInRadians = 0;
        if (diffY != 0 && diffX != 0) {
            tanResult = (diffY) / (diffX);
            angleInRadians = (float) Math.atan(tanResult);
        }

        // COMPUTE THE X VELOCITY COMPONENT
        vX = (float) (15 * Math.cos(angleInRadians));
        if (play.getPlayerName().toLowerCase().equals("computer0") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer1") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer2") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer3") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer4") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer5") && this.gameJustStart >= 1) {
            vX = (float) (5 * Math.cos(angleInRadians));
        }

        // CLAMP THE VELOCTY IN CASE OF NEGATIVE ANGLES
        if ((diffX < 0) && (vX > 0)) {
            vX *= -1;
        }
        if ((diffX > 0) && (vX < 0)) {
            vX *= -1;
        }

        // COMPUTE THE Y VELOCITY COMPONENT
        vY = (float) (15 * Math.sin(angleInRadians));
        if (play.getPlayerName().toLowerCase().equals("computer0") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer1") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer2") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer3") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer4") && this.gameJustStart >= 1
                || play.getPlayerName().toLowerCase().equals("computer5") && this.gameJustStart >= 1) {
            vY = (float) (5 * Math.sin(angleInRadians));
        }
        // CLAMP THE VELOCITY IN CASE OF NEGATIVE ANGLES
        if ((diffY < 0) && (vY > 0)) {
            vY *= -1;
        }
        if ((diffY > 0) && (vY < 0)) {
            vY *= -1;
        }
        //you can animate character walking animation if you want 

        play.setPlayerX(play.getPlayerX() + vX);
        play.setPlayerY(play.getPlayerY() + vY);

        if (diffX <= 30 && diffY >= -30 && diffY >= -30 && diffY <= 30) {
            if (play.getPlayerOnAir() || play.getPlayerOnBoat()) {
                play.setPlayerOnAir(false);
                play.setPlayerOnBoat(false);
            }
           
            return true;
        }

        return false;
    }

    public boolean animateCardToCoord(JTESpriteCards play, JTESprite node) {
        int destX = (int) node.getX();
        int destY = (int) node.getY();
        float vX = 0, vY = 0;
        int diffX = (int) (destX - play.getX());
        int diffY = (int) (destY - play.getY());

        // double dirDouble = ((Math.atan2(diffX, diffY)) / ((Math.PI/4))+ 4);
        // int direction = (int) Math.round(dirDouble) % 8; 
        float tanResult = 0;
        float angleInRadians = 0;
        if (diffY != 0 && diffX != 0) {
            tanResult = (diffY) / (diffX);
            angleInRadians = (float) Math.atan(tanResult);
        }

        // COMPUTE THE X VELOCITY COMPONENT
        vX = (float) (30 * Math.cos(angleInRadians));

        // CLAMP THE VELOCTY IN CASE OF NEGATIVE ANGLES
        if ((diffX < 0) && (vX > 0)) {
            vX *= -1;
        }
        if ((diffX > 0) && (vX < 0)) {
            vX *= -1;
        }

        // COMPUTE THE Y VELOCITY COMPONENT
        vY = (float) (30 * Math.sin(angleInRadians));

        // CLAMP THE VELOCITY IN CASE OF NEGATIVE ANGLES
        if ((diffY < 0) && (vY > 0)) {
            vY *= -1;
        }
        if ((diffY > 0) && (vY < 0)) {
            vY *= -1;
        }
        //you can animate character walking animation if you want 

        play.setX(play.getX() + vX);
        play.setY(play.getY() + vY);

        if (diffX > -30 && diffX < 30 && diffY > -30 && diffY < 30) {
            return true;
        }

        return false;
    }

    //goto direction city
    public void gotoDirectionCity() {
        if (view.getDataModel().inHandState()
                && this.canGoToPlayersTurn && this.directionTurn != null) {
            if (fixViewPortForPlayerDistribution(this.directionTurn)) {
                this.canGoToPlayersTurn = false;
                this.directionTurn = null;
            }
        }
    }

    public void animatePlayingDealingCards() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE) {
            if (canDealCards) {
                if (this.dealCardByIndex < view.getMaxNumOfPlayer()) {
                    JTEPlayers player = view.getPlayers().get(view.getPlayerPlaying().get(this.dealCardByIndex));
                    if (animateCardToCoord(player.getPlayerCards().get(this.cardDealedIndex), player.getCardRendererCoord())
                            && this.cardDealedIndex < player.getPlayerCards().size()) {
                        //check to end dealing lol
                        System.out.println(this.cardDealedIndex);
                        if (this.dealCardByIndex == view.getMaxNumOfPlayer() - 1
                                && this.cardDealedIndex == player.getPlayerCards().size() - 1) {
                            this.canDealCards = false;
                            robot.text("All cards has been dealed");
                            //set the game data model to be in play hand
                        }

                        //if not true lets iterate to next card
                        player.getPlayerCards().get(this.cardDealedIndex).setX(player.getCardRendererCoord().getX());
                        player.getPlayerCards().get(this.cardDealedIndex).setY(player.getCardRendererCoord().getY());
                        robot.text(player.getPlayerName() + " has received " + player.getPlayerCards().get(this.cardDealedIndex).getCardName());

                        //lets check the cardDealedIndex to go to next player
                        if (this.cardDealedIndex == player.getPlayerCards().size() - 1) {
                            this.dealCardByIndex += 1;
                            player.getCardRendererCoord().setY(player.getCardRendererCoord().getY() - this.yCardPadding);
                            this.yCardPadding = 0;
                            this.cardDealedIndex = 0;
                        } else {
                            this.cardDealedIndex += 1;

                        }
                        this.yCardPadding += 20;
                        player.getCardRendererCoord().setY(player.getCardRendererCoord().getY() + this.yCardPadding);

                    }

                }

                //lets do something if this can dealcar is false lets proceed to add the players turn to gamedata
                if (!this.canDealCards) {
                    //set the game data model to be in play hand
                    view.getDataModel().setGameState(JTEuropeDataModel.JTEGameState.GAME_PLAYING_HANDS);
                    makePlayerCardInvisibleButIndex(this.playersTurnIndex);
                }
            }
        }
    }

    //THE GAME PLAYING HAND METHOD
    public void gamePlayingHand() {
        if (view.getState() == JTEuropeUI.JTEuropeScreenState.PLAYING_GAME_STATE
                && view.getDataModel().inHandState()) {
            //lets do animate dice if user request it
            if (this.diceAnimation) {

                if (!view.getDiceImagesAnimation().completedTrowAnimation()) {
                    //play dice animation
                    //view.getSoundPool().get(1).play();
                    context.drawImage(view.getDiceImagesAnimation().calculateRandomDice(), view.getDiceImagesAnimation().getX(), view.getDiceImagesAnimation().getY());
                } else {
                    JTEPlayers player = view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
                    player.addPlayerStep(view.getDiceImagesAnimation().getNumSteps());
                    robot.text("Player " + player.getPlayerName() + " has received " + player.getPlayerNumSteps() + " step");
                    score = new JTEScoreAnimator("Number Of Steps : " + view.getDiceImagesAnimation().getNumSteps(),
                            (int) player.getPlayerX(), (int) player.getPlayerY(), "");
                    view.getDiceImagesAnimation().resetNumberOfIterations();
                    this.diceAnimation = false;
                }
            }

            if (score != null && score.getY() > 0) {
                context.setFill(Color.GREENYELLOW);
                context.fillText(score.getString(), score.getX(), score.getY());
                score.upDateYUp();
            } else {
                score = null;
            }

            applyGameRuleToPlayerHand();

        }
    }

    public void applyGameRuleToPlayerHand() {
        if (playersTurnIndex <= view.getMaxNumOfPlayer() - 1) {
            JTEPlayers player = view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
            if (player.getPlayerTurn()) {
                boolean iscomputer = false;
                if (player.getPlayerName().length() >= 8) {
                    if (player.getPlayerName().substring(0, 8).toLowerCase().equals("computer")) {
                        iscomputer = true;
                    }
                }
                if (!iscomputer) {
                    if (player.getPlayerNumSteps() > 0 && this.gameJustStart > 0) {
                        boolean alreadyVisited = false;
                         if (this.nextVisitedCity != null) {
                            if (player.getVisitedCities().size() > 2) {
                                JTECity c = player.getVisitedCities().get(player.getVisitedCities().size() - 2);
                                if (this.nextVisitedCity.getX() == c.getX() && this.nextVisitedCity.getY() == c.getY()) {
                                    alreadyVisited = true;
                                }
                            }

                        }

                        if (!alreadyVisited) {
                            //check for airplane and ships lol
                            if (nextVisitedCity != null) {
                                if (this.animatePlayerToNode(player, nextVisitedCity)) {
                                    player.addCity(nextVisitedCity);
                                    player.setPlayerCurrentCity(nextVisitedCity);
                                    player.subPlayerStep(1);
                                    nextVisitedCity = null;
                                    this.makePlayerCardInvisibleButIndex(playersTurnIndex);

                                    //if player steps is equals to zero we are ready to increase game to next turn
                                    if (player.getPlayerNumSteps() == 0) {

                                        for (int i = 0; i < player.getPlayerCards().size(); i++) {
                                            if (player.getPlayersCurrentCity().getCityName().equals(player.getPlayerCards().get(i).getCardName()) && this.gameJustStart > 1) {
                                                this.playerHasCard = true;
                                                this.cardToRemove = i;
                                            }
                                        }
                                        if (this.playerHasCard) {
                                           if (player.getPlayerCards().size() > 1) {
                                                if (player.getPlayersCurrentCity().hasDescription) {
                                                    view.initCityInfoBox(player.getPlayersCurrentCity());

                                                }

                                                player.addToScore(player.getPlayersCurrentCity().getScore());
                                                player.addPlayerStep(player.getPlayersCurrentCity().getStepToadd());
                                                player.subPlayerStep(player.getPlayersCurrentCity().getStepTosub());
                                                player.getPlayerCards().get(this.cardToRemove).setCardEnabledToShuffle(false);
                                                robot.text("dealer removed " + player.getPlayerCards().get(this.cardToRemove).getCardName());
                                                robot.text(" player has gain " + player.getPlayersCurrentCity().getStepToadd() + " to move ");
                                                robot.text(" player had loss " + player.getPlayersCurrentCity().getStepTosub() + " steps ");
                                                robot.text(" player has earn score of  " + player.getPlayersCurrentCity().getScore() + " in this play");
                                                robot.text(" player has loss " + player.getPlayersCurrentCity().makeLoseATurn + " turn");
                                                robot.text(" player has gain another Trow " + player.getPlayersCurrentCity().anExtraDiceThrow + " in this play");
                                                player.getPlayerCards().remove(this.cardToRemove);
                                                this.cardToRemove = -1;
                                                this.playerHasCard = false;
                                                if (player.getPlayersCurrentCity().anExtraDiceThrow) {
                                                    this.makePlayerCardInvisibleButIndex(playersTurnIndex);
                                                }
                                            } else {
                                                //we have a winner
                                                player.addToScore(player.getPlayersCurrentCity().getScore());
                                                player.addPlayerStep(player.getPlayersCurrentCity().getStepToadd());
                                                player.subPlayerStep(player.getPlayersCurrentCity().getStepTosub());
                                                player.getPlayerCards().get(this.cardToRemove).setCardEnabledToShuffle(false);
                                                robot.text("dealer removed " + player.getPlayerCards().get(this.cardToRemove).getCardName());
                                                robot.text(" player has gain " + player.getPlayersCurrentCity().getStepToadd() + " to move ");
                                                robot.text(" player had loss " + player.getPlayersCurrentCity().getStepTosub() + " steps ");
                                                robot.text(" player has earn score of  " + player.getPlayersCurrentCity().getScore() + " in this play");
                                                robot.text(" player has loss " + player.getPlayersCurrentCity().makeLoseATurn + " turn");
                                                robot.text(" player has gain another Trow " + player.getPlayersCurrentCity().anExtraDiceThrow + " in this play");
                                                player.getPlayerCards().remove(this.cardToRemove);
                                                this.cardToRemove = -1;
                                                this.playerHasCard = false;
                                                robot.text("Current player " + player.getPlayerName() + " Is the winner of the game");
                                            }

                                        } else {
                                            player.setPlayerTurn(false);
                                            if (playersTurnIndex < view.getMaxNumOfPlayer() - 1) {
                                                playersTurnIndex++;
                                                this.makePlayerCardInvisibleButIndex(playersTurnIndex);
                                            } else {
                                                playersTurnIndex = 0;
                                                this.makePlayerCardInvisibleButIndex(playersTurnIndex);
                                            }
                                        }//end else

                                    }

                                }
                            }

                        } else {
                            score = new JTEScoreAnimator("City already Visited : ",
                                    (int) nextVisitedCity.getX(), (int) nextVisitedCity.getY(), "");
                        }

                    }

                } else {//apply computer ai if the player is not human lolsx

                    computerPlay();
                }
            }
        }//close the if check for players           
    }

    public void computerPlay() {

        JTEPlayers playerC = view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
        if (playerC.getPlayerNumSteps() == 0 && !computerDoneFindingPath && pathToFollow == null) {
            this.diceAnimation = true;
        }
        if (!diceAnimation && playerC.getPlayerNumSteps() > 0 && this.gameJustStart >= 0) {
            if (!computerDoneFindingPath) {
                this.ComputerPathFinder.setCurrentPlayer(playerC);
                pathToFollow = this.ComputerPathFinder.BSF(playerC.getPlayersCurrentCity());
                computerDoneFindingPath = true;
                this.canDrawPotentialLines = false;
                System.out.println("done finding path");
            }
        }

        //after we have the computer step finder we will navigate to it lol
        if (computerDoneFindingPath && pathToFollow.size() >= 2 && computerPathIterator <= pathToFollow.size() - 1
                && playerC.getPlayerNumSteps() >= 1) {
            //draw line of the follow path
            drawComputerPath();
            if (this.animatePlayerToNode(playerC, pathToFollow.get(computerPathIterator))) {
                playerC.setPlayerCurrentCity(pathToFollow.get(computerPathIterator));
                playerC.addCity(pathToFollow.get(computerPathIterator));

                if (this.fixViewPortForPlayerDistribution(playerC.getPlayersCurrentCity())) {
                    playerC.subPlayerStep(1);
                    computerPathIterator += 1;

                }
                if (playerC.getPlayerNumSteps() == 0) {
                    for (int i = 0; i < playerC.getPlayerCards().size(); i++) {
                        if (playerC.getPlayersCurrentCity().getCityName().equals(playerC.getPlayerCards().get(i).getCardName()) && this.gameJustStart > 1) {
                            this.playerHasCard = true;
                            this.cardToRemove = i;
                        }
                    }
                    if (this.playerHasCard) {
                        if (playerC.getPlayersCurrentCity().hasDescription) {
                            view.initCityInfoBox(playerC.getPlayersCurrentCity());
                        }

                        playerC.addToScore(playerC.getPlayersCurrentCity().getScore());
                        playerC.addPlayerStep(playerC.getPlayersCurrentCity().getStepToadd());
                        playerC.subPlayerStep(playerC.getPlayersCurrentCity().getStepTosub());
                        playerC.getPlayerCards().get(this.cardToRemove).setCardEnabledToShuffle(false);
                        robot.text("dealer removed " + playerC.getPlayerCards().get(this.cardToRemove).getCardName());
                        robot.text(" player has gain " + playerC.getPlayersCurrentCity().getStepToadd() + " to move ");
                        robot.text(" player had loss " + playerC.getPlayersCurrentCity().getStepTosub() + " steps ");
                        robot.text(" player has earn score of  " + playerC.getPlayersCurrentCity().getScore() + " in this play");
                        robot.text(" player has loss " + playerC.getPlayersCurrentCity().makeLoseATurn + " turn");
                        robot.text(" player has gain another Trow " + playerC.getPlayersCurrentCity().anExtraDiceThrow + " in this play");
                        playerC.getPlayerCards().remove(this.cardToRemove);
                        this.cardToRemove = -1;
                        this.playerHasCard = false;

                    }
                }
            }

        } else {
            //since we finish all follow path for computer we now proceed to next player
            //also lets check if computer hold a card of current city so we apply effect to the computer 
            if (pathToFollow != null) {
                if (playerC.getPlayerNumSteps() == 0 && playerC.getPlayerTurn()) {
                    pathToFollow = null;
                    computerPathIterator = 1;
                    computerDoneFindingPath = false;
                    playerC.setPlayerTurn(false);
                    if (playersTurnIndex < view.getMaxNumOfPlayer() - 1) {
                        playersTurnIndex++;
                        this.makePlayerCardInvisibleButIndex(playersTurnIndex);
                    } else {
                        playersTurnIndex = 0;
                        this.makePlayerCardInvisibleButIndex(playersTurnIndex);
                    }
                }
            }//end if of path null check
        }
    }

    public void drawPotentialLines() {
        if (view.getDataModel().inHandState() && this.canDrawPotentialLines) {
            //draw line to potential visited cities
            Iterator<JTECityEdges> potentialCities = view.getCityEdgesIterator();
            while (potentialCities.hasNext()) {
                JTECityEdges line = potentialCities.next();
                if (line.getNode1().x == currentPlayerCityToLineRender.x && line.getNode1().y == currentPlayerCityToLineRender.y
                        && line.getNode2().x != currentPlayerCityToLineRender.x && line.getNode2().y != currentPlayerCityToLineRender.y) {

                    Random rand = new Random();
                    JTEPlayers playerC = view.getPlayers().get(view.getPlayerPlaying().get(playersTurnIndex));
                    if (playerC.isNodeAllow(line.getNode2())) {
                        context.setStroke(Color.CYAN);
                        context.setLineWidth(5);
                        context.strokeLine(currentPlayerCityToLineRender.x, currentPlayerCityToLineRender.y, line.getNode2().x, line.getNode2().y);
                        context.setStroke(color[rand.nextInt(color.length) + 0]);
                        context.strokeOval(line.getNode2().x - 10, line.getNode2().y - 10, 50, 50);
                    }
                }

            }

        }
    }

    public void drawComputerPath() {
        if (view.getDataModel().inHandState()) {
            if (this.pathToFollow.size() > 0 && computerDoneFindingPath) {
                for (int i = 1; i < pathToFollow.size(); i++) {
                    context.setStroke(Color.YELLOW);
                    context.setLineWidth(5);
                    context.strokeLine(pathToFollow.get(i - 1).x,
                            pathToFollow.get(i - 1).y,
                            pathToFollow.get(i).x,
                            pathToFollow.get(i).y);
                }
            }
        }
    }

    public void animateViewCheatCode() {
        if (this.nextCheatCity != null && this.canFixKeyViewPort) {
            if (this.fixViewPortForPlayerDistribution(this.nextCheatCity)) {
                this.canFixKeyViewPort = false;
                this.nextCheatCity = null;

            }
        }
    }

    private class RenderThread extends AnimationTimer {

        private boolean isPaused;
        private boolean isRunning;
        private long FramePerSecond;

        public RenderThread(long fps) {
            isPaused = false;
            isRunning = true;
            FramePerSecond = fps;
        }

        public void paused() {
            isPaused = true;
        }

        public void resume() {
            isPaused = false;
        }

        @Override
        public void handle(long mil) {
            if (!isPaused && isRunning) {

                try {
                    context = getCurrentCanvas().getGraphicsContext2D();
                    refreshBackGround();
                    renderOceanAnimated();
                    upDatePlayerSelectionGrid();
                    renderSpalshBackGround();
                    renderGameInProgressMap();
                    renderAnimatedPlane();
                    renderCityNodes();
                    animateMiniGridPane();

                    animateHelpPane();
                    animateMiniStatPane();
                    animateCityCallAction();
                    animatePlayerPlaces();
                    animatePlayingDealingCards();

                    drawPotentialLines();
                    drawPlayersToGame();
                    gamePlayingHand();
                    //go to players turn city
                    animateViewCheatCode();
                    gotoDirectionCity();
                    drawPlayersCardToGame();
                    renderButtons();
                    renderMouseOver();

                    Thread.sleep(FramePerSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
