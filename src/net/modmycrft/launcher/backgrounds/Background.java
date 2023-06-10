package net.modmycrft.launcher.backgrounds;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel {
	private static final long serialVersionUID = 3798549706748053999L;

	public void paintComponent(Graphics g){
        Image im = null;
    		Calendar calendar1 = Calendar.getInstance();
    		calendar1.setTime(new Date());
    		if (calendar1.get(5) == 25 || calendar1.get(5) == 26 ||calendar1.get(5) == 27) {
    			im = this.loadImage("bdayBackground.png");
    		} else {
        		if (calendar1.get(2) + 1 == 12 || calendar1.get(2) + 1 == 1 || calendar1.get(2) + 1 == 2){
                    im = this.loadImage("snowBackground.png");
        		} else {
                    im = this.loadImage("background.png");
        		} 
    		}
        g.drawImage(im, 0, 0, null); 
    }
	 public BufferedImage loadImage(String fileName){

		    BufferedImage buff = null;
		    try {
		        buff = ImageIO.read(getClass().getResourceAsStream(fileName));
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
		    return buff;

		}
}