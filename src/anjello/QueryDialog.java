/**
 * Travel Recommender example for the jCOLIBRI2 framework. 
 * @author Juan A. Recio-García.
 * GAIA - Group for Artificial Intelligence Applications
 * http://gaia.fdi.ucm.es
 * 25/07/2006
 */
package anjello;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import jcolibri.cbrcore.CBRQuery;
import anjello.ConstructionDescription;
import anjello.ConstructionRecommender;
import anjello.TravelDescription.AccommodationTypes;
import anjello.TravelDescription.Seasons;
import anjello.ConstructionDescription.ClientTypes;
import anjello.ConstructionDescription.ClientExperiences;
import anjello.ConstructionDescription.BuildTypes;
import anjello.ConstructionDescription.CostParameters;
import anjello.ConstructionDescription.TimeParameters;
import jcolibri.util.FileIO;

/**
 * Query dialgo
 * @author Juan A. Recio-Garcia
 * @version 1.0
 */
public class QueryDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	JLabel image;
	
	JComboBox clientTypeCombo;
	JComboBox clientExperience;
	RegionSelector building;
	JComboBox buildType;
	JComboBox costParameterCombo;
	JComboBox timeParameterCombo;
	JComboBox accommodation;
	
	public QueryDialog(JFrame parent)
	{
		super(parent,true);
		configureFrame();
	}
	
	private void configureFrame()
	{
		try
		{
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1)
		{
		}
		
		this.setTitle("Configure Query - Prototype developed and owned by Anjello - Anjelloatoz@gmail.com");
		
		image = new JLabel();
//		image.setIcon(new ImageIcon(FileIO.findFile("jcolibri/examples/TravelRecommender/gui/step1.png")));
		this.getContentPane().setLayout(new BorderLayout());
//		this.getContentPane().add(image, BorderLayout.WEST);
		
		
		/**********************************************************/
		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(8,2));
		panel.setLayout(new SpringLayout());
		
		JLabel label;
		panel.add(label = new JLabel("Attribute"));
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		panel.add(label = new JLabel("Value"));
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		
		panel.add(new JLabel("Client Type"));
		String[] clientTypes = {"Private", "Public"};
		panel.add(clientTypeCombo = new JComboBox(clientTypes));
		
		panel.add(new JLabel("Client Experience"));
		String[] experienceTypes = {"Inexperienced", "PartlyExperienced", "HighlyExperienced"};
		panel.add(clientExperience = new JComboBox(experienceTypes));
		
		panel.add(new JLabel("Building Type"));
		//String[] regions = {
		//		"AdriaticSea","Algarve","Allgaeu","Alps","Atlantic","Attica","Balaton","BalticSea","Bavaria","Belgium","BlackForest","Bornholm","Brittany","Bulgaria","Cairo","Carinthia","Chalkidiki","Corfu","Corsica","CostaBlanca","CostaBrava","CotedAzur","Crete","Czechia","Denmark","Egypt","England","ErzGebirge","Fano","France","Fuerteventura","GiantMountains","GranCanaria","Harz","Holland","Ibiza","Ireland","LakeGarda","Lolland","Madeira","Mallorca","Malta","Normandy","NorthSea","Poland","Rhodes","Riviera","SalzbergerLand","Scotland","Slowakei","Styria","Sweden","Teneriffe","Thuringia","Tunisia","TurkishAegeanSea","TurkishRiviera","Tyrol","Wales"};
		panel.add(building = new RegionSelector(this));
		
		panel.add(new JLabel("Build Type"));
		String[] build_types = {"GreenField", "BrownField", "Refurb", "Refit"};
		panel.add(buildType = new JComboBox(build_types));
		
		panel.add(new JLabel("Cost Parameter"));
		String[] cost_parameters = {"LowestCost", "HighestCost", "CostCertainty"};
		panel.add(costParameterCombo = new JComboBox(cost_parameters));
		
		panel.add(new JLabel("Time Parameter"));
		String[] timeParameters = {"ShortestTime", "EarliestCommencement", "TimeCertainty"};
		panel.add(timeParameterCombo = new JComboBox(timeParameters));
		
		panel.add(new JLabel(""));
		String[] accommodations = {};
		panel.add(accommodation = new JComboBox(accommodations));
		
		
//		Lay out the panel.
		Utils.makeCompactGrid(panel,
		                8, 2, //rows, cols
		                6, 6,        //initX, initY
		                10, 10);       //xPad, yPad
		
		JPanel panelAux = new JPanel();
		panelAux.setLayout(new BorderLayout());
		panelAux.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelAux.add(panel,BorderLayout.NORTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());
		
		JButton ok = new JButton("Set Query >>");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setQuery();
			}
		});
		buttons.add(ok,BorderLayout.CENTER);
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					ConstructionRecommender.getInstance().postCycle();
				} catch (Exception ex) {
					org.apache.commons.logging.LogFactory.getLog(ConstructionRecommender.class).error(ex);
				}
				System.exit(-1);
			}
		});
		buttons.add(exit,BorderLayout.WEST);
		
		panelAux.add(buttons, BorderLayout.SOUTH);
		this.getContentPane().add(panelAux, BorderLayout.CENTER);
		
		/**********************************************************/
		
		
		this.pack();
		this.setSize(600, this.getHeight());
		this.setResizable(false);
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - this.getWidth()) / 2,
			(screenSize.height - this.getHeight()) / 2, 
			getWidth(),
			getHeight());
	}
	
	void setQuery()
	{
		this.setVisible(false);
	}
	
	public CBRQuery getQuery()
	{
		ConstructionDescription desc = new ConstructionDescription();
		desc.setClientType(ClientTypes.valueOf((String)this.clientTypeCombo.getSelectedItem()));
		desc.setClientExperience(ClientExperiences.valueOf((String)this.clientExperience.getSelectedItem()));
		desc.setBuildingType(this.building.getSelectedInstance());
		desc.setBuildType(BuildTypes.valueOf((String)this.buildType.getSelectedItem()));
		desc.setCostParameter(CostParameters.valueOf((String)this.costParameterCombo.getSelectedItem()));
		desc.setTimeParameter(TimeParameters.valueOf((String)this.timeParameterCombo.getSelectedItem()));

/*		desc.setHolidayType((String)this.clientTypeCombo.getSelectedItem());
		desc.setNumberOfPersons(this.numberOfPersons.getNumber().intValue());
		
		desc.setSeason(Seasons.valueOf((String)this.season.getSelectedItem()));
		desc.setTransportation((String)this.transportation.getSelectedItem());
	*/	
		CBRQuery query = new CBRQuery();
		query.setDescription(desc);
		
		return query;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QueryDialog qf = new QueryDialog(null);
		qf.setVisible(true);
		System.out.println("Bye");
	}

	

}
