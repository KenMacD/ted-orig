package ted;

import java.io.File;

public class TedSystemInfo
{
	private static boolean saveLocal;
	private static final String osname = System.getProperty("os.name").toLowerCase(); //$NON-NLS-1$
	
	public static final String MINIMUM_JAVA = "1.5";
	
	/**
	 * Check if the operating system ted is running on supports trayicons
	 * @return If the OS supports trayicons
	 */
	public static boolean osSupportsTray() 
	{
		// return if the tray program supports the current os ted is running on	
		if (osIsWindows() || osIsMac() || osIsLinux() || osIsSolaris()) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if the operating system ted is running on supports balloons
	 * @return If the OS supports balloons
	 */
	public static boolean osSupportsBalloon() 
	{
		// return if the tray program supports the current os ted is running on
		if (osIsWindows() || osIsLinux() || osIsSolaris()) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		{
			return true;
		}
		
		return false;
	}
	
	
	
	/**
	 * create user directory from user.home property
	 * @return The user directory
	 */
	public static String getUserDirectory()
	{
		// if user wants to save local, return empty string
		if (saveLocal)
		{
			return "";
		}
		String userdir;
		String seperator = System.getProperty("file.separator");
		String teddir = "Torrent Episode Downloader";
		String windows = "Application Data";
		String mac = "Library" + seperator + "Application Support";
		
		userdir = System.getProperty("user.home");
		
		// if windows:
		if (TedSystemInfo.osIsWindows())
		{
			userdir = userdir + seperator + windows + seperator + teddir + seperator;
		}
		else if (TedSystemInfo.osIsMac())
		{
			userdir = userdir + seperator + mac + seperator + teddir + seperator;
		}
		else
		{
			// linux??
			return "";
		}	
		
		// if the directory doesn't already exist, create it
	    File dir = new File( userdir );
	    if (!dir.exists()) 
	    {
	      dir.mkdirs();
	    }
		
		return userdir;
	}

	public static void setSaveInLocalDir(boolean saveInLocalDir) 
	{
		// set whether user wants to save in local dir
		saveLocal = saveInLocalDir;	
	}

	/**
	 * @return If ted currently runs on linux
	 */
	public static boolean osIsLinux()
	{
		String osname = System.getProperty("os.name").toLowerCase(); //$NON-NLS-1$
		if (osname.startsWith("linux")) //$NON-NLS-1$
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @return If ted currently runs on solaris
	 */
	public static boolean osIsSolaris()
	{
		String osname = System.getProperty("os.name").toLowerCase(); //$NON-NLS-1$
		if (osname.startsWith("solaris")) //$NON-NLS-1$
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @return If ted currently runs on MacOs
	 */
	public static boolean osIsMac() 
	{
		if (osname.startsWith("mac")) //$NON-NLS-1$
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @return If ted currently runs on windows
	 */
	public static boolean osIsWindows() 
	{
		if (osname.startsWith("windows")) //$NON-NLS-1$
		{
			return true;
		}
		return false;
	}
	
	public static String getJavaVersion()
	{
		return java.lang.System.getProperty("java.version");
	}
	
	public static String getJavaVendor()
	{
		return java.lang.System.getProperty("java.vendor");
	}
	
	/**
	 * @return If the current version of java is supported by ted
	 */
	public static boolean isSupportedJavaVersion()
	{
		String version = TedSystemInfo.getJavaVersion();
		
		if (version.compareTo(MINIMUM_JAVA) >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @return If the current Java vendor is supported by ted
	 */
	public static boolean isSupportedJavaVendor()
	{
		String vendor = TedSystemInfo.getJavaVendor();
		vendor.toLowerCase();
		System.out.println(vendor);
		
		if (vendor.contains("sun"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @return If the current Java is supported by ted
	 */
	public static boolean isSupportedJava()
	{
		if(isSupportedJavaVersion())
			if(osIsWindows() || osIsMac())
				return true;
			else if(isSupportedJavaVendor())
				return true;
		
		return false;			
	}

}
