package ted.infrastructure;

import java.util.Collection;
import java.util.Vector;

import ted.TedDailySerie;
import ted.TedIO;
import ted.TedLog;
import ted.TedSerie;
import ted.headless.DaemonLog;

public class TedMainCore implements ITedMain
{
    protected Vector<TedSerie> series = new Vector<TedSerie>();

    public void setSeries(Collection c)
    {
    	this.series.clear();
    	this.series.addAll(c);
    }

    public Vector<TedSerie> getSeries()
    {
    	return this.series;
    }

    /**
     * Saves the shows that are in displayed in the ted window
     */
    public void saveShows()
    {
    	TedIO.getInstance().SaveShows(this.series);
    }

    public void saveConfig(boolean resetTime)
    {
    	TedIO.getInstance().SaveConfig();
    }

    public void repaint()
    {
    	// update status file
    }

    public void displayError(String string, String message, String string2)
    {
    	DaemonLog.error(string + " " + message + " " + string2);
    }

    public void displayHurray(String string, String message, String string2)
    {
    	DaemonLog.debug("Hurray !!!: " + string + " " + message + " " + string2);
    }

    public boolean getStopParsing()
    {
		// TODO Auto-generated method stub
		return false;
    }
}
