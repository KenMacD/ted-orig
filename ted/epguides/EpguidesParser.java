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

import ted.datastructures.SeasonEpisode;

/* 
 * TedEpguidesParser parses an epguides.com webpage and retrieves 
 * informations that match a regular expression (regex) pattern.
 * 
 * @author bororo
 * 
 */


public class EpguidesParser {
    // String that holds the series name pased in the parse() methrod
    private String seriesName;    
    // A flag that shows if next episode is found
    private boolean foundNext=false;
    // A flag that shows if a double episode (no matter when it's aired) is found
    private boolean foundDouble=false;
    // A flag that shows if the current episode is double
    private boolean currentIsDouble=false;
    // A flag that shows if the next episode is double
    private boolean nextIsDouble=false;
    // Strings to hold current episode's details 
    private String currentEpisode=null;
    private String currentSeason=null;
    private String currentAirDate=null;
    // Strings to hold next episode's details 
    private String nextEpisode=null;
    private String nextSeason=null;
    private String nextAirDate=null;
    // Strings to hold double episode's details 
    private String doubleEpisode=null;
    private String doubleSeason=null;
    private String doubleAirDate=null;
    
    // The general pattern that epguides follows for their show lists
    private String regex = "(\\d+)(\\-|\\-\\s)(\\d+)(\\s+)(\\w*+)(\\s+)(\\d+)(\\s+)(\\w+)(\\s+)(\\d+)";
    
    
    private Vector<SeasonEpisode> parseSeasonEpisodes (String seriesName, Date from, Date to, boolean findNextAfterTo)
    {
        Date parsedAirDate=null;
        Date previousParsedAirDate=null;
        String DATE_FORMAT = "d/MMM/yy";        
       
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);        
        Pattern pattern =Pattern.compile(regex);
        
        Vector<SeasonEpisode> episodes = new Vector<SeasonEpisode>();
        
        try 
        {
            URL epguides = new URL("http://www.epguides.com/" + seriesName + "/");
            BufferedReader in = new BufferedReader( new InputStreamReader(epguides.openStream())); 
            
            /* An integer that holds the number of times while has gone through
             * and also shows a unique increment number that can be used for comparison */             
            int runs = 0;      
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {
                
                previousParsedAirDate = parsedAirDate;
                
                if (runs==0)
                {
                    /* RE-initialize variables for first time runs (run=0)
                     * It's necessary in order to avoid conflicts with multiple shows parsing
                     * But could be skipped depending on how we'll use the parser */                
                    foundNext=!findNextAfterTo;
                    foundDouble=false;
                    currentIsDouble=false;
                    nextIsDouble=false;
                    currentEpisode=null;
                    currentSeason=null;
                    currentAirDate=null;
                    nextEpisode=null;
                    nextSeason=null;
                    nextAirDate=null;
                    doubleEpisode=null;
                    doubleSeason=null;
                    doubleAirDate=null;
                    try 
                    {
                        previousParsedAirDate=sdf.parse("1/Jan/50");                                              
                        
                    }
                    catch (java.text.ParseException pe)
                    {
                        // ParseAirDate failled
                        // TODO: Add Handling Code
                    }
                }
                Matcher matcher = pattern.matcher(inputLine);                 
                if (matcher.find()) 
                { 
                    // Increase runs by one if the parser finds a line containing our regular expression
                    runs++;
                    try
                    {
                        // Convert between String and Date
                        parsedAirDate = sdf.parse(matcher.group(7)+"/" + matcher.group(9) + "/" + matcher.group(11));
                         
                         // Find one episode after the "to" date if that is needed                         
                         if ((parsedAirDate.after(to)) & (!foundNext)) {
                             foundNext=true;
                             int season = Integer.parseInt(matcher.group(1));
                             int episode = Integer.parseInt(matcher.group(3));
                             Date airdate = parsedAirDate;
                             String title = "";
                        	 SeasonEpisode se = new SeasonEpisode(season, episode, airdate, title);
                        	 episodes.add(se);
                         }
                         // The if checks to see if the parsed episode is the current one       
                         /*if ((parsedAirDate.before(systemDate)) & (parsedAirDate.after(previousParsedAirDate)))
                         {
                             currentSeason = matcher.group(1);
                             currentEpisode = matcher.group(3);
                             currentAirDate = matcher.group(7)+"/" + matcher.group(9) + "/" + matcher.group(11);
                         }  */          
                         /* The if checks to see if the parsed episode has same air date with the previous
                          * wich means it's a double aired episode */                          
                         /*if (parsedAirDate.equals(previousParsedAirDate))
                         {
                        	 // TODO: add handling code for double episodes
                             foundDouble = true;
                             doubleSeason = matcher.group(1);
                             doubleEpisode = matcher.group(3);
                             doubleAirDate = matcher.group(7)+"/" + matcher.group(9) + "/" + matcher.group(11);
                         }*/
                         if (parsedAirDate.after(from) && parsedAirDate.before(to))
                         {
                        	 int season = Integer.parseInt(matcher.group(1));
                             int episode = Integer.parseInt(matcher.group(3));
                             Date airdate = parsedAirDate;
                             String title = "";
                        	 SeasonEpisode se = new SeasonEpisode(season, episode, airdate, title);
                        	 episodes.add(se);                    	 
                         }
                            
                         
                    }
                    catch (java.text.ParseException pe) 
                    {
                            // ParseAirDate or Season / episode failed
                            // TODO: Add Handling Code
                    }                       
                 } // matcher.find() ends             
            } // while ends
            in.close();
            
            /* The found double check must be out of the while loop in order to compare  
             * the last dates that are stored in the strings and not the passed double episodes */             
            if (foundDouble){
                if (doubleAirDate.equalsIgnoreCase(currentAirDate)){ currentIsDouble=true; }
                if (doubleAirDate.equalsIgnoreCase(nextAirDate)){ nextIsDouble=true; }
            } 
           
        } catch (MalformedURLException e) {
            // new URL() failed     
            // TODO: Add Handling Code

        } catch (IOException e) { 
            // openConnection() failed 
            // TODO: Add Handling Code
        }
        
        // sort the seasons and episodes in ascending order
        Collections.sort(episodes);
        
        return episodes;
        
    } // parse() ends
    
    
    public Vector<SeasonEpisode> getPastSeasonEpisodes(String showName)
    {
    	Date systemDate = new Date();   // Get time and date from system
        Date past = new Date();   // Get time and date from system
        past.setTime(0);
        return this.parseSeasonEpisodes(showName, past, systemDate, false);
    }
    
    public Vector<SeasonEpisode> getFutureSeasonEpisodes(String showName)
    {
    	// system date
       	Date systemDate = new Date();
       
        // one year from now
        Calendar yearFromNow = Calendar.getInstance();
        yearFromNow.add(Calendar.YEAR, 1);
    	return this.parseSeasonEpisodes(showName, systemDate, yearFromNow.getTime(), false);
    }
    
    public Vector<SeasonEpisode> getScheduledSeasonEpisodes(String showName)
    {
    	Date past = new Date();   // Get time and date from system
        past.setTime(0);
       
        // one year from now
        Calendar yearFromNow = Calendar.getInstance();
        yearFromNow.add(Calendar.YEAR, 1);
    	return this.parseSeasonEpisodes(showName, past, yearFromNow.getTime(), false);
    }
    
} // TedEpguidesParser ends
