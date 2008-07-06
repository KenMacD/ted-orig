package ted.datastructures;

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
	
	public String getDisplayName()
	{
		return name;
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
//	public Vector<StandardStructure> getAiredEpisodes()
//	{
//		// New instance of the parser
//        EpguidesParser tedEP = new EpguidesParser();
//        
//        return tedEP.getPastSeasonEpisodes(this.getName());
//	}
	
	
}
