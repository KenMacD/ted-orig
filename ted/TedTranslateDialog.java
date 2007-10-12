package ted;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

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
public class TedTranslateDialog extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7178318494685933166L;
	private JScrollPane scrollPane;
	private JButton buttonSave;
	private JButton buttonOpen;
	private JPanel buttonPanel;
	private JTable tableKeys;
	private Properties workingProperties;
	private String workingCopyLocation = "";
	private String [] headers = {"ID", "Original", "Translation"};
	private JPanel searchPanel;
	private JButton buttonNext;
	private JComboBox comboColumn;
	private JLabel labelIn;
	private JLabel labelSearch;
	private JTextField textSearch;
	private JButton buttonHelp;

	public TedTranslateDialog()
	{
		readOriginal();
		initGUI();
	}
	
	private void initGUI() 
	{
		try {
			{
				scrollPane = new JScrollPane(tableKeys);
				getContentPane().add(scrollPane, BorderLayout.CENTER);
			}
			{
				buttonPanel = new JPanel();
				getContentPane().add(buttonPanel, BorderLayout.NORTH);
				{
					buttonOpen = new JButton();
					buttonPanel.add(buttonOpen);
					buttonOpen.setText("Open");
					buttonOpen.addActionListener(this);
					buttonOpen.setActionCommand("open");
				}
				{
					buttonSave = new JButton();
					buttonPanel.add(buttonSave);
					buttonSave.setText("Save");
					buttonSave.addActionListener(this);
					buttonSave.setActionCommand("save");
				}
				{
					buttonHelp = new JButton();
					buttonPanel.add(buttonHelp);
					buttonHelp.setText("Help");
					buttonHelp.addActionListener(this);
					buttonHelp.setActionCommand("help");
				}
			}
			{
				searchPanel = new JPanel();
				getContentPane().add(searchPanel, BorderLayout.SOUTH);
				{
					labelSearch = new JLabel();
					searchPanel.add(labelSearch);
					labelSearch.setText("Search for:");
				}
				{
					textSearch = new JTextField();
					searchPanel.add(textSearch);
					textSearch.setPreferredSize(new java.awt.Dimension(108, 20));
				}
				{
					labelIn = new JLabel();
					searchPanel.add(labelIn);
					labelIn.setText("in");
				}
				{
					ComboBoxModel comboColumnModel = new DefaultComboBoxModel(headers);
					comboColumn = new JComboBox();
					searchPanel.add(comboColumn);
					comboColumn.setModel(comboColumnModel);
				}
				{
					buttonNext = new JButton();
					searchPanel.add(buttonNext);
					buttonNext.setText("Find next");
					buttonNext.addActionListener(this);
					buttonNext.setActionCommand("next");
				}
			}
			{
				this.setSize(1024, 350);
				this.setTitle("ted translator v0.1 beta");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void readOriginal()
	{
		File f = new File("tedLang.properties");
		
		// Init tables
	    MyTableModel tm = new MyTableModel();
	    String [][] data;
	    
		if(f.exists())
		{
			// Read properties file.
		    Properties properties = new Properties();
		    try 
		    {
		        properties.load(new FileInputStream("tedLang.properties"));
		    } 
		    catch (IOException e) 
		    {
		    
		    }
		    	  
		    // Get keys from properties
		    Enumeration<?> keys = properties.keys();
		    
		    // Put keys in a vector so they can be added to the table
		    Vector<String> items = new Vector<String>();
		    while(keys.hasMoreElements())
		    {
		    	items.add((String)keys.nextElement());
		    }
		    
		    // Add keys and values to their own table
		    data = new String[items.size()][3];
		    
		    // Language and Credits at top of table
		    data[0][0] = "Lang.Name"; data[0][1] = "(add language name)";
		    data[1][0] = "Lang.TranslatorCredits"; data[1][1] = "(add your name)";
		    String key;
		    int offset = 2;
		    int row;
		    for(int i=0; i<items.size(); i++)
		    {
		    	key = items.get(i);
		    	
		    	// Compensate for name and credits at top of table
		    	if(key.equals("Lang.Name"))
		    	{
		    		row = 0;
		    		offset--;
		    	}
		    	else if(key.equals("Lang.TranslatorCredits"))
		    	{
		    		row = 1;
		    		offset--;
		    	}
		    	else
		    	{
		    		row = i+offset;
		    	
			    	// Add data
			    	data[row][0] = key;
			    	data[row][1] = properties.getProperty(key);
		    	}
		    }
		}
		else
		{
			// Add keys and values to their own table
		    data = new String[2][3];
		    
		    // Language and Credits at top of table
		    data[0][0] = "Can't find the tedLang.properties file";
		    data[1][0] = "Check 'help' to get the translator to function";	
		}

	    tm.setDataVector(data, headers);
	    tableKeys = new JTable(tm);
	}

	private void readWorkingCopy() 
	{
		try
		{
		    JFileChooser chooser = new JFileChooser();
		    
		    // Set the current directory
		    File f = new File(new File(".").getCanonicalPath());
	        chooser.setCurrentDirectory(f);
		    
	        int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) 
		    {
		    	workingCopyLocation = chooser.getSelectedFile().getAbsolutePath();
			    			    
				// Read properties file.
			    workingProperties = new Properties();
			    try 
			    {
			    	workingProperties.load(new FileInputStream(workingCopyLocation));
			    	
			    	// Get the information from the properties file
				    // And put it in the table
				    String key = "";
				    for(int i=0; i<tableKeys.getRowCount(); i++)
				    {
				    	key = (String)tableKeys.getValueAt(i, 0);
				    	tableKeys.setValueAt(workingProperties.getProperty(key), i, 2);
				    }
			    }
			    catch (IOException e) 
			    {
			    
			    }
		    } 
		}
		catch(IOException e)
		{
			
		}
	}
	
	private void saveWorkingCopy()
	{
		
		JFileChooser chooser = new JFileChooser();
	    
		try 
		{
			//Set the current directory
			File f = new File(new File(workingCopyLocation).getCanonicalPath());
		
	        chooser.setCurrentDirectory(f);
	        chooser.setSelectedFile(f);
		    
	        int returnVal = chooser.showSaveDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION)
		    {
				Properties saveProperty = new Properties();
				String key="";
				String value="";
				
				for(int i=0; i<tableKeys.getRowCount(); i++)
			    {
					key = (String)tableKeys.getValueAt(i, 0);
					value = (String)tableKeys.getValueAt(i, 2);
					if(!(value==null))
						saveProperty.setProperty(key, value);
			    }
				
				try 
				{
			        saveProperty.store(new FileOutputStream(chooser.getSelectedFile().getCanonicalPath()), null);
			    } 
				catch (IOException e) 
				{	
			    }
		    }
		}
	    catch (IOException e1) 
	    {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		String action = arg0.getActionCommand();
		if(action.equals("open"))
		{
			readWorkingCopy();
		}
		else if(action.equals("save"))
		{
			saveWorkingCopy();
		}
		else if(action.equals("help"))
		{
			// try to open the ted documentation website
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/wiki/index.php/Translate_translator"); //$NON-NLS-1$
			} 
			catch (IOException ep) 
			{
				// error launching ted website
				// TODO: add error message
				System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
				ep.printStackTrace();
			}	
		}
		else if(action.equals("next"))
		{
			FindNextInTable();
		}
	}
	
	private void FindNextInTable()
	{
		int searchColumn = comboColumn.getSelectedIndex();
		int selectedRow  = Math.max(0, tableKeys.getSelectedRow());
		String searchString = textSearch.getText().toLowerCase();
		
		// first search the part of the table after the selected row
		for(int i=selectedRow+1; i<tableKeys.getRowCount(); i++)
		{
			if(((String)tableKeys.getValueAt(i,searchColumn)).toLowerCase().contains(searchString))
			{
				// select row
				tableKeys.setRowSelectionInterval(i, i);
				
				// adjust scrollbar
				int scrollvalue = (i*scrollPane.getVerticalScrollBar().getMaximum())/tableKeys.getRowCount();
				scrollPane.getVerticalScrollBar().setValue(scrollvalue);
				
				break;
			}			
		}
		
		// and if nothing is found the part before it
		for(int i=0; i<selectedRow; i++)
		{
			if(((String)tableKeys.getValueAt(i,searchColumn)).toLowerCase().contains(searchString))
			{
				tableKeys.setRowSelectionInterval(i, i);
				
				int scrollvalue = (i*scrollPane.getVerticalScrollBar().getMaximum())/tableKeys.getRowCount();
				scrollPane.getVerticalScrollBar().setValue(scrollvalue);
				
				break;
			}			
		}
	}
	
	class MyTableModel extends AbstractTableModel 
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = -3815927013633221088L;
		private String[] columnNames;
	    private Object[][] data;

	    public int getColumnCount() 
	    {
	        return columnNames.length;
	    }

	    public int getRowCount() 
	    {
	        return data.length;
	    }

	    public String getColumnName(int col) 
	    {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) 
	    {
	        return data[row][col];
	    }

	    public boolean isCellEditable(int row, int col) 
	    {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        if (col < 2) 
	        	return false;
	        else 
	            return true;
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
	    public void setValueAt(Object value, int row, int col) 
	    {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }
	    
	    public void setDataVector(String[][] data, String[] names)
	    {
	    	columnNames = names;
	    	this.data = data;
	    }
	}
}
