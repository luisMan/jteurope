/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
* google speech api has been implemented to make game even more fun lol
 */

package mini_board_game_ui;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;


/**
 *
 * @author ProgrammingSection
 */
public class JTETalkingRobot {
    
    private JTEuropeUI game;
    
    public JTETalkingRobot(JTEuropeUI game)
    {
        this.game =game;
    }
    
    public void text(String text)
    {
    Audio audio = Audio.getInstance();
    try{
    InputStream sound  = audio.getAudio(text, Language.ENGLISH);
    audio.play(sound);
    }catch(Exception e){}
    }
    
}
