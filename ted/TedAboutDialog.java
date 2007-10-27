package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;


/**
 * TED: Torrent Episode Downloader (2005 - 2007)
 * 
 * This is the about dialog of ted, it shows some trivial information
 * 
 * ted License:
 * This file is part of ted. ted and all of it's parts are licensed
 * under GNU General Public License (GPL) version 2.0
 * 
 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
 * 
 * @author Roel
 * @author Joost
  */

public class TedAboutDialog extends javax.swing.JDialog
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = -4699341387814031284L;
	private JPanel aboutPanel;
	private Image logo;
	private double version;
	
	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Show the about dialog
	 * @param version Current version of ted
	 */
	public TedAboutDialog(double version)
	{
		this.version = version;
		this.initGUI();
	}

	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/	
	private void initGUI()
	{
		this.setVisible(false);
		this.setSize(300, 300);	
		this.setResizable(false);
		//this.setAlwaysOnTop(true);
		this.setTitle("About");
		{
			aboutPanel = new JPanel();
			this.getContentPane().add(aboutPanel, BorderLayout.CENTER);
			Color background = new Color(63, 63, 63);
			aboutPanel.setBackground(background);
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		logo = toolkit.getImage(getClass().getClassLoader().getResource("icons/logo.jpg"));
		MediaTracker mediaTracker = new MediaTracker(this);
		mediaTracker.addImage(logo, 0);
		this.repaint();
		this.setVisible(true);
	}
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/	
	public void paint(Graphics g)
	{
		Color background = new Color(63, 63, 63);
		g.setColor(background);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.drawImage(logo, 5, 30, this);
	
		g.setColor(Color.WHITE);
		g.drawString("ted v" + version, 10, 170);
		g.drawString("Created by Roel and Joost", 10, 190);
		g.drawString(Lang.getString("Lang.TranslatorCredits"), 10, 210);
		String s1 = "The authors of this software can't be held";
		String s2 = "responsible for any damage or illegal usage";
		String s3 = "you may experience";
		g.drawString(s1, 10, 250);
		g.drawString(s2, 10, 265);
		g.drawString(s3, 10, 280);
	}
}
