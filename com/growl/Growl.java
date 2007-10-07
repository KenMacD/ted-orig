/**
 * Growl.java
 * 
 * Version:
 * $Id:$
 *
 * Revisions:
 * $Log:$
 *
 */

package com.growl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A class that encapsulates the "work" of talking to growl
 *
 * @author Karl Adam
 */
public class Growl {

	// defines
	/** The name of the growl registration notification for DNC. */
	public static final String GROWL_APP_REGISTRATION = "GrowlApplicationRegistrationNotification";

	//  Ticket Defines
	/** Ticket key for the application name. */
	public static final String GROWL_APP_NAME = "ApplicationName";
	/** Ticket key for the application icon. */
	public static final String GROWL_APP_ICON = "ApplicationIcon";
	/** Ticket key for the default notifactions. */
	public static final String GROWL_NOTIFICATIONS_DEFAULT = "DefaultNotifications";
	/** Ticket key for all notifactions. */
	public static final String GROWL_NOTIFICATIONS_ALL = "AllNotifications";

	//  Notification Defines
	/** The name of the growl notification for DNC. */
	public static final String GROWL_NOTIFICATION = "GrowlNotification";
	/** Notification key for the name. */
	public static final String GROWL_NOTIFICATION_NAME = "NotificationName";
	/** Notification key for the title. */
	public static final String GROWL_NOTIFICATION_TITLE = "NotificationTitle";
	/** Notification key for the description. */
	public static final String GROWL_NOTIFICATION_DESCRIPTION = "NotificationDescription";
	/** Notification key for the icon. */
	public static final String GROWL_NOTIFICATION_ICON = "NotificationIcon";
	/** Notification key for the application icon. */
	public static final String GROWL_NOTIFICATION_APP_ICON = "NotificationAppIcon";
	/** Notification key for the sticky flag. */
	public static final String GROWL_NOTIFICATION_STICKY = "NotificationSticky";
	/** Notification key for the identifier. */
	public static final String GROWL_NOTIFICATION_IDENTIFIER = "GrowlNotificationIdentifier";
	
	
	private static final String GROWL_APPLESCRIPT_ID = "GrowlHelperApp";

	// Actual instance data
	private boolean      registered;    // We should only register once
	private String       appName;       // "Application" Name
	
	private String[] allNotes; // All notifications
	private String[] defNotes; // Default Notifications

	//************  Constructors **************//

	public Growl()
	{	
	}
	
	
	
	/**
	 * Convenience method to contruct a growl instance, defers to Growl(String 
	 * inAppName, NSData inImageData, NSArray inAllNotes, NSArray inDefNotes, 
	 * boolean registerNow) with empty arrays for your notifications.
	 *
	 *
	 * @param inAppName - The Name of your "application"
	 * @param inImage - The NSImage Icon for your Application
	 *
	 */
	/*public Growl(String inAppName, NSImage inImage) {
	
		this(inAppName,
			  inImage.TIFFRepresentation(),
			  new NSArray(),
			  new NSArray(),
			  false);
	}*/

	/**
	 * Convenience method to contruct a growl instance, defers to Growl(String 
	 * inAppName, NSData inImageData, NSArray inAllNotes, NSArray inDefNotes, 
	 * boolean registerNow) with empty arrays for your notifications.
	 *
	 * @param inAppName - The Name of your "Application"
	 * @param inImageData - The NSData for your NSImage
	 */
	/*public Growl(String inAppName, NSData inImageData) {

		this(inAppName,
			 inImageData,
			 new NSArray(),
			 new NSArray(),
			 false);
	}*/

	/**
	 * Convenience method to contruct a growl instance, defers to Growl(String 
	 * inAppName, NSData inImageData, NSArray inAllNotes, NSArray inDefNotes, 
	 * boolean registerNow) with empty arrays for your notifications.
	 * 
	 * @param inAppName - The Name of your "Application"
	 * @param inImagePath - The path to your icon
	 *
	 */
	/*public Growl(String inAppName, String inImagePath) {
	
		this(inAppName, 
			 new NSImage(inImagePath, false).TIFFRepresentation(), 
			 new NSArray(), 
			 new NSArray(), 
			 false);
	}*/

	/**
	 * Convenience method to contruct a growl instance, defers to Growl(String 
	 * inAppName, NSData inImageData, NSArray inAllNotes, NSArray inDefNotes, 
	 * boolean registerNow) with the arrays passed here and empty Data for the icon.
	 *
	 * @param inAppName - The Name of your "Application"
	 * @param inAllNotes - A String Array with the name of all your notifications
	 * @param inDefNotes - A String Array with the na,es of the Notifications on 
	 *                     by default
	 */
	/*public Growl(String inAppName, String [] inAllNotes, String [] inDefNotes) {

		this(inAppName, 
			 new NSData(), 
			 new NSArray(inAllNotes), 
			 new NSArray(inDefNotes), 
			 false);
	}*/

	/**
	 * Convenience method to contruct a growl instance, defers to Growl(String 
	 * inAppName, NSData inImageData, NSArray inAllNotes, NSArray inDefNotes, 
	 * boolean registerNow) with empty arrays for your notifications.
	 *
	 * @param inAppName - The Name of your "Application"
	 * @param inImageData - The Data of your "Application"'s icon
	 * @param inAllNotes - The NSArray of Strings of all your Notifications
	 * @param inDefNotes - The NSArray of Strings of your default Notifications
	 * @param registerNow - Since we have all the necessary info we can go ahead 
	 *                      and register
	 */
	public Growl(String inAppName, /*NSData inImageData,*/ String [] inAllNotes, 
	   String[] inDefNotes, boolean registerNow) {
		appName = inAppName;
		//appImageData = inImageData;
		this.setAllowedNotifications(inAllNotes);
		try {
			this.setDefaultNotifications(inDefNotes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (registerNow) {
			this.registered = this.register();
		}
	}

	//************  Commonly Used Methods **************//

	/**
	 * Register all our notifications with Growl, this should only be called
	 * once.
	 * @return <code>true</code>.
	 */
	public boolean register() {
		if (!registered) {
		
			String notificationsMessage = "set the allNotificationsList to " + arrayToGrowlString (this.allNotes);
			String defNotificationsMessage = "set the enabledNotificationsList to " + arrayToGrowlString (this.defNotes);
			String registerMessage = "register as application \""+ this.appName + "\" all notifications allNotificationsList default notifications enabledNotificationsList icon of application \"ted.app\"";
			String [] script = {notificationsMessage, defNotificationsMessage, registerMessage};
			
			this.contactGrowlThroughAppleScript(script);
		}

		return true;
	}
	
	/**
	 * The fun part is actually sending those notifications we worked so hard for
	 * so here we let growl know about things we think the user would like, and growl
	 * decides if that is the case.
	 *
	 * @param inNotificationName - The name of one of the notifications we told growl
	 *                             about.
	 * @param inIconData - The NSData for the icon for this notification, can be null
	 * @param inTitle - The Title of our Notification as Growl will show it
	 * @param inDescription - The Description of our Notification as Growl will 
	 *                        display it
	 * @param inExtraInfo - Growl is flexible and allows Display Plugins to do as they 
	 *                      please with thier own special keys and values, you may use 
	 *                      them here. These may be ignored by either the user's 
	 *                      preferences or the current Display Plugin. This can be null
	 * @param inSticky - Whether the Growl notification should be sticky
	 * @param inIdentifier - Notification identifier for coalescing. This can be null.
	 *
	 * @throws Exception When a notification is not known
	 */
	public void notifyGrowlOf(String inNotificationName,
								String inTitle, 
								String inDescription,
								boolean inSticky,
								int inPriority) throws Exception {
		// check if notification name is known
		
		// construct applescript to notify growl
		String script = "notify with ";
		script += "name \"" + inNotificationName + "\" "; 		// notification name
		script += "title \"" + inTitle + "\" ";					// title
		script += "description \"" + inDescription + "\" ";		// body
		script += "application name \"" + this.appName + "\" ";	// application name
		script += "priority " + inPriority +" ";				// priority
		
		if(inSticky)
		{
			script += "with sticky ";							// is message sticky?
		}
		
		// execute script
		this.contactGrowlThroughAppleScript(script);		
	}
	
	/**
	 * Convenience method, defers to full notifyGrowlOf method, without sticky and priority
	 * parameters
	 * @param inNotificationName The name of the notification
	 * @param inTitle The title of the notification
	 * @param inDescription The notification description
	 * @throws Exception When notification name is not known
	 */
	public void notifyGrowlOf(String inNotificationName,
								String inTitle, 
								String inDescription) throws Exception {

		this.notifyGrowlOf(inNotificationName, inTitle, inDescription, false, 0);	
	}
	
	/**
	 * According to http://growl.info/documentation/applescript-support.php we can check whether growl is running
	 * by asking the system events
	 * 
	 * @return Whether growl is running on the current system
	 */
	public boolean isGrowlEnabled()
	{		
		String [] script = new String[3];
		script[0] = "tell application \"System Events\"";
		script[1] = "set isRunning to count of (every process whose name is \"" + GROWL_APPLESCRIPT_ID +"\") > 0";
		script[2] = "end tell";
		
		String result = this.executeAppleScript(script);
		
		// check if the result was true
		return result.contains("true");		
	}
		
	/**
	 * Construct an applescript that is send to the growl application
	 * @param script The applescript that has to be send to growl
	 */
	private void contactGrowlThroughAppleScript(String script) 
	{
		String [] fullScript = {"tell application \"" + GROWL_APPLESCRIPT_ID + "\"", script, "end tell"};
		this.executeAppleScript(fullScript);		
	}
	
	/**
	 * Construct an applescript that is send to the growl application
	 * @param script The applescript that has to be send to growl
	 */
	private void contactGrowlThroughAppleScript(String[] script) 
	{
		String [] fullScript = new String[script.length+2];
		fullScript[0] = "tell application \"" + GROWL_APPLESCRIPT_ID + "\"";
		
		// convert string array to one long string
		for (int i = 0; i < script.length; i++)
		{
			fullScript[i+1] = script[i];
		}
		
		fullScript[fullScript.length-1] = "end tell";
		
		this.executeAppleScript(fullScript);		
	}
	
	/**
	 * Execute a applescript using the "osascript" commandline interface to it
	 * @param script The script to be executed, every item in the array is a line of script
	 * @return The eventual result of the script
	 */
	private String executeAppleScript(String[] script)
	{
		String cmd = "osascript ";
		for (int i = 0; i < script.length; i++)
		{
			cmd += "-e \'" + script[i] + "\' ";
		}
		try {
			// open a runtime process to execute applescript
			Process Results = Runtime.getRuntime().exec(new String[] {"/bin/sh","-c",cmd});
			// read the returned value
			BufferedReader br=new  BufferedReader(new InputStreamReader(Results.getInputStream()));
			String s=br.readLine();
			br.close();
			br=null;
			Results=null;
			return s;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Converts an array of strings to an applescript array
	 * @param array Array with Java Strings
	 * @return String with Applescript array
	 */
	private String arrayToGrowlString(String[] array) {
		String result = "{";
		
		for (int i = 0; i < array.length; i++)	{
			// wrap with quotes
			result += "\""+array[i]+"\"";
			
			if (i != array.length-1)
			{
				result += " , ";
			}
		}
		
		result += "}";
		return result;
	}
	
	//************  Accessors **************//

	



	/**
	 * Accessor for The currently set "Application" Name
	 *
	 * @return String - Application Name
	 */
	public String applicationName() {
		return appName;
	}

	/**
	 * Accessor for the Array of allowed Notifications returned an NSArray
	 *
	 * @return the array of allowed notifications.
	 */
	public String[] getAllowedNotifications() {
		return allNotes;
	}

	/**
	 * Accessor for the Array of default Notifications returned as an NSArray
	 *
	 * @return the array of default notifications.
	 */
	public String[] getDefaultNotifications() {
		return defNotes;
	}

	//************  Mutators **************//

	/**
	 * Sets The name of the Application talking to growl
	 *
	 * @param inAppName - The Application Name
	 */
	public void setApplicationName(String inAppName) {
		appName = inAppName;
	}

	/**
	 * Set the list of allowed Notifications
	 *
	 * @param inAllNotes - The array of allowed Notifications
	 */
	/*public void setAllowedNotifications(NSArray inAllNotes) {
		allNotes = inAllNotes;
	}*/

	/**
	 * Set the list of allowed Notifications
	 *
	 * @param inAllNotes - The array of allowed Notifications
	 *
	 */
	public void setAllowedNotifications(String[] inAllNotes) {
		allNotes = inAllNotes;
	}

	/**
	 * Set the list of Default Notfiications
	 *
	 * @param inDefNotes - The default Notifications
	 *
	 * @throws Exception when an element of the array is not in the 
	 *                   allowedNotifications
	 */
	/*public void setDefaultNotifications(NSArray inDefNotes) throws Exception {
		int stop = inDefNotes.count();
		int i = 0;

		for(i = 0; i < stop; i++) {
			if (!allNotes.containsObject(inDefNotes.objectAtIndex(i))) {
				throw new Exception("Array Element not in Allowed Notifications");
			}
		} 

		defNotes = inDefNotes;
	}*/

	/**
	 * Set the list of Default Notfications
	 *
	 * @param inDefNotes - The default Notifications
	 *
	 * @throws Exception when an element of the array is not in the 
	 *                   allowedNotifications
	 *
	 */
	public void setDefaultNotifications(String [] inDefNotes) throws Exception {
		int stop = inDefNotes.length;
		int i = 0;

		/*for(i = 0; i < stop; i++) {
			if (! allNotes.containsObject(inDefNotes[i])) {
				throw new Exception("Array Element not in Allowed Notifications");
			}
		} */

		defNotes = inDefNotes;
	}
	
	
}
