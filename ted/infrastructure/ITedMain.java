package ted.infrastructure;

public interface ITedMain {

	void repaint();

	boolean getStopParsing();

	void displayError(String string, String message, String string2);
	
	public void saveShows();

	void displayHurray(String string, String message, String string2);

}
