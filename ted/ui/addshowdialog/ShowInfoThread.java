package ted.ui.addshowdialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;

import ted.Lang;
import ted.TedSerie;


/**
 * Thread that retrieves showinformation from TV.com website
 * @author roel
 *
 */
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
		// loading...
		showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.LabelLoadingInfo"));
		
		// when a show is selected
		if(currentSerie!=null)
		{	
			// and has a TV.com url
			if (currentSerie.getTVcom() != "" && currentSerie.getTVcom() != null)
			{
				URL url;
				try
				{
					// open the showinfo url and display
					url = new URL("http://ted.sourceforge.net/showinfo.php?tvcom="+currentSerie.getTVcom());
					showInfoPane.setPage(url);
				} 
				catch (MalformedURLException e)
				{
					// url malformed, display error
					showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.ErrorMalformedURL"));
				} 
				catch (IOException e)
				{
					// url not found, display error
					showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.ErrorIO"));
				}
				catch (Exception e)
				{
					// unkown error, display error and print stacktrace
					showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.ErrorUnknown"));
					e.printStackTrace();
				}
			}
			else
			{
				// no info url available
				showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.LabelNoInfo"));
			}
		}
	
	}

}
