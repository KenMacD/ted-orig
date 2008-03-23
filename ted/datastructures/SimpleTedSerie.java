package ted.datastructures;

import java.util.Vector;

import ted.epguides.EpguidesParser;

/**
 * @author Roel
 *
 */
public class SimpleTedSerie
{
	private String name;

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return If the show name starts with The, The gets
	 * 		   placed at the end of the name.
	 */
	public String getDisplayName()
	{
		String returnName = name;
		if(name.startsWith("The "))
		{
			returnName = name.substring(4) + ", The";
		}
		
		return returnName;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return A vector of episodes that are currently aired (from epguides info)
	 * and the next episode
	 */
	public Vector<SeasonEpisode> getAiredEpisodes()
	{
		// New instance of the parser
        EpguidesParser tedEP = new EpguidesParser();
        
        return tedEP.getPastSeasonEpisodes(this.getName());
	}
	
	
}
