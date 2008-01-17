package ted;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ted.datastructures.SimpleTedSerie;

public class TedXMLParser 
{
	/**
	 * @param args
	 */
	public static void main(InputStream args)
	{
	}

	/**
	 * Use this to read the XML file from a file for further use
	 * @param args The location of the XML file (Location in filesystem)
	 * @return Element with contents of the XML file or null if file not exists
	 */
	public Element readXMLFromFile(String args)
	{	
//		 get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// using factory get an instance of document builder
		DocumentBuilder db;
		
		try 
		{
			File file = new File(args);
			db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			Document dom = db.parse(file);
			
			return dom.getDocumentElement();
		} 
		catch (SAXException e1) 
		{
			TedLog.error(e1, "XML parsing error, shows.xml couldn't be found");
		} 
		catch (IOException e1) 
		{
			TedLog.error(e1, "XML parsing error, shows.xml couldn't be read");
		} 
		catch (ParserConfigurationException e1) 
		{
			TedLog.error(e1, "XML parser error");
		}
		
		// if file not exists return null
		return null;
	}
	
	/**
	 * Use this to read the XML file from a url for further use
	 * @param args The location of the XML file (URL!!)
	 * @return Element with contents of the XML file or null if file not exists
	 */
	public Element readXMLFromURL(String url)
	{
//		 get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// using factory get an instance of document builder
		DocumentBuilder db;
		
		try 
		{
			db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			Document dom = db.parse(url);
			
			return dom.getDocumentElement();
		} 
		catch (SAXException e1) 
		{
			TedLog.error(e1, "XML parsing error, shows.xml couldn't be found");
		} 
		catch (IOException e1) 
		{
			TedLog.error(e1, "XML parsing error, shows.xml couldn't be read");
		} 
		catch (ParserConfigurationException e1) 
		{
			TedLog.error(e1, "XML parser error");
		}
		
		// if file not exists return null
		return null;
	}
	
	/**
	 * Find the serie in the nodelist archieved with readXMLFile
	 * @param nodelist Element got by readXMLFile
	 * @param name The name of the serie
	 * @return TedSerie or null if serie not exists in XML file
	 */
	public TedSerie getSerie(Element nodelist, String name)
	{
		// serie to be returned
		TedSerie serie;// = new TedSerie();
		
		// get the shows from the element 
		NodeList shows = nodelist.getElementsByTagName("show");
		
		// if there are shows
		if(shows!=null && shows.getLength()>0)
		{
			for(int i=0; i<shows.getLength(); i++)
			{
				Element show = (Element)shows.item(i);
				String elName = getTextValue(show, "name");
				
				// if we've found the show
				if(name.equals(elName))
				{
					int temp = -1;
					String tempS = "";
					
					// first check to see if it is a daily show
					// before creating the serie object
					tempS = getTextValue(show, "daily");
					if(tempS.equals("True"))
						serie = new TedDailySerie();
					else
						serie = new TedSerie();
					
					// set the properties of the serie
					serie.setName(elName);
					
					temp = getIntValue(show, "minimumsize");
					if(temp!=-1)
						serie.setMinSize(temp);
					
					temp = getIntValue(show, "maximumsize");
					if(temp!=-1)
						serie.setMaxSize(temp);
					
					tempS = getTextValue(show, "keywords");
					if(!tempS.equals(""))
						serie.setKeywords(tempS);
					
					tempS = getTextValue(show, "tv_com");
					if(!tempS.equals(""))
						serie.setTVcom(tempS);
					
					temp = getIntValue(show, "seeders");
					if(temp!=-1)
						serie.setMinNumOfSeeders(temp);
					
					// set the feeds
					Vector tempF = getVectorValue(show, "feeds", "feed");
					Vector feeds = new Vector();
					for(int j=0; j<tempF.size(); j++)
					{
						TedSerieFeed f = new TedSerieFeed((String)tempF.elementAt(j), 0);
						feeds.addElement(f);
					}
					serie.setFeeds(feeds);
					
					// set the schedules
					
					// first check if the until date is in the future
					// otherwise using the break schedule wouldn't be very useful
					Calendar c = new GregorianCalendar();
					NodeList breakS = show.getElementsByTagName("break");
					Element bS = (Element)breakS.item(0);
										
					String date = getTextValue(bS, "until");
					if(!date.equals(""))
					{
						c = StringToCalendar(date);
						
						Calendar today = new GregorianCalendar();
						
						// use the break schedule
						if(c.getTimeInMillis()>today.getTimeInMillis())
						{
							serie.setBreakUntil(c.getTimeInMillis());
							serie.setUseBreakSchedule(true);
						}
						else
						{
							serie.setUseBreakSchedule(false);
						}
					}
					
					// check to see if the break episode has to be used
					int bE = getIntValue(bS, "episode");
					if(bE!=-1)
					{
						if(bE>0)
						{
							serie.setUseBreakScheduleEpisode(true);
							serie.setBreakEpisode(bE);
						}
						else
						{
							serie.setUseBreakScheduleEpisode(false);
						}
					}
					
					// check to see if the from break option has to be used
					date = getTextValue(bS, "from");
					if(!date.equals(""))
					{
						c = StringToCalendar(date);				
						if(c.getTimeInMillis()>0)
						{
							serie.setUseBreakScheduleFrom(true);
							serie.setBreakFrom(c.getTimeInMillis());
						}
						else
						{
							serie.setUseBreakScheduleFrom(false);
						}
					}
					
					// set the episode schedule
					String days = getTextValue(show, "releaseday");
					if(!days.equals(""))
					{
						days.toLowerCase();
						
						String[] wd = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
						boolean[] output = new boolean[7];
						boolean useSchedule = false;
						for(int j=0; j<7; j++)
						{
							if(days.contains(wd[j]))
							{
								output[j] = true;
								useSchedule = true;
							}
							else
							{
								output[j] = false;
							}
						}
						serie.setEpisodeSchedule(useSchedule, output);
					}
					
					// check to see if the from break option has to be used
					String searchName = getTextValue(show, "searchname");
					serie.setSearchName(searchName);
					
					return serie;
				}
			}
		}
		
		return null;
	}
	
	private Calendar StringToCalendar(String date)
	{
		Calendar c = new GregorianCalendar();
		
		if(date.equals(""))
			date = "00-00-0000";
		
		try
		{
			int k = Integer.parseInt(date.substring(0,2));
			c.set(Calendar.DAY_OF_MONTH, k);
			
			k = Integer.parseInt(date.substring(3,5));
			c.set(Calendar.MONTH, k-1);
			
			k = Integer.parseInt(date.substring(6,10));
			c.set(Calendar.YEAR, k);
		}
		catch(Exception e)
		{
			TedLog.error("Malformed date in xml file");
		}
		
		return c;
	}
	
	/**
	 * Get the names of the shows from Element for Episode dialog
	 * @param el Element obtainend from readXMLFile
	 * @param shows Vector to put shows in
	 */
	public Vector getNames(Element el)
	{
		Vector shows = new Vector();
		NodeList nl = el.getElementsByTagName("show");
		
		for(int i=0; i<nl.getLength(); i++)
		{
			Element show = (Element)nl.item(i);
			SimpleTedSerie serie = new SimpleTedSerie();
			serie.setName(getTextValue(show, "name"));
			shows.addElement(serie);
		}
		
		return shows;
	}
	
	/**
	 * Get string object value from the nodelist
	 * @param el The nodelist
	 * @param s Name of the object
	 * @return The string value
	 */
	private String getTextValue(Element el, String s)
	{
		try
		{
			String rString = "";
			NodeList nl = el.getElementsByTagName(s);
			if(nl!=null && nl.getLength()>0)
			{
				Element el1 = (Element)nl.item(0);
				if(el1.getFirstChild()!=null)
					rString = el1.getFirstChild().getNodeValue();
			}
			return rString;
		}
		catch(Exception e)
		{
			return "";
		}
	}
	
	/**
	 * Same as getTextValue() only returns integer
	 * @param el The nodelist
	 * @param s Name of the object
	 * @return The integer value	 
	 * */
	private int getIntValue(Element el, String s)
	{
		try
		{
			return Integer.parseInt(getTextValue(el, s));
		}
		catch(NumberFormatException e)
		{
			return -1;
		}
	}
	
	/**
	 * Get a vector with the values for this object
	 * @param el The nodelist
	 * @param s Name of the object
	 * @return The vector
	**/
	private Vector getVectorValue(Element el, String parent, String child)
	{
		Vector v = new Vector();
		
		NodeList nl = el.getElementsByTagName(parent);
		
		if(nl!=null && nl.getLength()>0)
		{
			Element el1 = (Element)nl.item(0);
			NodeList nl1 = el1.getElementsByTagName(child);
			
			for(int i=0; i<nl1.getLength(); i++)
			{
				Element el2 = (Element)nl1.item(i);
				v.addElement(el2.getFirstChild().getNodeValue());
			}
		}
		
		return v;
	}
	
	/**
	 * Return the version of the XML file
	 * @param el NodeList obtained by readXMLFile
	 * @return version of XML file
	 */
	public int getVersion(Element el)
	{
		int version = -1;
		String temp;
		
		NodeList nl = el.getElementsByTagName("version");
		
		if(nl!=null && nl.getLength()>0)
		{
			temp = ((Element)nl.item(0)).getFirstChild().getNodeValue();
			version = Integer.parseInt(temp);
		}
		
		return version;
	}
	
	public Vector<TedPopupItem> getPopupItems(Element nodelist)
	{
		Vector<TedPopupItem> v = new Vector<TedPopupItem>();
		TedPopupItem pi;
		NodeList nl = nodelist.getElementsByTagName("rsslocations");
		
		if(nl!=null && nl.getLength()>0)
		{
			Element el1 = (Element)nl.item(0);
			NodeList nl1 = el1.getElementsByTagName("location");
			
			for(int i=0; i<nl1.getLength(); i++)
			{
				Element e = (Element)nl1.item(i);
				String elName = getTextValue(e, "name");
				String location = getTextValue(e, "feed");
				String website = getTextValue(e, "website");
				int type = getIntValue(e, "type");
				
				pi = new TedPopupItem(elName, location, website, type);
				v.add(pi);
			}
		}
		
		return v;
	}
	
	public Vector<TedPopupItem> getAutoFeedLocations(Element nodelist)
	{
		Vector<TedPopupItem> v = new Vector<TedPopupItem>();
		TedPopupItem pi;
		NodeList nl = nodelist.getElementsByTagName("rsslocations");
		
		if(nl!=null && nl.getLength()>0)
		{
			Element el1 = (Element)nl.item(0);
			NodeList nl1 = el1.getElementsByTagName("location");
			
			for(int i=0; i<nl1.getLength(); i++)
			{
				Element e = (Element)nl1.item(i);
				int type = getIntValue(e, "type");
				if (type == TedPopupItem.IS_SEARCH_AND_AUTO)
				{
					String elName = getTextValue(e, "name");
					String location = getTextValue(e, "feed");
					String website = getTextValue(e, "website");
					
					
					pi = new TedPopupItem(elName, location, website, type);
					v.add(pi);
				}
			}
		}
		
		return v;
	}
	
	public int getPopupIndex(Element nodelist, String name)
	{
		NodeList nl = nodelist.getElementsByTagName("rsslocations");
		
		if(nl!=null && nl.getLength()>0)
		{
			Element el1 = (Element)nl.item(0);
			NodeList nl1 = el1.getElementsByTagName("location");
			
			for(int i=0; i<nl1.getLength(); i++)
			{
				Element e = (Element)nl1.item(i);
				String elName = getTextValue(e, "name");
				
				if(elName.equals(name))
					return i;
			}
		}
		
		return nl.getLength();
	}
	
	/**
	 * Returns the Amazons URL locations in a Vector as defined
	 * in the shows.xml file
	 * @param nodelist
	 * @return
	 */
	public Vector getAmazonURLs(Element nodelist)
	{
		Vector v = new Vector();
		NodeList nl = nodelist.getElementsByTagName("weblocations");
		
		if(nl!=null && nl.getLength()>0)
		{
			Element el1 = (Element)nl.item(0);
			NodeList nl1 = el1.getElementsByTagName("Amazon");
			
			for(int i=0; i<nl1.getLength(); i++)
			{
				Element e = (Element)nl1.item(i);
				v.add(getTextValue(e, "firsthalf"));
				v.add(getTextValue(e, "secondhalf"));
				v.add(getTextValue(e, "complete"));
			}
		}
		
		return v;
	}
	
	public String getShowInfoURL(Element nodelist)
	{
		NodeList nl = nodelist.getElementsByTagName("weblocations");
		
		if(nl!=null && nl.getLength()>0)
		{
			Element el1 = (Element)nl.item(0);
			NodeList nl1 = el1.getElementsByTagName("showinfo");
			
			Element e = (Element)nl1.item(0);
			return getTextValue(e, "location");
		}
		
		return "";
	}
}
