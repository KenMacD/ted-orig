package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the logviewer window
 * 
 * @author Roel
 * @author Joost
 *
 * ted License:
 * This file is part of ted. ted and all of it's parts are licensed
 * under GNU General Public License (GPL) version 2.0
 * 
 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
 *
 */
public class TedLogDialog extends JDialog implements ActionListener
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = -8661705723352441097L;
	private JTextArea log_all;
	private JTextArea log_simple;
	private JScrollPane scroll;
	private boolean logAll;
	private JPanel panel;
	private JTextField lines;
	private JButton clear;
	private JButton switchLog;
	private JButton save;
	private int maxLines;
	private boolean isClosed;
    private static TedLogDialog instance = null; 
    private Font normalFont = new Font(null,0,11);
    private TedLogWriter logWriter;
	
	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Constructs a TedLog window
	 */
	private TedLogDialog()
	{
        this.setIsClosed(true);
                
		initGUI();
		
		// init logwriter
		logWriter = new TedLogWriter();
        logWriter.resetLogFile();
	}
        
    /**
     * Singleton getInstance method
     */
    public static TedLogDialog getInstance() {
        if(instance == null)
            instance = new TedLogDialog();
        return instance;
    }


	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/
	private void initGUI() 
	{
		try {
				log_all = new JTextArea();
				log_all.setFont(normalFont);
				log_all.setEditable(false);
				logAll = false;
				
				log_simple = new JTextArea();
				log_simple.setFont(normalFont);
				scroll = new JScrollPane(log_simple);
				this.getContentPane().add(scroll, BorderLayout.CENTER);
				log_simple.setEditable(false);
				scroll.setVisible(true);
				
				panel = new JPanel();
				JLabel label1 = new JLabel(Lang.getString("TedLog.NumberOfLines"));
				panel.add(label1);
				maxLines = 1000;//this.getMaxLines();
				lines = new JTextField(""+maxLines, 4);
				panel.add(lines);
				save = new JButton(Lang.getString("TedLog.Reset"));
				save.addActionListener(this);
				save.setActionCommand("save");
				panel.add(save);
				clear = new JButton(Lang.getString("TedLog.Clear"));
				clear.addActionListener(this);
				clear.setActionCommand("clear");
				panel.add(clear);
				
				//not used at the moment
				switchLog = new JButton(Lang.getString("TedLog.DetailedLog"));
				switchLog.addActionListener(this);
				switchLog.setActionCommand("switch");
				panel.add(switchLog);
				
								
				this.getContentPane().add(panel, BorderLayout.SOUTH);
				
				this.setTitle(Lang.getString("TedLog.Name"));
				this.setSize(700, 400);
				
				this.addWindowListener(new WindowAdapter() 
						{
							public void windowClosing(WindowEvent evt) 
							{
								rootWindowClosing(evt);
							}
						}
					);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
    /****************************************************
     * PROTECTED METHODS
     ****************************************************/
    /**
     * Add an entry to the TedLog. This method should not be used
     * to add a log message. You should use the TedLogger methods
     * @param s String to be added
     */
    protected void addEntry(int level, String s)
    {       	
    	if(log_all.getLineCount()>=maxLines)
    	{
    		try 
    		{
    			int offset=0;
    			
    			if(maxLines!=0)
    				offset = log_all.getLineEndOffset(maxLines-2);
    			
    			int end =  log_all.getLineEndOffset(maxLines-1);
    			log_all.getDocument().remove(offset, (end-offset));
			}
    		catch (BadLocationException e) 
			{
				e.printStackTrace();
			}
    	}
    	
    	// ERROR or DEBUG message
    	if(level == 0 || level == 1)
    	{
    		log_all.insert(s, 0);
    	}
    	// SIMPLE message
    	else if(level == 2)
    	{
    		log_simple.insert(s, 0);
    	}
    	
    	try
		{
       		// write line to file
    		if (TedLog.isWriteToFile())
    		{
    			logWriter.addLine(s);
    		}
		} 
       	catch (IOException e)
		{
       		System.out.println("Error writing logfile");
		}
    }
    
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
    /**
     * cleans up this window when Root Window is closing 
     */
	public void rootWindowClosing(WindowEvent e)
	{
		isClosed = true;
		this.setVisible(false);
	}
	
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/
	/**
	 * @return if the logwindow is closed
	 */
	public boolean getIsClosed()
	{
		return this.isClosed;
	}
	
	/**
	 * Set if the window is closed
	 * @param b
	 */
	public void setIsClosed(boolean b)
	{
		isClosed = b;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("clear"))
		{
			log_all.setText("");
			log_simple.setText("");
		}
		else if(arg0.getActionCommand().equals("switch"))
		{
			this.getContentPane().remove(scroll);
			
			if(logAll)
			{
				switchLog.setText(Lang.getString("TedLog.DetailedLog"));
				scroll = new JScrollPane(log_simple);
				logAll = false;
			}
			else
			{
				switchLog.setText(Lang.getString("TedLog.SummaryLog"));
				scroll = new JScrollPane(log_all);
				logAll = true;
			}
			
			this.getContentPane().add(scroll, BorderLayout.CENTER);
			this.getContentPane().repaint();
			this.getContentPane().validate();
			scroll.repaint();
			scroll.validate();
		}
		else if(arg0.getActionCommand().equals("save"))
		{
			this.resetAction();
		}	
	}
	
	public void setLines(int l)
	{
		lines.setText("" + l);
		setMaxLines(l);		
	}
	
	public void setMaxLines(int i)
	{
		this.maxLines = i;
	}
	
	public int getLines()
	{
		int i =  -1;
		
		try
		{
			i = Integer.parseInt(lines.getText());
		}
		catch(NumberFormatException e)
		{
			TedLog.error(lines.getText() + " " + Lang.getString("TedConfigDialog.DialogNotANumber1") + " The number of lines");
		}
		
		return i;
	}
	
	public int getMaxLines()
	{
		return this.maxLines;
	}
	
	public void resetAction()
	{
		this.resetAction(log_all);
		this.resetAction(log_simple);		
	}
	
	public void resetAction(JTextArea log)
	{
		if(getLines() > 1)
		{
			if(getLines()<log.getLineCount())
			{
				try 
	    		{
	    			int offset = log.getLineEndOffset(log.getLineCount()-(log.getLineCount()-getLines())-1);
	    			int end =  log.getLineEndOffset(log.getLineCount()-1);
	    			log.getDocument().remove(offset, (end-offset));
				}
	    		catch (BadLocationException e) 
				{
					e.printStackTrace();
				}
			}
			this.setMaxLines(this.getLines());
		}
		else
		{
			TedLog.debug(Lang.getString("TedLog.NeedMoreLines"));
		}
	}
}
