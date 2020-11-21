package score;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import javax.swing.JOptionPane;

import game.SokobanWars;

public class Score {
	private static Score INSTANCE;
    private static final int MAX_SCORE=200;	
    private ArrayList<String> levelScores;
    private String playerName;
    
    
    public Score() {
    	this.playerName = SokobanWars.getInstance().getUsername();
    	if(playerName == null) {
    		this.playerName = "No username found";
    	}
    	
    	levelScores = new ArrayList<>();
    }
    

	 public static Score getInstance() {
	        if (INSTANCE == null) {
	            INSTANCE = new Score();
	        }
	        return INSTANCE;
	    }
	 
	 
    public int getPlayerScore() {
    	return MAX_SCORE-SokobanWars.getInstance().getPlayer().getMoves();
    }
    public void WriteScoresIntoArray () {
    	
    	levelScores.add ("Level "+ getLevelNumber()+" ----> " + getPlayerScore()+" points" + " \n");
    	
    }
    
  
    
    public String getUsername() {
    	return this.playerName;
    }
    public int getLevelNumber() {
    	return SokobanWars.getInstance().getLevelNumber();
    }
	
    public void writeScore()  {
    
    
	try {
		
		FileWriter writer;
		writer = new FileWriter("scores/"+this.playerName+" Last Score.txt");
	
    
   
    writer.write("Jedi: "+ this.playerName +" \n");
   
    for (String s : levelScores) {
    
    	writer.write(s);
    
    }
   
  
    writer.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
   
    
    
    }
    
    

}
