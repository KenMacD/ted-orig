package ted;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import ted.ui.editshowdialog.EditShowDialog;

/**
 * @author Joost
 * The TedPopupMenu receives a Vector of TedPopupItems. These menu items are seperated by
 * type and shown in the popup menu. After selecting an item the action that has to be
 * done is determined by the type the menu item has.
 *
 */
public class TedPopupMenu extends JPopupMenu implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private Vector<TedPopupItem> allItems = new Vector<TedPopupItem>();
	private Vector<TedPopupItem> search   = new Vector<TedPopupItem>();
	private Vector<TedPopupItem> category = new Vector<TedPopupItem>();
	private Vector<TedPopupItem> general  = new Vector<TedPopupItem>();
	private TedPopupItem help;

	private EditShowDialog dialog;

	/**
	 * Constructs a new TedPopupMenu
	 * @param v The vector containing the JPopupItems
	 * @param dialog2 The Episode Dialog that initialized this menu
	 */
	public TedPopupMenu(EditShowDialog dialog2, Vector<TedPopupItem> v)
	{
		dialog = dialog2;
		
		// make a copy determine which action has to be done later on
		this.allItems = v;
			
		// add the "add empty" item
		TedPopupItem pi = new TedPopupItem();
		pi.setName(Lang.getString("TedEpisodeDialog.FeedsTable.UserDefined"));
		pi.setActionCommand("empty");
		pi.setType(TedPopupItem.IS_EMPTY);
		this.addItem(pi);
		pi = new TedPopupItem();
		pi.setName(Lang.getString("TedEpisodeDialog.FeedsTable.Automatic"));
		pi.setActionCommand("auto");
		pi.setType(TedPopupItem.IS_EMPTY);
		this.addItem(pi);
		this.addSeparator();
		
		// divide the vector in groups
		this.divideMenu(allItems);
		
		// add the groups to the menu (divided by a seperator)
		this.setMenu(search);
		this.setMenu(category);
		this.setMenu(general);
		
		// add help item		
		this.addItem(help);
	}


	/**
	 * Divides the given vector in groups based on the type of the 
	 * JPopupItem 
	 */
	private void divideMenu(Vector<TedPopupItem> v)
	{
		TedPopupItem item;
		int type;
		for(int i=0; i<v.size(); i++)
		{
			item = (TedPopupItem)v.get(i);
			type = item.getType();
			
			if(type==TedPopupItem.IS_SEARCH_BASED || type == TedPopupItem.IS_SEARCH_AND_AUTO)
				search.add(item);
			else if(type==TedPopupItem.IS_CATEGORY_BASED)
				category.add(item);
			else if(type==TedPopupItem.IS_GENERAL_FEED)
				general.add(item);
			else if(type==TedPopupItem.IS_HELP)
			{
				help = item;
				item.setName(Lang.getString("TedEpisodeDialog.Help"));
			}
		}
	}
	
	/**
	 * Add the items from the vector to the menu
	 */
	private void setMenu(Vector<TedPopupItem> v)
	{
		if(v.size()!=0)
		{
			for(int i=0; i<v.size(); i++)
			{
				TedPopupItem pi = (TedPopupItem)v.get(i);
				this.addItem(pi);
			}
			this.addSeparator();
		}
	}
	
	
	private void addItem(TedPopupItem item) 
	{
		item.addActionListener(this);
		this.add(item);
		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		TedPopupItem item;
		int type;
		String action = arg0.getActionCommand();
		
		for(int i=0; i<allItems.size(); i++)
		{
			item = (TedPopupItem)allItems.get(i);
			type = item.getType();
			
			if(item.getName().equals(action))
			{
				if(type==TedPopupItem.IS_SEARCH_BASED || type == TedPopupItem.IS_SEARCH_AND_AUTO)
					this.openOptionDialog(item.getUrl(), item.getWebsite(), item.getType());
				else if(type==TedPopupItem.IS_CATEGORY_BASED)
				{
					// open website and add empty feed placeholder
					this.openUrl(item.getUrl());
					dialog.addFeed();
				}
				else if(type==TedPopupItem.IS_GENERAL_FEED)
					this.openOptionDialog(item.getUrl(), item.getWebsite(), item.getType());
				else if(type==TedPopupItem.IS_HELP)
					this.openUrl(item.getUrl());
				
				return;
			}
		}
		
		if (action.equals("empty"))
		{
			dialog.addFeed();
		}
		else if (action.equals("auto"))
		{
			// automatically generate feeds
			// TODO
		}
	}
	
	private void openUrl(String url)
	{
		try
		{
			BrowserLauncher.openURL(url);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void openOptionDialog(String url, String website, int type)
	{
		String question;
		
		// decide which question has to be shown
		if(type==TedPopupItem.IS_SEARCH_BASED || type == TedPopupItem.IS_SEARCH_AND_AUTO)
			question = Lang.getString("TedEpisodeDialog.DialogFindSearch");
		else
			question = Lang.getString("TedEpisodeDialog.DialogFindGeneral");
		
		// show question dialog
		 Object[] options = { Lang.getString("TedGeneral.Yes"), Lang.getString("TedGeneral.No"), Lang.getString("TedGeneral.Website") };
		 int selectedValue = JOptionPane.showOptionDialog(null, 
				 	question, Lang.getString("TedGeneral.Question"),
		             JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
		             null, options, options[0]);
		 
		 if(selectedValue == 0) //Yes
		 {
			 String name = dialog.getShowName();
			 if(!name.equals(""))
			 {
				 // do action based on type
				 if(type==TedPopupItem.IS_SEARCH_BASED || type == TedPopupItem.IS_SEARCH_AND_AUTO)
					 url = url.replace("#NAME#", name); // add name to rss query
				 else if(type==TedPopupItem.IS_GENERAL_FEED)
					 dialog.addKeywords(name); // use general feeds combined with keywords
					 
				 dialog.addFeed(url);
			 }
			 else
			 {
				 JOptionPane.showMessageDialog(null, Lang.getString("TedEpisodeDialog.DialogFindError"));
			 }
		 }
		 else if(selectedValue == 1) //No
		 { }
		 else if(selectedValue == 2) //Go to website
		 { 
			 this.openUrl(website);
		 }
	}
}
