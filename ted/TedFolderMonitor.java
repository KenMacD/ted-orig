package ted;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TedFolderMonitor 
{
	private static TedFolderMonitor instance = null;
	private String monitored_folder;
	private String destination_folder;
	private ConcurrentLinkedQueue monitored_files;
	private String monitored_file;
	private TedFolderMonitorCounter counter;
	
	private TedFolderMonitor()
	{
		monitored_folder = "C:\\Mijn Gedeelde Map\\Download\\Temp\\";
		monitored_files = new ConcurrentLinkedQueue();
		
		counter = new TedFolderMonitorCounter(this, 1);
		startMonitoring();
	}
	
	/**
     * Singleton getInstance method
     */
    public static TedFolderMonitor getInstance() {
        if(instance == null)
            instance = new TedFolderMonitor();
        return instance;
    }
    
    private void startMonitoring()
    {
    	counter.start();
    }
    
    public void updateStatus()
    {
    	// iterator?
    	if(monitored_files.size()!=0)
    	{
	    	String name = (String)monitored_files.remove();
	    	
	    	File f = new File(monitored_folder + name);
	    	
	    	if(isFileInUse(f))
			{
	    		// TODO: should wait a few seconds because download doesn't start immediatly
	    		monitored_files.add(name);
				TedLog.debug(f.toString() + ": being downloaded" );
			}
			else
			{				
				TedLog.debug(f.toString() + ": is downloaded!" );
			}	    	
    	}
    	
    }
    
    public boolean isFileInUse(File sourceFile) 
    {
    	final String tmpFilePath = sourceFile.getParent() + File.separator + "TEMP_" + sourceFile.getName();
    	File tempFile = new File(tmpFilePath);
   
    	if (sourceFile.renameTo(tempFile))
    	{
    		// TODO: rename file to preferred name as prediscribed by user
    		tempFile.renameTo(sourceFile);
    		return false;
    	}
    	else
    	{
    		tempFile.delete();
	        return true;
	    }
    }

    
    public void addFileToMonitor(String name)
    {
    	TedLog.debug("added " + name + " to monitor list");
    	monitored_files.add(name);
    }
	
	public String getDestination_folder() 
	{
		return destination_folder;
	}

	public void setDestination_folder(String destination_folder) 
	{
		this.destination_folder = destination_folder;
	}

	public String getMonitored_folder() 
	{
		return monitored_folder;
	}

	public void setMonitored_folder(String monitored_folder) 
	{
		this.monitored_folder = monitored_folder;
	}

}
