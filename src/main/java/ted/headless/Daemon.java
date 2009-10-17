package ted.headless;

import ted.TedConfig;
import ted.TedIO;
import ted.TedParser;
import ted.TedSerie;
import ted.TedSystemInfo;
import ted.infrastructure.TedMainCore;

public class Daemon
{
    private TedMainCore core = new TedMainCore();

    public static void main(String[] args)
    {
		DaemonLog.debug("Starting Daemon");
		TedSystemInfo.getUserDirectory();
		TedSystemInfo.setHeadless(true);
		Daemon d = new Daemon();
		d.runDaemon();

		DaemonLog.debug("All Done. Goodbye.");
		System.exit(0);
    }

    @SuppressWarnings("unchecked")
    public void loadShows()
    {
    	this.core.setSeries(TedIO.getInstance().GetShows());
    }

    public void runDaemon()
    {
		while (true)
		{	    
			try
			{
				TedIO.getInstance().GetConfig();
			    loadShows();
			    parseShows();
			    TedIO.getInstance().SaveShows(this.core.getSeries());
			    DaemonLog.debug("Its been a hard day's night... gotta get some sleep (for "+TedConfig.getInstance().getRefreshTime()  +" seconds)");
			    Thread.sleep(TedConfig.getInstance().getRefreshTime()*1000);
			    DaemonLog.debug("Yaawn... I woke up and will look for shows...");
			}
			catch (Exception e)
			{
			    DaemonLog.error("Got an error... continuing after printing stacktrace...");
			    e.printStackTrace();
			    System.exit(1);
			}
		}
    }

    private void parseShows()
    {
		for (TedSerie serie : this.core.getSeries())
		{
		    TedParser parser = new TedParser(serie, core, true);
		    parser.run();
		}
    }

}
