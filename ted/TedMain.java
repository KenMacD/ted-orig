package ted;

public class TedMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// set menu to use on mac
		try
		{
			System.setProperty( "com.apple.mrj.application.apple.menu.about.name", "ted" );
			System.setProperty( "com.apple.macos.useScreenMenuBar", "true" );
		    System.setProperty( "apple.laf.useScreenMenuBar", "true" ); // for older versions of Java		
		} 
		catch ( SecurityException e ) 
		{
		    /* probably not running on mac, do nothing */
		} 
		boolean userWantsTray = true;
		boolean saveLocal = false;
		
		for(int i=0; i<args.length; i++)
		{
			if(args[i].equals("noTray"))
				userWantsTray=false;
			else if(args[i].equals("localSave"))
				saveLocal=true;			
		}
		
		// make a new ted
		TedMainDialog inst = new TedMainDialog(userWantsTray, saveLocal);

	}

}
