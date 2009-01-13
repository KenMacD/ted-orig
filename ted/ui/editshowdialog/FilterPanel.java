package ted.ui.editshowdialog;
import java.awt.TextField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import ted.Lang;
import ted.TedSerie;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class FilterPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4433322536308265906L;
	private JLabel label_minSize;
	private JSpinner spinner_maxSize;
	private TextField keyword_text;
	private JLabel label_keywords;
	private JLabel label_keywords3;
	private JLabel label_keywords2;
	private JLabel label_keywords1;
	private JSeparator jSeparator1;
	private JLabel label_Seeders;
	private JSpinner text_minSeeders;
	private JLabel label_seederFilters;
	private JSeparator jSeparator4;
	private JLabel label_mb2;
	private JLabel label_maxSize;
	private JLabel label_mb1;
	private JSpinner text_minSize;
	private JLabel label_sizeFilters;
	
	private SpinnerNumberModel maxSizeSpinnerModel = new SpinnerNumberModel();
	private SpinnerNumberModel minSizeSpinnerModel = new SpinnerNumberModel();
	private SpinnerNumberModel minSeedersSpinnerModel = new SpinnerNumberModel();
	
	public FilterPanel()
	{
		this.initUI();
	}

	private void initUI()
	{
		try 
		{
			FormLayout thisLayout = new FormLayout(
				"max(p;5dlu), 90dlu:grow, 40dlu, 5dlu, max(p;5dlu):grow, max(p;15dlu)",
				"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), 5dlu, max(p;15dlu), max(p;15dlu), 5dlu, max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu):grow, max(p;10px)");
			this.setLayout(thisLayout);
			{
				label_sizeFilters = new JLabel();
				this.add(label_sizeFilters, new CellConstraints("2, 2, 4, 1, default, default"));
				label_sizeFilters.setText(Lang.getString("TedEpisodeDialog.LabelSize2"));
			}
			{
				label_minSize = new JLabel();
				this.add(label_minSize, new CellConstraints("2, 3, 1, 1, default, default"));
				label_minSize.setText(Lang
					.getString("TedEpisodeDialog.LabelMinSize2"));
			}
			{
				text_minSize = new JSpinner();
				text_minSize.setModel(minSizeSpinnerModel);
				this.add(text_minSize, new CellConstraints("3, 3, 1, 1, default, default"));
			}
			{
				label_mb1 = new JLabel();
				this.add(label_mb1, new CellConstraints("5, 3, 1, 1, default, default"));
				label_mb1.setText(Lang
					.getString("TedEpisodeDialog.LabelMegaByte"));
			}
			{
				label_maxSize = new JLabel();
				this.add(label_maxSize, new CellConstraints("2, 4, 1, 1, default, default"));
				label_maxSize.setText(Lang
					.getString("TedEpisodeDialog.LabelMaxSize"));
			}
			{
				spinner_maxSize = new JSpinner();
				spinner_maxSize.setModel(maxSizeSpinnerModel);
				this.add(spinner_maxSize, new CellConstraints("3, 4, 1, 1, default, default"));
			}
			{
				label_mb2 = new JLabel();
				this.add(label_mb2, new CellConstraints("5, 4, 1, 1, default, default"));
				label_mb2.setText(Lang
					.getString("TedEpisodeDialog.LabelMegaByte"));
			}
			{
				jSeparator4 = new JSeparator();
				this.add(jSeparator4, new CellConstraints("2, 5, 4, 1, default, default"));
			}
			{
				label_seederFilters = new JLabel();
				this.add(label_seederFilters, new CellConstraints("2, 6, 4, 1, default, default"));
				label_seederFilters.setText(Lang
					.getString("TedEpisodeDialog.LabelSeeders2"));
			}
			{
				text_minSeeders = new JSpinner();
				text_minSeeders.setModel(minSeedersSpinnerModel);
				this.add(text_minSeeders, new CellConstraints("3, 7, 1, 1, default, default"));
			}
			{
				label_Seeders = new JLabel();
				this.add(label_Seeders, new CellConstraints("5, 7, 1, 1, default, default"));
				label_Seeders.setText(Lang
					.getString("TedEpisodeDialog.LabelSeeders"));
			}
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1, new CellConstraints("2, 8, 4, 1, default, default"));
			}
			{
				label_keywords1 = new JLabel();
				this.add(label_keywords1, new CellConstraints("2, 9, 4, 1, default, default"));
				label_keywords1.setText(Lang
					.getString("TedEpisodeDialog.LabelKeywords"));
			}
			{
				label_keywords2 = new JLabel();
				this.add(label_keywords2, new CellConstraints("2, 10, 4, 1, default, default"));
				label_keywords2.setText(Lang
					.getString("TedEpisodeDialog.LabelKeywordsHelp2"));
			}
			{
				label_keywords3 = new JLabel();
				this.add(label_keywords3, new CellConstraints("2, 11, 4, 1, default, default"));
				label_keywords3.setText(Lang
					.getString("TedEpisodeDialog.LabelKeywordsHelp3"));
			}
			{
				label_keywords = new JLabel();
				this.add(label_keywords, new CellConstraints("2, 12, 1, 1, default, top"));
				label_keywords.setText(Lang
					.getString("TedEpisodeDialog.LabelKeywords1"));
			}
			{
				keyword_text = new TextField();
				this.add(keyword_text, new CellConstraints("3, 12, 3, 1, default, fill"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Integer value = new Integer(0);
		this.maxSizeSpinnerModel.setMinimum(value);
		this.minSeedersSpinnerModel.setMinimum(value);
		this.minSizeSpinnerModel.setMinimum(value);
	}
	
	public void addKeywords(String key)
	{
		String oldKeywords = this.keyword_text.getText();
		String newKeywords;
		if(!oldKeywords.contains(key))
		{
			if(!oldKeywords.equals(""))
				newKeywords = "((" + oldKeywords + ")&" + key + ')';
			else 
				newKeywords = key;
			
			this.keyword_text.setText(newKeywords);
		}
	}

	public void setValues(TedSerie serie)
	{
		// convert to integers
		Integer minSize = new Integer (serie.getMinSize());
		Integer maxSize = new Integer (serie.getMaxSize());
		Integer minSeeders = new Integer (serie.getMinNumOfSeeders());
		
		text_minSize.setValue(minSize);
		spinner_maxSize.setValue(maxSize);
		text_minSeeders.setValue(minSeeders);
		keyword_text.setText(""+serie.getKeywords());
		
	}

	public boolean checkValues() 
	{
		int min = this.minSizeSpinnerModel.getNumber().intValue();
		int max = this.maxSizeSpinnerModel.getNumber().intValue();
			
		if(min>max)
		{
			JOptionPane.showMessageDialog(null, Lang.getString("TedEpisodeDialog.DialogMinLargerThanMax")); //$NON-NLS-1$
			return false;
		}
		if(!checkBrackets(keyword_text.getText()))
		{
			JOptionPane.showMessageDialog(null, Lang.getString("TedEpisodeDialog.DialogBrackets")); //$NON-NLS-1$
			return false;
		}	
		return true;
	}
	
	private boolean checkBrackets(String s)
	{
		boolean result = false;
		int count = 0;
		char c;
		
		for(int i=0; i<s.length(); i++)
		{
			c = s.charAt(i);
			if(c=='(')
				count++;
			else if(c==')')
				count--;
		}
		
		if(count==0)
			result = true;
		
		return result;
	}

	public void saveValues(TedSerie currentSerie) 
	{
		if (this.checkValues())
		{
			int min = this.minSizeSpinnerModel.getNumber().intValue();
			int max = this.maxSizeSpinnerModel.getNumber().intValue();
			int minSeeders = this.minSeedersSpinnerModel.getNumber().intValue();
			currentSerie.setMinSize(min);
			currentSerie.setMaxSize(max);
			currentSerie.setKeywords(keyword_text.getText().toLowerCase());
			currentSerie.setMinNumOfSeeders(minSeeders);
		}
		
	}

}
