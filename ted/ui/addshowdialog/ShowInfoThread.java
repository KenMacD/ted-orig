package ted.ui.addshowdialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;

import ted.TedSerie;


public class ShowInfoThread extends Thread
{
	private JEditorPane showInfoPane;
	private TedSerie currentSerie;
	
	public ShowInfoThread(JEditorPane jep, TedSerie ts)
	{
		showInfoPane = jep;
		currentSerie = ts;
	}
	
	public void run()
	{
		showInfoPane.setText("Loading show information...");
		if(currentSerie!=null)
		{	
			if (currentSerie.getTVcom() != "" && currentSerie.getTVcom() != null)
			{
				URL url;
				try
				{
					url = new URL("http://www.rulecam.net/ted/showinfo.php?tvcom="+currentSerie.getTVcom());
					showInfoPane.setPage(url);
					//this.addShowDialog.repaint();
				} catch (MalformedURLException e)
				{
					// TODO Auto-generated catch block
					showInfoPane.setText("Error retrieving show information. Malformed URL.");
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					showInfoPane.setText("Error retrieving show information. Cannot read information page.");
					e.printStackTrace();
				}
			}
			else
			{
				showInfoPane.setText("No show information available");
			}
		}
	
	}

}
