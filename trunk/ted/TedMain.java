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
		    //System.setProperty("apple.awt.brushMetalLook", "true");
		} 
		catch ( SecurityException e ) 
		{
			//this.addLogMessage("Problem starting up with MacOsX code\n" + e.getMessage());
		    /* probably running via webstart, do nothing */
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
		// overwrite osHasTray if user inputs something
		/*if (args.length > 0)
		{
			String tray = System.getProperty("tray", "1");
			if (tray.equals("0"))
			{
				userWantsTray = false;
			}
			String save = System.getProperty("localsave");

			if (save.equals("1"))
			{
				saveLocal = true;
			}
		}*/
		
		// make a new ted
		TedMainDialog inst = new TedMainDialog(userWantsTray, saveLocal);

	}

}
