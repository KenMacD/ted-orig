package ted;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class TedColorPicker extends javax.swing.JFrame
                            implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private TedMainDialog mainDialog;
	private JButton returnButton;
	protected JColorChooser tcc;

    public TedColorPicker(TedMainDialog main) 
    {       
    	this.setSize(500, 450);
    	this.setTitle("Choose your colors");
    	this.setResizable(false);
    	
    	mainDialog = main;
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new BorderLayout());
    	
        tcc = new JColorChooser();
        
        AbstractColorChooserPanel panels[] = tcc.getChooserPanels();
        AbstractColorChooserPanel newPanels[] = { panels[1] };
        tcc.setChooserPanels(newPanels);
        
        panel.add(tcc, BorderLayout.NORTH);
  
        JButton oddRowColor     = new JButton("Odd rows");
        JButton evenRowColor    = new JButton("Even rows");
        JButton restoreDefaults = new JButton("Restore defaults");
    	JButton returnButton    = new JButton("Return to preferences");

        oddRowColor    .addActionListener(this);
        evenRowColor   .addActionListener(this);
        restoreDefaults.addActionListener(this);
    	returnButton   .addActionListener(this);

        oddRowColor    .setActionCommand("odd");
        evenRowColor   .setActionCommand("even");
        restoreDefaults.setActionCommand("default");
    	returnButton   .setActionCommand("return");

        JLabel label = new JLabel("Assign selected color to:");
        
        JPanel buttonPanel  = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        BorderLayout buttonPanelLayout = new BorderLayout();
        BorderLayout buttonPanel2Layout = new BorderLayout();
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanel2.setLayout(buttonPanel2Layout);
        buttonPanel2.add(label, BorderLayout.NORTH);
        buttonPanel2.add(oddRowColor, BorderLayout.WEST);
        buttonPanel2.add(evenRowColor, BorderLayout.CENTER);
        buttonPanel2.add(restoreDefaults, BorderLayout.EAST);
    	buttonPanel.add(returnButton, BorderLayout.SOUTH);
        
    	panel.add(buttonPanel2, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        this.getContentPane().add(panel);
    }

	public void actionPerformed(ActionEvent action) 
	{
		String command = action.getActionCommand();
		if (command.equals("odd"))
		{
			TedConfig.setOddRowColor(tcc.getColor());
		}
		else if (command.equals("even"))
		{
			TedConfig.setEvenRowColor(tcc.getColor());
		}
		else if (command.equals("default"))
		{
			TedConfig.restoreDefaultColors();
		}
		else if (command.equals("return"))
		{
			this.setVisible(false);
			ActionEvent event = new ActionEvent(this, 6, "Preferences...");
			mainDialog.actionPerformed(event);
		}	
		
		mainDialog.updateGUI();
	}
}
