package ted.ui.addshowdialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;

import ted.Lang;
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
		showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.LabelLoadingInfo"));
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
					showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.ErrorMalformedURL"));
					e.printStackTrace();
				} 
				catch (IOException e)
				{
					showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.ErrorIO"));
					e.printStackTrace();
				}
				catch (Exception e)
				{
					showInfoPane.setText(Lang.getString("TedAddShowDialog.ShowInfo.ErrorUnknown"));
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
