/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini_board_game_events;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import mini_board_game_ui.JTEuropeUI;

public class JTEHyperLinkListener implements HyperlinkListener {
	private JTEuropeUI ui;
	
	public JTEHyperLinkListener (JTEuropeUI initUI){
		ui = initUI;
		
	}

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
           
            try{
                       if(e.getURL().equals("HOME")){
                        System.out.println(e.getURL());
                        URL url = new URL(e.getURL().toString());
                        ui.loadRemoteHelpPage(url);
                       }
              
            }catch(Exception s){System.out.println(s.getMessage());}
        }
    }
}

