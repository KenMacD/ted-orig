package ted.headless;

import ted.TedLog;

public class DaemonLog {
	
	public static void error(String s)
	{
		TedLog.error("##[*DAEMON ERROR*] -- "+ s);
	}
	
	public static void debug(String s)
	{
		TedLog.error("##[*DAEMON DEBUG*] -- "+ s);
	}
}
