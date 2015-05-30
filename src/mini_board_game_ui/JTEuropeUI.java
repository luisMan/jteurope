/*
 *This program was done by Luis Manon 
 */

package mini_board_game_ui;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import jteurope.JTEurope.JTEuropePropertyType;
import mini_board_game_data.JTEuropeDataModel;
import mini_board_game_events.JTEHyperLinkListener;
import mini_board_game_events.JTEuropeErrorHandler;
import mini_board_game_events.JTEuropeEventHandlers;
import mini_board_game_events.JTEuropeKeyEvents;
import mini_board_game_events.JTEuropeMouseEvent;
import mini_board_game_file.JTEuropeFileManager;
import static mini_board_game_ui.JTEuropeUI.JTEuropeScreenState.*;
import properties_manager.PropertiesManager;

public class JTEuropeUI extends Pane {
    /*data model*/
   private JTEuropeDataModel dataModel;
   /*events handlers*/
   private JTEuropeRenderer  graphRenderer;
   private JTEuropeEventHandlers handler;
   private JTEuropeMouseEvent mouse;
   private JTEuropeKeyEvents keyevent;
   private JTEuropeErrorHandler error;
   private JTEViewPort viewport;
 
   /*File manager*/
   private JTEuropeFileManager file;
   
   private long gameWidth,gameHeight;
   
   
   private ArrayList<Image>background;
   private ArrayList<Image>ocean;
   private ArrayList<Image>flyPlan;
   private ArrayList<Image>airplane;
   private ArrayList<Image>boat;
   private Image airPlane;
   private Image BackgroundMap;
   private TreeMap<String,JTESpriteCards>cards;
   private TreeMap<String,JTESpriteCards>RedCards;
   private TreeMap<String,JTESpriteCards>GreenCards;
   private TreeMap<String,JTESpriteCards>YellowCards;
   private TreeMap<String,JTESprite>buttons;
   private TreeMap<String,JTEPlayers>players;

   
   private ArrayList<String>PlayerNamesToPlay =  new ArrayList();

   
   private TreeMap<String,JTESprite>backgrounds;
   private ArrayList<JTECity> cities;
   private ArrayList<JTECityEdges>connectedNodes;
   
   private Stage myPrimaryStage;
   private Scene myScene;
   
   private ArrayList<AudioClip>sound;
   private ArrayList<MediaPlayer>musics;
   
   
   
   private StackPane mainPane;
   private BorderPane helpPane=null;
   private BorderPane  cityInfo;
   private Button closeCityInfo;
   private WebView helpEditor;
   private Button closeHelp;
   private Button closeStat;
   private JEditorPane helpeditor;
   private BorderPane statPane;
   private BorderPane PlayerSelectorPane;
   
   
   private JTEuropeScreenState currentScreenState;
   
   
   //player check boxes and atributes
   private  VBox miniGridPane = null;
   private TextField numOfPlayers;
   private Label errorL;
   private Button refreshGUI;
   //player ai screen attributes 
   private Label flagSelectorL;
   private Label coordLevel;
   private Button [][] flagSelector;
   private Button [][] screenCoordinates;
   private JTESprite[][] coordinateScreenxy={{new JTESprite(10,0),new JTESprite(1050,0)},
                                            {new JTESprite(10,260),new JTESprite(1050,260)},
                                            {new JTESprite(10,490),new JTESprite(1050,490)}};
    private Image[][] flagButtonImg={{loadImage("flag_black.png"),loadImage("flag_white.png"),loadImage("flag_blue.png")},
                                            {loadImage("flag_green.png"),loadImage("flag_red.png"),loadImage("flag_yellow.png")}};

    
   
     private String [][] pieceButtonImg={{"piece_black.png","piece_white.png","piece_blue.png"},
                                            {"piece_green.png","piece_red.png","piece_yellow.png"}};
     
   private ArrayList<Image>diceGameAnimation =  new ArrayList();
   private JTEDice diceSprite;
   private int PlayerIndex=1;
   private int MaxPlayer = 0;
   private Image flag=null;
   private ArrayList<Image> piece=null;
   private Image flyP=null;
   private int flyPlanType =-1;
   private JTESprite coordinat=null;
   private String PlayerName="";
   private Button addNextPlayer;
   private Button startGame;
   private boolean isPlayerSelectionInProcess = false;
   //player list selection
   private  Label choosePerson;
   private final ComboBox PlayerSelectorBox = new ComboBox();
    

    public static int flagIndex = 1;  //tjis will prevent user from choosing a node twice
    public static int coodIndex = 1;  //||
    private int computerPlayers=0;
    private int flagC, flagR;
    public static int refreshIndex = 1;
    private GridPane playerVsComp;
    private BorderPane tilePane;
    private  BorderPane coordPane;
    private  GridPane results;
    
   
   public enum JTEuropeScreenState{

        GAME_SCREEN_STATE, PLAYING_GAME_STATE, VIEW_STATS_STATE, VIEW_HELP_STATE, VISIBLE_STATE, INVISIBLE_STATE
    }
   
   public JTEuropeUI(Stage stage, long width, long height)
   {
       //out 
       out("UI object initialized");
       myPrimaryStage =  stage;
       gameWidth =  width;
       gameHeight =  height;
       initJTEData();
       initJTEHandlers();
       initJTESounds();
       initJTEScreenPane();
      
   }
   
   
      public void initJTEScreenPane()
      {
          //out 
          out("screen pane initialized");
          //hide buttons back
          buttons.get(JTEConstant.NEW_GAME_BUTTON_TYPE).setState(JTEuropeScreenState.VISIBLE_STATE.toString());
          buttons.get(JTEConstant.NEW_GAME_BUTTON_TYPE).setEnabled(true);
          buttons.get(JTEConstant.EXIT_GAME_BUTTON_TYPE).setState(JTEuropeScreenState.VISIBLE_STATE.toString());
          buttons.get(JTEConstant.EXIT_GAME_BUTTON_TYPE).setEnabled(true);
          buttons.get(JTEConstant.LOAD_SAVE_GAME_TYPE).setState(JTEuropeScreenState.VISIBLE_STATE.toString());
          buttons.get(JTEConstant.LOAD_SAVE_GAME_TYPE).setEnabled(true);
          buttons.get(JTEConstant.VIEW_STATS_BUTTON_TYPE).setState(JTEuropeScreenState.VISIBLE_STATE.toString());
          buttons.get(JTEConstant.VIEW_STATS_BUTTON_TYPE).setEnabled(true);
          buttons.get(JTEConstant.VIEW_HELP_BUTTON_TYPE).setState(JTEuropeScreenState.VISIBLE_STATE.toString());
          buttons.get(JTEConstant.VIEW_HELP_BUTTON_TYPE).setEnabled(true);
          mainPane =  new StackPane();
          graphRenderer =  new JTEuropeRenderer(this);
          mainPane.getChildren().add(graphRenderer);
         
         currentScreenState =  JTEuropeScreenState.GAME_SCREEN_STATE;
      }
      public void initJTEData()
      {
          //out 
          out("Data pane initialized");
          cards =  new TreeMap();
          GreenCards =  new TreeMap();
          YellowCards = new TreeMap();
          RedCards =  new TreeMap();
          
          players =  new TreeMap();
          buttons = new TreeMap();
          backgrounds = new TreeMap();
       
          
          
          dataModel = new JTEuropeDataModel(this);
          System.out.println("Game base "+gameWidth+ "  "+gameHeight);
          file = new JTEuropeFileManager(this);
          
          
          
          Image img;
          JTESpriteType sT;
          JTESprite s;

        // FIRST PUT THE ICON IN THE WINDOW
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
       
        try{
        String back = props.getProperty(JTEuropePropertyType.BACKGROUND_IMAGE);
        String flyp = props.getProperty(JTEuropePropertyType.FLY_PLAN);
        background = new ArrayList();
        ocean =  new ArrayList();
        background =  loadSpriteSheetImages(JTEConstant.IMG+back,2,1,2,0,0);
        String oce = props.getProperty(JTEuropePropertyType.OCEAN_IMAGE);
        ocean  = loadSpriteSheetImages(JTEConstant.IMG+oce,2,1,2,0,0);
        String airP =  props.getProperty(JTEuropePropertyType.AIRPLANE_IMAGE);
        System.out.println(JTEConstant.IMG_PATH+airP);
        airPlane = loadImage(airP);
        String boats = props.getProperty(JTEuropePropertyType.BOAT_IMAGE);
        this.boat = loadSpriteSheetImages(JTEConstant.IMG+boats,12,3,4,0,0);
        String air =  props.getProperty(JTEuropePropertyType.AIR_IMAGE);
        this.airplane = loadSpriteSheetImages(JTEConstant.IMG+air,12,3,4,0,0);
        this.flyPlan =loadSpriteSheetImages(JTEConstant.IMG+flyp,6,2,3,0,0);
        }catch(IOException e){e.printStackTrace();}
        
        //new game ui control
        String newGame = props.getProperty(JTEuropePropertyType.NEW_GAME_IMG_NAME);
        String newGameMouseOver =  props.getProperty(JTEuropePropertyType.OVER_GAME_IMAGE_NAME);
        sT = new JTESpriteType(JTEConstant.NEW_GAME_BUTTON_TYPE);
        img = loadImage(newGame);
        sT.addState(JTEConstant.NEW_GAME_BUTTON_TYPE, img);
        img =  loadImage(newGameMouseOver);
        sT.addState(JTEConstant.NEW_GAME_BUTTON_TYPE_MOUSEOVER, img);
        s = new JTESprite(sT, JTEConstant.NEW_GAME_X, JTEConstant.NEW_GAME_Y, 0, 0,JTEuropeScreenState.VISIBLE_STATE.toString());
        buttons.put(JTEConstant.NEW_GAME_BUTTON_TYPE, s);
        buttons.get(JTEConstant.NEW_GAME_BUTTON_TYPE).setEnabled(true);
      
         
        //new dice  ui control
        String newDiceButton = props.getProperty(JTEuropePropertyType.DICE_GAME_IMG_NAME);
        String newDiceButtonOver =  props.getProperty(JTEuropePropertyType.OVER_GAME_IMAGE_NAME);
        sT = new JTESpriteType(JTEConstant.DICE_BUTTON_TYPE);
        img = loadImage(newDiceButton);
        System.out.println("dice = "+img);
        sT.addState(JTEConstant.DICE_BUTTON_TYPE, img);
        img =  loadImage(newDiceButtonOver);
        sT.addState(JTEConstant.DICE_BUTTON_TYPE_MOUSEOVER, img);
        s = new JTESprite(sT, 400, JTEConstant.LOAD_GAME_Y, 0, 0,JTEuropeScreenState.INVISIBLE_STATE.toString());
        buttons.put(JTEConstant.DICE_BUTTON_TYPE, s);
        buttons.get(JTEConstant.DICE_BUTTON_TYPE).setEnabled(false);
        
        //stats game ui control
        String statGame = props.getProperty(JTEuropePropertyType.STATS_GAME_IMG_NAME);
        String statGameMouseOver =  props.getProperty(JTEuropePropertyType.OVER_GAME_IMAGE_NAME);
        sT = new JTESpriteType(JTEConstant.VIEW_STATS_BUTTON_TYPE);
        img = loadImage(statGame);
        sT.addState(JTEConstant.VIEW_STATS_BUTTON_TYPE, img);
        img =  loadImage(statGameMouseOver);
        sT.addState(JTEConstant.VIEW_STATS_BUTTON_TYPE_MOUSEOVER, img);
        s = new JTESprite(sT, JTEConstant.STAT_GAME_X, JTEConstant.STAT_GAME_Y, 0, 0,JTEuropeScreenState.VISIBLE_STATE.toString());
        buttons.put(JTEConstant.VIEW_STATS_BUTTON_TYPE, s);
        buttons.get(JTEConstant.VIEW_STATS_BUTTON_TYPE).setEnabled(true);
        
        //help game ui control 
        String helpGame = props.getProperty(JTEuropePropertyType.HELP_GAME_IMG_NAME);
        String helpGameMouseOver =  props.getProperty(JTEuropePropertyType.OVER_GAME_IMAGE_NAME);
        sT = new JTESpriteType(JTEConstant.VIEW_HELP_BUTTON_TYPE);
        img = loadImage(helpGame);
        sT.addState(JTEConstant.VIEW_HELP_BUTTON_TYPE, img);
        img =  loadImage(helpGameMouseOver);
        sT.addState(JTEConstant.VIEW_HELP_BUTTON_TYPE_MOUSEOVER, img);
        s = new JTESprite(sT, JTEConstant.HELP_GAME_X , JTEConstant.HELP_GAME_Y, 0, 0,JTEuropeScreenState.VISIBLE_STATE.toString());
        buttons.put(JTEConstant.VIEW_HELP_BUTTON_TYPE, s);
        buttons.get(JTEConstant.VIEW_HELP_BUTTON_TYPE).setEnabled(true);
        
        
       // load game ui control
        String loadGame = props.getProperty(JTEuropePropertyType.LOAD_GAME_IMG_NAME);
        String loadGameMouseOver =  props.getProperty(JTEuropePropertyType.OVER_GAME_IMAGE_NAME);
        sT = new JTESpriteType(JTEConstant.LOAD_SAVE_GAME_TYPE);
        img = loadImage(loadGame);
        sT.addState(JTEConstant.LOAD_SAVE_GAME_TYPE, img);
        img =  loadImage(loadGameMouseOver);
        sT.addState(JTEConstant.LOAD_SAVE_GAME_TYPE_MOUSEOVER, img);
        s = new JTESprite(sT, JTEConstant.LOAD_GAME_X , JTEConstant.LOAD_GAME_Y, 0, 0,JTEuropeScreenState.VISIBLE_STATE.toString());
        buttons.put(JTEConstant.LOAD_SAVE_GAME_TYPE, s);
        buttons.get(JTEConstant.LOAD_SAVE_GAME_TYPE.toString()).setEnabled(true);
        
        String exitGame = props.getProperty(JTEuropePropertyType.EXIT_GAME_IMG_NAME);
        String exitGameMouseOver =  props.getProperty(JTEuropePropertyType.OVER_GAME_IMAGE_NAME);
        sT = new JTESpriteType(JTEConstant.EXIT_GAME_BUTTON_TYPE);
        img = loadImage(exitGame);
        sT.addState(JTEConstant.EXIT_GAME_BUTTON_TYPE, img);
        img =  loadImage(exitGameMouseOver);
        sT.addState(JTEConstant.EXIT_GAME_BUTTON_TYPE_MOUSEOVER, img);
        s = new JTESprite(sT, JTEConstant.EXIT_GAME_X , JTEConstant.EXIT_GAME_Y, 0, 0,JTEuropeScreenState.VISIBLE_STATE.toString());
        buttons.put(JTEConstant.EXIT_GAME_BUTTON_TYPE, s);
        buttons.get(JTEConstant.EXIT_GAME_BUTTON_TYPE).setEnabled(true);
        
   
        //DICE animation imgae
        ArrayList<String>diceImg =  props.getPropertyOptionsList(JTEuropePropertyType.DICE_GAME);
        for(int i=0; i<diceImg.size(); i++)
        {
            diceGameAnimation.add(loadImage(diceImg.get(i)));
        }
        diceSprite =  new JTEDice(diceGameAnimation,(int)getGameWidth()/2,(int)getGameHeight()/2);
        
        
        
        //init flag selector 2d array 
        flagSelector = new Button[2][3];
        int j=0, x=0;
        ArrayList<String> flags = props
                .getPropertyOptionsList(JTEuropePropertyType.FLAG_IMAGES);
        for(int i=0; i<2; i++)
        { 
            for(j=0; j<3; j++){
                img = loadImage(flags.get(x).toString());
                flagSelector[i][j] = new Button();
                flagSelector[i][j].setGraphic(new ImageView(img));
                flagSelector[i][j].setDisable(false);
                x++;
              }     
        }
        
        //creen coordinates directons
        screenCoordinates = new Button[3][2];
         j=0; x=0;
         flags = props
                .getPropertyOptionsList(JTEuropePropertyType.COORD_DIRECTIONS);
        for(int i=0; i<3; i++)
        { 
            for(j=0; j<2; j++){
                img = loadImage(flags.get(x).toString());
                screenCoordinates[i][j] = new Button();
                screenCoordinates[i][j].setGraphic(new ImageView(img));
                
                x++;
                
              }     
        }
        
        
        //load cards attributes red, green, yello cards for the game rendering
        ArrayList<String>card =  props.getPropertyOptionsList(JTEuropePropertyType.GREEN_CARD_GAME);
        for(int i=0; i<card.size(); i+=3)
        {
            String [] string = card.get(i).split("green/");
            String[] city = string[1].split(".jpg"); 
           
            JTESpriteCards singleCard;
            Image imgCity = loadImage(card.get(i));
            String desc =  card.get(i+2);
            if(card.get(i+1).equals("EMPTY"))
            {
                singleCard =  new JTESpriteCards(city[0],imgCity,null,desc);
                singleCard.setX(getGameWidth());
                singleCard.setY(getGameHeight());
            }else{
                Image imgDesc = loadImage(card.get(i+1));
                singleCard =  new JTESpriteCards(city[0],imgCity,imgDesc,desc);
                singleCard.setX(getGameWidth());
                singleCard.setY(getGameHeight());
            }
            cards.put(city[0],singleCard);
            GreenCards.put(city[0], singleCard);
        }
          //load cards attributes red, green, yello cards for the game rendering
        card =  props.getPropertyOptionsList(JTEuropePropertyType.RED_CARD_GAME);
        for(int i=0; i<card.size(); i+=3)
        {
            String [] string = card.get(i).split("red/");
            String[] city = string[1].split(".jpg"); 
            
            JTESpriteCards singleCard;
            Image imgCity = loadImage(card.get(i));
            String desc =  card.get(i+2);
            if(card.get(i+1).equals("EMPTY"))
            {
                singleCard =  new JTESpriteCards(city[0],imgCity,null,desc);
                singleCard.setX(getGameWidth());
                singleCard.setY(getGameHeight());
            }else{
                Image imgDesc = loadImage(card.get(i+1));
                singleCard =  new JTESpriteCards(city[0],imgCity,imgDesc,desc);
                singleCard.setX(getGameWidth());
                singleCard.setY(getGameHeight());
            }
            cards.put(city[0],singleCard);
            RedCards.put(city[0],singleCard);
        }
        
        
         card =  props.getPropertyOptionsList(JTEuropePropertyType.YELLOW_CARD_GAME);
        for(int i=0; i<card.size(); i+=3)
        {
            String [] string = card.get(i).split("yellow/");
            String[] city = string[1].split(".jpg"); 
        
            JTESpriteCards singleCard;
            Image imgCity = loadImage(card.get(i));
            String desc =  card.get(i+2);
            if(card.get(i+1).equals("EMPTY"))
            {
                singleCard =  new JTESpriteCards(city[0],imgCity,null,desc);
                singleCard.setX(getGameWidth());
                singleCard.setY(getGameHeight());
            }else{
                Image imgDesc = loadImage(card.get(i+1));
                singleCard =  new JTESpriteCards(city[0],imgCity,imgDesc,desc);
                singleCard.setX(getGameWidth());
                singleCard.setY(getGameHeight());
            }
            cards.put(city[0],singleCard);
            YellowCards.put(city[0],singleCard);
        }
        
        //load data with respect to cities
        cities  = new ArrayList();
        connectedNodes =  new ArrayList();
        
        for(int i=0; i<JTEConstant.board.length; i++)
        {
            for(int c=0; c<1; c++)
            {
                String cityName =  JTEConstant.board[i][4].toString();
                float sx =  Float.parseFloat(JTEConstant.board[i][0].toString());
                float sy  = Float.parseFloat(JTEConstant.board[i][1].toString());
                float s2x = Float.parseFloat(JTEConstant.board[i][2].toString());
                float s2y = Float.parseFloat(JTEConstant.board[i][3].toString());
                int stepToAdd =  Integer.parseInt(JTEConstant.board[i][7].toString());              
                int stepToSub =  Integer.parseInt(JTEConstant.board[i][8].toString());                
               
                 int score =  Integer.parseInt(JTEConstant.board[i][12].toString());
                 int flyPlans =  Integer.parseInt(JTEConstant.board[i][13].toString());            
                 JTECity cityToAdd =  new JTECity(cityName,(int)sx,(int)sy,stepToAdd,stepToSub,
                 JTEConstant.board[i][5].toString(),JTEConstant.board[i][6].toString(),
                 JTEConstant.board[i][9].toString(),JTEConstant.board[i][10].toString(),
                 JTEConstant.board[i][11].toString());
                 cityToAdd.setScore(score);
                 cityToAdd.setFlyPlan(flyPlans);
              
                 cities.add(cityToAdd);  
                 JTECity secondEdgedCity =  new JTECity((int)s2x,(int)s2y);
                 JTECityEdges edge = new JTECityEdges();
                 edge.setNode1(cityToAdd);
                 edge.setNode2(secondEdgedCity);
                 connectedNodes.add(edge);
                 
            }
        }
      }
      public void initJTEHandlers()
      {
          //out 
          out("event handlers pane initialized");
          handler = new JTEuropeEventHandlers(this);
          mouse = new JTEuropeMouseEvent(this);
          keyevent =  new JTEuropeKeyEvents(this);
          error =  new JTEuropeErrorHandler(myPrimaryStage);
    
          
      }
      public void initJTESounds()
      { 
          //out 
          out("sounds pane initialized");
          sound = new ArrayList<>();
          musics = new ArrayList<>();
          /*intro music and in game music credits goes to lino rise*/
           try{
          AudioClip soundpool;Media music;
          File file = new File("music/block.wav");
          URL resource = new URL("file:///"+file.getAbsolutePath());
          soundpool = new AudioClip(resource.toString());
          sound.add(soundpool);
          file = new File("music/dicesteps.wav");
          resource = new URL("file:///"+file.getAbsolutePath());
          soundpool = new AudioClip(resource.toString());
          sound.add(soundpool);
          
         file = new File("music/viewportfix.wav");
         resource = new URL("file:///"+file.getAbsolutePath());
         soundpool = new AudioClip(resource.toString());
         sound.add(soundpool);
         
         file = new File("music/walking.wav");
         resource = new URL("file:///"+file.getAbsolutePath());
         soundpool = new AudioClip(resource.toString());
         sound.add(soundpool);
        
        //music
        file = new File("music/intro.mp3");
        resource = new URL("file:///"+file.getAbsolutePath());
        music =  new Media(resource.toString());
        MediaPlayer media =  new MediaPlayer(music);
        musics.add(media);
        
         file = new File("music/ingame.mp3");
        resource = new URL("file:///"+file.getAbsolutePath());
        music =  new Media(resource.toString());
        media =  new MediaPlayer(music);
        musics.add(media);
        
        }catch(IOException e){e.printStackTrace();}
          
           
       musics.get(0).play();
      }
      
      public void initJTEGUIControls()
      {
          //out 
          out("int gui controls pane initialized");
          //since all our handlers obj has been initialized lets add mouse listener to our renderer
          
     
          graphRenderer.setOnMousePressed(mouseEvent ->{
          mouse.MousePressed(mouseEvent);
          });
          graphRenderer.setOnMouseDragged(mouseEvent ->{
          mouse.MouseDragged(mouseEvent);
          });
          graphRenderer.setOnMouseReleased(mouseEvent ->{
          mouse.MouseReleased(mouseEvent);
          });
          
          graphRenderer.setOnMouseMoved(mouseEvent->{
          mouse.MouseMoved(mouseEvent);
          });
          
          
          this.getSceneNode().setOnKeyPressed(keyEvent ->{
          keyevent.keyPressed(keyEvent);
          });
          this.getSceneNode().setOnKeyReleased(keyEvent ->{
          keyevent.keyReleased(keyEvent);
          });
          
           
          
      }
      
      public void initJTEStatsPane()
      {  
          //out 
          out("stats pane initialized");
          statPane  = new BorderPane();
          statPane.setTranslateX(0);
          statPane.setTranslateY(this.getGameHeight()+10);
          statPane.setStyle("-fx-background-image: url(\"file:./img/statback.png\"); -fx-background-repeat: no-repeat;");
          
          closeStat = new Button("Close");
          closeStat.setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                  graphRenderer.setStatPaneMovingDown(true);
                  mainPane.getChildren().remove(statPane);
              }
          });
          if(this.getDataModel().inHandState())
          {
               GridPane grid =  new GridPane();
               VBox playerCol ;
               
               int numColumns = 0;
              for(int i=0; i<((this.MaxPlayer%3==0)? this.MaxPlayer/3:this.MaxPlayer);i++)
              {
                  for(int j=0; j<3&&numColumns<=this.MaxPlayer-1; j++)
                  {
                      playerCol = new VBox();
                      playerCol.setStyle("-fx-background-color: rgba(255,0,0,0.5);-fx-background-radius: 5.0;-fx-background-insets: 10.5 10.0 10.0 10.0;-fx-padding: 10;");
              
                      Label text = new Label();
                      text.setTextFill(Color.WHITE);
                      text.setText("Player Name :"+players.get(PlayerNamesToPlay.get(numColumns)).getPlayerName());
                      playerCol.getChildren().add(text);
                      text = new Label();
                      text.setTextFill(Color.WHITE);
                      text.setText("Player Steps :"+players.get(PlayerNamesToPlay.get(numColumns)).getPlayerNumSteps());
                      playerCol.getChildren().add(text);
                      text = new Label();
                      text.setTextFill(Color.WHITE);
                      text.setText("Player Score :"+players.get(PlayerNamesToPlay.get(numColumns)).getScore());
                      playerCol.getChildren().add(text);
                      text = new Label();
                      text.setTextFill(Color.WHITE);
                      text.setText("Player current city :"+players.get(PlayerNamesToPlay.get(numColumns)).getPlayersCurrentCity().toString());
                      playerCol.getChildren().add(text);
                      text = new Label();
                      text.setTextFill(Color.WHITE);
                      text.setText("Player Home city :"+players.get(PlayerNamesToPlay.get(numColumns)).getStartingNodeLocation().toString());
                      playerCol.getChildren().add(text);
                      text = new Label();
                      text.setTextFill(Color.WHITE);
                      text.setText("Player visited Cities ");
                      playerCol.getChildren().add(text);
                      TextArea textA = new TextArea();
                      for(int x=0; x<players.get(PlayerNamesToPlay.get(numColumns)).getVisitedCities().size(); x++)
                      {
                           textA.appendText(players.get(PlayerNamesToPlay.get(numColumns)).getVisitedCities().get(x).toString()+"\n");
                      }
                      ScrollPane scroll = new ScrollPane(textA);
                      playerCol.getChildren().add(scroll);
                      text = new Label("Cards :");
                      text.setTextFill(Color.WHITE);
                      HBox horizontal = new HBox();
                      for(int c=0; c<players.get(PlayerNamesToPlay.get(numColumns)).getPlayerCards().size(); c++)
                      {
                         ImageView img = new ImageView(players.get(PlayerNamesToPlay.get(numColumns)).getPlayerCards().get(c).getCityImg());
                         img.setFitWidth(80);
                         img.setFitHeight(130);
                         horizontal.getChildren().add(img);
                      }
                      playerCol.getChildren().add(horizontal);
                      grid.add(playerCol, j,i);
                      numColumns++;
                  }
              }
          //add playerCol to statPane
          statPane.setCenter(grid);
          }
        
          statPane.setTop(closeStat);
          mainPane.getChildren().add(statPane);
          
      }
      
      //init real time game screen
       public void initRealTimeJTEGameScreen()
       {
           //firs make some buttons invisible 
           buttons.get(JTEConstant.NEW_GAME_BUTTON_TYPE).setState(JTEuropeScreenState.INVISIBLE_STATE.toString());
           buttons.get(JTEConstant.NEW_GAME_BUTTON_TYPE).setEnabled(false);
           buttons.get(JTEConstant.EXIT_GAME_BUTTON_TYPE).setState(JTEuropeScreenState.INVISIBLE_STATE.toString());
           buttons.get(JTEConstant.EXIT_GAME_BUTTON_TYPE).setEnabled(false);
           buttons.get(JTEConstant.DICE_BUTTON_TYPE).setState(JTEuropeScreenState.VISIBLE_STATE.toString());
           buttons.get(JTEConstant.DICE_BUTTON_TYPE).setEnabled(true);
          
           // musics.get(0).play();
            musics.get(0).stop();
            musics.get(1).play();
           
         JTESpriteType sT;
         Image img ;
         JTESprite s;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String jteGame = props.getProperty(JTEuropePropertyType.JTE_IMAGE);
        sT = new JTESpriteType(JTEConstant.JTE_IMAGE_TYPE);
        img = loadImage(jteGame);
        BackgroundMap =  img;
        sT.addState(JTEConstant.JTE_IMAGE_TYPE, img);
        viewport =  new JTEViewPort(this);
        viewport.setGameWorldSize((long)img.getWidth(),(long) img.getHeight());
        viewport.setX(0);viewport.setY(0);
        s = new JTESprite(sT, viewport.getViewPortX(), viewport.getViewPortY(), 0, 0,JTEuropeScreenState.VISIBLE_STATE.toString());
        backgrounds.put(JTEConstant.JTE_IMAGE_TYPE, s);
           
        //fix viewport to be -30,-30
        movingLeft(70);
        movingUp(70);
           //CHANGE SCREEN STATE
           this.currentScreenState =  JTEuropeScreenState.PLAYING_GAME_STATE;
         //set to distribute players to true
           graphRenderer.initPlayerReady();
           
       }
      public void initJTEHelpPane()
      {
          //help 
       
          out("help pane initialized");
          helpPane  = new BorderPane();
          helpPane.setTranslateX(this.getGameWidth());
          helpPane.setTranslateY(0);
         
          helpEditor = new WebView();
          PropertiesManager props = PropertiesManager.getPropertiesManager();
          helpEditor.getEngine().load(props.getProperty(JTEuropePropertyType.HELP_FILE_NAME));
		
          closeHelp = new Button("Close");
          closeHelp.setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                  graphRenderer.setHelpPaneMovingRight(true);
     
              }
          });
          
          helpPane.setTop(closeHelp);
          helpPane.setCenter(helpEditor);
       
          mainPane.getChildren().add(helpPane);
      }
      
      
      public void initPlayerAISelection()
      {
          currentScreenState = GAME_SCREEN_STATE;
          miniGridPane =  new VBox();
          miniGridPane.setTranslateX(-600);
          miniGridPane.setTranslateY(0);
          miniGridPane.setPadding(new Insets(10));
          miniGridPane.setSpacing(8);
          miniGridPane.setStyle("-fx-background-image: url(\"file:./img/playerselectorback.png\"); -fx-background-repeat: no-repeat;");
          GridPane topPane =  new GridPane();
          Label player =  new Label("#Players: ");
          player.setFont(new Font(40));
          player.setTextFill(Color.web("#0076a3"));
          errorL =  new  Label();
          errorL.setFont(new Font(12));
          errorL.setTextFill(Color.RED);
          numOfPlayers =  new TextField();
          numOfPlayers.setText("0");
          numOfPlayers.autosize();
          refreshGUI = new Button("GO");
          topPane.add(player, 0, 0);
          topPane.add(numOfPlayers,1,0);
          topPane.add(refreshGUI, 2,0);
          topPane.add(errorL,0,1);
          miniGridPane.getChildren().add(topPane);
          
          refreshGUI.setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                  if(Character.isDigit(numOfPlayers.getText().charAt(0)) &&Integer.parseInt(numOfPlayers.getText()) <= 6){
                  if(refreshIndex<=1){
                  isPlayerSelectionInProcess = true;
                  showPlayerAttr(); refreshIndex++;
                  }else{
                      
                       playerVsComp.getChildren().clear();
                       miniGridPane.getChildren().remove(playerVsComp);
                       tilePane.getChildren().clear();
                       miniGridPane.getChildren().remove(tilePane);
                       coordPane.getChildren().clear();
                       miniGridPane.getChildren().remove(coordPane);
                       results.getChildren().clear();
                       miniGridPane.getChildren().remove(results);
                       players.clear();
                       PlayerIndex = 1;
                       isPlayerSelectionInProcess = true;
                       
                       showPlayerAttr(); refreshIndex++;
                  }
                  }else{
                      errorL.setText("Invalid number must be less or = 6");
                  }
                  
                  
              }
          });
          
          if(currentScreenState == GAME_SCREEN_STATE){
           out("added mainPane player ai"); mainPane.getChildren().add(miniGridPane);

          }else{
           mainPane.getChildren().remove(miniGridPane);}
          
      }
      
   
      
      //apply effect on buttons and get value
      public  void applyEffectOnCoordButtonAndGetValues(int i, int j)
      {
          this.coordinat = this.coordinateScreenxy[i][j];
          this.screenCoordinates[i][j].setDisable(true);
      }
      //apply effect on flag button
      public void applyEffectOnFlagButtonAndGetValues(int i, int j)
      {
          this.flag = this.flagButtonImg[i][j];
          try{
          this.piece = loadSpriteSheetImages(JTEConstant.IMG+this.pieceButtonImg[i][j],12,3,4,0,0); 
          }catch(Exception e){}
          this.flagSelector[i][j].setDisable(true);
          if(i==0 && j==0)
          {flyP = flyPlan.get(0); flyPlanType =1;}
          if(i==0 && j==1)
          {flyP = flyPlan.get(1); flyPlanType =2;}
          if(i==1 && j==0)
          {flyP = flyPlan.get(2); flyPlanType =3;}
          if(i==1 && j==1)
          {flyP = flyPlan.get(3); flyPlanType =4;}
          if(i==2 && j==0)
          {flyP = flyPlan.get(4); flyPlanType =5;}
          if(i==2 && j==1)
          {flyP = flyPlan.get(5); flyPlanType = 6;}
          
      }
      
      //show player attr
      public void  showPlayerAttr()
      {
            //set the ai attributes selector 
          if(Character.isDigit(numOfPlayers.getText().charAt(0))&&Integer.parseInt(numOfPlayers.getText())>=1){
          errorL.setText("");
          MaxPlayer =  Integer.parseInt(numOfPlayers.getText());
          
          playerVsComp = new GridPane();
          choosePerson =  new Label("Select AI For Player:"+this.PlayerIndex);
          choosePerson.setFont(new Font(20));
          choosePerson.setTextFill(Color.web("#0076a3"));
          this.PlayerSelectorBox.getItems().addAll(
            "Human",
            "Computer"
          );
          this.PlayerSelectorBox.setEditable(true);
          
          playerVsComp.add(choosePerson,0,0);
          playerVsComp.add(PlayerSelectorBox,0,1);
          PlayerSelectorBox.valueProperty().addListener(new ChangeListener<String>() {
           @Override public void changed(ObservableValue ov, String t, String t1) {
            PlayerName =  t1;
            System.out.println("t ="+t+" t1 = "+t1);
        }    
    });
          
          
          
          
          this.miniGridPane.getChildren().add(playerVsComp);
          
          
          //add the tile flag pane
          tilePane =  new BorderPane();
          GridPane tile =  new GridPane();
          flagSelectorL =  new Label("Flag Selector for Player ="+this.PlayerIndex);
          flagSelectorL.setFont(new Font(20));
          flagSelectorL.setTextFill(Color.web("#0076a3"));
         
          for(int i=0; i<flagSelector.length; i++)
          {for(int j=0; j<3; j++)
          {
             tile.add(flagSelector[i][j],j,i);
          }}
          tilePane.setTop(flagSelectorL);
          tilePane.setLeft(tile);
         
          miniGridPane.getChildren().add(tilePane);
          
          //set the ai for player coord selector
          coordPane =  new BorderPane();
          GridPane coord =  new GridPane();
          coordLevel =  new Label("coord renderer Selector for Player ="+this.PlayerIndex);
          coordLevel.setFont(new Font(20));
          coordLevel.setTextFill(Color.web("#0076a3"));
         
          for(int i=0; i<this.screenCoordinates.length; i++)
          {for(int j=0; j<this.screenCoordinates[0].length; j++)
          {
             coord.add(this.screenCoordinates[i][j],(i),(j));
          }}
          coordPane.setTop(coordLevel);
          coordPane.setCenter(coord);
          miniGridPane.getChildren().add(coordPane);
          
          results =  new GridPane();
          addNextPlayer = new Button("Add next Player");
          startGame = new Button("Start Game");
          results.add(addNextPlayer, 0, 0);
          results.add(startGame,1,0);
          miniGridPane.getChildren().add(results);
          
          
          //lets add some listeners to the selection grid
          for(int i=0; i<this.screenCoordinates.length; i++)
          {for(int j=0; j<this.screenCoordinates[0].length; j++)
          {
             int x =i,y=j;
            this.screenCoordinates[i][j].setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                  if(coodIndex<=1){
                 applyEffectOnCoordButtonAndGetValues(x, y); coodIndex++;
                  }else{  errorL.setText("Only one screen coord can be selected");}
              }
            });
          }}
          
          
          //apply listener to flags
           for(int i=0; i<flagSelector.length; i++)
          {for(int j=0; j<3; j++)
          {
              int x=i, y=j;
            this.flagSelector[i][j].setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                  if(flagIndex<=1){
                 applyEffectOnFlagButtonAndGetValues(x, y);
                  flagC = y; flagR = x;
                   flagIndex++;
                  }else{ errorL.setText("Only one button flag can be choose :-)");     }
              }
            });
          }}
          
           //add NextPlayer listener
        this.addNextPlayer.setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                if(PlayerName.length()>0&&PlayerName.charAt(0)!=' '&&flag!=null&&coordinat!=null&&PlayerIndex<=MaxPlayer){
                     if(PlayerName.toLowerCase().equals("computer"))
                   {
                     PlayerName+=computerPlayers++; 
                   }
                    JTEPlayers player = new JTEPlayers(PlayerName,flag,piece,flyP,coordinat);         
                   player.setPlayerX(getGameWidth());
                   player.setPlayerY(getGameHeight()+50);
                   player.setImgR(flagR);
                   player.setImgC(flagC);
                   player.setFlyPlanType(flyPlanType);
                   player.setBoat(boat);
                   player.setAir(airplane);
                   players.put(PlayerName,player);
                   PlayerNamesToPlay.add(PlayerName);
                   //reset levels
                   PlayerIndex++;
                   flagIndex = 1;
                   coodIndex = 1;
                   choosePerson.setText("Select AI For Player:"+PlayerIndex);
                   flagSelectorL.setText("Flag Selector for Player ="+PlayerIndex);
                   coordLevel.setText("coord renderer Selector for Player ="+PlayerIndex);
                   PlayerName=""; flag=null; coordinat=null; piece=null; flyP=null; errorL.setText("");
                }else{
                    if(PlayerName.length()>0&&PlayerName.charAt(0)!=' '
                          &&flag!=null&&coordinat!=null){
                    addNextPlayer.setDisable(true);
                    }else{ errorL.setText("Provide a name :-)");}
                }
              }
            });
  
          }//end if
          else{
              errorL.setText("Please :"+this.numOfPlayers.getText()+" is not a valid number :-)");
          } 
          //buttons handlers 
          //player information analog buttons
           startGame.setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                if(checkIfReadyToProceed())
                {
                      if(PlayerName.toLowerCase().equals("computer"))
                   {
                     PlayerName+=computerPlayers++; 
                   }
                   JTEPlayers player = new JTEPlayers(PlayerName,flag,piece,flyP,coordinat); 
                   player.setPlayerX(getGameWidth());
                   player.setPlayerY(getGameHeight()+50);
                   player.setImgC(flagC);
                   player.setImgR(flagR);
                   player.setFlyPlanType(flyPlanType);
                 
                   players.put(PlayerName,player);
                   PlayerNamesToPlay.add(PlayerName);
                   addDealingCardsToPlayer();
                  graphRenderer.setMiniGridPaneMovindLeft(true);
                  out("Players treemap "+players);
                  initRealTimeJTEGameScreen();
                }
              }
          });        
          
      }
      
      
      public void  addDealingCardsToPlayer()
      {
          Random rand = new Random();
          int greenSize =  GreenCards.size();
          int redCard =  RedCards.size();
          int yellowCard =  YellowCards.size();
          Iterator<JTEPlayers> player = players.values().iterator();
          while(player.hasNext())
          { 
               JTEPlayers dealer =  player.next();
               JTESpriteCards yellow,red,greenc;
               //get yellow card deck
               int redStartIndex =-1;
              
               yellow = YellowCards.get(YellowCards.keySet().toArray()[rand.nextInt(yellowCard)+0]);
               while(!yellow.getCardEnableToShuffle())
               {yellow = YellowCards.get(YellowCards.keySet().toArray()[rand.nextInt(yellowCard)+0]);}
               yellow.setCardEnabledToShuffle(false);
               //get red card deck
               redStartIndex = rand.nextInt(redCard)+0;
               red = RedCards.get(RedCards.keySet().toArray()[redStartIndex]);
               while(red.getCardName().equals("BREMEN")||red.getCardName().equals("SZCZECIN"))
               {
               redStartIndex = rand.nextInt(redCard)+0;
               red = RedCards.get(RedCards.keySet().toArray()[redStartIndex]);
               }
               while(RedCards.get(RedCards.keySet().toArray()[redStartIndex]).getCardName().equals("BREMEN")){
               redStartIndex = rand.nextInt(redCard)+0;
               red = RedCards.get(RedCards.keySet().toArray()[redStartIndex]);
               }
               while(!red.getCardEnableToShuffle())
               {red = RedCards.get(RedCards.keySet().toArray()[rand.nextInt(redCard)+0]);}
               red.setCardEnabledToShuffle(false);
               //get green card deck
               greenc = GreenCards.get(GreenCards.keySet().toArray()[rand.nextInt(greenSize)+0]);
               while(!greenc.getCardEnableToShuffle())
               {  greenc = GreenCards.get(GreenCards.keySet().toArray()[rand.nextInt(greenSize)+0]);}
               greenc.setCardEnabledToShuffle(false);
               
               //after all card has been take lets added it to the player 
               dealer.addPlayerCard(red);
               dealer.addPlayerCard(greenc);
               dealer.addPlayerCard(yellow);
               /*JTECity c =  cities.get(rand.nextInt(cities.size())+0);
               while(c.hasPlayer)
               {
                c =  cities.get(rand.nextInt(cities.size())+0);
               }*/
               Iterator<JTECity>cityToLook =  cities.iterator();
               String cityToAdd =  RedCards.get(RedCards.keySet().toArray()[redStartIndex]).getCardName();
               JTECity c=null;
               System.out.println(cityToAdd);
               while(cityToLook.hasNext())
               {
                   JTECity startEnd =  cityToLook.next();
                   if(startEnd.getCityName().equals(cityToAdd))
                   {
                       
                       c = startEnd;
                       System.out.println("found cityToadd ="+c);
                       break;
                   }
               }
               if(c!=null){
               dealer.setStartingNodeLocation(c);
               dealer.setDestinationNodeLocation(c);
               }
               System.out.println("this player = "+dealer.getPlayerName()+" has this many cards = "+dealer.getPlayerCards().size());
               
          }
          
      }
      public void initCityInfoBox(JTECity node)
      {
          
          System.out.println("node been called has description "+node.hasDescription+ " name ="+node.cityName);
          if(node.hasDescription){
          this.cityInfo = new BorderPane();
          VBox innercity =  new VBox();
          this.cityInfo.setTranslateX(1200);
          this.cityInfo.setTranslateY(700);
          this.cityInfo.setPrefSize(400, 400);
          this.cityInfo.setStyle("-fx-background-image: url(\"file:./img/cityback.png\"); -fx-background-repeat: no-repeat;");
          GridPane cityInfoGrid =  new GridPane();
          ScrollPane paneText =  new ScrollPane();  
          paneText.setMinHeight(130);
          paneText.setMinWidth(310);
          TextArea text = new TextArea();
          text.setText(cards.get(node.cityName).getCityInfo());
          paneText.setContent(text);
             
          cityInfoGrid.add(new ImageView(cards.get(node.cityName).getCityImg()),0,4);
          cityInfoGrid.add(new ImageView(cards.get(node.cityName).getCityDescImg()),1,4);
          
          Label labelCity  = new Label("City :"+node.getCityName());
          labelCity.setFont(Font.font("serif", 20));
          labelCity.setTextFill(Color.YELLOW);
          this.closeCityInfo = new Button("Close");
          cityInfoGrid.add(labelCity, 0, 0);
          Button readInfo =  new Button("Read Text");
          cityInfoGrid.add(closeCityInfo,1,0);
          cityInfoGrid.add(readInfo,0,1);
          innercity.getChildren().add(cityInfoGrid);
          innercity.getChildren().add(paneText);
           //fly plan
          if(node.isFlyPlanAvailable||node.isShipAvailable){
           GridPane traveling = new GridPane();
          ImageView img =  new ImageView(graphRenderer.getcurrentPlayer().getFlyP());
          img.setFitWidth(130);
          img.setFitHeight(210);

            final ComboBox flyComboBox = new ComboBox();
            for(int  i=0; i<cities.size(); i++){
                
               if(cities.get(i).getFlyPlan()==graphRenderer.getcurrentPlayer().getFlyPlanType()){
                 if(!flyComboBox.getItems().contains(cities.get(i).getCityName()))
                         {
                             flyComboBox.getItems().add(cities.get(i).getCityName());
                         }
               }
            }
             flyComboBox.setPromptText("City To Travel");
             flyComboBox.setEditable(false);   
             
            flyComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                  cityInfo.getChildren().clear();
                  graphRenderer.setCityCallActionDown(true, 0, 0);
                  mainPane.getChildren().remove(cityInfo); 
                  graphRenderer.setNextVisitedCity(t1, node.isFlyPlanAvailable, node.isShipAvailable);
            }    
           });
             
            traveling.add(img,0,0);
            traveling.add(flyComboBox,0,1);
            cityInfoGrid.add(traveling, 4,4);
          }//end if check
          
          
          this.cityInfo.getChildren().add(innercity);
          this.mainPane.getChildren().add(cityInfo);
         
          //after the city is constructed lets animate it
          graphRenderer.setCityCallActionUp(true, node.x, node.y);
          
           readInfo.setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                  String textToRead = cards.get(node.cityName).getCityInfo().toString();
                  graphRenderer.getRobot().text(textToRead);
              }
           });
          
           this.closeCityInfo.setOnAction(new EventHandler<ActionEvent>(){
              @Override public void handle(ActionEvent e)
              {
                  cityInfo.getChildren().clear();
                  graphRenderer.setCityCallActionDown(true, 0, 0);
                  mainPane.getChildren().remove(cityInfo);
     
              }
          });
         
          }
      }
      
      public boolean checkIfReadyToProceed()
      {
         return PlayerIndex==MaxPlayer && flag!=null && coordinat!=null;
      }
      public void checkPlayerSelectionGrid()
      {
          if(checkIfReadyToProceed())
          {
              this.startGame.setStyle("-fx-background-color:green; -fx-base:white; -fx-color:white");
              this.startGame.setTextFill(Color.WHITE);
          }else{
              if(flag!=null&&coordinat!=null&&PlayerName.length()>0)
              {  errorL.setText("Ready To Add Next Player Host :-)");                                            }
          }
      }
      
      public void setScene(Scene scene)
      { myScene =  scene;}
      public Scene getSceneNode()
      {return this.myScene;}
      public void setGameWidth(long w)
      {this.gameWidth = w;}
      public void setGameHeight(long h)
      {this.gameHeight =  h;}
       public void removeHelpPane()
      {mainPane.getChildren().remove(helpPane); helpPane=null;}
        public void setPlayerNamesToPlay(ArrayList<String> PlayerNamesToPlay) {
        this.PlayerNamesToPlay = PlayerNamesToPlay;
    }
       public void removePlayerPane()
       {mainPane.getChildren().remove(this.miniGridPane); miniGridPane = null;}
      
      
      
      public StackPane getMainPane()
      {return this.mainPane;}
      public long getGameWidth()
      {return this.gameWidth;}
      public long getGameHeight()
      {return this.gameHeight;}
      public Scene getMyScene()
      {return this.myScene;}
      public Stage getMyStage()
      {return this.myPrimaryStage;}
      public BorderPane getHelpPane()
      {return this.helpPane;}
      public BorderPane getStatsPane()
      {return this.statPane;}
      public JTEViewPort getViewPort()
      {return viewport;}
      public void setMaxNumberOfPlayers(int x)
      {this.MaxPlayer=x;}
      public BorderPane getPlayerSelectorPane()
      {return this.PlayerSelectorPane;}
      public BorderPane getCityPane()
      {return this.cityInfo;}
      public boolean getPlayerSelectionProgress()
      {return this.isPlayerSelectionInProcess;}
      public JTEuropeScreenState  getState()
      {return currentScreenState;}
      public VBox getMiniGridPane()
      {return this.miniGridPane;}
      public BorderPane getMiniStatPane()
      {return this.statPane;}
      public JTEDice getDiceImagesAnimation()
      {return this.diceSprite;}
      public ArrayList<JTECity> getAllCities()
      {return this.cities;}
      public ArrayList<JTECityEdges>getAllEdges()
      {return this.connectedNodes;}
      public JTEuropeFileManager getFileManager()
      {return this.file;}
      public JTEuropeRenderer getRenderer()
      {return this.graphRenderer;}
      public JTEuropeDataModel getDataModel()
      {return this.dataModel;}
      public TreeMap<String, JTESprite> getGuiButtons(){
          return buttons;
      }
      public Image getMapBackGround()
      {return BackgroundMap;}
      public ArrayList<Image>getAir()
      {return airplane;}
      public ArrayList<Image>getBoat()
      {return boat;}
      public TreeMap<String, JTESpriteCards> getCards()
      {return cards;}
      public TreeMap<String, JTESpriteCards> getRedCards()
      {return RedCards;}
      public TreeMap<String, JTESpriteCards> getGreenCards()
      {return GreenCards;}
      public TreeMap<String, JTESpriteCards> getYellowCards()
      {return YellowCards;}
      public TreeMap<String, JTEPlayers> getPlayers()
      {return players;}
      public TreeMap<String, JTESprite> getBackgrounds()
      {return backgrounds;}
      public int getMaxNumOfPlayer()
      {return MaxPlayer;}
      public ArrayList<String> getPlayerPlaying()
      {return PlayerNamesToPlay;}
      public ArrayList<AudioClip>getSoundPool()
      {return sound;}
      public void setPlayers(TreeMap<String, JTEPlayers> players) {
        this.players = players;
    }
      public Iterator<JTECity> getCityIterator()
      {return  cities.iterator();}
      public Iterator<JTECityEdges> getCityEdgesIterator()
      {return  this.connectedNodes.iterator();}
      
      
      public ArrayList<Image>getBackgroundIMG()
      {return background;}
      public ArrayList<Image>getOceanIMG()
      {return ocean;}
      public Image getAirPlane()
      {return airPlane;}
      public Image[][] getFlagButtonImg() {
        return flagButtonImg;
    }

    public void setFlagButtonImg(Image[][] flagButtonImg) {
        this.flagButtonImg = flagButtonImg;
    }

    public String[][] getPieceButtonImg() {
        return pieceButtonImg;
    }

    public void setPieceButtonImg(String[][] pieceButtonImg) {
        this.pieceButtonImg = pieceButtonImg;
    }
   
         public Image loadImage(String imageName) {
        Image img = new Image(JTEConstant.IMG_PATH + imageName);
        return img;
    }

    public void loadPage(JEditorPane jep,JTEuropePropertyType fileProperty) {
        // GET THE FILE NAME
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String fileName = props.getProperty(fileProperty);
        System.out.println(fileName);
        try {
            // LOAD THE HTML INTO THE EDITOR PANE
            String fileHTML = file.loadTextFile(fileName);
            jep.setText(fileHTML);
        } catch (IOException ioe) {
            error.processError(JTEuropePropertyType.INVALID_URL_ERROR_TEXT);
        }
    }
    
    public void loadRemoteHelpPage(URL link)
    {
        try
        {
            // PUT THE WEB PAGE IN THE HELP PANE
            Document doc = this.helpeditor.getDocument();
            doc.putProperty(Document.StreamDescriptionProperty, null);
            helpeditor.setPage(link);            
        }
        catch(IOException ioe)
        {
            this.error.processError(JTEuropePropertyType.INVALID_URL_ERROR_TEXT);
        }
    }
     public ArrayList<Image> loadSpriteSheetImages(String fileName, int numImages,
            int columns, int rows,
            int xMargin, int yMargin)
            throws IOException {
        BufferedImage img = ImageIO.read(new File(fileName));

        return cutUpSpriteSheet(img, numImages, columns, rows, xMargin, yMargin);
    }

    public ArrayList<Image> cutUpSpriteSheet(BufferedImage img, int numImages, int columns, int rows, int xMargin, int yMargin) {
        // NOW CHOP UP THE SHEET
        int cellWidth = img.getWidth() / columns;
        int cellHeight = img.getHeight() / rows;
        ArrayList<Image> tiles = new ArrayList();
        int counter = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; (j < columns) && (counter < numImages); j++) {
                int x = (cellWidth * j) + xMargin;
                int y = (cellHeight * i) + yMargin;
                int w = cellWidth - (2 * xMargin);
                int h = cellHeight - (2 * yMargin);

                BufferedImage tileImage = img.getSubimage(x, y, w, h);
                tiles.add(SwingFXUtils.toFXImage(tileImage, null));

                counter++;
            }
        }

        return tiles;
    }
   
     public boolean processButtonPress(int x, int y)
    {
        boolean buttonClickPerformed = false;

        // TEST EACH BUTTON
        for (JTESprite s : buttons.values())
        {
            // THIS METHOD WILL INVOKE actionPeformed WHEN NEEDED
            buttonClickPerformed = s.testForClick(this, x, y);

            // ONLY EXECUTE THE FIRST ONE, SINCE BUTTONS
            // SHOULD NOT OVERLAP
            if (buttonClickPerformed)
            {
                return true;
            }
        }
        return false;
    }
     
      public boolean processButtonPressOnCity(int x, int y)
    {
        boolean buttonClickPerformed = false;
        // TEST EACH BUTTON
       if(this.getDataModel().inHandState()){ 
            // THIS METHOD WILL INVOKE actionPeformed WHEN NEEDED
      for(int i=0; i<cities.size(); i++){
           
            buttonClickPerformed = cities.get(i).testForClickOnCity(this, x, y);

            // ONLY EXECUTE THE FIRST ONE, SINCE BUTTONS
            // SHOULD NOT OVERLAP
            if (buttonClickPerformed)
            {
                return true;
            }
        }
        
       }
       return false;
    }
     
     
     public void checkMouseOverOnSprites(JTEuropeUI game, int x, int y) {  
         Iterator<JTESprite>b =  buttons.values().iterator();
    
        while(b.hasNext()){
            JTESprite canvas  = b.next();
            if(canvas.containsPoint(x, y))
            {
               
                this.graphRenderer.renderImageOver(canvas.getSpriteType().getSpriteTypeID(),canvas.getSpriteType().getSpriteTypeID()+"_MOUSEOVER");
            }
           
        }
       
     }
     
     public void zoomIn(int x, int y)
     {
        Iterator<JTECity>node =  this.getCityIterator();
        float scaleX =  (float)viewport.getViewPortGameWidth()/2500;
        float scaleY = (float)viewport.getViewPortGameHeight()/4144;
        while(node.hasNext())
        {
            JTECity newNode = node.next();
            newNode.setX((int)((float)newNode.getX()+scaleX));
            newNode.setY((int)((float)newNode.getY()+scaleY));
        }
         
     }
       public void zoomOut(int x, int y)
     {
        Iterator<JTECity>node =  this.getCityIterator();
         float scaleX =  (float)viewport.getViewPortGameWidth()/2500;
        float scaleY = (float)viewport.getViewPortGameHeight()/4144;
        while(node.hasNext())
        {
            JTECity newNode = node.next();
            newNode.setX((int)((float)newNode.getX()-scaleX));
            newNode.setY((int)((float)newNode.getY()-scaleY));
        }
         
     }
     public void movingLeft(int steps)
    {
        
        if(viewport.getViewPortX()>(-1270)){
        viewport.setScrollSpeed(steps);
        viewport.scrollViewPortLeft();
        graphRenderer.updatePaneCity((-1*steps),0);
        for(int i=0; i<cities.size(); i++){
           
            cities.get(i).setX(cities.get(i).getX()-steps);
            
        }
         if(graphRenderer.getPlayersRendered().size()>0)
          {
            for(int i=0; i<graphRenderer.getPlayersRendered().size(); i++){
            graphRenderer.getPlayersRendered().get(i).setPlayerX((int)graphRenderer.getPlayersRendered().get(i).getPlayerX()- steps);
              }
          }
         
          //lets update second children node
         for(int i=0; i<this.connectedNodes.size(); i++)
         {
             this.connectedNodes.get(i).getNode2().setX( this.connectedNodes.get(i).getNode2().getX()-(int)steps);
         }
         
         
         
        }
    }
     public void movingRight(int steps)
    {
        
          if(viewport.getViewPortX()<-30){
             viewport.setScrollSpeed(steps);
             viewport.scrollViewPortRight();
             graphRenderer.updatePaneCity(steps, 0);
             for(int i=0; i<cities.size(); i++){          
             cities.get(i).setX(cities.get(i).getX()+steps);    
            }
             
          if(graphRenderer.getPlayersRendered().size()>0)
          {
            for(int i=0; i<graphRenderer.getPlayersRendered().size(); i++){
            graphRenderer.getPlayersRendered().get(i).setPlayerX((int)graphRenderer.getPlayersRendered().get(i).getPlayerX()+ steps);
              }
          }
          
          //update node 2
           //lets update second children node
         for(int i=0; i<this.connectedNodes.size(); i++)
         {
             this.connectedNodes.get(i).getNode2().setX( this.connectedNodes.get(i).getNode2().getX()+(int)steps);
         }
          }
    }
      public void movingUp(int steps)
    {   
        if(viewport.getViewPortY()>(-3400)){
        viewport.setScrollSpeed(steps);
        viewport.scrollViewPortTop();
        graphRenderer.updatePaneCity(0, (-1*steps));
        for(JTECity cites : cities){
           
            cites.setY(cites.getY()-steps);
            
        }
        
        if(graphRenderer.getPlayersRendered().size()>0)
          {
            for(int i=0; i<graphRenderer.getPlayersRendered().size(); i++){
            graphRenderer.getPlayersRendered().get(i).setPlayerY((int)graphRenderer.getPlayersRendered().get(i).getPlayerY()-steps);
            //cities.get(i).setX(cities.get(i).getX()+(int)vX);
              }
          }
         //lets update second children node
         for(int i=0; i<this.connectedNodes.size(); i++)
         {
             this.connectedNodes.get(i).getNode2().setY( this.connectedNodes.get(i).getNode2().getY()-(int)steps);
         }
        
        }
    }
       public void movingDown(int steps)
    {
        if(viewport.getViewPortY()<-50){
            
        viewport.setScrollSpeed(steps);
        viewport.scrollViewPortDown();
        graphRenderer.updatePaneCity(0, (steps));
        for(int i=0; i<cities.size(); i++){
           
            cities.get(i).setY(cities.get(i).getY()+steps);
            
        }
         if(graphRenderer.getPlayersRendered().size()>0)
          {
            for(int i=0; i<graphRenderer.getPlayersRendered().size(); i++){
            graphRenderer.getPlayersRendered().get(i).setPlayerY((int)graphRenderer.getPlayersRendered().get(i).getPlayerY()+steps);
            //cities.get(i).setX(cities.get(i).getX()+(int)vX);
              }
          }
         
          //lets update second children node
         for(int i=0; i<this.connectedNodes.size(); i++)
         {
             this.connectedNodes.get(i).getNode2().setY( this.connectedNodes.get(i).getNode2().getY()+(int)steps);
         }

        }
    }
     
     public void moveViewPort(float vX,float vY)
     {
        if(viewport.getViewPortX()>=(-1270) && viewport.getViewPortX()<=-30){        
        
        viewport.setX((int)viewport.getViewPortX()+(int)vX); 
       
        graphRenderer.updatePaneCity((int)vX, 0);
        for(int i=0; i<cities.size(); i++){
            //cities.get(i).setY(cities.get(i).getY()+(int)vY);
            cities.get(i).setX(cities.get(i).getX()+(int)vX);          
        }
         if(graphRenderer.getPlayersRendered().size()>0)
          {
            for(int i=0; i<graphRenderer.getPlayersRendered().size(); i++){
            graphRenderer.getPlayersRendered().get(i).setPlayerX((int)graphRenderer.getPlayersRendered().get(i).getPlayerX()+(int)vX);
              }
          }
         //lets update second children node
         for(int i=0; i<this.connectedNodes.size(); i++)
         {
             this.connectedNodes.get(i).getNode2().setX( this.connectedNodes.get(i).getNode2().getX()+(int)vX);
         }
    
      }//close if restriction
        
        if(viewport.getViewPortY()>=(-3400) && viewport.getViewPortY()<=-50){
          viewport.setY((int)viewport.getViewPortY()+(int)vY);
          graphRenderer.updatePaneCity(0, (int)vY);
          for(int i=0; i<cities.size(); i++){
            cities.get(i).setY(cities.get(i).getY()+(int)vY);
            //cities.get(i).setX(cities.get(i).getX()+(int)vX);
              }
          if(graphRenderer.getPlayersRendered().size()>0)
          {
            for(int i=0; i<graphRenderer.getPlayersRendered().size(); i++){
            graphRenderer.getPlayersRendered().get(i).setPlayerY((int)graphRenderer.getPlayersRendered().get(i).getPlayerY()+(int)vY);
            //cities.get(i).setX(cities.get(i).getX()+(int)vX);
              }
          }
          
           //lets update second children node
         for(int i=0; i<this.connectedNodes.size(); i++)
         {
             this.connectedNodes.get(i).getNode2().setY( this.connectedNodes.get(i).getNode2().getY()+(int)vY);
         }
        }
     }
     public void checkMousePressOnSprites(JTEuropeUI game, int x, int y) {
  
     }
     
     public void checkMousePressOnCity(JTEuropeUI game, int x, int y)
     {
         
     }
     
     //receive the even handler on city
      public void ReceivedEventHandlesForCity(JTECity sprite)
     {
        //initCityInfoBox(sprite);
        graphRenderer.setNextVisitedCity(sprite);
         
     }
     //this method will receive sprite type id base on user clisk
     public void ReceivedEventHandles(JTESprite sprite)
     {
       if(this.currentScreenState == JTEuropeScreenState.GAME_SCREEN_STATE){
       if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.NEW_GAME_BUTTON_TYPE)
      {initPlayerAISelection(); graphRenderer.setMiniGridPaneMovingRight(true);}
       if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.EXIT_GAME_BUTTON_TYPE)
      {System.exit(0);}
      if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.LOAD_SAVE_GAME_TYPE)
      { try{this.file.loadJTERecord(); graphRenderer.setIsComingFromLoadData(true); this.initRealTimeJTEGameScreen();  this.getDataModel().setGameState(JTEuropeDataModel.JTEGameState.GAME_PLAYING_HANDS);}catch(Exception e){e.printStackTrace();}}
       if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.VIEW_STATS_BUTTON_TYPE)
       {initJTEStatsPane(); graphRenderer.setStatPaneMovingUp(true);}
       if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.VIEW_HELP_BUTTON_TYPE)
      { graphRenderer.setHelpPaneMovingLeft(true);initJTEHelpPane();}
       }
       
      if(this.currentScreenState == JTEuropeScreenState.PLAYING_GAME_STATE){
      if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.VIEW_HELP_BUTTON_TYPE)
      { graphRenderer.setHelpPaneMovingLeft(true);initJTEHelpPane();}
      if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.VIEW_STATS_BUTTON_TYPE)
      {initJTEStatsPane(); graphRenderer.setStatPaneMovingUp(true); System.out.println("Stats called");}
       if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.DICE_BUTTON_TYPE)
      {graphRenderer.setDiceAnimation(true);}
       if(sprite.getSpriteType().getSpriteTypeID()==JTEConstant.LOAD_SAVE_GAME_TYPE)
      { try{this.file.saveLevelRecord();}catch(Exception e){e.printStackTrace();}}
      }
      
     }
    //out function for logging
    public void out(String out)
    {System.out.println(out);}
    
    
}
