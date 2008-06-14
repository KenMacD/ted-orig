package ted.epguides;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.net.URL;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;

import ted.TedConfig;
import ted.datastructures.DailyDate;
import ted.datastructures.SeasonEpisode;
import ted.datastructures.StandardStructure;

/* 
 * TedEpguidesParser parses an epguides.com webpage and retrieves 
 * informations that match a regular expression (regex) pattern.
 * 
 * @author bororo
 * 
 */


public class EpguidesParser 
{    
    // The general pattern that epguides follows for their show lists
    // Format:   1.   1- 1        100     22 Sep 04   <a target="_blank" href="http://www.tv.com/lost/pilot-1/episode/334467/summary.html">Pilot (1)</a>
    private String regex       = "(\\d+)(\\-|\\-\\s)(\\d+)(\\s+)(\\w*+)(\\s+)(\\d+)(\\s+)(\\w+)(\\s+)(\\d+)";
    private String regexNoDate = "(\\d+)(\\-|\\-\\s)(\\d+)(\\s+)(\\w*+)(\\s+)";
    private String regexName   = "(>)(.+)(</a>)(\\s*)$";
    
    
	private Vector<StandardStructure> parseSeasonEpisodes (String seriesName, Date from, Date to, boolean isDaily)
    {
        Date parsedAirDate=null;
        String DATE_FORMAT = "d/MMM/yy";        
       
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        
        Pattern pattern       = Pattern.compile(regex);
        Pattern patternNoDate = Pattern.compile(regexNoDate); 
        Pattern patternName   = Pattern.compile(regexName);
        
        Vector<StandardStructure> episodes = new Vector<StandardStructure>();
        
        try 
        {
            URL epguides = new URL("http://www.epguides.com/" + seriesName + "/");
            BufferedReader in = new BufferedReader( new InputStreamReader(epguides.openStream())); 
                
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {               
                Matcher matcher       = pattern      .matcher(inputLine);      
                Matcher matcherNoDate = patternNoDate.matcher(inputLine);
                Matcher matcherName   = patternName  .matcher(inputLine);
                
                boolean date   = matcher.find();
                boolean noDate = matcherNoDate.find();
                
                int season   = 0;
                int episode  = 0;
                String title = "";
                Date airdate = null;
                
                if (date) 
                { 
                    try
                    {
                        // Convert between String and Date
                        parsedAirDate = sdf.parse(matcher.group(7)+"/" + matcher.group(9) + "/" + matcher.group(11));
                         
                         if (parsedAirDate.after(from) && parsedAirDate.before(to))
                         {
                        	 season   = Integer.parseInt(matcher.group(1));
                             episode  = Integer.parseInt(matcher.group(3));
                             airdate = parsedAirDate;
                             
                            // If you're not living in the USA
                     		if (TedConfig.getTimeZoneOffset() >= 0)
                     		{
                     			if (airdate.getYear() < 1000)
                     			{
                     				int henk = airdate.getYear();
                     				henk++;
                     			}
                     			// Add one day to the schedule
                     			long time = airdate.getTime();
                     			time += 86400000;
                     			airdate.setTime(time);
                     		}
                                    
                             if (matcherName.find())
                             {
                            	 title = matcherName.group(2);
                             }
                         }
                    }
                    catch (java.text.ParseException pe) 
                    {
                            // ParseAirDate or Season / episode failed
                            // TODO: Add Handling Code
                    }                       
                } // matcher.find() ends 
                else if (noDate)
                {
                	// We've found an episode without a date.                	
                    season  = Integer.parseInt(matcherNoDate.group(1));
                    episode = Integer.parseInt(matcherNoDate.group(3));
                                                
                    if (matcherName.find())
                    {
                   	 title = matcherName.group(2);
                    }
                }
                

                if (date || noDate)
                {
                	if (isDaily)
                	{
                		if (airdate != null)
                		{           			
                			DailyDate dd = new DailyDate(airdate, title);
                			episodes.add(dd);
                		}
                	}
                	else
                	{
                		SeasonEpisode se = new SeasonEpisode(season, episode, airdate, title);
                		episodes.add(se);
                	}
                }
            } // while ends
            in.close();
           
        } catch (MalformedURLException e) {
            // new URL() failed     
            // TODO: Add Handling Code

        } catch (IOException e) { 
            // openConnection() failed 
            // TODO: Add Handling Code
        }
        
        // sort the seasons and episodes in ascending order
        Collections.sort(episodes);
        
        // Finally detect double episodes
        // Walk backwards over all episodes, if the previous episode (the one with the
        // higher episode number) has the same air date than we've found a double episode.
        if (episodes.size() > 0)
        {
        	Date airDate         = null;
	        Date previousAirDate = episodes.get(0).getAirDate();
	        for (int i = 1; i < episodes.size(); i++)
	        {
	        	airDate = episodes.get(i).getAirDate();
	        	
	        	if (   airDate != null
	        		&& previousAirDate != null
	        		&& airDate.getTime() == previousAirDate.getTime())
	        	{
	        		episodes.get(i).setDouble(true);
	        	}
	        	
	        	previousAirDate = airDate;
	        }
    	}
        
        return episodes;
        
    } // parse() ends
    
    
    public Vector<StandardStructure> getPastSeasonEpisodes(String showName, boolean isDaily)
    {
    	Date systemDate = new Date();   // Get time and date from system
        Date past = new Date();   // Get time and date from system
        past.setTime(0);
        return this.parseSeasonEpisodes(showName, past, systemDate, isDaily);
    }
    
    public Vector<StandardStructure> getFutureSeasonEpisodes(String showName, boolean isDaily)
    {
    	// system date
       	Date systemDate = new Date();
       
        // one year from now
        Calendar yearFromNow = Calendar.getInstance();
        yearFromNow.add(Calendar.YEAR, 1);
    	return this.parseSeasonEpisodes(showName, systemDate, yearFromNow.getTime(), isDaily);
    }
    
    public Vector<StandardStructure> getScheduledSeasonEpisodes(String showName, boolean isDaily)
    {
    	Date past = new Date();   // Get time and date from system
        past.setTime(0);
       
        // one year from now
        Calendar yearFromNow = Calendar.getInstance();
        yearFromNow.add(Calendar.YEAR, 1);
    	return this.parseSeasonEpisodes(showName, past, yearFromNow.getTime(), isDaily);
    }
    
} // TedEpguidesParser ends
