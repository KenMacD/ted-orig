package ted;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class TedColorPicker extends javax.swing.JFrame
                            implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private TedMainDialog mainDialog;
	protected JColorChooser tcc;

    public TedColorPicker(TedMainDialog main) 
    {       
    	this.setSize(500, 450);
    	this.setTitle("Choose your colors");
    	this.setResizable(false);
    	
    	mainDialog = main;
    	
    	JPanel panel = new JPanel();
    	
        tcc = new JColorChooser();
        
        AbstractColorChooserPanel panels[] = tcc.getChooserPanels();
        AbstractColorChooserPanel newPanels[] = { panels[1] };
        tcc.setChooserPanels(newPanels);
        
        panel.add(tcc, BorderLayout.SOUTH);
  
        JButton oddRowColor     = new JButton("Odd rows");
        JButton evenRowColor    = new JButton("Even rows");
        JButton restoreDefaults = new JButton("Restore defaults");
        
        oddRowColor    .addActionListener(this);
        evenRowColor   .addActionListener(this);
        restoreDefaults.addActionListener(this);
        
        oddRowColor    .setActionCommand("odd");
        evenRowColor   .setActionCommand("even");
        restoreDefaults.setActionCommand("default");
        
        JLabel label = new JLabel("Assign selected color to:");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(label,           BorderLayout.NORTH);
        buttonPanel.add(oddRowColor,     BorderLayout.SOUTH);
        buttonPanel.add(evenRowColor,    BorderLayout.SOUTH);
        buttonPanel.add(restoreDefaults, BorderLayout.SOUTH);
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        
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
		
		mainDialog.updateGUI();
	}
}
