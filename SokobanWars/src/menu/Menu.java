package menu;

import game.SokobanWars;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

    public Rectangle playButton = new Rectangle(SokobanWars.getInstance().getWidth() / 2 + 120, 150, 100, 50);
    public Rectangle helpButton = new Rectangle(SokobanWars.getInstance().getWidth() / 2 + 120, 150, 100, 50);
    public Rectangle quitButton = new Rectangle(SokobanWars.getInstance().getWidth() / 2 + 120, 150, 100, 50);
    
    public void render(Graphics g) {
      
        Font fnt0 = new Font("Star Jedi",Font.PLAIN ,50);
        
        g.setFont(fnt0);
        g.setColor(Color.yellow);
        g.drawString("Sokoban Wars", SokobanWars.getInstance().getHeight() / 2, 100);
        
        Font fnt1 = new Font("arial",Font.BOLD ,30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x, playButton.y);
        
       /* 
        g2d.draw(playButton);
        g2d.draw(helpButton);
     
        g2d.draw(quitButton);
        */
    }
    
   
    
}


