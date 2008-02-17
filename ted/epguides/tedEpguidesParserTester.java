package ted.epguides;


public class tedEpguidesParserTester
{
    public static void main(String args[]){
        
        // New instance of the parser
        EpguidesParser tedEP = new EpguidesParser();
        // Call the parse() method of the parser 
        // to parse the epguides coresponding webpage
        tedEP.parse(args[0]);
        
        
        System.out.println("\n--------------------------");                             
        System.out.println("Current Episode ");            
        System.out.println("Season: " + tedEP.getCurrentSeason()); 
        System.out.println("Episode: " + tedEP.getCurrentEpisode()); 
        System.out.println("Air Date: " + tedEP.getCurrentAirDate()); 
        if (tedEP.getCurrentIsDouble()) { System.out.println("* Double Episode *"); } 
        System.out.println("--------------------------\n");
            
        if (tedEP.getFoundNext()){            
            System.out.println("--------------------------");
            System.out.println("Next Episode ");                
            System.out.println("Season: " + tedEP.getNextSeason()); 
            System.out.println("Episode: " + tedEP.getNextEpisode()); 
            System.out.println("Air Date: " + tedEP.getNextAirDate());
            if (tedEP.getNextIsDouble()) { System.out.println("* Double Episode *"); };
            System.out.println("--------------------------");
        } else{
            System.out.println("--------------------------");
            System.out.println("Next Episode");  
            System.out.println("To Be Announced"); 
            System.out.println("--------------------------");
        }        
                
    }
}
