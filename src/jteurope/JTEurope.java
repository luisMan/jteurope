/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jteurope;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mini_board_game_ui.JTEuropeUI;
import properties_manager.PropertiesManager;

/**
 *
 * @author ProgrammingSection
 */
public class JTEurope extends Application {
    
    static String PROPERTY_TYPES_LIST = "property_types.txt";
    static String UI_PROPERTIES_FILE_NAME = "properties.xml";
    static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    static String DATA_PATH = "./data/";
    
    
    @Override
    public void start(Stage primaryStage) {
       
        try{
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(JTEuropePropertyType.UI_PROPERTIES_FILE_NAME,
                    UI_PROPERTIES_FILE_NAME);
            props.addProperty(JTEuropePropertyType.PROPERTIES_SCHEMA_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
            props.addProperty(JTEuropePropertyType.DATA_PATH.toString(),
                    DATA_PATH);
            props.loadProperties(UI_PROPERTIES_FILE_NAME,
                    PROPERTIES_SCHEMA_FILE_NAME);
        
        
        
        
        
        StackPane root = new StackPane();
        JTEuropeUI game  = new JTEuropeUI(primaryStage, 1200, 700);
     
        root.getChildren().add(game.getMainPane());
        
        Scene scene = new Scene(root, game.getGameWidth(), game.getGameHeight());
        game.setScene(scene);
        game.initJTEGUIControls();
        primaryStage.setTitle("JTEurope by Luis Manon");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
     public enum JTEuropePropertyType {
        /* SETUP FILE NAMES */

        UI_PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME,
        /* DIRECTORIES FOR FILE LOADING */
        DATA_PATH, IMG_PATH,
        /* WINDOW DIMENSIONS */
        WINDOW_WIDTH, WINDOW_HEIGHT,
        /* LEVEL OPTIONS PROPERTIES */
        CARDS_OPTIONS, GAME_FILES, CARDS_IMAGE_NAMES,
        PLAYER_SCREEN_TITLE_TEXT,
        /* IMAGE FILE NAMES */
         DICE_GAME_IMG_NAME,STATS_GAME_IMG_NAME,  EXIT_GAME_IMG_NAME,NEW_GAME_IMG_NAME, LOAD_GAME_IMG_NAME,TIME_IMG_NAME,BACKGROUND_IMAGE,OCEAN_IMAGE,AIRPLANE_IMAGE,HELP_GAME_IMG_NAME,
         AIR_IMAGE,BOAT_IMAGE,OVER_GAME_IMAGE_NAME,FLAG_IMAGES,FLAG_PIECES,COORD_DIRECTIONS,DICE_GAME,GREEN_CARD_GAME,RED_CARD_GAME,YELLOW_CARD_GAME,JTE_IMAGE,BACK_GAME_IMG_NAME,FLY_PLAN,
         
        /* DATA FILE STUFF */
        STATS_FILE_NAME, HELP_FILE_NAME, 
        /* TOOLTIPS */
        GAME_TOOLTIP, STATS_TOOLTIP, HELP_TOOLTIP, EXIT_TOOLTIP, NEW_GAME_TOOLTIP, HOME_TOOLTIP,
        /*
         * THESE ARE FOR LANGUAGE-DEPENDENT ERROR HANDLING, LIKE FOR TEXT PUT
         * INTO DIALOG BOXES TO NOTIFY THE USER WHEN AN ERROR HAS OCCURED
         */
        ERROR_DIALOG_TITLE_TEXT,  IMAGE_LOADING_ERROR_TEXT, INVALID_URL_ERROR_TEXT, INVALID_DOC_ERROR_TEXT, INVALID_XML_FILE_ERROR_TEXT, INVALID_GUESS_LENGTH_ERROR_TEXT, WORD_NOT_IN_DICTIONARY_ERROR_TEXT, INVALID_DICTIONARY_ERROR_TEXT,
        INSETS
    }
}
