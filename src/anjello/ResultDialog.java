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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

import jcolibri.cbrcore.CBRCase;
import anjello.ConstructionDescription;
import anjello.TravelRecommender;
import anjello.ConstructionSolution;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.util.FileIO;

/**
 * Result dialog
 * @author Juan A. Recio-Garcia
 * @version 1.0
 */
public class ResultDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	JLabel image;
	
	JLabel holidayType;
	JLabel  numberOfPersons;
	JLabel region;
	JLabel transportation;
	JLabel  duration;
	JLabel season;
	JLabel accommodation;
	JLabel caseId;
	JLabel price;
	JLabel hotel;
	
	ArrayList<RetrievalResult> cases;
	int currentCase;
	
	public ResultDialog(JFrame main)
	{
		super(main,true);
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
		
		this.setTitle("Retrived cases - Prototype developed and owned by Anjello - Anjelloatoz@gmail.com");

		
		image = new JLabel();
//		image.setIcon(new ImageIcon(FileIO.findFile("jcolibri/examples/TravelRecommender/gui/step3.png")));
		this.getContentPane().setLayout(new BorderLayout());
//		this.getContentPane().add(image, BorderLayout.WEST);
		
		
		/**********************************************************/
		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(8,2));
		panel.setLayout(new SpringLayout());
		
		JLabel label;

		panel.add(label = new JLabel("Description"));
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		panel.add(label = new JLabel());

		
		panel.add(new JLabel("Client Type"));
		panel.add(holidayType = new JLabel("adsfadfadsf"));
		
		panel.add(new JLabel("Client Experience"));
		panel.add(this.numberOfPersons = new JLabel());
		
		panel.add(new JLabel("Cost Parameter"));
		panel.add(this.region = new JLabel());
		
		panel.add(new JLabel("--"));
		panel.add(this.transportation = new JLabel());
		
		panel.add(new JLabel("Build Type"));
		panel.add(this.duration = new JLabel());
		
		panel.add(new JLabel("Time Parameter"));
		panel.add(this.season = new JLabel());
		
		panel.add(new JLabel("Building Type"));
		panel.add(this.accommodation = new JLabel());
		
		panel.add(label = new JLabel("Solution"));
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		panel.add(label = new JLabel());

		
		panel.add(new JLabel("Organizational Stratergy"));
		panel.add(price = new JLabel());
		
		panel.add(new JLabel("Payment Modality"));
		panel.add(hotel = new JLabel());
		
//		Lay out the panel.
		Utils.makeCompactGrid(panel,
		                11, 2, //rows, cols
		                6, 6,        //initX, initY
		                30, 10);       //xPad, yPad
		
		JPanel casesPanel = new JPanel();
		casesPanel.setLayout(new BorderLayout());
		casesPanel.add(panel, BorderLayout.CENTER);
		
		JPanel casesIterPanel = new JPanel();
		casesIterPanel.setLayout(new FlowLayout());
		JButton prev = new JButton("<<");
		casesIterPanel.add(prev);
		casesIterPanel.add(caseId = new JLabel("Case id"));
		JButton follow = new JButton(">>");
		casesIterPanel.add(follow);
		
		prev.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentCase = (currentCase+cases.size()-1) % cases.size();
				showCase();
			}
		});
		
		follow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentCase = (currentCase+1) % cases.size();
				showCase();
			}
		});
		
		casesPanel.add(casesIterPanel, BorderLayout.NORTH);
		
		
		JPanel panelAux = new JPanel();
		panelAux.setLayout(new BorderLayout());
		panelAux.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelAux.add(casesPanel,BorderLayout.NORTH);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());
		
		JButton ok = new JButton("Next >>");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		buttons.add(ok,BorderLayout.CENTER);
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					TravelRecommender.getInstance().postCycle();
				} catch (Exception ex) {
					org.apache.commons.logging.LogFactory.getLog(TravelRecommender.class).error(ex);
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
	
	void next()
	{
		this.setVisible(false);
	}
	
	
	public void showCases(Collection<RetrievalResult> eval, Collection<CBRCase> selected)
	{
		cases = new ArrayList<RetrievalResult>();
		for(RetrievalResult rr: eval)
			if(selected.contains(rr.get_case()))
				cases.add(rr);
		currentCase = 0;
		showCase();
	}
	
	void showCase()
	{
		System.out.println("showCase 01: "+cases.size());
		RetrievalResult rr = cases.get(currentCase);
		System.out.println("showCase 02: "+rr.toString());
		double sim = rr.getEval();
		System.out.println("showCase 03");
		CBRCase _case = rr.get_case();
		System.out.println("showCase 04");
		this.caseId.setText(_case.getID().toString()+" -> "+sim+" ("+(currentCase+1)+"/"+cases.size()+")");
		System.out.println("showCase 05");
		ConstructionDescription desc = (ConstructionDescription) _case.getDescription();
		System.out.println("showCase 06");
		this.accommodation.setText(desc.getBuildingType().toString());
		this.duration.setText(desc.getBuildType().toString());
		this.holidayType.setText(desc.getClientType().toString());
		this.numberOfPersons.setText(desc.getClientExperience().toString());
		this.region.setText(desc.getCostParameter().toString());
		this.season.setText(desc.getTimeParameter().toString());
		System.out.println("showCase 07");
//		this.transportation.setText(desc.getTransportation());
		
		ConstructionSolution sol = (ConstructionSolution) _case.getSolution();
		System.out.println("showCase 08");
		this.price.setText(sol.getOrganizationalStrategy().toString());
		System.out.println("showCase 09");
		this.hotel.setText(sol.getPaymentModality().toString());
		System.out.println("showCase 10");
		JOptionPane.showMessageDialog(null, "Prototype only for testing.\nSoftware developed and owned by Anjello.\nAnjelloatoz@gmail.com");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ResultDialog qf = new ResultDialog(null);
		qf.setVisible(true);
		System.out.println("Bye");
	}

	

}
