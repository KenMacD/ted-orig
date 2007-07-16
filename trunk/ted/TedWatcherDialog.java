package ted;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the dialog where a user can edit a show
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
public class TedWatcherDialog extends javax.swing.JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label_info;
	private boolean newWatcher;
	TedWatcher currentWatcher;
	TedMainDialog TMdialog;
	private JLabel dialog;
	private JTextField text_feed;
	private JComboBox combo_after;
	private JLabel label_after;
	private JComboBox watcher_chooser;
	private JButton button_cancel;
	private JButton button_save;
	private JTextField text_key;
	private JTextField text_max;
	private JTextField text_min;
	private JTextField text_name;
	private JLabel label_keywords;
	private JLabel label_rss;
	private JLabel label_name;
	private JLabel label_max;
	private JLabel label_min;
	private JLabel label_url;
	private Vector names;
	private Vector locations;

	public TedWatcherDialog(TedMainDialog d, TedWatcher watcher, boolean ns)
	{
		this.currentWatcher = watcher;
		this.newWatcher = ns;
		TMdialog = d;
		initGUI();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String actionCommand = arg0.getActionCommand(); 
		if(actionCommand.equals("Save"))
		{
			int max;
			int min;
			try
			{
				max = Integer.parseInt(text_max.getText());
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, "Please enter a number for the maximum size");
				return;
			}
			try
			{
				min = Integer.parseInt(text_min.getText());
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, "Please enter a number for the minimum size");
				return;
			}
			if (text_name.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Please enter a name");
				return;
			}
			if (text_feed.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Please enter a rss feed");
				return;
			}
			if (text_key.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Please enter keywords");
				return;
			}
			currentWatcher.setName(text_key.getText() + ": " + text_name.getText());
			//currentWatcher.setUrl(text_feed.getText());
			currentWatcher.setMinSize(min);
			currentWatcher.setMaxSize(max);
			currentWatcher.setKeywords(text_key.getText());
			
			String chosen_status = combo_after.getSelectedItem().toString();
			if(chosen_status.equals("Hold"))
			{
				currentWatcher.setAfterStatus(TedSerie.STATUS_HOLD);
			}
			else if(chosen_status.equals("Resume"))
			{
				currentWatcher.setAfterStatus(TedSerie.STATUS_CHECK);
			}
			else if(chosen_status.equals("Delete"))
			{
				currentWatcher.setAfterStatus(TedWatcher.STATUS_DELETE);
			}
			
			if (newWatcher)
			{
				TMdialog.addSerie(currentWatcher);
			}
			TMdialog.saveShows();	
			this.setVisible(false);
		}
		else if(actionCommand.equals("cancel"))
		{
			this.setVisible(false);
		}
		else if(actionCommand.equals("chooser"))
		{
			JComboBox cb = (JComboBox)arg0.getSource();
			// if the user doesnt select the "Select One" string
			if (cb.getSelectedIndex() > 0)
			{
				// put the name and the url of the selected show in the textfields
				String name = (String)names.get(cb.getSelectedIndex());
				String url = (String)locations.get(cb.getSelectedIndex());
		
				text_name.setText(name);
				text_feed.setText(url);
			}
		}
	}
	
	private void initGUI() 
	{
		try {
			{
				label_info = new JLabel();
				this.getContentPane().add(label_info, BorderLayout.NORTH);
				if(this.newWatcher)
				{
					String s = "ted: New Watcher";
					label_info.setText(s);
					this.setTitle(s);
				}
				else
				{
					String s = "ted: Edit " + currentWatcher.getName();
					label_info.setText(s);
					this.setTitle(s);
				}
			}
			{
				dialog = new JLabel();
				this.getContentPane().add(dialog, BorderLayout.CENTER);
				dialog.setPreferredSize(new java.awt.Dimension(327, 267));
				{
					label_max = new JLabel();
					dialog.add(label_max);
					label_max.setText("Maximum size");
					label_max.setBounds(29, 155, 89, 30);
				}
				{
					label_name = new JLabel();
					dialog.add(label_name);
					label_name.setText("Name");
					label_name.setBounds(29, 20, 60, 30);
				}
				{
					label_rss = new JLabel();
					dialog.add(label_rss);
					label_rss.setText("RSS feed");
					label_rss.setBounds(29, 55, 60, 30);
				}
				{
					label_url = new JLabel();
					dialog.add(label_url);
					label_url.setText("Url:");
					label_url.setBounds(29, 90, 60, 30);
				}
				{
					label_keywords = new JLabel();
					dialog.add(label_keywords);
					label_keywords.setText("Keywords");
					label_keywords.setBounds(29, 190, 60, 30);
				}
				{
					label_min = new JLabel();
					dialog.add(label_min);
					label_min.setText("Minimum size");
					label_min.setBounds(29, 120, 91, 30);
				}
				{
					text_name = new JTextField();
					dialog.add(text_name);
					text_name.setText(currentWatcher.getName());
					text_name.setBounds(135, 20, 225, 30);
				}
				{
					text_feed = new JTextField();
					dialog.add(text_feed);
					//text_feed.setText(currentWatcher.getUrl());
					text_feed.setBounds(135, 90, 225, 30);
				}
				{
					text_min = new JTextField();
					dialog.add(text_min);
					text_min.setText("" + currentWatcher.getMinSize());
					text_min.setBounds(135, 125, 60, 30);
				}
				{
					text_max = new JTextField();
					dialog.add(text_max);
					text_max.setText("" + currentWatcher.getMaxSize());
					text_max.setBounds(135, 160, 60, 30);
				}
				{
					text_key = new JTextField();
					dialog.add(text_key);
					text_key.setText(currentWatcher.getKeywords());
					text_key.setBounds(135, 195, 225, 30);
				}
				{
					button_save = new JButton();
					dialog.add(button_save);
					button_save.setText("Save");
					button_save.setBounds(280, 265, 80, 30);
					button_save.setActionCommand("Save");
					button_save.addActionListener(this);
				}
				{
					button_cancel = new JButton();
					dialog.add(button_cancel);
					button_cancel.setText("Cancel");
					button_cancel.addActionListener(this);
					button_cancel.setBounds(195, 265, 80, 30);
					button_cancel.setActionCommand("cancel");
				}
				{
					this.ReadRSSFile();
					ComboBoxModel watcher_chooserModel = new DefaultComboBoxModel(names);
					watcher_chooser = new JComboBox();
					dialog.add(watcher_chooser);
					watcher_chooser.setModel(watcher_chooserModel);
					watcher_chooser.setBounds(135, 55, 225, 30);
					watcher_chooser.addActionListener(this);
					watcher_chooser.setActionCommand("chooser");
				}
				{
					label_after = new JLabel();
					dialog.add(label_after);
					label_after.setText("After download");
					label_after.setBounds(29, 230, 90, 30);
				}
				{
					ComboBoxModel combo_afterModel = new DefaultComboBoxModel(
						new String[] { "Hold", "Resume"});
					combo_after = new JComboBox();
					dialog.add(combo_after);
					combo_after.setModel(combo_afterModel);
					combo_after.setBounds(135, 230, 75, 30);

					if(currentWatcher.getAfterStatus() == TedWatcher.STATUS_HOLD)
					{
						combo_after.setSelectedIndex(0);
					}
					else if(currentWatcher.getAfterStatus() == TedWatcher.STATUS_CHECK)
					{
						combo_after.setSelectedIndex(1);
					}
					// for later to implement
					/*else if(currentWatcher.getAfterStatus() == TedWatcher.STATUS_DELETE)
					{
						combo_after.setSelectedIndex(2);
					}*/
					
					combo_after.addActionListener(this);
					combo_after.setActionCommand("after");
				}
			}
			{
				this.setSize(382, 354);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read the textfile with predefined shows and rss urls
	 */
	private void ReadRSSFile() 
	{
		names = new Vector();
		locations = new Vector();
		
		names.addElement("Select one");
		locations.addElement("");
		
		BufferedReader br;
		try 
		{
			br = new BufferedReader(new FileReader("rss-watch.txt"));
			String line = null;
			StringTokenizer str;

			while ((line = br.readLine()) != null) 
			{
				str = new StringTokenizer(line, ":");
				names.addElement(str.nextToken());
				locations.addElement((str.nextToken()
					+ ":"
					+ str.nextToken()).trim());
			}

			br.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}		
	}
}
