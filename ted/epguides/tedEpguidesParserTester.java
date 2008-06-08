package ted.epguides;

public class tedEpguidesParserTester
{
    public static void main(String args[]){
        
        // New instance of the parser
        EpguidesParser tedEP = new EpguidesParser();
        
        tedEP.getPastSeasonEpisodes(args[0], false);
        tedEP.getFutureSeasonEpisodes(args[0], false);
                
    }
}
