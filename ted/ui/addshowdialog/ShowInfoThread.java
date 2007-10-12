package ted.ui.addshowdialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;

import org.w3c.dom.Element;

import ted.Lang;
import ted.TedIO;
import ted.TedLog;
import ted.TedSerie;
import ted.TedXMLParser;


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
					TedXMLParser parser = new TedXMLParser();
					Element nl = parser.readXMLFromFile(TedIO.XML_SHOWS_FILE);
					String location = parser.getShowInfoURL(nl);
					
					if(!location.equals(""))
					{
						// open the showinfo url and display
						url = new URL(location+currentSerie.getTVcom());
						showInfoPane.setPage(url);
					}
					else
					{
						TedLog.error("shows.xml file is corrupt");
					}
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
